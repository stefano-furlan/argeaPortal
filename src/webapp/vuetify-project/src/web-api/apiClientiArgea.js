import axios from 'axios'

let BASEPATH = 'md/';

export default {

  methods: {
    getCompanies: function () {
      let url = '/rs/'+ BASEPATH + 'ubicazioni';
      return axios
        .get(url)
    },
    getClientiArgea: function () {
      let url = '/rs/'+ BASEPATH + 'clienti-argea';
      return axios
        .get(url)
    },


  }

}

