angular.module('collaboApp')
.controller('AppusereditorController', ['$scope', '$http', function($scope, $http) {
	$scope.appusereditorText = '';
	
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
		});
	};
	
	// When a user is selected
	$scope.onAppUserSelect = function($item, $model, $label) {
		if($scope.appusers.indexOf($item) < 0) {
			$scope.appusers.push($item);
		}
		$scope.appusereditorText = '';
	};
	
	$scope.removeAppUser = function(appUser) {
		var index = $scope.appusers.indexOf(appUser);
		if(index > -1) {
			$scope.appusers.splice(index, 1);
		}
	};

}]);