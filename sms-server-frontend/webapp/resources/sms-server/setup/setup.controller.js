(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('setupCtrl', setupCtrl);

  /** @ngInject */
  function setupCtrl($scope, $location) {
    $scope.openSetup = function (uri) {
      $location.path(uri);
    }
  }

})();