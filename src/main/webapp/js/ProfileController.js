/**
 * Created by Thomas on 3/20/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("ProfileController", function ($scope,$http, $window, $rootScope,  $modal) {

    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");
    var loggedInId = $window.localStorage.getItem("loggedInId");


    $http.get('/newjersey/rest/customers/' + loggedInId)
        .then(function (response) {
            $scope.isSubscribed = response.data.isSubscribed;
            $scope.name = response.data.isSubscribed;
            $scope.method = response.data.paymentType;
        }, function errorCallback(response) {
            $scope.name = "ERROR";
        });

    $http.get('/newjersey/rest/customers/' + loggedInId + '/purchase_history')
        .then(function (response) {
            $scope.orderHistory = response.data;
        }, function errorCallback(response) {
            $scope.name = "ERROR";
        });

    $scope.subscribe = function () {
        $http.get('/newjersey/rest/customers/' + loggedInId + "/subscribe")
            .then(function (response) {
                $scope.resp = response.data;
                $scope.isSubscribed = true;
            }, function errorCallback(response) {
                $scope.resp = "error";
            });
    }

    $scope.unsubscribe = function () {
        $http.get('/newjersey/rest/customers/' + loggedInId + "/unsubscribe")
            .then(function (response) {
                $scope.resp = response.data;
                $scope.isSubscribed = false;
            }, function errorCallback(response) {
                $scope.resp = "error";
            });
    }

    $scope.rateThis = function (name) {
        alert("Rate " + name);
    }

    $scope.submitPaymentType = function(){
        $http.get('/newjersey/rest/customers/' + loggedInId + "/setPaymentType/" + $scope.method)
            .then(function (response) {
                $scope.resp = response.data;
            }, function errorCallback(response) {
                $scope.resp = "error";
            });
    }

    $scope.showModal = function(names, id) {

        $scope.opts = {
            backdrop: true,
            backdropClick: true,
            dialogFade: false,
            keyboard: true,
            templateUrl : 'views/modal.html',
            controller : ModalInstanceCtrl,
            resolve: {} // empty storage
        };


        $scope.opts.resolve.item = function() {
            return angular.copy({name:names, id:id}); // pass name to Dialog
        }

        var modalInstance = $modal.open($scope.opts);

        modalInstance.result.then(function(){
            $scope.dialog = "gh";
        },function(){
            //on cancel button press
            console.log("Modal Closed");
        });
    };
});

var ModalInstanceCtrl = function($scope, $http, $rootScope, $window, $modalInstance, $modal, item) {
    $rootScope.loggedInUser = $window.localStorage.getItem("loggedInName");
    $rootScope.type = $window.localStorage.getItem("loggedInType");
    var loggedInId = $window.localStorage.getItem("loggedInId");
    $scope.item = item;


    $scope.data = {
        ratingNumber:0,
        reviewText:""
    };

    $scope.ok = function () {
        $http.get('/newjersey/rest/products/' + item.id + "/review?customerId=" + loggedInId + '&review=' + $scope.data.reviewText + "&rating=" + $scope.data.ratingNumber)
            .then(function (response) {

                $modalInstance.close();
                alert(response.data)
                return "ok";
            }, function errorCallback(response) {
                $scope.resp = "error";
            });

    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}