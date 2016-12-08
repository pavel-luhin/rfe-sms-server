angular.module('sms-server').controller('UsersController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal', 'ConfirmModalService',
        function ($scope, $http, RestURLFactory, $uibModal, ConfirmModalService) {

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

            $scope.changePasswordModal = function () {
                $uibModal.open({
                                   animation: true,
                                   ariaLabelledBy: 'modal-title',
                                   ariaDescribedBy: 'modal-body',
                                   templateUrl: 'resources/templates/modal/changePassword-modal.html',
                                   controller: 'ChangePasswordController',
                                   size: 'lg'
                               });
            };

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete User?',
                bodyText: 'Are you sure you want to delete this user?'
            };

            $scope.removeUser = function (id) {
                ConfirmModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    $http.delete(RestURLFactory.USERS + '/' + id).then(function (data) {
                        getUsers();
                    })
                });
            };
        }
    ]
);
