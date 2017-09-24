angular.module('services', [])
.service('constants', [function() {
	return {
		apiPrefix: '/api'
	}
}])
.service('api', ['$http', '$q', 'constants', function($http, $q, constants) {
	function promiseFactory(method, url, headers, data) {
		var defer = $q.defer();
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
		return defer.promise;
	}
	var urlPrefix = constants.apiPrefix;
	function getHotelList() {
		return promiseFactory('GET', "${urlPrefix}/hotels/listHotelsForAdmin");
	}
}])
/*
    .factory('data', [function () {
        //hotel list, detail
        var hotelList = [];
        var hotelNames = ["上海思南公館酒店", "上海雅詩閣衡山服務公寓", "上海萬和昊美藝術酒店", "上海衡山路十二號豪華精選酒店", "上海鵬利輝盛閣國際公寓",
            "上海迪士尼樂員酒店", "上海材大豪生大酒店", "上海朱家角安麓酒店", "上海璞麗酒店", "上海月湖國際會議中心",
            "上海半島酒店", "上海新天地朗廷酒店", "上海外灘悅榕莊", "上海浦東文華東方酒店", "上海浦東麗思卡爾頓酒店"];
        var phoneNums = 12345678;
        var descriptions = ["位於上海黃浦區中山東壹路32號，與外灘為鄰，可盡覽外灘、黃浦江、浦東及前英國領事館花園盛景。",
            "位於新天地，俯瞰繁華景致，毗鄰淮海路商業區、人民廣場， 步行至地鐵1/10號線舉步之遙，提供超卓服務及奢華設施。",
            "位於上海市虹口區海平路，與陸家嘴金融貿易區隔江相望，可俯瞰壯麗的浦江兩岸及臨江1.8公裏的蔥郁地帶。",
            "位於浦東新區浦東南路111號，毗鄰地鐵2號線陸家嘴站。",
            "座落於陸家嘴金融貿易區中心，位於壹棟設計新穎的58層大樓之上，可俯瞰外灘全景。網友評價說“酒店相當豪華”。",]
        function initHotel() {
            for (var i = 0; i < 15; i++) {
                var hotel = {
                    id: i,
                    name: hotelNames[i],
                    contactPerson: '高允翔',
                    phone: phoneNums + i,
                    address: "南京大學鼓樓校區"
                };
                hotelList.push(hotel);
            }
        }
        initHotel();

        var hotelDetailImageRoot = "../image/hotel-detail/";
        var hotelDetailImageList = [];
        var location = "上海黄浦区思南路51号";
        function initHotelDetailImageList() {
            for (var i = 0; i < 6; i++) {
                hotelDetailImageList.push({
                    url: hotelDetailImageRoot + '/' + i + '.jpg',
                    id: i,
                });
            }
        }
        initHotelDetailImageList();

        var banquetHallList = [];
        var banquetHallNames = ["百合廳", "明珠廳", "新梅廳", "水晶廳", "琥珀廳"];
        function initbanquetHallList() {
            for (var i = 0; i < 5; i++) {
                var banquetHall = {
                    id: i,
                    name: banquetHallNames[i],
                    area: 500,
                    height: 11,
                    shape: '方形',
                    pillar: '無',
                    color: '方形',
                    welcomeArea: '大',
                    tableCount: 10 + i,
                    price: i * 100 + 500,
                    images: hotelDetailImageList
                }
                banquetHallList.push(banquetHall);
            }
        }
        initbanquetHallList();

        function getHotelDetail(id) {
            return {
                id: id,
                name: hotelNames[id],
                phone: 12345678,
                images: hotelDetailImageList,
                address: "南京大學鼓樓校區",
                contactPerson: "高允翔",
                description: descriptions[0],
                breifInfo: {
                    location: location,
                    phone: 12345678 + id,
                    recommend: true,
                    avgPrice: 5000,
                    point: 5.0,
                    description: "位於新天地，俯瞰繁華景致，毗鄰淮海路商業區、人民廣場， 步行至地鐵1/10號線舉步之遙，提供超卓服務及奢華設施。"
                },
                banquetHalls: banquetHallList,
            };
        }

        var foodList = [];
        var foodNames = ["永结同心宴", "花好月圆宴", "良缘美满宴", "珠联璧合宴", "佳偶天成宴"]
        function initFoodList() {
            for (var i = 0; i < 5; i++) {
                var food = {
                    id: i,
                    name: foodNames[i],
                    price: 1000 * i + 5000
                }
                foodList.push(food);
            }
        }
        initFoodList();

        var benefitList = [];

        var orderList = [];
        var states = ['预约', '商议', '支付订金', '上传合同', '反馈'];
        function initOrderList() {
            for (var i = 0; i < 15; i++) {
                var order = {
                    id: i,
                    state: states[Math.floor(Math.random() * 5)],
                    hotel: {
                        id: 0,
                        name: hotelNames[0]
                    }
                }
                orderList.push(order);
            }
        }
        initOrderList();

        var userList = [];
        var userNames = ['杜甫', '李白', '王维', '李贺', '白居易', '屈原', '苏轼', '陶渊明', '陆游', '曹植', '辛弃疾', '李商隐', '李煜', '谢灵运', '张若虚'];
        var roles = ['普通用户', '推荐人'];
        function initUserList() {
            for (var i = 0; i < userNames.length; i++) {
                var user = {
                    id: i,
                    name: userNames[i],
                    phone: 87654321 + i,
                    role: roles[Math.floor(Math.random() * 2)]
                }
                userList.push(user);
            }
        }
        initUserList();

        function getUserDetail(id) {
            return {
                id: id,
                name: userNames[id],
                phone: 87654321 + id,
                mail: (87654321 + id) + '@gmail.com',
                role: roles[Math.floor(Math.random() * 2)],
                gender: 'male',
                birth: new Date(),
                description: '下马饮君酒，问君何所之。君言不得意，归卧南山陲。但去莫复问，白云无尽时。'
            }
        }

        function getOrderDetail(id) {
            return {
                id: id,
                userId: 0,
                userName: userNames[0],
                hotelId: 0,
                hotelName: hotelNames[0],
                appointment: [{
                    time: new Date(),
                    message: "参观宴会厅"
                }, {
                    time: new Date(),
                    message: "选择菜单"
                }],
                discuss: [{
                    message: "商议价格",
                }, {
                    message: "商议婚礼日期"
                }],
                payment: {
                    state: "已支付",
                    cash: "100000",
                    time: new Date()
                },
                contract: {
                    location: '../image/contract/0.jpg'
                },
                feedback: [{
                    message: "非常满意，期待下次合作",
                    time: new Date()
                }]
            }
        }

        return {
            hotelList: hotelList,
            getHotelDetail: getHotelDetail,
            banquetHallList: banquetHallList,
            foodList: foodList,
            benefitList: benefitList,
            orderList: orderList,
            orderStates: states,
            userList: userList,
            getUserDetail: getUserDetail,
            getOrderDetail: getOrderDetail
        }
    }])
    .factory('api', ['data', function (data) {
        function getHotelList() {
            return data.hotelList;
        }
        function getHotelDetail(id) {
            return data.getHotelDetail(id);
        }
        function getbanquetHallList() {
            return data.banquetHallList;
        }
        function getFoodList() {
            return data.foodList;
        }
        function getBenefitList() {
            return data.benefitList;
        }
        function getOrderList() {
            return data.orderList;
        }
        function getOrderStates() {
            return data.orderStates;
        }
        function getUserList() {
            return data.userList;
        }
        function getUserDetail(id) {
            return data.getUserDetail(id);
        }
        function getOrderDetail(id) {
            return data.getOrderDetail(id);
        }
        return {
            getHotelList: getHotelList,
            getHotelDetail: getHotelDetail,
            getbanquetHallList: getbanquetHallList,
            getFoodList: getFoodList,
            getBenefitList: getBenefitList,
            getOrderList: getOrderList,
            getOrderStates: getOrderStates,
            getUserList: getUserList,
            getUserDetail: getUserDetail,
            getOrderDetail: getOrderDetail
        }
    }])
    .filter('booleanConvert', function () {
        return function (input) {
            return input ? "是" : "否";
        }
    })
    .filter('stateConvert', function () {
        return function (input) {
            switch (input) {
                case "预约":
                    return "appointment";
                case "商讨":
                    return "discuss";
                case "支付订金":
                    return "payment";
                case "上传合同":
                    return "contract";
                case "反馈":
                    return "feedback";
                default:
                    return "appointment";
            }
        }
    })*/