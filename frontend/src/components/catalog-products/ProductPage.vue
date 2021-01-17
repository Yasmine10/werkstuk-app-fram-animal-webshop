<template>
  <div>
    <div class="sidebar">
      <p v-on:click="fetchProductData()">Alle producten</p>
      <div v-for="(category, index) in categories" :key="index">
        <p v-on:click="fetchProductsByCategory(category.id)">{{ category.name }}</p>
      </div>
    </div>
    <div class="products">
      <h1>{{ animalName }}</h1>
      <div class="results-p">
        <p>{{this.products.length}} result(s)</p>
      </div>
      <div class="product-card" v-for="product in products" :key="product.id">
        <router-link tag="li" :to="{name: 'Product', params: {id: product.id}}">
        <div class="img-wrapper">
          <img :src="imagePath + product.image" :alt="product.title" >
        </div>
        <h4>{{ product.name }}</h4>
        <p>{{ product.brand }}</p>
        <div class="buy-wrapper">
          <h3>€ {{product.price}}</h3>
          <form action="" method="post">
            <label>
              <select>
                <option v-for="(number, index) in amount" :key="index" :value="number">{{ number }}</option>
              </select>
            </label>
            <button class="add-to-cart-button" type="submit"><ph-shopping-cart :size="28" weight="thin" /> In winkelmandje</button>
          </form>
        </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { PhShoppingCart } from 'phosphor-vue';

export default {
  name: "ProductPage",
  props: ['animalId'],
  components: {
    PhShoppingCart
  },
  data: () => ({
    categories: [],
    products: [],
    animal: {},
    amount: [1,2,3,4,5,6,7,8,9,10],
    imagePath: "@/assets/products/",
    animalName: "Hond"
  }),
  created: function() {
    this.fetchAllCategories();
    this.fetchProductData();

  },
  methods: {
    fetchAllCategories: function() {
      fetch(`http://localhost:8080/api/animal/${this.animalName}/categories`, {
        method: "GET",
        headers: {
          'accept' : 'application/json'
        }
      })
        .then(resp => {
          if(resp.ok)
            return resp.json();
          console.log("error: ", resp);
        }) // convert json to object
        .then(json => { // do something with data
          console.log("categories: ", json);
          this.categories = json;
        })
        .catch(er => {
          console.log("Error; ", er);
        });
    },
    fetchProductData: function () {
      fetch(`http://localhost:8080/api/product/animal=${this.animalName}`, {
        method: "GET",
        headers: {
          'accept' : 'application/json'
        }
      })
        .then(resp => {
          if(resp.ok)
            return resp.json();
          console.log("error: ", resp);
        }) // convert json to object
        .then(json => { // do something with data
          console.log("products: ", json);
          this.products = json;
        })
        .catch(er => {
          console.log("Error; ", er);
        });
    },
    fetchProductsByCategory: function (id) {
      fetch(`http://localhost:8080/api/product/category=${id}`, {
        method: "GET",
        headers: {
          'accept' : 'application/json'
        }
      })
        .then(resp => {
          if(resp.ok)
            return resp.json();
          console.log("error: ", resp);
        }) // convert json to object
        .then(json => { // do something with data
          console.log("products: ", json);
          this.products = json;
        })
        .catch(er => {
          console.log("Error; ", er);
        });
    },

  }
}
</script>

<style scoped>

.sidebar {
  border: 1px solid var(--light-beige-color);
  padding: 1rem;
  width: 100%;
}

p {
  margin-bottom: 1rem;
  font-size: larger;
}

</style>