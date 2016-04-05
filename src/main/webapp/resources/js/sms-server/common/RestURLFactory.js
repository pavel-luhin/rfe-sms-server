angular.module('sms-server').factory('RestURLFactory',
    function () {
        var BASE_REST_PREFIX = '/rest';
        return {
            SEND_CUSTOM_SMS: BASE_REST_PREFIX + '/sms/custom',
            GET_FULL_STATISTICS: BASE_REST_PREFIX + '/statistics',
            ADD_RECIPIENT: BASE_REST_PREFIX + '/recipient/add/recipients',
            GET_ALL_RECIPIENTS: BASE_REST_PREFIX + '/recipient/all',
            LOGOUT: BASE_REST_PREFIX + '/user/logout',
            AUTHENTICATE: BASE_REST_PREFIX + '/user/authenticate'
        }
    }
);