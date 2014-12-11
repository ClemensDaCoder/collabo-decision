angular.module('collaboApp').controller('IssueDetailViewController', ['$scope', '$modalInstance', '$http', '$modal', 'id', function($scope, $modalInstance, $http, $modal, id) {
	
	// When id is set -> it means that the Issue is going to be edited
	$scope.isEdit = id != null;
	
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
					'idsRelates' : idsRelates,
					'issueStatus' : id == null ? 'NEW' : $scope.issueResponse.issue.issueStatus
				},
				method : id == null ? 'POST' : 'PUT',
				url : id == null ? "/rest/issues" : "rest/issues/" + id
		}
		
		$http(config).success(function() {
			$scope.cancel();
		}).error(function(data, status, headers, config) {
			alert(data.error + " - " + data.exception);
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
				  $scope.issuesMatchingTagsResponse = (!data || data.length === 0)  ?  null : angular.fromJson(data);
			});
		} else {
			  $scope.issuesMatchingTagsResponse = null;
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
				
				// fill the fields for the relations				
				for (i = 0; i < $scope.issueResponse.dependsIssuesFrom.length; i++) {
					$scope.addDepends($scope.issueResponse.dependsIssuesFrom[i]);
				}
				
				for (i = 0; i < $scope.issueResponse.resolvesIssuesFrom.length; i++) { 
					$scope.addResolves($scope.issueResponse.resolvesIssuesFrom[i]);
				}
				
				for (i = 0; i < $scope.issueResponse.relatesIssues.length; i++) { 
					$scope.addRelates($scope.issueResponse.relatesIssuesFrom[i]);
				}
				
				$scope.isEditDisabled = !($scope.issueResponse.editable);
			});
		}
	};
	
	//TODO: move to issue detail view
	$scope.createDesignDecision = function(){
		var modalInstance = $modal.open({
			templateUrl : 'app/components/designdecisions/views/newDesignDecision.html',
			controller : 'NewDesignDecisionController',
			resolve: {
				idIssue : function() {
					return id;
				}
			}
		});
	};
	
	// fetch the Issue and show it
	if(id != null) {
		$scope.initialize();
	}
	
}]);

function getIdsFromIssueArray(array) {
	
	var ids = [];
	
	for(var i in array) {
		ids.push(array[i].idIssue);
	}
	
	return ids;
}