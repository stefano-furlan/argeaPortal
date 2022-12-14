import axios from 'axios'
import {GlobalConstants} from '../utils/GlobalConstants.js'

export default {

  methods: {
    invalidateCaches() {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/invalidate-caches';
      return axios
        .post(url);
    },
    getUsers() {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/users';
      return axios
        .get(url);
    },
    getFeatures() {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/features';
      return axios
        .get(url);
    },
    getUserFeatures() {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/users/features';
      return axios
        .get(url);
    },
    getUser(username) {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/user';
      return axios
        .get(url,{
          params:{username:username}
        });
    },
    getUsersAndFeaturesAndUserFeatures: function () {
      return axios.all([this.getUsers(), this.getFeatures(), this.getUserFeatures()])
        .then(axios.spread((users, features, userFeatures) => {
          return {
            data: {
              users: users.data,
              features: features.data,
              userFeatures: userFeatures.data,
            }
          }
        }))
    },
    saveUser:function(utente){
      let url = GlobalConstants.REST_BASE_PATH + 'admin/user';
      return axios
        .put(url,utente);
    },
    createUser:function(utente){
      let url = GlobalConstants.REST_BASE_PATH + 'admin/user';
      return axios
        .post(url,utente);
    },
    deleteUser:function(utente){
      let url = GlobalConstants.REST_BASE_PATH + 'admin/user';
      return axios
        .delete(url,{data:utente});
    },
    reimpostaPassword:function(utente){
      let url = GlobalConstants.REST_BASE_PATH + 'admin/change-password';
      return axios
        .put(url,utente);
    },
    setPermission:function(featureObj){
      let url = GlobalConstants.REST_BASE_PATH + 'admin/user/feature';
      return axios
        .put(url,featureObj);
    },
    test() {
      let url = GlobalConstants.REST_BASE_PATH + 'admin/test';
      return axios
        .get(url);
    }


  }


}

