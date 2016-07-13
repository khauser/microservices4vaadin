var angularApp = angular.module('hello', ['ngResource', 'ngRoute']);

angularApp.config(function($routeProvider, $httpProvider) {
  $routeProvider.when('/', {
    templateUrl: '/empty.html',
    resolve: {
      loadData: function(AuthenticateService) {
        AuthenticateService.authenticate();
      }
    }
  }).when('/loginPage', {
    templateUrl: '/login.html'
  });

  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
  $httpProvider.defaults.headers.common['Accept'] = 'application/json';
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
});

angularApp.controller('LoginController', ['$http', '$rootScope', '$scope', '$location', 'AuthenticateService', function ($http, $rootScope, $scope, $location, AuthenticateService) {

  //AuthenticateService.authenticate();

  $scope.credentials = {};
  $scope.login = function() {
    $http.post('/authserver/uaa/login', $.param($scope.credentials)).success(function(data) {
      console.log("Login succeed")
      AuthenticateService.authenticate();
    }).error(function(data) {
      console.log("Login failed")
      $location.path("/loginPage");
      $scope.error = true;
      $rootScope.authenticated = false;
    })
  };


}]);


angularApp.factory('Register', ['$resource', function ($resource) {
    return $resource('/authserver/uaa/register', {}, {
      'post': { method: 'POST', params: {}, isArray: false}
    });
}]);

angularApp.factory('Activate', ['$resource', function ($resource) {
    return $resource('/authserver/uaa/activate', {}, {
        'get': { method: 'GET', params: {}, isArray: false}
    });
}]);

angularApp.controller('RegisterController', ['$http', '$rootScope', '$scope', 'Register', function ($http, $rootScope, $scope, Register) {
    $scope.success = null;
    $scope.error = null;
    $scope.errorDifferentPasswordConfirm = null;
    $scope.errorEmailExists = null;
    $rootScope.registrationSuccess = null;
    $scope.activateionSuccess = null;
    $scope.activationError = null;
    $scope.registrationData = null;
    $scope.register = function () {
        if ($scope.registrationData === null) {
            $scope.error = "ERROR";
        }
        else if ($scope.registrationData.password != $scope.confirmPassword) {
            $scope.errorDifferentPasswordConfirm = "ERROR";
        } else {
            $scope.success = null;
            $scope.error = null;
            $scope.errorDifferentPasswordConfirm = null;
            $scope.errorEmailExists = null;
            //DOM access in a controller is bad juju but since its just a demo :P
            var csrfToken = angular.element( document.querySelector( '#csrf_token' ) ).val();
            $http.defaults.headers.common['X-CSRF-TOKEN']= csrfToken;
            Register.save($scope.registrationData,
                function (value, responseHeaders) {
                    //Resource wants responses to be objects or arrays.  String responses are
                    //treated like an array of strings.  You would probably want to use Restangular or http instead
                    //or change the backend service to return an object
                    $rootScope.activationKey = value.registrationKey;
                    $rootScope.registrationSuccess = "OK";
                },
                function (httpResponse) {
                  console.log("asdf " + httpResponse.status + " Hallo");
                    if (httpResponse.status === 400 && httpResponse.data === "e-mail address already in use") {

                      $scope.error = null;
                        $scope.errorEmailExists = "ERROR";
                    } else {
                        $scope.error = "ERROR";
                    }
                });
        }
    }
}]);

angularApp.controller('ActivationController', ['$rootScope', '$scope', 'Activate', function ($rootScope, $scope, Activate) {
    $scope.onActivatePressed = function () {
      Activate.get({key: $rootScope.activationKey},
          function (value, responseHeaders) {
              $scope.error = null;
              $scope.activationSuccess = 'OK';
          },
          function (httpResponse) {
              $scope.activationSuccess = null;
              $scope.activationError = "ERROR";
          });
    }

}]);

angularApp.factory('AuthenticateService', ['$window', '$http', '$rootScope', function($window, $http, $rootScope) {
  var service = {};

  service.authenticate = function() {
    console.log("check authentication");
    $http.get('user')
      .success(function(data) {
        if (data.email) {
          console.log("exists");
          $rootScope.authenticated = true;
          $window.location = "/ui";
        } else {
          console.log("not exists");
          $rootScope.authenticated = false;
          $window.location = "#/loginPage";
        }
      })
      .error(function() {
        console.log("error");
        $rootScope.authenticated = false;
        $window.location = "#/loginPage";
      }
    );
  }

  return service;
}]);


