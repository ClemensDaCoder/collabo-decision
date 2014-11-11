var collaboApp = angular.module('collaboApp', [ 'ngRoute', 'ngCookies',
		'collaboControllers', 'ui.bootstrap' ]);

// Routing to the Views
collaboApp.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpPovider) {
	$routeProvider
	.when('/login', {
		templateUrl : 'partials/login.html',
		controller : 'LoginController'
	})
	.when('/issues', {
		templateUrl : 'partials/issues.html',
		controller : 'IssuesController'
	})
	.when('/designDecisions', {
		templateUrl : 'partials/designDecisions.html',
		controller : 'DesignDecisionsController'
	})
	.when('/statistics', {
		templateUrl : 'partials/statistics.html',
		controller : 'StatisticsController'
	})
	.otherwise({
		redirectTo : "/issues"
	});
	
	$httpPovider.defaults.headers.common.Accept = '*/*';
	$httpPovider.interceptors.push('AuthInterceptor');
	
	
} ]);

/**
 * Authentication Hook / Interceptor: Adding the Authorization Header to the request (from cookie)
 * If Response is 401 - Unauthorized: Redirect to login
 */
collaboApp.factory('AuthInterceptor', ['$cookieStore', '$q', '$location', function($cookieStore, $q, $location) {
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


/**
 * Base 64 encryption and decryption
 */
collaboApp
		.factory(
				'Base64',
				function() {
					var keyStr = 'ABCDEFGHIJKLMNOP' + 'QRSTUVWXYZabcdef'
							+ 'ghijklmnopqrstuv' + 'wxyz0123456789+/' + '=';
					return {
						encode : function(input) {
							var output = "";
							var chr1, chr2, chr3 = "";
							var enc1, enc2, enc3, enc4 = "";
							var i = 0;

							do {
								chr1 = input.charCodeAt(i++);
								chr2 = input.charCodeAt(i++);
								chr3 = input.charCodeAt(i++);

								enc1 = chr1 >> 2;
								enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
								enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
								enc4 = chr3 & 63;

								if (isNaN(chr2)) {
									enc3 = enc4 = 64;
								} else if (isNaN(chr3)) {
									enc4 = 64;
								}

								output = output + keyStr.charAt(enc1)
										+ keyStr.charAt(enc2)
										+ keyStr.charAt(enc3)
										+ keyStr.charAt(enc4);
								chr1 = chr2 = chr3 = "";
								enc1 = enc2 = enc3 = enc4 = "";
							} while (i < input.length);

							return output;
						},

						decode : function(input) {
							var output = "";
							var chr1, chr2, chr3 = "";
							var enc1, enc2, enc3, enc4 = "";
							var i = 0;

							// remove all characters that are not A-Z, a-z, 0-9,
							// +, /, or =
							var base64test = /[^A-Za-z0-9\+\/\=]/g;
							if (base64test.exec(input)) {
								alert("There were invalid base64 characters in the input text.\n"
										+ "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n"
										+ "Expect errors in decoding.");
							}
							input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

							do {
								enc1 = keyStr.indexOf(input.charAt(i++));
								enc2 = keyStr.indexOf(input.charAt(i++));
								enc3 = keyStr.indexOf(input.charAt(i++));
								enc4 = keyStr.indexOf(input.charAt(i++));

								chr1 = (enc1 << 2) | (enc2 >> 4);
								chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
								chr3 = ((enc3 & 3) << 6) | enc4;

								output = output + String.fromCharCode(chr1);

								if (enc3 != 64) {
									output = output + String.fromCharCode(chr2);
								}
								if (enc4 != 64) {
									output = output + String.fromCharCode(chr3);
								}

								chr1 = chr2 = chr3 = "";
								enc1 = enc2 = enc3 = enc4 = "";

							} while (i < input.length);

							return output;
						}
					};
				});

/**
 * Authorization Service - Set Credentials and clearCredentials
 */
collaboApp.factory('Auth', [
		'Base64',
		'$cookieStore',
		'$http',
		'$location',
		function(Base64, $cookieStore, $http, $location) {
			return {
				setCredentials : function(username, password) {
					var encoded = Base64.encode(username + ':' + password);
					$http.defaults.headers.common.Authorization = 'Basic '
							+ encoded;
					$cookieStore.put('authdata', encoded);
				},
				clearCredentials : function() {
					document.execCommand("ClearAuthenticationCache");
					$cookieStore.remove('authdata');
					$http.defaults.headers.common.Authorization = 'Basic ';
					$location.path("/login")
				}
			};
		} ]);