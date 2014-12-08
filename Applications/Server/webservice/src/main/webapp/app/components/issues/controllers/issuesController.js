angular.module('collaboApp').controller('IssuesController', ['$http', '$scope', '$modal', function($http, $scope, $modal) {
	
	$scope.getIssues = function(status) {
		
		var uri = "/rest/issues";
		
		if(status != null) {
			$scope.currentTab = status;
			uri += "?status=" + status;
		} else {
			$scope.currentTab = "ALL";
		}
		
		$http.get(uri).success(function(data) {
			$scope.issues = angular.fromJson(data);
		});
	}
	
	$scope.showIssue = function(issue) {
		alert(issue.title);
	}
	
	$scope.newIssue = function() {
		var modalInstance = $modal.open({
			templateUrl : 'app/components/issues/views/newIssueView.html',
			controller : 'NewIssueController',
			backdrop : false
		});
		
		modalInstance.result.then(function() {
			$scope.getIssues('NEW')
		}, function() {
			$scope.getIssues('NEW');
		});
	}
	
	//TODO: move to issue detail view
	$scope.createDesignDecision = function(idIssue){
		var modalInstance = $modal.open({
			templateUrl : 'app/components/designdecisions/views/newDesignDecision.html',
			controller : 'NewDesignDecisionController',
			resolve: {
				idIssue : function() {
					return idIssue;
				}
			}
		});
	};
	
	// On load show all
	$scope.getIssues(null);
}]);