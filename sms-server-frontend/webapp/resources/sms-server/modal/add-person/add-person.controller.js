(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addPersonCtrl', addPersonCtrl);

  /** @ngInject */
  function addPersonCtrl(addPersonService, $scope, $uibModalInstance) {
    $scope.saveRecipient = function (person) {
      addPersonService.addNewPerson(person).then(function (data) {
        $uibModalInstance.dismiss();
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    }
  }
})();