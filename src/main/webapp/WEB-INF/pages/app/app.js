(function(){

	var app = angular.module("purchaseApp", ["ngRoute"]);
	
	app.config(function($routeProvider){
		$routeProvider
			.when('/',{
				controller: 'PurchaseController',
				templateUrl: 'app/views/purchases.html'
			})
            .when('/goods/',{
                controller: 'GoodsController',
                templateUrl: 'app/views/goods.html'
            })
			.otherwise( {redirectTo: '/'});
	});			
}());
 