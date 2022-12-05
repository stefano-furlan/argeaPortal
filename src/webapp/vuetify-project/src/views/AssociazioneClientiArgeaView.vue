<template>
  <div>

    <v-progress-linear v-show="isLoading" indeterminate color="black"></v-progress-linear>


    <div v-if="clienteArgeaInEdit!=null">
      <v-row>

        <v-col cols="12"
               md="5">
          <v-card class="padded-card">
            <v-toolbar density="compact" title="CLIENTE ARGEA" color="primary">
              <v-spacer></v-spacer>
              <v-btn icon @click="salvaClienteArgea()">
                <v-icon>mdi-content-save</v-icon>
              </v-btn>
            </v-toolbar>
            <v-form v-model="isValid">
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    md="4"
                  >
                    <v-text-field
                      v-model="clienteArgeaInEdit.id"
                      label="codice argea"
                      disabled variant="outlined"
                      density="compact"
                      hide-details="auto"
                    ></v-text-field>
                  </v-col>

                  <v-col
                    cols="12"
                    md="8"
                  >
                    <v-text-field
                      v-model="clienteArgeaInEdit.descrizione"
                      :rules="nameRules"
                      label="descrizione"
                      variant="outlined"
                      density="compact"
                      hide-details="auto"
                    ></v-text-field>
                  </v-col>

                </v-row>
              </v-container>
            </v-form>


            <EasyDataTable
              :headers="headersClientiCompanySelezionati"
              :items="clienteArgeaInEdit.clientiCompany"
              class="drop-zone"
              @drop="onDrop($event)"
              @dragover.prevent
              @dragenter.prevent
            >
              <template #item-maniglia="item">
                <v-icon icon="mdi-drag" size="small" draggable="true" class="draggable"
                        @dragstart="startDragRemove($event, item)"></v-icon>
              </template>
            </EasyDataTable>
          </v-card>

        </v-col>
        <v-col cols="12"
               md="7">
          <v-card class="padded-card">
            <v-card-title>Clienti company</v-card-title>
            <v-card-text>
              <v-text-field label="filtra" v-model="searchField" variant="underlined"></v-text-field>
              <EasyDataTable
                :headers="headersClientiCompany"
                :items="filteredClientiCompany"
                class="drop-zone"
                @drop="onDropRemove($event)"
                @dragover.prevent
                @dragenter.prevent
              >

                <template #item-maniglia="item">
                  <v-icon icon="mdi-drag" size="small" draggable="true" class="draggable"
                          v-show="item.codiceClienteArgea==null"
                          @dragstart="startDrag($event, item)"></v-icon>
                  <v-icon icon="mdi-account-check" size="small" v-show="item.codiceClienteArgea!=null"></v-icon>
                </template>

              </EasyDataTable>
            </v-card-text>

          </v-card>

        </v-col>
      </v-row>
    </div>


    <!--      <EasyDataTable
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
    clienteArgeaStore.clienteArgea={{ clienteArgeaStore.clienteArgea }}
    clienteTest={{ clienteTest }}
  -->
  </div>
</template>
<script>
import apiClientiArgea from "../web-api/apiClientiArgea";
import {useClienteArgeaStore} from '@/stores/clienteArgea';

//const clone = require('lodash/clone');
import clone from 'lodash/clone';
import find from 'lodash/find';
import remove from 'lodash/remove';



