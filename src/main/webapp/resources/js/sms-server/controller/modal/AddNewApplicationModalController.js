angular.module('sms-server').controller('AddNewApplicationModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, toaster) {

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