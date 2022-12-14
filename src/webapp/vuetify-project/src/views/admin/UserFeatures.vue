<template>
  <div v-if="isLoaded">

      <v-toolbar flat>
        <v-icon>fa-filter</v-icon>
        <v-text-field label="Utente1" v-model="utente1" outlined dense hide-details class="field-max-width"></v-text-field>
        <v-text-field label="Utente2" v-model="utente2" outlined dense hide-details class="field-max-width"></v-text-field>
      </v-toolbar>
      <v-layout >

        <v-simple-table fixed-header dense height="75vh">
          <template v-slot:default>
            <thead>
            <tr>
              <th class="sticky-col first-col">Feature</th>
              <th class="sticky-col second-col"></th>
              <th v-for="user in users" :key="user.username"
                  :class="{'text-left':true, 'display-none':!((utente1==='' && utente2==='') || (utente1!=='' && user.username.includes(utente1)) || (utente2!=='' && user.username.includes(utente2)))}">
                {{ user.username }}
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(value, name) in mappa" :key="name">
              <td class="sticky-col first-col">{{ name }}</td>
              <td class="sticky-col second-col">{{ value.note }}</td>
              <td class="text-left" v-for="(v, n) in value.data" :key="n"
                  :class="{'display-none':!((utente1==='' && utente2==='') || (utente1!=='' && n.includes(utente1)) || (utente2!=='' && n.includes(utente2)))}">
                <!--<v-checkbox
                        v-model="mappa[name].data[n]"
                ></v-checkbox>-->
                <!--   {{name}}{{n}}-->
                <!-- {{mappa[name].data[n]}}-->
                <input type="checkbox" :checked="mappa[name].data[n]" @click="togglePermission(name,n)">
              </td>
            </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-layout>
  </div>
</template>


<script>

import apiAdmin from '../../web-api/apiAdmin.js'
import globalMixins from "../../utils/globalMixins";


export default {
  components: {},
  mixins: [globalMixins],
  data() {
    return {
      //local data
      users: [],
      userFeatures: [],
      features: [],
      mappa: {},
      utente1: '',
      utente2: '',

      //window functioning
      isLoaded: false,
      tab: 0,

    }
  },
  created() {
    this.getAll();

  },
  computed: {},
  watch: {},
  methods: {
    getAll: function () {
      this.isLoading = true;
      apiAdmin.methods.getUsersAndFeaturesAndUserFeatures().then(function (response) {
        this.users = response.data.users;
        this.features = response.data.features;
        this.userFeatures = response.data.userFeatures;
        this.postProcess();
        this.isLoading = false;
        this.isLoaded = true;
      }.bind(this)).catch(function () {
        this.isLoading = false;
      }.bind(this));
    },
    postProcess() {
      let map = {};
      for (let feature of this.features) {
        map[feature.id] = {note: feature.note, data: {}};
        for (let user of this.users) {
          map[feature.id].data[user.username] = false;
        }
      }
      for (let f of this.userFeatures) {
        map[f.feature].data[f.username] = f.enabled;
      }
      this.mappa = map;
    },
    togglePermission(feature, username) {
      let requestObj = {
        username: username,
        feature: feature,
        value: !this.mappa[feature].data[username]
      };
      apiAdmin.methods.setPermission(requestObj).then(function (response) {
        this.mappa[feature].data[username] = response.data.value;
      }.bind(this));
    },


  }

}

</script>
<style scoped>
.white-space-pre {
  white-space: pre;
}

.wrapper {
  position: relative;
  overflow: auto;
  border: 1px solid black;
  white-space: nowrap;
}

.sticky-col {
  position: -webkit-sticky;
  position: sticky;
  background-color: white;
}

.first-col {
  width: 200px;
  min-width: 200px;
  max-width: 200px;
  left: 0px;
}

.second-col {
  width: 250px;
  min-width: 250px;
  max-width: 250px;
  left: 200px;
}

.field-max-width{
  max-width: 200px;
}


</style>