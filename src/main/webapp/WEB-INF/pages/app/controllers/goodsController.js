/**
 * Created with IntelliJ IDEA.
 * User: Миша
 * Date: 09.11.14
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
(function () {
    var GoodsController = function GoodsController($scope, $filter, purchaseFactory) {
        $scope.goods = [];
        $scope.shops = [];
        $scope.goodsPurchases = [];
        $scope.selectedGood = null;
        $scope.filteredGoods = null;
        $scope.shopPurchases = [];
        $scope.selectedShop = null;
        $scope.filteredShop = null;
        $scope.editedItem = {};
        $scope.deletedItem = {};
        var messageUtil = MessageUtil();

        function init() {
            purchaseFactory.getAllGoods()
                .success(function (goods) {
                    $scope.goods = goods;
                    $scope.filteredGoods = goods;

                });

            purchaseFactory.getAllShops()
                .success(function (shops) {
                    $scope.shops = shops;
                    $scope.filteredShop = shops;
                });
        }

        function initEditListener() {
            $('#editItems').on('show.bs.modal',function (event) {
                var modal = $(this);
                var editedItem = $scope.editedItem;
                modal.find('.modal-title').text(editedItem.title);
                modal.find('.control-label').text('Название:');
                modal.find('.modal-body input').val(editedItem.item.name);
            }).on('click', '#updateItemBtn', function (e) {
                    var editedItem = $scope.editedItem;
                    var newName = $('#newItemName').val();
                    if (editedItem.item.name != newName) {
                        var newItem = $.extend(true, {}, editedItem.item);
                        newItem.name = newName;
                        purchaseFactory.updateItem(newItem, editedItem.path)
                            .success(function (data) {
                                editedItem.item.name = data.name;
                                editedItem.item.version = data.version;
                                $('#editItems').modal('hide');
                            })
                            .error(function (data, status) {
                                console.log("data: " + data + " status: " + status);
                                messageUtil.showErrorMessage("Request failed");
                            });
                    }
                });
        }

        function initDeleteListener() {
            $('#confirmDeleteItem').on('show.bs.modal',function (event) {
                var data = $scope.deletedItem.data;
                var modal = $(this);
                var message = data.message ? data.message : '';
                modal.find('.modal-body').text(message);
            }).on('click', '#removeItemBtn', function (e) {
                    var target = $scope.deletedItem.target;
                    var item = $scope.deletedItem.item;
                    $scope.removeItem(item, true, target);
                    $(this).find('.modal-body').text('');
                });
        }

        init();
        initEditListener();
        initDeleteListener();

        $scope.showGoodsPurchases = function (good) {
            var id = good.id;
            if (id === undefined) {
                console.log("no id:" + good);
                return;
            }
            $scope.selectedGood = null;
            purchaseFactory.getPurchasesForGood(id)
                .success(function (goodsPurchases) {
                    $scope.goodsPurchases = goodsPurchases;
                    $scope.selectedGood = good;
                });
        };

        $scope.showShopsPurchases = function (shop) {
            var id = shop.id;
            if (id === undefined) {
                console.log("no id:" + shop);
                return;
            }
            $scope.selectedShop = null;
            purchaseFactory.getPurchasesForShop(id)
                .success(function (shopPurchases) {
                    $scope.shopPurchases = shopPurchases;
                    $scope.selectedShop = shop;
                });
        };

        $scope.addItem = function (item, target) {
            if (item === undefined) {
                item = {};
                item.name = "";
            }
            var arr, path;
            switch (target) {
                case "goods":
                    arr = $scope.goods;
                    path = 'api/goods/';
                    break;
                case "shop":
                    arr = $scope.shops;
                    path = 'api/shops/';
                    break;
                default:
                    return;
            }
            purchaseFactory.updateItem(item, path).
                success(function (data) {
                    arr.push(data);
                    item.name = '';
                }).
                error(function (data, status) {
                    console.log("data: " + data + " status: " + status);
                    messageUtil.showErrorMessage(data);
                });
        };

        $scope.removeItem = function (item, force, target, event) {
            var req = {};
            req.force = force;
            var removeFromUI;
            switch (target) {
                case "goods":
                    req.path = 'api/deleteGoods/';
                    removeFromUI = $scope.removeGoodsFromUI;
                    break;
                case "shop":
                    req.path = 'api/deleteShops/';
                    removeFromUI = $scope.removeShopFromUI;
                    break;
                default:
                    return;
            }
            var id = item.id;
            if (event) {
                event.stopPropagation();
                event.preventDefault();
            }
            purchaseFactory.deleteItem(id, req)
                .success(function (data) {
                    var count = data.count;
                    if (count == 0) {
                        removeFromUI(id)
                    } else {
                        $scope.deletedItem.item = item;
                        $scope.deletedItem.target = target;
                        $scope.deletedItem.data = data;
                        $('#confirmDeleteItem').modal('show');
                    }
                })
                .error(function (data, status) {
                    console.log("data: " + data + " status: " + status);
                    messageUtil.showErrorMessage("Request failed");
                });
        };

        $scope.removeGoodsFromUI = function (id) {
            var index = -1;
            var arr = $scope.goods;
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].id === id) {
                    index = i;
                    break;
                }
            }
            if (index === -1) {
                return;
            }
            $scope.goods.splice(index, 1);
            $scope.selectedGood = null;
            $scope.goodsPurchases = [];
        };

        $scope.removeShopFromUI = function (id) {
            var index = -1;
            var arr = $scope.shops;
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].id === id) {
                    index = i;
                    break;
                }
            }
            if (index === -1) {
                return;
            }
            $scope.shops.splice(index, 1);
            $scope.selectedShop = null;
            $scope.shopPurchases = [];
        };

        $scope.editItem = function (item, target, event) {
            if (event) {
                event.stopPropagation();
                event.preventDefault();
            }
            var path;
            var title;
            switch (target) {
                case "goods":
                    path = 'api/goods/';
                    title = 'Изменить товар';
                    break;
                case "shop":
                    path = 'api/shops/';
                    title = 'Изменить магазин';
                    break;
                default:
                    return;
            }
            $scope.editedItem.item = item;
            $scope.editedItem.path = path;
            $scope.editedItem.title = title;
            $('#editItems').modal('show');
        };


        $scope.$watch("newGoods.name", function (query) {
            $scope.filteredGoods = $filter("filter")($scope.goods, query);
        });

        $scope.$watch("newShop.name", function (query) {
            $scope.filteredShop = $filter("filter")($scope.shops, query);
        });

    };

    // in case of script minification
    GoodsController.$inject = ['$scope', '$filter', 'purchaseFactory'];

    angular.module('purchaseApp').controller('GoodsController', GoodsController);
}());