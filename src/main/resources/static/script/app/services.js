angular.module("services", ["ui.bootstrap", "ngFileUpload"])
.constant("constants", {
	apiPrefix: "/api",
	authTokenName: "FeastBookingAuthToken",
	feastBookingPrefix: "FeastBooking",
	hotelsStatePrefix: "FeastBooking.hotels",
	hotelsListState: "list",
	hotelsRecommondationState: "recommendation",
	hotelStatePrefix: "FeastBooking.hotel",
	createHotelState: "newHotel",
	hotelInfoState: "info",
	hotelBanquetState: "banquet",
	hotelDiscountState: "discount",
	hotelOrderState: "order",
	hotelCommentState: "commont",
	hotelCreateBanquetState: "newBanquet",
	hotelFeastState: "feast",
	hotelCreateFeastState: "newFeast",
	advertisementState: "advertisement",
	ordersState: "orders"
})
.service("alertManager", ["$rootScope", "$timeout", function($rootScope, $timeout) {
	var timeout = 5000;
	function addAlert(level, message) {
		if(!level)
			level = "warning";
		switch(level.toLowerCase()) {
			case "success": level = "success";break;
			case "warning": level = "warning";break;
			case "danger": level = "danger";break;
			default: level = "warning"
		}
		if(message) {
			var notification = {
				level: level,
				message: message
			};
			$rootScope.alerts.push(notification);
			if(level !== "danger") {
				$timeout(function() {
					$rootScope.alerts.splice($rootScope.alerts.indexOf(notification), 1);
				}, timeout);
			}
		} else {
			console.log("No alert message!");
		}
	}
	function removeAlert(index) {
		$rootScope.alerts.splice(index, 1);
	}
	return {
		addAlert: addAlert,
		removeAlert: removeAlert
	}
}])
.service("apiService", ["$http", "$q", "$window", "constants", "Upload", function($http, $q, $window, constants, Upload) {
	function promiseFactory(method, url, headers, data) {
		var defered = $q.defer();
		if(!method) {
			method = "GET";
		} else {
			switch(method.toLowerCase()) {
			case "get": method = "GET";break;
			case "post": method = "POST";break;
			case "delete": method = "DELETE"; break;
			case "put": method = "PUT";break;
			default: method = "GET";
			}
		}
		headers = headers || {};
		var token = $window.localStorage[constants.authTokenName];
		if(!token)
			alert("No token.");
		headers["Authorization"] = token 
		$http({ method: method, url: url, headers: headers, data: data}).then(function(response) {
			defered.resolve(response.data);
		}, function(response) {
			defered.reject(response);
		});
		return defered.promise;
	}
	function fileUploadFactory(url, data) {
		var token = $window.localStorage[constants.authTokenName];
		if(!token)
			alert("No token.");
		var headers = {
			"Authorization": token
		};
		return Upload.upload({
			headers: headers,
			url: url,
			data: data
		});
	}
	var urlPrefix = constants.apiPrefix;
	// account api
	function signin(account) {
		return promiseFactory("POST", `${urlPrefix}/auth/token`, null, account);
	}
	// hotel api
	function getHotelList() {
		return promiseFactory("GET", `${urlPrefix}/hotels/all`);
    }
	function createHotel(hotel) {
		return promiseFactory("POST", `${urlPrefix}/hotels`, null, hotel);
	}
	function getHotelDetail(hotelId) {
		return promiseFactory("GET", `${urlPrefix}/hotels/${hotelId}`);
	}
	function updateHotelDetail(hotelDetail) {
		var hotelId = hotelDetail.id;
		return promiseFactory("PUT", `${urlPrefix}/hotels/${hotelId}`, null, hotelDetail);
	}
	function addHotelImages(hotelId, images) {
		return fileUploadFactory(`${urlPrefix}/hotels/${hotelId}/pictures`, images);
	}
	function deleteHotelImage(hotelId, names) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/hotels/${hotelId}/pictures`, headers, names);
	}
	function deleteHotels(ids) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory('DELETE', `${urlPrefix}/hotels`, headers, ids);
	}
	function createBanquet(hotelId, banquet) {
		return promiseFactory("POST", `${urlPrefix}/hotels/${hotelId}/banquet_halls`, null, banquet);
	}
	function getBanquetHall(hallId) {
		return promiseFactory("GET", `${urlPrefix}/banquet_halls/${hallId}`);
	}
	function addBanquetImages(banquetId, images) {
		return fileUploadFactory(`${urlPrefix}/banquet_halls/${banquetId}/pictures`, images);
	}
	function removeBanquetImage(banquetId, names) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/banquet_halls/${banquetId}/pictures`, headers, names);
	}
	function updateBanquet(banquet) {
		var banquetId = banquet.id;
		return promiseFactory("PUT", `${urlPrefix}/banquet_halls/${banquetId}`, null, banquet);
	}
	function createFeast(hotelId, feast) {
		return promiseFactory("POST", `${urlPrefix}/hotels/${hotelId}/feasts`, null, feast);
	}
	function getFeast(feastId) {
		return promiseFactory("GET", `${urlPrefix}/feasts/${feastId}`);
	}
	function updateFeast(feast) {
		var feastId = feast.id;
		return promiseFactory("PUT", `${urlPrefix}/feasts/${feastId}`, null, feast);
	}
	function addFeastImages(feastId, images) {
		return fileUploadFactory(`${urlPrefix}/feasts/${feastId}/pictures`, images);
	}
	function removeFeastImage(feastId, names) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/feasts/${feastId}/pictures`, headers, names);
	}
	function removeBanquet(banquetId) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/banquet_halls/${banquetId}`, headers);
	}
	function removeFeast(feastId) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/feasts/${feastId}`, headers);
	}
	function getAdsList() {
		return promiseFactory("GET", `${urlPrefix}/advertisements`);
	}
	function addAds(images) {
		return fileUploadFactory(`${urlPrefix}/advertisements`, images);
	}
	function deleteAds(id) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/advertisements/${id}`, headers);
	}
	function getRecommendationList() {
		return promiseFactory("GET", `${urlPrefix}/recommendations`);
	}
	function addRecommendation(recommendation) {
		return fileUploadFactory(`${urlPrefix}/recommendations`, recommendation);
	}
	function removeReccomndation(recommendationId) {
		var headers = {"Content-Type": "application/json"};
		return promiseFactory("DELETE", `${urlPrefix}/recommendations/${recommendationId}`, headers);
	}
    return {
    	signin: signin,
        getHotelList: getHotelList,
        createHotel: createHotel,
        getHotelDetail: getHotelDetail,
        updateHotelDetail: updateHotelDetail,
        addHotelImages: addHotelImages,
        deleteHotelImage: deleteHotelImage,
        deleteHotels: deleteHotels,
        createBanquet: createBanquet,
        getBanquetHall: getBanquetHall,
        addBanquetImages: addBanquetImages,
        removeBanquetImage: removeBanquetImage,
        updateBanquet: updateBanquet,
        createFeast: createFeast,
        getFeast: getFeast,
        updateFeast: updateFeast,
        addFeastImages: addFeastImages,
        removeFeastImage: removeFeastImage,
        removeBanquet: removeBanquet,
        removeFeast: removeFeast,
        getAdsList: getAdsList,
        addAds: addAds,
        deleteAds: deleteAds,
        getRecommendationList: getRecommendationList,
        addRecommendation: addRecommendation,
        removeReccomndation: removeReccomndation
    };
}])
.service("modals", ["$uibModal", function($uibModal) {
	function messageModal(title, message) {
		var modalInstance = $uibModal.open({
			template: '<div class="modal-container">'
						+ '<header class="modal-header">'
							+ '<h3>{{::title}}<span class="modal-cross" ng-click="cancel()"><span></h3>'
						+ '</header>'
						+ '<div class="modal-body">'
							+ '<span>{{::message}}</span>'
						+ '</div>'
						+ '<footer class="modal-footer">'
							+ '<button class="btn btn-primary" ng-click="confirm()">確認</button>'
							+ '<button class="btn btn-default" ng-click="cancel()">取消</button>'
						+ '</footer>'
					+ '</div>',
			controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
				$scope.title = title;
				$scope.message = message;
				$scope.confirm = function() {
					$uibModalInstance.close();
				};
				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				};
			}],
			windowClass: "modal-window"
		});
		return modalInstance.result;
	}
	function addRecommendationModal(hotel) {
		var modalInstance = $uibModal.open({
			template: '<div class="modal-container">'
						+ '<div class="modal-header">'
							+ '<h3>設置推薦圖片<span class="modal-cross" ng-click="cancel()"><span></h3>'
						+ '</div>'
						+ '<div class="modal-body">'
							+ '<div>{{hotel.name}}</div>'
							+ '<div ngf-select ngf-multiple="false" ngf-change="addRecommendation($files)" class="btn btn-primary">添加圖片</div>'
						+ '</div>'
					+ '</div>',
			controller: ['$scope', '$uibModalInstance', 'alertManager', function($scope, $uibModalInstance, alertManager) {
				$scope.addRecommendation = function(file) {
					if(!file || file.length !== 1) {
						alertManager.addAlert('warning', '請添加一張圖片');
						$scope.cancel();
						return;
					}
					$uibModalInstance.close({ hotelId: hotel.id, file: file[0] });
				}
				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}
			}],
			wondowClass: "modal-window"
		});
		return modalInstance.result;
	}
	return {
		messageModal: messageModal,
		addRecommendationModal: addRecommendationModal
	}
}])
.service('orderState', function() {
	function stateFactory(name, index) {
		return {
			name: name,
			index: index
		}
	}
	return {
		cancelState: stateFactory("已取消", "CANCELED"),
		consultState: stateFactory("咨詢中", "CONSULTING"),
		reservedState: stateFactory("已預訂", "RESERVED"),
		feastedState: stateFactory("已擺酒", "FEASTED"),
		cashBackState: stateFactory("等待返現", "CASHBACK"),
		toBeReviewedState: stateFactory("待評價", "TO_BE_REVIEWD"),
		doneState: stateFactory("結束", "DONE")
	}
})
.service('orderDateState', function() {
	function stateFactory(name, index) {
		return {
			name: name,
			index: index
		}
	}
	return {
		last1Day: stateFactory("最近一天", 0),
		last1Week: stateFactory("最近一個禮拜", 1),
		last1Month: stateFactory("最近一個月", 2),
		last1Year: stateFactory("最近一年", 3),
		custom: stateFactory("自定義時間段", 4)
	}
})