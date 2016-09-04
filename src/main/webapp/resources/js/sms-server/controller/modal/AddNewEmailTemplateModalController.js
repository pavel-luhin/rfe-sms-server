angular.module('sms-server').controller('AddNewEmailTemplateModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance',
        function ($scope, $http, RestURLFactory, $uibModalInstance) {
            $scope.addTemplate = function (template) {
                if (!template || !template.subject || !template.content || !template.smsType) {
                    $scope.error = "Please, fill all required fields with correct data.";
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