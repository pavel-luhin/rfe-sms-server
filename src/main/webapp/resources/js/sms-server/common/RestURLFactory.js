angular.module('sms-server').factory('RestURLFactory',
    function () {
        var BASE_REST_PREFIX = '/rest';
        return {
            SEND_CUSTOM_SMS: BASE_REST_PREFIX + '/sms/custom',
            SEND_BULK_SMS: BASE_REST_PREFIX + '/bulkSMS',
            GET_FULL_STATISTICS: BASE_REST_PREFIX + '/statistics',
            ADD_RECIPIENT: BASE_REST_PREFIX + '/recipient/add/recipients',
            GET_ALL_RECIPIENTS: BASE_REST_PREFIX + '/recipient/all',
            LOGOUT: BASE_REST_PREFIX + '/user/logout',
            AUTHENTICATE: BASE_REST_PREFIX + '/user/authenticate',
            GET_SENDER_NAMES: BASE_REST_PREFIX + '/user/senderNames',
            CREDENTIALS: BASE_REST_PREFIX + "/setup/credentials",
            SMS_TEMPLATE: BASE_REST_PREFIX + "/setup/smsTemplate",
            FIND_TEMPLATE: BASE_REST_PREFIX + "/sms/template",
            USERS: BASE_REST_PREFIX + "/setup/user",
            APPLICATION: BASE_REST_PREFIX + "/setup/application",
            EMAIL_TEMPLATE: BASE_REST_PREFIX + "/setup/emailTemplate",
            SHARE_CREDENTIALS: BASE_REST_PREFIX + "/setup/shareCredentials"
        }
    }
);