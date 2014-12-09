angular.module('collaboApp').controller('NewDesignDecisionController', ['$scope', '$modalInstance', '$http', 'idIssue', function($scope, $modalInstance, $http, idIssue) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.save = function() {
		
		$scope.appUserIds = [];
		for (var index = 0; index < $scope.shareholders.length; index++) {
			$scope.appUserIds.push($scope.shareholders[index].idUser);
		}
		
		var config = {
				data : {
					'title' : $scope.title,
					'idIssue' : idIssue,
					'appUserIds' : $scope.appUserIds,
					'assumption' : $scope.assumption,
					'designDecisionStatus' : 'COLLECTING_ALTERNATIVES'
				}
		}
		
		$http.post("/rest/designdecisions", config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
		});
	};
	
	$scope.shareholders = [];
}]);