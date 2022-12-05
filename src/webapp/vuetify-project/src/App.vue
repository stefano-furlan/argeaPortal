<template>
  <v-app id="app-argea">


    <v-app-bar>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <v-img width="20" src="/src/assets/argea.png"></v-img>
      <v-spacer></v-spacer>
      <v-icon icon="mdi:mdi-logout-variant" />

    </v-app-bar>
    <v-navigation-drawer
      image="/src/assets/argeamenu.png"
      permanent
      class="menuimage"
      v-model="drawer"
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
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script >
export default {
  data: () => ({ drawer: null }),
}
</script>

<style >
.menuimage .v-navigation-drawer__content {
  background: -moz-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: -o-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: -ms-linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
  background: linear-gradient(156deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 90%);
}
.draggable:hover {
  cursor: grab;
}

.padded-card {
  margin: 10px;
}
</style>
