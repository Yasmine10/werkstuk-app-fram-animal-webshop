<template>
	<div class="cat">
		<section class="wrapper">
			<div class="categories-list">
        <h4 class="categories-list-title">Producten</h4>
				<ul>
					<li class="category-item">
						<p @click="fetchAllProducts()">Alles weergeven</p>
					</li>
					<li v-for="category in categories" :key="category.id" class="category-item">
						<p @click="fetchProductsByCategory(category.id)">{{ category.name }}</p>
					</li>
				</ul>
        <h4 class="categories-list-title">Merken</h4>
        <ul>
          <li class="category-item">
            <p @click="fetchAllProducts()">Alles weergeven</p>
          </li>
          <li v-for="(brand, index) in brands" :key="index" class="category-item">
            <p @click="fetchProductsByBrand(brand.brand)">{{ brand.brand }}</p>
          </li>
        </ul>
			</div>
			<div>
				<h1>{{ pageTitle }}</h1>
				<p>{{products.length}} artikel(en) weergegeven</p>
				<ul class="products-list">
					<li v-for="product in products" :key="product.id" class="product-item">
						<router-link tag="li" :to="{name: 'Product', params: {id: product.id}}">
							<img class="product-image" :src="makeImagePath(product)" alt="">
							<h4 class="product-title">{{ product.name }}</h4>
							<p class="product-brand">{{ product.brand }}</p>
							<p class="product-price"><span>€ {{ product.price.toFixed(2) }}</span></p>
						</router-link>
					</li>
				</ul>
			</div>
		</section>
	</div>
</template>

<script>
import { imagePath } from '@/mixins/imagePath.js'

export default {
	name: "Cat",
	mixins: [imagePath],
	data() {
		return {
			products: [],
			categories: [],
      brands: []
		};
	},
	computed: {
		animal() {
			return this.$route.name;
		},
		pageTitle() {
			return `${this.animal[0].toUpperCase()}${this.animal.slice(1)}`;
		},
	},
	created() {
		this.fetchAllCategories();
    this.fetchAllBrands();
		this.fetchAllProducts();
	},
	methods: {
		fetchAllCategories: function() {
			fetch(`http://localhost:8080/api/animal/${this.animal}/categories`, {
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
    fetchAllBrands: function () {
      fetch(`http://localhost:8080/api/product/animal=${this.animal}/brands`, {
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
          console.log("brands: ", json);
          this.brands = json;
        })
        .catch(er => {
          console.log("Error; ", er);
        });
    },
		fetchAllProducts: function () {
			fetch(`http://localhost:8080/api/product/animal=${this.animal}`, {
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
					console.log("products by category: ", json);
					this.products = json;
				})
				.catch(er => {
					console.log("Error; ", er);
				});
		},
    fetchProductsByBrand: function (name) {
      fetch(`http://localhost:8080/api/product/animal=${this.animal}/brand=${name}`, {
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
          console.log("products by brand: ", json);
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

h1 {
	text-transform: uppercase;
}

.wrapper {
	display: flex;
	justify-content: flex-start;
	align-items: flex-start;
	margin-top: 1.5rem;
}

.categories-list-title {
  margin-bottom: 1rem;
  text-transform: uppercase;
}

.categories-list {
  border: 1px solid var(--light-beige-color);
  padding: 1rem;
  margin-right: 2rem;
  min-width: 20%;
}

.category-item {
  list-style: none;
  margin-bottom: 1rem;
  margin-left: 0.5rem;
}

.products-list {
	display: grid;
	grid-template-columns: 1fr 1fr 1fr 1fr;
	margin-top: 1.5rem;
  grid-gap: 1rem;
}

.product-item {
	list-style: none;
	/*margin: 1rem;*/
	border: 1px solid var(--light-color);
	padding: 1.5rem;
	transition: transform 500ms;
}

.product-item:hover {
	transform: scale(1.05);
}

.product-image {
	max-width: 100%;
  max-height: 100%;
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