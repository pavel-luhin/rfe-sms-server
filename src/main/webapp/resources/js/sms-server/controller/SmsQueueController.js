angular.module('sms-server').controller('SmsQueueController',
    ['$scope', '$location', '$http', 'RestURLFactory', 'ConfirmModalService',
        function ($scope, $location, $http, RestURLFactory, ConfirmModalService) {

            var getAllMessagesFromQueue = function () {
                $http.get(RestURLFactory.SMS_QUEUE).then(function (response) {
                    $scope.queue = response.data;
                });
            };

            getAllMessagesFromQueue();

            var confirmDeleteModalOptions = {
                closeButtonText: 'Cancel',
                actionButtonText: 'Delete',
                headerText: 'Delete Sms From Queue?',
                bodyText: 'Are you sure you want to delete this sms from queue?'
            };

            $scope.removeFromQueue = function (id) {
                ConfirmModalService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                    $http.delete(RestURLFactory.SMS_QUEUE + '?id=' + id).then(function (response) {
                        getAllMessagesFromQueue();
                    })
                });
            }
        }
    ]
);