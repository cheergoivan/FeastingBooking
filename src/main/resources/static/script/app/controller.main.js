angular.module("controller.main", ["services"])
.controller("mainCtrl", ["$rootScope", "alertManager", "constants", function($rootScope, alertManager, constants) {
	$rootScope.constants = constants;
	$rootScope.alerts = [];
	$rootScope.removeAlert = alertManager.removeAlert;
	$rootScope.$on('$stateChangeStart', function(evt, toState) {
		$rootScope.state = toState;
	});
}])
.controller("feastBookingCtrl", ["$scope", "$state", "$rootScope", function($scope, $state, $rootScope) {
	$scope.data = {};
	$rootScope.state = $state.current;
}])