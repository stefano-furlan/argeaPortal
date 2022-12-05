<template>
  <div>

    <div>
      <v-row>

        <v-col cols="12"
               md="5">
          <v-card class="padded-card">
            <v-toolbar density="compact" color="primary">
              <v-toolbar-title><span
                v-show="clienteArgeaLocalInEdit!=null && clienteArgeaLocalInEdit.id==null">NUOVO</span> CLIENTE ARGEA
              </v-toolbar-title>

              <v-spacer></v-spacer>
              <v-btn icon @click="salvaClienteArgea()">
                <v-icon>mdi-content-save</v-icon>
              </v-btn>
            </v-toolbar>
            <v-progress-linear v-show="isLoadingClienteArgea" indeterminate color="primary"></v-progress-linear>
            <div v-if="clienteArgeaLocalInEdit!=null">
              <v-form ref="formClienteArgea" v-model="isFormClienteArgeaValid">
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
                        ref="descrizione"
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
            </div>
          </v-card>

        </v-col>
        <v-col cols="12"
               md="7">
          <v-card class="padded-card">
            <v-toolbar density="compact" title="CLIENTI COMPANY" color="grey">
            </v-toolbar>

            <v-card-text>
              <v-text-field label="filtra" v-model="searchField" variant="underlined" density="compact"></v-text-field>
              <v-progress-linear v-show="isLoadingClientiCompany" indeterminate color="primary"></v-progress-linear>
              <EasyDataTable
                v-show="isLoadingClientiCompany==false"
                :headers="headersClientiCompany"
                :items="filteredClientiCompany"
                alternating
                class="drop-zone"
                :rows-per-page="10"
                @drop="onDropRemove($event)"
                @dragover.prevent
                @dragenter.prevent
              >
                <template #item-maniglia="item">
                  <v-icon icon="mdi-drag" size="small" draggable="true" class="draggable"
                          v-show="item.codiceClienteArgea==null"
                          @dragstart="startDrag($event, item)"></v-icon>

                  <!--                  <v-menu  open-on-hover
                                             :close-on-content-click="false"
                                             absolute
                                             offset-y>
                  &lt;!&ndash;                    <template v-slot:activator="{ on ,attrs }">
                                        <v-icon v-bind="attrs"  v-on="on" icon="mdi-account-check" size="small"
                                                v-show="item.codiceClienteArgea!=null && item.codiceClienteArgea==clienteArgeaLocalInEdit.id"></v-icon>
                                        <v-icon v-bind="attrs"  v-on="on" icon="mdi-account-cancel" size="small"
                                                v-show="item.codiceClienteArgea!=null && item.codiceClienteArgea!=clienteArgeaLocalInEdit.id"></v-icon>
                                      </template>
                                      MENU!&ndash;&gt;
                                      <template v-slot:activator="{ on, attrs }">
                                        <v-btn
                                          color="indigo"
                                          dark
                                          v-bind="attrs"
                                          v-on="on"
                                        >
                                          Menu as Popover
                                        </v-btn>
                                      </template>
                                      <v-card>
                                        AZZ!
                                      </v-card>
                                    </v-menu>-->
                  <v-menu
                    open-on-hover
                    close-on-content-click

                  >
                    <template v-slot:activator="{ props }">

                      <v-icon v-bind="props" icon="mdi-account-check" size="small"
                              v-show="item.codiceClienteArgea!=null && item.codiceClienteArgea==clienteArgeaLocalInEdit.id"></v-icon>
                      <v-icon v-bind="props" icon="mdi-account-cancel" size="small"
                              v-show="item.codiceClienteArgea!=null && item.codiceClienteArgea!=clienteArgeaLocalInEdit.id"></v-icon>

                    </template>

                    <v-card>
                      <cliente-argea-info :cliente-argea-id="item.codiceClienteArgea"></cliente-argea-info>
                    </v-card>
                  </v-menu>
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
import ClienteArgeaInfo from "@/components/ClienteArgeaInfo";

export default {
  name: "ClienteArgeaEdit",
  components: {ClienteArgeaInfo},
  props: {
    clienteArgeaInEdit: Object // previously was `value: String`
  },
  emits: ['clienteSalvato'],
  data() {
    return {
      //window workings
      clienteArgeaLocalInEdit: null,
      isLoadingClienteArgea: false,
      isLoadingClientiCompany: false,
      isFormClienteArgeaValid: true,
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

      fav: true,
      menu: false,
      message: false,
      hints: true,

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

      let paramsClienti = {};
      this.isLoadingClientiCompany = true;
      apiClientiArgea.methods.getClientiCompany(paramsClienti).then(function (response) {
        this.isLoadingClientiCompany = false;
        this.clientiCompany = response.data;
      }.bind(this)).catch(function () {
        this.isLoadingClientiCompany = false;
      }.bind(this));

      this.isLoadingClienteArgea = true;
      if (this.clienteArgeaInEdit != null && this.clienteArgeaInEdit.id != null) {
        let paramsClienteArgea = {id: this.clienteArgeaInEdit.id};
        apiClientiArgea.methods.getClienteArgea(paramsClienteArgea).then(function (response) {
          this.isLoadingClienteArgea = false;
          this.clienteArgeaLocalInEdit = response.data;
        }.bind(this)).catch(function () {
          this.isLoadingClientiCompany = false;
        }.bind(this));
      } else {
        this.isLoadingClienteArgea = false;
        this.clienteArgeaLocalInEdit = {
          id: null
          , descrizione: null
          , clientiCompany: []
        }
      }

    },
    async salvaClienteArgea() {
      await this.$refs['descrizione'].validate();
      /*      this.formHasErrors = false

            Object.keys(this.form).forEach(f => {
              if (!this.form[f]) this.formHasErrors = true

              this.$refs[f].validate(true)
            })*/

      if (!this.isFormClienteArgeaValid) {
        return;
      }

      this.isLoadingClienteArgea = true;
      apiClientiArgea.methods.salvaClienteArgea(this.clienteArgeaLocalInEdit).then(function (response) {
        this.isLoadingClienteArgea = false;
        this.clienteArgeaLocalInEdit = response.data;
        //TODO: emit evento di salvataggio completato
        this.$emit('clienteSalvato', response.data);
      }.bind(this)).catch(function () {
        this.isLoadingClienteArgea = false;
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
