angular.module('sms-server').controller('SmsServerPropertiesController',
    ['$scope', '$location', '$http', 'RestURLFactory', 'toaster',
        function ($scope, $location, $http, RestURLFactory, toaster) {
            $http.get(RestURLFactory.SMS_SERVER_PROPERTIES).then(function (response) {
                $scope.properties = response.data;
            });

            $scope.saveProperties = function (properties) {
                $http.post(RestURLFactory.SMS_SERVER_PROPERTIES, properties).then(function (response) {
                    toaster.pop({
                        type: 'success',
                        title: 'Success',
                        body: 'Properties Saved',
                        timeout: 0
                    });
                });
            };

            $scope.print = function (property) {
                console.log(property);
            }
        }
    ]
);