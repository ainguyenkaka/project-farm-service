(function () {
    'use strict';

    angular
        .module('farmServiceApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$http'];

    function LoginController($http) {
        var vm = this;
        vm.login = login;
        vm.logout = logout;

        const tokenKey = "farm-authenticationToken";

        vm.isAuthenticated = sessionStorage.getItem(tokenKey) != null;

        vm.account = {
            username: null,
            password: null
        };

        function login() {

            var data = {
                username: vm.account.username,
                password: vm.account.password
            };
            return $http.post('api/app/authenticate', data).success(authenticateSuccess);

            function authenticateSuccess(data, status, headers) {
                var bearerToken = headers('Authorization');
                if (angular.isDefined(bearerToken) && bearerToken.slice(0, 7) === 'Bearer ') {
                    var jwt = bearerToken.slice(7, bearerToken.length);
                    storeAuthenticationToken(jwt);
                    return jwt;
                }
            }
        }

        function storeAuthenticationToken(jwt) {
            vm.isAuthenticated = true;
            sessionStorage.setItem(tokenKey,jwt);
        }

        function logout() {
            sessionStorage.removeItem(tokenKey);
            vm.isAuthenticated = false;
        }

    }
})();
