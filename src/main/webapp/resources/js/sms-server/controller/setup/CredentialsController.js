angular.module('sms-server').controller('CredentialsController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal', '$rootScope', 'ConfirmDeleteModalService',
        function ($scope, $http, RestURLFactory, $uibModal, $rootScope, ConfirmDeleteModalService) {

            var getUserCredentials = function () {
                $http.get(RestURLFactory.CREDENTIALS).then(function (data) {
                    $scope.userCredentials = data.data;
                });
            };

            getUserCredentials();

            $scope.addCredentialsModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewCredentials-modal.html',
                    controller: 'AddNewCredentialsModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getUserCredentials();
                });
            };

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete Credentials?',
                bodyText: 'Are you sure you want to delete this credentials?'
            };

            $scope.removeCredentials = function (id) {
                ConfirmDeleteModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    $http.delete(RestURLFactory.CREDENTIALS + '/' + id).then(function (data) {
                        getUserCredentials();
                    })
                });
            };

            $scope.shareCredentials = function (id) {
                $rootScope.shareCredentialsId = id;
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/shareCredentials-modal.html',
                    controller: 'ShareCredentialsModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getUserCredentials();
                });
            };
        }
    ]
);
