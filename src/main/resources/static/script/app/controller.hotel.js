angular.module("controller.hotel", ["services", "ui.router", "ngFileUpload"])
	.controller("hotelsCtrl", ["$scope", "$state", "$rootScope", "constants", function($scope, $state, $rootScope, constants) {
		$scope.data = {};
		$rootScope.state = $state.current;
		$scope.data.goto = function goto(state) {
			$state.go(constants.hotelsStatePrefix + "." + state);
		};
	}])
    .controller("hotelListCtrl", ["$scope", "$state", "apiService", "alertManager", "modals", "recommendList", function ($scope, $state, apiService, alertManager, modals, recommendList) {
        function initHotelList() {
        	apiService.getHotelList().then(function(response) {
        		$scope.data.hotelList = response;
        		$scope.data.hotelList.forEach(function(hotel) {
        			if(recommendList.find(function(recommend) {
        				return recommend.hotelId === hotel.id;
        			})) {
        				hotel.$$recommend = true;
        			}
        		});
        	}, function(response) {
        		$scope.data.hotelList = [];
        		alertManager.addAlert('danger', response);
        	});
        };
        initHotelList();
        $scope.data = {
            countPerPage: 10,
            page: 1,
            maxCount: 5
        };
        $scope.data.changePage = function() {
            $scope.data.startIndex = ($scope.data.page - 1) * $scope.data.countPerPage;
        };
        $scope.data.goDetail = function(id) {
        	$state.go('FeastBooking.hotel.info', { hotelId: id });
        };
        $scope.$watch(function() {
            return $scope.data.chooseAll;
        }, function(newVal) {
            angular.forEach($scope.data.hotelList, function(hotel) {
                hotel.$$selected = newVal;
            });
        });
        $scope.data.addHotel = function() {
        	$state.go('FeastBooking.hotels.newHotel');
        };
        $scope.data.deleteHotels = function() {
        	var ids = $scope.data.hotelList.filter(function(hotel) {
        		return hotel.$$selected;
        	}).map(function(selected) {
        		return selected.id;
        	});
        	if(!ids || ids.length === 0) {
        		alertManager.addAlert("warning", "請選擇要刪除的酒店");
        		return;
        	};
        	apiService.deleteHotels({ ids: ids}).then(function() {
        		alertManager.addAlert("success", "成功刪除酒店");
        		$scope.data.hotelList = $scope.data.hotelList.filter(function(hotel) {
        			return !hotel.$$selected;
        		});
        	}, function(response) {
        		alertManager.addAlert("error", response);
        	});
        };
    }])
    .controller("hotelDetailCtrl", ["$state", "$scope", "apiService", "hotelDetail", "alertManager", "constants", "modals", function($state, $scope, apiService, hotelDetail, alertManager, constants, modals, recommendList) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	$scope.data.goto = function(state) {
            var fullState = constants.hotelStatePrefix + '.' + state;
    		if(fullState.toLowerCase() !== $state.current.name.toLowerCase()) {
                $state.go(fullState, $scope.data.hotelDetail.id);
            }
    	};
    	$scope.data.isBanquetActive = function(banquetId) {
    		return window.location.href.substring(window.location.href.indexOf("banquet/")).endsWith(banquetId);
    	}
    	$scope.data.isFeastActive = function(feastId) {
    		return window.location.href.substring(window.location.href.indexOf("feast/")).endsWith(feastId);
    	}
    	$scope.data.createNewBanquet = function() {
    		$state.go(constants.hotelStatePrefix + "." + constants.hotelCreateBanquetState, $scope.data.hotelDetail.id);
    	}
    	$scope.data.createNewFeast = function() {
    		$state.go(constants.hotelStatePrefix + "." + constants.hotelCreateFeastState, $scope.data.hotelDetail.id);
    	}
    	$scope.data.goBanquet = function(banquetId) {
    		$state.go(constants.hotelStatePrefix + "." + constants.hotelBanquetState, {hotelId: $scope.data.hotelDetail.id, banquetId: banquetId});
    	}
    	$scope.data.goFeast = function(feastId) {
    		$state.go(constants.hotelStatePrefix + "." + constants.hotelFeastState, {hotelId: $scope.data.hotelDetail.id, feastId: feastId});
    	}
    	$scope.data.removeBanquet = function(banquetId, $event) {
    		modals.messageModal("確認", "確認刪除這個宴會廳?").then(function() {
    			apiService.removeBanquet(banquetId).then(function() {
    				$scope.data.hotelDetail.banquetHalls = $scope.data.hotelDetail.banquetHalls.filter(function(banquet) {
    					return banquet.id !== banquetId;
    				});
    				alertManager.addAlert("success", "成功刪除宴會廳");
    				$state.go(constants.hotelStatePrefix + "." + constants.hotelInfoState);
    			}, function(response) {
    				alertManager.addAlert("danger", response);
    			});
    		});
    		$event.stopPropagation();
    	}
    	$scope.data.removeFeast = function(feastId, $event) {
    		modals.messageModal("確認", "確認刪除這個菜單?").then(function() {
    			apiService.removeFeast(feastId).then(function() {
    				$scope.data.hotelDetail.feasts = $scope.data.hotelDetail.feasts.filter(function(feast) {
    					return feast.id !== feastId;
    				});
    				alertManager.addAlert("success", "成功刪除菜單");
    				$state.go(constants.hotelStatePrefix + "." + constants.hotelInfoState);
    			}, function(response) {
    				alertManager.addAlert("danger", response);
    			});
    		});
    	}
    }])
    .controller("hotelInfoCtrl", ["$scope", "apiService", "hotelDetail", "alertManager", "Upload", "modals", "recommendList", function($scope, apiService, hotelDetail, alertManager, Upload, modals, recommendList) {
    	$scope.data = { hotelDetail : angular.copy(hotelDetail) };
    	$scope.data.recommendInfo = {};
    	$scope.data.hotelName = hotelDetail.name;
    	var recommend = recommendList.find(function(recommend) {
			return recommend.hotelId === hotelDetail.id;
		});
    	if(recommend) {
    		$scope.data.recommendInfo.isRecommend = true;
    		$scope.data.recommendInfo.recommendDetail = recommend;
    		$scope.data.recommendInfo.title = "取消推薦";
    	} else {
    		$scope.data.recommendInfo.isRecommend = false;
    		$scope.data.recommendInfo.title = "設爲推薦";
    	}
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
    			$scope.data.hotelName = hotelDetail.name;
    		}, function(response) {
    			alertManager.addAlert('danger', response);
    		});
    	};
    	$scope.data.addHotelImage = function(files) {
    		if(files && files.length) {
    			apiService.addHotelImages(hotelDetail.id, { files: files}).then(function(response) {
    				alertManager.addAlert('success', '成功添加圖片。');
    				var urls = response.data;
    				if(urls && urls.length) {
    					hotelDetail.pictureUrls = hotelDetail.pictureUrls.concat(urls);
    					$scope.data.hotelDetail.pictureUrls = $scope.data.hotelDetail.pictureUrls.concat(urls);
    				}
    			}, function(response) {
    				alertManager.addAlert('danger', response);
    			});
    		}
    	};
    	$scope.data.deleteImage = function(url) {
    		modals.messageModal('確認', '刪除這張圖片?').then(function() {
    			var names = [url.substring(url.lastIndexOf('/') + 1)];
        		apiService.deleteHotelImage(hotelDetail.id, {names : names}).then(function(response) {
        			alertManager.addAlert('success', '成功刪除圖片。');
        			hotelDetail.pictureUrls.splice(hotelDetail.pictureUrls.indexOf(url), 1);
        			$scope.data.hotelDetail.pictureUrls.splice($scope.data.hotelDetail.pictureUrls.indexOf(url), 1);
        		}, function(response) {
        			alertManager.addAlert('danger', response);
        		});
    		});
    	};
    	$scope.data.revertChange = function() {
    		$scope.data.hotelDetail = hotelDetail;
    	};
    	$scope.data.toggleRecommend = function() {
    		if($scope.data.recommendInfo.isRecommend) {
    			apiService.removeReccomndation($scope.data.recommendInfo.recommendDetail.id).then(function() {
    				$scope.data.recommendInfo = {
    					isRecommend: false,
    					title: "設爲推薦"
    				}
    				alertManager.addAlert("success", "成功取消推薦");
    			}, function(response) {
    				console.log(response);
    				alertManager.addAlert("danger", "取消推薦失敗");
    			});
    		} else {
    			modals.addRecommendationModal(hotelDetail).then(function(recommendation) {
    				apiService.addRecommendation(recommendation).then(function(response) {
    					$scope.data.recommendInfo = {
    	    				isRecommend: true,
    	    				recommendDetail: response.data,
    	    				title: "取消推薦"
    					}
    					alertManager.addAlert("success", "成功設置推薦");
    				}, function(response) {
    					console.log(response);
    					alertManager.addAlert("danger", "設置推薦失敗");
    				})
    			});
    		}
    	}
    }])
    .controller('hotelBanquetCtrl', ['$scope', '$state', 'apiService', 'alertManager', 'hotelDetail', 'banquet', 'modals', function($scope, $state, apiService, alertManager, hotelDetail, banquet, modals) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	function initBanquet(banquet) {
        	$scope.data.banquet = banquet;
        	$scope.data.banquet.minimumTables = banquet.tableRange[0];
        	$scope.data.banquet.maximumTables = banquet.tableRange[1];
    	}
    	initBanquet(banquet);
    	$scope.data.updateBanquet = function() {
    		$scope.data.banquet.extraInfo = $scope.data.banquet.extraInfos.join(";");
    		apiService.updateBanquet($scope.data.banquet).then(function(response) {
    			alertManager.addAlert('success', '成功更新宴會聼。');
    		}, function(response) {
    			alertManager.addAlert('danger', response);
    		});
    	}
    	$scope.data.addBanquetImage = function(files) {
    		if(files && files.length) {
    			apiService.addBanquetImages(banquet.id, { files: files}).then(function(response) {
    				alertManager.addAlert('success', '成功添加圖片。');
    				var urls = response.data;
    				if(urls && urls.length) {
    					banquet.pictureUrls = banquet.pictureUrls.concat(urls);
    				}
    			}, function(response) {
    				alertManager.addAlert('danger', response);
    			});
    		}
    	}
    	$scope.data.removeBanquetImage = function(url) {
    		modals.messageModal('確認', '刪除這張圖片').then(function() {
        		var names = [url.substring(url.lastIndexOf('/') + 1)];
        		apiService.removeBanquetImage($scope.data.banquet.id, { names: names }).then(function() {
        			alertManager.addAlert('success', '成功刪除圖片');
        			$scope.data.banquet.pictureUrls = $scope.data.banquet.pictureUrls.filter(function(src) {
        				return src !== url;
        			});
        		}, function(response) {
        			alertManager.addAlert(response);
        		});
    		});
    	}
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
    .controller("banquetCreateCtrl", ["$scope", "$state", "apiService", "alertManager", "hotelDetail", "constants", function($scope, $state, apiService, alertManager, hotelDetail, constants) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	$scope.data.banquet = {};
        $scope.data.createBanquet = function() {
        	$scope.data.banquet.extraInfo = $scope.data.banquet.extraInfos.join(";") || "";
        	apiService.createBanquet($scope.data.hotelDetail.id, $scope.data.banquet).then(function(banquetId) {
        		$scope.data.banquet.id = banquetId;
        		hotelDetail.banquetHalls.push($scope.data.banquet);
        		alertManager.addAlert("success", "成功創建宴會廳");
        		$state.go(constants.hotelStatePrefix + "." + constants.hotelBanquetState, {hotelId: $scope.data.hotelDetail.id, banquetId: banquetId});
        	}, function(response) {
        		alertManager.addAlert("danger", response);
        	})
        };
    }])
    .controller("feastCreateCtrl", ["$scope", "$state", "apiService", "alertManager", "hotelDetail", "constants", function($scope, $state, apiService, alertManager, hotelDetail, constants) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	$scope.data.feast = {};
    	$scope.data.createFeast = function() {
    		$scope.data.feast.courses = $scope.data.feast.courseList.join(";") || "";
    		apiService.createFeast($scope.data.hotelDetail.id, $scope.data.feast).then(function(feastId) {
    			$scope.data.feast.id = feastId;
    			alertManager.addAlert("success", "成功創建菜單");
    			hotelDetail.feasts.push($scope.data.feast);
    			$state.go(constants.hotelStatePrefix + "." + constants.hotelFeastState, {hotelId: $scope.data.hotelDetail.id, feastId: feastId});
    		}, function(response) {
    			alertManager.addAlert("danger", response);
    		});
    	}
    }])
    .controller("hotelFeastCtrl", ["$scope", "$state", "apiService", "alertManager", "hotelDetail", "feast", 'modals', function($scope, $state, apiService, alertManager, hotelDetail, feast, modals) {
    	$scope.data = {};
    	$scope.data.hotelDetail = hotelDetail;
    	$scope.data.feast = feast;
    	$scope.data.updateFeast = function() {
    		var copyOfFeast = angular.copy($scope.data.feast);
    		copyOfFeast.courses = copyOfFeast.courses.join(";");
    		apiService.updateFeast(copyOfFeast).then(function(response) {
    			alertManager.addAlert('success', '成功更新宴會聼。');
    		}, function(response) {
    			alertManager.addAlert('danger', response);
    		});
    	};
    	$scope.data.addFeastImage = function(files) {
    		if(files && files.length) {
    			apiService.addFeastImages(feast.id, { files: files}).then(function(response) {
    				alertManager.addAlert('success', '成功添加圖片。');
    				var urls = response.data;
    				if(urls && urls.length) {
    					feast.pictureUrls = feast.pictureUrls.concat(urls);
    				}
    			}, function(response) {
    				alertManager.addAlert('danger', response);
    			});
    		}
    	}
    	$scope.data.removeFeastImage = function(url) {
    		modals.messageModal('確認', '刪除這張圖片').then(function() {
        		var names = [url.substring(url.lastIndexOf('/') + 1)];
        		apiService.removeFeastImage($scope.data.feast.id, { names: names }).then(function() {
        			alertManager.addAlert('success', '成功刪除圖片');
        			$scope.data.feast.pictureUrls = $scope.data.feast.pictureUrls.filter(function(src) {
        				return src !== url;
        			});
        		}, function(response) {
        			alertManager.addAlert("danger", "無法刪除圖片");
        		});
    		});
    	}
    }])