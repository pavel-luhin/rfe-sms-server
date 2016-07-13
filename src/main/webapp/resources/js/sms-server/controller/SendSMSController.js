angular.module('sms-server').controller('SendSMSController', ['$scope', '$http', 'RestURLFactory',
    function ($scope, $http, RestURLFactory) {
        var defaultSymbolsLeft = 160;
        $scope.symbolsLeft = defaultSymbolsLeft;
        $scope.smsCount = 1;

        $scope.messages = [];

        $scope.loading = true;

        $scope.countSymbols = function () {
            recalculateSymbols();
            if ($scope.symbolsLeft <= 0) {
                $scope.smsCount++;
                recalculateSymbols();
            }

            if ($scope.symbolsLeft > defaultSymbolsLeft) {
                $scope.smsCount--;
                recalculateSymbols();
            }
        };

        $scope.sendSMS = function (smsObject) {
            $scope.loading = true;
            if (isSMSValid(smsObject)) {
                $scope.error = undefined;
            }

            console.log(smsObject);
            
            var sms = createBaseSMS();
            
            for (var obj in smsObject.recipient) {
                if (smsObject.recipient[obj].recipientType === undefined) {
                    sms.recipients[smsObject.recipient[obj].name] = 'NUMBER';
                } else {
                    sms.recipients[smsObject.recipient[obj].name] = smsObject.recipient[obj].recipientType;
                }                
            }
            
            sms.smsContent = smsObject.content;

            $http.post(RestURLFactory.SEND_CUSTOM_SMS, sms)
                .then(function (data) {
                    console.log(data.data.success == true);
                    if (data.data.success == true) {
                        alert("SMS sent successfully");
                    } else {
                        alert("Some error occured");
                    }
                    $scope.messages = [];
                    smsObject.recipient = '';
                    smsObject.content = '';
                    sms = null;
                    $scope.loading = false;
                });
        };

        $scope.sendBulkSMS = function () {
            $scope.loading = true;
            var file = $scope.myFile;
            var sameForAll = $scope.sameForAll;
            if (sameForAll === undefined) {
                sameForAll = false;
            }
            console.log(file);
            var fd = new FormData();
            fd.append('file', file);
            fd.append('sameContentForAll', sameForAll);

            $http.post(RestURLFactory.SEND_BULK_SMS, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (data) {
                $scope.loading = false;
            });
        };

        function isSMSValid(sms) {
            if (!sms || !sms.recipient) {
                $scope.error = 'You must select at least one recipient or recipients group';
                return false;
            }
            if (!sms.content) {
                $scope.error = 'You must enter message';
                return false;
            }
            return true;
        }

        function recalculateSymbols() {
            $scope.symbolsLeft = defaultSymbolsLeft * $scope.smsCount - $scope.sms.content.length;
        }

        function createBaseSMS() {
            var sms = {};
            sms.recipients = {};
            sms.smsParameters = {};
            sms.duplicateEmail = false;
            sms.smsContent = "";
            return sms;
        }
        
        $scope.loadRecipients = function (query) {
            return $http.get(RestURLFactory.GET_ALL_RECIPIENTS)
                .then(function (response) {
                    var recipients = response.data;
                    return recipients;
                })
        }
    }
]);

angular.module('sms-server').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);