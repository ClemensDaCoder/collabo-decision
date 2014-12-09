angular.module('collaboApp').controller('AlternativeDetailController', ['$scope', '$modalInstance', '$http', 'idAlternative', function($scope, $modalInstance, $http, idAlternative) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.initialize = function() {
		
		// get the alternative from server
		var uri = "/rest/alternatives/";
		
		if(idAlternative != null) {
			uri += idAlternative + "?withRelations=true";
			$http.get(uri).success(function(data) {
				$scope.alternativeWrapper = angular.fromJson(data);
			});
		}
	};
	
	$scope.initialize();
	
}]);