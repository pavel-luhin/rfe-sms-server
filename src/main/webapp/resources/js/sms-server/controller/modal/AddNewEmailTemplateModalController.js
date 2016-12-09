angular.module('sms-server').controller('AddNewEmailTemplateModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, toaster) {

            $http.get(RestURLFactory.AVAILABLE_SMS_TEMPLATES).then(function (response) {
                $scope.smsTypes = response.data;
            });

            $scope.template = {};

            $scope.addTemplate = function (template) {
                if (!template || !template.subject || !template.content || !template.smsType) {

                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Please, fill all required fields with correct data.',
                        timeout: 0
                    });

                    return;
                }

                $http.post(RestURLFactory.EMAIL_TEMPLATE, template).then(function (data) {
                    $uibModalInstance.dismiss();
                });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            }
        }
    ]
);