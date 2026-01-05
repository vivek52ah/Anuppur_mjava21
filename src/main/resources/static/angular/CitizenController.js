var dms = angular.module('dms', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
dms.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            var maxSizeUpload = 1048576;
            
            var allowedExtensionsForImage = ['png', 'PNG','jpeg', 'JPEG', 'jpg', 'JPG'];

            element.bind('change', function () {
            	if (element[0].files[0]) {
					var fileSize = element[0].files[0].size;
					var fileExtension = element[0].files[0].name.substring(element[0].files[0].name.lastIndexOf('.') + 1);
					
					switch (attrs.fileModel) {
					
					case "imageCitizen":
						scope.maxSizeErrorForImageCitizen = (fileSize > maxSizeUpload);					
						scope.fileExtentionErrorForImageCitizen = (allowedExtensionsForImage.indexOf(fileExtension) < 0);							
						if (scope.fileExtentionErrorForImageCitizen == false && scope.maxSizeErrorForImageCitizen == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}					
					break;
					default:
					break;
					}
            	}
            	
             /*   scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);*/
               /* });*/
            });
        }
    };
}]);

dms.controller('CitizenController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
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
	
	$scope.loadStates = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchStates');
		response.success(function(data, status, headers, config) {
			$scope.states = data;
			$loading.finish('sample-1');
		});
	};
	
	$scope.loadDistrictsByStateFA = function(stateId) {

		$loading.start('sample-1');
		$scope.entrepreneurData.factoryAddress.districtId = "";
		var response = $http.get('fetchDistrictsByState/'+stateId);
		response.success(function(data, status, headers, config) {
			$scope.districtsFA = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadDistrictsByStateRA = function(stateId) {

		$loading.start('sample-1');
		$scope.entrepreneurData.registeredAddress.districtId = "";
		var response = $http.get('fetchDistrictsByState/'+stateId);
		response.success(function(data, status, headers, config) {
			$scope.districtsRA = data;
			$loading.finish('sample-1');
		});
	};
	
	$scope.assignParentAccountName = function() {
		
		$scope.entrepreneurData.parentAccountName = $('#parentAccount').val().trim();
	}
	
	$scope.validateSubAcctStartingDate = function() {
		$scope.isSubAcctStartingDateSmaller = false;
		
		if($scope.entrepreneurData.parentAccountName && $scope.entrepreneurData.parentAccountName.includes(' [')){
			
			var parentAccountName = $scope.entrepreneurData.parentAccountName.split(" [")[0];
			var response = $http.get('fetchEntrepreneursByNameOrAccNo', {params: {'searchBoxVal': parentAccountName}});

			response.success(function(data, status, headers, config) {
				if(data.length>=1){
					var parentAcct = data[0];
					
					var newSubAcctStartingDate = null;
					var newParentAcctStartingDate = null;
					
					if (parentAcct.startingDate && $scope.entrepreneurData.startingDate) {
						var parentAcctStartingDate = parentAcct.startingDate.split("/");
						//newParentAcctStartingDate = new Date(parentAcctStartingDate[2], parentAcctStartingDate[1] - 1, parentAcctStartingDate[0]);
						newParentAcctStartingDate = new Date(parentAcctStartingDate[1], parentAcctStartingDate[0] - 1, 1);
					
						var subAcctStartingDate = $scope.entrepreneurData.startingDate.split("/");
						//newSubAcctStartingDate = new Date(subAcctStartingDate[2], subAcctStartingDate[1] - 1, subAcctStartingDate[0]);
						newSubAcctStartingDate = new Date(subAcctStartingDate[1], subAcctStartingDate[0] - 1, 1);
						
						if (newSubAcctStartingDate < newParentAcctStartingDate) {
							$scope.isSubAcctStartingDateSmaller = true;
							$scope.parentAcctStartingDate = parentAcct.startingDate
						}
					}
					$scope.entrepreneurData.parentAccountId = parentAcct.id;
				}
			});			
		}
	};
	
	$scope.toggleRegisteredAddress = function(){

		if($scope.sameAsFactoryAddress){
			$scope.entrepreneurData.registeredAddress = angular.copy($scope.entrepreneurData.factoryAddress);
			$scope.districtIdRA = $scope.entrepreneurData.registeredAddress.districtId;
			if($scope.entrepreneurData.registeredAddress.stateId)
				$scope.loadDistrictsByStateRA($scope.entrepreneurData.registeredAddress.stateId);
			$scope.entrepreneurData.registeredAddress.districtId = $scope.districtIdRA;
		}else{
			$scope.entrepreneurData.registeredAddress = {};
			$scope.entrepreneurData.registeredAddress.country = 'India';
		}
	}
	
	$scope.newEntrepreneurSignup = function(form) {

		if (!form.$valid) 
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			
			var password = $scope.entrepreneurData.password;
			var confirmPassword = $scope.entrepreneurData.confirmPassword;
			$scope.entrepreneurData.password = hash($scope.entrepreneurData.password);
			$scope.entrepreneurData.confirmPassword = hash($scope.entrepreneurData.confirmPassword);
			
			var responsePromise = $http.post('newEntrepreneurSignup', $scope.entrepreneurData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					//$("#entrepreneur-success-popup").modal("show");
					$window.location.href = 'login?register';
				}
				if($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$scope.entrepreneurData.password = password;
					$scope.entrepreneurData.confirmPassword = confirmPassword;
				}
				$loading.finish('sample-1');
			});
			responsePromise.error(function() {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 5000);
				$scope.entrepreneurData.password = password;
				$scope.entrepreneurData.confirmPassword = confirmPassword;
				$loading.finish('sample-1');
			});
		}
	};
	
	$scope.searchByEntrepreneurNameAccNoOldAccNo = function(){

		$loading.start('sample-1');
		if($('#searchBox').val().trim()!="" && $('#searchBox').val().trim().length >=2){
			var response = $http.get('fetchEntrepreneursByNameOrAccNoOldAccNo', {params: {'searchBoxVal': $('#searchBox').val().trim()}});
			response.success(function(data, status, headers, config) {
				$scope.entrepreneurs = data;
				
				$scope.entrepreneurFormShow = false;
				$scope.entrepreneurData = "";
				$loading.finish('sample-1');
			});
		}
		else{
			$loading.finish('sample-1');
		}
	};
	
	$scope.selectEntrepreneur = function(id) {	
		
		$scope.entrepreneurData = $scope.entrepreneurs.find(x => x.id === id);
		
		if($scope.entrepreneurData.parentAccountId){
			$scope.entrepreneurData.parentAccountId = $scope.entrepreneurData.parentAccountId+"";
		}
		$scope.entrepreneurData.districtId= $scope.entrepreneurData.districtId+"";

		$scope.districtIdFA = $scope.entrepreneurData.factoryAddress.districtId;
		$scope.loadDistrictsByStateFA($scope.entrepreneurData.factoryAddress.stateId);
		$scope.entrepreneurData.factoryAddress.districtId = $scope.districtIdFA;
		
		$scope.districtIdRA = $scope.entrepreneurData.registeredAddress.districtId;
		$scope.loadDistrictsByStateRA($scope.entrepreneurData.registeredAddress.stateId);
		$scope.entrepreneurData.registeredAddress.districtId = $scope.districtIdRA;

		$scope.entrepreneurData.factoryAddress.stateId = $scope.entrepreneurData.factoryAddress.stateId+"";
		$scope.entrepreneurData.factoryAddress.districtId = $scope.entrepreneurData.factoryAddress.districtId+"";
		$scope.entrepreneurData.registeredAddress.stateId = $scope.entrepreneurData.registeredAddress.stateId+"";
		$scope.entrepreneurData.registeredAddress.districtId = $scope.entrepreneurData.registeredAddress.districtId+"";

		$scope.entrepreneurs="";
		
		$scope.entrepreneurFormShow = true;
	}
	
	$scope.existingEntrepreneurSignup = function(form) {

		if (!form.$valid) 
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			
			var password = $scope.entrepreneurData.password;
			var confirmPassword = $scope.entrepreneurData.confirmPassword;
			$scope.entrepreneurData.password = hash($scope.entrepreneurData.password);
			$scope.entrepreneurData.confirmPassword = hash($scope.entrepreneurData.confirmPassword);
			
			var responsePromise = $http.post('existingEntrepreneurSignup', $scope.entrepreneurData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					//$("#entrepreneur-success-popup").modal("show");
					$window.location.href = 'login?register';
				}
				if($rootScope.responseObject.errorMessage != null) {
					alert($rootScope.responseObject.errorMessage);
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$scope.entrepreneurData.password = password;
					$scope.entrepreneurData.confirmPassword = confirmPassword;
				}
				$loading.finish('sample-1');
			});
			responsePromise.error(function() {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 5000);
				$scope.entrepreneurData.password = password;
				$scope.entrepreneurData.confirmPassword = confirmPassword;
				$loading.finish('sample-1');
			});
		}
	};
	
