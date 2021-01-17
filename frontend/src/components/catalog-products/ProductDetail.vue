<template>
  <div>
    <a >
      <ph-caret-left :size="20" color="#000000" weight="thin" />
      <p>Terug naar overzicht</p>
    </a>
    <div class="product-detail-wrapper">
      <div class="product-img-wrapper">
        <img :src="`./product-images/${product.image}`" :alt="product.title">
        {{product.image}}
      </div>
      <div class="product-data-wrapper">
        <h1>{{ product.name }}</h1>
        <small>{{ product.brand }}</small>
        <h2>€ {{ product.price }}</h2>
        <div class="in-stock-text">
          <ph-check-circle :size="20" color="#2FBD89" weight="bold" />
          <p>In stock</p>
        </div>
        <form action="" method="post">
          <select>
            <option v-for="(number, index) in amount" :key="index" :value="number">{{ number }}</option>
          </select>
          <button class="add-to-cart-button" type="submit"><ph-shopping-cart :size="28" weight="thin" /> In winkelmandje</button>
        </form>
        <div class="advantages-text">
          <div>
            <ph-check :size="20" color="#26b543" weight="bold" />
            <p>Levertijd: 1-3 werkdagen</p>
          </div>
          <div>
            <ph-check :size="20" color="#26b543" weight="bold" />
            <p>Geen verzendkosten vanaf €50,-</p>
          </div>
          <div>
            <ph-check :size="20" color="#26b543" weight="bold" />
            <p>Gratis retourneren binnen de 14 dagen!</p>
          </div>
        </div>
      </div>
    </div>
    <div class="product-description-wrapper">
      <h3>Beschrijving</h3>
      <p>{{ product.description }}</p>
    </div>
  </div>
</template>

<script>
import { PhCheckCircle, PhCheck, PhShoppingCart, PhCaretLeft } from "phosphor-vue";

export default {
  name: "ProductDetail",
  components: {
    PhCheckCircle,
    PhCheck,
    PhShoppingCart,
    PhCaretLeft
  },
  data() {
    return {
      product: {},
      productId: this.$route.params.productId,
      amount: [1,2,3,4,5,6,7,8,9,10],
      imagePath: "@/assets/products/"
    }
  },
  created: function (){
    this.fetchProduct();
  },
  computed: {
    // getImagePath: function () {
    //   return this.imagePath + this.product.image;
    // }
  },
  methods: {
    fetchProduct: function (){
      fetch(`http://localhost:8080/api/product/${this.productId}`, {
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
          console.log("product: ", json);
          this.product = json;
        })
        .catch(er => {
          console.log("Error; ", er);
        });
    }
  }
}
</script>

<style scoped>

.in-stock-text, .advantages-text div {
  display: flex;
}

.in-stock-text p {
  margin-left: 0.5rem;
}

.advantages-text div p {
  margin-left: 1rem;
  margin-bottom: 0.75rem;
}

</style>