/**
 * Created by Thomas on 3/20/17.
 */



angular.module('app').controller("CartController", function ($scope,$http, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    var id = $window.localStorage.getItem("loggedInId");

    $http.get('/newjersey/rest/customers/'+id+ '/cart')
        .then(function(response){

            $scope.cartItems = response.data;
            $scope.totalCartPrice = response.data[0].cart.totalPrice;

        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.clearCart = function(){
        $http({
            method: 'POST',
            url: '/newjersey/rest/customers/'+id+'/clear_cart',
        }).then(function (result) {
            console.log(result);
            $scope.message = "CLEARED";
            $scope.cartItems = null;
            $scope.totalCartPrice = null;
        }, function (error) {
            console.log(error);
            $scope.message = "err0r - " + error;
        });
    }

    $scope.purchase = function(){
        $http({
            method: 'POST',
            url: '/newjersey/rest/customers/'+id+'/checkout',
        }).then(function (result) {
            console.log(result);
            $scope.cartItems = null;
            $scope.totalCartPrice = null;
            $scope.message = "Congratulations, purchased!";
        }, function (error) {
            console.log(error);
            $scope.message = "err0r - " + error;
        });
    }

});