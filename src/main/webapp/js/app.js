/**
 * Created by Thomas on 3/13/17.
 */
(function(){
    var app = angular.module('app',['ngRoute', 'ui.bootstrap'])
        .config(['$routeProvider', function($routeProvider){
            $routeProvider
                .when('/',{
                    template:'This is the default Route'
                })
                .when('/login', {
                    templateUrl: 'views/login.html',
                    controller : "LoginController"
                })
                .when('/welcome', {
                    templateUrl: 'views/welcome.html',
                    controller : "LoginController"
                })
                .when('/logout', {
                    templateUrl: 'views/logout.html',
                    controller : "LogoutController"
                })
                .when('/register',{
                    templateUrl: 'views/register.html',
                    controller : "RegisterController"
                })
                .when('/catalog',{
                    templateUrl: 'views/catalog.html',
                    controller : "CatalogController"
                })
                .when('/cart',{
                    templateUrl: 'views/cart.html',
                    controller : "CartController"
                })
                .when('/admin',{
                    templateUrl: 'views/admin.html',
                    controller : "AdminController"
                })
                .when('/orderHistory',{
                    templateUrl: 'views/orderHistory.html',
                    controller : "OrderHistoryController"
                })
                .when('/profile',{
                    templateUrl: 'views/profile.html',
                    controller : "ProfileController"
                })
                .when('/product/:productId',{
                    templateUrl: 'views/StockItem.html',
                    controller : "StockItemController"
                })
                .otherwise({redirectTo:'/'});
        }]);
})();
/*
var app1 = angular.module('app1', []);

app1.controller('ctrl1', function($scope, $http) {

    $scope.selectedManufacturer = null;
    $scope.manufacturers = [];


    var lId;

    $http.get('/newjersey/rest/store/manufacturers')
        .then(function(response){
            $scope.manufacturers = response.data;
        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.result = 1;

    $scope.login = function(){
        $http.get('/newjersey/rest/store/login?username=' + $scope.username + "&password=" + $scope.password)
            .then(function(response){
                $scope.result = "Logged in successfully as " + $scope.username;
                lId = response.id;
                $scope.loggedInName = response.data.name;
                $scope.loggedInId = response.data.id;
            }, function errorCallback(response) {
                $scope.result = "Incorrect username or password";
            });
    };

    $scope.viewManufacturerById = function(){
        $http.get('/newjersey/rest/manufacturer/' + $scope.id)
            .then(function(response){
                $scope.man = response.data;
            }, function errorCallback(response) {
                $scope.man = "Error yo";
            });
    };

    $scope.register = function() {
        $scope.user = {
            name: $scope.createUsername,
            password: $scope.createPassword
        };

        $scope.json = angular.toJson($scope.user);

        $http({
            method: 'POST',
            url: '/newjersey/rest/store/users/',
            data: $scope.json,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + $scope.user.name + ", you have registered successfully!";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }


    $scope.getAllProducts = function(){

        $http.get('/newjersey/rest/store/products')
            .then(function(response){

                $scope.stockItems = response.data;

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.manufacturersProducts = function(id){
        $http.get('/newjersey/rest/store/manufacturers/'+id)
            .then(function(response){

                $scope.manProds = response.data;

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }





    $scope.addProduct = function(newTitle, newManufacturerId, newPrice){

        var newManufacturer = {
            id: newManufacturerId
        }

        var product = {
            title: newTitle,
            manufacturer: newManufacturer,
            price:newPrice
        }

        var json = angular.toJson(product);


        $scope.result = json;

        $http({
            method: 'POST',
            url: '/newjersey/rest/store/products/',
            data: product,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + product.title + ", you have registered successfully!";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }

    $scope.addItemToCart = function(stockItemId, quant){

        $http({
            method: 'POST',
            url: '/newjersey/rest/store/products/'+stockItemId+'/add_to_cart?userId='+$scope.loggedInId+"&quantity=" + quant,
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + stockItemId + ",added to cart!";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });

        $http.get('/newjersey/rest/store/users/'+$scope.loggedInId+ '/cart')
            .then(function(response){

                $scope.cartItems = response.data;
                $scope.totalCartPrice = response.data[0].cart.totalPrice;

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.clearCart = function(){
        $http({
            method: 'POST',
            url: '/newjersey/rest/store/users/'+$scope.loggedInId+'/clear_cart',
        }).then(function (result) {
            console.log(result);
            $scope.msg = "CLEARED";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }
});
*/