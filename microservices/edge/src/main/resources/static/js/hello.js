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
  }).otherwise({
    redirectTo:'/'
  });

  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
  $httpProvider.defaults.headers.common['Accept'] = 'application/json';
});

angularApp.controller('LoginController', ['$http', '$rootScope', '$scope', '$location', 'AuthenticateService', function ($http, $rootScope, $scope, $location, AuthenticateService) {

  //AuthenticateService.authenticate();

  $scope.credentials = {};
  $scope.login = function() {
    $http({
      method  : 'POST',
      url     : '/authserver/uaa/login',
      params  : $scope.credentials,
      headers : {'Content-Type': 'application/x-www-form-urlencoded'}
    })
    .success(function(data, status) {
      console.log("Login succeed")
      AuthenticateService.authenticate();
    }).error(function(data, status) {
      console.log("Login failed with status " + status)
      $location.path("/loginPage");
      $scope.error = true;
      $rootScope.authenticated = false;
    })
  };


}]);


angularApp.factory('Activate', ['$resource', function ($resource) {
  return $resource('/authserver/uaa/activate', {}, {
    'get': { method: 'GET', params: {}, isArray: false}
  });
}]);

angularApp.controller('RegisterController', ['$http', '$rootScope', '$scope', function ($http, $rootScope, $scope) {
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
      $http({
        method  : 'POST',
        url     : '/authserver/uaa/register',
        data    : $scope.registrationData //forms user object
      })
      .success(function(data, status) {
        console.log("Registration succeed");
        console.log(JSON.stringify(data));
        console.log(JSON.stringify(status));
        $rootScope.activationKey = data.registrationKey;
        $rootScope.registrationSuccess = "OK";
      }).error(function(data, status) {
        console.log("Registration failed")
        console.log(JSON.stringify(data));
        console.log(JSON.stringify(status));
        if (status === 400 && data === "e-mail address already in use") {
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
      }
    );
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


