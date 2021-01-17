<template>
  <div class="register">
    <form class="register-form" @submit.prevent="register">
      <h1>Account aanmaken</h1>
      <div class="form-control">
        <label for="firstname">Voornaam</label>
        <input type="text" required v-model="user.firstname" name="firstname" id="firstname">
      </div>
      <div class="form-control">
        <label for="lastname">Achternaam</label>
        <input type="text" required v-model="user.lastname" name="lastname" id="lastname">
      </div>
      <div class="form-control">
        <label for="emailAddress">E-mailadres</label>
        <input type="email" required v-model="user.email" name="emailAddress" id="emailAddress" >
      </div>
      <div class="form-control">
        <label for="password">Wachtwoord</label>
        <input type="password" required v-model="user.password" name="password" id="password">
      </div>
      <div class="form-control">
        <label for="confirmPassword">Bevestig wachtwoord</label>
        <input type="password" required v-model="user.confirmPassword" name="confirmPassword" id="confirmPassword">
      </div>
<!--      <div class="checkbox-newsletter-div">-->
<!--        <label for="checkNewsletter">-->
<!--          <input type="checkbox" name="checkNewsletter" id="checkNewsletter">-->
<!--          <span class="checkbox-text">Ik wil graag wekelijks een nieuwsbrief ontvangen met de laatste nieuwe producten en promoties.</span>-->
<!--        </label>-->
<!--      </div>-->
      <button type="submit" class="register-btn btn">Registreren</button>
    </form>
  </div>
</template>

<script>

import bcrypt from 'bcryptjs'

export default {
  name: "Register",
  data() {
    return {
      user: {
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        confirmPassword: ''
      }
    };
  },
  computed: {

  },
  methods: {
    register: function () {

      let hashedPassword = this.encryptPassword(this.user.password);

      let data = {
        firstname: this.user.firstname,
        lastname: this.user.lastname,
        email: this.user.email,
        password: hashedPassword
      }

      this.$store.dispatch('register', data)
        .then(() => this.$router.push('/login'))
        .catch(err => console.log(err));
    },
    encryptPassword: function (password) {
      const salt = bcrypt.genSaltSync(10);
      return bcrypt.hashSync(password, salt);
    }
  }
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.register {
  display: flex;
  justify-content: center;
  align-content: center;
}

.register-form h1 {
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
  margin-bottom: 1rem;
  padding: 0.5rem 1rem;
  width: 20rem;
  border: none;
  outline: 1px solid lightgrey;
}

.form-control>label {
  margin-bottom: 0.5rem;
  max-width: 20rem;
}

.checkbox-newsletter-div {
  width: 20rem;
  display: block;
  position: relative;
  left: 2rem;
  margin-top: 1.5rem;
}

input[type="checkbox"] {
  margin-top: 1.5rem;
  /*margin-right: 1rem;*/
  padding: 0.25rem;
  position: absolute;
  left: -2rem;
  top: -1.25rem;
}

.checkbox-newsletter-div .checkbox-text {
  word-break: normal;
}

.register-btn {
  display: flex;
  justify-self: center;
  align-self: center;
}

</style>