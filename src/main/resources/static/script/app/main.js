angular.module("app", ["controllers", "ui.bootstrap", "directives", "ui.router", "services", "blockUI"])
.config(["$stateProvider", "$locationProvider", "$urlRouterProvider", "constants", function($stateProvider, $locationProvider, $urlRouterProvider, constants) {
	$locationProvider.html5Mode(true).hashPrefix("!");
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
				templateUrl: "partialView/FeastBooking",
				controller: "feastBookingCtrl"
			}
		}
	}, {
		name: constants.hotelsStatePrefix,
		url: "/hotels",
		abstract: true,
		views: {
			"main@FeastBooking": {
				templateUrl: "partialView/hotels",
				controller: "hotelsCtrl"
			}
		}
	}, {
		name: constants.hotelsStatePrefix + "." + constants.hotelsListState,
		url: "/list",
		views: {
			"hotelsMain@FeastBooking.hotels": {
				templateUrl:"partialView/hotel_list",
				controller: "hotelListCtrl"
			}
		}
	}, {
		name: constants.hotelsStatePrefix + "." + constants.hotelsRecommondationState,
		url: "/recommendation",
		views: {
			"hotelsMain@FeastBooking.hotels": {
				template: "TODO"
			}
		}
	}, {
		name: constants.hotelStatePrefix,
		url: "/hotel/:hotelId",
		abstract: true,
		views: {
			"main@FeastBooking": {
				templateUrl: "partialView/hotel_detail",
				controller: "hotelDetailCtrl"
			}
		},
		resolve: {
			apiService: "apiService",
			hotelDetail: ["$stateParams", "apiService", function($stateParams, apiService) {
				return apiService.getHotelDetail(parseInt($stateParams.hotelId, 10));
			}]
		}
	}, {
		name: constants.hotelsStatePrefix + "." + constants.createHotelState,
		url: "/new",
		views: {
			"hotelsMain@FeastBooking.hotels": {
				templateUrl: "partialView/newHotel",
				controller: "hotelCreateCtrl"
			}
		}
	}, {
		name: constants.hotelStatePrefix + "." + constants.hotelInfoState,
		url: "/info",
		views: {
			"hotelDetailMain@FeastBooking.hotel": {
				templateUrl: "partialView/hotelDetailInfo",
				controller: "hotelInfoCtrl"
			}
		}
	}, {
		name: constants.hotelStatePrefix + "." + constants.hotelBanquetState,
		url: "/banquet/:banquetId",
		views: {
			"hotelDetailMain@FeastBooking.hotel": {
				templateUrl: "partialView/hotelDetailBanquet",
				controller: "hotelBanquetCtrl"
			}
		},
		resolve: {
			apiService: "apiService",
			banquet: ["$stateParams", "apiService", function($stateParams, apiService) {
				return apiService.getBanquetHall($stateParams.banquetId);
			}]
		}
	}, {
		name: constants.hotelStatePrefix + "." + constants.hotelCreateBanquetState,
		url: "/newBanquet",
		views: {
			"hotelDetailMain@FeastBooking.hotel": {
				templateUrl: "partialView/newBanquet",
				controller: "banquetCreateCtrl"
			}
		}
	}, {
		name: constants.hotelStatePrefix + "." + constants.hotelFeastState,
		url: "/feast/:feastId",
		views: {
			"hotelDetailMain@FeastBooking.hotel": {
				templateUrl: "partialView/hotelDetailFeast",
				controller: "hotelFeastCtrl"
			}
		},
		resolve: {
			apiService: "apiService",
			feast: ["$stateParams", "apiService", function($stateParams, apiService) {
				return apiService.getFeast($stateParams.feastId);
			}]
		}
	}, {
		name: constants.hotelStatePrefix + "." + constants.hotelCreateFeastState,
		url: "/newFeast",
		views: {
			"hotelDetailMain@FeastBooking.hotel": {
				templateUrl: "partialView/newFeast",
				controller: "feastCreateCtrl"
			}
		},
	}];
	states.forEach(function(state) {
		$stateProvider.state(state);
	});
}])
.run(['$window', function($window) {
	$window.onresize = function() {
		//TODO
	}
}])