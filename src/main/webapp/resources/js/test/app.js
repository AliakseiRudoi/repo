


var app = angular.module('newsApp', ['ngRoute']).config(function ($routeProvider) { 
	
	$routeProvider.when('/home', {templateUrl: 'div1.html', controller: 'div1'});
	  $routeProvider.otherwise({redirectTo: '/'});
	  
	 
});

app.controller('myCntr', function ($scope) {
        $scope.test = 'hello';
    });