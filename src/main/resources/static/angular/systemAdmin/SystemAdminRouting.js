var dms = angular.module('dms');
dms.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
	}]);

dms.run(['Idle', function(Idle) {
Idle.watch();
}]);

function getAppContextPath() {
	var path = window.location.pathname || '';
	var segments = path.split('/');
	return segments.length > 1 && segments[1] ? '/' + segments[1] : '';
}

function rootTemplateUrl(url) {
	var path = window.location.pathname || '';
	var segments = path.split('/');
	
	// Get context path (e.g., 'anuppur')
	var contextPath = '';
	if (segments.length > 1 && segments[1]) {
		contextPath = '/' + segments[1];
	}
	
	// Get controller base path (e.g., 'systemAdmin', 'admin', etc.)
	var controllerBase = '';
	if (segments.length > 2 && segments[2]) {
		// Check if it's a known controller path
		var knownControllers = ['systemAdmin', 'admin', 'superAdmin', 'dpo', 'district', 'hq', 'division', 'agencyAdmin', 'ceo'];
		if (knownControllers.indexOf(segments[2]) !== -1) {
			controllerBase = '/' + segments[2];
		}
	}
	
	if (url && url.charAt(0) === '/') {
		url = url.substring(1);
	}
	
	return contextPath + controllerBase + '/' + url;
}

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
			.when('/manageOngoingWorks', {
				templateUrl: rootTemplateUrl('manageOngoingWorks'),
				controller: 'CommonController'
			})
			.when('/departmentWiseWorksReport', {
				templateUrl: rootTemplateUrl('departmentWiseWorksReport'),
				controller: 'CommonController'
			})
			.when('/photoUpdateReport', {
				templateUrl: rootTemplateUrl('photoUpdateReport'),
				controller: 'CommonController'
			})
			.when('/dmRemarkWiseReport', {
				templateUrl: rootTemplateUrl('dmRemarkWiseReport'),
				controller: 'CommonController'
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
				controller : 'SystemAdminController'
			})
			
			.when('/agencyWiseReport', {
				templateUrl: rootTemplateUrl('agencyWiseReport'),
				controller: 'CommonController'
			})
			.when('/schemeWiseReport', {
				templateUrl: rootTemplateUrl('schemeWiseReport'),
				controller: 'CommonController'
			})
			.when('/yearWiseReport', {
				templateUrl: rootTemplateUrl('yearWiseReport'),
				controller: 'CommonController'
			})
			.when('/reports', {
				templateUrl: rootTemplateUrl('reports'),
				controller: 'CommonController'
			})
			.when('/inspectionReport', {
				templateUrl: rootTemplateUrl('inspectionReport'),
				controller: 'CommonController'
			})
			.when('/workExpenditureReport', {
				templateUrl: rootTemplateUrl('workExpenditureReport'),
				controller: 'CommonController'
			})
			.when('/segmentWiseRport', {
				templateUrl: rootTemplateUrl('segmentWiseRport'),
				controller: 'CommonController'
			})
			.when('/schemeYearWiseReport', {
				templateUrl: rootTemplateUrl('schemeYearWiseReport'),
				controller: 'CommonController'
			})
			.when('/divisionReport', {
				templateUrl: rootTemplateUrl('divisionReport'),
				controller: 'CommonController'
			})
			.when('/drawingStatusReport', {
				templateUrl: rootTemplateUrl('drawingStatusReport'),
				controller: 'CommonController'
			})
			.when('/asIssuedReport', {
				templateUrl: rootTemplateUrl('asIssuedReport'),
				controller: 'CommonController'
			})
			.when('/physicalPercentageWiseReport', {
				templateUrl: rootTemplateUrl('physicalPercentageWiseReport'),
				controller: 'CommonController'
			})
			.when('/generatePptReports', {
				templateUrl: rootTemplateUrl('generatePptReports'),
				controller: 'CommonController'
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
			.when('/manageWorkSubtype',{
				templateUrl:'manageWorkSubtype',
				controller:'SystemAdminController'
			})

			.when('/editWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editWork/' + params.id); },
				controller: 'CommonController'
			})
			.when('/viewWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('viewWork/' + params.id); },
				controller: 'CommonController'
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
