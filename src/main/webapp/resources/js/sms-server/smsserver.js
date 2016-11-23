var application = angular.module('sms-server',
                                 ['ngRoute',
                                  'ngCookies',
                                  'ngProgress',
                                  'ngStorage',
                                  'ngTagsInput',
                                  'ui.bootstrap',
                                  'ui.bootstrap.tpls',
                                  'ui.bootstrap.tooltip',
                                  'ngSanitize',
                                  'ui.select',
                                  'angular-md5'
                                 ]);

application.config(['$httpProvider',
    function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    }
]);

application.run(
    ['$cookies',
        function ($cookies) {
            if (!$cookies.get('auth_token')) {
                if (localStorage.getItem('authentication')) {
                    var auth = JSON.parse(localStorage.getItem('authentication'));
                    $cookies.put('auth_token', auth.token);
                }
            }
        }
    ]
);

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
                templateUrl: BASE_TEMPLATE_LOCATION + 'recipients.html'
            })
            .when('/recipients/add', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewRecipient.html'
            })
            .when('/recipients/add-group', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewGroup.html'
            })
            .when('/recipients/edit-group/:groupId', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewGroup.html'
            })
            .when('/setup', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup.html',
                controller: 'SetupController'
            })
            .when('/setup/templates', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/templates.html',
                controller: 'TemplateController'
            })
            .when('/setup/credentials', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/credentials.html',
                controller: 'CredentialsController'
            })
            .when('/setup/users', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/users.html',
                controller: 'UsersController'
            })
            .when('/setup/applications', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/applications.html',
                controller: 'ApplicationsController'
            })
            .when('/setup/emailTemplates', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/emailTemplates.html',
                controller: 'EmailTemplateController'
            })
            .when('/test', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'test.html',
                controller: 'TestController'
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