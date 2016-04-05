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

        $scope.sendSMS = function (sms) {
            if (isSMSValid(sms)) {
                $scope.error = undefined;
            }

            $scope.messages.push({
                recipient: sms.recipient,
                content: sms.content,
                recipientType: 'PERSON'
            });

            $http.post(RestURLFactory.SEND_CUSTOM_SMS, $scope.messages);
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
    }
]);