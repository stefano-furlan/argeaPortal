<template>
  <div>
    <h1>ADMIN UNDER CONSTRUCTION</h1>
    <div>{{data}}</div>
  </div>
</template>
<script>


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



    },


  }
}
</script>
<style>

</style>
