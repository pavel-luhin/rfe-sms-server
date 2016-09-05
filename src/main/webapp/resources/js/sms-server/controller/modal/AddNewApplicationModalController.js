angular.module('sms-server').controller('AddNewApplicationModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance',
        function ($scope, $http, RestURLFactory, $uibModalInstance) {

            $scope.selected = {};
            $scope.selected.credentials = {};

            $scope.addApplication = function (app) {
                if (!app || !app.applicationName) {
                    $scope.error = "Please, fill all required fields with correct data.";
                    return;
                }

                app.credentialsSenderName = $scope.selected.credentials.sender;
                $http.post(RestURLFactory.APPLICATION, app).then(function (data) {
                    $uibModalInstance.dismiss();
                });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            };

            $http.get(RestURLFactory.CREDENTIALS).then(function (response) {
                $scope.userCredentials = response.data;
            });
        }
    ]
);