angular.module("sms-server").controller("TestController", function ($http, $scope, RestURLFactory) {
    $http.get(RestURLFactory.USERS).then(function (response) {
        $scope.users = response.data;
    })
});