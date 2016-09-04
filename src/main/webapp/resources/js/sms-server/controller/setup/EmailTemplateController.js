angular.module('sms-server').controller('EmailTemplateController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal',
        function ($scope, $http, RestURLFactory, $uibModal) {

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

            $scope.removeEmailTemplate = function (id) {
                $http.delete(RestURLFactory.EMAIL_TEMPLATE + '/' + id).then(function (data) {
                    getEmailTemplates();
                })
            };
        }
    ]
);
