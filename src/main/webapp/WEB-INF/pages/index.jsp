<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>

<html ng-app="purchaseApp">
<head>
    <meta charset="utf-8">
    <link href="app/styles/styles.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" type="text/css">
    <script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
</head>
<body>
    
<header>
    <nav class="navbar navbar-inverse" role="navigation">
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <li ng-class="{'active':selectedTab === 'Purchase'}" ng-click="selectedTab = 'Purchase'"><a href="#">Покупки</a></li>
                <li ng-class="{'active':selectedTab === 'Goods'}" ng-click="selectedTab = 'Goods'"><a href="#/goods">Товары и магазины</a></li>
            </ul>
        </div>
    </nav>
</header>

<div ng-view></div>

<script src="app/angular_scripts/angular.js"></script>
<script src="app/angular_scripts/angular-route.js"></script>

<script src="app/app.js"></script>
<script src="app/message.js"></script>
<script src="app/controllers/purchaseController.js"></script>
<script src="app/controllers/goodsController.js"></script>
<script src="app/services/purchaseFactory.js"></script>
</body>


</html>
