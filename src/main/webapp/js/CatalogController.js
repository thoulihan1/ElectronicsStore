/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("CatalogController", function ($scope,$http, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    $http.get('/newjersey/rest/manufacturers/all')
        .then(function(response){
            $scope.manufacturers = response.data;
        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.result = 1;


    $scope.getAllProducts = function(){

        $http.get('/newjersey/rest/products/all')
            .then(function(response){
                $scope.manProds = response.data;
            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.manufacturersProducts = function(id){
        $http.get('/newjersey/rest/manufacturers/'+id+'/products/all')
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
            url: '/newjersey/rest/products/'+stockItemId+'/add_to_cart?userId='+userId+"&quantity=" + quant,
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + stockItemId + ",added to cart!";
            alert(stockItemId + " added to cart!");

        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });

        $http.get('/newjersey/rest/customers/'+userId+ '/cart')
            .then(function(response){

                $scope.cartItems = response.data;
                $scope.totalCartPrice = response.data[0].cart.totalPrice;

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.sortType     = 'name'; // set the default sort type
    $scope.sortReverse  = false;  // set the default sort order





});