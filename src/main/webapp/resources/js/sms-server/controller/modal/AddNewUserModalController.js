angular.module('sms-server').controller('AddNewUserModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance',
        function ($scope, $http, RestURLFactory, $uibModalInstance) {
            $scope.addUser = function (user) {
                if (!user || !user.username) {
                    $scope.error = "Please, fill all required fields with correct data.";
                    return;
                }

                $http.post(RestURLFactory.USERS, user).then(function (data) {
                    $uibModalInstance.dismiss();
                });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            }
        }
    ]
);