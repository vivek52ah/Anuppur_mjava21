var dms = angular.module('dms');

/*dms.run(['$rootScope', function($rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;        
    });
}]);*/

/*//Directive for chart, pass in chart options
dms.directive('hcChart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        scope: {
            options: '='
        },
        link: function (scope, element) {
            Highcharts.chart(element[0], scope.options);
        }
    };
})*/

dms.controller('SuperAdminController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
	$scope.started = false;
	
	$scope.doTheBack = function() {
		  window.history.back();
		};
	
	function closeModals() {
		if ($scope.warning) {
			$scope.warning.close();
			$scope.warning = null;
		}

		if ($scope.timedout) {
			$scope.timedout.close();
			$scope.timedout = null;
		}
	}

	$scope.$on('IdleStart', function() {
		closeModals();
	});

	$scope.$on('IdleEnd', function() {
		closeModals();
	});

	$scope.$on('IdleTimeout', function() {
		closeModals();
		alert("Your Session has expired, Please relogin.");
		$window.location.reload();
	});

	$scope.startOrStopSpinner = function(isStart) {

		if(isStart) {
			$loading.start('sample-1');
		} else {
			$loading.finish('sample-1');			
		}

	};
	
	$scope.searchByUserNameOrAnyFilters = function(){
		 $loading.start('sample-1');
		 if(($('#searchBox').val().trim()!=""&& $('#searchBox').val().trim().length >=4) 
				 /*|| $('#username').val().trim()!=""*/ || $('#emailId').val().trim()!="" ||  $('#status').val()!=""){
			 reDraw();
		 }
		 else{
			 $loading.finish('sample-1');
		 }
	 };
	 
	 $scope.resetFunction = function() {
			$loading.start('sample-1');

			$timeout(function () {
				reDraw();
			}, 0);
		};

	$scope.loadUserList = function() {
		$loading.start('sample-1');
		if (typeof window.initManageUsersTable === 'function') {
			window.initManageUsersTable();
		} else if (typeof fetchUserList === 'function') {
			fetchUserList();
		} else {
			console.error('initManageUsersTable is not defined yet');
			$loading.finish('sample-1');
		}
	};

	 $scope.loadUserRoles = function() {

		 $loading.start('sample-1');
		 var responseRoles = $http.get('fetchRoles');
		 responseRoles.success(function(data, status, headers, config) {
			 $scope.roles = data;
			 $loading.finish('sample-1');
		 });
	 };

	 $scope.loadUserDetails = function() {

		 $loading.start('sample-1');
		 var response = $http.get('fetchUserDetails/'+$routeParams.id);

		 response.success(function(data, status, headers, config) {
			 $scope.userData = data;
			 $scope.userData.districtId = $scope.userData.districtId +"";
			 $scope.userData.designationId = $scope.userData.designationId +"";
			 $scope.userData.subEnggId = $scope.userData.subEnggId +"";
			 
			 $scope.loadDesignationsByRole($scope.userData.role.roleCode);$scope.loadAllSubEngg();
			 $loading.finish('sample-1');
		 });
	 };

	 $scope.addUser = function(isValid) {

		 if (!isValid) 
			 return false;
			 
			
        
       
			 

		 if (confirm("Are you sure you want to save the data?")) {
			 $loading.start('sample-1');
			 var responsePromise = $http.post('addUser', $scope.userData);

			 responsePromise.success(function(data, status, headers, config) {

				 $rootScope.responseObject = data;

				 if($rootScope.responseObject.successMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.successMessage = null;
					 }, 5000);
					 $window.location.href = '#manageusers';
				 }
				 if($rootScope.responseObject.errorMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.errorMessage = null;
					 }, 5000);
				 }
				 $loading.finish('sample-1');
			 });
		 }
	 };

	 $scope.editUser = function(isValid) {

		 if (!isValid) 
			 return false;

		 if (confirm("Are you sure you want to save the data?")) {
			 //$scope.userData.id = $routeParams.id;
			 $loading.start('sample-1');
			 var responsePromise = $http.post('editUser', $scope.userData);

			 responsePromise.success(function(data, status, headers, config) {

				 $rootScope.responseObject = data;

				 if($rootScope.responseObject.successMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.successMessage = null;
					 }, 5000);
					 $window.location.href = '#manageusers';
				 }
				 if($rootScope.responseObject.errorMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.errorMessage = null;
					 }, 5000);
				 }
				 $loading.finish('sample-1');
			 });
		 }
	 };

	 $scope.deleteUser = function(userId) {		
		 if (confirm("Are you sure to delete this entry?")) {
			 $loading.start('sample-1');

			 var responsePromise = $http.post('deleteUser/'+ userId);
			 responsePromise.success(function(data, status, headers, config) {
				 $rootScope.responseObject = data;
				 if ($rootScope.responseObject.successMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.successMessage = null;
					 }, 5000);
					 
					 $window.location.href = '#manageusers';
				 }
				 if($rootScope.responseObject.errorMessage != null) {
					 $timeout(function() {
						 $rootScope.responseObject.errorMessage = null;
					 }, 5000);
				 }
				 $loading.finish('sample-1');
			 });
		 }else{
			 return false;
		 }
	 };
	 
	 $scope.loadDesignations = function() {

		 $loading.start('sample-1');
		 var response = $http.get('fetchDesignations');
		 response.success(function(data, status, headers, config) {
			 $scope.designations = data;
			 $loading.finish('sample-1');
		 });
	 };

	 $scope.loadDesignationsByRole = function(roleCode) {

		 $loading.start('sample-1');
		 var response = $http.get('fetchDesignationsByRole/'+ roleCode);
		 response.success(function(data, status, headers, config) {
			 $scope.designations = data;
			 $loading.finish('sample-1');
		 });
	 };	
	 
	 $scope.loadAllSubEngg = function() {

		 $loading.start('sample-1');
		 var response = $http.get('fetchSubEngg');
		 response.success(function(data, status, headers, config) {
			 $scope.subEngg = data;
			 $loading.finish('sample-1');
		 });
	 };
	 
	 $scope.loadDistricts = function() {

		 $loading.start('sample-1');
		 var response = $http.get('fetchDistricts');
		 response.success(function(data, status, headers, config) {
			 $scope.districts = data;
			 $loading.finish('sample-1');
		 });
	 };
	 
	 $scope.loadUserForm = function(){
		 
		 $scope.userData = {};
			
		$http.get('fetchLoggedInUser').then(function(response){
			
			var user = response.data;
			
			$scope.userData.districtId = user.districtId+""; 
		});
	}
	
	$scope.loadStateIdOfMPsss = function() {
	alert("call===AAA==")
//	Pace.start();
	var response = $http.get('fetchStateIdOfMP');
	response.success(function(data, status, headers, config) {
		
		$scope.userData.agencyId = data;
		//alert("call function ===== $scope.userData.agencyId " + $scope.userData.agencyId)
	//	Pace.stop();
	});
};
});
