(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('applicationCtrl', applicationCtrl);

  /** @ngInject */
  function applicationCtrl(applicationService, $scope, loginService) {
    $scope.$watch(loginService.isAuthenticated, function () {
      $scope.authenticated = loginService.isAuthenticated();
    });

    $scope.versionLoaded = false;

    var confirmLogoutModalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Log Out',
      headerText: 'Confirm logout?',
      bodyText: 'Are you sure you want to log out?'
    };

    $scope.logout = function () {
      loginService.logout();
    };

    $scope.getCurrentUserName = function () {
      return loginService.getCurrentUserName();
    };

    $scope.getVersionInfo = function () {
      applicationService.getVersionInfo()
      .then(function (response) {
        $scope.version = response.data;
        $scope.versionLoaded = true;
      })
    };
  }
})();