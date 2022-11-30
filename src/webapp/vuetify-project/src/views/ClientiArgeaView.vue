<template>
  <div>
    <v-card elevation="0" height="400px">

      <h1>clienti argea</h1>
      <v-table density="compact">
        <thead>
        <tr>
          <th class="text-left">
            ID
          </th>
          <th class="text-left">
            Descrizione
          </th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr
          v-for="item in data"
          :key="item.id"
        >
          <td>{{ item.id }}</td>
          <td>{{ item.descrizione }}</td>
          <td>

          </td>
        </tr>
        </tbody>
      </v-table>
    </v-card>

  </div>
</template>
<script>
import apiClientiArgea from "../web-api/apiClientiArgea";

export default {
  //components: { InterventoManutenzioneEditor},
  //mixins: [globalMixins, agGridMixins],
  data() {
    return {

      //window workings
      isLoading: false,

      //DATA
      data: null,
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


  }
}
</script>
<style>

</style>
