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
}).directive('replycomment', function($compile) {
	return {
		restrict: 'E',
		transclude: true,
		templateUrl : 'app/shared/directives/replycomment/views/replycommentView.html',
		scope : { 
			parentcomment : "="
		},
		compile: function(tElement, tAttr, transclude) {
            var contents = tElement.contents().remove();
            var compiledContents;
            return function(scope, iElement, iAttr) {
                if(!compiledContents) {
                    compiledContents = $compile(contents, transclude);
                }
                compiledContents(scope, function(clone, scope) {
                         iElement.append(clone); 
                });
            };
        }
		
	}
});