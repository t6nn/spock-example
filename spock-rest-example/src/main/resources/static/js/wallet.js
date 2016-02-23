/**
 * Created by tonispi on 23.02.2016.
 */
var wallet = angular.module('wallet', ['ngResource']);

wallet.factory('Balances', ['$resource', function($resource){
   return $resource('/person/balances', {}, {
       query: {method: 'GET', isArray:true}
   }) ;
}]);

wallet.controller('BalanceListController', ['Balances', function(balances) {
    this.balances = balances.query();
}]);