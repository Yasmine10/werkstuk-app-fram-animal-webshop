<template>
  <div>
    <h1>Checkout</h1>
    <form class="grid-wrapper" @submit.prevent="checkout">
      <div v-if="addresses.length > 0" class="flex-column">
        <div class="address-div" >
          <div class="address-card" v-for="item in addresses" :key="item.id" >
            <label for="address" class="details" style="background-color: blue">
              <input type="radio" id="address">
              <!--            <p class="address-card-user">{{ user.firstname }} {{ user.lastname }}</p>-->
              <p>{{ item.street }} {{ item.houseNr }}</p>
              <p>{{ item.zipcode }}  {{ item.city }}</p>
              <p>{{ item.country }}</p>
            </label>
          </div>
        </div>

        <div class="flex-column">
          <button class="accordion btn" v-on:click="toggleAccordion">Nieuw adres aanmaken</button>
          <div class="panel">
            <div class="address-div">
              <h3 class="title-address">Address</h3>
              <div class="form-control flex-column">
                <label>Straat</label>
                <input type="text" v-model="address.street">
              </div>
              <div class="form-control flex-column">
                <label>Huisnummer</label>
                <input type="text" v-model="address.houseNr">
              </div>
              <div class="form-control flex-column">
                <label>Postcode</label>
                <input type="text" v-model="address.zipcode">
              </div>
              <div class="form-control flex-column">
                <label >Gemeente</label>
                <input type="text" v-model="address.city">
              </div>
              <div class="form-control flex-column">
                <label>Land</label>
                <select v-model="country.selectedCountry">
                  <option selected value="België">België</option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="addresses.length === 0" class="flex-column">
        <div class="address-div">
          <h3 class="title-address">Address</h3>
          <div class="form-control flex-column">
            <label for="street">Straat</label>
            <input type="text" id="street">
          </div>
          <div class="form-control flex-column">
            <label for="housenumber">Huisnummer</label>
            <input type="text" id="housenumber">
          </div>
          <div class="form-control flex-column">
            <label for="zipcode">Postcode</label>
            <input type="text" id="zipcode">
          </div>
          <div class="form-control flex-column">
            <label for="city">Gemeente</label>
            <input type="text" id="city">
          </div>
          <div class="form-control flex-column">
            <label for="country">Land</label>
            <select id="country">
              <option v-for="(country, index) in country.countryData" :key="index" :value="country">{{ country }}</option>
            </select>
          </div>
        </div>
      </div>
      <div class="flex-column ">
        <h3>Levering</h3>
        <div class="delivery-method-div">
          <div class="delivery-method-card">
            <input type="radio" name="delivery" id="standard-delivery" :value="{method: 'Standaard verzending', price: 4.95, time: '3-5'}" ref="standardDelivery" v-model="delivery">
            <label for="standard-delivery" class="delivery-method-label">
              <ph-truck class="delivery-method-icon" :size="64" weight="light" color="#484848"/>
              <section>
                <h4>Standaard verzending</h4>
                <p>3 - 5 werkdagen</p>
                <span>€ 4.95</span>
              </section>
            </label>
          </div>
          <div class="delivery-method-card">
            <input type="radio" name="delivery" id="express-delivery" :value="{method: 'Express verzending', price: 8.95, time: '1-2'}" ref="expressDelivery" v-model="delivery">
            <label for="express-delivery" class="delivery-method-label">
              <ph-truck class="delivery-method-icon" :size="64" weight="light" color="#484848"/>
              <section>
                <h4>Express verzending</h4>
                <p>1 - 2 werkdagen</p>
                <span>€ 8.95</span>
              </section>
            </label>
          </div>
        </div>
        <h3 class="title-payment">Betaling</h3>
        <div class="payment-method-div">
          <div class="payment-method-card">
            <input type="radio" name="payment" id="bancontact" value="bancontact" v-model="payment">
            <label for="bancontact" class="payment-method-label">
              <img :src="bancontactImage" alt="bancontact-logo">
              <h4>Bancontact</h4>
              <p>Free</p>
            </label>
          </div>
          <div class="payment-method-card">
            <input type="radio" name="payment" id="paypal" value="paypal" v-model="payment">
            <label for="paypal" class="payment-method-label">
              <img :src="paypalImage" alt="paypal-logo">
              <h4>Paypal</h4>
              <p>Free</p>
            </label>
          </div>
        </div>
      </div>
      <div class="grid total-wrapper">
        <h3>Overzicht bestelling</h3>
        <div class="products-summary-div">
          <div class="product-item" v-for="(item, index) in cart" :key="index">
            <img :src="makeImagePath(item.item)" :alt="item.item.title" class="product-item-image">
            <div class="product-item-info">
              <p class="product-item-name">{{ item.item.name }}</p>
              <div class="product-price-quantity">
                <p>Aantal: {{ item.quantity}}</p>
                <p>€ {{ item.item.price}}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="subtotal-div">
          <p>Subtotaal</p>
          <p>€ {{ itemsSubtotal }}</p>
        </div>
          <div class="shipping-div">
            <div>
              <p>Verzendkosten</p>
            </div>
            <p>€ {{ deliveryCosts }}</p>
          </div>
        <div class="tax-div">
          <p>BTW ({{ btw * 100 }}%)</p>
          <p>€ {{ btwApplied }}</p>
        </div>
        <div class="total-div">
          <p>Totaal</p>
          <p>€ {{ orderTotal }}</p>
        </div>
        <button type="submit" class="checkout-btn">Doorgaan met bestellen</button>
        <p class="agreedToPolicy">Door mijn bestelling te plaatsen verklaar ik de <a href="">verkoopsvoorwaarden</a> gelezen te hebben en ermee akkoord te gaan, ik begrijp de informatie over het gebruik van mijn persoonlijke gegevens zoals toegelicht in het <a href="">privacybeleid</a> </p>
      </div>
    </form>
  </div>