$scope.loadWorkForm = function(){
		
		$http.get('fetchLoggedInUser').then(function(response){
			
			var user = response.data;
			$scope.workData = {};
			$scope.workData.districtName = user.districtName; 
			$scope.loadBlocksByDistrict($scope.workData.districtName);
			$scope.loadLCsByDistrict($scope.workData.districtName);
			$scope.loadPCsByDistrict($scope.workData.districtName);
			$scope.loadULB($scope.workData.districtName);
		});
	}

$scope.loadMLAs = function(lcId) {
	 if(lcId && lcId!=null && lcId!='null'){
		$loading.start('sample-1');
		var response = $http.get('fetchMLAByLCId/'+ lcId);
		response.success(function(data, status, headers, config) {
			$scope.mlas = data;
			$loading.finish('sample-1');
		});
	 }
};
	
$scope.loadULB = function(districtName) {
/*	alert(hii);*/
	$loading.start('sample-1');
	var response = $http.get('fetchULBByDistrictName/'+districtName);
	response.success(function(data, status, headers, config) {
		$scope.ulbs = data;
		$loading.finish('sample-1');
	});
};

$scope.loadULBById = function(districtId) {
	/*alert("hii");*/
	/*alert(districtId);*/
	$loading.start('sample-1');
	var response = $http.get('fetchULBByDistrictId/'+districtId);
	response.success(function(data, status, headers, config) {
		$scope.ulbs = data;
		$loading.finish('sample-1');
	});
};
	
