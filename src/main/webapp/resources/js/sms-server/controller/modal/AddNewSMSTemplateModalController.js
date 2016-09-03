angular.module('sms-server').controller('AddNewSMSTemplateModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance',
        function ($scope, $http, RestURLFactory, $uibModalInstance) {
            $scope.addTemplate = function (template) {
                console.log("lalka");
                if (!template || !template.smsType || !template.uriPath) {
                    $scope.error = "Please, fill all required fields with correct data.";
                    return;
                }

                $http.post(RestURLFactory.SMS_TEMPLATE, template).then(function (data) {
                    $uibModalInstance.dismiss();
                });
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss();
            }
        }
    ]
);