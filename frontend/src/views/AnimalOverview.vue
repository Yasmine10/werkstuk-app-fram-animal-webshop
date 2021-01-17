<template>
  <div>
    <div class="wrapper">
      <div>
        <ul class="categories-list">
          <li class="category-item">
            <p @click="fetchProductData()">Alle Producten</p>
          </li>
          <li v-for="category in categories" :key="category.id" class="category-item">
            <p @click="filterProductsByCategory(category.id)">{{ category.name }}</p>
          </li>
        </ul>
      </div>
      <div>
        <h1>{{ pageTitle }}</h1>
        <p>{{products.length}} result(s)</p>
        <ul class="products-list">
          <li v-for="product in products" :key="product.id" class="product-item">
            <router-link tag="li" :to="{name: 'Product', params: {id: product.id}}">
              <img class="product-image" :src="makeImagePath(product)" alt="">
              <h4 class="product-title">{{ product.name }}</h4>
              <p class="product-brand">{{ product.brand }}</p>
              <p class="product-price"><span>€ {{ product.price }}</span></p>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { imagePath } from '@/mixins/imagePath.js'

export default {
  name: "AnimalOverview",
  mixins: [imagePath],
  data() {
    return {
      products: [this.productsAnimal],
      categories: []
    };
  },
  computed: {
    animal() {
      return this.$route.params.animal;
    },
    pageTitle() {
      return `${this.animal[0].toUpperCase()}${this.animal.slice(1)}`;
    },
    productsAnimal() {
      return this.$store.getters.getProductsByAnimal(this.animal);
    }
  },
  mounted() {
    this.fetchProductData();
    this.fetchAllCategories();
    this.fetchProductsByCategory();
  },
  methods: {
    fetchAllCategories: function() {
      fetch(`http://localhost:8080/api/animal/${this.pageTitle}/categories`, {
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
      fetch(`http://localhost:8080/api/product/animal=${this.pageTitle}`, {
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

@import '../assets/styles/stylesheet.css';

h1 {
  text-transform: uppercase;
}

.wrapper {
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  margin-top: 1.5rem;
}

.categories-list {
  border: 1px solid var(--light-beige-color);
  padding: 1rem;
  margin-right: 2rem;
  width: 300px;
}

.category-item {
  list-style: none;
  margin-bottom: 1rem;
  font-size: larger;
}

.products-list {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  margin-top: 1.5rem;
}

.product-item {
  list-style: none;
  /*margin: 1rem;*/
  border: 1px solid var(--light-color);
  padding: 1.5rem;
  transition: transform 500ms;
}

.product-item:not(first-child) {
  margin-right: 1rem;
}

.product-item:hover {
  transform: scale(1.05);
}

.product-image {
  max-width: 100%;
  display: flex;
  justify-content: center;
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