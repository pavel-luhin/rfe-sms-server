(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('groupsService', groupsService);

    groupsService.$inject = ['RestURLFactory', '$http'];
    function groupsService(RestURLFactory, $http) {
        return {
            getGroup: getGroup,
            getPersonsWithoutGroup: getPersonsWithoutGroup,
            addGroup: addGroup
        };

        function getGroup(groupId) {
            return $http.get(RestURLFactory.GROUP + '/' + groupId);
        }

        function getPersonsWithoutGroup(groupId) {
            return $http.get(RestURLFactory.PERSONS_WITHOUT_GROUP + groupId);
        }

        function addGroup(group) {
            return  $http.post(RestURLFactory.GROUP, group);
        }
    }
})();