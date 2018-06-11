(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('smsQueueService', smsQueueService);

  /** @ngInject */
  function smsQueueService(RestURLFactory, $http) {
    return {
      getAllMessagesInQueue: getAllMessagesInQueue,
      removeFromQueue: removeFromQueue
    };

    function getAllMessagesInQueue() {
      return $http.get(RestURLFactory.SMS_QUEUE);
    }

    function removeFromQueue(id) {
      return $http.delete(RestURLFactory.SMS_QUEUE + '?id=' + id);
    }
  }
})();