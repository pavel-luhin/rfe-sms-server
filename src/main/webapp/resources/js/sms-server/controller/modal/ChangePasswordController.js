angular.module('sms-server').controller('ChangePasswordController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'md5', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, md5, toaster) {

            $scope.selected = {};
            $scope.selected.credentials = {};

            $scope.changePassword = function (oldPassword, newPassword, anotherNewPassword) {
                if (!oldPassword || !newPassword || !anotherNewPassword || newPassword != anotherNewPassword) {

                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Please, fill all required fields with correct data.',
                        timeout: 0
                    });

                    return;
                }

                oldPassword = md5.createHash(oldPassword);
                newPassword = md5.createHash(newPassword);

                var passObj = {
                    oldPassword: oldPassword,
                    newPassword: newPassword
                };

                $http.post(RestURLFactory.CHANGE_PASSWORD, passObj)
                    .success(function (data) {
                        $uibModalInstance.dismiss();
                    })
                    .error(function (data) {
                        $scope.error = "Old password is invalid";
                    });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            };
        }
    ]
);