</template>

<script>
import {imagePath} from "@/mixins/imagePath";
import { PhTruck } from "phosphor-vue";
import BancontactImage from "@/assets/images/payment-methods/bancontact.png";
import PayPalImage from "@/assets/images/payment-methods/paypal.png";

export default {
  name: "Checkout",
  mixins: [imagePath],
  components: {
    PhTruck
  },
  data() {
    return {
      btw: this.$route.params.btw,
      bancontactImage : BancontactImage,
      paypalImage: PayPalImage,
      delivery: '',
      payment: '',
      address: {
        street: '',
        houseNr: '',
        zipcode: '',
        city: '',
        country: '',
        user: {
          id: ''
        }
      },
      country: {
        countryData: [],
        selectedCountry: ''
      },
      createdAddress: {},
      deliveryMethod: '',
      paymentMethod: '',
      order: {}
    };
  },
  computed: {
    user: function () {
      return this.$store.getters.getUser;
    },
    addresses: function () {
      return this.$store.getters.getAddresses;
    },
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
    deliveryCosts: function () {
      if(this.delivery !== null)
        return this.delivery.price;
      else
        return (0.0).toFixed(2);
    },
    deliveryMethodSet: function () {
      if(this.delivery !== null) {
        return this.delivery.method;
      } else {
        return null;
      }
    },
    paymentMethodSet: function () {
      if(this.payment !== null) {
        console.log(this.payment)
        return this.payment;
      } else {
        return null;
      }
    },
    orderTotal: function () {
      if(this.delivery !== null) {
        return (parseFloat(this.itemsSubtotal) + this.delivery.price).toFixed(2);
      } else {
        return (parseFloat(this.itemsSubtotal)).toFixed(2);
      }
    },
  },
  mounted() {
    let countries = ["België"];
    this.country.countryData = Object.assign({}, this.country.countryData, countries);
    this.country.selectedCountry = this.country.countryData[0];
  },
  watch: {
    delivery(value) {
      console.log(value)
      this.delivery = value;
    },
    payment(value) {
      console.log(value);
      this.payment = value;
    }
  },
  methods: {
    toggleAccordion: function () {
      let acc = document.getElementsByClassName("accordion");
      let i;

      for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function() {
          this.classList.toggle("active");
          let panel = this.nextElementSibling;
          if (panel.style.display === "block") {
            panel.style.display = "none";
          } else {
            panel.style.display = "block";
          }
        });
      }
    },
    checkout: function (){
      if(this.addresses.length === 0) {

        this.address.user.id = this.user.id;
        this.address.country = this.country.selectedCountry;

        this.$store.dispatch('addAddress', {address: this.address})
          .then((resp) => this.createdAddress = resp.data )
          .catch(err => console.log(err));

      } else {
        // selected address from addresses
      }
      let totalprice = this.orderTotal;
      let shippingcost = this.delivery.price
      let payment = this.payment
      let delivery = this.delivery

      let order = {
        totalPrice: totalprice,
        shippingPrice: shippingcost,
        shippingMethod: delivery,
        paymentMethod: payment,
        user: {
          id: this.user.id
        }
      }

      console.log("order in checkout view: " + order.paymentMethod);

      this.$store.dispatch('addOrder', order)
        .then((resp) => this.order = resp.data)
        .catch(err => console.log(err));

      let createdOrder = this.order;
      console.log(createdOrder);

      // this.cart.forEach(item => {
      //   this.$store.dispatch('addOrderItem', {orderitem: item.item, subtotal: (item.item.price * item.quantity), orderId: this.order.id})
      //     .then((resp) => console.log(resp.data))
      //     .catch(err => console.log(err));
      // })



    }
  }
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.accordion {
  /*color: #444;*/
  cursor: pointer;
  padding: 18px;
  border: none;
  text-align: left;
  outline: none;
  font-size: 15px;
  transition: 0.4s;
}

