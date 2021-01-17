<template>
	<div class="header-wrapper">
		<router-link tag="li" to="/" class="logo-wrapper">
			<img src="../assets/animal-paw-print-dark.svg" alt="logo">
			<h1>Dierenwinkel</h1>
		</router-link>
		<div class="button-wrapper">
      <router-link tag="li" to="/profiel/gegevens" v-if="isLoggedIn === true">
        <ph-user :size="28" weight="regular" color="#484848" />
        <p class="">Account</p>
      </router-link>
      <router-link tag="li" to="/login" v-else>
        <ph-user :size="28" weight="regular" color="#484848" />
        <p class="login-text">Login</p>
      </router-link>
			<router-link tag="li" to="/cart" class="cart-button">
				<ph-shopping-cart :size="28" weight="regular" color="#484848"/>
				<CounterBadge :count="cartCount"></CounterBadge>
<!--				<p>€ {{ cartTotalPrice }}</p>-->
			</router-link>
		</div>
	</div>
</template>

<script>
import { PhShoppingCart, PhUser } from "phosphor-vue";
import CounterBadge from "@/components/CounterBadge";

export default {
	name: "Header",
	components: {
		CounterBadge,
		PhShoppingCart,
		PhUser
	},
	computed: {
		cartCount() {
			return this.$store.getters.getCartItemsCount;
		},
		user() {
			return this.$store.getters.getUser;
		},
		fullname() {
			return this.user.firstname;
		},
    isLoggedIn()
    {
      return this.$store.getters.isLoggedIn;
    }
	},
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.header-wrapper {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 10%;
}

.logo-wrapper {
	display: flex;
	justify-content: space-evenly;
	align-items: center;
}

.logo-wrapper img {
	height: 50px;
	margin-right: 1rem;
}

.logo-wrapper h1 {
	color: var(--dark-color);
	padding-top: 2.25rem;
	margin-left: 0.75rem;
}

.button-wrapper {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.button-wrapper li {
	list-style: none;
	display: flex;
	/*justify-content: space-evenly;*/
	align-items: center;
}

.cart-button {
	position: relative;
	margin-left: 1.5rem;
}

.login-text {
	/*margin-top: 0.25rem;*/
	margin-left: 0.5rem;
}

</style>