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

dms.controller('SystemAdminController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
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

		if (isStart) {
			$loading.start('sample-1');
		} else {
			$loading.finish('sample-1');
		}

	};

	$scope.loadUserRolesForManage = function() {
		$loading.start('sample-1');
		fetchRolesForManage();
	};

	$scope.loadSchemeForManage = function() {
		$loading.start('sample-1');
		fetchSchemesForManage();
	};

	$scope.loadDesignationOfThisRole = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDesignationOfThisRole/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.userData = data;
			$scope.userData.roleCode = data.roleCode + "";
			$loading.finish('sample-1');
		});
	};


	$scope.loadDashBoardData = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDashboardData');
		response.success(function(data, status, headers, config) {
			$scope.dashboardData = data;

			updateChart($scope.dashboardData.workCount, $scope.dashboardData.handOverCount, $scope.dashboardData.ccCount, $scope.dashboardData.completedCount
				, $scope.dashboardData.inProgressCount, $scope.dashboardData.notStartedCount, $scope.dashboardData.woIssuedCount, $scope.dashboardData.loaIssuesCount
				, $scope.dashboardData.reTenderCount, $scope.dashboardData.tenderApprovalInprocessCount, $scope.dashboardData.tenderRcvCount, $scope.dashboardData.tenderCalledCount
				, $scope.dashboardData.asIsuuesCount);
			$loading.finish('sample-1');
		});
	};






	$scope.loadPieChartDashboardData = function() {

		fetchPieChartDashboardData();
	};

	$scope.editRoles = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.userData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('addOrEditRoles', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageRoleDesgntn';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadDesignationOfThisScheme = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDesignationOfThisScheme/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.userData = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadSchemeOfThisDesignation = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemeOfThisDesignation/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.userData = data;
			$loading.finish('sample-1');
		});
	};


	$scope.editSchemes = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.userData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('addOrEditSchemes', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSchemeDesgntn';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};

	$scope.editDesignation = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.userData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('addOrEditDesignation', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageDesgntnScheme';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWorkCatList = function() {

		$loading.start('sample-1');
		fetchWorkCatListByDst();
	};

	$scope.loadMlaList = function() {

		$loading.start('sample-1');
		fetchMlas();
	};

	$scope.searchBySearchBox = function() {
		$loading.start('sample-1');
		if (($('#searchBox').val().trim() != "" && $('#searchBox').val().trim().length >= 2)) {
			reDraw();
		}
		else {
			$loading.finish('sample-1');
		}
	};

	$scope.resetFunction = function() {
		$loading.start('sample-1');

		$timeout(function() {
			reDraw();
		}, 0);
	};

	$scope.loadWorkCategory = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchWorkCatById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.workCatData = data;
			$scope.workCatData.workTypeBean.workTypeId = $scope.workCatData.workTypeBean.workTypeId + "";
			$loading.finish('sample-1');
		});
	};

	$scope.loadMLA = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchMLAById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.mlaData = data;
			/*$scope.workCatData.workTypeBean.workTypeId = $scope.workCatData.workTypeBean.workTypeId+""; */
			$loading.finish('sample-1');
		});
	};
	$scope.addWorkCat = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addWorkCat', $scope.workCatData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkCategory';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};

	$scope.addMlaDet = function(isValid) {

		if (!isValid)
			return false;
		var responsePromise = $http.post('fetchMLaIfAlreadyExistsForDuration', $scope.mlaData);

		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.errorMessage != null) {
				alert("Mla for Given Constituency and Vidhansabha Period already exist for this Duration. Please select other dates")
			} else {
				if (confirm("Are you sure you want to save the data?")) {
					$loading.start('sample-1');
					var responsePromise = $http.post('addMlaDet', $scope.mlaData);

					responsePromise.success(function(data, status, headers, config) {

						$rootScope.responseObject = data;

						if ($rootScope.responseObject.successMessage != null) {
							$timeout(function() {
								$rootScope.responseObject.successMessage = null;
							}, 5000);
							$window.location.href = '#manageMLA';
						}
						if ($rootScope.responseObject.errorMessage != null) {
							$timeout(function() {
								$rootScope.responseObject.errorMessage = null;
							}, 5000);
						}
						$loading.finish('sample-1');
					});
				}
			}
			$loading.finish('sample-1');
		});
	};

	$scope.addMlaDetNew = function(isValid) {

		if (!isValid)
			return false;
		var responsePromise = $http.post('fetchMLaIfAlreadyExistsForDuration', $scope.mlaData);

		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.errorMessage != null) {
				alert("Mla for Given Constituency and Vidhansabha Period already exist for this Duration. Please select other dates")
			} else {
				if (confirm("Are you sure you want to save the data?")) {
					$loading.start('sample-1');
					var responsePromise = $http.post('addMlaDetNew', $scope.mlaData);

					responsePromise.success(function(data, status, headers, config) {

						$rootScope.responseObject = data;

						if ($rootScope.responseObject.successMessage != null) {
							$timeout(function() {
								$rootScope.responseObject.successMessage = null;
							}, 5000);
							$window.location.href = '#manageMLA';
						}
						if ($rootScope.responseObject.errorMessage != null) {
							$timeout(function() {
								$rootScope.responseObject.errorMessage = null;
							}, 5000);
						}
						$loading.finish('sample-1');
					});
				}
			}
			$loading.finish('sample-1');
		});



	};

	$scope.compareDates = function(fromDate, toDate) {

		var fromDate = fromDate.split("/");
		var toDate = toDate.split("/");
		if (new Date(fromDate[2], fromDate[1] - 1, fromDate[0]) > new Date(toDate[2], toDate[1] - 1, toDate[0])) {
			alert("To Date cannot be less than From Date");
			$scope.mlaData.toDate = null;
		}

	};


	$scope.deleteWorkCat = function(workCatId) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');
			var responsePromise = $http.get('deleteWorkCatById/' + workCatId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkCategory';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	/*$scope.loadSchemes = function() {

		$loading.start('sample-1');
		fetchSchemes();
	};*/

	$scope.loadSchemeDetails = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchSchemeDetails/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.schemeData = data;
			$loading.finish('sample-1');
		});
	};
	$scope.addScheme = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addScheme', $scope.schemeData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSchemes';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};


	$scope.loadWorkTypes = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkTypes');
		response.success(function(data, status, headers, config) {
			$scope.workTypes = data;
			$loading.finish('sample-1');
		});
	};
	$scope.loadSchemesForSysAdmin = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemesForSyatemAdmin');
		response.success(function(data, status, headers, config) {
			$scope.schemes = data;
			$loading.finish('sample-1');
		});
	};

	$scope.fetchDefaultDocList = function() {
		//		$scope.fileArr = [10];
		$loading.start('sample-1');
		var response = $http.get('fetchDefaultDocList');

		response.success(function(data, status, headers, config) {
			if ($scope.workData != undefined) {
				$scope.workData.otherDocList = data;
			} else {
				$scope.workData = {};
				$scope.workData.otherDocList = data;

			}
			$loading.finish('sample-1');
		});

	};
	$scope.loadWorkCategoryByWorkType = function(workTypeId) {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkCategoryByWorkType/' + workTypeId);
		response.success(function(data, status, headers, config) {
			$scope.workCategories = data;
			$loading.finish('sample-1');
		});
	};

	$scope.searchByUserNameOrAnyFilters = function() {
		$loading.start('sample-1');
		if (($('#searchBox').val().trim() != "" && $('#searchBox').val().trim().length >= 1)
				 /*|| $('#username').val().trim()!=""*/ || $('#emailId').val().trim() != "" || $('#status').val() != "") {
			reDraw();
		}
		else {
			$loading.finish('sample-1');
		}
	};

	$scope.resetFunction = function() {
		$loading.start('sample-1');

		$timeout(function() {
			reDraw();
		}, 0);
	};

	$scope.loadUserList = function() {

		$loading.start('sample-1');
		fetchUserList();
	};

	$scope.loadUserRoles = function() {

		$loading.start('sample-1');
		var responseRoles = $http.get('fetchRoles');
		responseRoles.success(function(data, status, headers, config) {
			$scope.roles = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadUserTypes = function() {

		$loading.start('sample-1');
		var responseUserType = $http.get('fetchUserType');
		responseUserType.success(function(data, status, headers, config) {
			$scope.userTypes = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadDesignation = function() {

		$loading.start('sample-1');
		var responseUserType = $http.get('fetchDesignation');
		responseUserType.success(function(data, status, headers, config) {
			$scope.userDesignation = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadOfficeTypes = function() {

		$loading.start('sample-1');
		var responseOfficeType = $http.get('fetchOfficeType');
		responseOfficeType.success(function(data, status, headers, config) {
			$scope.officeTypes = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadAgencies = function() {

		$loading.start('sample-1');
		var responseOfficeType = $http.get('fetchConstructionAgency');
		responseOfficeType.success(function(data, status, headers, config) {
			$scope.agencies = data;
			$loading.finish('sample-1');
		});
	};



	$scope.loadOfficeTypesByUserType = function(userTypeId) {
		//alert($scope.signUpData.officeTypeId+ 'Loades');
		if (null != userTypeId && userTypeId != "null") {
			//alert(officeTypeId);
			$loading.start('sample-1');

			var response = $http.get('fetchOfficeTypesByUserType/' + userTypeId);
			response.success(function(data, status, headers, config) {
				$scope.officeTypes = data;
				$loading.finish('sample-1');
			});
		}

	};


	$scope.loadRolesByUserTypeOfficeType = function(userTypeId, officeTypeId) {
		//alert($scope.signUpData.officeTypeId+ 'Loades');
		if (null != userTypeId && userTypeId != "null" && null != officeTypeId && officeTypeId != "null") {
			//alert(officeTypeId);
			$loading.start('sample-1');
			var response = $http.get('fetchRoleTypesByUserOfficeType/' + userTypeId + '/' + officeTypeId);
			response.success(function(data, status, headers, config) {
				$scope.roles = data;
				$loading.finish('sample-1');
			});
		}

	};

	$scope.loadUserDetails = function() {

		if ($routeParams.id != null) {

			$loading.start('sample-1');
			var response = $http.get('fetchUserDetails/' + $routeParams.id);
		}

		response.success(function(data, status, headers, config) {
			$scope.userData = data;

			$scope.userData.districtId = $scope.userData.districtId + "";
			//$scope.userData.designationId = $scope.userData.designationId +"";
			//$scope.userData.subEnggId = $scope.userData.subEnggId +"";

			$scope.userData.divisionId = $scope.userData.divisionId + "";
			$scope.userData.userTypeId = $scope.userData.userTypeId + "";
			$scope.userData.officeTypeId = $scope.userData.officeTypeId + "";
			$scope.userData.implementationAgencyId = $scope.userData.implementationAgencyId + "";
			//$scope.loadDesignationsByRole($scope.userData.role.roleCode);$scope.loadAllSubEngg();
			$scope.userData.designationId = $scope.userData.designationId + "";
			$loading.finish('sample-1');
		});
	};

	$scope.addUser = function(isValid) {
		// alert(userData.designationId);
		if (!isValid)
			return false;


		$scope.userData.districtId = Number(49);
		$scope.userData.divisionId = Number(3);


		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addUser', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if ($scope.userData.designationId == 2) {
						$window.location.href = '#manageDepartmentUser';
					}

					else {

						$window.location.href = '#manageDepartmentUser';
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if ($scope.userData.designationId == 2) {
						$window.location.href = '#manageDepartmentUser';
					}

					else {

						//$window.location.href = '#manageusers';
						$window.location.href = '#manageDepartmentUser';
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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

			var responsePromise = $http.get('deleteUser/' + userId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$window.location.href = '#manageDepartmentUser';

				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	/*$scope.loadDesignations = function() {

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
	};*/

	$scope.loadDistricts = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDistricts');
		response.success(function(data, status, headers, config) {
			$scope.districts = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadDivisions = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDivisions');
		response.success(function(data, status, headers, config) {
			$scope.divisions = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadUserForm = function() {

		$scope.userData = {};

		$http.get('fetchLoggedInUser').then(function(response) {

			var user = response.data;

			$scope.userData.districtId = user.districtId + "";

			$scope.implAgencyData.emailId = user.emailId + "";

			$scope.userData.roleCode = user.role.roleCode + "";
		});
	}

	$scope.loadLCs = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchLegislativeConstituency');
		response.success(function(data, status, headers, config) {
			$scope.lcs = data;
			$loading.finish('sample-1');
		});
	};


	/*$scope.loadRole = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchRole');
		response.success(function(data, status, headers, config) {
			$scope.roles = data;

			angular.forEach($scope.roles, function(value, key) {
				if (value.roleCode){
					alert("------------" + $scope.roleCode)
					$scope.userData.role = $scope.roles.roleCode;
				}
					//console.log("username is thomas");
			});
				
				
				
				
				$loading.finish('sample-1');
			});
		};*/


	$scope.loadRole = function() {
		$loading.start('sample-1');

		$scope.userData = {};

		$http.get('fetchRole').then(function(response) {
			$scope.roles = response.data;

			// Iterate over each role and perform operations
			angular.forEach($scope.roles, function(role) {
				if (role.roleCode) {
					alert("Role Code: " + role.roleCode);
					// Set the roleCode to userData.role or perform any other necessary operations
					$scope.userData.role = role.roleCode;
				}
			});

			$loading.finish('sample-1');
		}).catch(function(error) {
			console.error("Error fetching roles:", error);
			$loading.finish('sample-1');
		});
	};


	$scope.loadUserAgencyList = function() {

		$loading.start('sample-1');
		fetchUserAgencyList();
	};


	$scope.loadAgenciesById = function() {
		//	alert("call================ ")
		$loading.start('sample-1');
		var responseOfficeType = $http.get('fetchConstructionAgencyById');
		responseOfficeType.success(function(data, status, headers, config) {
			$scope.agencies = data;
			//	alert($scope.agencies.implementationAgencyId+'$scope.agencies.implementationAgencyId')
			$scope.userData.implementationAgencyId = $scope.agencies.implementationAgencyId + '';
			$scope.userData.implementationAgencyNameE = $scope.agencies.implementationAgencyNameE;
			$loading.finish('sample-1');
		});
	};


	//ManageWorkFacility-loadWorkFacility

	$scope.loadWorkFacility = function() {
		$loading.start('sample-1');
		fetchWorkFacility();
		$loading.finish('sample-1');
	}

	$scope.addWorkFacility = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addWorkFacility', $scope.formData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkFacility';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};

	//loadWorkFacilityEdit



	$scope.loadWorkFacilityEdit = function() {
		$loading.start('sample-1');
		console.log("Route Params ID: ", $routeParams.id);
		var response = $http.get('fetchWorkFacilityById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.formData = data;
			$loading.finish('sample-1');
		});
	};


	//deleteWorkFacility
	$scope.deleteWorkFacility = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteWorkFacility/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkFacility';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};




	//addsubtype
	$scope.addWorkSubType = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addWorkSubType', $scope.formData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkType';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};

	//loadWorkSubtype

	$scope.loadWorkSubtype = function() {
		$loading.start('sample-1');
		fetchWorkSubType();
		$loading.finish('sample-1');
	}


	//delete 

	$scope.deleteWorkSubType = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteWorkSubType/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkType';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};


	//loadWorkSubTypeEdit



	$scope.loadWorkSubTypeEdit = function() {
		$loading.start('sample-1');
		console.log("Route Params ID: ", $routeParams.id);
		var response = $http.get('fetchWorkTypeById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.formData = data;
			$loading.finish('sample-1');
		});
	};


	//addImplAgency

	$scope.addImplAgencyy = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addImplAgencyy', $scope.formData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageImplAgencyy';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};


	$scope.loadImplAgecnyy = function() {
		$loading.start('sample-1');
		fetchImplAgencyyById();
		$loading.finish('sample-1');
	}



	//deleteImplAgency

	$scope.deleteImplAgencyy = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteImplAgencyy/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageImplAgencyy';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	//loadImplAgecnyyEdit


	$scope.loadImplAgecnyyEdit = function() {
		$loading.start('sample-1');
		console.log("Route Params ID: ", $routeParams.id);
		var response = $http.get('fetchImplAgencyy/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.formData = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadAllDistrict = function() {
		$loading.start('sample-1');
		fetchAllDistricts();
		$loading.finish('sample-1');
	}

	$scope.loadDistrictMP = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDistrictsMP');
		response.success(function(data, status, headers, config) {
			$scope.districtMP = data;
			$loading.finish('sample-1');
		});
	};

	$scope.fetchBlockByDistrict = function() {
		$loading.start('sample-1');
		fetchBlockByDistrict();
		$loading.finish('sample-1');
	}



	$scope.loadBlockByDistrict = function(dID) {

		$loading.start('sample-1');
		var response = $http.get('fetchBlocksByDistirct/' + dID);
		response.success(function(data, status, headers, config) {
			$scope.blocks = data;
			$loading.finish('sample-1');
		});
	};
	$scope.loadBlockByDistrict2 = function(dID) {

		$loading.start('sample-1');
		var response = $http.get('fetchBlocksByDistirct2/' + dID);
		response.success(function(data, status, headers, config) {
			$scope.blocks = data;
			$loading.finish('sample-1');
		});
	};
	$scope.loadDistrictDetails = function() {

		if ($routeParams.id != null) {

			$loading.start('sample-1');
			var response = $http.get('fetchDistrictDetails/' + $routeParams.id);
		}

		response.success(function(data, status, headers, config) {
			$scope.districtData = data;
			$scope.districtData.divisionId = $scope.districtData.divisionId + "";


			$loading.finish('sample-1');
		});
	};
	$scope.loadBlockDetails = function() {

		if ($routeParams.id != null) {

			$loading.start('sample-1');
			var response = $http.get('fetchBlockDetails/' + $routeParams.id);
		}

		response.success(function(data, status, headers, config) {
			$scope.blockData = data;


			$loading.finish('sample-1');
		});
	};
	$scope.loadGrampanchayatDetails = function() {

		if ($routeParams.id != null) {

			$loading.start('sample-1');
			var response = $http.get('fetchGPDetails/' + $routeParams.id);
		}

		response.success(function(data, status, headers, config) {
			$scope.gpData = data;
			$scope.loadBlockByDistrict2($scope.gpData.districtCode);
			$scope.gpData.blockCode += "";

			$loading.finish('sample-1');
		});
	};

	$scope.addDistrict = function(isValid) {
		if (!isValid) {
			return false;
		}
		// $scope.districtData.districtNameH = decodeURIComponent(escape( $scope.districtData.districtNameH));

		//alert($scope.districtData.districtNameH);
		//alert(decodeURIComponent($scope.districtData.districtNameH));
		//alert($scope.districtData.divisionId);


		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addDistrict', $scope.districtData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageDistricts';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};
	$scope.addBlock = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addBlock', $scope.blockData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageBlock';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};
	$scope.addGP = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addGP', $scope.gpData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageGrampanchayat';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};


	$scope.deleteDistrict = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteDistrict/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageDistricts';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};



	$scope.deleteBlock = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteBlock/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageBlock';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	$scope.deleteGP = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteGP/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageGrampanchayat';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	$scope.remakrsChange = function() {

		if ($scope.workData.dmRemakrs == null) {
			$scope.workData.dmStatus = null;
		}
	};

	$scope.approveUser = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.userData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editUser', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$window.location.href = '#managePendingUsers';

				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		}
	};

	$scope.financialYearjson = {}

	$scope.loadFinancialYears = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchFinancialYear');
		response.success(function(data, status, headers, config) {
			$scope.financialYears = data;

			$scope.financialYearjson = angular.copy(data[0]);

			var i = $scope.financialYearjson.financialYearName.charAt(2) + $scope.financialYearjson.financialYearName.charAt(3) + "";

			var j = $scope.financialYearjson.financialYearName.charAt(5) + $scope.financialYearjson.financialYearName.charAt(6) + "";

			i = j;
			j = Number(j) + 1 + "";
			$scope.financialYearjson.financialYearName = "" + 20 + "" + (i) + "-" + (j);


			$loading.finish('sample-1');
		});
	};




	$scope.loadFinancialYearData = function(id) {



		$loading.start('sample-1');
		var response = $http.get('fetchFinancialYearData/' + id);

		response.success(function(data, status, headers, config) {
			$scope.financialYear = data;




			$scope.financialYear.enabled += "";
			$loading.finish('sample-1');

		});
	};










	$scope.addFinancialForm = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {


			var responsePromise = $http.post('addfinancialYear', $scope.financialYear);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				//alert($rootScope.responseObject.successMessage);
				reDraw();
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);

					$window.location.href = '#addFinancialYear';
					$scope.loadFinancialYears();

				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};
	/*$scope.loadWorkSubtype = function() {
   //alert("call------------==========")
		   $loading.start('sample-1');
		   var response = $http.get('fetchWorkSubTypes');
		   response.success(function(data, status, headers, config) {
			   $scope.workSubTypes = data;
			   $loading.finish('sample-1');
		   });
	   };*/


	$scope.loadWorkSubtypes = function() {
		$loading.start("sample-1");
		if (localStorage.getItem("filters_data") == null) {
			//$scope.workData = {};

			$scope.workSubTypes = {};
			fetchWorkSubTypes();
		}
	};

	//addsubtypes
	$scope.addWorkSubTypes = function(isValid) {
		if (!isValid) {
			return false;
		}
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');

			var responsePromise = $http.post('addWorkSubTypes', $scope.formData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkSubTypes';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$loading.finish('sample-1');
				}
			})
		}

	};
	
	//loadWorkSubTypeEdit

	$scope.loadWorkSubTypeEditForm = function() {
		$loading.start('sample-1');
		console.log("Route Params ID: ", $routeParams.id);
		var response = $http.get('fetchWorkSubTypeById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.formData = data;
			$loading.finish('sample-1');
		});
	};
	
	//delete WorkSubTypes

	$scope.deleteWorkSubTypes = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteWorkSubTypes/' + id);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageWorkSubTypes';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

});