<template>
  <div>
    <form class="edit-address-form" @submit.prevent="editAddress">
      <div class="address-div">
        <h4 class="title-address">Adres bewerken</h4>
        <div class="form-control flex-column">
          <label for="street">Straat</label>
          <input type="text" id="street" required v-model="address.street">
        </div>
        <div class="form-control flex-column">
          <label for="housenumber">Huisnummer</label>
          <input type="text" id="housenumber" required v-model="address.houseNr">
        </div>
        <div class="form-control flex-column">
          <label for="zipcode">Postcode</label>
          <input type="text" id="zipcode" required v-model="address.zipcode">
        </div>
        <div class="form-control flex-column">
          <label for="city">Gemeente</label>
          <input type="text" id="city" required v-model="address.city">
        </div>
        <div class="form-control flex-column">
          <label for="country">Land</label>
          <select id="country" v-model="country.selectedCountry">
            <option v-for="(country, index) in country.countryData" :key="index" :value="country">{{ country }}</option>
          </select>
        </div>
        <button type="submit" class="edit-address-btn btn">Adres wijzigen</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "EditAddress",
  data() {
    return {
      address: this.$store.getters.getAddress(this.$route.params.id),
      country: {
        countryData: [],
        selectedCountry: ''
      }
    };
  },
  computed: {

  },
  mounted() {
    let countries = ["België"];
    this.country.countryData = Object.assign({}, this.country.countryData, countries);
    this.country.selectedCountry = this.country.countryData[0];
  },
  methods: {
    editAddress: function () {
      this.$store.dispatch('updateAddress', {address: this.address})
        .then(() => this.$router.push('/profiel/addressen/'))
        .catch(err => console.log(err));
    }
  }
}
</script>

<style scoped>

@import '../../assets/styles/stylesheet.css';

h4 {
  text-transform: uppercase;
  margin-top: 1rem;
  font-size: smaller;
}

.flex-column {
  display: flex;
  flex-direction: column;
  margin-right: 1.5rem;
}

.form-control {
  margin: 1rem auto;
}

.form-control>label {
  margin-bottom: 0.5rem;
}

.form-control input, .form-control select, .form-control option {
  background-color: transparent;
  color: var(--dark-color);
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
  padding: 0.5rem 1rem;
  width: 20rem;
  border: none;
  outline: 1px solid lightgrey;
}

.title-address {
  margin-top: 1rem;
}

</style>