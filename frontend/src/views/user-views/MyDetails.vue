<template>
  <div>
    <h3>Mijn gegevens</h3>
    <div class="form-control">
      <label for="firstname">Voornaam</label>
      <input type="text" disabled name="firstname" id="firstname" :value="user.firstname">
    </div>
    <div class="form-control">
      <label for="lastname">Achternaam</label>
      <input type="text" disabled name="lastname" id="lastname" :value="user.lastname">
    </div>
    <div class="form-control">
      <label for="emailAddress">E-mailadres</label>
      <input type="email" disabled name="emailAddress" id="emailAddress" :value="user.email">
    </div>
<!--    <div class="form-control">-->
<!--      <label for="password">Wachtwoord</label>-->
<!--      <input type="password" disabled name="password" id="password">-->
<!--    </div>-->
<!--    <div class="form-control">-->
<!--      <label for="confirmPassword">Bevestig wachtwoord</label>-->
<!--      <input type="password" disabled name="confirmPassword" id="confirmPassword">-->
<!--    </div>-->
  </div>
</template>

<script>
export default {
  name: "MyDetails",
  data () {
    return {
      // user: {
      //   firstname: "Yasmine",
      //   lastname: "De Wolf",
      //   email: "yasminedewolf@hotmail.com"
      // }
    };
  },
  computed: {
    user: function () {
      return this.$store.getters.getUser;
    }
  }
}
</script>

<style scoped>

h3 {
  color: var(--secondary-color);
  font-family: 'Quicksand', sans-serif;
  margin-bottom: 2rem;
}


.form-control {
  display: flex;
  flex-direction: column;
  width: 20rem;
}

.form-control input[type="text"], .form-control input[type="password"], .form-control input[type="email"] {
  background-color: transparent;
  color: var(--dark-color);
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
  margin-bottom: 1.5rem;
  padding: 0.5rem 1rem;
  width: 30rem;
  border: none;
  outline: 1px solid lightgrey;
  font-size: medium;

}

.form-control>label {
  margin-bottom: 0.75rem;
  max-width: 30rem;
}

</style>