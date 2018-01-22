(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('personsService', personsService);

    personsService.$inject = ['RestURLFactory', '$http'];
    function personsService(RestURLFactory, $http) {
        return {
            removePerson: removePerson,
            getPersons: getPersons,
            addPersons: addPersons
        };

        function removePerson(id) {
            return $http.delete(RestURLFactory.PERSONS + '?personId=' + id);
        }

        function getPersons(skip, currentPageSize, sortField, sortDirection) {
            return $http.get(RestURLFactory.PERSONS +
                '?skip=' + skip + '&offset=' + currentPageSize.value + '&sortField=' + sortField + '&sortDirection=' + sortDirection);
        }

        function addPersons(persons) {
            return $http.post(RestURLFactory.PERSONS, recipients);
        }
    }
})();