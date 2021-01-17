<template>
  <div>
    <h3>Mijn adres</h3>
    <p class="subtitle">Adres bewerken of toevoegen</p>
    <router-view></router-view>
  </div>
</template>

<script>


export default {
  name: "MyAddresses",
  components: {

  }
}
</script>

<style scoped>

@import '../../assets/styles/stylesheet.css';

h3 {
  color: var(--secondary-color);
  font-family: 'Quicksand', sans-serif;
}

.subtitle {
  margin-top: 0.5rem;
}



</style>