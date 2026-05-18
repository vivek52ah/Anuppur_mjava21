// Get the existing dms module (don't recreate it)
var dms = angular.module('dms');

// Add routes to the existing module
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
