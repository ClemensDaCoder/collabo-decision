angular.module('collaboApp').controller('DashboardController', ['$http', '$scope', function($http, $scope) {
	
	$scope.getOwningIssues = function() {
		
		var uri = "/rest/issues?owned=true";
		
		$http.get(uri).success(function(data) {
			$scope.issues = angular.fromJson(data);
		});
	};
	
	$scope.getOwningIssues();
	
}]);