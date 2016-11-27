angular.module('sms-server').controller('ApplicationController',
    ['$scope', 'AuthenticationProvider', '$location', '$http', 'RestURLFactory',
        function ($scope, AuthenticationProvider, $location, $http, RestURLFactory) {
            $scope.$watch(AuthenticationProvider.isAuthenticated, function () {
                $scope.authenticated = AuthenticationProvider.isAuthenticated();
            });

            $scope.versionLoaded = false;

            $scope.logout = function () {
                AuthenticationProvider.logout();
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