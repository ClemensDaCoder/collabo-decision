angular.module('collaboApp').controller('NewDesignDecisionController', ['$scope', '$modalInstance', '$http', 'idIssue', function($scope, $modalInstance, $http, idIssue) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	$scope.save = function() {
		
		var config = {
				data : {
					'title' : $scope.title,
					'idIssue' : idIssue,
					'shareholders' : $scope.currentShareholders,
					'assumption' : $scope.assumption,
					'idDesignDecisionStatus' : 1
				}
		}
		
		$http.post("/rest/designdecisions", config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
		});
	};
	
	$scope.currentShareholders = [];
	
}]);