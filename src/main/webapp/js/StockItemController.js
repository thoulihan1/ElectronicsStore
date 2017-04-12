/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("StockItemController", function ($scope,$http, $window, $rootScope, $routeParams) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");
    var loggedInId = $window.localStorage.getItem("loggedInId");
    var id = $routeParams.productId;

    $http.get('/newjersey/rest/products/' + id + '/reviews')
        .then(function(response){
            $scope.stockItem = response.data[0].stockItem.title;
            $scope.reviews = response.data;
        }, function errorCallback(response) {
            $scope.name = "ERROR";
        });

});