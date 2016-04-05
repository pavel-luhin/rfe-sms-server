angular.module('sms-server').factory('AuthenticationProvider', ['$rootScope', '$cookies', '$http', 'RestURLFactory', '$location',
    function ($rootScope, $cookies, $http, RestURLFactory, $location) {

        var cookieName = 'auth_token';
        var localStorageAuthName = 'authentication';

        return {
            setAuthentication: function(authentication) {
                $cookies.put(cookieName, authentication.token);
                console.log(authentication.token);
                $rootScope.authenticated = true;
                authentication.authenticated = true;
                localStorage.setItem(localStorageAuthName, JSON.stringify(authentication));
            },

            logout: function () {
                $http.post(RestURLFactory.LOGOUT, '').then(function (promise) {
                    $cookies.remove(cookieName);
                    $rootScope.authenticated = false;
                    localStorage.removeItem(localStorageAuthName);
                    $location.path('/login');
                });
            },

            isAuthenticated: function() {
                if ($rootScope.authenticated != undefined) {
                    return $rootScope.authenticated;
                } else {
                    var auth = JSON.parse(localStorage.getItem(localStorageAuthName));
                    if (auth != undefined) {
                        return auth.authenticated;
                    } else {
                        return false;
                    }
                }
            }
        }
    }
]);