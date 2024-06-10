<template>
  <div class="container">
    <div v-if="isLoggedIn" class="hello">
      <!-- Admin Menu -->
      <div v-if="isAdmin" class="admin-menu">
        <h1 class="text-center">Welcome! {{ getUserName }}</h1>
        <h2 class="text-center text-info">Employee menu</h2>
        <div class="row">
          <!-- Manage Accounts -->
          <div class="col-sm-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Manage accounts</h5>
                <p class="card-text">This is quick access to manage the accounts</p>
                <router-link to="/accounts" class="btn btn-primary">Accounts view</router-link>
              </div>
            </div>
          </div>
          <!-- Manage Users -->
          <div class="col-sm-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Manage users</h5>
                <p class="card-text">This is quick access to manage the users</p>
                <router-link to="/users" class="btn btn-primary">User view</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Customer Menu -->
      <div class="admin-menu">
        <h2 class="text-center text-muted">Customer menu</h2>
        <div class="row">
          <!-- Deposit -->
          <div class="col-sm-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Deposit</h5>
                <p class="card-text">This is quick access to make a deposit</p>
                <router-link to="/deposit" class="btn btn-success text-white">Deposit</router-link>
              </div>
            </div>
          </div>
          <!-- Withdraw -->
          <div class="col-sm-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Withdraw</h5>
                <p class="card-text">This is quick access to make a withdrawal</p>
                <router-link to="/withdraw" class="btn btn-info text-white">Withdraw</router-link>
              </div>
            </div>
          </div>
          <!-- Make a Transaction -->
          <div class="col-sm-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Make a transaction</h5>
                <p class="card-text">This is quick access to make a transaction</p>
                <router-link to="/transaction" class="btn btn-warning text-white">Make transaction</router-link>
              </div>
            </div>
          </div>
          <!-- View Transactions -->
          <div class="col-sm-4">
            <div class="card mt-4"> <!-- Add margin top -->
              <div class="card-body">
                <h5 class="card-title">View Transactions</h5>
                <p class="card-text">This is quick access to view your transactions</p>
                <router-link to="/transactions" class="btn btn-primary">View Transactions</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Not Logged In -->
    <div v-if="!isLoggedIn" class="text-center">
      <p>You are not logged in. Please login!</p>
      <router-link to="/login" class="btn w-100 text-center submit vue-butt btn-success btn-lg">
        <span>Go to login</span>
      </router-link>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "HelloWorld",
  computed: {
    ...mapGetters(["isLoggedIn"]),
    ...mapGetters(["getUserName"]),
    ...mapGetters(["getUserRole"]),
    ...mapGetters(["isAdmin"]),
  },
};
</script>

<style scoped>
/* Add margin top to the card */
.admin-menu .card {
  margin-top: 20px;
}
</style>
