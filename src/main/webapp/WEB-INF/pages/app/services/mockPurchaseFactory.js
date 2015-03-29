(function(){
    var mockPurchaseFactory = function($http){

        var factory = {};
        
        var mockGoods = [{"id":1,"name":"bridge2","description":null},{"id":2,"name":"Tv","description":null},{"id":5,"name":"телевизор","description":null},{"id":7,"name":"Морковь","description":null},{"id":8,"name":"Туалетная бумага","description":null},{"id":9,"name":"Ножницы","description":null},{"id":11,"name":"dd","description":null},{"id":12,"name":"fff","description":null},{"id":13,"name":"ведро","description":null},{"id":17,"name":"а","description":null},{"id":18,"name":"аа","description":null},{"id":22,"name":"Швабра","description":null},{"id":26,"name":"Шв","description":null},{"id":27,"name":"Барометр","description":null},{"id":29,"name":"бедро","description":null},{"id":30,"name":"Бур","description":null},{"id":31,"name":"Пыжик","description":null},{"id":32,"name":"Бурка","description":null},{"id":33,"name":"Бак","description":null},{"id":34,"name":"Бибишка","description":null},{"id":35,"name":"Белка","description":null},{"id":36,"name":"bridge3","description":null},{"id":37,"name":"bri","description":null},{"id":38,"name":"карты","description":null}];
        
        var mockShops = [{"id":1,"name":"Мега","address":null},{"id":2,"name":"Фантастика","address":null},{"id":3,"name":"Пасека","address":null},{"id":5,"name":"Рынок","address":null},{"id":6,"name":"Ларек","address":null},{"id":7,"name":"Чапок","address":null},{"id":8,"name":"33","address":null},{"id":9,"name":"222","address":null},{"id":10,"name":"Икеа","address":null},{"id":11,"name":"а","address":null},{"id":12,"name":"ррр","address":null},{"id":16,"name":"Икеаа","address":null},{"id":19,"name":"Магнит","address":null},{"id":20,"name":"Гостинный двор","address":null},{"id":21,"name":"Детский мир","address":null}];
        
        var mockPurchase = [{"purchaseId":12,"goodsId":29,"shopId":2,"price":3.0,"shopName":"Фантастика","goodsName":"бедро"},{"purchaseId":18,"goodsId":31,"shopId":20,"price":22.0,"shopName":"Гостинный двор","goodsName":"Пыжик"},{"purchaseId":23,"goodsId":33,"shopId":10,"price":3.0,"shopName":"Икеа","goodsName":"Бак"},{"purchaseId":24,"goodsId":34,"shopId":21,"price":3.0,"shopName":"Детский мир","goodsName":"Бибишка"},{"purchaseId":25,"goodsId":35,"shopId":10,"price":3.0,"shopName":"Икеа","goodsName":"Белка"},{"purchaseId":26,"goodsId":7,"shopId":2,"price":4.0,"shopName":"Фантастика","goodsName":"Морковь"},{"purchaseId":30,"goodsId":7,"shopId":1,"price":22.0,"shopName":"Мега","goodsName":"Морковь"},{"purchaseId":32,"goodsId":7,"shopId":1,"price":23.0,"shopName":"Мега","goodsName":"Морковь"},{"purchaseId":38,"goodsId":7,"shopId":6,"price":3.0,"shopName":"Ларек","goodsName":"Морковь"},{"purchaseId":12,"goodsId":7,"shopId":2,"price":3.0,"shopName":"Фантастика","goodsName":"Морковь"},{"purchaseId":18,"goodsId":7,"shopId":20,"price":22.0,"shopName":"Гостинный двор","goodsName":"Морковь"},{"purchaseId":23,"goodsId":7,"shopId":10,"price":3.0,"shopName":"Икеа","goodsName":"Морковь"},{"purchaseId":24,"goodsId":7,"shopId":21,"price":3.0,"shopName":"Детский мир","goodsName":"Морковь"},{"purchaseId":25,"goodsId":7,"shopId":10,"price":3.0,"shopName":"Икеа","goodsName":"Морковь"},{"purchaseId":26,"goodsId":7,"shopId":2,"price":4.0,"shopName":"Фантастика","goodsName":"Морковь"},{"purchaseId":30,"goodsId":7,"shopId":1,"price":22.0,"shopName":"Мега","goodsName":"Морковь"},{"purchaseId":32,"goodsId":7,"shopId":1,"price":23.0,"shopName":"Мега","goodsName":"Морковь"},{"purchaseId":38,"goodsId":5,"shopId":6,"price":3.0,"shopName":"Ларек","goodsName":"телевизор"}];
        
        
        factory.getPurchases = function(){
//            return $http.get('api/purchases');
            return mockPurchase;
        };

        factory.addPurchase = function(purchase){
            return $http.post('api/purchase', purchase);
        };

        factory.deletePurchase = function(id){
            return $http.delete('api/purchase/'+ id);
        };

        factory.getGoods = function(){
//            return $http.get('api/goods');
            return mockGoods;
        };
        factory.getShops = function(){
//            return $http.get('api/shops');
            return mockShops;
        };
        
        
        

        return factory;
    };
    mockPurchaseFactory.$inject = ['$http'];
    angular.module('purchaseApp').factory('mockPurchaseFactory', mockPurchaseFactory);

})();