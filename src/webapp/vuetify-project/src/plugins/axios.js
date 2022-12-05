import axios from 'axios'
import {createToaster} from "@meforma/vue-toaster";

axios.interceptors.response.use(
  function (response) {
    /*    //effettuo il lancio dei messaggi contestuali server-side
        if (response != null && response.data != null && Object.prototype.hasOwnProperty.call(response.data, 'contextualMessages')) {
          if (response.data.contextualMessages != null && response.data.contextualMessages.length > 0) {
            //per il momento faccio una cosa semplice: concateno i messaggi e mando fuori una info
            var result = Object.keys(response.data.contextualMessages).map(function (k) {
              return response.data.contextualMessages[k].message
            }).join(",");
            //alert(result);
            EventBus.$emit('OPEN_TOASTER', result);
          }
          response.data = response.data.data;
        }*/


    return response;
  },
  function (error) {
    if (error.config.failSilently == true) {
      return Promise.reject(error);
    }
    const toaster = createToaster({position: 'bottom', type: 'error'});
    let message = 'Si è verificato un errore'
    if (error.response != null && error.response.data != null && error.response.data.message != null) {
      message = error.response.data.message;
    }
    toaster.show(message);

    /* // handle error
     if (error.config.failSilently == true) {
       return Promise.reject(error);
     }

     let message;
     let type;
     let title = 'Si è verificato un errore imprevisto';


     if (error.response != null && error.response.data != null) {
       let msg = null;
       if (error.response.data.message != null) {
         msg = error.response.data.message;
       } else {
         if (error.response.data.byteLength != null && error.response.data.byteLength > 0) {
           //se il valore di ritorno è un bytearray tento comunque di verificare che non sia un json sotto mentite spoglie
           var decodedString = String.fromCharCode.apply(null, new Uint8Array(error.response.data));
           var obj = JSON.parse(decodedString);
           msg = obj.message;
         } else {
           msg = error;
         }
       }
       message = msg;
       title = 'Errore';
       type = error.response.data.type;
     } else {
       message = error;
     }
     //Confirm.$emit("openMessage",);
     var params = {title: title, message: message, options: {color: 'red', mode: 'OKONLY'}};
     if (type == 'RECOVERABLE') {
       params.options.color = 'orange';
     }
     EventBus.$emit('OPEN_MODAL', params);
     //Confirm.methods.open(title, message, { color: 'red' ,mode:'OKONLY'});*/
    return Promise.reject(error);
  });


//gestione CSRF
const getCookies = () =>
  document.cookie.split(';').reduce((cookies, item) => {
    const [name, value] = item.split('=');
    cookies[name] = value;
    return cookies;
  }, {});
axios.defaults.headers.common['X-CSRF-TOKEN'] = getCookies()['XSRF-TOKEN'];
