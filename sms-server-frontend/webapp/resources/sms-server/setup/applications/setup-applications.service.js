(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('setupApplicationsService', setupApplicationsService);

  /** @ngInject */
  function setupApplicationsService($http, RestURLFactory) {
    return {
      getApplications: getApplications,
      removeApplication: removeApplication
    };

    function getApplications() {
      return $http.get(RestURLFactory.APPLICATION);
    }

    function removeApplication(id) {
      return $http.delete(RestURLFactory.APPLICATION + '/' + id);
    }
  }
})();