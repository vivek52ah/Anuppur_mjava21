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
		
			.when('/manageusers', {
				templateUrl: 'manageusers',
				controller : 'SuperAdminController'
			})
			.when('/editUserForm/:id', {
				templateUrl: function(params){ return 'editUserForm/' + params.id; }, 
				controller : 'SuperAdminController'
			})
			.when('/addUserForm', {
				templateUrl: 'addUserForm',
				controller : 'SuperAdminController'
			})
			.when('/manageAgencyUsers', {
				templateUrl: 'manageAgencyUsers',
				controller : 'SuperAdminController'
			})
			
		
			
			.otherwise({
				redirectTo: '/'
			});
	}]);
