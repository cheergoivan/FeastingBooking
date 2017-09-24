angular.module('services', [])
.service('constants', [function() {
	return {
		apiPrefix: '/api'
	}
}])
.service('api', ['$http', '$q', 'constants', function($http, $q, constants) {
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
		$http({ method: method, url: url, headers: headers, data: data}).then(function(response) {
			defered.resolve(response.data);
		}, function(response) {
			defered.reject(response);
		});
		return defered.promise;
	}
	var urlPrefix = constants.apiPrefix;
	//account api
	function signin(account) {
		return promiseFactory('POST', `${urlPrefix}/auth/token`, null, account);
	}
	//hotel api
	function getHotelList() {
		return promiseFactory('GET', `${urlPrefix}/hotels/all`);
    }

    return {
    	signin: signin,
        getHotelList: getHotelList
    }
}])