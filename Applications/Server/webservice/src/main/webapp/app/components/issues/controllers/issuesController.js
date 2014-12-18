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
			$scope.issuesResponse = angular.fromJson(data);
		});
	}
	
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
			$scope.getIssues(null)
		}, function() {
			$scope.getIssues(null);
		});
	}
	
	$scope.newIssue = function() {
		var modalInstance = $modal.open({
			templateUrl : 'app/components/issues/views/issueDetailView.html',
			controller : 'IssueDetailViewController',
			backdrop : false,
			resolve : {
				id : function() {
					return null;
				}
			}
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
			templateUrl : 'app/components/designdecisions/views/newDesignDecisionView.html',
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