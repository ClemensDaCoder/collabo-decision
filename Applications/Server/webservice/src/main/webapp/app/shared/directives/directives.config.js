angular.module('collaboApp').directive('tageditor', function() {
	return {
		restrict: 'E',
		templateUrl : 'app/shared/directives/tageditor/views/tageditorView.html',
		scope : { 
			tags : "=",
			isDisabled : "=ngDisabled"
			
		}
	}
}).directive('issueeditor', function() {
	return {
		restrict: 'E',
		templateUrl : 'app/shared/directives/issueeditor/views/issueeditorView.html',
		scope : {
			issues : "=",
			isDisabled : "=ngDisabled"
			
		}
	}
}).directive('appusereditor', function() {
	return {
		restrict: 'E',
		templateUrl : 'app/shared/directives/appusereditor/views/appusereditorView.html',
		scope : { 
			appusers : "=",
			isDisabled : "=ngDisabled"
		}
	}
});