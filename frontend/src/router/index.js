import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/index'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home')
  },
  {
    path: '/products/:id',
    name: 'Product',
    component: () => import('../views/Product')
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/Cart'),
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: () => import('../views/Checkout'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/checkout/success',
    name: 'Checkout success',
    component: () => import('../views/CheckoutSuccess'),
    meta: {
      requiresAuth: true
    }
  },
  // {
  //   path: '/:animal/',
  //   name: 'AnimalOverview',
  //   component: () => import('../views/AnimalOverview'),
  //   delay: 200,
  // },
  {
    path: '/hond',
    name: 'Hond',
    component: () => import('../views/Dog')
  },
  {
    path: '/kat',
    name: 'Kat',
    component: () => import('../views/Cat')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login'),
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  },
  {
    path: '/profiel',
    name: 'Profile',
    component: () => import('../views/user-views/Profile'),

    children: [
      {
        path: '/profiel/gegevens',
        name: 'MyDetails',
        props: true,
        meta: {
          requiresAuth: true
        },
        component: () => import('../views/user-views/MyDetails'),
        // children: [
        //   {
        //     path: '/profiel/gegevens/bewerken'
        //   }
        // ]
      },
      {
        path: '/profiel/addressen',
        name: 'MyAddresses',
        component: () => import('../views/user-views/MyAddresses'),
        children: [
          {
            path: '/profiel/addressen/',
            name: 'ShowAddresses',
            props: true,
            component: () => import('../components/profile-components/ShowAddresses')
          },
          {
            path: '/profiel/addressen/adres-toevoegen',
            name: 'AddAddress',
            component: () => import('../components/profile-components/AddAddress'),
          },
          {
            path: '/profiel/addressen/adres-bewerken/:id',
            name: 'EditAddress',
            component: () => import('../components/profile-components/EditAddress')
          }
        ]
      },
      {
        path: '/profiel/bestellingen',
        name: 'MyOrders',
        component: () => import('../views/user-views/MyOrders'),
        // children: [
        //   {
        //     path: '/profiel/bestellingen/:id',
        //     name: 'Order',
        //     component: () => import('')
        //   }
        // ]
      }
    ]
  },

]



const router = new VueRouter({

  routes
})

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)) {
    if (store().getters.isLoggedIn) {
      console.log(store().getters.isLoggedIn)
      next()
      return
    }
    //console.log(store().getters.isLoggedIn)
    next('/login')
  } else {
    next()
  }
})

export default router
