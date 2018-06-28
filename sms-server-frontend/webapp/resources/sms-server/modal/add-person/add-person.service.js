(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('addPersonService', addPersonService);

  /** @ngInject */
  function addPersonService($http, RestURLFactory) {
    return {
      addNewPerson: addNewPerson
    };

    function addNewPerson(person) {
      return $http.post(RestURLFactory.PERSONS, person);
    }
  }
})();