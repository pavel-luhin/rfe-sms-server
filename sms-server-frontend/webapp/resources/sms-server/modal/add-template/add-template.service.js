(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('addTemplateService', addTemplateService);

  /** @ngInject */
  function addTemplateService($http, RestURLFactory) {
    return {
      addTemplate: addTemplate
    };

    function addTemplate(template) {
      return $http.post(RestURLFactory.SMS_TEMPLATE, template);
    }
  }
})();