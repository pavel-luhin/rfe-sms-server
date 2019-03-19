(function () {
  'use strict';

  angular
  .module('sms-server')
  .config(config);

  /** @ngInject */
  function config($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
  }
})();