<template>
  <div>

    <v-progress-circular v-show="clienteArgea==null" indeterminate color="primary"></v-progress-circular>
    <div v-if="clienteArgea!=null">
      <v-card max-height="400px">
        <v-toolbar color="grey" density="compact" title="cliente argea"></v-toolbar>
        <v-card-text>
          <div>{{ clienteArgea.id }}</div>
          <div>{{ clienteArgea.descrizione }}</div>
          <v-list lines="one" density="compact" max-height="300px">
            <v-list-item
              v-for="(item,index) in clienteArgea.clientiCompany"
              :key="index"
            >
              {{ item.company }} - {{ item.codiceClienteCompany }}
              <v-list-item-subtitle>
                {{ item.descrizione }}
              </v-list-item-subtitle>

            </v-list-item>
          </v-list>
        </v-card-text>
      </v-card>

    </div>

    <!--    <div>{{ clienteArgea.id }}</div>
        <div>{{ clienteArgea.descrizione }}</div>
        <div>
          <v-list lines="one">
            <v-list-item
              v-for="(item,index) in clienteArgea.clientiCompany"
              :key="index"
              :title="item.descrizione"
            >{{item.company}}-{{item.descrizione}}
            </v-list-item>
          </v-list>
        </div>-->
  </div>
</template>

<script>

import apiClientiArgea from "@/web-api/apiClientiArgea";
import find from "lodash/find";
import remove from 'lodash/remove';

export default {
  name: "ClienteArgeaInfo",
  props: {
    clienteArgeaId: String // previously was `value: String`
  },
  data() {
    return {
      isLoading: false,
      clienteArgea: null

    }
  },
  mounted() {
    this.init();
  },
  computed: {},
  watch: {},
  methods: {
    init() {
      this.getData();
    },
    getData() {

      let params = {
        id: this.clienteArgeaId
      };
      this.isLoading = true;
      apiClientiArgea.methods.getClienteArgea(params).then(function (response) {
        this.isLoading = false;
        this.clienteArgea = response.data;
      }.bind(this)).catch(function () {
        this.isLoading = false;
      }.bind(this));


    },


  }
}
</script>

<style scoped>

</style>
