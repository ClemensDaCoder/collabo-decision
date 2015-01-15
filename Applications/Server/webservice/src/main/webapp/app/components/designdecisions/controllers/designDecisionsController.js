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
		
		modalInstance.result.then(function() {
			$scope.getDesignDecisions(null);
		}, function() {
			$scope.getDesignDecisions(null);
		});
	};
	
	$scope.calcRating = function(designDecision){
		//calculate average rating
		var sum=0;
		for(var i = 0; i<designDecision.designDecisionRatings.length;i++){
			sum+=designDecision.designDecisionRatings[i].rating;
		}
		var rating = sum / designDecision.designDecisionRatings.length;
		if(isNaN(rating))
			return "no rating";
		else
			return (Math.round(rating * 100) / 100) + "/10";
	};
	
	// On load show all
	$scope.getDesignDecisions(null);
}]);