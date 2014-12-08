angular.module('collaboApp').controller('DesignDecisionsController', ['$scope', '$http', '$modal', function($scope, $http, $modal) {
	$scope.getDesignDecisions = function(status){

		var uri = "/rest/designdecisions";
		
		if(status != null) {
			$scope.currentTab = status;
			uri += "?status=" + status;
		} else {
			$scope.currentTab = "ALL";
		}
		
		$http.get(uri).success(function(data) {
			$scope.designDecisions = angular.fromJson(data);
		});
	};
	
	$scope.showDesignDecision = function(idDesignDecision){
		var modalInstance = $modal.open({
			templateUrl : 'app/components/designdecisions/views/designDecisionDetailView.html',
			controller : 'DesignDecisionDetailViewController',
			resolve: {
				id : function() {
					return idDesignDecision;
				}
			}
		});
	};
	
	// On load show all
	$scope.getDesignDecisions(null);
}]);