'use strict';

var authServer = angular.module('authServer', [ 'ngResource' ]);

authServer.factory('Register', function ($resource) {
        return $resource('/uaa/register', {}, {
          'post': { method: 'POST', params: {}, isArray: false}
        });
    });

authServer.factory('Activate', function ($resource) {
        return $resource('/uaa/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });


authServer.controller('RegisterController', function ($scope, $rootScope, Register, $http) {
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
                        if (httpResponse.status === 400 && httpResponse.data === "e-mail address already in use") {
                            $scope.error = null;
                            $scope.errorEmailExists = "ERROR";
                        } else {
                            $scope.error = "ERROR";
                        }
                    });
            }
        }
    });

authServer.controller('ActivationController', function ($rootScope, $scope, Activate) {
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

    });
