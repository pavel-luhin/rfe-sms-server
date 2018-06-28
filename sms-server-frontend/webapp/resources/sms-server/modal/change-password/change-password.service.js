(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('changePasswordService', changePasswordService);

  /** @ngInject */
  function changePasswordService($http, RestURLFactory) {
    return {
      changePassword: changePassword
    };

    function changePassword(password) {
      return $http.post(RestURLFactory.CHANGE_PASSWORD, password);
    }
  }
})();