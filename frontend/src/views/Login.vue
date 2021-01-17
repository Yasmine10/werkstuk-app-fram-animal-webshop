<template>
	<div>
		<div class="wrapper">
			<div class="login-wrapper">
				<h1>Welkom terug!</h1>
				<form class="login-form" @submit.prevent="login">
					<div class="form-control">
						<label for="emailAddress">E-mailadres</label>
						<input type="email" required v-model="user.email" name="emailAddress" id="emailAddress">
					</div>
					<div class="form-control">
						<label for="password">Wachtwoord</label>
						<input type="password" required v-model="user.password" name="password" id="password">
					</div>
					<button type="submit" class="login-btn btn">Inloggen</button>
				</form>
			</div>
			<div class="border"></div>
			<div class="register-wrapper">
				<h1>Nog niet geregistreerd?</h1>
				<p>Heb je nog geen account? Registreer je dan hier en ontdek alle voordelen.</p>
				<div class="advantages-div">
					<div class="flex-div">
						<ph-check :size="20" color="#26b543" weight="bold" />
						<p>Sneller afrekenen</p>
					</div>
					<div class="flex-div">
						<ph-check :size="20" color="#26b543" weight="bold" />
						<p>Bekijken van je geplaatste bestellingen</p>
					</div>
					<div class="flex-div">
						<ph-check :size="20" color="#26b543" weight="bold" />
						<p>Retourneren binnen 14 dagen!</p>
					</div>
				</div>
				<router-link tag="button" :to="{name: 'Register'}" type="submit" class="register-btn btn">Registreren</router-link>
			</div>
		</div>
	</div>
</template>

<script>
import { PhCheck } from "phosphor-vue";

export default {
	name: "Login",
	components: {
		PhCheck,
	},
	data() {
		return {
			user: {
				email: '',
				password: ''
			},
		};
	},
	computed: {
		userData() {
			return this.$store.getters.getUser;
		}
	},
	methods: {
		login: function () {
			let email = this.user.email;
			let password = this.user.password;
			this.$store.dispatch('login', {email, password})
        .then(() => this.$router.push('/'))
        .catch(err => console.log(err));
		},
	}
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.wrapper {
	display: flex;
	justify-content: space-around;
	/*align-items: center;*/
	margin-top: 6rem;
	margin-bottom: 8rem;
}

.border {
	border-right: 2px solid lightgray;
	margin: 0 -10rem;
}

.login-form {
	/*margin-right: 2rem;*/
	
}

.form-control {
	display: flex;
	flex-direction: column;
	width: 20rem;
}

.form-control input {
	/*padding: 0.5rem 1rem;*/
	/*margin-top: 0.5rem;*/
	margin-bottom: 1rem;
	/*border: 2px solid grey;*/
	/*border-radius: 5px;*/

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

.form-control input:focus {

	outline: 1px solid var(--primary-color);
}

.form-control>label {
  margin-bottom: 0.5rem;
}

.login-btn {
}

.advantages-div {
	margin-top: 2.5rem;
}

.flex-div {
	display: flex;
	justify-content: flex-start;
	align-items: center;
	margin: 1.25rem 0;
}

.flex-div p {
	margin-left: 1rem;
}

</style>