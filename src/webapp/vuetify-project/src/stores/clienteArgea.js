import {defineStore} from 'pinia'

export const useClienteArgeaStore = defineStore('clienteArgea', {
  state: () => {
    return { clienteArgea: null }
  },
  // could also be defined as
  // state: () => ({ count: 0 })
  actions: {
    clear() {
      this.clienteArgea=null;
    },
  },
})
