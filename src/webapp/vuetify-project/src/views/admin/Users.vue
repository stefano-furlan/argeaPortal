<template>
  <div>

    <v-data-table v-if="isLoaded"
                  :headers="headers"
                  :items="users"
                  class="elevation-1"
                  :search="search"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-spacer></v-spacer>
          <v-text-field
              v-model="search"
              append-icon="fa-search"
              label="Cerca"
              single-line
              hide-details
          ></v-text-field>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
          <v-btn
              color="primary"
              dark
              class="mb-2"
              @click.stop="nuovoUtente"
          >NUOVO
          </v-btn>

        </v-toolbar>
      </template>

      <template v-slot:item.lastLogin="{ item }">
        <span>{{ item.lastLogin|formatDateTime }}</span>
      </template>

      <template v-slot:item.stato="{ item }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-icon small color="grey" class="cursor-pointer" v-show="item.enabled===false" v-on="on">
              fa-user-slash
            </v-icon>
          </template>
          <span>Disabilitato</span>
        </v-tooltip>
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-icon small color="red" class="cursor-pointer" v-show="item.blocked===true" v-on="on">
              fa-user-lock
            </v-icon>
          </template>
          <span>Bloccato per superamento numero login fallite</span>
        </v-tooltip>
      </template>

      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editUtente(item)"
        >
          mdi-pencil
        </v-icon>
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-icon
                small
                class="mr-2"
                @click="reimpostaPasswordInit(item)"
                v-bind="attrs"
                v-on="on"
            >
              fa-key
            </v-icon>
          </template>
          <span>Reimposta password</span>
        </v-tooltip>
        <v-icon
            small
            @click="deleteUtenteInit(item)"
        >
          mdi-delete
        </v-icon>
      </template>

    </v-data-table>

    <v-form ref="formUtente" v-model="formUserValid">
      <v-dialog v-model="dialogUtenteVisible" max-width="800px" persistent>

        <v-card v-if="editedItem!=null">
          <v-card-title>
            <span class="headline" v-if="editedItem.operationType==='NEW'">NUOVO UTENTE</span>
            <span class="headline" v-if="editedItem.operationType!=='NEW'">UTENTE</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.username" label="username"
                                :readonly="editedItem.operationType!=='NEW'"
                                :disabled="editedItem.operationType!=='NEW'"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">

                  <v-switch
                      v-model="editedItem.enabled"
                      :label="`${(editedItem.enabled===true)?'Abilitato':'Disabilitato'}`"
                  ></v-switch>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-checkbox
                      v-model="editedItem.blocked"
                      label="Bloccato"
                      color="red"
                  ></v-checkbox>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.nome" label="Nome"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.cognome"
                                label="Cognome"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.email" label="email"
                                type="email"></v-text-field>
                </v-col>
              </v-row>

              <v-row v-if="editedItem.operationType==='NEW'">

                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.newPassword" type="password"
                                :rules="rulesPassword" ref="password"
                                label="Prima password"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.passwordCheck" type="password"
                                :rules="rulesPasswordCheck" ref="newPassword"
                                label="Conferma prima password"
                  ></v-text-field>
                </v-col>
                <!--                                        <v-col cols="12" sm="6" md="4">
                                                            <v-checkbox
                                                                    v-model="editedItem.mustChangePassword"
                                                                    label="Cambiare la password al primo accesso"
                                                                    color="red"
                                                            ></v-checkbox>
                                                        </v-col>-->


              </v-row>
              <v-row>
                <v-card outlined min-width="100%">
                  <v-card-subtitle>SSO botter.loc
                  </v-card-subtitle>
                  <v-card-text>
                    <v-row>
                      <v-col cols="12" sm="6" md="4">
                        <v-switch
                            v-model="editedItem.ssoEnabled"
                            :label="`${(editedItem.ssoEnabled===true)?'Abilitato':'Disabilitato'}`"
                        ></v-switch>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.ssoUser"
                                      label="Utente botter.loc"></v-text-field>
                      </v-col>
                    </v-row>
                  </v-card-text>
                </v-card>

              </v-row>
              <v-row>
                <v-card outlined min-width="100%">
                  <v-card-subtitle>Galileo
                  </v-card-subtitle>
                  <v-card-text>
                    <v-text-field v-model="editedItem.utenteGalileo"
                                  label="Utente galileo (max 10 caratteri) usato per utente ultima manutenzione"></v-text-field>
                  </v-card-text>
                </v-card>

              </v-row>
              <v-row>
                <v-card outlined min-width="100%">
                  <v-card-subtitle>Informazioni per utenti esterni(fornitori)
                  </v-card-subtitle>
                  <v-card-text>
                    <v-row>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.codiceFornitore"
                                      label="Codice fornitore"></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.descrizioneAzienda"
                                      label="Azienda"></v-text-field>
                      </v-col>
                    </v-row>
                  </v-card-text>
                </v-card>

              </v-row>
              <v-row>
                <v-col cols="12">
                  <v-text-field v-model="editedItem.note"
                                label="Note"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialogUtenteVisible=false">Cancel</v-btn>
            <v-btn color="blue darken-1" text @click="salvaUtente">Save</v-btn>
          </v-card-actions>
        </v-card>

      </v-dialog>
    </v-form>

    <v-dialog v-model="isShowConfermaEliminazioneDialog" max-width="500">
      <v-card>
        <v-card-title class="headline">Eliminare l'utente?</v-card-title>
        <v-card-text>
          Attenzione! l'operazione non è reversibile
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="isShowConfermaEliminazioneDialog = false">
            Cancel
          </v-btn>
          <v-btn color="primary" @click="deleteUtente" :loading="isLoadingEliminaUtente">
            Ok
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-form ref="formReimpostaPassword" v-model="formReimpostaPasswordValid">
      <v-dialog v-model="isShowReimpostaPasswordDialog" max-width="500">
        <v-card>
          <v-card-title class="headline">Reimpostare la password per l'utente?</v-card-title>
          <v-card-text>
            <v-col cols="12" sm="12" md="6">
              <v-text-field v-model="passwordReimpostata" type="password"
                            label="Nuova password"></v-text-field>
            </v-col>
            <v-col cols="12" sm="12" md="6">
              <v-text-field v-model="passwordReimpostataCheck" type="password"
                            label="Conferma nuova password"
              ></v-text-field>
            </v-col>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="isShowReimpostaPasswordDialog = false">
              Cancel
            </v-btn>
            <v-btn color="primary" @click="reimpostaPassword" :loading="isLoadingReimpostaPassword">
              Ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-form>

  </div>
