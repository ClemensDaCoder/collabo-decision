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
	.when('/dashboard', {
		templateUrl : 'app/components/dashboard/views/dashboardView.html',
		controller : 'DashboardController'
	})
	.otherwise({
		redirectTo : "/dashboard"
	});
	
	$httpPovider.defaults.headers.common.Accept = '*/*';
	$httpPovider.interceptors.push('AuthInterceptor');
	
	
} ]);