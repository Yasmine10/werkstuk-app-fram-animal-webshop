<template>
	<nav class="navbar-wrapper">
		<ul class="nav-links">
			<router-link tag="li" to="/">Home</router-link>
			<router-link tag="li" to="/hond">Hond</router-link>
			<router-link tag="li" to="/kat">Kat</router-link>
		</ul>
	</nav>
</template>

<script>
export default {
	name: "Navbar",
	props: ["links"]
}
</script>

<style scoped>

.navbar-wrapper {
	padding: 0 10%;
	
	display: flex;
	justify-content: flex-start;
	align-items: center;
	background-color: var(--primary-color);
	color: var(--white-color);
}

.nav-links {
	margin-right: 3rem;
	margin-bottom: 0;
	display: flex;
}

li {
	list-style: none;
	text-transform: uppercase;
	padding: 1rem 2rem;
	/*margin-right: 1rem;*/
}

li.router-link-exact-active {
	background-color: var(--white-color);
	color: var(--primary-color);
	padding: 1rem 2rem;
}

</style>