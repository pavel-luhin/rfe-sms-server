(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('addUserService', addUserService);

  /** @ngInject */
  function addUserService($http, RestURLFactory) {
    return {
      addNewUser: addNewUser
    };

    function addNewUser(user) {
      return $http.post(RestURLFactory.USERS, user);
    }
  }
})();