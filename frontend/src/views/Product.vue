<template>
  <div>
    <a class="go-back-link" @click="goBack"><ph-caret-left :size="20" color="#000000" weight="thin" /> Terug naar overzicht</a>
    <div class="product-detail-wrapper">
      <div class="product-img-wrapper">
        <img :src="makeImagePath(product)" :alt="product.title">
      </div>
      <div class="product-data-wrapper">
        <h1 class="product-title">{{ product.name }}</h1>
        <p class="product-brand">{{ product.brand }}</p>
        <h2 class="product-price">€ {{ product.price.toFixed(2) }} <small style="font-size: small">(incl. BTW)</small></h2>
        <div class="in-stock-text">
          <ph-check-circle :size="20" color="#2FBD89" weight="bold" />
          <p>In stock</p>
        </div>
        <div class="buy-product-div">
          <select v-model="quantityData.selectedQuantity" class="select-quantity-product">
            <option v-for="(number, index) in quantityData.quantity" :key="index" :value="number">{{ number }}</option>
          </select>
          <button class="add-to-cart-button" type="submit" @click="addToCart">
            <ph-shopping-cart class="add-to-cart-icon" :size="28" weight="regular" /> In winkelmandje
          </button>
        </div>
        <div class="advantages-text">
          <div>
            <ph-check :size="20" color="#26b543" weight="bold" />
            <p>Levertijd: 1-3 werkdagen</p>
          </div>
<!--          <div>-->
<!--            <ph-check :size="20" color="#26b543" weight="bold" />-->
<!--            <p>Geen verzendkosten vanaf €50,-</p>-->
<!--          </div>-->
          <div>
            <ph-check :size="20" color="#26b543" weight="bold" />
            <p>Gratis retourneren binnen de 14 dagen!</p>
          </div>
        </div>
      </div>
    </div>
    <div class="product-description-wrapper">
      <h3>Beschrijving</h3>
      <div class="border-under-description-title"></div>
      <p>{{ product.description }}</p>
    </div>
    <div class="category-wrapper">
      <h5>Categorie</h5>
      <p>{{ product.category.name }}</p>
    </div>
    <div class="animal-wrapper">
      <h5>Dier</h5>
      <p>{{ product.animal.name }}</p>
    </div>
  </div>
</template>

<script>
import { imagePath } from '@/mixins/imagePath.js';
import { PhCheckCircle, PhCheck, PhShoppingCart, PhCaretLeft } from "phosphor-vue";

export default {
  name: "Product",
  mixins: [imagePath],
  components: {
    PhCheckCircle,
    PhCheck,
    PhShoppingCart,
    PhCaretLeft
  },
  data () {
    return {
      product: this.$store.getters.getProduct(this.$route.params.id),
      quantityData: {
        quantity: [],
        selectedQuantity: ''
      }
    }
  },
  mounted() {
    let quantity = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    this.quantityData.quantity = Object.assign({}, this.quantityData.quantity, quantity);
    this.quantityData.selectedQuantity = this.quantityData.quantity[0];
  },
  methods: {
    addToCart() {
      this.$store.dispatch('addToCart', {item: this.product, quantity: this.quantityData.selectedQuantity});
    },
    goBack() {
      this.$router.back();
    }
  }
}
</script>

<style scoped>

@import '../assets/styles/stylesheet.css';

.go-back-link {
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  cursor: pointer;
}

.product-detail-wrapper {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.product-img-wrapper {

}

.product-img-wrapper img {
  width: 60%;
  height: 100%;
  padding: 2rem;
  border: 1px solid var(--light-color);
}

.product-data-wrapper {
  margin-left: 2rem;
}

.product-title {
  text-transform: uppercase;
}

.product-brand {
  margin-top: -0.75rem;
}

.product-price {
  margin-top: 2rem;
  margin-bottom: 0.5rem;
  color: var(--dark-color);
}

.in-stock-text, .advantages-text div {
  display: flex;
}

.in-stock-text p {
  margin-left: 0.5rem;
  margin-bottom: 2rem;
}

.buy-product-div {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 4rem;
}

.select-quantity-product {
  padding: 0.5rem 1rem;
  font-size: large;
  font-weight: bold;
  background-color: var(--light-color);
  border-radius: 5px;
  border-color: dimgrey;
}

.select-quantity-product:focus {
  /*border: none;*/
  outline: none;
  /*border: 2px solid var(--accent-color);*/
}

.select-quantity-product option {

}

.select-quantity-product option::selection {
  background-color: var(--primary-color);
}

.advantages-text div p {
  margin-left: 1rem;
  margin-bottom: 1.5rem;
}

.add-to-cart-button {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: var(--secondary-color);
  color: var(--white-color);
  border: transparent;
  cursor: pointer;
  border-radius: 5px;
  /*margin-bottom: 4rem;*/
  margin-left: 1.5rem;
  text-transform: uppercase;
  font-size: medium;
  font-weight: bold;
}

.add-to-cart-button:hover {
  opacity: 80%;
}

.add-to-cart-button:focus {
  outline: none;
}

.add-to-cart-icon {
  margin-right: 1rem;
}

.product-description-wrapper {
  margin-top: 3rem;
  margin-bottom: 2rem;
  border-top: 2px solid var(--light-color);
}

.product-description-wrapper h3 {
  text-transform: uppercase;
  color: var(--dark-color);
  margin-bottom: 1rem;
  margin-top: 2rem;
}

.category-wrapper, .animal-wrapper {
  display: grid;
  grid-template-columns: 1fr 14fr;
  grid-column-start: auto;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 1rem;
}

.category-wrapper h5, .animal-wrapper h5 {
  text-transform: uppercase;
  color: var(--dark-color);
}

.category-wrapper p, .animal-wrapper p {
  font-size: small;
}

</style>