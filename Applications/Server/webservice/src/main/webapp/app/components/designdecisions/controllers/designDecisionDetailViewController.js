angular.module('collaboApp').controller('DesignDecisionDetailViewController', ['$scope', '$modalInstance', '$http', '$modal', 'id', 'DateService', function($scope, $modalInstance, $http, $modal, id, DateService) {
	
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
				
				$scope.alternativeRanks = [];
				
				if($scope.canRank) {
					
					for(var i in $scope.designDecisionWrapper.designDecision.alternatives) {
						
						var idAlternative = $scope.designDecisionWrapper.designDecision.alternatives[i].idAlternative;
						
						$scope.alternativeRanks.push({
							'idAlternative' : idAlternative,
							'rank' : null
						});
					}	
				}
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
			templateUrl : 'app/components/alternatives/views/newAlternativeView.html',
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
	
	$scope.save = function() {
		
		//$scope.designDecisionWrapper.designDecision
		/*
		  	private String title;
			private String assumption;
			private long idIssue;
			private List<String> files;
			private Set<Long> appUserIds;
			private String designDecisionStatus;
			private String rationale;
			private boolean onlyStatusChange;
		 */
		var config = {
				data : {
					'title' : $scope.designDecisionWrapper.designDecision.title,
					'assumption' : $scope.designDecisionWrapper.designDecision.assumption,
					'idIssue' : $scope.designDecisionWrapper.designDecision.idIssue,
					'appUserIds' : $scope.designDecisionWrapper.designDecision.addUserIds,
					'rationale' : $scope.designDecisionWrapper.designDecision.rationale,
					'onlyStatusChange' : false
				},
				method : 'PUT',
				url : "rest/designdecisions/" + id
		}
		
		$http(config).success(function() {
			$scope.cancel();
		}).error(function(data, status, headers, config) {
			alert(data.error + " - " + data.exception);
		});
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
	
	$scope.rankChosen = function(idAlternative, rank) {
		
		for(var i in $scope.alternativeRanks) {
			if($scope.alternativeRanks[i].rank == rank) {
				$scope.alternativeRanks[i].rank = null;
			}
		}
		
		for(var i in $scope.alternativeRanks) {
			if($scope.alternativeRanks[i].idAlternative == idAlternative) {
				$scope.alternativeRanks[i].rank = rank;
				break;
			}
		}
		
	};
	
	$scope.finishRanking = function() {
		
		for(var i in $scope.alternativeRanks) {
			
			
			$http.post("/rest/alternatives/" + $scope.alternativeRanks[i].idAlternative + "/rankings?rank=" + $scope.alternativeRanks[i].rank).success(function() {
				alert("saved");
			})
			
			
		}
		
	}

	//initialize fields
	$scope.initialize();
	
	$scope.addDesignDecisionComment = function(){
		var uri = "/rest/designdecisions/" + id + "/comments?message=" + $scope.newComment + "&date=" + DateService.date;
		
		$http.post(uri, null).success(function() {
			$scope.toggleInputComment();
			$scope.initialize();
		}).error(function() {
			alert("fehler");
		});
	};
	
	$scope.hideInputComment = true;
	
	$scope.toggleInputComment = function(){
		$scope.newComment="";
		$scope.hideInputComment = !$scope.hideInputComment;
	};
	
	// handles event if a new reply is added
	$scope.$on("addedReply", function(event, args){
		$scope.initialize();
	});
}]);