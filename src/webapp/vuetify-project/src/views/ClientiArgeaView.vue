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
        :items="clientiArgea"
        alternating
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

    </v-card>

    <v-dialog v-model="isClienteEditShown" persistent>
      <v-card v-if="isClienteEditShown">
        <v-toolbar color="primary" dark density="compact">
          <v-spacer></v-spacer>
          <v-btn icon @click="isClienteEditShown=false">
            <v-icon icon="mdi-close"></v-icon>
          </v-btn>
        </v-toolbar>
        <cliente-argea-edit :cliente-argea-in-edit="clienteArgeaInEdit"
                            @cliente-salvato="onClienteSalvato"></cliente-argea-edit>
      </v-card>

    </v-dialog>

    <v-dialog v-model="isClienteDeleteShown" persistent max-width="300">
      <v-card v-if="isClienteDeleteShown">
        <v-toolbar color="primary" dark density="compact">
          <v-spacer></v-spacer>
          <v-btn icon @click="isClienteDeleteShown=false">
            <v-icon icon="mdi-close"></v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-text>
          Eliminare il cliente ARGEA {{this.clienteArgeaInEdit.descrizione}}?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" text @click="isClienteDeleteShown = false">
            Annulla
          </v-btn>
          <v-btn color="green" dark @click="executeDeleteClienteArgea" :loading="isLoading">
            Si
          </v-btn>
        </v-card-actions>
      </v-card>

    </v-dialog>
  </div>
</template>
<script>
import apiClientiArgea from "../web-api/apiClientiArgea";
//import {Header, Item} from "vue3-easy-data-table";
import clienteArgeaEdit from "@/components/ClienteArgeaEdit";
import {useClienteArgeaStore} from '@/stores/clienteArgea';
import findIndex from "lodash/findIndex";

export default {
  setup() {
    const clienteArgeaStore = useClienteArgeaStore()
    return {
      // you can return the whole store instance to use it in the template
      clienteArgeaStore,
    }
  },
  components: {clienteArgeaEdit},
  //mixins: [globalMixins, agGridMixins],
  data() {
    return {

      //window workings
      isLoading: false,
      headers: [
        {text: "ID", value: "id", sortable: true },
        {text: "Descrizione", value: "descrizione", sortable: true },
        {text: "", value: "azioni", width: 200},
      ],
      isClienteEditShown: false,
      isClienteDeleteShown: false,

      //DATA
      clientiArgea: [],
      clienteArgeaInEdit: {}
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
        this.clientiArgea = response.data;
      }.bind(this)).catch(function(){
        this.isLoading = false;
      }.bind(this));
    },
    nuovoClienteArgea(item) {
      //mi sposto in pagina edit

      this.clienteArgeaInEdit = {
        id: null,
        descrizione: null,
        clientiCompany: []
      };

      this.isClienteEditShown = true;
    },
    editClienteArgea(item) {
      //salvo nello store l'oggetto
      this.clienteArgeaInEdit = item;
      this.isClienteEditShown = true;
    },

    deleteClienteArgea(item) {
      this.clienteArgeaInEdit=item;
      this.isClienteDeleteShown = true;
    },
    executeDeleteClienteArgea() {
      let params = {};
      let item= this.clienteArgeaInEdit;
      this.isLoading = true;
      apiClientiArgea.methods.eliminaClienteArgea(item).then(function (response) {
        this.isLoading = false;

        let index = findIndex(this.clientiArgea, {
          id: item.id,
        });
        if (index >= 0) {
          this.clientiArgea.splice(index, 1);
        }
        this.isClienteDeleteShown=false;
      }.bind(this)).catch(function(){
        this.isLoading = false;
      }.bind(this));
    },
    onClienteSalvato(clienteArgea) {
      //chiudo il popup
      this.isClienteEditShown = false;
      //evitiamo il becero reload che è brutto
      //se esiste in lista rimpiazzo, else lo accodo
      let index = findIndex(this.clientiArgea, {
        id: clienteArgea.id,
      });
      if (index < 0) {
        this.clientiArgea.push(clienteArgea);
      } else {
        this.clientiArgea.splice(index, 1, clienteArgea);
      }


    }

  }
}
</script>
<style>

</style>
