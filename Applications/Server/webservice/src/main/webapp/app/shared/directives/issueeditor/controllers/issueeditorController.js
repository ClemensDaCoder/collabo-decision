angular.module('collaboApp')
.controller('IssueeditorController', ['$scope', '$http', function($scope, $http) {

	$scope.issueeditorText = '';
	
	$scope.getIssueResponses = function(partialTitle) {
		return $http.get('/rest/issues', {
			params : {
				'partialtitle' : partialTitle
			}
		}).then(function(response) {
			var partials = response.data.map(function(e) {
				return e;
			});
			
			return partials;
		});
	};
	
	// Check if space at the end => means that the tag was finished
	$scope.onIssueSelect = function($item, $model, $label) {
		
		// Check if the last character was a space
		// If so, add the Text to the selected Tags
		// Only add if not already in Array
		if($scope.issues.indexOf($item.issue) < 0) {
			$scope.issues.push($item.issue);
		}
		$scope.issueeditorText = '';
	};
	
	$scope.removeIssue = function(issue) {
		var index = $scope.issues.indexOf(issue);
		if(index > -1) {
			$scope.issues.splice(index, 1);
		}
	}
	
}]);