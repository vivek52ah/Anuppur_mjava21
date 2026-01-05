//Created By Yousra
var dms = angular.module('dms', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
dms.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
	}]);

dms.run(['Idle', function(Idle) {
Idle.watch();
}]);
dms
	.config( ['$routeProvider', function($routeProvider) {
		$routeProvider

			.when('/workBookAndPhotos', {
				templateUrl: 'workBookAndPhotos',
				controller : 'AdminController'
			})
			.otherwise({
				redirectTo: '/'
			});
	}]);
