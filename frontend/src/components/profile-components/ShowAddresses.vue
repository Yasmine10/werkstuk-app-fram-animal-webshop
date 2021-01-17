<template>
  <div class="address-list grid">
    <router-link tag="button" class="address-card create-address-card" :to="{name: 'AddAddress'}">
      <ph-plus :size="24" color="#000000" weight="fill" class="add-address-icon"/>
      <p>Adres toevoegen</p>
    </router-link>
    <div class="address-card" v-for="item in address" :key="item.id">
      <div class="title flex">
        <div class="icons">
          <router-link tag="a" :to="{name: 'EditAddress', params: {id: item.id}}" class="edit-address-btn" href=""><ph-pencil class="edit-address-icon" :size="24" weight="regular" color="#C89465"/></router-link>
          <a class="delete-address-btn" @click="deleteAddress(item.id)"><ph-trash class="delete-address-icon" :size="24" weight="regular" color="#C89465"/></a>
        </div>
      </div>
      <div class="details">
        <p class="address-card-user">{{ user.firstname }} {{ user.lastname }}</p>
        <p>{{ item.street }} {{ item.houseNr }}</p>
        <p>{{ item.zipcode }}  {{ item.city }}</p>
        <p>{{ item.country }}</p>
      </div>
    </div>

  </div>
</template>

<script>
import { PhPlus, PhPencil, PhTrash } from "phosphor-vue";

export default {
  name: "ShowAddresses",
  components: {
    PhPlus,
    PhPencil,
    PhTrash
  },
  computed: {
    address: function () {
      console.log(this.$store.state.address);
      return this.$store.state.address;
    },
    user: function () {
      return this.$store.getters.getUser;
    }
  },
  mounted() {
    this.$store.dispatch("loadAddress", this.user.id);
  },
  methods: {
    deleteAddress: function (id) {
      this.$store.dispatch('deleteAddress', id)
        .then(() => this.$router.push('/profiel/addressen/'))
        .catch(err => console.log(err));
    }
  }
}
</script>

<style scoped>

@import '../../assets/styles/stylesheet.css';

.add-address-icon {
  margin-right: 1rem;
}

.address-list {
  margin-top: 2rem;
}

.address-card {
  border: 2px solid var(--primary-color);
  /*padding: 3rem;*/
}

.title {
  padding-top: 0.75rem;
  padding-right: 0.75rem;
}

.address-card-user {
  font-weight: bold;
  font-size: large;
  font-family: 'Raleway', sans-serif;
  margin-bottom: 0.5rem;
}

.icons .edit-address-icon {
  margin-right: 0.5rem;
}

.flex {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-column-gap: 1.5rem;
  grid-row-gap: 1.5rem;
}

.create-address-card {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: medium;
  background-color: transparent;
}

.create-address-card:hover {
  background-color: var(--primary-color);
  font-weight: bold;
  outline-color: var(--primary-color);
}

.create-address-card:hover .add-address-icon color {
  color: var(--light-color);
}

.details {
  padding: 2rem 3rem 3rem 3rem;
}

</style>