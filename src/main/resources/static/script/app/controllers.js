angular.module('controllers', ['controller.hotel', 'controller.account'])
/*
.controller('mainCtrl', ['$rootScope', function($rootScope) {
    $rootScope.state = 'order-detail';
    $rootScope.params = {};
    var $ctrl = this;
    $ctrl.enterHotelList = function() {
        $rootScope.state = 'hotel-list';
    }
    $ctrl.enterUserList = function() {
        $rootScope.state = 'user-list';
    }
    $ctrl.enterOrderList = function() {
        $rootScope.state = "order-list";
    }
}])
.controller('hotelListCtrl', ['$scope', '$rootScope', 'api',
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.hotelList = api.getHotelList();
        $ctrl.countPerPage = 10;
        $ctrl.page = 1;
        $ctrl.maxCount = 5;
        $ctrl.changePage = function() {
            $ctrl.startIndex = ($ctrl.page - 1) * $ctrl.countPerPage;
        }
        var selectAll = false;
        $ctrl.toggleAllSelect = function() {
            selectAll = !selectAll;
            $ctrl.hotelList.forEach(h => { h.$$selected = selectAll; });
        }
        $ctrl.enterHotelDetail = function(id) {
            $rootScope.state = 'hotel-detail';
            $rootScope.$broadcast('enterHotelDetail', {id: id});
        }
    }]
)
.controller('hotelDetailCtrl', ['$scope', '$rootScope', 'api',
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.hotelDetail = api.getHotelDetail(0);
        $ctrl.active = 0;
        $ctrl.interval = 2000;
        $rootScope.$on('enterHotelDetail', function(evt, data) {
            $ctrl.hotelDetail = api.getHotelDetail(data.id);
        })
        $ctrl.tableList = api.getTableList();
        $ctrl.foodList = api.getFoodList();
        $ctrl.benefitList = api.getBenefitList();
        $ctrl.enterOrderDetail = function(id) {
            $rootScope.state = 'order-detail';
            $rootScope.$broadcast('enterOrderDetail', {id: id});
        }
        $ctrl.orderList = api.getOrderList();
        $ctrl.orderMaxCount = 3;
    }
])
.controller('orderListCtrl', ['$scope', '$rootScope', 'api', 
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.maxCount = 10;
        $ctrl.orderList = api.getOrderList();
        $ctrl.enterHotelDetail = function(id) {
            $rootScope.state = 'hotel-detail';
            $rootScope.$broadcast('enterHotelDetail', {id: id});
        }
        $ctrl.enterOrderDetail = function(id) {
            $rootScope.state = 'order-detail';
            $rootScope.$broadcast('enterOrderDetail', {id: id});
        }
    }
]) 
.controller('orderDetailCtrl', ['$scope', '$rootScope', 'api', 
    function($scope, $rootScope, api) {
        $ctrl.orderStates = api.getOrderStates();
        $ctrl.state = $ctrl.orderStates[0];
    }
])
.controller('userListCtrl', ['$scope', '$rootScope', 'api', 
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.userList = api.getUserList();
        $ctrl.countPerPage = 10;
        $ctrl.page = 1;
        $ctrl.maxCount = 5;
        $ctrl.changePage = function() {
            $ctrl.startIndex = ($ctrl.page - 1) * $ctrl.countPerPage;
        }
        var selectAll = false;
        $ctrl.toggleAllSelect = function() {
            selectAll = !selectAll;
            $ctrl.hotelList.forEach(h => { h.$$selected = selectAll; });
        }
        $ctrl.enterUserDetail = function(id) {
            $rootScope.state = 'user-detail';
            $rootScope.$broadcast('enterUserDetail', {id: id});
        }
    }
])
.controller('userDetailCtrl', ['$scope', '$rootScope', 'api', 
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.userDetail = api.getUserDetail(0);
        $rootScope.$on('enterUserDetail', function(evt, data) {
            $ctrl.userDetail = api.getUserDetail(data.id);
        });
        $ctrl.orderList = api.getOrderList();
        $ctrl.orderMaxCount = 3;
        $ctrl.enterOrderDetail = function(id) {
            $rootScope.state = 'order-detail';
            $rootScope.$broadcast('enterOrderDetail', {id: id});
        }
    }
])
.controller('orderDetailCtrl', ['$scope', '$rootScope', 'api',
    function($scope, $rootScope, api) {
        var $ctrl = this;
        $ctrl.orderDetail = api.getOrderDetail(0);
        $ctrl.orderStates = api.getOrderStates();
        $rootScope.$on('enterOrderDetail', function(evt, data) {
            $ctrl.orderDetail = api.getOrderDetail(data.id);
        });
        $ctrl.enterUserDetail = function(id) {
            $rootScope.state = 'user-detail';
            $rootScope.$broadcast('enterUserDetail', {id: id});
        }
        $ctrl.enterHotelDetail = function(id) {
            $rootScope.state = 'hotel-detail';
            $rootScope.$broadcast('enterHotelDetail', {id: id});
        }  
    }
])*/