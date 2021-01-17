<template>
  <div class="wrapper">
    <div class="links-wrapper">
      <div class="product-div">
        <h4>Producten</h4>
        <router-link tag="a" to="/Hond">Hond</router-link>
        <router-link tag="a" to="/Kat">Kat</router-link>
      </div>
      <div class="customer-service-div">
        <h4>Klantenservice</h4>
        <router-link tag="a" to="">Betalen</router-link>
        <router-link tag="a" to="">Levertijd en verzendkosten</router-link>
        <router-link tag="a" to="">Retouren</router-link>
        <router-link tag="a" to="">Veelgestelde vragen</router-link>
      </div>
      <div class="information-div">
        <h4>Informatie</h4>
        <router-link tag="a" to="">Over ons</router-link>
        <router-link tag="a" to="">Privacy verklaring</router-link>
        <router-link tag="a" to="">Algemene voorwaarden</router-link>
        <router-link tag="a" to="">Contact</router-link>
      </div>
      <div class="social-media-div">
        <h4>Social media</h4>
        <router-link tag="a" to=""><ph-facebook-logo :size="30" color="#484848" weight="fill" class="social-media-icon"/></router-link>
        <router-link tag="a" to=""><ph-instagram-logo :size="30" color="#484848" weight="fill" class="social-media-icon"/></router-link>
        <router-link tag="a" to=""><ph-twitter-logo :size="30" color="#484848" weight="fill" class="social-media-icon"/></router-link>
      </div>
    </div>
    <div class="text-bottom">
      <p>© Dierenwinkel - 2020</p>
    </div>
  </div>
</template>

<script>
import { PhFacebookLogo, PhInstagramLogo, PhTwitterLogo } from "phosphor-vue";

export default {
  name: "Footer",
  components: {
    PhFacebookLogo,
    PhInstagramLogo,
    PhTwitterLogo
  }
}
</script>

<style scoped>

.wrapper {
  background-color: var(--light-color);
  padding: 0 10%;
  /*margin-top: 2rem;*/
}

.links-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding-top: 2rem;
  padding-bottom: 2rem;
}

.product-div, .customer-service-div, .information-div, social-media-div {
  display: flex;
  flex-direction: column;
}

h4 {
  text-transform: uppercase;
  font-family: 'Raleway', sans-serif;
  margin-bottom: 1rem;
}

a {
  color: var(--dark-color);
  text-decoration: none;
  padding-bottom: 0.5rem;
  font-family: 'Quicksand', sans-serif;
}

a:hover {
  color: var(--primary-color);
}

.social-media-icon:not(first-child) {
  margin-right: 0.5rem;
}

.text-bottom {
  border-top: 1px solid lightgrey;
  text-align: center;
  padding: 1rem 0;
  font-family: 'Quicksand', sans-serif;
}

</style>