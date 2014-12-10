angular.module('collaboApp').controller('DesignDecisionDetailViewController', ['$scope', '$modalInstance', '$http', '$modal', 'id', function($scope, $modalInstance, $http, $modal, id) {
	
	$scope.idDesignDecision = id;
	
	$scope.initialize = function(){
		$scope.currentShareholders = [];
		
		// get the design decision from server
		var uri = "/rest/designdecisions/";
		
		if($scope.idDesignDecision != null) {
			uri += $scope.idDesignDecision + "?withRelations=true";
			$http.get(uri).success(function(data) {
				$scope.designDecisionWrapper = angular.fromJson(data);
				//$scope.owner = $scope.designDecisionWrapper.designDecision.issue.owner.forename + " " + $scope.designDecisionWrapper.designDecision.issue.owner.surname;
				
				for (var index = 0; index < $scope.designDecisionWrapper.designDecision.shares.length; index++) {
					$scope.currentShareholders.push($scope.designDecisionWrapper.designDecision.shares[index].appUser);
				}
				$scope.isEditDisabled = !($scope.designDecisionWrapper.editable);
				
				$scope.canRank = $scope.designDecisionWrapper.designDecision.designDecisionStatus === 'RANK_ALTERNATIVES' && 
									$scope.designDecisionWrapper.shareholder;
				
			});
		}
	};
	
	$scope.startRanking = function(){
		//update design decision and set status to "RANK_ALTERNATIVES"
		
		var config = {
				data : {
					'designDecisionStatus' : 'RANK_ALTERNATIVES',
					'onlyStatusChange' : true
				}
		}
		
		$http.put("/rest/designdecisions/" + $scope.idDesignDecision, config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
		});
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
	};
	
	$scope.showAlternative = function(idAlternative){
		var modalInstance = $modal.open({
			templateUrl : 'app/components/alternatives/views/alternativeDetailView.html',
			controller : 'AlternativeDetailController',
			resolve: {
				idAlternative : function() {
					return idAlternative;
				}
			}
		});
		modalInstance.result.then(function() {
			$scope.initialize();
		}, function() {
			$scope.initialize();
		});
	};
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.getUser = function(partialName) {
			
			return $http.get('/rest/appusers', {
				params : {
					'partialname' : partialName
				}
			}).then(function(response) {
				
				var partials = response.data.map(function(e) {
					return e;
				});
				
				return partials;
			})
		};
	
	$scope.alternativeRank = [];

	$scope.rankChosen = function(alternative, rank) {
		
		$scope.alternativeRank.push({
			'idAlternative' : alternative.idAlternative,
			'rank' : rank
		});
		
		
		
	};

	//initialize fields
	$scope.initialize();
}]);