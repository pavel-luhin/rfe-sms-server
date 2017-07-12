angular.module('sms-server').controller('SmsServerPropertiesController',
    ['$scope', '$location', '$http', 'RestURLFactory', 'toaster',
        function ($scope, $location, $http, RestURLFactory, toaster) {
            var allProperties = [];

            $http.get(RestURLFactory.SMS_SERVER_PROPERTIES).then(function (response) {
                $scope.propertyGroups = [];

                for (var key in response.data) {
                    $scope.propertyGroups.push(key);
                }

                allProperties = response.data;

                $scope.selectedPropertyGroup = $scope.propertyGroups[0];
                $scope.selectGroup();
            });

            $scope.saveProperties = function (properties) {
                console.log(allProperties);
                console.log($scope.selectedPropertyGroup);
                allProperties[$scope.selectedPropertyGroup] = properties;
                $http.post(RestURLFactory.SMS_SERVER_PROPERTIES, allProperties).then(function (response) {
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
            };

            $scope.selectGroup = function () {
                var selectedGroup = $scope.selectedPropertyGroup;
                $scope.properties = allProperties[selectedGroup];
            }
        }
    ]
);