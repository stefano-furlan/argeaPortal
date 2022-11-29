<script setup>
import {/*RouterLink,*/ RouterView} from 'vue-router'
import NavigationDrawer from "@/components/NavigationDrawer.vue";
</script>

<template>


  <v-app>


    <v-app-bar dense >
      <v-app-bar-nav-icon icon="mdi:mdi-menu"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <v-img width="20" src="/src/assets/argea.png"></v-img>
      <v-spacer></v-spacer>
      <v-icon icon="mdi:mdi-logout-variant" />
    </v-app-bar>

    <navigation-drawer></navigation-drawer>
    <v-main>
        <RouterView/>
    </v-main>

<!--    <RouterLink></RouterLink>-->
  </v-app>


</template>
<script>
export default {
  data() {
    return {
      test: true,
      cards: ['Today', 'Yesterday'],
      drawer: null,
      links: [
        ['mdi-inbox-arrow-down', 'Inbox'],
        ['mdi-send', 'Send'],
        ['mdi-delete', 'Trash'],
        ['mdi-alert-octagon', 'Spam']
          ]
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
