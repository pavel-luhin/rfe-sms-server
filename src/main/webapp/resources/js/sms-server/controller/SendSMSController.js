angular.module('sms-server').controller('SendSMSController', ['$scope', '$http', 'RestURLFactory',
    function ($scope, $http, RestURLFactory) {
        var defaultSymbolsLeft = 160;
        $scope.symbolsLeft = defaultSymbolsLeft;
        $scope.smsCount = 1;

        $scope.messages = [];

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
            if (isSMSValid(smsObject)) {
                $scope.error = undefined;
            }

            smsObject.recipientType = 'NUMBER';

            var sms = createBaseSMS();
            sms.recipients[smsObject.recipient] = smsObject.recipientType;
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
    }
]);