angular.module('collaboApp')
.controller('TageditorController', ['$scope', '$http', function($scope, $http) {

	$scope.tageditorText = '';
	
	$scope.getTag = function(partialName) {
		return $http.get('/rest/tags', {
			params : {
				'partialname' : partialName
			}
		}).then(function(response) {
			var partials = response.data.map(function(e) {
				return e;
			});
			
			return partials;
		});
	};
	
	// Check if space at the end => means that the tag was finished
	$scope.$watchCollection('tageditorText', function() {
		
		var text = $scope.tageditorText;
		
		// Check if the last character was a space
		// If so, add the Text to the selected Tags
		if(text.slice(-1) === " ") {
			
			text = text.trim();
			
			// Only add if not already in Array
			if($scope.tags.indexOf(text) < 0) {
				$scope.tags.push(text.trim());
			}
			$scope.tageditorText = '';
		}
	});
	
	// When a tag is selected
	$scope.onTagSelect = function($item, $model, $label) {
		
		if($scope.tags.indexOf($item.name) < 0) {
			$scope.tags.push($item.name);
		}
		$scope.tageditorText = '';
	}
	
	$scope.removeTag = function(tag) {
		var index = $scope.tags.indexOf(tag);
		if(index > -1) {
			$scope.tags.splice(index, 1);
		}
	}
	
}]);