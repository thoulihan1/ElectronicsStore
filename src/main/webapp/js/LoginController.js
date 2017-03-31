

/**
 * Created by Thomas on 3/20/17.
 */

angular.module('app').controller("LoginController", function ($scope,$http, $location, $window, $rootScope) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    $scope.login = function(){
        $http.get('/newjersey/rest/store/login?email=' + $scope.email+ "&password=" + $scope.password)
            .then(function(response){
                $scope.loggedInName = response.data.name;
                $scope.loggedInId = response.data.id;
                $scope.loggedInType = response.data.type;
                $window.localStorage.setItem("loggedInName", response.data.name);
                $window.localStorage.setItem("loggedInId", response.data.id);
                $window.localStorage.setItem("loggedInType", response.data.type);

                $scope.result = "Logged in successfully as " +  $window.localStorage.getItem("loggedInName");
                $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
                $rootScope.type = $window.localStorage.getItem("loggedInType");

                $location.path("/welcome");

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