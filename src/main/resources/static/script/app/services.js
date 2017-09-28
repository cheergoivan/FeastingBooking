angular.module('services', ['ui.bootstrap', 'ngFileUpload'])
.service('constants', [function() {
	return {
		apiPrefix: '/api',
		authTokenName: 'FeastBookingAuthToken'
	}
}])
.service('alertManager', ['$rootScope', '$timeout', function($rootScope, $timeout) {
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
			if(level !== 'danger') {
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
.service('apiService', ['$http', '$q', '$window', 'constants', 'Upload', function($http, $q, $window, constants, Upload) {
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
		return Upload.upload({
			url: url,
			data: data
		});
	}
	var urlPrefix = constants.apiPrefix;
	// account api
	function signin(account) {
		return promiseFactory('POST', `${urlPrefix}/auth/token`, null, account);
	}
	// hotel api
	function getHotelList() {
		return promiseFactory('GET', `${urlPrefix}/hotels/all`);
    }
	function createHotel(hotel) {
		return promiseFactory('POST', `${urlPrefix}/hotels`, null, hotel);
	}
	function getHotelDetail(hotelId) {
		return promiseFactory('GET', `${urlPrefix}/hotels/${hotelId}`);
	}
	function updateHotelDetail(hotelDetail) {
		var hotelId = hotelDetail.id;
		return promiseFactory('POST', `${urlPrefix}/hotels/${hotelId}`, null, hotelDetail);
	}
	function updateHotelDetailWithImage(hotelDetail) {
		var hotelId = hotelDetail.id;
		return fileUploadFactory(`${urlPrefix}/hotels/${hotelId}`, hotelDetail);
	}
    return {
    	signin: signin,
        getHotelList: getHotelList,
        createHotel: createHotel,
        getHotelDetail: getHotelDetail,
        updateHotelDetail: updateHotelDetail,
        updateHotelDetailWithImage: updateHotelDetailWithImage
    };
}])
.service('modals', ['$uibModal', function($uibModal) {
	function messageModal(title, message) {
		var modalInstance = $uibModal.open({
			template: '<div class="modal-container">'
						+ '<header class="modal-header">'
							+ '<h3>{{::title}}<span class="modal-cross" ng-click="cancel()"><span></h3>'
						+ '</header>'
						+ '<div class="modal-body"></div>'
						+ '<footer class="modal-footer">'
							+ '<button class="btn btn-primary" ng-click="confirm()">確認</button>'
							+ '<button class="btn btn-default" ng-click="cancel()">取消</button>'
						+ '</footer>'
					+ '</div>',
			controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
				$socpe.title = title;
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
}])