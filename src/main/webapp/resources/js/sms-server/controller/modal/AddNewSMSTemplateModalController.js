angular.module('sms-server').controller('AddNewSMSTemplateModalController',
    ['$scope', '$http', 'RestURLFactory', '$uibModalInstance', 'toaster',
        function ($scope, $http, RestURLFactory, $uibModalInstance, toaster) {
            $scope.addTemplate = function (template) {
                console.log("lalka");
                if (!template || !template.smsType || !template.uriPath) {

                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Please, fill all required fields with correct data.',
                        timeout: 0
                    });

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