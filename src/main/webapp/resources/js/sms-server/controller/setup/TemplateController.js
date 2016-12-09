angular.module('sms-server').controller('TemplateController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal', 'ConfirmModalService',
        function ($scope, $http, RestURLFactory, $uibModal, ConfirmModalService) {

            var getSMSTemplates = function () {
                $http.get(RestURLFactory.SMS_TEMPLATE).then(function (data) {
                    $scope.smsTemplates = data.data;
                });
            };

            getSMSTemplates();

            $scope.addSMSTemplateModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewSMSTemplate-modal.html',
                    controller: 'AddNewSMSTemplateModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getSMSTemplates();
                });
            };

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete SMS Template?',
                bodyText: 'Are you sure you want to delete this sms template?'
            };

	        var confirmDeleteEmailTemplateModalOptions = {
		        closeButtonText: 'Cancel',
		        actionButtonText: 'Delete',
		        headerText: 'Delete Email Template?',
		        bodyText: 'If you will remove this SMS template, email template will be removed too. Are you wish to continue?'
	        };

            $scope.removeSMSTemplate = function (id) {
                ConfirmModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    ConfirmModalService.showModal({}, confirmDeleteEmailTemplateModalOptions).then(function (result) {
	                    $http.delete(RestURLFactory.SMS_TEMPLATE + '/' + id).then(function (data) {
		                    getSMSTemplates();
	                    })
                    });
                });
            };
        }
    ]
);
