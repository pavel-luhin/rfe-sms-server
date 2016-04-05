angular.module('sms-server').controller('RecipientsController', ['$scope', '$http', '$location', 'RestURLFactory',
    function ($scope, $http, $location, RestURLFactory) {

        var recipientsCount = 0;
        $scope.recipients = [];
        var defaultRecipientForm = {
            name: null,
            email: null,
            phoneNumber: null,
            recipientNumber: recipientsCount
        };

        var allRecipients = [];

        $scope.addNewRecipientForm = function () {
            $scope.recipients.push(angular.copy(defaultRecipientForm));
            recipientsCount++;
        };

        $scope.removeRecipientForm = function (index) {
            $scope.recipients.splice(index, 1);
        };

        $scope.addRecipients = function (recipients) {
            $http.post(RestURLFactory.ADD_RECIPIENT, recipients);
            $location.path('/recipients')
        };

        $scope.getAllRecipientsAndGroups = function () {
            $http.get(RestURLFactory.GET_ALL_RECIPIENTS)
                .success(function (data) {
                    $scope.receivedRecipients = data;
                    allRecipients = data;
                    console.log(data);
                });
        };
        
        $scope.getAllRecipientsAndGroups();

        $scope.addNewRecipientForm();

        $scope.$watch('recipientsFilter', function (newVlaue) {
            console.log(newVlaue);
            if (newVlaue != undefined) {
                $scope.receivedRecipients = [];
                if (newVlaue === '') {
                    $scope.receivedRecipients = allRecipients;
                } else {
                    for (var i = 0; i < allRecipients.length; i++) {
                        if (allRecipients[i].firstName.toLowerCase().startsWith(newVlaue.toLowerCase())) {
                            $scope.receivedRecipients.push(allRecipients[i]);
                        }
                        if (allRecipients[i].lastName.toLowerCase().startsWith(newVlaue.toLowerCase())) {
                            $scope.receivedRecipients.push(allRecipients[i]);
                        }
                        if (allRecipients[i].phoneNumber.toLowerCase().startsWith(newVlaue.toLowerCase())) {
                            $scope.receivedRecipients.push(allRecipients[i]);
                        }
                    }
                }
            }
        });
    }
]);