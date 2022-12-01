import axios from 'axios'

let BASEPATH = 'md/';

export default {

  methods: {
    getCompanies: function () {
      let url = '/rs/' + BASEPATH + 'company';
      return axios
        .get(url)
    },
    getClientiArgea: function () {
      let url = '/rs/' + BASEPATH + 'clienti-argea';
      return axios
        .get(url)
    },
    getClientiCompany: function () {
      let url = '/rs/' + BASEPATH + 'clienti-company';
      return axios
        .get(url)
    },
    getClienteArgea: function (params) {
      let url = '/rs/' + BASEPATH + 'cliente-argea';
      return axios
        .get(url, {
          params: params
        })
    },
    salvaClienteArgea: function (clienteArgea) {
      let url = '/rs/' + BASEPATH + 'cliente-argea';
      return axios
        .put(url, clienteArgea)
    }

  }

}

