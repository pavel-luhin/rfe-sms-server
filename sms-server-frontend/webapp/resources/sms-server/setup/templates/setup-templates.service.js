(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('setupTemplatesService', setupTemplatesService);

  /** @ngInject */
  function setupTemplatesService($http, RestURLFactory) {
    return {
      getAllTemplates: getAllTemplates,
      removeTemplate: removeTemplate
    };

    function getAllTemplates() {
      return $http.get(RestURLFactory.SMS_TEMPLATE);
    }

    function removeTemplate(id) {
      return $http.delete(RestURLFactory.SMS_TEMPLATE + '/' + id);
    }
  }
})();