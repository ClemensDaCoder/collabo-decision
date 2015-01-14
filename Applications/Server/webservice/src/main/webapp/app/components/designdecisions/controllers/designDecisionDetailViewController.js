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
				
				$scope.showAddAlternative = $scope.designDecisionWrapper.designDecision.designDecisionStatus == "COLLECTING_ALTERNATIVES";
				
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
		
		var idsShareholders = [];
		//$scope.currentShareholders[i].idUser
		for(var i in $scope.currentShareholders) {
			idsShareholders.push($scope.currentShareholders[i].idUser);
		}
		var config = {
				data : {
					'title' : $scope.designDecisionWrapper.designDecision.title,
					'idIssue' : $scope.designDecisionWrapper.designDecision.issue.idIssue,
					'appUserIds' : idsShareholders,
					'assumption' : $scope.designDecisionWrapper.designDecision.assumption,
					'rationale' : $scope.designDecisionWrapper.designDecision.rationale,
					'designDecisionStatus' : $scope.designDecisionWrapper.designDecision.designDecisionStatus,
					'onlyStatusChange' : false
				}
		}
		
		var uri = "rest/designdecisions/" + id;
		
		$http.put(uri, config).success(function() {
			$scope.cancel();
		}).error(function() {
			alert("fehler");
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
		
		var config = {
			data : 	$scope.alternativeRanks
		};
		
		$http.post("/rest/rankings", config).success(function(data) {
			
			var rankingFinished = angular.fromJson(data);
			
			if(rankingFinished) {
				$scope.cancel()
			} else {
				alert("Sucessfully Ranked");
				$scope.cancel();
			}
		});	
	}
	
	$scope.selectAlternative = function(alternative, designDecision) {
		$http.post("rest/designdecisions/" + designDecision.idDesignDecision + "/solution?solution=" + alternative.idAlternative).success(function() {
			alert("Alternative: " + alternative.idAlternative + " was successfully selected as Solution.");
			
			// get the design decision from server
			$scope.currentShareholders = [];
			var uri = "/rest/designdecisions/";
			
			if($scope.idDesignDecision != null) {
				uri += $scope.idDesignDecision + "?withRelations=true";
				$http.get(uri).success(function(data) {
					$scope.designDecisionWrapper = angular.fromJson(data);
					
					for (var index = 0; index < $scope.designDecisionWrapper.designDecision.shares.length; index++) {
						$scope.currentShareholders.push($scope.designDecisionWrapper.designDecision.shares[index].appUser);
					}
					//set rationale and save
					$scope.designDecisionWrapper.designDecision.rationale = $scope.rationale;
					$scope.save();
				});
			}
		});
	};

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