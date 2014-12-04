angular.module('collaboApp').controller('NewIssueController', ['$scope', '$modalInstance', '$http', function($scope, $modalInstance, $http) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	$scope.save = function() {
		
		var idsDepends = getIdsFromIssueArray($scope.dependsIssues);
		var idsResolves = getIdsFromIssueArray($scope.resolvesIssues);
		var idsRelates = getIdsFromIssueArray($scope.relatesIssues);
		
		
		
		var config = {
				data : {
					'title' : $scope.title,
					'description' : $scope.description,
					'idOwner' : $scope.selectedOwner.idUser,
					'tags' : $scope.currentTags,
					'idsDepends' : idsDepends,
					'idsResolves' : idsResolves,
					'idsRelates' : idsRelates
				}
		}
		
		$http.post("/rest/issues", config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
		});
	};
	
	$scope.getUser = function(partialName) {
		
		return $http.get('/rest/appusers', {
			params : {
				'partialname' : partialName
			}
		}).then(function(response) {
			
			var partials = response.data.map(function(e) {
				return e;
			});
			
			return partials;
		})
	};
	
	$scope.currentTags = [];
	
	// Fetch issues with Matching Tags (when tageditor directive adds tag to array)
	$scope.$watchCollection('currentTags', function() {
		
		if($scope.currentTags && $scope.currentTags.length > 0) {
			var tags = $scope.currentTags;
			var tagString = "";
			
			for(var i in tags) {
				tagString += "tag=" + tags[i] + "&";
			}
			
			$http.get('/rest/issues?' + tagString).success(function(data) {
				$scope.issuesMatchingTags = (!data || data.length === 0)  ?  null : angular.fromJson(data);
			});
		} else {
			$scope.issuesMatchingTags = null;
		}
	})
	
	$scope.dependsIssues = [];
	$scope.resolvesIssues = [];
	$scope.relatesIssues = [];
	
	$scope.addDepends = function(issue) {
		// Only add if not already existing
		if($scope.dependsIssues.indexOf(issue) < 0) {
			$scope.dependsIssues.push(issue);
		}
	}
	
	$scope.addResolves = function(issue) {
		// Only add if not already existing
		if($scope.resolvesIssues.indexOf(issue) < 0) {
			$scope.resolvesIssues.push(issue);
		}
	}
	
	$scope.addRelates = function(issue) {
		// Only add if not already existing
		if($scope.relatesIssues.indexOf(issue) < 0) {
			$scope.relatesIssues.push(issue);
		}
	}
	
}]);

function getIdsFromIssueArray(array) {
	
	var ids = [];
	
	for(var i in array) {
		ids.push(array[i].idIssue);
	}
	
	return ids;
}