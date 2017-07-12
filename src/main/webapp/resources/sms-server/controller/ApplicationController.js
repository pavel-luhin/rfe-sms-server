angular.module('sms-server').controller('ApplicationController',
    ['$scope', 'AuthenticationProvider', '$location', '$http', 'RestURLFactory', 'ConfirmModalService',
        function ($scope, AuthenticationProvider, $location, $http, RestURLFactory, ConfirmModalService) {
            $scope.$watch(AuthenticationProvider.isAuthenticated, function () {
                $scope.authenticated = AuthenticationProvider.isAuthenticated();
            });

            $scope.versionLoaded = false;

            var confirmLogoutModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Log Out',
                headerText: 'Confirm logout?',
                bodyText: 'Are you sure you want to log out?'
            };

            $scope.logout = function () {
                ConfirmModalService.showModal({}, confirmLogoutModalOptions).then(function (result) {
                    AuthenticationProvider.logout();
                });
            };

            $scope.getCurrentUserName = function () {
                return AuthenticationProvider.getCurrentUserName();
            };

            $scope.getVersionInfo = function () {
                $http.get(RestURLFactory.VERSION)
                    .then(function (response) {
                        $scope.version = response.data;
                        $scope.versionLoaded = true;
                    })
            };
        }
    ]
);