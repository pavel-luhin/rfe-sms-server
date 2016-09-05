angular.module('sms-server').controller('ApplicationsController',
    ['$scope', '$http', 'RestURLFactory', '$uibModal',
        function ($scope, $http, RestURLFactory, $uibModal) {

            var getAllApplications = function () {
                $http.get(RestURLFactory.APPLICATION).then(function (data) {
                    $scope.applications = data.data;
                });
            };

            getAllApplications();

            $scope.addApplicationModal = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'resources/templates/modal/addNewApplication-modal.html',
                    controller: 'AddNewApplicationModalController',
                    size: 'lg'
                }).closed.then(function () {
                    getAllApplications();
                });
            };

            $scope.removeApplication = function (id) {
                $http.delete(RestURLFactory.APPLICATION + '/' + id).then(function (data) {
                    getAllApplications();
                })
            };
        }
    ]
);
