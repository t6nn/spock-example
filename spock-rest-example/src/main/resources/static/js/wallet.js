/**
 * Created by tonispi on 23.02.2016.
 */
var wallet = angular.module('wallet', ['ngResource', 'ui.bootstrap']);

wallet.factory('PersonREST', ['$resource', function ($resource) {
    return $resource('/person/:verb', {}, {
        query: {params: {verb: 'balances'}, method: 'GET', isArray: true},
        give: {params: {verb: 'give'}, method: 'POST'},
        ask: {params: {verb: 'ask'}, method: 'POST'}
    });
}]);

wallet.factory('Alert', ['$rootScope', '$timeout', function (scope, timeout) {
    return function (msg) {
        scope.alert = msg;
        timeout(function () {
            scope.alert = ""
        }, 3000);
    }
}]);

wallet.factory('Person', ['PersonREST', function (person) {
    var svc = function () {
    };

    svc.update = function () {
        this.balances = person.query();
    };

    svc.give = function (amount, currency, onSuccess) {
        return person.give({amount: amount, currency: currency}, function (result) {
            svc.update();
            if (onSuccess !== undefined) {
                onSuccess(result);
            }
        });
    };

    svc.ask = function (amount, currency, onSuccess) {
        return person.ask({amount: amount, currency: currency}, function (result) {
            svc.update();
            if (onSuccess !== undefined) {
                onSuccess(result);
            }
        });
    };
    svc.update();
    return svc;
}]);

wallet.controller('BalanceListCtrl', ['$scope', 'Person', function ($scope, person) {
    $scope.balances = person.balances;
    $scope.$watchCollection(function () {
        return person.balances
    }, function (newBalances) {
        $scope.balances = newBalances;
    });
}]);

wallet.controller('TransactionCtrl', ['$scope', 'Alert', 'Person', function ($scope, alert, person) {
    $scope.availableCurrencies = ['EUR', 'USD'];

    $scope.message = "";

    $scope.amount = 0;

    $scope.give = function (currency) {
        person.give($scope.amount, currency, function (data) {
            alert(data.message);
        });
    };

    $scope.ask = function (currency) {
        person.ask($scope.amount, currency, function (data) {
            alert(data.message + " " + data.money.amount + " " + data.money.currency);
        });
    };
}]);