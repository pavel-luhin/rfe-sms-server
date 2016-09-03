angular.module('sms-server').controller('AddNewCredentialsModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance',
        function ($scope, $http, RestURLFactory, $uibModalInstance) {
            $scope.addCredentials = function (credentials) {
                if (!credentials || !credentials.apiKey || !credentials.sender || !credentials.username) {
                    $scope.error = "Please, fill all required fields with correct data.";
                    return;
                }

                $http.post(RestURLFactory.CREDENTIALS, credentials).then(function (data) {
                    $uibModalInstance.dismiss();
                });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            }
        }
    ]
);