/**
 * Created by Thomas on 3/25/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

/**
 * Created by Thomas on 3/25/17.
 */

angular.module('app').controller("LogoutController", function ($scope,$http, $location, $window, $rootScope) {
        $window.localStorage.clear();
        $rootScope.loggedInUser = null;
});

