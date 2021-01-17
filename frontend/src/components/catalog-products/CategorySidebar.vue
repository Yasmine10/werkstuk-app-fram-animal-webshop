<template>
  <div class="category-sidebar-wrapper">
    <div v-for="(category, index) in categories" :key="index">
      <p v-on:click="fetchProductsByCategory(category.id)">{{ category.name }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "CategorySidebar",
  props: ['animalId'],
  data: () => ({
    categories: [],
  }),
  created: function() {
    this.fetchAllCategories();
  },
  methods: {
    fetchAllCategories: function() {
      fetch(`http://localhost:8080/api/animal/${this.animalId}/categories`, {
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

        })
        .catch(er => {
          console.log("Error; ", er);
        });
    }
  }
}
</script>

<style scoped>

.category-sidebar-wrapper {
  border: 1px solid var(--light-beige-color);
  padding: 1rem;
  width: 100%;
}

p {
  margin-bottom: 1rem;
  font-size: larger;
}

</style>