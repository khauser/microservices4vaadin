var angularApp = angular.module('hello', ['ngResource']);
angularApp.controller('LoginController', ['$http', '$rootScope', '$scope', '$location', function ($http, $rootScope, $scope, $location) {

  var authenticate = function(callback) {
	$http.get('user').success(function(data) {
	  if (data.email) {
	    $rootScope.authenticated = true;
	    window.location = "/ui";
      } else {
        $rootScope.authenticated = false;
      }
      callback && callback();
    }).error(function() {
      $rootScope.authenticated = false;
      callback && callback();
    });
  }

  authenticate();	
	
  $scope.credentials = {};
  $scope.login = function() {
    $http.post('/authserver/uaa/login', $.param($scope.credentials), {
      headers : {
        "content-type" : "application/x-www-form-urlencoded"
      }
    }).success(function(data) {
      authenticate(function() {
        if ($rootScope.authenticated) {
          console.log("Login succeeded")
          //$rootScope.$apply(function() { $location.path("/ui"); });
          //$location.path("/ui");
          $scope.error = false;
          $rootScope.authenticated = true;
        } else {
          console.log("Login failed with redirect")
          $location.path("/");
          $scope.error = true;
          $rootScope.authenticated = false;
        }
      });
    }).error(function(data) {
      console.log("Login failed")
      $location.path("/");
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
    $scope.doNotMatch = null;
    $scope.errorEmailExists = null;
    $rootScope.registrationSuccess = null;
    $scope.activateionSuccess = null;
    $scope.activationError = null;
    $scope.register = function () {
        if ($scope.registration.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.doNotMatch = null;
            $scope.success = null;
            $scope.error = null;
            $scope.errorEmailExists = null;
            //DOM access in a controller is bad juju but since its just a demo :P
            var csrfToken = angular.element( document.querySelector( '#csrf_token' ) ).val();
            $http.defaults.headers.common['X-CSRF-TOKEN']= csrfToken;
            Register.save($scope.registration,
                function (value, responseHeaders) {
                    //Resource wants responses to be objects or arrays.  String responses are
                    //treated like an array of strings.  You would probably want to use Restangular or http instead
                    //or change the backend service to return an object
                    $rootScope.activationKey = value.registrationKey;
                    $rootScope.registrationSuccess = "OK";
                },
                function (httpResponse) {
                	console.log("asdf " + httpResponse.data + " Hallo");
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
