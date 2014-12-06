angular.module('collaboApp').controller('DesignDecisionDetailViewController', ['$scope', '$modalInstance', '$http', 'id', function($scope, $modalInstance, $http, id) {
	
	$scope.idDesignDecision = id;
	
	$scope.initialize = function(){
		// get the design decision from server
		var uri = "/rest/designdecisions/";
		
		if($scope.idDesignDecision != null) {
			uri += $scope.idDesignDecision + "?withRelations=false";		
			$http.get(uri).success(function(data) {
				$scope.designDecisionWrapper = angular.fromJson(data);
				$scope.fillFields();
			});
		}
	};
	
	$scope.fillFields = function(){
		$scope.owner = $scope.designDecisionWrapper.designDecision.issue.owner.forename + " " + $scope.designDecisionWrapper.designDecision.issue.owner.surname;
		$scope.shareholders = "Here will be the shareholders.";
	};
	
	//initialize fields
	$scope.initialize();
}]);