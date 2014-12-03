/**
 * Authentication Hook / Interceptor: Adding the Authorization Header to the request (from cookie)
 * If Response is 401 - Unauthorized: Redirect to login
 */
angular.module('collaboApp').factory('AuthInterceptor', ['$cookieStore', '$q', '$location', function($cookieStore, $q, $location) {
	return {
		request: function(config) {
            config.headers = config.headers || {};
            if(typeof $cookieStore.get('authdata') != 'undefined') {
            	config.headers.Authorization = 'Basic ' + $cookieStore.get('authdata')
            }
            return config || $q.when(config);
        },
        response: function(response) {
            if (response.status === 401) {
            	$location.path("/login");
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
        	if(rejection.status === 401) {
        		$location.path("/login");
        	}
        	return $q.reject(rejection);
        }
	};
}]);

