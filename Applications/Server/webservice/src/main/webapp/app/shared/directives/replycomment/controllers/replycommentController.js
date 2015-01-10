angular.module('collaboApp')
.controller('ReplycommentController', ['$scope', '$http', function($scope, $http) {
	
	$scope.getComments = function(){
		//rest/comments/[idParent]
		//gets all child comments
		var uri = "/rest/comments/" + $scope.parentcomment;
		
		$http.get(uri).success(function(data) {
			$scope.comments = angular.fromJson(data);
		});
	};
	
	$scope.getComments();
}]);