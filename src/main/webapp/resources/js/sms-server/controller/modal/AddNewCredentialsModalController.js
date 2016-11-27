angular.module('sms-server').controller('AddNewCredentialsModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, toaster) {
            $scope.addCredentials = function (credentials) {
                if (!credentials || !credentials.apiKey || !credentials.sender || !credentials.username) {

                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Please, fill all required fields with correct data.',
                        timeout: 0
                    });

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