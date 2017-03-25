

/**
 * Created by Thomas on 3/20/17.
 */

angular.module('app').controller("LoginController", function ($scope,$http, $location, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");


    $scope.login = function(){
        $http.get('/newjersey/rest/store/login?username=' + $scope.username + "&password=" + $scope.password)
            .then(function(response){
                //$scope.result = "Logged in successfully as " + $scope.username;
                $scope.loggedInName = response.data.name;
                $scope.loggedInId = response.data.id;
                $window.localStorage.setItem("loggedInName", response.data.name);
                $window.localStorage.setItem("loggedInId", response.data.id);
                $scope.result = "Logged in successfully as " +  $window.localStorage.getItem("loggedInName");
                $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
            }, function errorCallback(response) {

                $scope.result = "Incorrect username or password";
            });
    };

    $scope.logout = function(){
        $location.path("/logout");
    };


    $scope.goToRegister = function () {
        $location.path("/register");
    };
});