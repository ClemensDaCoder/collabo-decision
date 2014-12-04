angular.module('collaboApp').directive('tageditor', function() {
	return {
		restrict: 'E',
		templateUrl : 'app/shared/directives/tageditor/views/tageditorView.html',
		scope : { 
			tags : "="
		}
	}
}).directive('issueeditor', function() {
	return {
		restrict: 'E',
		templateUrl : 'app/shared/directives/issueeditor/views/issueeditorView.html',
		scope : {
			issues : "="
		}
	}
});