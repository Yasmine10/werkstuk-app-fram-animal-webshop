<template>
  <div id="app">
    <Header class="header" />
    <Navbar class="navbar" />
    <div class="content-wrapper">
      <router-view class="content"/>
    </div>
    <Footer class="footer"/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";


export default {
	components: {
    Footer,
		Header,
		Navbar
	},

}
</script>

<style>

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

#app{
  position: relative;
  min-height: 100vh;
}

.content-wrapper {
	margin: 0 10%;
  padding-bottom: 16.5rem;
}

.content {
  margin-top: 3%;
  margin-bottom: 5%;
}

.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 16.5rem;
}

</style>
