angular.module('collaboApp').controller('NewIssueController', ['$scope', '$modalInstance', '$http', function($scope, $modalInstance, $http) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	$scope.save = function() {
		
		var config = {
				data : {
					'title' : $scope.title,
					'description' : $scope.description,
					'idOwner' : $scope.selectedOwner.idUser,
					'tags' : $scope.currentTags
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
	
}]);