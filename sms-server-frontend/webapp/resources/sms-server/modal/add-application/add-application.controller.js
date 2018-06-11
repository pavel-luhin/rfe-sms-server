(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addApplicationCtrl', addApplicationCtrl);

  /** @ngInject */
  function addApplicationCtrl(addApplicationService, $scope, $uibModalInstance,
      toaster) {
    $scope.selected = {};
    $scope.selected.credentials = {};

    $scope.addApplication = function (app) {
      if (!app || !app.applicationName) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'Please, fill application name.',
          timeout: 0
        });

        return;
      }

      app.credentialsSenderName = $scope.selected.credentials.sender;
      addApplicationService.addNewApplication(app).then(function (data) {
        $uibModalInstance.dismiss();
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    };

    addApplicationService.getUserCredentials().then(function (response) {
      $scope.userCredentials = response.data;
    });
  }
})();