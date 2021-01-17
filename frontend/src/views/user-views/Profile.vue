<template>
  <div class="profile">
    <section class="wrapper">
      <div class="sidebar-list">
        <h4 class="sidebar-list-title">Welkom {{ user.firstname }}</h4>
        <ul>
          <router-link tag="li" :to="{name: 'MyDetails'}" class="sidebar-list-item">Mijn gegevens</router-link>
          <router-link tag="li" :to="{path: '/profiel/addressen/'}" class="sidebar-list-item">Mijn adres</router-link>
          <router-link tag="li" :to="{name: 'MyOrders'}" class="sidebar-list-item">Mijn bestellingen</router-link>
          <li class="sidebar-list-item logout-btn"><a @click="logout">Logout</a></li>
        </ul>
      </div>
      <div class="view">
        <router-view />
      </div>
    </section>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "Profile",
  components: {
  },
  data() {
    return {

    };
  },
  computed: {
    user: function () {
      return this.$store.getters.getUser;
    }
  },
  methods: {
    logout: function () {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push('/login')
        })
    }
  },
  created: function () {
    axios.interceptors.response.use(undefined, function (err) {
      return new Promise(function () {
        if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
          this.$store.dispatch("logout")
        }
        throw err;
      });
    });
  }
}
</script>

<style scoped>

.wrapper {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  /*margin-top: -3.5%;*/
  /*margin-bottom: -3.5%;*/
}

.sidebar-list {
  border: 1px solid var(--light-beige-color);
  padding: 1rem;
  margin-right: 2rem;
  width: 15rem;
}

.sidebar-list-title {
  margin-bottom: 1rem;
  text-transform: uppercase;
}

.sidebar-list-item {
  list-style: none;
  margin-bottom: 1rem;
  margin-left: 0.5rem;
}

.logout-btn {
  margin-top: 3rem;
  font-weight: bold;
  text-transform: uppercase;
}

</style>