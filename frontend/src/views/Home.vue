<template>
  <div class="home">
    <section class="wrapper">
      {{ token }}
      {{ user.firstname }}
      <h1>In de kijker!</h1>
      <ul class="products-list">
        <li v-for="product in getRandomProducts()" :key="product.id" class="product-item">
          <router-link tag="li" :to="{name: 'Product', params: {id: product.id}}">
            <img class="product-image" :src="makeImagePath(product)" alt="">
            <div class="product-container">
              <h4 class="product-title">{{ product.name }}</h4>
              <p class="product-brand">{{ product.brand }}</p>
              <p class="product-price"><span>€ {{ product.price }}</span></p>
            </div>
          </router-link>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
// @ is an alias to /src
import { imagePath } from '@/mixins/imagePath.js'

export default {
  name: 'Home',
  components: {
  },
  mixins: [imagePath],
  data() {
    return {
      randomProducts: []
    };
  },
  computed: {
    gettersProducts: function () {
      return this.$store.getters.getAllProducts;
    },
    products: function () {
      return this.$store.state.products;
    },
    token: function () {
      return this.$store.getters.isLoggedIn;
    },
    user: function () {
      console.log(this.$store.getters.hasUser);
      console.log(this.$store.state.user);
      return this.$store.getters.getUser;
    }
  },
  mounted() {
    this.$store.dispatch("loadProducts");
  },
  methods: {
    getRandomProducts() {
      for(let i = this.products.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [this.products[i], this.products[j]] = [this.products[j], this.products[i]];
      }
      return this.products.slice(0,4);
    }
  }
}
</script>

<style scoped lang="css">

@import '../assets/styles/stylesheet.css';

.wrapper h1 {
  text-transform: uppercase;
  text-align: center;
}

.products-list {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  justify-content: space-around;
  margin-top: 1.5rem;
}

.product-item {
  list-style: none;
  /*margin: 1rem;*/
  border: 1px solid var(--light-color);
  padding: 1.5rem;
  transition: transform 500ms;
  display: flex;
  justify-content: center;
}

.product-item:not(first-child) {
  margin-right: 1rem;
}

.product-item:hover {
  transform: scale(1.05);
}

.product-image {
  max-height: 250px;
  display: flex;
  justify-content: space-evenly;
  align-content: space-evenly;
  margin-bottom: 2rem;
}

.product-title {
  font-family: 'Raleway', sans-serif;
  margin-top: 1rem;
  margin-bottom: 0.25rem;
}

.product-brand {
  color: dimgrey;
  margin-bottom: 1rem;
}

</style>
