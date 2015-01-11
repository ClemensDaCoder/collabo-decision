angular.module('collaboApp').controller('AlternativeDetailController', ['$scope', '$modalInstance', '$http', 'idAlternative', 'DateService', function($scope, $modalInstance, $http, idAlternative, DateService) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.initialize = function() {
		
		// get the alternative from server
		var uri = "/rest/alternatives/";
		
		if(idAlternative != null) {
			uri += idAlternative + "?withRelations=true";
			$http.get(uri).success(function(data) {
				$scope.alternativeWrapper = angular.fromJson(data);
				$scope.creator = $scope.alternativeWrapper.alternative.creator.forename + $scope.alternativeWrapper.alternative.creator.surname;
				$scope.isEditDisabled = !($scope.alternativeWrapper.editable);
			});
		}
	};
	
	$scope.initialize();
	
	$scope.addAlternativeComment = function(){
		var uri = "/rest/alternatives/" + idAlternative + "/comments?message=" + $scope.newComment + "&date=" + DateService.date;
		
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