$scope.loadWard = function(ulb) {
	
	if(ulb && ulb!=null && ulb!='null'){
		$loading.start('sample-1');
		var response = $http.get('fetchWardByULBCode/'+ulb);
		response.success(function(data, status, headers, config) {
			$scope.wards = data;
			$loading.finish('sample-1');
		});
	}
};


$scope.loadDistricts = function() {
	
	$loading.start('sample-1');
	var response = $http.get('fetchDistricts');
	response.success(function(data, status, headers, config) {
		$scope.districts = data;
		$loading.finish('sample-1');
	});
};

$scope.loadPCsByDistrictId = function(districtId) {
	
	$loading.start('sample-1');
	var response = $http.get('fetchPCsByDistrictId/'+districtId);
	response.success(function(data, status, headers, config) {
		$scope.pcs = data;
		$loading.finish('sample-1');
	});
};

$scope.loadLCsByDistrict = function(districtId) {
	
	$loading.start('sample-1');
	var response = $http.get('fetchLCsByDistrictId/'+districtId);
	response.success(function(data, status, headers, config) {
		$scope.lcs = data;
		$loading.finish('sample-1');
	});
};

$scope.loadBlocksByDistrictId = function(districtId) {
	
	$loading.start('sample-1');
	var response = $http.get('fetchBlocksByDistrict/'+districtId);
	response.success(function(data, status, headers, config) {
		$scope.blocks = data;
		$loading.finish('sample-1');
	});
};

