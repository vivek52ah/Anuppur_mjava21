/*var dms = angular.module('dms', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
dms.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
	}]);

dms.run(['Idle', function(Idle) {
Idle.watch();
}]);*/
dms
	.config( ['$routeProvider', function($routeProvider) {
		$routeProvider
		
			
			.when('/changepassword', {
				templateUrl: 'changepassword',
				controller : 'CommonController'
			})
			.when('/editUserForm/:id', {
				templateUrl: function(params){ return 'editUserForm/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/addUserForm', {
				templateUrl: 'addUserForm',
				controller : 'SystemAdminController'
			})
			
			.when('/manageusers', {
				templateUrl:  'manageusers',
				controller : 'SystemAdminController'
			})
			.when('/manageLegacyData',{
				templateUrl: 'manageLegacyData',
				controller : 'CommonController'
			})
			.when('/manageOngoingWorks',{
				templateUrl: 'manageOngoingWorks',
				controller : 'CommonController'
			})
			.when('/viewCompletedWork',{
				templateUrl: 'viewCompletedWork',
				controller : 'CommonController'
			})
			.when('/manageAsWorks',{
				templateUrl: 'manageAsWorks',
				controller : 'CommonController'
			})
		
			.when('/manageAllParentAS',{
				templateUrl :'manageAllParentAS',
				controller : 'CommonController'
			})
			.when(
				'/printSelectedAS/:parentAsId',
					{
						templateUrl : function(params) {
						return 'printSelectedAS/'
						+ params.parentAsId;
						},
						controller : 'CommonController'
				})
				.when(
				'/printPreviewGenerateAS/:workIds',
					{
						templateUrl : function(params) {
						return 'printPreviewGenerateAS/'
						+ params.workIds;
						},
						controller : 'CommonController'
				})
				.when(
				'/printPreviewSelectedAS/:parentAsId',
					{
						templateUrl : function(params) {
						return 'printPreviewSelectedAS/'
						+ params.parentAsId;
						},
						controller : 'CommonController'
				})
				
			.when('/addLegacyWork', {
				templateUrl: 'addLegacyWork',
				controller : 'CommonController'
			})
			.when('/addLegacyDataRoute', {
			templateUrl: 'addLegacyDataMapping',
			controller : 'EEController'
		     })
			 
			.when('/addNewWork', {
				templateUrl: 'addNewWork',
				controller : 'CommonController'
			})
			.when('/manageAgencyUser', {
				templateUrl: 'manageAgencyUser',
				controller: 'CommonController'

			})

			.when('/addAreaOfficer', {
				templateUrl: 'addAreaOfficer',
				controller: 'CommonController'

			})
			.when('/editOngoingWork/:id', {
				templateUrl: function(params){ return 'editOngoingWork/' + params.id; }, 
				controller : 'CommonController'
			})
			
			.when('/editWork/:id', {
				templateUrl: function(params){ return 'editWork/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/viewWork/:id', {
				templateUrl: function(params){ return 'viewWork/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/viewWorkData/:id', {
				templateUrl: function(params){ return 'viewWorkData/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/editLegacyData/:id', {
				templateUrl: function(params){ return 'editLegacyData/' + params.id; }, 
				controller : 'CommonController'
			})
			
			.when('/manageImplAgency', {
				templateUrl: 'manageImplAgency',
				controller : 'CommonController'
			})
			
			.when('/manageHead', {
				templateUrl: 'manageHead',
				controller : 'CommonController'
			})
			
			.when('/manageScheme', {
				templateUrl: 'manageScheme',
				controller : 'CommonController'
			})
			
			.when('/manageSor', {
				templateUrl: 'manageSor',
				controller : 'CommonController'
			})
			.when('/editImplAgencyForm/:id', {
				templateUrl: function(params){ return 'editImplAgencyForm/' + params.id; }, 
				controller : 'CommonController'
			})
			
		    .when('/editHeadForm/:id', {
				templateUrl: function(params){ return 'editHeadForm/' + params.id; }, 
				controller : 'CommonController'
			})
			
			.when('/editSchemeForm/:id', {
				templateUrl: function(params){ return 'editSchemeForm/' + params.id; }, 
				controller : 'CommonController'
			})
			
			.when('/editSorForm/:id', {
				templateUrl: function(params){ return 'editSorForm/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/addImplAgencyForm', {
				templateUrl: 'addImplAgencyForm',
				controller : 'CommonController'
			})
			
			.when('/addHeadForm', {
				templateUrl: 'addHeadForm',
				controller : 'CommonController'
			})
			
			.when('/addSchemeForm', {
				templateUrl: 'addSchemeForm',
				controller : 'CommonController'
			})
			
			.when('/addSorForm', {
				templateUrl: 'addSorForm',
				controller : 'CommonController'
			})
			
			.when('/manageSubEngg', {
				templateUrl: 'manageSubEngg',
				controller : 'CommonController'
			})
			.when('/editSubEnggForm/:id', {
				templateUrl: function(params){ return 'editSubEnggForm/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/addSubEnggForm', {
				templateUrl: 'addSubEnggForm',
				controller : 'CommonController'
			})
			
			.when('/subEnggPhotoUploadReport', {
				templateUrl: 'subEnggPhotoUploadReport',
				controller : 'CommonController'
			})
			.when('/implAgencyPhotoUploadReport', {
				templateUrl: 'implAgencyPhotoUploadReport',
				controller : 'CommonController'
			})
			.when('/mlaRecommendedWorks', {
				templateUrl: 'mlaRecommendedWorks',
				controller : 'CommonController'
			})
			
			
			.when('/approveAndCreateWork/:id', {
				templateUrl: function(params){ return 'approveAndCreateWork/' + params.id; }, 
				controller : 'CommonController'
			})
			
			.when('/viewMlaRecommendedWorkDet/:id', {
				templateUrl: function(params){ return 'viewMlaRecommendedWorkDet/' + params.id; }, 
				controller : 'CommonController'
			})
				.when('/manageBudgetDetails', {
				templateUrl: 'manageBudgetDetails',
				controller : 'CommonController'
			})
			.when('/addFundForm', {
				templateUrl: 'addFundForm',
				controller : 'CommonController'
			})
			.when('/editFundForm/:id', {
				templateUrl: function(params){ return 'editFundForm/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/viewFund/:id', {
				templateUrl: function(params){ return 'viewFund/' + params.id; }, 
				controller : 'CommonController'
			})
			
				.when('/mlaFundReport', {
				templateUrl: 'mlaFundReport',
				controller : 'CommonController'
			})
			
			.when('/printForm/:id', {
				templateUrl: function(params){ return 'printForm/' + params.id; }, 
				controller : 'CommonController'
			})
			.when('/manageSdr', {
				templateUrl: 'manageSdr',
				controller : 'CommonController'
			})
			.when('/addSdrForm', {
				templateUrl: 'addSdrForm',
				controller : 'CommonController'
			})
			.when('/editSdrForm/:id', {
				templateUrl: function(params){ return 'editSdrForm/' + params.id; }, 
				controller : 'CommonController'
			})

			.when('/manageAgencyUsers', {
				templateUrl: 'manageAgencyUsers',
				controller: 'SystemAdminController'
			})

			.when('/addUserAgencyFrom', {
				templateUrl: 'addUserAgencyFrom',
				controller: 'SystemAdminController'
			})
			
			

			.otherwise({
				redirectTo: '/'
			});
	}]);
