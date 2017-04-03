/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("OrderHistoryController", function ($scope,$http, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    $http.get('/newjersey/rest/admins/order_history')
        .then(function(response){
            $scope.orderHistory = response.data;
            $scope.boits = response.data;

        }, function errorCallback(response) {
            $scope.allProds = "none";
        });


});