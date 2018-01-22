(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('httpInterceptor', httpInterceptor);

    httpInterceptor.$inject = ['$q', '$location', 'toaster'];
    function httpInterceptor($q, $location, toaster) {
        return {
            requestError: requestError,
            responseError: responseError
        };

        function requestError(rejection) {
            if (canRecover(rejection)) {
                return responseOrNewPromise;
            }
            return $q.reject(rejection);
        }

        function responseError(rejection) {
            if (rejection.status === 401) {
                $location.path('/login');
            }

            if (rejection.status === 500) {
                var body = rejection.data;
                var messageObject = JSON.parse(body);
                toaster.pop({
                    type: 'error',
                    title: 'Error',
                    body: messageObject.message,
                    timeout: 0
                });
            }

            return $q.reject(rejection);
        }
    }
})();