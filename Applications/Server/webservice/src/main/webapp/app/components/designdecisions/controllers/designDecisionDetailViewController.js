angular.module('collaboApp').controller('DesignDecisionDetailViewController', ['$scope', '$modalInstance', '$http', '$modal', 'id', function($scope, $modalInstance, $http, $modal, id) {
	
	$scope.idDesignDecision = id;
	
	$scope.initialize = function(){
		// get the design decision from server
		var uri = "/rest/designdecisions/";
		
		if($scope.idDesignDecision != null) {
			uri += $scope.idDesignDecision + "?withRelations=false";		
			$http.get(uri).success(function(data) {
				$scope.designDecisionWrapper = angular.fromJson(data);
				$scope.owner = $scope.designDecisionWrapper.designDecision.issue.owner.forename + " " + $scope.designDecisionWrapper.designDecision.issue.owner.surname;
				$scope.fillFields();
			});
		}
	};
	
	$scope.fillFields = function(){
		$scope.shareholders = "Here will be the shareholders.";
	};
	
	$scope.startRanking = function(){
		//update design decision and set status to "RANK_ALTERNATIVES"
		
		//refresh view
		
	};
	
	$scope.setInappropriateSolution = function(){
		//update design decision and set status to "INAPPROPRIATE_SOLUTION
		
		//refresh view
		
	};
	
	$scope.addAlternative = function(){
		var modalInstance = $modal.open({
			templateUrl : 'app/components/alternatives/views/newAlternative.html',
			controller : 'NewAlternativeController',
			resolve: {
				idDD : function() {
					return $scope.idDesignDecision;
				}
			}
		});
		modalInstance.result.then(function() {
			$scope.initialize();
		}, function() {
			$scope.initialize();
		});
	}
	
	$scope.showAlternative = function(idAlternative){
		
	};
	
	//initialize fields
	$scope.initialize();
}]);