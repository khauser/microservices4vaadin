<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" href="css/app.css"/>
</head>
<body ng-app="authServer">
<div class="container">
    <section id="form">
        <div class="container">
            <div class="row" ng-show="registrationSuccess" ng-controller="ActivationController">
                <div class="col-md-8 col-md-offset-2">
                    <h1>Validate your account</h1>
                    <p>This would normally be done via e-mail, but for demo purposes click the button</p>
                    <div class="alert alert-success" ng-show="activationSuccess">
                        <strong>Validation Successfull</strong> Please authenticate.
                    </div>

                    <div class="alert alert-danger" ng-show="activationError">
                        <strong>There was an error validating your account.  Your code may have expired</strong> Please use the registration form to register your username.
                    </div>
                    <a class="btn btn-default" href="" ng-click="onActivatePressed()">Activate</a>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-sm-offset-1">
                    <div class="login-form">
                        <h2>Login to your account</h2>
                        <form role="form" action="login" method="post">
                            <input type="email" placeholder="Email" name="username"/>
                            <input type="password" placeholder="Password" name="password"/>
                            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default">Login</button>
                        </form>
                    </div>
                </div>
                <div class="col-sm-1">
                    <h2 class="or">OR</h2>
                </div>
                <div class="col-sm-4"  ng-controller="RegisterController">

                    <div class="alert alert-danger" ng-show="error">
                        <strong>Registration failed!</strong> Please check your input and try again.
                    </div>

                    <div class="alert alert-danger" ng-show="errorUserExists">
                        <strong>Login name already registered!</strong> Please choose another one.
                    </div>

                    <div class="alert alert-danger" ng-show="errorEmailExists">
                        <strong>E-mail is already in use!</strong> Please choose another one.
                    </div>

                    <div class="alert alert-danger" ng-show="doNotMatch">
                        The password and its confirmation do not match!
                    </div>
                    <div class="signup-form">
                        <h2>New User Signup!</h2>

                        <form ng-show="!success" name="form" role="form" novalidate ng-submit="register()">
                            <input type="text" placeholder="First Name" name="firstName" ng-model="registration.firstName"/>
                            <input type="text" placeholder="Last Name" name="lastName" ng-model="registration.lastName"/>
                            <input type="email" placeholder="Email Address" name="email" ng-model="registration.email"/>
                            <input type="password" placeholder="Password" name="password" ng-model="registration.password"/>
                            <input type="password" placeholder="Confirm Password" name="confirm" ng-model="confirmPassword"/>
                            <button type="submit" class="btn btn-default">Signup</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script src="js/angular.min.js" type="text/javascript"></script>
<script src="js/angular-resource.min.js" type="text/javascript"></script>
<script src="js/auth-server.js" type="text/javascript"></script>
</body>
</html>
