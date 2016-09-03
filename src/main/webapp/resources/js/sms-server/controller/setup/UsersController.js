angular.module('sms-server').controller('UsersController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal',
        function ($scope, $http, RestURLFactory, $uibModal) {

            var getUsers = function () {
                $http.get(RestURLFactory.USERS).then(function (data) {
                    $scope.users = data.data;
                });
            };

            getUsers();

            $scope.addUserModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewUser-modal.html',
                    controller: 'AddNewUserModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getUsers();
                });
            };

            $scope.removeUser = function (id) {
                $http.delete(RestURLFactory.USERS + '/' + id).then(function (data) {
                    getUsers();
                })
            };
        }
    ]
);
