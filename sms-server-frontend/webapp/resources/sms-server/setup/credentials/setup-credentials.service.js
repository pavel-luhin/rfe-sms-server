(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('setupCredentialsService', setupCredentialsService);

  /** @ngInject */
  function setupCredentialsService(RestURLFactory, $http) {
    return {
      getUserCredentials: getUserCredentials,
      removeCredentials: removeCredentials
    };

    function getUserCredentials() {
      return $http.get(RestURLFactory.CREDENTIALS);
    }

    function removeCredentials(id) {
      return $http.delete(RestURLFactory.CREDENTIALS + '/' + id);
    }
  }
})();