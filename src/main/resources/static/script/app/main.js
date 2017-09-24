angular.module('app', ['controllers', 'ui.bootstrap', 'directives', 'ui.router'])
.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function($stateProvider, $locationProvider, $urlRouterProvider) {
	
	$locationProvider.html5Mode(true).hashPrefix('!');
	var states = [{
		name: "hotels",
		url: "/hotels",
		views: {
			'main@': {
				templateUrl:"partialView/hotels",
				controller: "hotelListCtrl"
			}
		}
	}];
	states.forEach(function(state) {
		$stateProvider.state(state);
	});
}])
