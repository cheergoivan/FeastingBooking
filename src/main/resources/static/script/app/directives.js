angular.module('directives', [])
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
                                    + '<div class="image-zoom" ng-click="onZoom({id: image.id})" title="放大圖片"><span class="glyphicon glyphicon-zoom-in"></span></div>'
                                    + '<div class="image-remove" ng-click="onRemove({id: image.id})" title="刪除圖片"><span class="glyphicon glyphicon-trash"></span></div>'
                                + '</div>'
                                + '<img ng-src="{{image.url}}">'
                            + '</div>'
                        +'</div>'
                    + '</div>'
                    + '<div class="image-container-wrapper">'
                        + '<div class="image-container">'
                            + '<div class="image-container-content" ng-click="onAdd()" title="添加圖片">' 
                                + '<div class="image-plus-wrapper"><span class="glyphicon glyphicon-plus"></span></div>'
                            + '</div>'
                        + '</div>'
                    +'</div>'
                 +'</div>',
        scope: {    
            images: '=',
            onRemove: '&',
            onZoom: '&',
            onAdd: '&'
        }
    }
})
.directive('labelInputText', function() {
    return {
        restrict: 'AE',
        replace: true,
        template:  '<div class="label-input-text-container">'
                        + '<label class="control-label">{{label}}</label>'
                        + '<input ng-model="model" type="text" class="form-control"/>'
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
                    + '<textarea ng-model="model" class="form-control" rows="4"></textarea>'
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
                    + '<input ng-model="model" class="form-control" type="password">'
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
						+ '<input type="number" ng-model="from" class="form-control">'
						+ '<span class="range">~</span>'
						+ '<input type="number" ng-model="to" class="form-control">'
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
						+ '<input type="text" ng-model="city" class="form-control" placeholder="市">'
						+ '<input type="text" ng-model="district" class="form-control" placeholder="區">'
						+ '<input type="text" ng-model="street" class="form-control" placeholder="街道">'
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
.directive('labelNumberRange', function() {
	return {
		restrict: 'AE',
		replace: true,
		template: '<div class="label-number-range">'
					+ '<label class="control-label"></label>'
					+ '<div class="number-range-container">'
						+ '<input type="number" ng-model="from" class="form-control">'
						+ '<input type="number" ng-model="to" class="form-control">'
					+ '</div>'
				+ '</div>',
		scope: {
			from: '=',
			to: '=',
			min: '@',
			max: '@'，
			step: '@',
			label: '@'
		},
		link: function(elem, scope) {
			scope.min = parseFloat(scope.min) || 0;
			scope.max = parseFloat(scope.max) || 99999999;
			if(scope.min < scope.max) {
				
			}
			scope.step = parseFloat(scope)
		}
	}
})