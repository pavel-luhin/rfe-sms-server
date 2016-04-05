angular.module('sms-server').controller('AuthenticationController',
    ['$scope', 'AuthenticationProvider', '$cookies', '$location', '$http', 'ngProgressFactory', 'RestURLFactory',
    function ($scope, AuthenticationProvider, $cookies, $location, $http, ngProgressFactory, RestURLFactory) {
        var progress = ngProgressFactory.createInstance();
        progress.setColor('#00e6e6');
        $scope.login = function (user) {
            progress.start();
            $http.post(RestURLFactory.AUTHENTICATE, user)
                .success(function (data) {
                    AuthenticationProvider.setAuthentication(data);
                    $location.path('/statistics');
                    progress.complete();
                })
                .error(function() {
                    $scope.error = 'Unable to authenticate. Please, check your login and password.';
                    progress.complete();
                })
        }
    }
]);