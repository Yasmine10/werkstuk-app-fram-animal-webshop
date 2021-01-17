<template>
	<div>
		<h1>Winkelmandje</h1>
		<div v-if="cart.length === 0" class="no-items-div">
			<h3>Je hebt nog geen items in je winkelmandje.</h3>
		</div>
		<div class="cart-wrapper" v-else>
			<section class="cart-items-wrapper">
				<ul>
					<li class="cart-item header-cart-items">
						<p></p>
						<p style="margin-left: 2rem">Product</p>
						<p>Prijs</p>
						<p>Stuks</p>
						<p></p>
					</li>
					<li v-for="(item, index) in cart" :key="index" class="cart-item-wrapper">
						<a class="cart-item">
							<img :src="makeImagePath(item.item)" :alt="item.item.name" class="cart-item-image">
							<div class="cart-item-text">
								<h4 class="cart-item-title">{{ item.item.name }}</h4>
								<p class="cart-item-brand">{{ item.item.brand }}</p>
							</div>
							<p class="cart-item-price">€ {{ item.item.price.toFixed(2) }}</p>
							<p style="justify-self: flex-end; margin-right: 4.5rem">{{item.quantity}}</p>
							<a class="remove-cart-item-btn" @click="removeFromCart(item.item.id)"><ph-x :size="20" color="#484848" weight="fill" /></a>
						</a>
					</li>
				</ul>
			</section>
			<section class="total-wrapper">
				<h3>{{cartItemsCount}} product(en)</h3>
				<div class="subtotal-div">
					<p>Subtotaal</p>
					<p>€ {{ itemsSubtotal }}</p>
				</div>
<!--				<div class="shipping-div">-->
<!--					<div>-->
<!--						<p>Verzendkosten</p>-->
<!--						<small style="color: dimgrey; font-size: 11px">(Wordt in de volgende stap gekozen)</small>-->
<!--					</div>-->
<!--					<p>---</p>-->
<!--				</div>-->
				<div class="tax-div">
					<p>BTW ({{ btwPercentage }})</p>
					<p>€ {{ btwApplied }}</p>
				</div>
				<div class="total-div">
					<p>Totaal</p>
					<p>€ {{ itemsSubtotal }}</p>
				</div>

				<router-link tag="button" :to="{name: 'Checkout', params: { btw: btw }}" class="checkout-btn">Doorgaan met bestellen</router-link>
			</section>
		</div>
	</div>
</template>

<script>
import { imagePath } from '@/mixins/imagePath';
import { PhX } from "phosphor-vue";

export default {
	name: "Cart",
	mixins: [imagePath],
	components: {
		PhX
	},
	data() {
		return {
			btw: 0.06,
		};
	},
	computed: {
		cart: function () {
			return this.$store.state.cart;
		},
		// cartItems: function () {
		// 	return this.$store.getters.getCartItems;
		// },
		cartItemsCount: function () {
			return this.$store.getters.getCartItemsCount;
		},
		itemsSubtotal: function () {
      return this.$store.getters.getCartItemsSubtotal;
		},
		btwPercentage() {
			return `${this.btw * 100}%`;
		},
		btwApplied() {
			return (this.itemsSubtotal * this.btw).toFixed(2);
		},
	},
	methods: {
		removeFromCart(itemId) {
			this.$store.dispatch('removeFromCart', itemId);
		}
	}
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.no-items-div {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100%;
	margin: 11% 25%;
}

.cart-wrapper {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
}

.cart-items-wrapper {
	width: 70%;
	margin-top: 2rem;
	margin-right: 3rem;
}

.header-cart-items {
	/*background-color: #F2F2F2;*/
	font-weight: bold;
	font-family: 'Raleway', sans-serif;
}

.cart-item-wrapper {
	list-style: none;
}

.cart-item {
	list-style: none;
	display: grid;
	grid-template-columns: 1fr 5fr 1fr 1fr 1fr;
	align-items: center;
	border-bottom: 1px solid var(--light-color);
	/*margin-top: 1.5rem;*/
	padding-top: 1rem;
	padding-bottom: 1rem;
	/*transition: transform 500ms;*/
	text-decoration: none;
	color: var(--dark-color);
}

.cart-item:first-child {
	border-top: 1px solid var(--light-color);
}

.cart-item:hover {
	/*transform: scale(1.05);*/
}

.cart-item-image {
	width: 60%;
	border: 1px solid var(--light-color);
	padding: 0.5rem;
	margin-left: 1.5rem;
}

.cart-item-text {
	margin-left: 2rem;
	margin-right: 2rem;
}

.remove-cart-item-btn {
	border: none;
	background-color: transparent;
}

.total-wrapper {
	background-color: var(--light-color);
	width: 30%;
	margin-top: 2rem;
	padding: 1.5rem;
}

.total-wrapper h3 {
	
	border-bottom: 1px solid lightgrey;
	padding-bottom: 1rem;
}

.subtotal-div, .tax-div, .shipping-div {
	margin-top: 1rem;
	margin-bottom: 1rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.total-div {
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-weight: bold;
	text-transform: uppercase;
	font-size: larger;
	margin-top: 1.5rem;
	padding-top: 1rem;
	border-top: 1px solid lightgrey;
}

.checkout-btn {
	margin-top: 2rem;
	width: 100%;
	padding: 0.5rem 1rem;
	background-color: var(--secondary-color);
	color: var(--white-color);
	border: transparent;
	cursor: pointer;
	border-radius: 5px;
	text-transform: uppercase;
	font-size: 14px;
}

</style>