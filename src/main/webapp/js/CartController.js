/**
 * Created by Thomas on 3/20/17.
 */



angular.module('app').controller("CartController", function ($scope,$http, $window, $rootScope) {

    document.getElementById('payment').style.display = 'none';

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    var id = $window.localStorage.getItem("loggedInId");

    $http.get('/newjersey/rest/customers/'+id+ '/cart')
        .then(function(response){

            if(response.data.length>0){
                $scope.cartItems = response.data;
                $scope.totalCartPrice = response.data[0].cart.totalPrice;
                $scope.message = response.data;
            } else
                $scope.message = "No items in cart";



        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.removeItemFromCart = function(cartItemId){
        $http({
            method: 'POST',
            url: '/newjersey/rest/customers/'+id+'/removeItemFromCart?itemId=' + cartItemId,
        }).then(function (result) {
            console.log(result);
            $scope.message = "Item removed.";

            $http.get('/newjersey/rest/customers/'+id+ '/cart')
                .then(function(response){

                    if(response.data.length>0){
                        $scope.cartItems = response.data;
                        $scope.totalCartPrice = response.data[0].cart.totalPrice;
                        $scope.message = response.data;
                    } else{
                        $scope.message = "No items in cart";
                        $scope.cartItems = null;
                        $scope.totalCartPrice = null;


                    }




                }, function errorCallback(response) {
                    $scope.allProds = "none";
                });

        }, function (error) {
            console.log(error);
            $scope.message = "err0r - " + error;
        });
    }


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



    var Payment = function() {
        this.method = "";
    };

    Payment.prototype = {
        setStrategy: function(method) {
            this.method = method;
        },

        pay: function() {
            return this.method.pay();
        }
    };

    var PayPal = function() {
        this.pay = function() {
            $http({
                method: 'POST',
                url: '/newjersey/rest/customers/'+id+'/checkout?paymentType=paypal',
            }).then(function (result) {
                console.log(result);
                $scope.cartItems = null;
                $scope.totalCartPrice = null;
                $scope.message = "Congratulations, purchased!";
            }, function (error) {
                console.log(error);
                $scope.message = "err0r - " + error;
            });
            return "Paid with PayPal.";
        }
    };

    var Debit = function() {
        this.pay = function() {
            $http({
                method: 'POST',
                url: '/newjersey/rest/customers/'+id+'/checkout?paymentType=debit',
            }).then(function (result) {
                console.log(result);
                $scope.cartItems = null;
                $scope.totalCartPrice = null;
                $scope.message = "Congratulations, purchased!";
            }, function (error) {
                console.log(error);
                $scope.message = "err0r - " + error;
            });
            return "Paid with debit card";
        }
    };

    var Stripe = function() {
        this.pay = function() {
            $http({
                method: 'POST',
                url: '/newjersey/rest/customers/'+id+'/checkout?paymentType=stripe',
            }).then(function (result) {
                console.log(result);
                $scope.cartItems = null;
                $scope.totalCartPrice = null;
                $scope.message = "Congratulations, purchased!";
            }, function (error) {
                console.log(error);
                $scope.message = "err0r - " + error;
            });
            return "Paid with stripe";
        }
    };

// log helper


    $scope.checkout = function(){
        document.getElementById('payment').style.display = 'block';
    }

    $scope.pay = function(payType){
        payStrategy(payType);
    }



    var payStrategy = function(payType){

        var paypal = new PayPal();
        var debit = new Debit();
        var stripe = new Stripe();

        var payment = new Payment();


        switch (payType){
            case "PayPal":
                payment.setStrategy(paypal);
                break;
            case "Debit":
                payment.setStrategy(debit);
                break;
            case "Stripe":
                payment.setStrategy(stripe);
        }


        alert(" " + payment.pay());
    }
});