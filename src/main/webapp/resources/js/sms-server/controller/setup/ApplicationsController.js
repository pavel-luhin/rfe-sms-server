angular.module('sms-server').controller('ApplicationsController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal', 'ConfirmDeleteModalService',
        function ($scope, $http, RestURLFactory, $uibModal, ConfirmDeleteModalService) {

            var getAllApplications = function () {
                $http.get(RestURLFactory.APPLICATION).then(function (data) {
                    $scope.applications = data.data;
                });
            };

            getAllApplications();

            $scope.addApplicationModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewApplication-modal.html',
                    controller: 'AddNewApplicationModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getAllApplications();
                });
            };

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete Application?',
                bodyText: 'Are you sure you want to delete this application?'
            };

            $scope.removeApplication = function (id) {
                ConfirmDeleteModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    $http.delete(RestURLFactory.APPLICATION + '/' + id).then(function (data) {
                        getAllApplications();
                    })
                });
            };
        }
    ]
);
