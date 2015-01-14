angular.module('collaboApp').controller('LoginController', ['Auth', '$location', '$scope', function(Auth, $location, $scope) {
	$scope.login = function(email, password) {
		Auth.setCredentials(email, password);
		$location.path("/dashboard");
	}
}]);