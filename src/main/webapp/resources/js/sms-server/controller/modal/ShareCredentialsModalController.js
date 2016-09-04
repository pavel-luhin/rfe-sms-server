angular.module('sms-server').controller('ShareCredentialsModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', '$rootScope',
        function ($scope, $http, RestURLFactory, $uibModalInstance, $rootScope) {

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            };

            $http.get(RestURLFactory.USERS).then(function (response) {
                $scope.users = response.data;
            });

            $scope.selected = {};
            $scope.selected.user = {};

            $scope.share = function () {
                var credentialsId = $rootScope.shareCredentialsId;

                var request = {
                    credentialsId: credentialsId,
                    userId: $scope.selected.user.id
                };

                $http.post(RestURLFactory.SHARE_CREDENTIALS, request).then(function (data) {
                    $uibModalInstance.dismiss();
                })
            };
        }
    ]
);