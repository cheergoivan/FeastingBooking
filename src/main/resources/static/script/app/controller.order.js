angular.module("controller.order", ["services"])
    .controller("ordersCtrl", ["$scope", "orderState", "orderDateState", function($scope, orderState, orderDateState) {
    	$scope.data = {};
    	$scope.data.facetGroups = [{
    		name: "訂單狀態",
    		index: "state",
    		filters: [orderState.cancelState, orderState.consultState, orderState.reservedState, orderState.feastedState, 
    			orderState.cashBackState, orderState.toBeReviewedState, orderState.doneState],
    		$$isOpen: true
    	}, {
    		name: "訂單開始時間",
    		index: "date",
    		filters: [orderDateState.last1Day, orderDateState.last1Week, orderDateState.last1Month, orderDateState.last1Year, orderDateState.custom],
    		$$isOpen: true
    	},
    		{
        		name: "最後更新時間",
        		index: "lastModified",
        		filters: [orderDateState.last1Day, orderDateState.last1Week, orderDateState.last1Month, orderDateState.last1Year, orderDateState.custom],
        		$$isOpen: true
        	}
    	];
    	$scope.data.searchResults = [{
    		orderId: 1,
    		status: "ok",
    		hotel: "大酒店1",
    		contact: "聯係人1",
    		user: "用戶1",
    		agent: "中介人1",
    		startDate: "2016-12-31",
    		lastModefied: "2017-01-01"
    	}];
    }])