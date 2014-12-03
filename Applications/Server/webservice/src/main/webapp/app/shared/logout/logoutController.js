angular.module('collaboApp').controller('LogoutController', ['$scope', '$cookieStore', 'Auth', function($scope, $cookieStore, Auth) {
	$scope.logout = function() {
		Auth.clearCredentials();
	}
}]);