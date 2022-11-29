<template>
  <v-app id="inspire">
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

    <v-app-bar>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>Application</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <!--  -->
    </v-main>
  </v-app>
</template>

<script>
export default {
  data: () => ({ drawer: null }),
}
</script>
