// Routing to the Views
angular.module('collaboApp').config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpPovider) {
	$routeProvider
	.when('/login', {
		templateUrl : 'app/components/login/views/loginView.html',
		controller : 'LoginController'
	})
	.when('/issues', {
		templateUrl : 'app/components/issues/views/issuesView.html',
		controller : 'IssuesController'
	})
	.when('/designDecisions', {
		templateUrl : 'app/components/designdecisions/views/designDecisionsView.html',
		controller : 'DesignDecisionsController'
	})
	.otherwise({
		redirectTo : "/issues"
	});
	
	$httpPovider.defaults.headers.common.Accept = '*/*';
	$httpPovider.interceptors.push('AuthInterceptor');
	
	
} ]);