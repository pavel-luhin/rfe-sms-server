(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('groupsCtrl', groupsCtrl);

    groupsCtrl.$inject = ['$scope', '$http', '$location', 'RestURLFactory', '$routeParams', 'toaster'];
    function groupsCtrl($scope, $http, $location, RestURLFactory, $routeParams, toaster) {

    }
})();