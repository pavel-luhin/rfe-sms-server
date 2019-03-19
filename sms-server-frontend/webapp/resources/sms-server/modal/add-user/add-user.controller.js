(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addUserCtrl', addUserCtrl);

  /** @ngInject */
  function addUserCtrl(addUserService, $scope, $uibModalInstance, toaster) {
    $scope.addUser = function (user) {
      if (!user || !user.username) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'Please, enter valid username.',
          timeout: 0
        });

        return;
      }

      addUserService.addNewUser(user).then(function (data) {
        $uibModalInstance.dismiss();
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    }
  }
})();