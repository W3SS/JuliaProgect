(function () {
    var PurchaseController = function PurchaseController($scope, $filter, purchaseFactory) {
        $scope.sortBy = 'name';
        $scope.reverse = false;
        $scope.purchases = [];
        $scope.goods = [];
        $scope.shops = [];
        $scope.selectedPurchase = null;
        var messageUtil = MessageUtil();


        function init() {
            purchaseFactory.getPurchases()
                .success(function (purchases) {
                    $scope.purchases = purchases;
                });

            purchaseFactory.getAllGoods()
                .success(function (goods) {
                    $scope.goods = goods;
                });

            purchaseFactory.getAllShops()
                .success(function (shops) {
                    $scope.shops = shops;
                });
        }
         function initEditListener(){

             $('#editPurchase').on('show.bs.modal', function (event) {
                 var purchase = $scope.selectedPurchase;
                 var modal = $(this);
                 modal.find('.modal-title').text('Изменить покупку');
                 modal.find('#editPurchaseGood [value=' + purchase.goodsId + ']').attr("selected", "selected");
                 modal.find('#editPurchasePrice').val(purchase.price);
                 modal.find('#editPurchaseShop [value=' + purchase.shopId + ']').attr("selected", "selected");
             }).on('click', '#updatePurchaseBtn', function (e) {
                     var purchase = $scope.selectedPurchase;
                     var modal = $('#editPurchase');
                     var newPurchase = $.extend(true, {}, purchase);
                     newPurchase.goodsName = modal.find('#editPurchaseGood :selected').text();
                     newPurchase.shopName = modal.find('#editPurchaseShop :selected').text();
                     newPurchase.price = modal.find('#editPurchasePrice').val();
                     purchaseFactory.updatePurchase(newPurchase).
                         success(function (data) {
                             purchase.goodsName = data.goodsName;
                             purchase.shopName = data.shopName;
                             purchase.price = data.price;
                             purchase.timestamp = data.timestamp;
                             purchase.version = data.version;
                             modal.modal('hide');
                         }).
                         error(function (data, status) {
                             console.log("data: " + data + " status: " + status);
                             messageUtil.showErrorMessage(data);
                         });

                 });
         }

        init();
        initEditListener();

        $scope.doSort = function (propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse
        };

        $scope.addRow = function (good, price, shop) {
            var purchase = {};
            purchase.goodsId = good ? good.id : null;
            purchase.goodsName = good ? good.name : null;
            purchase.price = price ? price : null;
            purchase.shopId = shop ? shop.id : null;
            purchase.shopName = shop ? shop.name : null;

            purchaseFactory.updatePurchase(purchase).
                success(function (data) {
                    $scope.purchases.unshift(data);
                    $scope.newGood = '';
                    $scope.newShop = '';
                    $scope.newPrice = '';
                }).
                error(function (data, status) {
                    console.log("data: " + data + " status: " + status);
                    messageUtil.showErrorMessage(data);
                });
        };

        $scope.removeRow = function (purchase) {
            var id = purchase.purchaseId;
            purchaseFactory.deletePurchase(id).
                success(function (data) {
                    var index = -1;
                    var arr = eval($scope.purchases);
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].purchaseId === id) {
                            index = i;
                            break;
                        }
                    }
                    if (index === -1) {
                        return;
                    }
                    $scope.purchases.splice(index, 1);
                }).
                error(function (data, status) {
                    console.log("data: " + data + " status: " + status);
                    messageUtil.showErrorMessage("Request failed");
                });
        };

        $scope.setSelectedPurchase = function (purchase) {
            if (purchase.purchaseId === undefined) {
                console.log("no id:" + purchase);
                return;
            }
            $scope.selectedPurchase = purchase;
        };

        $scope.editRow = function (purchase) {
            $('#editPurchase').modal('show');
        };
    };


    // in case of script minification
    PurchaseController.$inject = ['$scope', '$filter', 'purchaseFactory'];

    angular.module('purchaseApp').controller('PurchaseController', PurchaseController);
}());
