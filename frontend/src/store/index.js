import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

Vue.use(Vuex)

export default function() {
  return new Vuex.Store({
    state: {
      cart: [],
      products: [],
      categories: [],
      status: '',
      token: sessionStorage.getItem('token') || '',
      user: sessionStorage.getItem('user') || {},
      address:[],
      orders: []
    },
    mutations: {
      saveProducts(state, products) {
        state.products = products;
      },
      saveCategories(state, categories) {
        state.categories = categories;
      },
      saveAddress(state, address) {
        state.address = address;
      },
      updateAddress(state, address) {
        let indexToDelete = state.address.indexOf(Number(address.id));
        state.address.splice(indexToDelete, 1);
        state.address.push(address);
      },
      deleteAddress(state, payload) {
        let indexToDelete = state.address.indexOf(Number(payload));
        state.address.splice(indexToDelete, 1);
      },
      saveOrder(state, payload) {
        state.orders.push(payload);
      },
      addToCart(state, payload) {
        let product = state.products.find(p => p.id === payload.item.id);
        let cartItem = state.cart.find(c => c.item === product);
        if (cartItem) {
          cartItem.quantity += payload.quantity;
        } else {
          state.cart.push(payload);
        }
      },
      removeFromCart(state, payload) {
        let indexToDelete = state.cart.indexOf(Number(payload));
        state.cart.splice(indexToDelete, 1);
      },
      auth_request(state) {
        state.status = 'loading'
      },
      auth_success(state, user) {
        state.status = 'success'
        state.token = user.token
        state.user = user
        console.log(state.user)
      },
      auth_error(state) {
        state.status = 'error'
      },
      logout(state) {
        state.status = ''
        state.token = ''
        state.user = []
        state.cart = []
      },
    },
    actions: {
      loadProducts: function ({commit}) {
        axios.get('http://localhost:8080/api/product')
          .then(response => {
            commit('saveProducts', response.data);
          }, error => {
            console.log(error);
          })
      },
      loadCategories: function ({commit}) {
        axios.get('http://localhost:8080/api/category')
          .then(response => {
            commit('saveCategories', response.data);
          }, error => {
            console.log(error);
          })
      },
      loadAddress: function ({commit}, id) {
        axios.get(`http://localhost:8080/api/user/${id}/addresses`)
          .then(response => {
            commit('saveAddress', response.data);
          }, error => {
            console.log(error);
          })
      },
      addAddress: function ({commit}, payload) {
        console.log(payload.address);
        let address = {
          street: payload.address.street,
          houseNr: payload.address.houseNr,
          zipcode: payload.address.zipcode,
          city: payload.address.city,
          country: payload.address.country,
          user: {
              id: payload.address.user.id
          }
        }
        console.log(address);
        return new Promise((resolve, reject) => {
          axios({url: 'http://localhost:8080/api/address', data: address, method: 'POST'})
            .then(resp => {
              commit('saveAddress', resp.data)
              resolve(resp)
            })
            .catch(err => {
              reject(err)
            })
        })
      },
      updateAddress: function ({commit}, payload) {
        console.log(payload.address);
        let address = {
          id: payload.address.id,
          street: payload.address.street,
          houseNr: payload.address.houseNr,
          zipcode: payload.address.zipcode,
          city: payload.address.city,
          country: payload.address.country,
          user: {
            id: payload.address.user.id
          }
        }
        console.log(address);
        return new Promise((resolve, reject) => {
          axios({url: 'http://localhost:8080/api/address', data: address, method: 'PUT'})
            .then(resp => {
              commit('updateAddress', resp.data)
              resolve(resp)
            })
            .catch(err => {
              reject(err)
            })
        })
      },
      deleteAddress: function ({commit}, id) {
        console.log(id);
        return new Promise((resolve, reject) => {
          axios({url: `http://localhost:8080/api/address/${id}`, method: 'DELETE'})
            .then(resp => {
              commit('deleteAddress', id)
              resolve(resp)
            })
            .catch(err => {
              reject(err)
            })
        })
      },
      addOrder: function ({commit}, order) {
        console.log("payload in addOrder axios: " + order)
        // let order = {
        //   totalPrice: payload.order.totalPrice,
        //   shippingPrice: payload.order.shippingPrice,
        //   shippingMethod: payload.order.delivery,
        //   paymentMethod: payload.order.payment,
        //   user: {
        //     id: payload.order.user.id
        //   }
        // }
        // console.log(order);
        return new Promise((resolve, reject) => {
          commit('auth_request')
          axios({url: 'http://localhost:8080/api/order', data: order, method: 'POST'})
            .then(resp => {
              commit('saveOrder', resp.data);
              resolve(resp)
            })
            .catch(err => {
              reject(err)
            })
        })
      },
      addOrderItem: function ({commit}, payload) {
        console.log(payload)

        let orderItem = {
          amount: payload.orderitem.quantity,
          subTotal: payload.subtotal,
          product: {
            id: payload.orderitem.products.id
          },
          order: {
            id: payload.orderId
          }
        }
        console.log(orderItem);
        return new Promise((resolve, reject) => {
          commit('auth_request')
          axios({url: 'http://localhost:8080/api/order-item', data: orderItem, method: 'POST'})
            .then(resp => {
              resolve(resp)
            })
            .catch(err => {
              reject(err)
            })
        })
      },
      addToCart({commit}, payload) {
        commit('addToCart', payload);
      },
      removeFromCart({commit}, payload) {
        commit('removeFromCart', payload);
      },
      login({commit}, user) {
        return new Promise((resolve, reject) => {
          commit('auth_request')
          axios.post('http://localhost:8080/api/user/login',{
            email: user.email,
            password: user.password
          })
            .then(resp => {
              console.log(resp.data.token);
              console.log(resp.data);
              const token = resp.data.token;
              // const user = resp.data;
              sessionStorage.setItem('token', token)
              sessionStorage.setItem('user', resp.data)
              axios.defaults.headers.common['Authorization'] = resp.data
              commit('auth_success', resp.data)
              resolve(resp)
            })
            .catch(err => {
              commit('auth_error')
              sessionStorage.removeItem('token')
              sessionStorage.removeItem('user')
              reject(err)
            })
        })
      },
      register({commit}, user) {
        return new Promise((resolve, reject) => {
          commit('auth_request')
          axios({url: 'http://localhost:8080/api/user', data: user, method: 'POST'})
            .then(resp => {
              const token = resp.data.token
              const user = resp.data
              sessionStorage.setItem('token', token)
              sessionStorage.setItem('user', resp.data)
              axios.defaults.headers.common['Authorization'] = resp.data
              commit('auth_success', user)
              resolve(resp)
            })
            .catch(err => {
              commit('auth_error', err)
              sessionStorage.removeItem('token')
              sessionStorage.removeItem('user')
              reject(err)
            })
        })
      },
      logout({commit}) {
        return new Promise((resolve) => {
          commit('logout')
          sessionStorage.removeItem('token')
          sessionStorage.removeItem('user')
          delete axios.defaults.headers.common['Authorization']
          resolve()
        })
      },
    },
    modules: {},
    getters: {
      getAllProducts: (state) => {
        return state.products;
      },
      getProduct: (state) => (id) => {
        return state.products.filter(p => p.id === Number(id))[0];
      },
      getProductsByAnimal: (state) => (animal) => {
        return state.products.filter(p => p.animal.name === animal);
      },
      getProductsByCategory: (state) => (category) => {
        return state.products.filter(p => p.category.id === Number(category));
      },
      getAllCategories: (state) => {
        return state.categories;
      },
      getCategoriesByAnimal: (state) => (animal) => {
        return state.categories.filter(c => c.animal.name === animal);
      },
      getUser: (state) => {
        return state.user;
      },
      getAddresses: (state) => {
        return state.address;
      },
      getAddress: (state) => (id) => {
        return state.address.filter(a => a.id === Number(id))[0];
      },
      getCartItemsCount: (state) => {
        return state.cart.reduce((total, item) => total + item.quantity, 0);
      },
      getCartItems: (state) => {
        return state.cart.map(
          itemId => state.products.find(product => product.id === itemId)
        );
      },
      getCartItemsSubtotal: (state) => {
        return state.cart.reduce((total, item) => total + (item.item.price * item.quantity), 0).toFixed(2);
      },
      isLoggedIn: state => !!state.token,
      authStatus: state => state.status,
      hasUser: state => !!state.user,
      hasAddresses: state => !!state.address
    }
  });
}