export default {
  setup() {
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
      isValid: false,
      headersClientiCompanySelezionati: [
        {text: "", value: "maniglia", width: 25},
        {text: "company", value: "company"},
        {text: "codice", value: "codiceClienteCompany"},
        {text: "descrizione", value: "descrizione"},

      ],
      headersClientiCompany: [
        {text: "", value: "maniglia", width: 25},
        {text: "company", value: "company", sortable: true},
        {text: "codice", value: "codiceClienteCompany", sortable: true},
        {text: "descrizione", value: "descrizione", sortable: true},
        {text: "piva", value: "partitaIva", width: 150, sortable: true},
        {text: "cfisc", value: "codiceFiscale", width: 150, sortable: true},
      ],

      nameRules: [
        v => !!v || 'Campo obbligatorio',
        v => v.length <= 50 || 'Max 50 caratteri',
      ],

      searchField: null,

      clienteTest: null,

      //DATA
      clientiCompany: [],
      clientiCompanyDelClienteArgea: [],
      clienteArgeaInEdit: {
        id: null,
        descrizione: null,
        clientiCompany: []
      }
    }
  },
  beforeMount: function () {

  },
  mounted() {
    this.init();
  },
  computed: {
    filteredClientiCompany() {
      let descrizione = (this.searchField == null) ? null : this.searchField.toLowerCase();
      let ret = [];
      if (this.clientiCompany != null) {
        ret = this.clientiCompany.filter((i) => {
          return (descrizione == null || (i.descrizione != null && i.descrizione.toLowerCase().indexOf(descrizione) >= 0));
        })
      }
      return ret;
    }
  },
  watch: {},
  methods: {
    init() {
      if (this.clienteArgeaStore != null && this.clienteArgeaStore.clienteArgea != null) {
        this.clienteArgeaInEdit = clone(this.clienteArgeaStore.clienteArgea);
      }
      this.getData();
    },
    getData() {

      this.isLoading = true;


      let params = {};

      apiClientiArgea.methods.getClientiCompany(params).then(function (response) {
        this.isLoading = false;
        this.clientiCompany = response.data;
      }.bind(this));

      if (this.clienteArgeaInEdit != null && this.clienteArgeaInEdit.id != null) {
        let params2 = {id: this.clienteArgeaInEdit.id};
        apiClientiArgea.methods.getClienteArgea(params2).then(function (response) {
          this.isLoading = false;
          this.clienteArgeaInEdit = response.data;
        }.bind(this));
      }

    },
    salvaClienteArgea() {
      this.isLoading = true;
      apiClientiArgea.methods.salvaClienteArgea(this.clienteArgeaInEdit).then(function (response) {
        this.isLoading = false;
        this.clienteArgeaInEdit = response.data;
      }.bind(this));
    },
    startDrag(evt, item) {
      evt.dataTransfer.dropEffect = 'move';
      evt.dataTransfer.effectAllowed = 'move';
      evt.dataTransfer.setData('text/plain', JSON.stringify({action: 'ADD', item: item}));
    },
    startDragRemove(evt, item) {
      evt.dataTransfer.dropEffect = 'move';
      evt.dataTransfer.effectAllowed = 'move';
      evt.dataTransfer.setData('text/plain', JSON.stringify({action: 'REMOVE', item: item}));
    },
    onDrop(evt) {
      let actionText = evt.dataTransfer.getData('text/plain')
      if (actionText != null) {
        let action = JSON.parse(actionText);
        if (action.action === 'ADD') {

          let myItem = action.item;
          this.clienteTest = myItem;//TODO: debug eliminare
          //marco in lista a destra che l'ho preso
          let item = find(this.clientiCompany, {
            codiceClienteCompany: myItem.codiceClienteCompany,
            company: myItem.company
          })
          if (this.clienteArgeaInEdit.id == null) {
            item.codiceClienteArgea = -1;
          } else {
            item.codiceClienteArgea = this.clienteArgeaInEdit.id;
          }
          //e metto in lista a sinistra
          this.clienteArgeaInEdit.clientiCompany.push(myItem);
        }
      }
    },
    onDropRemove(evt) {
      let actionText = evt.dataTransfer.getData('text/plain')
      if (actionText != null) {
        let action = JSON.parse(actionText);
        if (action.action === 'REMOVE') {
          let myItem = action.item;

          //marco in lista a destra che l'ho preso
          let itemDx = find(this.clientiCompany, {
            codiceClienteCompany: myItem.codiceClienteCompany,
            company: myItem.company
          })
          itemDx.codiceClienteArgea = null;

          //e rimuovo da lista a sx
          let itemSx = find(this.clienteArgeaInEdit.clientiCompany, {
            codiceClienteCompany: myItem.codiceClienteCompany,
            company: myItem.company
          })
          remove(this.clienteArgeaInEdit.clientiCompany, itemSx);
        }
      }
    },

  }
}
</script>
<style>

</style>
