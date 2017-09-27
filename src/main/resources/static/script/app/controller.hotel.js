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
    .controller('hotelDetailCtrl', ['$state', '$scope', 'apiService', 'hotelId', 'alertManager', function($state, $scope, apiService, hotelId, alertManager) {
    	$scope.data = {};
        function initHotelDetail() {
        	apiService.getHotelDetail(hotelId).then(function(response) {
        		$scope.data.hotelDetail = response;
        	}, function(response) {
        		alertManager.alert(response);
        	})
        }
        initHotelDetail();
        $scope.data.state = $state.current.url.substring(1).toLowerCase();
    	$scope.data.goto = function(state, params) {
            var fullState = "FeastBooking.hotel." + state;
    		if(fullState.toLowerCase() !== $state.current.name.toLowerCase()) {
                $state.go(fullState, params);
            }
    	};
    }])
    .controller('hotelDetailInfoCtrl', ['$scope', 'apiService', 'hotelId', 'alertManager',  function($scope, apiService, hotelId, alertManager) {
    	$scope.data = {};
        $scope.data.hotelId = hotelId;
    }])
    .controller('hotelCreateCtrl', ['$scope', '$state', 'apiService', 'alertManager', function($scope, $state, apiService, alertManager) {
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
            	alertManager.addAlert('danger', response);
            });
        };
    }])