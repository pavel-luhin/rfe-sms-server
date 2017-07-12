angular.module('sms-server').controller('AddNewUserModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, toaster) {
            $scope.addUser = function (user) {
                if (!user || !user.username) {

                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Please, enter valid username.',
                        timeout: 0
                    });

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