var collaboControllers = angular.module('collaboControllers', []);


collaboControllers.controller('BodyController', ['$scope', '$cookieStore', 'Auth', function($scope, $cookieStore, Auth) {
	$scope.logout = function() {
		Auth.clearCredentials();
	}
}]);

collaboControllers.controller('LoginController', ['Auth', '$location', '$scope', function(Auth, $location, $scope) {
	$scope.login = function(email, password) {
		Auth.setCredentials(email, password);
		$location.path("/issues");
	}
}]);

collaboControllers.controller('IssuesController', ['$http', '$scope', '$modal', function($http, $scope, $modal) {
	
	$scope.getIssues = function(status) {
		
		var uri = "/api/issues";
		
		if(status != null) {
			$scope.currentTab = status;
			uri += "?status=" + status;
		} else {
			$scope.currentTab = "ALL";
		}
		
		$http.get(uri).success(function(data) {
			$scope.issues = angular.fromJson(data);
		});
	}
	
	$scope.showIssue = function(issue) {
		alert(issue.title);
	}
	
	$scope.newIssue = function() {
		$modal.open({
			templateUrl : 'partials/newIssue.html',
			controller : 'NewIssueController',
			backdrop : false	
		});
	}
	
	// On load show all
	$scope.getIssues(null);
}]);


collaboControllers.controller('DesignDecisionsController', [function() {
	
}]);

collaboControllers.controller('StatisticsController', [function() {
	
}]);

collaboControllers.controller('NewIssueController', ['$scope', '$modalInstance', '$http', function($scope, $modalInstance, $http) {
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	$scope.getUser = function(partialName) {
		
		return $http.get('/api/users', {
			params : {
				'partialname' : partialName
			}
		}).then(function(response) {
			
			var partials = response.data.map(function(e) {
				return e;
			});
			
			return partials;
		})
	}
	
	$scope.getTag = function(partialName) {
		return $http.get('api/tags', {
			params : {
				'partialname' : partialName
			}
		}).then(function(response) {
			var partials = response.data.map(function(e) {
				return e;
			});
			
			return partials;
		});
		
	}
	
}]);