/**
 * Created by Thomas on 3/20/17.
 */

angular.module('app').controller("RegisterController", function ($scope,$http) {



    $scope.bop = function() {
        var factory = new UserFactory();
        var user;
        if (document.getElementById("isAdmin").checked) {
            user = factory.createUser("admin");
        } else {
            user = factory.createUser("customer");
        }
        register(user);
    }

    function UserFactory() {
        this.createUser = function (type) {
            var user;

            if (type === "admin") {
                user = new Admin();

            } else if (type === "customer") {
                user = new Customer();
            }

            user.type = type;
            user.email = $scope.newEmail;
            user.name = $scope.newName;
            user.password = $scope.newPassword
            //alert(user.type);
            return user;
        }
    }

    var Admin = function () {
    };

    var Customer = function () {
    };

    function register(user){
        var json = angular.toJson(user);
        $http({
            method: 'POST',
            url: '/newjersey/rest/'+user.type+'s/add/',
            data: json,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.msg = "Congratulations. " + user.type + " " +  user.name + " has registered successfully!";
        }, function (error) {
            console.log(error);
            $scope.msg = "error " + error;
        });
    }
});
