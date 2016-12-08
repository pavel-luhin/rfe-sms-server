angular.module('sms-server').controller('EmailTemplateController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal', 'ConfirmModalService',
        function ($scope, $http, RestURLFactory, $uibModal, ConfirmModalService) {

            var getEmailTemplates = function () {
                $http.get(RestURLFactory.EMAIL_TEMPLATE).then(function (data) {
                    $scope.emailTemplates = data.data;
                });
            };

            getEmailTemplates();

            $scope.addEmailTemplateModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewEmailTemplate-modal.html',
                    controller: 'AddNewEmailTemplateModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getEmailTemplates();
                });
            };

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete Email Template?',
                bodyText: 'Are you sure you want to delete this email template?'
            };

            $scope.removeEmailTemplate = function (id) {
                ConfirmModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    $http.delete(RestURLFactory.EMAIL_TEMPLATE + '/' + id).then(function (data) {
                        getEmailTemplates();
                    })
                });
            };
        }
    ]
);