</template>


<script>

import apiAdmin from '../../web-api/apiAdmin.js'
import globalMixins from "../../utils/globalMixins";
import {validationMixin} from 'vuelidate'


export default {
  components: {},
  mixins: [globalMixins, validationMixin],
  validations: {
    /*      name: { required, maxLength: maxLength(10) },
          email: { required, email },
          select: { required },
          checkbox: {
            checked (val) {
              return val
            },
          },*/
  },
  data() {
    return {
      //local data
      users: [],
      userFeatures: [],
      features: [],
      editedItem: null,

      //window functioning
      headers: [
        {text: 'utente', value: 'username'},
        {text: 'Nome', value: 'nome'},
        {text: 'Cognome', value: 'cognome'},
        {text: 'Stato', value: 'stato'},
        {text: 'Ultimo accesso', value: 'lastLogin'},
        {text: 'Azioni', value: 'actions', sortable: false},
      ],
      isLoaded: false,
      tab: 0,
      dialogUtenteVisible: false,
      search: null,


      //forms
      formUserValid: null,


      //eliminazione utente
      isShowConfermaEliminazioneDialog: false,
      isLoadingEliminaUtente: false,
      utenteInEliminazione: null,

      //reimpostazione password
      formReimpostaPasswordValid: null,
      isShowReimpostaPasswordDialog: false,
      isLoadingReimpostaPassword: false,
      utenteInReimpostazionePassword: null,
      passwordReimpostata: null,
      passwordReimpostataCheck: null


    }
  },
  created() {
    this.getAll();

  },
  computed: {
    rulesPassword() {
      const rules = [];
      /*        if (this.editedItem!=null) {
                const rule =
                  v => ((this.editedItem.password!=null && this.editedItem.password.trim().length!=0) && ((v || '').length <= 0 && this.editedItem.ssoEnabled!==true)) ||
                    `Password obbligatoria se SSO è disabilitato`;
                rules.push(rule)
              }*/
      rules.push(v => !!v || 'Campo obbligatorio');
      return rules
    },
    rulesPasswordCheck() {
      const rules = [];
      let myThis = this;
      if (this.editedItem != null) {
        const rule =
            v => ((!!v && v) === myThis.editedItem.newPassword) ||
                'La password non coincide';
        rules.push(rule);
      }
      rules.push(v => !!v || 'Campo obbligatorio');
      return rules
    },
  },
  watch: {},
  methods: {
    getAll: function () {
      this.isLoading = true;
      apiAdmin.methods.getUsers().then(function (response) {
        this.users = response.data;
        this.isLoading = false;
        this.isLoaded = true;
      }.bind(this)).catch(function () {
        this.isLoading = false;
      }.bind(this));
    },
    nuovoUtente: function () {
      this.editedItem = {
        operationType: 'NEW',
        enabled: true
      };
      this.$refs.formUtente.resetValidation();
      this.dialogUtenteVisible = true;
    },
    editUtente: function (item) {
      //recupero l'utente
      this.isLoading = true;

      apiAdmin.methods.getUser(item.username).then(function (response) {
        this.editedItem = response.data;
        this.dialogUtenteVisible = true;
        this.isLoading = false;
      }.bind(this)).catch(function () {
        this.isLoading = false;
      }.bind(this));


    },
    deleteUtenteInit(item) {
      this.utenteInEliminazione = item;
      this.isShowConfermaEliminazioneDialog = true;
    },
    deleteUtente() {
      this.isLoadingEliminaUtente = true;
      apiAdmin.methods.deleteUser(this.utenteInEliminazione).then(function () {
        this.isShowConfermaEliminazioneDialog = false;
        this.refreshUtenteLite();
        this.isLoadingEliminaUtente = false;
      }.bind(this)).catch(function () {
        this.isLoadingEliminaUtente = false;
      }.bind(this));
    },
    refreshUtenteLite() {
      /*        apiAdmin.methods.getUsers().then(function (response) {
              }.bind(this));*/
      this.getAll();
    },
    salvaUtente() {
      if (!this.$refs.formUtente.validate()) {
        return;
      }
      if (this.editedItem.operationType === 'NEW') {

        apiAdmin.methods.createUser(this.editedItem).then(function () {
          let username = this.editedItem.username;
          this.editedItem = null;
          this.dialogUtenteVisible = false;
          this.refreshUtenteLite(username);
          this.isLoading = false;
          this.$emit('new-user');
        }.bind(this)).catch(function () {
          this.isLoading = false;
        }.bind(this));

      } else {

        if (this.editedItem.codiceFornitore != null && this.editedItem.codiceFornitore.trim() === '') {
          this.editedItem.codiceFornitore = null;
        }
        apiAdmin.methods.saveUser(this.editedItem).then(function () {
          let username = this.editedItem.username;
          this.editedItem = null;
          this.dialogUtenteVisible = false;
          this.refreshUtenteLite(username);
          this.isLoading = false;
        }.bind(this)).catch(function () {
          this.isLoading = false;
        }.bind(this));

      }

    },
    reimpostaPasswordInit(item) {
      this.utenteInReimpostazionePassword = item;
      this.passwordReimpostata = null;
      this.passwordReimpostataCheck = null;
      this.isShowReimpostaPasswordDialog = true;
    },
    reimpostaPassword() {
      this.isLoadingReimpostaPassword = true;
      let changePasswordDto = {
        username: this.utenteInReimpostazionePassword.username,
        password: this.passwordReimpostata
      }
      apiAdmin.methods.reimpostaPassword(changePasswordDto).then(function () {
        this.isShowReimpostaPasswordDialog = false;
        this.isLoadingReimpostaPassword = false;
      }.bind(this)).catch(function () {
        this.isLoadingReimpostaPassword = false;
      }.bind(this));
    },
  }

}

</script>
