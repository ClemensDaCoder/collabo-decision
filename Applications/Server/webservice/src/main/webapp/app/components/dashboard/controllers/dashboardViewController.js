angular.module('collaboApp').controller('DashboardController', ['$http', '$scope', '$modal', function($http, $scope, $modal) {
	
	$scope.getOwningIssues = function() {
		
		var uri = "/rest/issues?owned=true";
		
		$http.get(uri).success(function(data) {
			$scope.issues = angular.fromJson(data);
		});
	};
	
	$scope.showIssue = function(idIssue) {
		var modalInstance = $modal.open({
			templateUrl : 'app/components/issues/views/issueDetailView.html',
			controller : 'IssueDetailViewController',
			backdrop : false,
			resolve : {
				id : function() {
					return idIssue;
				}
			}
		});
		
		modalInstance.result.then(function() {
			$scope.refresh();
		}, function() {
			$scope.refresh();
		});
	};
	
	$scope.getRankableDDs = function() {
		$scope.getDDs(true, false);
	};
	
	$scope.getRateableDDs = function() {
		$scope.getDDs(false, true);
	};
	
	$scope.getDDs = function(rankable, rateable) {
		var uri = "/rest/designdecisions";
		
		uri+="?isShareholder=true";
		if(rankable)
			uri+="&torank=true";
		else if(rateable)
			uri+="&torate=true";
		
		$http.get(uri).success(function(data) {
			if(rankable){
				$scope.rankableDDs = angular.fromJson(data);
				for(var i = 0;i<$scope.rankableDDs.length;i++){
					if(!$scope.rankableDDs[i].showFinishRanking){
						$scope.rankableDDs.splice(i,1);
					}
				}
			}
			else if(rateable){
				$scope.rateableDDs = angular.fromJson(data);
				for(var i = 0;i<$scope.rateableDDs.length;i++){
					if($scope.rateableDDs[i].rated){
						$scope.rateableDDs.splice(i,1);
					}
				}
			}
			else
				$scope.allDDs = angular.fromJson(data);
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
			$scope.refresh();
		}, function() {
			$scope.refresh();
		});
	};
	
	$scope.refresh = function(){
		$scope.getOwningIssues();
		$scope.getRankableDDs();
		$scope.getRateableDDs();
		$scope.getDDs();
	};
	
	$scope.refresh();
}]);