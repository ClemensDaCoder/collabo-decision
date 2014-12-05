angular.module('collaboApp').controller('DesignDecisionDetailViewController', ['$scope', '$modalInstance', '$http', 'id', function($scope, $modalInstance, $http, id) {
	
	$scope.idDesignDecision = id;
	
	$scope.initialize = function(){
		// get the design decision from server
		var uri = "/rest/designdecisions/";
		
		if($scope.idDesignDecision != null) {
			uri += $scope.idDesignDecision + "?withRelations=false";		
			$http.get(uri).success(function(data) {
				$scope.designDecision = angular.fromJson(data);
				$scope.fillFields();
			});
		}
	};
	
	$scope.fillFields = function(){
		alert($scope.designDecision.assumption);
		alert($scope.designDecision.issue.owner.forename);
		$scope.title = $scope.designDecision.title;
		$scope.issue = $scope.designDecision.issue.title;
		$scope.owner = $scope.designDecision.issue.owner.forename + " " + $scope.designDecision.issue.owner.surname;
		$scope.shareholders = "Here will be the shareholders.";
		$scope.rationale = $scope.designDecision.rationale;
		$scope.assumption = $scope.designDecision.assumption;
	};
	
	//initialize fields
	$scope.initialize();
}]);