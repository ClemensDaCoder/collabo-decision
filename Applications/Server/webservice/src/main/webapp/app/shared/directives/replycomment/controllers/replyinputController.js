angular.module('collaboApp')
.controller('ReplyInputController', ['$scope', '$http', function($scope, $http) {
	$scope.hideInputReply = true;
	
	$scope.toggleInputReply = function(){
		$scope.newReply="";
		$scope.hideInputReply = !$scope.hideInputReply;
	};
	
	$scope.addReplyComment = function(){
		var uri = "/rest/comments/" + $scope.parentcomment + "?message=" + $scope.newReply + "&date=2015-01-01 11:11:11";
		
		$http.post(uri, null).success(function() {
			$scope.toggleInputReply();
			//event to refresh comments
			$scope.$root.$broadcast("addedReply", {
	        });
		}).error(function() {
			alert("fehler");
		});
	};

}]);