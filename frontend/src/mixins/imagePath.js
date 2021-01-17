export const imagePath = {
  methods: {
    makeImagePath(product) {
      return require(`../assets/images/products/${product.image}`);
    }
  }
}