<template>
  <div>

    <v-progress-linear v-show="isLoading" indeterminate color="black"></v-progress-linear>


    <div v-if="clienteArgeaLocalInEdit!=null">
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
                      v-model="clienteArgeaLocalInEdit.id"
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
                      v-model="clienteArgeaLocalInEdit.descrizione"
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
              :items="clienteArgeaLocalInEdit.clientiCompany"
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
            <v-toolbar density="compact" title="CLIENTI COMPANY" color="grey">


            </v-toolbar>
            <v-card-text>
              <v-text-field label="filtra" v-model="searchField" variant="underlined" density="compact"></v-text-field>
              <EasyDataTable
                :headers="headersClientiCompany"
                :items="filteredClientiCompany"
                alternating
                class="drop-zone"
                :rows-per-page="10"
                @drop="onDropRemove($event)"
                @dragover.prevent
                @dragenter.prevent
              >
                <template #loading>
                  AAAA
                  <v-progress-circular XXv-show="isLoadingClienti" indeterminate color="primary"></v-progress-circular>
                </template>
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

  </div>
</template>

<script>

import apiClientiArgea from "@/web-api/apiClientiArgea";
import find from "lodash/find";
import remove from 'lodash/remove';

export default {
  name: "ClienteArgeaEdit",
  props: {
    clienteArgeaInEdit: Object // previously was `value: String`
  },
  emits: ['update:clienteArgeaInEdit'],
  data() {
    return {
      //window workings
      clienteArgeaLocalInEdit: null,
      isLoading: false,
      isLoadingClienti: false,
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

    }
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

      this.getData();
    },
    getData() {

      this.isLoading = true;

      let params = {};

      apiClientiArgea.methods.getClientiCompany(params).then(function (response) {
        this.isLoading = false;
        this.clientiCompany = response.data;
      }.bind(this));

      this.isLoadingClienti=true;
      if (this.clienteArgeaInEdit != null && this.clienteArgeaInEdit.id != null) {
        let params2 = {id: this.clienteArgeaInEdit.id};
        apiClientiArgea.methods.getClienteArgea(params2).then(function (response) {
          this.isLoadingClienti = false;
          this.clienteArgeaLocalInEdit = response.data;
        }.bind(this));
      } else {
        this.isLoadingClienti = false;
        this.clienteArgeaLocalInEdit = {
          id: null
          , descrizione: null
          , clientiCompany: []
        }
      }

    },
    salvaClienteArgea() {
      if (!this.isValid) {
        return;
      }
      this.isLoading = true;
      apiClientiArgea.methods.salvaClienteArgea(this.clienteArgeaLocalInEdit).then(function (response) {
        this.isLoading = false;
        this.clienteArgeaLocalInEdit = response.data;
        //TODO: emit evento di salvataggio completato
      }.bind(this)).catch(function () {
        this.isLoading = false;
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
          if (this.clienteArgeaLocalInEdit.id == null) {
            item.codiceClienteArgea = -1;
          } else {
            item.codiceClienteArgea = this.clienteArgeaLocalInEdit.id;
          }
          //e metto in lista a sinistra
          this.clienteArgeaLocalInEdit.clientiCompany.push(myItem);
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
          let itemSx = find(this.clienteArgeaLocalInEdit.clientiCompany, {
            codiceClienteCompany: myItem.codiceClienteCompany,
            company: myItem.company
          })
          remove(this.clienteArgeaLocalInEdit.clientiCompany, itemSx);
        }
      }
    },

  }
}
</script>

<style scoped>

</style>
