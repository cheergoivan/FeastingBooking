angular.module('controller.account', ['services'])
.controller('accountCtrl', ['$scope', '$window', 'api', function($scope, $window, api) {
	$scope.signin = function() {
		var account = {
			username: $scope.username,
			password: $scope.password
		}
		api.signin(account).then(function(response) {
			if(!response.token)
				alert("no token");
			else {
				$window.localStorage["FeastBookingAuthToken"] = "Bearer " + response.token;
				
			}
		}, function(response) {
			alert(response);
		})
	}
	$scope.signout = function() {
		
	}
}])