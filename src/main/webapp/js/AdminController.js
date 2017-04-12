

/**
 * Created by Thomas on 3/20/17.
 */

angular.module('app').controller("AdminController", function ($scope,$http, $rootScope, $window) {
    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");

    $http.get('/newjersey/rest/manufacturers/all')
        .then(function(response){
            $scope.manufacturers = response.data;
        }, function errorCallback(response) {
            $scope.allProds = "none";
        });

    $scope.addManufacturer = function(){
        var manufacturerToCreate = {
            name: $scope.newManufacturer,
        };

        $http({
            method: 'POST',
            url: '/newjersey/rest/manufacturers/add',
            data: manufacturerToCreate,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.manufacturerMsg =  manufacturerToCreate.name + " added successfully!";
            $scope.newManufacturer = null;
            $http.get('/newjersey/rest/manufacturers/all')
                .then(function(response){
                    $scope.manufacturers = response.data;
                }, function errorCallback(response) {
                    $scope.allProds = "none";
                });
        }, function (error) {
            console.log(error);
            $scope.manufacturerMsg = "err0r - " + error;
        });
    }

    $scope.addProduct = function(newTitle, newManufacturerId, newPrice, imageUrl, leftInStock, category){

        var newManufacturer = {
            id: newManufacturerId
        }

        var product = {
            title: newTitle,
            manufacturer: newManufacturer,
            price:newPrice,
            imgUrl:imageUrl,
            leftInStock:leftInStock,
            category: category
        }

        var json = angular.toJson(product);


        $scope.result = json;

        $http({
            method: 'POST',
            url: '/newjersey/rest/products/add/',
            data: product,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(function (result) {
            console.log(result);
            $scope.productMsg = product.title + " has been added successfully!";
        }, function (error) {
            console.log(error);
            $scope.productMsg = "err0r - " + error;
        });
    }
});