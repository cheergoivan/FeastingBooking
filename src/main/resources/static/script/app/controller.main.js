angular.module('controller.main', ['services'])
.controller('mainCtrl', ['$rootScope', 'alertManager', function($rootScope, alertManager) {
	$rootScope.alerts = [];
	$rootScope.removeAlert = alertManager.removeAlert;
}])