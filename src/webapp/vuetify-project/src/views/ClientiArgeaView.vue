<template>
  <div>
    <v-card elevation="0" height="400px">

      <v-toolbar density="compact" title="CLIENTI ARGEA" color="primary">
        <v-spacer></v-spacer>

        <v-btn icon @click="nuovoClienteArgea()">
          <v-icon>mdi-account-plus</v-icon>
        </v-btn>
      </v-toolbar>
      <v-progress-linear v-show="isLoading" indeterminate color="black"></v-progress-linear>
      <EasyDataTable
        :headers="headers"
        :items="data"
      >
        <template #item-azioni="item">
          <v-btn icon color="primary" size="small" @click="editClienteArgea(item)" variant="text">
            <v-icon icon="mdi-pencil" size="small"></v-icon>
          </v-btn>
          <v-btn icon color="primary" size="small" @click="deleteClienteArgea(item)" variant="text">
            <v-icon icon="mdi-delete" size="small"></v-icon>
          </v-btn>
        </template>
      </EasyDataTable>
      clienteArgeaStore.clienteArgea={{clienteArgeaStore.clienteArgea}}
    </v-card>

  </div>
</template>
<script>
import apiClientiArgea from "../web-api/apiClientiArgea";
//import {Header, Item} from "vue3-easy-data-table";

import { useClienteArgeaStore } from '@/stores/clienteArgea';

export default {
  setup(){
    const clienteArgeaStore = useClienteArgeaStore()

    return {
      // you can return the whole store instance to use it in the template
      clienteArgeaStore,
    }
  },
  //components: { InterventoManutenzioneEditor},
  //mixins: [globalMixins, agGridMixins],
  data() {
    return {

      //window workings
      isLoading: false,
      headers: [
        {text: "ID", value: "id"},
        {text: "Descrizione", value: "descrizione"},
        {text: "", value: "azioni", width: 200},
      ],
      //DATA
      data: [],
    }
  },
  beforeMount: function () {

  },
  mounted() {
    this.init();
  },
  computed: {},
  watch: {},
  methods: {
    async init() {
      this.getData();
    },
    reload() {
      this.getData();
    },
    getData() {

      this.isLoading = true;

      let params = {};

      apiClientiArgea.methods.getClientiArgea(params).then(function (response) {
        this.isLoading = false;
        this.data = response.data;
      }.bind(this));
    },
    nuovoClienteArgea(item) {
      //mi sposto in pagina edit

    },
    editClienteArgea(item) {
      //salvo nello store l'oggetto
      this.clienteArgeaStore.clienteArgea=item;
      this.$router.push('/associazioneclientiargea');
    },
    deleteClienteArgea(item) {
      alert(item);
    }

  }
}
</script>
<style>

</style>
