angular.module('collaboApp')
.controller('ShareholdereditorController', ['$scope', '$http', function($scope, $http) {
	$scope.shareholdereditorText = '';
	
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
	
	// When a shareholder is selected
	$scope.onShareholderSelect = function($item, $model, $label) {
		if($scope.shareholders.indexOf($item) < 0) {
			$scope.shareholders.push($item);
		}
		$scope.shareholdereditorText = '';
	}
	
	$scope.removeShareholder = function(shareholder) {
		var index = $scope.shareholders.indexOf(shareholder);
		if(index > -1) {
			$scope.shareholders.splice(index, 1);
		}
	}

}]);