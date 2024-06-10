import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory } from 'vue-router'
import store from './store/store'

//Users
import Login from './components/users/Login.vue';
import Register from './components/users/Register.vue';
import Users from './components/users/Users.vue';

import HomePage from './components/HomePage.vue';
//Accounts
import Index from './components/accounts/Index.vue';
import AddAccount from './components/accounts/AddAccount.vue';
//Transactions
import Deposit from './components/transactions/Deposit.vue';
import Withdraw from './components/transactions/Withdraw.vue';
import MakeTrans from './components/transactions/MakeTrans.vue';
import Transactions from "@/components/transactions/Transactions.vue";



const routes = [

    //user routes
    { path: '/', component: Login },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/users', component: Users },
    { path: '/home', component: HomePage },

    //account routes
    { path: '/accounts', component: Index },
    { path: '/addaccount', component: AddAccount },
    //transaction routes
    { path: '/deposit', component: Deposit },
    { path: '/withdraw', component: Withdraw },
    { path: '/transaction', component: MakeTrans },
    { path: '/transactions', component: Transactions },


];


const router = createRouter({
    "history": createWebHistory(),
    routes
})

const app = createApp(App);
app.use(router);
app.use(store);
app.mount('#app');
