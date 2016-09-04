angular.module('sms-server').controller('ApplicationController',
    ['$scope', 'AuthenticationProvider', '$location',
        function ($scope, AuthenticationProvider, $location) {
            $scope.$watch(AuthenticationProvider.isAuthenticated, function () {
                $scope.authenticated = AuthenticationProvider.isAuthenticated();
            });

            $scope.logout = function () {
                AuthenticationProvider.logout();
            };

            $scope.getCurrentUserName = function () {
                return AuthenticationProvider.getCurrentUserName();
            };
        }
    ]
);