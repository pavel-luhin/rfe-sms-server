(function () {
    'use strict';

    angular
        .module('sms-server')
        .config(routes);

    routes.$inject = ['$routeProvider'];
    function routes($routeProvider) {
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
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewGroup.html',
                controller: 'AddOrEditGroupController'
            })
            .when('/recipients/edit-group/:groupId', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'addNewGroup.html',
                controller: 'AddOrEditGroupController'
            })
            .when('/setup', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup.html',
                controller: 'SetupController'
            })
            .when('/setup/templates', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-templates.tmpl.html',
                controller: 'TemplateController'
            })
            .when('/setup/credentials', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-credentials.tmpl.html',
                controller: 'CredentialsController'
            })
            .when('/setup/users', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-users.tmpl.html',
                controller: 'UsersController'
            })
            .when('/setup/applications', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-applications.tmpl.html',
                controller: 'ApplicationsController'
            })
            .when('/setup/emailTemplates', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-emailTemplates.tmpl.html',
                controller: 'EmailTemplateController'
            })
            .when('/setup/properties', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'setup/setup-properties.tmpl.html',
                controller: 'SmsServerPropertiesController'
            })
            .when('/sms-queue', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'smsQueue.html',
                controller: 'SmsQueueController'
            })
            .when('/test', {
                templateUrl: BASE_TEMPLATE_LOCATION + 'test.html',
                controller: 'TestController'
            })
            .otherwise({
                redirectTo: '/statistics'
            });
    }
})();