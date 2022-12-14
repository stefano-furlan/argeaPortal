<template>
    <div id="AdminPanel">

        <v-container class="scrollable-view">



                <v-tabs
                        v-model="tab"
                >
                    <v-tabs-slider></v-tabs-slider>

                    <v-tab :key="1">
                        UTENTI
                    </v-tab>
                    <v-tab :key="2">
                        PERMESSI
                    </v-tab>
                    <v-tab :key="3">
                        VARIE
                    </v-tab>


                    <v-tabs-items v-model="tab">

                        <v-tab-item
                                :key="1"
                                transition="fade-transition"
                                reverse-transition="fade-transition"
                        >
                            <v-card flat max-width="1000px" class="spaced-card">
<!--                               <v-toolbar dense flat dark color="primary">
                                    <v-toolbar-title>UTENTI</v-toolbar-title>
                                </v-toolbar>-->
                                <v-card-text>
                                    <users @newUser="refreshUsers"></users>
                                </v-card-text>
                            </v-card>
                        </v-tab-item>

                        <v-tab-item
                                :key="2"
                                transition="fade-transition"
                                reverse-transition="fade-transition"
                        >
                            <v-card flat class="spaced-card">
<!--                                <v-toolbar dense flat dark color="green">
                                    <v-toolbar-title>PERMESSI</v-toolbar-title>
                                </v-toolbar>-->

                                <v-card-text>
                                    <user-features v-if="isFeaturesShown"></user-features>
                                </v-card-text>
                            </v-card>
                        </v-tab-item>

                        <v-tab-item
                                :key="3"
                                transition="fade-transition"
                                reverse-transition="fade-transition"
                        >
                            <v-card flat max-width="1000px" class="spaced-card">
<!--                                <v-toolbar dense flat dark color="grey">
                                    <v-toolbar-title>VARIE</v-toolbar-title>
                                </v-toolbar>-->

                                <v-card-text>
                                    <v-card>
                                        <v-card-title>

                                            Refresh Caches Applicativo

                                        </v-card-title>
                                        <v-card-subtitle>

                                        </v-card-subtitle>
                                        <v-card-actions>
                                            <v-btn @click="invalidateCaches">Invalidate caches</v-btn>
                                        </v-card-actions>


                                        <v-card>
                                            <v-card-title>

                                                Diagnostic

                                            </v-card-title>
                                            <v-card-subtitle>

                                            </v-card-subtitle>
                                            <v-card-actions>
                                                <v-btn @click="executeTest">Test BE</v-btn>
                                                <v-btn @click="executeTestFrontend">Test FE</v-btn>
                                            </v-card-actions>


                                        </v-card>

                                    </v-card>
                                </v-card-text>
                            </v-card>

                        </v-tab-item>

                    </v-tabs-items>
                </v-tabs>







        </v-container>


    </div>
</template>

<script>


  import globalMixins from "../utils/globalMixins";
  import apiAdmin from "../web-api/apiAdmin";
  import UserFeatures from "./UserFeatures";
  import Users from "./Users";

  export default {
    components: {UserFeatures,Users},
    mixins: [globalMixins],
    data() {
      return {
        cliente: null,
        codiceCliente: null,
        urlDownloadModuloOrdineCliente: null,
        errorMessage: null,

        tab:0,
        isFeaturesShown:true,
      }
    },
    created() {

    },
    computed: {},
    watch: {},
    methods: {
      invalidateCaches() {
        apiAdmin.methods.invalidateCaches().then(function () {
          this.$toast("caches invalidate", {
            color: 'green',
            dismissable: true,
            queueable: true,
            showClose: true,
            closeIcon: 'fa-times',
          });
        }.bind(this));
      },
      executeTest() {
        apiAdmin.methods.test().then(function (response) {
          this.$toast("test eseguito, risposta: "+response.data, {
            color: 'green',
            dismissable: true,
            queueable: true,
            showClose: true,
            closeIcon: 'fa-times',
          });
        }.bind(this));
      },
      executeTestFrontend(){
/*        if ('serviceWorker' in navigator) {
          navigator.serviceWorker.register('/service-worker.js')
            .then(initialiseState);
        } else {
          console.warn('Service workers aren\'t supported in this browser.');
        }*/

        // Let's check if the browser supports notifications
        if (!("Notification" in window)) {
          alert("This browser does not support desktop notification");
        }

        // Let's check whether notification permissions have already been granted
        else if (Notification.permission === "granted") {
          // If it's okay let's create a notification
          /*var notification = */new Notification("Questa è una notifica push: ciao!");
        }

        // Otherwise, we need to ask the user for permission
        else if (Notification.permission !== "denied") {
          Notification.requestPermission().then(function (permission) {
            // If the user accepts, let's create a notification
            if (permission === "granted") {
              /*var notification = */new Notification("Questa è una notifica push: ciao!");

            }
          });
        }

        // At last, if the user has denied notifications, and you
        // want to be respectful there is no need to bother them any more.
      },
      refreshUsers(){
        this.isFeaturesShown=false;
        setTimeout(function(){
          this.isFeaturesShown=true;
        }.bind(this),50)
      }
    }
  }
</script>
<style>

</style>
