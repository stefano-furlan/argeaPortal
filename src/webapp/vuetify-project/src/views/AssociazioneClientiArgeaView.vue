<template>
  <div>

    ESPLORAZIONE CLIENTI COMPANY/ARGEA



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

      searchField: null,

      //DATA

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

      this.getData();
    },
    getData() {

      this.isLoading = true;


      let params = {};

    /*  apiClientiArgea.methods.getClientiCompany(params).then(function (response) {
        this.isLoading = false;
        this.clientiCompany = response.data;
      }.bind(this));*/


    },

  }
}
</script>
<style scoped>

</style>
