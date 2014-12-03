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
					'tags' : [$scope.selectedTag]
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
	
	$scope.getIssuesMatchingTags = function(tags) {
		
		var tagString = "";
		var tagArray =  tags.split(" ");
		for(var i in tagArray) {
			tagString += "tag=" + tagArray[i] + "&";
		}
		
		$http.get('/rest/issues?' + tagString).success(function(data) {
			$scope.issuesMatchingTags = (!data || data.length === 0)  ?  null : angular.fromJson(data);
		});
	};
	
}]);