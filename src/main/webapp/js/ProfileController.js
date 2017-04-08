/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("ProfileController", function ($scope,$http, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");
    var loggedInId = $window.localStorage.getItem("loggedInId");


    $http.get('/newjersey/rest/customers/' + loggedInId)
        .then(function(response){
            $scope.isSubscribed = response.data.isSubscribed;
            $scope.name = response.data.isSubscribed;
        }, function errorCallback(response) {
            $scope.name = "ERROR";
        });


    $scope.subscribe = function(){
        $http.get('/newjersey/rest/customers/' + loggedInId + "/subscribe")
            .then(function(response){
                $scope.resp = response.data;
                $scope.isSubscribed = true;
            }, function errorCallback(response) {
                $scope.resp = "error";
            });
    }

    $scope.unsubscribe = function(){
        $http.get('/newjersey/rest/customers/' + loggedInId + "/unsubscribe")
            .then(function(response){
                $scope.resp = response.data;
                $scope.isSubscribed = false;
            }, function errorCallback(response) {
                $scope.resp = "error";
            });
    }



});