angular.module('collaboApp').controller('NewAlternativeController', ['$scope', '$modalInstance', '$http', 'idDD', function($scope, $modalInstance, $http, idDD) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	$scope.save = function() {
		var config = {
				data : {
					'description' : $scope.description,
					'idDesignDecision' : idDD,
				}
		}
		
		$http.post("/rest/alternatives", config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
		});
	};
	
}]);