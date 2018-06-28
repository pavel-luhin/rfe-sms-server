(function () {
  'use strict';

  angular
  .module('sms-server')
  .run(run);

  /** @ngInject */
  function run(loginService) {
    loginService.account();
  }
})();