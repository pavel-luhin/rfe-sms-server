var application = angular.module('sms-server', ['ngRoute', 'ngCookies', 'ngProgress', 'ngStorage']);

application.config(['$httpProvider',
    function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    }
]);

application.config(['$routeProvider',
    function ($routeProvider) {
        var BASE_TEMPLATE_LOCATION = 'resources/templates/';
        $routeProvider
            .when('/statistics', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'statistics.html',
                controller: 'StatisticsController'
            })
            .when('/send-sms', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'sendSMS.html',
                controller: 'SendSMSController'
            })
            .when('/login', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'login.html',
                controller: 'AuthenticationController'
            })
            .when('/recipients', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'recipients.html',
                controller: 'RecipientsController'
            })
            .when('/recipients/add', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewRecipient.html',
                controller: 'RecipientsController'
            })
            .when('/recipients/add-group', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewGroup.html',
                controller: 'RecipientsController'
            })
            .otherwise({
                redirectTo: '/statistics'
            });
    }
]);

application.factory('myHttpInterceptor',
    function($q, $location) {
        return {
            'requestError': function (rejection) {
                if (canRecover(rejection)) {
                    return responseOrNewPromise;
                }
                return $q.reject(rejection);
            },

            'responseError': function (rejection) {
                if (rejection.status === 401) {
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        };
    }
);