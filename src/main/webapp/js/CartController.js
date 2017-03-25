/**
 * Created by Thomas on 3/25/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("CartController", function ($scope,$http, $window) {

    // var username;
    // var password;

    var id = $window.localStorage.getItem("loggedInId");

    $http.get('/newjersey/rest/store/users/'+id+ '/cart')
        .then(function(response){

            $scope.cartItems = response.data;
            $scope.totalCartPrice = response.data[0].cart.totalPrice;

        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.clearCart = function(){
        $http({
            method: 'POST',
            url: '/newjersey/rest/store/users/'+id+'/clear_cart',
        }).then(function (result) {
            console.log(result);
            $scope.msg = "CLEARED";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }

});