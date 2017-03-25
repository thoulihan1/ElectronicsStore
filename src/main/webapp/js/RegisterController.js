/**
 * Created by Thomas on 3/20/17.
 */

angular.module('app').controller("RegisterController", function ($scope,$http, $location) {


    $scope.register = function() {
        $scope.user = {
            name: $scope.createUsername,
            password: $scope.createPassword
        };

        $scope.json = angular.toJson($scope.user);

        $http({
            method: 'POST',
            url: '/newjersey/rest/store/users/',
            data: $scope.json,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations " + $scope.user.name + ", you have registered successfully!";
        }, function (error) {
            console.log(error);
            $scope.msg = "err0r - " + error;
        });
    }
});
