(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('applicationService', applicationService);

  /** @ngInject */
  function applicationService(RestURLFactory, $http) {
    return {
      getVersionInfo: getVersionInfo
    };

    function getVersionInfo() {
      return $http.get(RestURLFactory.VERSION);
    }
  }
})();