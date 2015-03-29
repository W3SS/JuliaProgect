(function(){
    var purchaseFactory = function($http){

        var factory = {};

        factory.getPurchases = function(){
            return $http.get('api/purchases');
        };

        factory.getAllGoods = function(){
            return $http.get('api/goods');
        };

        factory.getAllShops = function(){
            return $http.get('api/shops');
        };

        factory.getItem = function(id, path){
            return $http.get(path+ id);
        };

        factory.getPurchasesForGood = function(id){
            return $http.get('api/goods/purchase/'+id);
        };

        factory.getPurchasesForShop = function(id){
            return $http.get('api/shops/purchase/'+id);
        };


        factory.updatePurchase = function(purchase){
            return $http.post('api/purchase', purchase);
        };

        factory.updateItem = function(item, path){
            return $http.post(path, item);
        };

        factory.deletePurchase = function(id){
            return $http.delete('api/purchase/'+ id);
        };

        factory.deleteItem = function(id, req){
            return $http.post(req.path+ id, req);
        };



        return factory;
    };
    purchaseFactory.$inject = ['$http'];
    angular.module('purchaseApp').factory('purchaseFactory', purchaseFactory);

})();