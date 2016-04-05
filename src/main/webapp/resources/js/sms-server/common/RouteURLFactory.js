angular.module('sms-server').factory('RouteURLFactory',
    function () {
        return {
            LOGIN: '/login',
            STATISTICS: '/statistics',
            SEND_SMS: '/send-sms',
            RECIPIENTS: '/recipients',
            ADD_RECIPIENTS: '/recipients/add',
            ADD_RECIPIENT_GROUP: '/recipients/add-group'
        }
    }
);