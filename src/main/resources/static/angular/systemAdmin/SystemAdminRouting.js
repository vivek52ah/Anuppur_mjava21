// Get the existing dms module (don't recreate it)
var dms = angular.module('dms');

// Add routes to the existing module
dms
	.config( ['$routeProvider', function($routeProvider) {
		$routeProvider
		
			.when('/manageRoleDesgntn', {
				templateUrl: 'manageRoleDesgntn',
				controller : 'SystemAdminController'
			})
			.when('/manageSchemeDesgntn', {
				templateUrl: 'manageSchemeDesgntn',
				controller : 'SystemAdminController'
			})
			.when('/manageDesgntnScheme', {
				templateUrl: 'manageDesgntnScheme',
				controller : 'SystemAdminController'
			})
			.when('/editThisRoleDesignation/:id', {
				templateUrl: function(params){ return 'editThisRoleDesignation/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/editThisSchemeDesignation/:id', {
				templateUrl: function(params){ return 'editThisSchemeDesignation/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/editThisDesignationScheme/:id', {
				templateUrl: function(params){ return 'editThisDesignationScheme/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/manageWorkCategory', {
				templateUrl: 'manageWorkCategory',
				controller : 'SystemAdminController'
			})
			.when('/manageMLA', {
				templateUrl: 'manageMLA',
				controller : 'SystemAdminController'
			})
			.when('/addWorkCatForm', {
				templateUrl: 'addWorkCatForm',
				controller : 'SystemAdminController'
			})
			.when('/editWorkCatForm/:id', {
				templateUrl: function(params){ return 'editWorkCatForm/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/addMLAForm', {
				templateUrl: 'addMLAForm',
				controller : 'SystemAdminController'
			})
			.when('/editMLAForm/:id', {
				templateUrl: function(params){ return 'editMLAForm/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			
			.when('/manageSchemes', {
				templateUrl: 'manageSchemes',
				controller : 'SystemAdminController'
			})
			.when('/addSchemeForm', {
				templateUrl: 'addSchemeForm',
				controller : 'SystemAdminController'
			})
			.when('/editSchemeForm/:id', {
				templateUrl: function(params){ return 'editSchemeForm/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/manageusers', {
				templateUrl:  'manageusers',
				controller : 'SystemAdminController'
			})
			.when('/manageOngoingWorks',{
				templateUrl: 'manageOngoingWorks',
				controller : 'CommonController'
			})
			.when('/departmentWiseWorksReport',{
				templateUrl: 'departmentWiseWorksReport',
				controller : 'CommonController'
			})
			.when('/photoUpdateReport',{
				templateUrl: 'photoUpdateReport',
				controller : 'CommonController'
			})
			.when('/dmRemarkWiseReport',{
				templateUrl: 'dmRemarkWiseReport',
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
			
			.when('/dashboard', {
				templateUrl: 'dashboard',
				controller : 'SystemAdminController'
			})
			.when('/manageAgencyUsers', {
				templateUrl: 'manageAgencyUsers',
				controller: 'SystemAdminController'
			})

			.when('/addUserAgencyFrom', {
				templateUrl: 'addUserAgencyFrom',
				controller: 'SystemAdminController'
			})
			
			.when('/agencyWiseReport', {
				templateUrl: 'agencyWiseReport',
				controller : 'CommonController'
			})
			
			.when('/schemeWiseReport', {
				templateUrl: 'schemeWiseReport',
				controller : 'CommonController'
			})
			
			.when('/yearWiseReport', {
				templateUrl: 'yearWiseReport',
				controller : 'CommonController'
			})
			.when('/reports', {
				templateUrl: 'reports',
				controller : 'CommonController'
			})
			.when('/inspectionReport', {
				templateUrl: 'inspectionReport',
				controller : 'CommonController'
			})
			.when('/workExpenditureReport', {
				templateUrl: 'workExpenditureReport',
				controller : 'CommonController'
			})
			.when('/segmentWiseRport', {
				templateUrl: 'segmentWiseRport',
				controller : 'CommonController'
			})
			.when('/schemeYearWiseReport', {
				templateUrl: 'schemeYearWiseReport',
				controller : 'CommonController'
			})
			.when('/divisionReport', {
				templateUrl: 'divisionReport',
				controller : 'CommonController'
			})
			.when('/drawingStatusReport', {
				templateUrl: 'drawingStatusReport',
				controller : 'CommonController'
			})
			.when('/asIssuedReport', {
				templateUrl: 'asIssuedReport',
				controller : 'CommonController'
			})
			.when('/physicalPercentageWiseReport', {
				templateUrl: 'physicalPercentageWiseReport',
				controller : 'CommonController'
			})
			.when('/generatePptReports', {
				templateUrl: 'generatePptReports',
				controller : 'CommonController'
			})
			
			.when('/addWorkFacility',{
				templateUrl:'addWorkFacility',
				controller:'SystemAdminController'
			})
			
			.when('/manageWorkFacility',{
				templateUrl:'manageWorkFacility',
				controller:'SystemAdminController'
				
			})
			
			.when('/editWorkFacility/:id', {
				templateUrl: function(params){ return 'editWorkFacility/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/addWorkType',{
				templateUrl:'addWorkSubType',
				controller:'SystemAdminController'
			})
			
			.when('/manageWorkType',{
				templateUrl:'manageWorkSubtype',
				controller:'SystemAdminController'
				
			})
			
			.when('/editWorkType/:id', {
				templateUrl: function(params){ return 'editWorkSubType/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			
			.when('/addImplAgencyy',{
				templateUrl:'addImplAgencyy',
				controller:'SystemAdminController'
			})
			
			.when('/manageImplAgencyy',{
				templateUrl:'manageImplAgencyy',
				controller:'SystemAdminController'
				
			})
			.when('/addFinancialYear',{
				templateUrl:'addFinancialYear',
				controller:'SystemAdminController'
				
			})
			
			.when('/editImplAgencyy/:id', {
				templateUrl: function(params){ return 'editImplAgencyy/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/manageDepartmentUser',{
				templateUrl:'manageDepartmentUser',
				controller:'SystemAdminController'
				
			})
			.when('/addDepartmentUser',{
				templateUrl:'addDepartmentUser',
				controller:'SystemAdminController'
				
			})
			.when('/editDepartmentUser/:id', {
				templateUrl: function(params){ return 'editDepartmentUser/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/manageDistricts',{
				templateUrl:'manageDistricts',
				controller:'SystemAdminController'
				
			})
			.when('/manageBlock',{
				templateUrl:'manageBlock',
				controller:'SystemAdminController'
				
			})
			.when('/manageGrampanchayat',{
				templateUrl:'manageGrampanchayat',
				controller:'SystemAdminController'
				
			})
			.when('/addDistrict',{
				templateUrl:'addDistrict',
				controller:'SystemAdminController'
				
			})
			.when('/editDistrict/:id', {
				templateUrl: function(params){ return 'editDistrict/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/addBlock',{
				templateUrl:'addBlock',
				controller:'SystemAdminController'
				
			})
			.when('/editBlock/:id', {
				templateUrl: function(params){ return 'editBlock/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/addGrampanchayat',{
				templateUrl:'addGrampanchayat',
				controller:'SystemAdminController'
				
			})
			.when('/editGrampanchayat/:id', {
				templateUrl: function(params){ return 'editGrampanchayat/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.when('/managePendingUsers',{
				templateUrl:'managePendingUsers',
				controller:'SystemAdminController'
				
			})
			.when('/ApproveUser/:id', {
				templateUrl: function(params){ return 'ApproveUser/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			
			.when('/manageWorkSubTypes',{
				templateUrl:'manageWorkSubTypes',
				controller:'SystemAdminController'
				
			})
			
			.when('/addWorkSubTypes',{
				templateUrl:'addWorkSubTypes',
				controller:'SystemAdminController'
			})
			
			.when('/editWorkSubTypes/:id', {
				templateUrl: function(params){ return 'editWorkSubTypes/' + params.id; }, 
				controller : 'SystemAdminController'
			})
			.otherwise({
				redirectTo: '/'
			});
	}]);
