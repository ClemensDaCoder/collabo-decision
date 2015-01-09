angular.module('collaboApp').controller('DashboardController', ['$http', '$scope', function($http, $scope) {
	
	$scope.getOwningIssues = function() {
		
		var uri = "/rest/issues?owned=true";
		
		$http.get(uri).success(function(data) {
			$scope.issues = angular.fromJson(data);
		});
	};
	
	$scope.showIssue = function(idIssue) {
		var modalInstance = $modal.open({
			templateUrl : 'app/components/issues/views/issueDetailView.html',
			controller : 'IssueDetailViewController',
			backdrop : false,
			resolve : {
				id : function() {
					return idIssue;
				}
			}
		});
		
		modalInstance.result.then(function() {
			$scope.getOwningIssues();
		}, function() {
			$scope.getOwningIssues();
		});
	};
	
	$scope.getOwningIssues();
	
}]);