angular.module("controller.advertisement", ["services", "ngFileUpload"])
.controller("advertisementCtrl", ["$scope", "modals", "apiService", "alertManager", function($scope, modals, apiService, alertManager) {
	$scope.data = {};
	$scope.data.adsList = [];
	$scope.data.urlList = [];
	getAdsList();
	function getAdsList() {
		apiService.getAdsList().then(function(response) {
			$scope.data.adsList = response;
		}, function(response) {
			alertManager.addAlert("danger", response);
		});
	}
	$scope.data.addAds= function(files) {
		var images = { files: files};
		apiService.addAds(images).then(function(response) {
			$scope.data.adsList = $scope.data.adsList.concat(response.data);
			alertManager.addAlert("success", "成功添加廣告");
		}, function(response) {
			alertManager.addAlert("danger", "無法添加廣告");
			console.log(response);
		});
	};
	$scope.data.deleteAds = function(url) {
		var deletedObj = $scope.data.adsList.find(function(ads) {
			return ads.pictureUrl === url;
		});
		if(!deletedObj)
			alertManager.addAlert("danger", "無法找到被刪除的廣告");
		var id = deletedObj.id;
		apiService.deleteAds(id).then(function(response) {
			$scope.data.adsList = $scope.data.adsList.filter(function(ads) {
				return ads.pictureUrl !== url;
			});
			alertManager.addAlert("success", "成功刪除廣告");
		}, function(response) {
			alertManager.addAlert("danger", "無法刪除廣告");
			console.log(response);
		});
	};
	$scope.$watch(function() {
		return $scope.data.adsList;
	}, function(newValue) {
		if(newValue) {
			$scope.data.urlList = $scope.data.adsList.map(function(ads) {
				return ads.pictureUrl;
			});
		}
	}, true);
}])
	
	