angular.module('controller.hotel', ['services', 'ui.router'])
    .controller('hotelListCtrl', ['$scope', '$state', 'apiService', 'alertManager', 'modals', function ($scope, $state, apiService, alertManager, modals) {
        function initHotelList() {
        	apiService.getHotelList().then(function(response) {
        		$scope.data.hotelList = response;
        	}, function(response) {
        		$scope.data.hotelList = [];
        		alertManager.addAlert('danger', response);
        	});
        }
        initHotelList();
        $scope.data = {
            countPerPage: 10,
            page: 1,
            maxCount: 5
        };
        $scope.data.changePage = function() {
            $scope.data.startIndex = ($scope.data.page - 1) * $scope.data.countPerPage;
        };
        $scope.$watch(function() {
            return $scope.data.chooseAll;
        }, function(newVal) {
            angular.forEach($scope.data.hotelList, function(hotel) {
                hotel.$$selected = newVal;
            });
        });
        $scope.data.addHotel = function() {
        	$state.go('FeastBooking.newHotel');
        };
        $scope.data.removeHotel = function() {
        	
        };
    }])
    .controller('hotelCtrl', ['$state', '$scope', 'hotelId', function($state, $scope, hotelId) {
        $scope.hotelId = hotelId;
    	$scope.goto = function(state, params) {
            var fullState = "FeastBooking.hotel." + state;
    		if(fullState.toLowerCase() !== $state.current.toLowerCase()) {
                $state.go(fullState, params);
            }
    	};
    }])
    .controller('hotelDetailCtrl', ['$scope', 'api', 'hotelId', function($scope, api, hotelId) {
    	$scope.data = {};
        $scope.data.hotelId = hotelId;
        $scope.data.hotelDetail = api.getHotelDetail(0);
        $scope.data.onMethod = function(id) {
            alert(id);
        }
    }])
    .controller('hotelCreateCtrl', ['$scope', 'apiService', function($scope, apiService) {
    	$scope.data = {};
        $scope.data.createHotel = function() {
        	var hotel = {
        		name: $scope.data.name,
        		cityOfAddress: $scope.data.city,
        		districtOfAddress: $scope.data.district,
        		streetOfAddress: $scope.data.street,
        		contact: $scope.data.contact,
        		email: $scope.data.email,
        		telephone: $scope.data.telephone,
        		description: $scope.data.description
        	}
            apiService.createHotel(hotel).then(function(response) {
            	$state.go("FeastBooking.hotel.info", {hotelId: response});
            }, function(response) {
            	console.log(response);
            });
        };
    }])