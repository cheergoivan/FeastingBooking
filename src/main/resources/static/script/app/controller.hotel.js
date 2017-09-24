angular.module('controller.hotel', ['services'])
    .controller('hotelListCtrl', ['$scope', 'api', function ($scope, api) {
        $scope.data = {
            countPerPage: 10,
            page: 1,
            maxCount: 5
        };
        $scope.data.hotelList = api.getHotelList();
        $scope.data.changePage = function() {
            $scope.data.startIndex = ($scope.data.page - 1) * $scope.data.countPerPage;
        };
        $scope.$watch(function() {
            return $scope.data.chooseAll;
        }, function(newVal) {
            angular.forEach($scope.data.hotelList, function(hotel) {
                hotel.$$selected = newVal;
            });
        })
    }])
    .controller('hotelDetailCtrl', ['$scope', 'api', function($scope, api) {
        $scope.data = {};
        $scope.data.hotelDetail = api.getHotelDetail(0);
        $scope.data.onMethod = function(id) {
            alert(id);
        }
    }])
