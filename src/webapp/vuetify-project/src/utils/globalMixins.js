import moment from "moment";
import {GlobalConstants} from '../utils/GlobalConstants.js'
import store from '../store' // vuex store instance

export default {
  methods: {

    cloneObject: function (obj) {
      return JSON.parse(JSON.stringify(obj));
    },
    copyObjectProperties: function (dst, src) {
      for (var k in src) {
        if (Object.prototype.hasOwnProperty.call(src, k)) {
          dst[k] = src[k];
        }
      }
      return dst;
    },
    removeObjectProperties: function (obj) {
      for (var k in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, k)) {
          delete obj[k];
        }
      }
      return obj;
    },
    isNullOrEmpty: function (val) {
      return (val == null || (val.trim != null && val.trim() == ''));
    },
    trimNullToEmpty: function (val) {
      return (val == null) ? '' : val;
    },
    isoDate2jsDate: function (dateAsString) {
      let dateParts = dateAsString.split("-");
      let cellDate = new Date(Number(dateParts[0]), Number(dateParts[1]) - 1, Number(dateParts[2]));
      return cellDate;
    },
    todayIsoDate: function () {
      return moment().format("YYYY-MM-DD");
    },
    todayIsoDateTime: function () {
      return moment().format("YYYY-MM-DD HH:mm");
    },
    momentToIsoDate: function (m) {
      return m.format("YYYY-MM-DD");
    },
    isoDateAddDays: function (isoDate, numDays) {
      if (isoDate == null) {
        return isoDate;
      }
      return moment(isoDate).add(numDays, 'd').format("YYYY-MM-DD");
    },
    convertArrayToObject: function (array, key) {
      const initialValue = {};
      return array.reduce((obj, item) => {
        return {
          ...obj,
          [item[key]]: item,
        };
      }, initialValue);
    },
    formatDate(value) {
      if (value) {
        if (value instanceof moment) {
          return (value).format('DD/MM/YYYY')
        } else {
          return moment(String(value)).format('DD/MM/YYYY')
        }
      }
    },
    todayIso() {
      return moment().format(GlobalConstants.FORMATO_DATA_REST);
    },
    isoDateToLocaleDate: function () {
      return moment().format("YYYY-MM-DD");
    },
    isoDateToMonthDay: function (isoDate) {
      return moment(isoDate).format("DD/MM");
    },

    roundTo2Decimals(number) {
      return Math.round((number + Number.EPSILON) * 100) / 100
    },
    formatNumber(number) {
      if (number == null) {
        return '';
      }
      return number.toLocaleString();
    },

  /*  dateFormatter: function (params) {
      if (params.value == null) {
        return '';
      }
      return moment(params.value).format('DD/MM/YYYY');//TODO: usare locale!
    },
    dateFormatLocaleString: function () {
      return 'DD/MM/YYYY';//TODO: usare locale da user preferences
    },
    dateTimeFormatter(params) {
      if (params.value == null) {
        return '';
      }
      return moment(params.value).format('DD/MM/YYYY HH:mm:ss');
    },
    numberFormatter: function (params) {
      if (params.value == null) {
        return null;
      }
      return params.value.toLocaleString();
    },
    numberWithSignFormatter: function (params) {
      if (params.value == null) {
        return null;
      }
      if (params.value > 0) {
        return "+" + params.value.toLocaleString();
      }
      return params.value.toLocaleString();
    },
    getDataInizioSettimanaIso(dataIso) {
      //indietreggio la dataDa fino a lunedì
      let dataInizio = moment(dataIso);
      while (dataInizio.isoWeekday() > 1) {
        dataInizio.subtract(1, 'days');
      }
      return dataInizio.format("YYYY-MM-DD");
    },
    getDataFineSettimanaIso(dataIso) {
      //avanzo la dataA fino a Domenica
      let dataFine = moment(dataIso);
      while (dataFine.isoWeekday() < 7) {
        dataFine.add(1, 'days');
      }
      return dataFine.format("YYYY-MM-DD");
    },
    isEmptyString(variable) {
      if (typeof variable === 'string' || variable instanceof String) {
        if (variable.trim() === '') {
          return true;
        }
      }
      return false;
    },
    countKeys(obj) {
      return Object.keys(obj).length;
    },
    findIndexOfItemInListByPropertyValue(list,field,value){
      for(let n=0;n<list.length;n++){
        if(list[n][field]===value){
          return n;
        }
      }
      return -1;
    }*/

  }
}
