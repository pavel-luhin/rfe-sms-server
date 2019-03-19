(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('recipientsCtrl', recipientsCtrl);

  /** @ngInject */
  function recipientsCtrl($scope, $location, $uibModal) {

    $scope.addPersonModal = function () {
      $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'modal/add-person/add-person.tmpl.html',
        controller: 'addPersonCtrl',
        size: 'lg'
      }).closed.then(function () {
        $scope.getAllPersons();
      });
    };

  }
})();