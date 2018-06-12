(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('loginService', loginService);

  /** @ngInject */
  function loginService(RestURLFactory, $http, $rootScope, $cookies, $location,
      localStorageAuthName) {

    return {
      logIn: logIn,
      setAuthentication: setAuthentication,
      logout: logout,
      isAuthenticated: isAuthenticated,
      getCurrentUserName: getCurrentUserName,
      account: account
    };

    function logIn(user) {
      var data = 'j_username=' + encodeURIComponent(user.username) +
          '&j_password=' + encodeURIComponent(user.password);

      return $http.post(RestURLFactory.AUTHENTICATE, data, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });
    }

    function setAuthentication() {
      account();
    }

    function logout() {
      $http.post(RestURLFactory.LOGOUT, '').then(function (promise) {
        $rootScope.authenticated = false;
        localStorage.removeItem(localStorageAuthName);
        $location.path('/login');
      });
    }

    function isAuthenticated() {
      return $rootScope.authenticated;
    }

    function getCurrentUserName() {
      var auth = JSON.parse(localStorage.getItem(localStorageAuthName));
      return auth.username;
    }

    function account() {
      $http.get(RestURLFactory.ACCOUNT_INFO).then(
          function (promise) {
            $rootScope.authenticated = true;
            localStorage.setItem(localStorageAuthName,
                JSON.stringify(promise.data));
            console.log(promise.data);
          }, function (reject) {
            $rootScope.authenticated = false;
            $location.path('/login');
          });
    }
  }

})();