.active, .accordion:hover {
  background-color: #ccc;
}

.panel {
  padding: 0 18px;
  display: none;
  /*background-color: white;*/
  overflow: hidden;
}


.flex-column {
  display: flex;
  flex-direction: column;
  margin-right: 1.5rem;
}

.flex {
  display: flex;
}

.grid-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;

}

h3 {
  text-transform: uppercase;
  color: var(--primary-color);
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

.delivery-method-div, .payment-method-div {
  /*margin-top: 0.5rem;*/
  margin-bottom: 2rem;
}

.delivery-method-div {
  /*margin-bottom: 3rem;*/
  /*display: grid;*/
  /*grid-template-rows: 1fr 1fr;*/
  /*grid-gap: 0.5rem;*/
}

.delivery-method-label {
  display: grid;
  grid-template-columns: 1fr 2fr;
  align-items: center;
  border: 1px solid lightgrey;
  padding: 1rem 2rem;
}

.delivery-method-label section h3{
  margin-bottom: 0.25rem;
}

.delivery-method-label section p {
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  font-size: smaller;
}

.delivery-method-label section span {
  font-weight: bold;
  font-size: large;
}

.delivery-method-icon {
  margin-right: 1rem;
}

input[type='radio'] {
  -webkit-appearance: none;
}

input[type='radio']:checked + label {
  outline: 2px solid var(--primary-color);
}

.payment-method-label {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  align-items: center;
  grid-gap: 1rem;
  border: 1px solid lightgrey;
  padding: 1rem 2rem;
}

.payment-method-card img {
  height: 48px;
}

.payment-method-card h4 {
  margin-left: 1rem;
  text-transform: initial;
  color: var(--dark-color)
}

.payment-method-card p {
  text-transform: uppercase;
  justify-self: flex-end;
}



.total-wrapper {
  background-color: var(--light-color);
  width: 30%;
  /*margin-top: 2rem;*/
  padding: 1.5rem;
}

.total-wrapper h3 {
  text-transform: uppercase;
  font-weight: bolder;
  color: var(--dark-color);
  border-bottom: 2px solid darkgray;
  padding-bottom: 1rem;
}

.products-summary-div {
  border-bottom: 2px solid darkgray;
  margin-top: 1.5rem;
  /*padding-bottom: 1rem;*/
}

.product-item {
  display: grid;
  grid-template-columns: 1fr 4fr;
  align-items: center;
  grid-gap: 1rem;
  margin-bottom: 1.5rem;
}

.product-item:not(:first-child) {
  /*border-top: 1px solid darkgray;*/
  margin-top: 1.5rem;
}

.product-item-image {
  width: 70px;
  height: 70px;
  margin-right: 1rem;
}

.product-item-name {
  font-weight: bold;
  font-size: medium;
  /*text-transform: uppercase;*/
  color: var(--dark-color);
}

.product-price-quantity {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1rem;
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
  border-top: 2px solid darkgray;
}

.agreedToPolicy {
  margin-top: 2rem;
  margin-right: 1rem;
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