/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("CatalogController", function ($scope,$http, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    var categories = [];

    $http.get('/newjersey/rest/products/all')
        .then(function(response){
            $scope.manProds = response.data;
            $scope.searching = 'All products';

            var arrLength = $scope.manProds.length;
            for(var i = 0; i< arrLength; i++){
                categories.push($scope.manProds[i].category);
            }

            categories = categories.filter( function( item, index, inputArray ) {
                return inputArray.indexOf(item) == index;
            });

            $scope.cats = categories;

        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

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
                $scope.selectedManufacturer = '';
                $scope.selectedCategory = '';
                $scope.searching = "All products";

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.manufacturersProducts = function(id){
        $http.get('/newjersey/rest/manufacturers/'+id+'/products/all')
            .then(function(response){
                $scope.selectedCategory = '';

                $scope.manProds = response.data;
                $scope.searching = "Searching by Manufacturer - " + response.data[0].manufacturer.name;
            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.showItemsByCategory = function(category){

        $http.get('/newjersey/rest/products/'+category)
            .then(function(response){
                $scope.manProds = response.data;
                $scope.selectedManufacturer = '';
                $scope.searching = "Searching by Category - " + category;

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
                if(response.data.length>0){
                    $scope.totalCartPrice = response.data[0].cart.totalPrice;

                }

            }, function errorCallback(response) {
                $scope.allProds = "none";
            });
    }

    $scope.sortType     = 'title';
    $scope.sortReverse  = false;
    $scope.search = '';

    $scope.changeStockQuantity = function(stockItemId, stockQuantity){

        $http({
            method: 'POST',
            url: '/newjersey/rest/products/'+stockItemId+'/updateQuantity?quantity=' + stockQuantity,
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Stock item " + stockItemId + " updated";
            alert(stockItemId + " quantity updated to " + stockQuantity);

        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }
});