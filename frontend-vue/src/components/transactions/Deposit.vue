<template>
  <div v-if="isLoggedIn" class="container">
    <h1 class="text-center my-2 text-muted">
      Please select an account in the dropdown
    </h1>
    <div class="form-container">
      <div class="">
        <h1 v-if="balance" class="text-center text-muted">
          Current account balance: €{{ balance }}
        </h1>
      </div>

      <div class="input-group mx-0 text-center mb-3">
        <select
            @change="onChange($event)"
            class="w-100 text-center mx-0"
            :disabled="disable"
            v-model="selected"
        >
          <option :value="null" disabled>Select Account</option>
          <option
              v-for="account in accounts"
              v-bind:key="{ value: account.iban }"
          >
            {{ account.iban }}
          </option>
        </select>
      </div>
      <form v-on:submit.prevent="login" ref="depoform">
        <div v-if="balance" class="form-hider">
          <div class="input-group mb-3">
            <span class="input-group-text">Amount to deposit: €</span>
            <input
                @keypress="isNumber($event)"
                selected
                type="text"
                required="required"
                v-model="balInput"
                class="text-center form-control"
            />
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">Pincode</span>
            <div class="input-wrapper">
              <div>
                <div class="input-group">
                  <input
                      class="form-control"
                      :disabled="disable"
                      v-model.number="pin_0"
                      v-on:keyup.right="pin_focus('pin_1')"
                      v-on:keypress="is_valid_pin_value($event, 'pin_0')"
                      ref="pin_0"
                      type="text"
                      placeholder="0"
                  />
                  <input
                      class="form-control"
                      :disabled="disable"
                      v-model.number="pin_1"
                      v-on:keyup.left="pin_focus('pin_0')"
                      v-on:keyup.right="pin_focus('pin_2')"
                      v-on:keypress="is_valid_pin_value($event, 'pin_1')"
                      ref="pin_1"
                      type="text"
                      placeholder="0"
                  />

                  <input
                      class="form-control"
                      :disabled="disable"
                      v-model.number="pin_2"
                      v-on:keyup.left="pin_focus('pin_1')"
                      v-on:keyup.right="pin_focus('pin_3')"
                      v-on:keypress="is_valid_pin_value($event, 'pin_2')"
                      ref="pin_2"
                      type="text"
                      placeholder="0"
                  />
                  <input
                      class="form-control"
                      :disabled="disable"
                      v-model.number="pin_3"
                      v-on:keyup.left="pin_focus('pin_2')"
                      v-on:keypress="is_valid_pin_value($event, 'pin_3')"
                      ref="pin_3"
                      type="text"
                      placeholder="0"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="input-group mb-3">
            <button
                type="button"
                class="w-100 btn btn-primary"
                @click="deposit()"
            >
              Deposit
            </button>
          </div>
          <p v-if="errorMsg" class="text-danger">{{ errorMsg }}</p>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "../../axios-auth";
import {mapGetters} from "vuex";

export default {
  name: "Deposit",
  computed: {
    ...mapGetters(["isAdmin"]),
    ...mapGetters(["isLoggedIn"]),
    ...mapGetters(["getuserID"]),
  },
  data() {
    return {
      pin_0: null,
      pin_1: null,
      pin_2: null,
      pin_3: null,
      pinfull: null,
      pintoCheck: null,
      selected: "current",
      disable: false,
      accounts: [],
      userID: "",
      balance: "",
      balInput: "",
      errorMsg: "",
      myIban: "",
      token: "",
    };
  },
  watch: {
    pin: function () {
      this.$bus.$emit("PIN/change", this.pin);
    },
    pin_0: function (nv) {
      if (nv.toString().length !== 0) {
        this.$refs.pin_1.focus();
        this.$refs.pin_1.select();
      }
    },
    pin_1: function (nv) {
      if (nv.toString().length !== 0) {
        this.$refs.pin_2.focus();
        this.$refs.pin_2.select();
      }
    },
    pin_2: function (nv) {
      if (nv.toString().length !== 0) {
        this.$refs.pin_3.focus();
        this.$refs.pin_3.select();
      }
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.userID = localStorage.getItem("userID");
    axios
        .request({
          url: "accounts/getByUserID/" + this.userID,
          method: "get",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((response) => {
          console.log(response.data);
          this.accounts = response.data;
        })
        .catch((error) => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));
  },
  methods: {
    isNumber: function (evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (
          charCode > 31 &&
          (charCode < 48 || charCode > 57) &&
          charCode !== 46
      ) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    pin_focus: function (ref) {
      this.$refs[ref].focus();
      this.$refs[ref].select();
    },
    is_valid_pin_value: function (e, pin_N) {
      const char = String.fromCharCode(e.keyCode);
      const is_value_selected =
          this[pin_N] !== null &&
          this.$refs[pin_N].selectionStart === 0 &&
          this.$refs[pin_N].selectionEnd === this[pin_N].toString().length;
      if (
          (this[pin_N] === null ||
              this[pin_N].toString().length === 0 ||
              is_value_selected) &&
          parseInt(char, 10) >= 0 &&
          parseInt(char, 10) <= 9
      ) {
        return true;
      }
      e.preventDefault();
    },
    deposit() {
      if (
          this.balInput != "" &&
          this.pin_0 != null &&
          this.pin_1 != null &&
          this.pin_2 != null &&
          this.pin_3 != null
      ) {
        const stringCode =
            this.pin_0.toString() +
            this.pin_1.toString() +
            this.pin_2.toString() +
            this.pin_3.toString();
        const balFloat = parseFloat(this.balInput);
        if (stringCode == this.pintoCheck && balFloat) {
          //deposit axios method here
          this.pinfull = parseInt(stringCode);
          this.depositAxios();
        } else {
          this.errorMsg = "pincode is incorrect";
        }
        console.log(stringCode + this.pintoCheck);
      } else {
        this.errorMsg = "please fill in all the fields";
      }
    },
    depositAxios() {
      const today = new Date();
      const date =
          today.getFullYear() +
          "-" +
          ("0" + (today.getMonth() + 1)).slice(-2) +
          "-" +
          ("0" + today.getDate()).slice(-2) +
          "T" +
          ("0" + today.getHours()).slice(-2) +
          ":" +
          ("0" + today.getMinutes()).slice(-2) +
          ":" +
          ("0" + today.getSeconds()).slice(-2);
      const data = JSON.stringify({
        transactionType: "deposit",
        timestamp: date,
        amount: parseFloat(this.balInput),
        userPerforming: this.userID,
        from: "NL01INHO0000000001",
        to: this.myIban,
        pincode: this.pinfull,
      });
      let config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${this.token}`,
        },
      };

      axios
          .post("transactions", data, config)
          .then((response) => {
            console.log(response);
            this.$router.replace("/transactions");
          })
          .catch((error) => {
            this.errorMsg = error.response.data.reason;
          });
    },
    onChange(event) {
      axios
          .request({
            url: "accounts/getByIban/" + event.target.value,
            method: "get",
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json; charset=UTF-8",
              Authorization: `Bearer ${this.token}`,
            },
          })
          .then((response) => {
            this.pin_0 = null;
            this.pin_1 = null;
            this.pin_2 = null;
            this.pin_3 = null;
            this.balInput = null;
            this.errorMsg = "";
            this.balance = response.data.balance;
            this.pintoCheck = response.data.pinCode;
            this.myIban = response.data.iban;
            this.$refs.depoform.reset();
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => (this.loading = false));
    },
  },
};
</script>