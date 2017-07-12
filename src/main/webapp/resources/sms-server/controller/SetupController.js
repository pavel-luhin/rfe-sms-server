angular.module('sms-server').controller('SetupController',
    ['$scope', '$location',
        function ($scope, $location) {
            $scope.openSetup = function (uri) {
                $location.path(uri);
            }
        }
    ]
);