$scope.loadGramPanchayatByBlockId = function(blockId) {
	if(blockId && blockId!=null && blockId!='null'){
		$loading.start('sample-1');
	
		var response = $http.get('fetchGramPanchayatByBlockId/'+blockId);
		response.success(function(data, status, headers, config) {
			$scope.gramPanchayats = data;
			$loading.finish('sample-1');
		});
	}
};

$scope.loadVillageByGPId = function(gpId) {
	if(gpId && gpId!=null && gpId!='null'){
		$loading.start('sample-1');
	
		var response = $http.get('fetchVillageByGramPanchayatId/'+gpId);
		response.success(function(data, status, headers, config) {
			$scope.villages = data;
			$loading.finish('sample-1');
		});
	}
};

$scope.citizenRequest = function(isValid,imageCitizen) {

	if (!isValid || ($scope.fileExtentionErrorForImageCitizen) || ($scope.maxSizeErrorForImageCitizen)) {
		return false;
	}

	if (confirm("कृपया अपना डाटा सुरक्षित (save) करने के लिए OK बटन दबाए ।")) {
		$loading.start('sample-1');
		var responsePromise = $http.post('addCitizenRequest', $scope.citizenData);

		responsePromise.success(function(data, status, headers, config) {

			$scope.responseObject = data;

			if($scope.responseObject.successMessage != null) {
				if(imageCitizen){
					$scope.uploadCitizenReqDocument(data.id, imageCitizen);
				}

				$scope.responseObject.successMessage="जनकार्य हेतु आवेदन फ़ॉर्म सफलता पूर्वक सेव हुआ!";
				$timeout(function() {
					$scope.responseObject.successMessage = null;
				}, 10000);
	/*			$scope.citizenData={};
				$scope.imageCitizen={};
				$("#imageCitizen").val(null);
				$scope.citizenForm.$setPristine();
//				alert("Candidate Request Form Success Saved!");
//				$window.location.href = '/dms_owms/citizenRequest';
*/	
				alert("जनकार्य हेतु आवेदन फ़ॉर्म सफलता पूर्वक सुरक्षित (save) हुआ!");
				$window.location.href = 'login';	
			}
			if($scope.responseObject.errorMessage != null) {
				/*alert("Candidate Request Form Not Saved ! Have Some Error!");
				 $window.location.href = '/dms_owms/citizenRequest';*/

				$scope.responseObject.errorMessage="Candidate Request Form Not Saved ! Have Some Error!";
				$timeout(function() {
					$scope.responseObject.errorMessage = null;
				}, 10000);
				$scope.citizenForm.$setPristine();
				$scope.citizenData={};

				$("#imageCitizen").val(null);
			}
			$loading.finish('sample-1');
		});
	}
};

$scope.uploadCitizenReqDocument = function(id, imageCitizen) { //line 714
	$loading.start('sample-1');
	var fd = new FormData();
	if(imageCitizen) {
		fd.append('imageCitizen', imageCitizen);
	}

	if(id){
		fd.append('id', id);  
	}
	var responsePromise = $http.post('addCitizenDoc', fd, {
		transformRequest: angular.identity, 
		headers: {
			'Content-Type': undefined
		} 

	});
	responsePromise.success(function(data, status, headers, config) {

		$rootScope.responseObject = data;

		if($rootScope.responseObject.successMessage != null) {
			$timeout(function() {
				$rootScope.responseObject.successMessage = null;
			}, 5000);
			/*$window.location.href = '#'+page;*/
		}
		if($rootScope.responseObject.errorMessage != null) {
			$timeout(function() {
				$rootScope.responseObject.errorMessage = null;
			}, 5000);
		}
		$loading.finish('sample-1');
	});
	responsePromise.error(function() {
		$rootScope.responseObject = {};
		$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
		$timeout(function() {
			$rootScope.responseObject.errorMessage = null;
		}, 5000);
		$loading.finish('sample-1');
	});

};

/*$scope.loadDistricts = function() {
	
	$loading.start('sample-1');
	var response = $http.get('fetchDistricts');
	response.success(function(data, status, headers, config) {
		$scope.districts = data;
		$loading.finish('sample-1');
	});
};*/
});