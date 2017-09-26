angular.module('controller.account', ['services', 'ui.router'])
.controller('accountCtrl', ['$scope', '$window', '$state', 'apiService', 'constants', function($scope, $window, $state, apiService, constants) {
	$scope.signin = function() {
		var account = {
			username: $scope.username,
			password: $scope.password
		}
		apiService.signin(account).then(function(response) {
			if(!response.token){
				
			}
			else {
				$window.localStorage[constants.authTokenName] = "Bearer " + response.token;
				$state.go('FeastBooking.hotels');
			}
		}, function(response) {
			alert(response);
		})
	}
	$scope.signout = function() {
		
	}
}])