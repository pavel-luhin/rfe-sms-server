angular.module('sms-server').controller('AddOrEditGroupController', ['$scope', '$http', '$location', 'RestURLFactory', '$routeParams',
    function ($scope, $http, $location, RestURLFactory, $routeParams) {

        $scope.receivedPersons = [];
        $scope.selectedPersons = [];

        $scope.getAllPersons = function () {
            $http.get(RestURLFactory.ALL_PERSONS).then(function (response) {
                $scope.receivedPersons = response.data;
            });
        };

        $scope.getGroup = function (groupId) {
            $http.get(RestURLFactory.GROUP + '/' + groupId)
                .then(function (response) {
                    $scope.selectedPersons = response.data.persons;
                    $scope.groupName = response.data.name;
                });
        };

        $scope.getPersonsWithoutGroup = function (groupId) {
            $http.get(RestURLFactory.PERSONS_WITHOUT_GROUP + groupId)
                .then(function (response) {
                    $scope.receivedPersons = response.data;
                });
        };

        $scope.selectPerson = function (index) {
            $scope.selectedPersons.push($scope.receivedPersons[index]);
            $scope.receivedPersons.splice(index, 1);
        };

        $scope.unselectPerson = function (index) {
            $scope.receivedPersons.push($scope.selectedPersons[index]);
            $scope.selectedPersons.splice(index, 1);
        };

        if ($routeParams.groupId === undefined) {
            $scope.getAllPersons();
            $scope.title = "Add new recipients group";
        } else {
            $scope.title = "Edit recipients group";
            var groupId = $routeParams.groupId;
            $scope.getGroup(groupId);
            $scope.getPersonsWithoutGroup(groupId);
        }

        $scope.saveGroup = function () {
            var groupToSave = {};

            if ($routeParams.groupId != undefined) {
                groupToSave.id = $routeParams.groupId;
            }

            groupToSave.persons = $scope.selectedPersons;
            groupToSave.name = $scope.groupName;

            $http.post(RestURLFactory.GROUP, groupToSave).then(function () {
                $scope.selectedPersons = [];
                $location.path('/recipients');
                $scope.getAllPersons();
            })
        };
    }
]);