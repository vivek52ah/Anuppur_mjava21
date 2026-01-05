//Created By Yousra

var dms = angular.module('dms');


dms.controller('AdminController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
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
	
	$scope.loadDesignationsByRole = function(roleCode) {

		 $loading.start('sample-1');
		 var response = $http.get('fetchDesignationsByRole/'+ roleCode);
		 response.success(function(data, status, headers, config) {
			 $scope.designations = data;
			 $loading.finish('sample-1');
		 });
	 };
	 
	 $scope.loadImplementationAgencies = function() {

			$loading.start('sample-1');
			var response = $http.get('fetchImplAgency');
			response.success(function(data, status, headers, config) {
				$scope.implementationAgencies = data;
				$loading.finish('sample-1');
			});
		};
		
		$scope.loadSubEnggByIA = function(implementationAgencyId) {

			if(implementationAgencyId && implementationAgencyId!=null && implementationAgencyId!='null'){
				$loading.start('sample-1');
				var response = $http.get('fetchSubEnggByImplAgencyId/'+implementationAgencyId);
				response.success(function(data, status, headers, config) {
					$scope.subEngg = data;
					$loading.finish('sample-1');
				});
			}
		};
	
		$scope.loadWorkStatus = function() {

			$loading.start('sample-1');
			var response = $http.get('fetchWorkStatus');
			response.success(function(data, status, headers, config) {
				$scope.workStatus = data;
				$loading.finish('sample-1');
			});
		};
	
	$scope.fetchWorksForWorkBook = function(iaId, subEnggId, designationId, workStatusName){
		
		$loading.start('sample-1');
		
		if(!iaId){
			iaId = 0;
		}
		if(!subEnggId){
			subEnggId = 0;
		}
		if(!designationId){
			designationId = 0;
		}
		if(!workStatusName){
			workStatusName = 'x';
		}
		
		var response = $http.get('fetchWorksForWorkBook/'+iaId+'/'+subEnggId+'/'+designationId+'/'+workStatusName);

		response.success(function(data, status, headers, config) {
			$scope.workData = data;
			$loading.finish('sample-1');
		});
	};
	
	
	$scope.fetchData = function() {
                const apiUrl = 'http://localhost:8080/api';

                $http({
                    method: 'GET',
                    url: apiUrl,
                    headers: {
                        'API-PASSWORD': $scope.password
                    }
                }).then(function(response) {
                    $scope.data = response.data;
                }).catch(function(error) {
                    console.error('Error fetching data:', error);
                    $scope.data = 'Error fetching data';
                });
            };
       
       
        });
