/**
 * Created by Thomas on 3/25/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("CatalogController", function ($scope,$http, $window) {

    // var username;
    // var password;
    $http.get('/newjersey/rest/store/manufacturers')
        .then(function(response){
            $scope.manufacturers = response.data;
        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.result = 1;

    $scope.viewManufacturerById = function(){
        $http.get('/newjersey/rest/manufacturer/' + $scope.id)
            .then(function(response){
                $scope.man = response.data;
            }, function errorCallback(response) {
                $scope.man = "Error yo";
            });
    };

    $scope.getAllProducts = function(){

        $http.get('/newjersey/rest/store/products')
            .then(function(response){

                $scope.manProds = response.data;

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

    $scope.addItemToCart = function(stockItemId, quant){

        var userId = $window.localStorage.getItem("loggedInId");

        $http({
            method: 'POST',
            url: '/newjersey/rest/store/products/'+stockItemId+'/add_to_cart?userId='+userId+"&quantity=" + quant,
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + stockItemId + ",added to cart!";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });

        $http.get('/newjersey/rest/store/users/'+userId+ '/cart')
            .then(function(response){

                $scope.cartItems = response.data;
                $scope.totalCartPrice = response.data[0].cart.totalPrice;

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

});