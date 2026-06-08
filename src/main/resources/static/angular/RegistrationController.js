var dms = angular.module('dms');

dms.controller('RegistrationController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
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
	
	$scope.loadDistricts = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDistricts');
		response.success(function(data, status, headers, config) {
			$scope.districts = data;
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
	
	$scope.loadMLAsNumber = function(lcId) {
		 if(lcId && lcId!=null && lcId!='null'){
			$loading.start('sample-1');
			var response = $http.get('fetchMLAByLCId/'+ lcId);
			response.success(function(data, status, headers, config) {
				$scope.mlasNumber = data;
				var mobileNo = $scope.mlasNumber[0].mobileNo;
				/*$scope.registrationData.contactNo={};*/
				$scope.regnData={};
				//if(contactNo!=null){
				$scope.regData.mobileNo=mobileNo+'';
				//}
				/*else{
					$scope.registrationData.contactNo='';
					
				}*/
				
				$loading.finish('sample-1');
			});
		 }
	};
	$scope.showVerificationMessage = function() {
		$scope.showNotForEmail='yes';
		return ($scope.emailIdError==false) && ($scope.emailIdValidError==false) && ($scope.emailIdDuplicateError==false);
	}
	
	$scope.checkEmailIdExists = function() {
		var sEmail = $scope.regData.emailId;
		if (sEmail != null && $.trim(sEmail) != "") {
			var response = $http
					.get('checkEmailIdRegistered?emailId='
							+ $scope.regData.emailId);
			response.success(function(data, status, headers,
					config) {
				var id = data.id;
				if (id == 0) {
					$scope.emailIdDuplicateError = false;
				} else {
					$scope.emailIdDuplicateError = true;
				}
			});
		}
	}
	
	$scope.validateEmail = function() {
		var sEmail = $scope.regData.emailId;
		if (sEmail != null && $.trim(sEmail) != "") {
			var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
			if (!filter.test(sEmail)) {
				$scope.emailIdValidError = true;
			} else {
				$scope.emailIdValidError = false;
			}
			$scope.emailIdError = false;
		} else {
			$scope.emailIdValidError = false;
			$scope.emailIdError = true;
		}
	}
	
	$scope.generateOTPForEmailId = function() {

		if ($scope.regData.emailId == null
				|| $.trim($scope.regData.emailId) == "") {
			$scope.emailIdError = true;
		}
		
		$scope.showNotForEmail='yes';

		if ($scope.emailIdError || $scope.emailIdValidError
				|| $scope.emailIdDuplicateError
				|| $scope.aadhaarIdValidError) {
			return false;
		} else {
			if (confirm("Are you sure you want to verify the Email details ?")) {
				$loading.start('sample-1');
				var responseObj = $http.post(
						'generateotpforemailid',
						$scope.regData.emailId);
				responseObj.success(function(data, status,
						headers, config) {
					$scope.showEmailOtpDetails = 'yes';
					$loading.finish('sample-1');
				});
			}
		}
	};
	
	$scope.submitOTPForEmailId = function() {
		var otp = $scope.emailIdOtp;
		var emailId = $scope.regData.emailId;

		if (otp == null || $.trim(otp) == "") {
			$scope.emailIdOtpError = true;
			return false;
		} else {
			var fd = new FormData();

			fd.append('otp', otp);
			fd.append('aadhaarNumber', emailId);

			$loading.start('sample-1');
			var aadhaarDetailsResponse = $http.post(
					'authenticateotpforemailid', fd, {
						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					});

			aadhaarDetailsResponse.success(function(emailData,
					status, headers, config) {
				if (emailData.id == 1) {
					$scope.emailIdVerified = 'yes';
					$scope.showEmailOtpDetails = 'no';
					$scope.showNotForEmail = 'no';
					alert("Your Email Id is verified.");
				} else {
					alert("Invalid Email Id OTP");
					$scope.emailIdOtp='';
				}
				$loading.finish('sample-1');
			});
			aadhaarDetailsResponse
					.error(function(data, status, headers,
							config) {
						alert("Error while Validating Email Id OTP. Please try after Some time.");
						$loading.finish('sample-1');
					});
		}
	};
	
	
	$scope.addRegistration = function(isValid) {

		if (!isValid) 
			return false;

		if ($scope.emailIdVerified == 'no'
			|| $scope.mobileNoVerified == 'no') {
			if ($scope.emailIdVerified == 'no') {
				alert("Please Verify Email Details.");
			}
			if ($scope.mobileNoVerified == 'no') {
				alert("Please Verify Mobile Details.");
			}
			return false;
		}


		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			
			var password = $scope.regData.password;
			var confirmPassword = $scope.regData.confirmPassword;
			$scope.regData.password = hash($scope.regData.password);
			$scope.regData.confirmPassword = hash($scope.regData.confirmPassword);
			
			var responsePromise = $http.post('addRegistration', $scope.regData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 10000);
					$window.location.href = 'login?register';
				}
				if($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 10000);
					$scope.regData.password = password;
					$scope.regData.confirmPassword = confirmPassword;
					//$window.location.href = '/res_owms/signup';
				}
				$loading.finish('sample-1');
			});
			responsePromise.error(function() {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 10000);
				$loading.finish('sample-1');
			});
		}
	};
	
	$scope.showVerificationMessageMobile = function() {
		return ($scope.mobileNoError==false) && ($scope.mobileNoValidError==false);
	};
	
	$scope.checkMobileNoValid = function() {
		var mobileNo = $scope.regData.mobileNo;
		if (null != mobileNo && $.trim(mobileNo) != "") {
			$scope.mobileNoError = false;
			if (mobileNo.length < 10) {
				$scope.mobileNoValidError = true;
			} else {
				$scope.mobileNoValidError = false;
			}
		} else {
			$scope.mobileNoValidError = false;
			$scope.mobileNoError = true;
		}
	}
	
	$scope.generateOTPForMobileNo = function() {
	/*	alert($scope.regData.mobileNo);
		alert($scope.regData.firstName);
		alert()*/
		
		if(($scope.regData.firstName==null || $scope.regData.firstName=='' || $scope.regData.firstName==undefined)   
				||  ($scope.regData.lastName==null || $scope.regData.lastName=='' || $scope.regData.lastName==undefined)){
			alert("Please Enter First Name And Last Name!");
			return false;
		}
		$scope.showNotification='yes';

		var isValid = true;
		if ($scope.regData.mobileNo == null
				|| $.trim($scope.regData.mobileNo) == "") {
			$scope.mobileNoError = true;
			isValid = false;
		}
		if (!isValid || $scope.mobileNoValidError) {
			return false;
		} else {
			if ("Are you sure you want to verify the Mobile No. details ?") {
				$loading.start('sample-1');
				var responseObj = $http.post(
						'generateotpformobileno',
						$scope.regData);
				responseObj.success(function(data, status,
						headers, config) {
					$scope.showMobileOtpDetails = 'yes';
					$loading.finish('sample-1');
				});
			}
		}
	};
	
	$scope.submitOTPForMobileNo = function() {
		var otp = $scope.mobileNoOtp;
		var mobileNo = $scope.regData.mobileNo;

		if (otp == null || $.trim(otp) == "") {
			$scope.mobileNoOtpError = true;
			return false;
		} else {
			var fd = new FormData();

			fd.append('otp', otp);
			fd.append('aadhaarNumber', mobileNo);

			$loading.start('sample-1');
			var aadhaarDetailsResponse = $http.post(
					'authenticateotpformobileno', fd, {
						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					});

			aadhaarDetailsResponse.success(function(mobileData,
					status, headers, config) {
				if (mobileData.id == 1) {
					$scope.mobileNoVerified = 'yes';
					$scope.showMobileOtpDetails = 'no';
					$scope.showNotification = 'no';
					alert("Your Request has been Authorized By MLA Mobile.");
				} else {
					alert("Invalid Mobile No. OTP");
					$scope.mobileNoOtp='';
				}
				$loading.finish('sample-1');
			});
			aadhaarDetailsResponse
			.error(function(data, status, headers,
					config) {
				alert("Error while Validating Mobile No. OTP. Please try after Some time.");
				$loading.finish('sample-1');
			});
		}
	};
});