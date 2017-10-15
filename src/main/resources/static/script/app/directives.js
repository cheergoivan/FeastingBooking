angular.module('directives', ['services'])
.directive('imageGallery', function() {
    return {
        restrict: 'AE',
        template: '<div class="image-gallery-container">' 
                    + '<ul uib-carousel active="active" interval="igInterval">'
                        + '<li uib-slide index="$index" ng-repeat="image in igList" class="image-gallery-item-wrapper">'
                            + '<div class="image-gallery-hidder" ng-click="onRemove({image: image})">'
                                + '&times;'
                            + '</div>'
                            + '<img ng-src="{{image.url}}">'
                        +'</li>'
                    + '</ul>'
                + '</div>',
        replace: true,
        scope: {
            igList: '=',
            igInterval: '@',
            onRemove: '&igOnRemove',
        },
        link: function(scope, elem) {
            scope.igInterval = scope.igInterval || 2000;
            scope.active = 0;
        }
    };
})
.directive('niceCheckbox', function() {
    return {
        restrict: 'E',
        template: '<div class="checkbox-wrapper">'
                    + '<div class="checkbox-view" ng-class="{\'selected\':niceModel}" ng-click="toggle()"><div>'
                + '</div>',
        replace: true,
        controller: ['$scope', function($scope) {
            $scope.toggle = function() {
                $scope.niceModel = $scope.niceModel? false: true;
            };
        }],
        scope: {
            niceModel: '='
        }
    };
})
.directive('imageList', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="image-list">'
                    + '<div class="image-container-wrapper" ng-repeat="image in images">'
                        + '<div class="image-container">'
                            + '<div class="image-container-content">'
                                + '<div class="image-container-control">'
                                    + '<div class="image-remove" ng-click="onRemove({url: image})" title="刪除圖片"><span class="glyphicon glyphicon-trash"></span></div>'
                                + '</div>'
                                + '<img ng-src="{{image}}">'
                            + '</div>'
                        +'</div>'
                    + '</div>'
                    + '<div class="image-container-wrapper">'
                        + '<div class="image-container">'
                            + '<div class="image-container-content" title="添加圖片" ngf-select ngf-multiple="true" ngf-change="$files.length> 0 && onAdd({files: $files})">' 
                                + '<div class="image-plus-wrapper"><span class="glyphicon glyphicon-plus"></span></div>'
                            + '</div>'
                        + '</div>'
                    +'</div>'
                 +'</div>',
        scope: {    
            images: '=',
            onRemove: '&',
            onAdd: '&'
        },
        link: function(elem, scope) {
        	scope.onchange = function(files) {
        		alert(files.length);
        	}
        }
    }
})
.directive('labelInputText', function() {
    return {
        restrict: 'AE',
        replace: true,
        template:  '<div class="label-input-text-container">'
                        + '<label class="control-label">{{label}}</label>'
                        + '<input ng-model="model" type="text" class="form-control" required/>'
                   +'</div>',
        scope: {
            model: "=",
            label: '@'
        }
    }
})
.directive('labelPlainText', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="label-plain-text-container">'
                    + '<label class="control-label">{{label}}</label>'
                    + '<span>{{model}}</span>'
                +'</div>',
        scope: {
            model: '=',
            label: '@'
        }
    }
})
.directive('labelTextarea', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="label-plain-text-container">'
                    + '<label class="control-label">{{label}}</label>'
                    + '<textarea ng-model="model" class="form-control" rows="7"></textarea>'
                +'</div>',
        scope: {
            model: '=',
            label: '@'
        }
    }
})
.directive('labelCheckbox', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="label-plain-text-container">'
                    + '<label class="control-label">{{label}}</label>'
                    + '<nice-checkbox nice-model="model"></nice-checkbox>'
                +'</div>',
        scope: {
            model: '=',
            label: '@'
        }
    }
})
.directive('labelPassword', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="label-plain-text-container">'
                    + '<label class="control-label">{{label}}</label>'
                    + '<input ng-model="model" class="form-control" type="password" required>'
                +'</div>',
        scope: {
            model: '=',
            label: '@'
        }
    }
})
.directive('labelRange', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div class="label-range-container">'
					+ '<label class="control-label">{{::label}}</label>'
					+ '<div class="range-container">'
						+ '<input type="number" ng-model="from" class="form-control" required>'
						+ '<span class="range">-</span>'
						+ '<input type="number" ng-model="to" class="form-control" required>'
					+ '</div>'
				+ '</div>',
		scope: {
			from: '=',
			to: '=',
			label: '@'
		}
	}
})
.directive('labelAddress', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div class="label-address">'
					+ '<label class="control-label">{{::label}}</label>'
					+ '<div class="address-container">'
						+ '<div class="city-and-district-wrapper">'
							+ '<input type="text" ng-model="city" class="form-control" placeholder="市" required>'
							+ '<input type="text" ng-model="district" class="form-control" placeholder="區" required>'
						+ '</div>'
						+ '<div class="street-wrapper">'
							+ '<input type="text" ng-model="street" class="form-control" placeholder="街道" required>'
						+ '</div>'
					+ '</div>'
                + '</div>',
        scope: {
            city: '=',
            district: '=',
            street: '=',
            label: '@'
        }
	}
})
.directive('labelInputNumber', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div>'
					+ '<label class="control-label">{{::label}}</label>'
					+ '<input ng-model="model" min="{{min}}" max="{{max}}" step="{{step}}" type="number" class="form-control" required>'
				+ '</div>'
					,
		scope: {
			min: '=',
			max: '=',
			model: '=',
			step: '=',
			label: '@'
		},
		link: function(elem, scope) {
			scope.min = parseFloat(scope.min) || 0;
			scope.max = parseFloat(scope.max) || 99999999;
			if(scope.min > scope.max) {
				var temp = scope.max;
				scope.max = scope.min;
				scope.min = temp;
			}
			scope.step = parseFloat(scope) || 1;
		}
	}
})
.directive('labelInputEmail', function() {
    return {
        restrict: 'AE',
        replace: true,
        template:  '<div class="label-input-text-container">'
                        + '<label class="control-label">{{label}}</label>'
                        + '<input ng-model="model" type="email" class="form-control" required/>'
                   +'</div>',
        scope: {
            model: "=",
            label: '@'
        }
    }
})
.directive('editableList', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div>'
					+ '<label class="control-label">{{::label}}</label>'
					+ '<div class="editable-list-container">'
						+ '<ul>'
							+ '<li class="editable-list-item btn btn-warning" ng-repeat="info in infos track by $index">{{info}}<span title="刪除" ng-click="infos.splice($index, 1)">&times</span></li>'
							+ '<li>'
								+ '<span class="glyphicon glyphicon-plus btn" style="padding: 6px 9px;" ng-click="editMode()" ng-class="{\'hidden\': mode === 1}" title="添加"></span>'
								+ '<input id="editableList" type="text" ng-model="candidateInfo" class="form-control" ng-class="{\'hidden\': mode === 0}"/>'
							+ '</li>'
						+ '</ul>'
					+ '</div>'
				+ '</div>',
		scope: {
			label: '@',
			infos: '='
		},
		controller: ['$scope', 'alertManager', function($scope, alertManager) {
			//mode 0 is scan, 1 is edit
			$scope.editMode = function() {
				$scope.mode = 1;
				$('#editableList').focus();
			};
			$('#editableList').blur(function() {
				if(!$scope.candidateInfo) {
					$scope.$apply(function() {
						alertManager.addAlert('warning', '不能爲空');
					});
					return;
				}
				if($scope.infos.indexOf($scope.candidateInfo) !== -1) {
					$scope.$apply(function() {
						alertManager.addAlert('warning', '不能重複');
					});
					return;
				}
				if($scope.candidateInfo.length > 20) {
					$scope.$apply(function() {
						alertManager.addAlert('warning', '長度不能超過20')
					});
					return;
				}
				$scope.$apply(function() {
					$scope.infos.push($scope.candidateInfo);
					$scope.candidateInfo = '';
					$scope.mode = 0;
				});
			});
		}],
		link: function(scope, elem) {
			scope.mode = 0;
			if(!scope.infos)
				scope.infos = [];
		}
	}
})
.directive('collapseSwitch', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div class="nav-menu-switch-container">'
					+ '<span class="glyphicon glyphicon-menu-hamburger nav-menu-switch" ng-click="cSwitch = !cSwitch" title="摺叠/展開導航欄"></span>'
				+ '</div>',
		scope: {
			cSwitch: '=',
			cSize: '='
		},
		link: function(scope, elem) {
			if(scope.cSize)
				scope.cSize = 768;
			var width = window.outerWidth;
			if(parseInt(width) < scope.cSize) {
				scope.cSwitch = true;
			}
			window.onresize = function() {
				var width = window.outerWidth;
				if(parseInt(width) < scope.cSize) {
					scope.$apply(function() {
						scope.cSwitch = true;
					});
				}
			} 
		}
	}
})