<template>
  <v-navigation-drawer
      image="/src/assets/argeamenu.png"
      permanent
      class="menuimage"
      :model-value="statoDrawer"
  >
    <v-list nav>
      <v-list-item prepend-icon="mdi-home" title="Home" value="home" to="/"></v-list-item>
      <v-list-item prepend-icon="mdi-account-group" title="Clienti Argea" value="clientiargea"
                   to="/clientiargea"></v-list-item>
      <v-list-item prepend-icon="mdi-account-switch" title="Associazione clienti"
                   value="associazioneclientiargea" to="/associazioneclientiargea"></v-list-item>
      <v-list-item prepend-icon="mdi-cog-outline" title="Administration"
                   value="administration" to="/administration"></v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "NavigationDrawer",
  data() {
    return {
      statoDrawer: null
    }
  },
}
</script>

<style scoped>
.menuimage .v-navigation-drawer__content {
  background: -moz-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: -o-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: -ms-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
}
</style>