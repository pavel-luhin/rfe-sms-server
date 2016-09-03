angular.module('sms-server').controller('TemplateController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal',
        function ($scope, $http, RestURLFactory, $uibModal) {

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

            $scope.removeSMSTemplate = function (id) {
                $http.delete(RestURLFactory.SMS_TEMPLATE + '/' + id).then(function (data) {
                    getSMSTemplates();
                })
            };
        }
    ]
);
