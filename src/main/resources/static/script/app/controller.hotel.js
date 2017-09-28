angular.module('controller.hotel', ['services', 'ui.router', 'ngFileUpload'])
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
    .controller('hotelDetailCtrl', ['$state', '$scope', 'apiService', 'hotelDetail', 'alertManager', function($state, $scope, apiService, hotelDetail, alertManager) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
        $scope.data.state = $state.current.url.substring(1).toLowerCase();
    	$scope.data.goto = function(state, params) {
            var fullState = "FeastBooking.hotel." + state;
    		if(fullState.toLowerCase() !== $state.current.name.toLowerCase()) {
                $state.go(fullState, params);
            }
    	};
    }])
    .controller('hotelDetailInfoCtrl', ['$scope', 'apiService', 'hotelDetail', 'alertManager', 'Upload', function($scope, apiService, hotelDetail, alertManager, Upload) {
    	$scope.data = { hotelDetail : angular.copy(hotelDetail) };
    	function hotelDTO2hotelPO(dto) {
    		var po = angular.copy(dto);
    		po.cityOfAddress = dto.address.city;
    		po.districtOfAddress = dto.address.district;
    		po.streetOfAddress = dto.address.street;
    		delete po.address;
    		return po;
    	};
    	$scope.data.updateHotelDetail = function() {
    		var detail = hotelDTO2hotelPO($scope.data.hotelDetail);
    		apiService.updateHotelDetail(detail).then(function() {
    			alertManager.addAlert("success", "成功更新酒店信息。");
    			hotelDetail = $scope.data.hotelDetail;
    		}, function(response) {
    			alertManager.addAlert('danger', response);
    		});
    	};
    	$scope.data.updateHotelDetailWithImages = function(images) {
    		var detail = hotelDTO2hotelPO($scope.data.hotelDetail);
    		detail.files = images;
    		apiService.updateHotelDetailWithImage(detail).then(function() {
    			alertManager.addAlert("success", "成功更新酒店信息。");
    			hotelDetail = $scope.data.hotelDetail;
    		}, function(response) {
    			alertManager.addAlert('danger', response);
    		})
    	};
    	$scope.data.revertChange = function() {
    		$scope.data.hotelDetail = hotelDetail;
    	};
    }])
    .controller('hotelDetailBanquetCtrl', ['$scope', '$state', 'apiService', 'alertManager', 'hotelDetail', function($scope, $state, apiService, alertManager, hotelDetail) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	$scope.data.addNewBanquet = function() {
    		$state.go('FeastBooking.hotel.newBanquet', $scope.data.hotelDetail.id);
    	};
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
            	$state.go("FeastBooking.hotel.info", { hotelId: response});
            }, function(response) {
            	console.log(response);
            	alertManager.addAlert('danger', response);
            });
        };
    }])