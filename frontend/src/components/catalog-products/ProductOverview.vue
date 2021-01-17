<template>
    <div class="products-overview-wrapper">
      <div class="results-p">
        <p>{{this.products.length}} result(s)</p>
      </div>
      <div class="products">
        <div class="product-card" v-for="product in products" :key="product.id">
<!--          <router-link :to="{name: `ProductDetail${animal.name}`, params: {productId: product.id}}" >-->
          <div class="img-wrapper">
            <img :src="imagePath + product.image" :alt="product.title" >
          </div>
          <h4>{{ product.name }}</h4>
          <p>{{ product.brand }}</p>
          <div class="buy-wrapper">
            <h3>€ {{product.price}}</h3>
            <form action="" method="post">
              <select>
                <option v-for="(number, index) in amount" :key="index" :value="number">{{ number }}</option>
              </select>
              <button class="add-to-cart-button" type="submit"><ph-shopping-cart :size="28" weight="thin" /> In winkelmandje</button>
            </form>
          </div>
<!--          </router-link>-->
        </div>
      </div>
    </div>
</template>

<script>
import { PhShoppingCart } from "phosphor-vue";

export default {
  name: "ProductOverview",
  props: ['animalId'],
  components: {
    PhShoppingCart
  },
  data: () => ({
    products: [],
    imagePath: "@/assets/products/",
    amount: [1,2,3,4,5,6,7,8,9,10],
  }),
  created: function() {
    this.fetchProductData();
  },
  methods: {
    fetchProductData: function () {
      fetch(`http://localhost:8080/api/product/animal=${this.animalId}`, {
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
    }
  }
}
</script>

<style scoped>
.products-overview-wrapper {
  margin-right: 1rem;
}

.results-p {

  margin-bottom: 0.75rem;
}

.products {
  display: flex;
  justify-content: space-evenly;
  align-items: center
}

.product-card {
  border: 2px solid var(--light-color);
  margin-top: 1rem;
  margin-bottom: 1rem;
  padding: 1rem;
  font-family: 'Quicksand', sans-serif;
  margin-right: 1rem;
  transition: transform 500ms;
}

.product-card:hover {
  transform: scale(1.1);
}

.img-wrapper {
  width: 250px;
  height: 250px;
  background-color: var(--light-color);
}

h4 {
  font-family: 'Raleway', sans-serif;
  margin-top: 1rem;
  margin-bottom: 0.25rem;
  overflow: auto;
}

</style>