var collaboControllers = angular.module('collaboControllers', []);


collaboControllers.controller('LoginController', ['Auth', '$location', '$scope', function(Auth, $location, $scope) {
	$scope.login = function(email, password) {
		Auth.setCredentials(email, password);
		$location.path("/issues");
	}
}]);

collaboControllers.controller('IssuesController', ['$http', '$scope', '$cookieStore', function($http, $scope, $cookieStore) {
	$http.get("/api/issues").success(function(data) {
		$scope.issues = angular.fromJson(data);
	});
}]);