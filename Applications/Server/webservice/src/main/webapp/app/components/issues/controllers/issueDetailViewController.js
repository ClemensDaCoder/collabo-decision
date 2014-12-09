angular.module('collaboApp').controller('IssueDetailViewController', ['$http', '$scope', 'id', function($http, $scope, id) {
	
	$scope.showStatusButtons = true;
	
	$scope.initialize = function() {
		
		if(id != null) {
			
			$http.get('/rest/issues/' + id + "?withRelations=true").success(function(data) {
				$scope.issueResponse = angular.fromJson(data);
				
				$scope.title = $scope.issueResponse.issue.title;
				$scope.description = $scope.issueResponse.issue.description;
				$scope.description = $scope.issueResponse.issue.description;
				$scope.selectedOwner = $scope.issueResponse.issue.owner;
				
				// Fill in the Tag names
				$scope.currentTags = [];
				for(var i in $scope.issueResponse.issue.issueTags) {
					var issueTag = $scope.issueResponse.issue.issueTags[i];
					$scope.currentTags.push(issueTag.tag.name);
				}
				
				
				$scope.isEditDisabled = !($scope.issueResponse.editable);
			});
		}
	}
	
	$scope.initialize();
}]);
