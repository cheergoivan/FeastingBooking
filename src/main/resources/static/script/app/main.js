angular.module('app', ['controllers', 'ui.bootstrap', 'directives', 'ui.router'])
.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function($stateProvider, $locationProvider, $urlRouterProvider) {
	$locationProvider.html5Mode(true).hashPrefix('!');
	var states = [{
		name: "signin",
		url: "/signin",
		views: {
			"index@": {
				templateUrl: "partialView/signin",
				controller: "accountCtrl"
			}
		}
	}, {
		name: "FeastBooking",
		url: "/FeastBooking",
		abstract: true,
		views: {
			"index@": {
				templateUrl: "partialView/FeastBooking"
			}
		}
	}, {
		name: "FeastBooking.hotels",
		url: "/hotels",
		views: {
			'main@FeastBooking': {
				templateUrl:"partialView/hotels",
				controller: "hotelListCtrl"
			}
		}
	}, {
		name: "FeastBooking.hotel",
		url: "/hotel/:hotelId",
		abstract: true,
		views: {
			'main@FeastBooking': {
				templateUrl: "partialView/hotelDetail",
				controller: "hotelDetailCtrl"
			}
		},
		resolve: {
			hotelId: ['$stateParams', function($stateParams) {
				return parseInt($stateParams.hotelId, 10);
			}]
		}
	}, {
		name: "FeastBooking.newHotel",
		url: "/newHotel",
		views: {
			'main@FeastBooking': {
				templateUrl: "partialView/newHotel",
				controller: "hotelCreateCtrl"
			}
		}
	}, {
		name: "FeastBooking.hotel.info",
		url: "/info",
		views: {
			'content@FeastBooking.hotel': {
				templateUrl: 'partialView/hotelDetailInfo',
				controller: "hotelDetailInfoCtrl"
			}
		}
	}];
	states.forEach(function(state) {
		$stateProvider.state(state);
	});
}])
