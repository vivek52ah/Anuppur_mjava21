var dms = angular.module('dms');

/*app.service('dmfService', function() {
	this.myFunc = function (x) {
		return x.toString(16);
	}
});*/

dms.directive('fileModel', ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);

			var modelSetter = model.assign;
			var maxSizeUpload = 5000000;//in bytes (here 2 MB)
			//	var maxSizeErrorDoc2 = 5000000; // 50 MB
			var allowedExtensions = ['pdf', 'PDF'];
			var allowedExtensions2 = ['png', 'PNG', 'jpg', 'JPG', 'jpeg', 'JPEG'];
			var allowedExtensionZip = ['zip', 'ZIP', 'rar', 'RAR'];
			var allowedExtensions2attach = ['png', 'PNG', 'jpg', 'JPG', 'jpeg', 'JPEG', 'pdf', 'PDF'];
			element.bind('change', function() {
				//            	scope.noFileError = false;
				//            	scope.maxSizeError = false;
				/*	scope.fileExtentionErrorAs = false;    */
				var fileExtension = element[0].files[0].name.substring(element[0].files[0].name.lastIndexOf('.') + 1);

				switch (attrs.fileModel) {

					case "ldPdfFile":
						scope.fileExtentionErrorLd = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLd == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "AdPdfFile":
						scope.fileExtentionErrorAd = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorAd == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "ldTdfFile":
						scope.fileExtentionErrorLT = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLT == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;

					case "WProdfFile":
						scope.fileExtentionErrorLW = (allowedExtensions2.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLW == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "CCPdfFile":
						scope.fileExtentionErrorLC = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLC == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "ldRevPdfFile":
						scope.fileExtentionErrorRevLd = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorRevLd == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "AdRevPdfFile":
						scope.fileExtentionErrorRevAd = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorRevAd == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "ldTULFile":
						scope.fileExtentionErrorLT = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLT == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "ldUAFile":
						scope.fileExtentionErrorLT = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLT == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;
					case "dmAttachment":
						scope.fileExtentionErrorLd = (allowedExtensions2attach.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLd == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;

					/*	case "dTTTFile":
						scope.fileExtentionErrorLT = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorLT == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;*/

					case "dTTTFile":
						scope.fileExtentionErrorDW = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorDW == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;

					case "dmattachment":
						scope.fileExtentionErrorDM = (allowedExtensions.indexOf(fileExtension) < 0);
						if (scope.fileExtentionErrorDM == false) {
							scope.$apply(function() { modelSetter(scope, element[0].files[0]); });
						}
						break;


					/*case "CCPdfFile":
										  scope.maxSizeErrorDoc2 = (fileSize > maxSizeUpload);
										  scope.fileExtentionErrorDoc2 = (allowedExtensionZip.indexOf(fileExtension) < 0);							
										if (scope.maxSizeErrorDoc2 || scope.fileExtentionErrorDoc2 == false) {
											scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
										}							
									break;*/





				}

			});
		}
	};
}]);









dms.filter('indianCurrency', function() {
	return function(value) {
		var x = parseFloat(value).toFixed(2);
		x = x.toString();
		var afterPoint = '';
		if (x.indexOf('.') > 0)
			afterPoint = x.substring(x.indexOf('.'), x.length);
		x = Math.floor(x);
		x = x.toString();

		//for negative numbers
		var isNegative = false;
		if (x.includes("-")) {
			x = x.split("-")[1];
			isNegative = true;
		}

		var lastThree = x.substring(x.length - 3);
		var otherNumbers = x.substring(0, x.length - 3);
		if (otherNumbers != '')
			lastThree = ',' + lastThree;
		var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
		if (isNegative) {
			res = "-" + res;
		}
		return '₹' + res;
		//return res;
		// return x;
	}
});

dms.filter('inWords', function() {
	return function(num) {
		var a = ['', 'One ', 'Two ', 'Three ', 'Four ', 'Five ', 'Six ', 'Seven ', 'Eight ', 'Nine ', 'Ten ', 'Eleven ', 'Twelve ', 'Thirteen ', 'Fourteen ', 'Fifteen ', 'Sixteen ', 'Seventeen ', 'Eighteen ', 'Nineteen '];
		var b = ['', '', 'Twenty', 'Thirty', 'Forty', 'Fifty', 'Sixty', 'Seventy', 'Eighty', 'Ninety'];

		if (num) {
			//debugger;
			var str = '';
			if ((num = num.toString()).length > 10) {
				return 'overflow';
			}
			if ((num = num.toString()).length == 10) {

				var x = num.substr(0, 1);
				str = a[Number(x)] + 'Hundred Crore ';

				num = num.substr(1);
			}
			n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
			if (!n) return;
			str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'Crore ' : '';
			str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'Lakh ' : '';
			str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'Thousand ' : '';
			str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'Hundred ' : '';
			str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) : '';

			if ((str.split("Crore").length - 1) > 1) {
				str = str.replace('Crore ', '');
			}
			return str + 'only ';
		}
	}
});

dms.filter('decimal', function() {
	return function(value) {
		var x = parseFloat(value).toFixed(2);
		return x;
	}
});

dms.service('commonService', function() {
	this.currentDate = function() {
		var d = new Date();

		var datestring = ("0" + d.getDate()).slice(-2) + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();

		return datestring;
	}
});

dms.factory('Excel', function($window, $document) {
	var uri = 'data:application/vnd.ms-excel;base64,',
		template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
		base64 = function(s) { return $window.btoa(unescape(encodeURIComponent(s))); },
		format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) };
	return {
		tableToExcel: function(tableId, fileName, worksheetName) {

			/*Clone the table that is to be exported*/
			var table = $(tableId).clone();

			var hyperLinks = table.find('a');

			for (i = 0; i < hyperLinks.length; i++) {

				//hyperLinks[i] =  $(hyperLinks[i]).text();
				var sp1 = document.createElement("span");

				// give it an id attribute called 'newSpan'
				sp1.setAttribute("id", "newSpan");

				// create some content for the new element.
				var sp1_content = document.createTextNode($(hyperLinks[i]).text());
				// apply that content to the new element
				sp1.appendChild(sp1_content);

				// build a reference to the existing node to be replaced
				var sp2 = hyperLinks[i];
				var parentDiv = sp2.parentNode;

				// replace existing node sp2 with the new span element sp1
				parentDiv.replaceChild(sp1, sp2);
			}
			/*var ctx={worksheet:worksheetName,table:table.html()};
		    
			var link = $document[0].createElement('a');
			link.download = fileName;
			link.href = uri + base64(format(template, ctx));
			link.click();*/

			var ctx = { download: fileName, worksheet: worksheetName, table: table.html() },
				//download=fileName,
				href = uri + base64(format(template, ctx));
			return href;
		}
	};
})

dms.controller('CommonController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout, $sce, commonService, Excel, $parse, $route, $q, $location) {
	if ($location.path() === '/changepassword' && $window.loggedInRoleName === 'ROLE_SYSTEM_ADMIN') {
		$location.path('/manageOngoingWorks');
		return;
	}
	
	// ✅ ADD AT TOP
    $scope.workData = {};
    $scope.workDataR = {};
    $scope.mode = 'view';
    
    if ($routeParams.id) {
        var hash = window.location.hash;
        $scope.mode = hash.includes('editWork') ? 'edit' : 'view';
    }

    // ✅ This is called by data-ng-init="loadWorkDetails(mode)"
    $scope.workDataTender = $scope.workDataTender || {};
    $scope.workDataContractor = $scope.workDataContractor || {};
    $scope.workDataProgress = $scope.workDataProgress || {};

    $scope.loadWorkDetails = function(mode) {
        if (!$routeParams.id) return;
        
        $loading.start('sample-1');
        $http.get('fetchWorkDetails/' + $routeParams.id)
            .then(function(response) {
                $scope.workData = response.data;
                if (typeof $scope.loadTenderDetails === 'function') {
                    $scope.loadTenderDetails();
                }
                $loading.finish('sample-1');
            }, function(error) {
                console.error('Error loading work details', error);
                $loading.finish('sample-1');
            });
    };

    $scope.tabChange = function(tabName) {
        if (tabName === 'step2') {
            // Department remarks load via ng-init when workData is set
        } else if (tabName === 'step3') {
            $scope.loadTenderDetails();
        } else if (tabName === 'step4') {
            $scope.loadContractorDetails();
        } else if (tabName === 'step5') {
            $scope.loadWorkProgress();
        } else if (tabName === 'step6') {
            $scope.loadCCDetails();
        }
    };

    $scope.goToNextWizardTab = function() {
        var $activeTab = $('.wizard .nav-tabs .nav-item .active');
        var $nextTabLi = $activeTab.parent("li").next();
        while ($nextTabLi.length > 0) {
            var $nextTab = $nextTabLi.find('a[data-toggle="tab"]');
            if ($nextTab.length > 0 && $nextTab.is(':visible') && !$nextTab.parent().is(':hidden')) {
                $nextTab.removeClass("disabled");
                $nextTab.tab('show');
                break;
            }
            $nextTabLi = $nextTabLi.next();
        }
    };

	$scope.moveToNextTenderStatusField = function(savedStatusId) {
		var nextStatusByCurrentStatus = {
			'3': '4',
			'4': '7',
			'7': '8',
			'14': '4'
		};
		var nextStatusId = nextStatusByCurrentStatus[String(savedStatusId)];
		if (nextStatusId) {
			$scope.workDataTender.tenderUpdated = String(savedStatusId);
			$scope.workDataTender.workStatusId = nextStatusId;
			$scope.workTenderForm.$submitted = false;
		}
	};

	$scope.canEditContractorDetails = function() {
		var savedTenderStatus = String(($scope.workDataTender && $scope.workDataTender.tenderUpdated) || '');
		return ['8', '9', '10', '11', '12', '13'].indexOf(savedTenderStatus) >= 0;
	};

	$scope.isTenderWork = function() {
		return $scope.workData && ($scope.workData.isTenders == 1 || $scope.workData.isTenders === true || $scope.workData.isTenders === '1');
	};

	$scope.isWorkOrderIssuedStatus = function() {
		return $scope.workDataTender && String($scope.workDataTender.workStatusId) === '8';
	};

	$scope.isWorkOrderIssuedRequired = function() {
		return $scope.isTenderWork() && $scope.isWorkOrderIssuedStatus();
	};
    
	$scope.started = false;

// At the top of CommonController, after $routeParams injection:
 // ✅ ADD THIS BLOCK AT THE TOP
    // if ($routeParams.id) {
    //     var currentHash = window.location.hash;
    //     if (currentHash.includes('editWork')) {
    //         $scope.mode = 'edit';
    //     } else if (currentHash.includes('viewWork')) {
    //         $scope.mode = 'view';
    //     }
    //     $scope.workId = $routeParams.id;
    // }

	$scope.filesDrawing = [];
	$scope.drawingFileStatus = [];

// assign officer

$scope.showAssignOfficer = false;

	$scope.addNewFile = function() {
		$scope.filesDrawing.push({ fileObj: null });
		$scope.drawingFileStatus.push({ drawingFileStatus: null });
	};

	$scope.deleteFile = function(index) {
		$scope.filesDrawing.splice(index, 1);
	};


	$scope.check = function() {
		//$scope.division= division;
		//$scope.role= role;
		//console.log('devision....'+$scope.division);
		//alert('Division..'+$scope.division+"")
		//$scope.loadDivisionsByDivisionId($scope.division);
		//alert('devision...'+$scope.division);
		//false

		var currentTime = new Date()

		// returns the month (from 0 to 11)
		var month = currentTime.getMonth() + 1

		// returns the day of the month (from 1 to 31)
		var day = currentTime.getDate()

		// returns the year (four digits)
		var year = currentTime.getFullYear()

		// write output MM/dd/yyyy
		//document.write(month + "/" + day + "/" + year)
		console.log('Current date...' + month + "/" + day + "/" + year);
	}


	/*
	
	 $scope.tabChange = function (tabName) {
		   $scope.urlTab = tabName;
		  // alert('Noted...'+$scope.urlTab)
		  // false;
		  
		  console.log('Tabs...'+tabName);
		  
		if (tabName=='step2'){
			  
			   $scope.loadTSASDetails();
		  }else if (tabName=='step3'){
			  
			 $scope.loadTenderDetails();
		  }
		  else if (tabName=='step4'){
			  
				$scope.loadContractorDetails();
		  }
		   else if (tabName=='step5'){
			  
			   $scope.loadWorkProgress();
		  } else if (tabName=='step6'){
			  
			   $scope.loadWorkCompleted();
		  }
		  
		  
		   
		   }
	
	
	
	
	
	
	
	
	$scope.Date  = function(){

		var d = new Date();

		var datestring = ("0" + d.getDate()).slice(-2)  + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();

		return datestring;
	
	
	
	
	 $scope.setTab = function (tabName) {
		   $scope.urlTab = tabName;
		  
		 if($scope.urlTab==2){
			$scope.loadTSASDetails(1);
		    
		 }else if($scope.urlTab==3){
		   $scope.loadTenderDetails(1);
		   
		 }
		 else if($scope.urlTab==4){
		 $scope.loadContractorDetails(1);
		 
		 }
		 else if($scope.urlTab==5){
		   $scope.loadWorkProgress(1);
		   
		 }
		  else if($scope.urlTab==6){
		 $scope.loadWorkCompleted(1);
		 
		 }
	   };
	
	
	*/

	$scope.workData = {};


	$scope.reloadJqueryDatatable = function() {
		$loading.start('sample-1');
		//reDraw();
		window.reloadJqueryDatatables();
		$loading.finish('sample-1');

	};

	$scope.reloadJqueryDatatablestatus = function() {

		$loading.start('sample-1');
		reDrawstatus();
		$loading.finish('sample-1');

	};
	$scope.reloadJqueryDatatable2 = function() {
		$loading.start('sample-1');
		reDraw2();
		//window.reloadJqueryDatatable2();
		$loading.finish('sample-1');

	};

	$scope.reloadedJqueryDatatableex = function() {
		$loading.start('sample-1');
		exDraw();
		$loading.finish('sample-1');
		$scope.$apply();
	};



	$scope.doTheBack = function() {
		var path = $location.path() || '';
		if (path.indexOf('/editWork') === 0 || path.indexOf('/viewWork') === 0 || path.indexOf('/addNewWork') === 0) {
			$location.path('/manageOngoingWorks').search({ _r: Date.now() });
			return;
		}
		if (window.history.length > 1) {
			window.history.back();
		} else {
			$location.path('/manageOngoingWorks');
		}
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

	$scope.openModal = function(workid, userid) {
		console.log('openModal called with workid:', workid, 'userid:', userid);
		
		// Set global variables first
		window.currentWorkId = workid;
		window.currentUser = userid;
		
		// Try to call the global openModal function defined in editTender.html
		if (typeof window.openModal === 'function') {
			console.log('Calling global window.openModal function');
			try {
				window.openModal(workid, userid);
				return;
			} catch (error) {
				console.error('Error calling global openModal:', error);
			}
		}
		
		// Fallback: manually open the modal
		console.log('Using fallback modal opening method');
		
		// Initialize the DataTable if not already done
		if (typeof t2 !== 'undefined' && t2 !== null) {
			console.log('Redrawing DataTable t2');
			t2.draw();
		} else if (typeof fetchUserList === 'function') {
			console.log('Calling fetchUserList');
			fetchUserList();
		}
		
		// Show the modal with a small delay to ensure DOM is ready
		$timeout(function() {
			try {
				var modalElement = document.getElementById('exampleModal2');
				console.log('Modal element found:', modalElement);
				
				if (modalElement) {
					// Try jQuery Bootstrap way first (most common)
					if (typeof $ !== 'undefined' && $.fn.modal) {
						console.log('Opening modal with jQuery');
						$('#exampleModal2').modal('show');
					}
					// Try Bootstrap 5 way
					else if (typeof bootstrap !== 'undefined' && bootstrap.Modal) {
						console.log('Opening modal with Bootstrap 5');
						var modal = new bootstrap.Modal(modalElement);
						modal.show();
					}
					// Fallback: show using CSS
					else {
						console.log('Opening modal with CSS fallback');
						modalElement.style.display = 'block';
						modalElement.classList.add('show');
						var backdrop = document.createElement('div');
						backdrop.className = 'modal-backdrop fade show';
						document.body.appendChild(backdrop);
					}
				} else {
					console.error('Modal element #exampleModal2 not found in DOM');
					alert('Modal element not found. Please make sure you are on the correct tab.');
				}
			} catch (error) {
				console.error('Error opening modal:', error);
				alert('Error opening modal: ' + error.message);
			}
		}, 100);
	};

	$scope.startOrStopSpinner = function(isStart) {

		if (isStart) {
			$loading.start('sample-1');
		} else {
			$loading.finish('sample-1');
		}
	};

//	$scope.changePasswordFunction = function(isValid) {

//		if (!isValid)
	//		return false;
//
//		$loading.start('sample-1');

//		$scope.changePasswordData.currentPassword = hash($scope.changePasswordData.currentPassword);
//		$scope.changePasswordData.password = hash($scope.changePasswordData.password);
//		$scope.changePasswordData.confirmPassword = hash($scope.changePasswordData.confirmPassword);

//		var responsePromise = $http.post('/dochangepassword', $scope.changePasswordData);

//		responsePromise.success(function(data, status, headers, config) {

//			$rootScope.responseObject = data;

//			if ($rootScope.responseObject.successMessage != null) {
//				$timeout(function() {
//					$rootScope.responseObject.successMessage = null;
//				}, 5000);
//				$window.location.href = '#changepassword';
//			}
//			$loading.finish('sample-1');
//		});
//		responsePromise.error(function() {
//			$rootScope.responseObject = {};
//			$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
//			$timeout(function() {
//				$rootScope.responseObject.errorMessage = null;
//			}, 5000);
//			$loading.finish('sample-1');
//		});
//	};

$scope.changePasswordFunction = function(isValid) {

    if (!isValid)
        return false;

    $loading.start('sample-1');

    var requestData = angular.copy($scope.changePasswordData);

    // Send plain text passwords - backend will handle BCrypt encoding
    // requestData.currentPassword = hash(requestData.currentPassword);
    // requestData.password = hash(requestData.password);
    // requestData.confirmPassword = hash(requestData.confirmPassword);

    $http.post('dochangepassword', requestData)

        .success(function(data) {

            $rootScope.responseObject = data;

            if ($rootScope.responseObject.successMessage != null) {

                $timeout(function() {
                    $rootScope.responseObject.successMessage = null;
                    // Redirect to login page after password change success
                    $window.location.href = getLoginUrl();
                }, 2000);
            }

            $loading.finish('sample-1');
        })

        .error(function() {

            $rootScope.responseObject = {};
            $rootScope.responseObject.errorMessage =
                "Some error occured while saving the data";

            $timeout(function() {
                $rootScope.responseObject.errorMessage = null;
            }, 5000);

            $loading.finish('sample-1');
        });
};

	$scope.populateCurrentMonth = function() {
		var d = new Date();

		$scope.fromDate = "01" + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();
		$scope.toDate = ("0" + d.getDate()).slice(-2) + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();
	};

	$scope.populateCurrentMonthWithFormat = function() {
		var d = new Date();

		$scope.fromDate = "01" + "-" + ("0" + (d.getMonth() + 1)).slice(-2) + "-" + d.getFullYear();
		$scope.toDate = ("0" + d.getDate()).slice(-2) + "-" + ("0" + (d.getMonth() + 1)).slice(-2) + "-" + d.getFullYear();
	};


	$scope.addNewWorkData = function(isValid, tsDocument, asDocument, estDocument, workOrderDocument, fileArr, mlaDocument) {
		var ccDocument = null;
		if ($scope.uploadFileData && ($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined)
			&& ($scope.uploadFileData.remarks != '' && $scope.uploadFileData.remarks != undefined)
			&& ($scope.uploadFileData.otherDocDate != '' && $scope.uploadFileData.otherDocDate != undefined) &&
			($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined) && $scope.model != undefined) {
			alert("Please Add Other Document By Clicking Add Button! ");
			return false;
		}

		if (!isValid || ($scope.maxSizeErrorAS) || ($scope.fileExtentionErrorAS) || ($scope.maxSizeErrorEst) || ($scope.fileExtentionErrorEst) || ($scope.maxSizeErrorTS) || ($scope.fileExtentionErrorTS) || ($scope.maxSizeErrorWorkOrder) || ($scope.fileExtentionErrorWorkOrder) || ($scope.maxSizeErrorOtherDoc) || ($scope.fileExtentionErrorOtherDoc) || ($scope.maxSizeErrorTS) || ($scope.fileExtentionErrorTS)) {
			/*var error = $scope.workForm.$error;
			angular.forEach(error.pattern, function(field){
				if(field.$invalid){
					var fieldName = field.$name;
					console.log(fieldName);
				}
			});*/
			return false;
		}

		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.estimatedAmt)) {

			alert("Amount released till date cannot be greater than Estimated Amount. Please check.");
			return false;
		}
		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.estimatedAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.tsAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.firstInstallAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.secondInstallmentAmt) > parseFloat($scope.workData.asAmt)) {

			alert("All Amounts should be less than or equal to the Administrative Sanction Amount. Please check.");
			return false;
		}

		if (Number.isNaN(parseFloat($scope.workData.finalInstallmentAmt))) {
			var finInstAmt = 0;

		} else {
			var finInstAmt = $scope.workData.finalInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.secondInstallmentAmt))) {
			var secInstAmt = 0;

		} else {
			var secInstAmt = $scope.workData.secondInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.firstInstallAmt))) {
			var firsInstAmt = 0;

		} else {
			var firsInstAmt = $scope.workData.firstInstallAmt;

		}

		if ((parseFloat(firsInstAmt) + parseFloat(secInstAmt) + parseFloat(finInstAmt)) > parseFloat($scope.workData.asAmt)) {

			alert("Total installment amount cannot be greater than Administrative Sanction Amount. Please check.");
			return false;
		}

		if (confirm("Are you sure you want to save the data?")) {

			document.getElementById("submit").disabled = true;

			$loading.start('sample-1');

			var responsePromise = $http.post('addNewWorkData', $scope.workData);

			responsePromise.success(function(data, status, headers, config) {
				$loading.start('sample-1');
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);

					var newWorkId = data.id;
					if (tsDocument || asDocument || workOrderDocument || estDocument || ccDocument || mlaDocument) {
						$scope.uploadWorkDocument(newWorkId, tsDocument, asDocument, estDocument, ccDocument, workOrderDocument, mlaDocument, null, null, null, null, null, null, null, null, null, 'editWork/' + newWorkId);
					}
					if (fileArr.length > 0) {
						$scope.uploadOtherDoc(newWorkId, fileArr, 'editWork/' + newWorkId);
					} else {
						$window.location.href = '#editWork/' + newWorkId;
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		}
	};

	$scope.checkWorkDetailForTabs = function(isValid) {
		$loading.start('sample-1');
		var $active = $('.wizard .nav-tabs .nav-item .active');
		var $activeli = $active.parent("li");
		$($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
		$($activeli).next().find('a[data-toggle="tab"]').click();
		$loading.finish('sample-1');
	};


	$scope.removeTagOnBackspace = function(event) {
		if (event.keyCode === 8) {
			console.log('here!');
		} else {
			return null;
		}

		console.log('here! ' + event);
	};





	$scope.exportAgencyWiseReportData = function(mode) {

		$window.open('exportAgencyWiseReportData/' + mode);




	};


	$scope.exportPPT = function(mode) {

		$window.open('generatePPT');




	};


	$scope.exportAgencyWiseReportDataLatest = function(mode) {

		$window.open('exportAgencyWiseReportDataLatest/' + mode);

	};
	$scope.approveWorkData = function() {
		// Set dmStatus to 'approved' when the user clicks the "Approve" button
		$scope.workData.dmStatus = 1;
	};
	$scope.rejectWorkData = function() {
		// Set dmStatus to 'Reject' when the user clicks the "Reject" button
		$scope.workData.dmStatus = 2;
	};
	
	

	$scope.createWorkData = function(isValid, mode, ldPdfFile, AdPdfFile, form) {
		//		alert("Call DM Login Remarks")
		//$scope.workData.dmStatus =$scope.workData.dmStatus;

		/*if (!isValid) {

			return false;
		}*/









		if (ldPdfFile) {
			$scope.noFileError = (ldPdfFile) ? false : true;
			var maxSizeUpload = 25000000;// in bytes (here 5 MB)
			//var allowedExtensions = ['pdf', 'PDF'];
			if (ldPdfFile) {
				$scope.fileSizeErrorLd = (ldPdfFile.size > maxSizeUpload) ? true : false;

			}

			if ($scope.noFileError)
				return false;
			if ($scope.fileSizeErrorLd)
				return false;

		} else {
			//$scope.noFileError = false;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorLd)
				return false;
			if ($scope.workDataTS.tsFileId) {
				alert("Please select Technical Sanction File");
				return false;
			}
		}

		if (AdPdfFile) {

			$scope.noFile2Error = (AdPdfFile) ? false : true;

			var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
			if (AdPdfFile) {
				$scope.fileSizeErrorAd = (AdPdfFile.size > maxSizeUpload2) ? true : false;
			}
			if ($scope.noFile2Error)
				return false;
			if ($scope.fileSizeErrorAd)
				return false;

		} else {
			//$scope.noFile2Error = true;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorAd)
				return false;
			if ($scope.workDataTS.asFileId) {
				alert("Please select Administration Approval File");
				return false;
			}
		}



		var fd = new FormData();

		if ($scope.workData.dmStatus) {
			fd.append('dmStatus', $scope.workData.dmStatus);
		}
		if ($scope.workData.DmRemakrs) {
			fd.append('DmRemakrs', $scope.workData.DmRemakrs);
		}
		if ($scope.workData.id) {
			fd.append('id', $scope.workData.id);
		}


		if ($scope.workData.workSubTypeId) {
			fd.append('workSubTypeId', $scope.workData.workSubTypeId);
		}



		if ($scope.workData.workName) {
			fd.append('workName', $scope.workData.workName);
		}

		if ($scope.workData.workTypeId) {
			fd.append('workTypeId', $scope.workData.workTypeId);
		}

		if ($scope.workData.financialYear) {
			fd.append('financialYear', $scope.workData.financialYear);
		}

		if ($scope.workData.isTender) {
			fd.append('isTender', $scope.workData.isTender);
		}

		if ($scope.workData.implementationAgency) {
			fd.append('implementationAgency', $scope.workData.implementationAgency);
		}

		if ($scope.workData.districtCode) {
			fd.append('districtCode', $scope.workData.districtCode);
		}

		if ($scope.workData.blockCode) {
			fd.append('blockCode', $scope.workData.blockCode);
		}

		if ($scope.workData.constituencyCode) {
			fd.append('constituencyCode', $scope.workData.constituencyCode);
		}

		if ($scope.workData.tsNo) {
			fd.append('tsNo', $scope.workData.tsNo);
		}

		if ($scope.workData.tsDate) {
			fd.append('tsDate', $scope.workData.tsDate);
		}

		if ($scope.workData.tsAmt) {
			fd.append('tsAmt', $scope.workData.tsAmt);
		}

		if (ldPdfFile) {
			fd.append('tsDocumentUpload', ldPdfFile);
		}

		if ($scope.workData.tsRemarks) {
			//		alert("$scope.workData.tsRemarks " + $scope.workData.tsRemarks)
			fd.append('tsRemarks', $scope.workData.tsRemarks);
		}

		// For Administrative Approval fields
		if ($scope.workData.asNo) {
			fd.append('asNo', $scope.workData.asNo);
		}

		if ($scope.workData.asDate) {
			fd.append('asDate', $scope.workData.asDate);
		}

		if ($scope.workData.asAmt) {
			fd.append('asAmt', $scope.workData.asAmt);
		}

		if (AdPdfFile) {
			fd.append('asDocumentUpload', AdPdfFile);
		}

		if ($scope.workData.asRemarks) {
			//	alert("$scope.workData.asRemarks" + $scope.workData.asRemarks)
			fd.append('asRemarks', $scope.workData.asRemarks);
		}

		if ($scope.workData.isTenders) {
			fd.append('isTenders', $scope.workData.isTenders);
		}
		if ($scope.workData.isTenders == false) {

			fd.append('isTenders', 0);
		}
		if ($scope.workData.isTenders == true) {

			fd.append('isTenders', 1);
		}


		if ($scope.workData.gramPanchayatCode) {
			fd.append('gramPanchayatCode', $scope.workData.gramPanchayatCode);
		}

//		if ($scope.workData.financialHeadId) {
//			fd.append('financialHeadId', $scope.workData.financialHeadId);
//		}

		if ($scope.workData.vidhanSabhaId) {
			fd.append('vidhanSabhaId', $scope.workData.vidhanSabhaId);
		}
		
		
		
		
		if ($scope.workDataRows && $scope.workDataRows.length > 0) {
	
	
			if (!$scope.workData || !$scope.workData.asAmt) {
				alert("AS Amount is missing!");
				return false;
			}

			let asAmount = parseFloat($scope.workData.asAmt);
			let totalCost = 0;

			for (let index = 0; index < $scope.workDataRows.length; index++) {

				let row = $scope.workDataRows[index];

				if (row.financialHeadId && row.cost) {

					let cost = parseFloat(row.cost);

					// 🔥 Add cost to total
					totalCost += cost;

					// 🔥 Check total cost should not exceed AS amount
					if (totalCost > asAmount) {
						alert("Total cost (" + totalCost + ") cannot be greater than AS Amount (" + asAmount + ")");
						return false;  // STOP SAVE
					}

					// Append only when valid
					fd.append('financialHeads[' + index + '].financialHeadId', row.financialHeadId);
					fd.append('financialHeads[' + index + '].cost', row.cost);
					if(row.financialAgencyId){
					fd.append('financialHeads[' + index + '].financialAgencyId', row.financialAgencyId);
				}
				}
			}
			
			 fd.append('totalCost', totalCost);

    console.log("Total Financial Heads Cost = " + totalCost);
}
		
		

	/*// Add dynamic financial head and cost rows
		if ($scope.workDataRows && $scope.workDataRows.length > 0) {
			$scope.workDataRows.forEach(function(row, index) {
				if (row.financialHeadId && row.cost) {
					fd.append('financialHeads[' + index + '].financialHeadId', row.financialHeadId);
					fd.append('financialHeads[' + index + '].cost', row.cost);
					
					alert("financialHeadId" + row.financialHeadId)
					alert("cost" + row.cost)
				}
			});
		}*/
		
		

		if (confirm("Are you sure you want to save the data?")) {
			$scope.workData.dmStatus = $scope.workData.dmStatus;
			//document.getElementById("submit").disabled=true;

			$loading.start('sample-1');



			var responsePromise = $http.post('addWorkData', fd, {

				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});
			responsePromise.success(function(data, status, headers, config) {

				$loading.start('sample-1');
				$rootScope.responseObject = data;

				$scope.workDataTS = {};
				$scope.workDataTS.workId = null;
				//$scope.workDataTender = {};
				//$scope.workDataTender.workId = null;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$scope.workDataTS = data;
					$scope.workDataTS.tsNo = $scope.workDataTS.tsNo + "";
					//$scope.loadTenderDetails();
					$scope.workDataTS.workId = $rootScope.responseObject.id;
					//if ($scope.workData.fileStatus == '1') {
					//	$scope.uploadedDrawingFiles($scope.workDataTS.workId, dTTTFile);
					//}
					if (mode == 'Add') {
						$scope.createTSASWorkData(isValid, ldPdfFile, AdPdfFile, mode);
						//$window.location.href = '#manageOngoingWorks';
					} if (mode == 'Edit') {
						$scope.createTSASWorkData(isValid, ldPdfFile, AdPdfFile, mode);
						$scope.loadTSASDetails();
						$scope.loadWorkDetails('sec');
						if ($scope.saveAndNext) {
							$scope.goToNextWizardTab();
						}
					}
					/*var $active = $('.wizard .nav-tabs .nav-item .active');
					var $activeli = $active.parent("li");
					$($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
					$($activeli).next().find('a[data-toggle="tab"]').click();
					$scope.loadWorkDetails('sec');
*/


				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		} else {
			$scope.workData.dmStatus = null;
		}


	};

	/*$scope.uploadedDrawingFiles = function(workId, dTTTFile) {
	
		var fd = new FormData();
	
		if (workId) {
			fd.append('workId', workId);
		}
	
		if ($scope.drawingStatus) {
			fd.append('drawingStatus', $scope.drawingStatus);
		}
	
		if (dTTTFile) {
			fd.append('drawingFile', dTTTFile);
		}
	
		$loading.start('sample-1');
		var responsePromise = $http.post('uploadedDrawingFiles', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});
	
		responsePromise.success(function(data, status, headers, config) {
	
		});
	
	
		responsePromise.error(function() {
			$rootScope.responseObject = {};
			$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
			$timeout(function() {
				$rootScope.responseObject.errorMessage = null;
			}, 10000);
			$loading.finish('sample-1');
		});
	
	
	};
*/

	$scope.createTSASWorkData = function(isValid, ldPdfFile, AdPdfFile, mode) {

		if (!isValid)
			return false;

		if (parseFloat($scope.workDataTS.tsAmt) > parseFloat($scope.workDataTS.asAmt)) {

			alert("TS amount cannot be greater than AS amount. Please check.");
			return false;
		}



		// Department requirement I commented on this code but it is working fine if you want validation
		/*if (ldPdfFile) {
			$scope.noFileError = (ldPdfFile) ? false : true;
			var maxSizeUpload = 25000000;// in bytes (here 5 MB)
			//var allowedExtensions = ['pdf', 'PDF'];
			if (ldPdfFile) {
				$scope.fileSizeErrorLd = (ldPdfFile.size > maxSizeUpload) ? true : false;
	
			}
	
			if ($scope.noFileError)
				return false;
			if ($scope.fileSizeErrorLd)
				return false;
	
		} else {
			//$scope.noFileError = true;
			if ($scope.fileExtentionErrorLd)
				return false;
			if ($scope.workDataTS.tsFileId) {
				alert("Please select Technical Sanction File");
				return false;
			}
		}*/


		/*if (AdPdfFile) {
			$scope.noFile2Error = (AdPdfFile) ? false : true;
			var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
			if (AdPdfFile) {
				$scope.fileSizeErrorAd = (AdPdfFile.size > maxSizeUpload2) ? true : false;
			}
			if ($scope.noFile2Error)
				return false;
			if ($scope.fileSizeErrorAd)
				return false;

		} else {
			//$scope.noFile2Error = true;
			if ($scope.fileExtentionErrorAd)
				return false;
			if (!$scope.workDataTS.asFileId) {
				alert("Please select Administration Approval File");
				return false;
			}
		}*/






		//	if (confirm("Are you sure you want to save the data?")) {

		$loading.start('sample-1');



		var fd = new FormData();



		//	if($scope.saveAsDraft == true && $scope.saveAndNext == false  ){

		//	$scope.workDataTS.workRequestStatusId = 1;
		//    }

		//if($scope.saveAsDraft == true && $scope.saveAndNext == true  ){

		//    $scope.workDataTS.workRequestStatusId =  2;
		//  }




		if ($scope.workDataTS.workId) {
			fd.append('workId', $scope.workDataTS.workId);
		}


		if ($scope.workDataTS.tsNo) {
			fd.append('tsNo', $scope.workDataTS.tsNo);
		}



		if (ldPdfFile) {
			fd.append('tsDocumentUpload', ldPdfFile);
		}

		if (AdPdfFile) {
			fd.append('asDocumentUpload', AdPdfFile);
		}


		if ($scope.workDataTS.tsDate) {
			fd.append('tsDate', $scope.workDataTS.tsDate);
		}



		if ($scope.workDataTS.tsAmt) {
			fd.append('tsAmt', $scope.workDataTS.tsAmt);
		}




		if ($scope.workDataTS.tsRemarks) {
			//			alert("$scope.workDataTS.tsRemarks " + $scope.workDataTS.tsRemarks)
			fd.append('tsRemarks', $scope.workDataTS.tsRemarks);
		}

		if ($scope.workDataTS.asNo) {
			fd.append('asNo', $scope.workDataTS.asNo);
		}

		if ($scope.workDataTS.asDate) {
			fd.append('asDate', $scope.workDataTS.asDate);
		}

		if ($scope.workDataTS.asAmt) {
			fd.append('asAmt', $scope.workDataTS.asAmt);
		}

		if ($scope.workDataTS.asRemarks) {
			//			alert("$scope.workDataTS.asRemarks " + $scope.workDataTS.asRemarks)
			fd.append('asRemarks', $scope.workDataTS.asRemarks);
		}

		$loading.start('sample-1');

		var responsePromise = $http.post('addTSASWorkData', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.success(function(data, status, headers, config) {
			$rootScope.responseObject = data;
			$scope.workDataTender = {};
			$scope.workDataTender.workId = null;
			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);
				$scope.workDataTender.workId = $rootScope.responseObject.id;
				$scope.successRespondeTS = 'success';
				if (mode == 'Add') {

					$window.location.href = '#editWork/' + $rootScope.responseObject.id;
				} if (mode == 'Edit') {
					$scope.loadTenderDetails();
					$scope.loadWorkDetails('sec');
					if ($scope.saveAndNext) {
						$scope.goToNextWizardTab();
					}
				}


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

		/*}

		else {
			$scope.saveAsDraft = false;
			$scope.submit = false;
		}*/
	};


	$scope.createRevisedCall = function(isValid, ldRevPdfFile, AdRevPdfFile, mode) {

		if ($scope.workDataRTS.revisedStatus == '0' || $scope.workDataRTS.revisedStatus == '2') {
			//if (!isValid)
			//	return false;
			$scope.createTSReviseWorkData(ldRevPdfFile, AdRevPdfFile, mode);
		}

		if ($scope.workDataRTS.revisedStatus == '1') {
			//if (!isValid)
			//return false;
			$scope.createASReviseWorkData(ldRevPdfFile, AdRevPdfFile, mode);
		}
	},


		$scope.createTSReviseWorkData = function(ldRevPdfFile, AdRevPdfFile, mode) {



			if ($scope.workDataRTS.revisedStatus == '2') {
				if (parseFloat($scope.workDataRTS.tsRevAmt) > parseFloat($scope.workDataRTS.asRevAmt)) {

					alert("TS revised amount cannot be greater than AS revised amount. Please check.");
					return false;
				}
			}

			if (!$scope.workDataRTS.tsRevNo) {
				alert("Please enter revise TS no.");
				return false;
			}

			if (!$scope.workDataRTS.tsRevDate) {
				alert("Please enter revise TS date.");
				return false;
			}

			if (!$scope.workDataRTS.tsRevAmt) {
				alert("Please enter revise TS amount.");
				return false;
			}

			if (!$scope.workDataRTS.tsRevRemarks) {
				alert("Please enter revise TS remarks.");
				return false;
			}



			if (ldRevPdfFile) {
				$scope.noFileError = (ldRevPdfFile) ? false : true;
				var maxSizeUpload = 25000000;// in bytes (here 5 MB)
				if (ldPdfFile) {
					$scope.fileSizeErrorRevLd = (ldRevPdfFile.size > maxSizeUpload) ? true : false;
				}
				if ($scope.noFileError)
					return false;
				if ($scope.fileSizeErrorRevLd)
					return false;
			} else {
				//$scope.noFileError = true;
				if ($scope.fileExtentionErrorRevLd)
					return false;
				if (!$scope.workDataRTS.tsRevFileId) {
					alert("Please select revise Technical Sanction File");
					return false;
				}
			}

			if ($scope.workDataRTS.revisedStatus == '2') {

				if (!$scope.workDataRTS.asRevNo) {
					alert("Please enter revise AS no.");
					return false;
				}

				if (!$scope.workDataRTS.asRevDate) {
					alert("Please enter revise AS date.");
					return false;
				}

				if (!$scope.workDataRTS.asRevAmt) {
					alert("Please enter revise AS amount.");
					return false;
				}

				if (!$scope.workDataRTS.asRevRemarks) {
					alert("Please enter revise AS remarks.");
					return false;
				}

				if (AdRevPdfFile) {
					$scope.noFile2Error = (AdRevPdfFile) ? false : true;
					var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
					if (AdRevPdfFile) {
						$scope.fileSizeErrorAd = (AdRevPdfFile.size > maxSizeUpload2) ? true : false;
					}
					if ($scope.noFile2Error)
						return false;
					if ($scope.fileSizeErrorAd)
						return false;

				} else {
					//$scope.noFile2Error = true;
					if ($scope.fileExtentionErrorRevAd)
						return false;
					if (!$scope.workDataRTS.asRevFileId) {
						alert("Please select revise Administration Sanction File");
						return false;
					}
				}
			}



			if (confirm("Are you sure you want to save the data?")) {

				$loading.start('sample-1');

				var fd = new FormData();

				if ($scope.workData.workId) {
					fd.append('workId', $scope.workData.workId);
				}

				if ($scope.workDataTS.id) {
					fd.append('tsAsId', $scope.workDataTS.id);
				}



				if ($scope.workDataRTS.tsRevNo) {
					fd.append('rvOrderNo', $scope.workDataRTS.tsRevNo);
				}



				if (ldRevPdfFile) {
					fd.append('rvDocumentUpload', ldRevPdfFile);
				}




				if ($scope.workDataRTS.tsRevDate) {
					fd.append('rvOrderDate', $scope.workDataRTS.tsRevDate);
				}



				if ($scope.workDataRTS.tsRevAmt) {
					fd.append('rvAmt', $scope.workDataRTS.tsRevAmt);
				}




				if ($scope.workDataRTS.tsRevRemarks) {
					fd.append('rvRemarks', $scope.workDataRTS.tsRevRemarks);
				}



				if ($scope.workDataRTS.revisedStatus) {
					fd.append('revisedStatus', $scope.workDataRTS.revisedStatus);
				}



				$loading.start('sample-1');

				var responsePromise = $http.post('addTSReviseWorkData', fd, {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined
					}
				});

				responsePromise.success(function(data, status, headers, config) {
					$rootScope.responseObject = data;

					if ($rootScope.responseObject.successMessage != null) {
						$timeout(function() {
							$rootScope.responseObject.successMessage = null;
						}, 5000);

						if ($scope.workDataRTS.revisedStatus == '2') {
							$scope.createASReviseWorkData(ldRevPdfFile, AdRevPdfFile, mode);
						} else {
							$scope.successResponseRevised = 'success';


							$scope.workDataTS.workId = $rootScope.responseObject.id;
							$window.location.href = '#editWork/' + $scope.workDataTS.workId;

							// $scope.loadTSASRevisedList($scope.workDataTS.workId);

						}



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

			else {

				$scope.submit = false;
			}
		};

	$scope.createASReviseWorkData = function(ldRevPdfFile, AdRevPdfFile, mode) {

		if ($scope.workDataRTS.revisedStatus == '1') {
			if (!$scope.workDataRTS.asRevNo) {
				alert("Please enter revise AS no.");
				return false;
			}

			if (!$scope.workDataRTS.asRevDate) {
				alert("Please enter revise AS date.");
				return false;
			}

			if (!$scope.workDataRTS.asRevAmt) {
				alert("Please enter revise AS amount.");
				return false;
			}

			if (!$scope.workDataRTS.asRevRemarks) {
				alert("Please enter revise AS remarks.");
				return false;
			}
			if (AdRevPdfFile) {
				$scope.noFile2Error = (AdRevPdfFile) ? false : true;
				var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
				if (AdRevPdfFile) {
					$scope.fileSizeErrorRevAd = (AdRevPdfFile.size > maxSizeUpload2) ? true : false;
				}
				if ($scope.noFile2Error)
					return false;
				if ($scope.fileSizeErrorRevAd)
					return false;

			} else {
				//$scope.noFile2Error = true;

				if (!$scope.workDataRTS.asRevFileId) {
					alert("Please select revise Administration Sanction File");
					return false;
				}
			}

			if (confirm("Are you sure you want to save the data?")) {

				$loading.start('sample-1');

				var fd = new FormData();

				if ($scope.workData.workId) {
					fd.append('workId', $scope.workData.workId);
				}

				if ($scope.workDataTS.id) {
					fd.append('tsAsId', $scope.workDataTS.id);
				}




				if (AdRevPdfFile) {
					fd.append('rvDocumentUpload', AdRevPdfFile);
				}





				if ($scope.workDataRTS.asRevNo) {
					fd.append('rvOrderNo', $scope.workDataRTS.asRevNo);
				}

				if ($scope.workDataRTS.asRevDate) {
					fd.append('rvOrderDate', $scope.workDataRTS.asRevDate);
				}

				if ($scope.workDataRTS.asRevAmt) {
					fd.append('rvAmt', $scope.workDataRTS.asRevAmt);
				}

				if ($scope.workDataRTS.asRevRemarks) {
					fd.append('rvRemarks', $scope.workDataRTS.asRevRemarks);
				}

				if ($scope.workDataRTS.revisedStatus) {
					fd.append('revisedStatus', $scope.workDataRTS.revisedStatus);
				}



				$loading.start('sample-1');

				var responsePromise = $http.post('addASReviseWorkData', fd, {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined
					}
				});

				responsePromise.success(function(data, status, headers, config) {
					$rootScope.responseObject = data;

					if ($rootScope.responseObject.successMessage != null) {
						$timeout(function() {
							$rootScope.responseObject.successMessage = null;
						}, 5000);


						$scope.successResponseRevised = 'success';
						$scope.workDataTS.workId = $rootScope.responseObject.id;
						$window.location.href = '#editWork/' + $scope.workDataTS.workId;
						//$scope.loadTSASRevisedList($scope.workDataTS.workId);

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

			else {

				$scope.submit = false;
			}

		} else {


			$loading.start('sample-1');

			var fd = new FormData();

			if ($scope.workData.workId) {
				fd.append('workId', $scope.workData.workId);
			}

			if ($scope.workDataTS.id) {
				fd.append('tsAsId', $scope.workDataTS.id);
			}




			if (AdRevPdfFile) {
				fd.append('rvDocumentUpload', AdRevPdfFile);
			}





			if ($scope.workDataRTS.asRevNo) {
				fd.append('rvOrderNo', $scope.workDataRTS.asRevNo);
			}

			if ($scope.workDataRTS.asRevDate) {
				fd.append('rvOrderDate', $scope.workDataRTS.asRevDate);
			}

			if ($scope.workDataRTS.asRevAmt) {
				fd.append('rvAmt', $scope.workDataRTS.asRevAmt);
			}

			if ($scope.workDataRTS.asRevRemarks) {
				fd.append('rvRemarks', $scope.workDataRTS.asRevRemarks);
			}

			if ($scope.workDataRTS.revisedStatus) {
				fd.append('revisedStatus', $scope.workDataRTS.revisedStatus);
			}



			$loading.start('sample-1');

			var responsePromise = $http.post('addASReviseWorkData', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$scope.successResponseRevised = 'success';
					$scope.workDataTS.workId = $rootScope.responseObject.id;
					$window.location.href = '#editWork/' + $scope.workDataTS.workId;
					//$scope.loadTSASRevisedList($scope.workDataTS.workId);


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


	$scope.downloadDocumentTS = function(documentId) {
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentTS/' + documentId);
	};

	$scope.downloadDocumentTSRevised = function(documentId) {
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentTSRevised/' + documentId);
	};

	$scope.downloadDocumentWSPro = function(documentId) {
		console.log(" downloadDocument =" + documentId);


		$window.open('downloadDocumentWSPro/' + documentId);
	};

	$scope.downloadDocumentIdWSPro = function(documentId, workSubStatusNameE) {
		//console.log(" downloadDocument =" + documentId + "@@@@" + workSubStatus);
		// $scope.loadWorkProgressDocumetnId(documentId);
		$scope.workDataCC = $scope.workDataCC || {};
		$scope.workDataProgress = $scope.workDataProgress || {};
		$scope.workDataCC.workNo = ($scope.workData && $scope.workData.workNo)
			|| $scope.workDataCC.workNo
			|| '';
		$scope.workDataProgress.workSubStatusNameE = (workSubStatusNameE && workSubStatusNameE !== 'null')
			? String(workSubStatusNameE)
			: ($scope.workDataProgress.workSubStatusNameE || '');
		$scope.imageurl = 'downloadDocumentWSPro/' + documentId;

		$scope.$applyAsync(function() {
			$('#exampleModal').modal('show');
		});
		return false;
		//	$window.open('downloadDocumentWSPro/' + documentId, '_blank');
	};

	$scope.downloadDocumentAS = function(documentId) {
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentAS/' + documentId);
	};
	$scope.dowloadWPGroupImages = function(doucmentid) {
		$window.open('downloadDocumentsZip/' + doucmentid);
	}

	$scope.downloadDocumentDW = function(documentId) {

		$window.open('downloadDocumentDW/' + documentId);
	};

	$scope.downloadDocumentTender = function(documentId) {
		//alert('documentId  '+documentId)
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentTender/' + documentId);
	};

	$scope.downloadDocumentProgress = function(documentId) {
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentProgress/' + documentId);
	};

	$scope.downloadDocumentCC = function(documentId) {

		$window.open('downloadDocumentCC/' + documentId);
	};

	$scope.downloadDocumentLOI = function(documentId) {
		//alert('documentId  '+documentId)
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentLOI/' + documentId);
	};

	$scope.downloadDocumentAgreement = function(documentId) {
		//alert('documentId  '+documentId)
		console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentAgreement/' + documentId);
	};



	$scope.checkTenderStatus = function(statusId) {

		if (statusId == '3' || statusId == '4' || statusId == '5' || statusId == '6' || statusId == '7') {

			$scope.createTenderAgreementDataStatusWise(statusId);
		}


	};


	$scope.createTenderAgreementDataStatusWise = function(statusId) {



		$loading.start('sample-1');





		var fd = new FormData();


		if ($scope.workDataTender.workId) {
			fd.append('workId', $scope.workDataTender.workId);
		} else {


		}
		if ($scope.workDataTender.workOrderDate) {
			fd.append('workOrderDate', $scope.workDataTender.workOrderDate);
		}


		if ($scope.workDataTender.workStatusId) {

			fd.append('workStatusId', $scope.workDataTender.workStatusId)
		}

		if ($scope.workDataTender.rateStatus) {
			fd.append('rateStatus', $scope.workDataTender.rateStatus);
		}


		if ($scope.workDataTender.tenderPercentage) {
			fd.append('tenderPercentage', $scope.workDataTender.tenderPercentage);
		}



		if ($scope.workDataTender.contractAmount) {
			fd.append('contractAmount', $scope.workDataTender.contractAmount);
		}


		if ($scope.workDataTender.contractTenure) {
			fd.append('contractTenure', $scope.workDataTender.contractTenure);
		}
		if ($scope.workDataTender.workCompletionDate) {
			fd.append('workCompletionDate', $scope.workDataTender.workCompletionDate);
		}

		if ($scope.workDataTender.remarks) {
			fd.append('remarks', $scope.workDataTender.remarks);
		}



		if ($scope.workDataTender.workRequestStatusId) {
			fd.append('workRequestStatusId', $scope.workDataTender.workRequestStatusId);
		}


		$loading.start('sample-1');

		var responsePromise = $http.post('addTenderWorkAgreement', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.success(function(data, status, headers, config) {
			$rootScope.responseObject = data;
			$scope.workDataContractor = {};
			$scope.workDataContractor.workId = null;
			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);

				$scope.workDataContractor.workId = $rootScope.responseObject.id;


				$scope.loadContractorDetails();
				$scope.loadTSASDetails();
				$scope.loadWorkDetails('sec');
				$scope.loadWorkProgress();
				$scope.reloadJqueryDatatablestatus();


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





	};



	$scope.createTenderAgreementData = function(workTenderForm, isValid, ldTdfFile, dTTTFile, ldTULFile, ldUAFile) {


		if ($scope.isWorkOrderIssuedRequired()) {
			if (!isValid) {
				alert("All fields are required.");
				return false;
			}

			var missingWorkOrderFields = [];
			var hasValue = function(value) {
				return value !== undefined && value !== null && value !== '' && value !== 'undefined' && value !== 'null';
			};

			if (!hasValue($scope.workDataTender.workOrderDate)) missingWorkOrderFields.push("Work Order Date");
			if (!hasValue($scope.workDataTender.tenderCalledDate)) missingWorkOrderFields.push("Tender Called Date");
			if (!hasValue($scope.workDataTender.eTenderNo)) missingWorkOrderFields.push("E-Tender No");
			if (!hasValue($scope.workDataTender.tenderReceivedDate)) missingWorkOrderFields.push("Tender Received Date");
			if (!hasValue($scope.workDataTender.loaIssuedDate)) missingWorkOrderFields.push("LoA Issued Date");
			if (!hasValue($scope.workDataTender.agreementNo)) missingWorkOrderFields.push("Agreement No");
			if (!hasValue($scope.workDataTender.agreementDate)) missingWorkOrderFields.push("Agreement Date");
			if (!hasValue($scope.workDataTender.sorYear)) missingWorkOrderFields.push("SOR Year");
			if (!hasValue($scope.workDataTender.pacAmount)) missingWorkOrderFields.push("PAC Amount");
			if (!hasValue($scope.workDataTender.contractTenure)) missingWorkOrderFields.push("Contract Period");
			if (!hasValue($scope.workDataTender.workCompletionDate)) missingWorkOrderFields.push("Work Completion Date");

			if ($scope.workData.isTender == '1' || $scope.workData.isTender == 1 || $scope.workData.isTender === true) {
				if (!hasValue($scope.workDataTender.tenderPercentage)) missingWorkOrderFields.push("Tender Percentage");
				if (!hasValue($scope.workDataTender.rateStatus)) missingWorkOrderFields.push("Above/Below");
				if (!hasValue($scope.workDataTender.contractAmount)) missingWorkOrderFields.push("Contract Amount");
			}

			if (!ldTdfFile && !$scope.workDataTender.tenderFileId) missingWorkOrderFields.push("Work Order File");
			if (!dTTTFile && !$scope.workData.drawingId) missingWorkOrderFields.push("Drawing File");
			if (!ldTULFile && !$scope.workDataTender.uLoiId) missingWorkOrderFields.push("LOA File");
			if (!ldUAFile && !$scope.workDataTender.uAId) missingWorkOrderFields.push("Agreement File");

			if (missingWorkOrderFields.length > 0) {
				alert("Please fill required fields: " + missingWorkOrderFields.join(", "));
				return false;
			}
		}

		var tenderPostStatuses = ['8', '9', '10', '11', '12', '13'];
		if (tenderPostStatuses.indexOf(String($scope.workDataTender.workStatusId)) >= 0) {

			if ($scope.workDataTender.workStatusId == '8' && ($scope.workData.isTenders == 1 || $scope.workData.isTenders == true)) {
			//	alert($scope.workDataTender.eTenderNo+"<==$scope.workDataTender.eTenderNo------$scope.workDataTender.workStatusId"+$scope.workDataTender.workStatusId);
				if ($scope.workDataTender.eTenderNo == undefined || $scope.workDataTender.eTenderNo =="undefined" || $scope.workDataTender.eTenderNo == "" || $scope.workDataTender.eTenderNo == null || $scope.workDataTender.eTenderNo == "null") {
					//alert('Please select E-Tender No');
					alert("All fields are required.")
					return false;
				}

				if ($scope.workDataTender.tenderCalledDate == undefined || $scope.workDataTender.tenderCalledDate =="undefined" || $scope.workDataTender.tenderCalledDate == "" || $scope.workDataTender.tenderCalledDate == null || $scope.workDataTender.tenderCalledDate == "null") {
					//	alert('Please select Tender Called Date');
					alert("All fields are required.")
					return false;
				}
			

			
				if ($scope.workDataTender.tenderReceivedDate == undefined || $scope.workDataTender.tenderReceivedDate =="undefined" || $scope.workDataTender.tenderReceivedDate == "" || $scope.workDataTender.tenderReceivedDate == null || $scope.workDataTender.tenderReceivedDate == "null") {
					//	alert('Please select Tender Received Date');
					alert("All fields are required.")
					return false;
				}
			

			/*if ($scope.workDataTender.workStatusId == '14') {
				if ($scope.workDataTender.reTenderDate == undefined) {
					alert('Please select Re-Tender Date');
					return false;
				}
			}*/
		
				if ($scope.workDataTender.loaIssuedDate == undefined || $scope.workDataTender.loaIssuedDate =="undefined" || $scope.workDataTender.loaIssuedDate == "" || $scope.workDataTender.loaIssuedDate == null || $scope.workDataTender.loaIssuedDate == "null") {
					//alert('Please select LoA Issued Date');
					alert("All fields are required.")
					return false;
				}

				if ($scope.workDataTender.pacAmount == undefined || $scope.workDataTender.pacAmount =="undefined" || $scope.workDataTender.pacAmount == "" || $scope.workDataTender.pacAmount == null || $scope.workDataTender.pacAmount == "null") {
					alert("Please enter PAC Amount")
					return false;
				}
			}

			if ($scope.workDataTender.workStatusId == '8' && ($scope.workData.isTenders == 1 || $scope.workData.isTenders == true)) {
				if (ldTdfFile) {
					$scope.noFileError = (ldTdfFile) ? false : true;
					var maxSizeUpload = 25000000;// in bytes (here 5 MB)
					if (ldTdfFile) {
						$scope.fileSizeErrorLT = (ldTdfFile.size > maxSizeUpload) ? true : false;
					}
					if ($scope.noFileError)
						return false;
					if ($scope.fileSizeErrorLT)
						return false;

				} else {
					//$scope.noFileError = true;
					if ($scope.fileExtentionErrorLT)
						return false;
					if (!$scope.workDataTender.tenderFileId) {
						alert("Please select PAC File");
						return false;
					}
				}
			}

			if ($scope.workDataTender.workStatusId == '8' && ($scope.workData.isTenders == 1 || $scope.workData.isTenders == true)) {
				if (dTTTFile) {
					$scope.noFileError = (dTTTFile) ? false : true;
					var maxSizeUpload = 25000000;// in bytes (here 5 MB)
					if (dTTTFile) {
						$scope.fileSizeErrorDW = (dTTTFile.size > maxSizeUpload) ? true : false;
					}
					if ($scope.noFileErrorDW) return false;
					if ($scope.fileSizeErrorDW) return false;

				} else {
					if ($scope.fileExtentionErrorDW)
						return false;
					if (!$scope.workData.drawingId) {
						alert("Please select drawing File..");
						return false;
					}

				}

			}


			/*	ldTULFile
				if (ldTULFile) {
					$scope.noFileError = (ldTULFile) ? false : true;
					var maxSizeUpload = 25000000;// in bytes (here 5 MB)
					if (ldTULFile) {
						$scope.fileSizeErrorLT = (ldTULFile.size > maxSizeUpload) ? true : false;
					}
					if ($scope.noFileError)
						return false;
					if ($scope.fileSizeErrorLT)
						return false;
	
				} else {
					//$scope.noFileError = true;
					if ($scope.fileExtentionErrorLT)
						return false;
					if (!$scope.workDataTender.uLoiId) {
						alert("Please select PAC File");
						return false;
					}
				}*/

			/*	ldUAFile
				if (ldUAFile) {
					$scope.noFileError = (ldUAFile) ? false : true;
					var maxSizeUpload = 25000000;// in bytes (here 5 MB)
					if (ldUAFile) {
						$scope.fileSizeErrorLT = (ldUAFile.size > maxSizeUpload) ? true : false;
					}
					if ($scope.noFileError)
						return false;
					if ($scope.fileSizeErrorLT)
						return false;
	
				} else {
					//$scope.noFileError = true;
					if ($scope.fileExtentionErrorLT)
						return false;
					if (!$scope.workDataTender.uAId) {
						alert("Please select PAC File");
						return false;
					}
				}*/

			if (confirm("Are you sure you want to save the data?")) {

				$loading.start('sample-1');

				if ($scope.saveTDraft == true && $scope.saveTNext == false) {

					$scope.workDataTender.workRequestStatusId = 1;
				}

				if ($scope.saveTDraft == true && $scope.saveTNext == true) {

					$scope.workDataTender.workRequestStatusId = 2;
				}


				var fd = new FormData();


				if ($scope.workDataTender.workId) {
					fd.append('workId', $scope.workDataTender.workId);
				}

				if ($scope.workDataTender.workOrderDate && $scope.workData.isTenders == 1) {
					fd.append('workOrderDate', $scope.workDataTender.workOrderDate);
				} else {
					fd.append('workOrderDate', $scope.workDataTender.workOrderDate);
				}



				if (ldTdfFile) {
					fd.append('pac', ldTdfFile);
				}
				if (ldTULFile) {
					fd.append('ldtul', ldTULFile);
				}
				if (ldUAFile) {
					fd.append('uploadAgreementforWork', ldUAFile);
				}

				if ($scope.workDataTender.rateStatus && $scope.workData.isTenders == 1) {
					fd.append('rateStatus', $scope.workDataTender.rateStatus);
				} else {
					fd.append('rateStatus', $scope.workDataTender.rateStatus);
				}

				if ($scope.workDataTender.tenderPercentage) {
					fd.append('tenderPercentage', $scope.workDataTender.tenderPercentage);
				}

				if ($scope.workDataTender.workStatusId && $scope.workData.isTenders == 1) {

					fd.append('workStatusId', $scope.workDataTender.workStatusId)
				} else {
					fd.append('workStatusId', $scope.workDataTender.workStatusId)
				}


				if ($scope.workDataTender.contractAmount) {
					fd.append('contractAmount', $scope.workDataTender.contractAmount);
				}

				var pacAmountValue = $scope.workDataTender.pacAmount;
				if (pacAmountValue && pacAmountValue !== 'undefined' && pacAmountValue !== undefined && pacAmountValue !== '' && !isNaN(pacAmountValue)) {
					fd.append('pacAmount', pacAmountValue);
				} else {
					fd.append('pacAmount', '0');
				}





				$scope.workDataTender.sorYear = Number($scope.workDataTender.sorYear);

				if ($scope.workDataTender.sorYear) {
					fd.append('sorYear', $scope.workDataTender.sorYear);
				}
				else {
					fd.append('sorYear', "");
				}

				if ($scope.workDataTender.contractTenure) {
					fd.append('contractTenure', $scope.workDataTender.contractTenure);
				}

				if ($scope.workDataTender.workCompletionDate) {
					fd.append('workCompletionDate', $scope.workDataTender.workCompletionDate);
				}

				if ($scope.workDataTender.remarks && $scope.workData.isTenders == 1) {
					fd.append('remarks', $scope.workDataTender.remarks);
				} else {
					fd.append('remarks', $scope.workDataTender.remarks);
				}


				if ($scope.workDataTender.workRequestStatusId) {
					fd.append('workRequestStatusId', $scope.workDataTender.workRequestStatusId);
				}

				if ($scope.workDataTender.secureAmtStatus) {
					fd.append('secureAmtStatus', $scope.workDataTender.secureAmtStatus);
				}

				if ($scope.workDataTender.startDate) {
					fd.append('startDate', $scope.workDataTender.startDate)
				}

				if ($scope.workDataTender.endDate) {
					fd.append('endDate', $scope.workDataTender.endDate)
				}


				if ($scope.workDataTender.tenderCalledDate) {
					fd.append('tenderCalledDate', $scope.workDataTender.tenderCalledDate)
				}


				if ($scope.workDataTender.eTenderNo) {
					fd.append('eTenderNo', $scope.workDataTender.eTenderNo)
				}


				if ($scope.workDataTender.tenderReceivedDate) {
					fd.append('tenderReceivedDate', $scope.workDataTender.tenderReceivedDate)
				}

				if ($scope.workDataTender.reTenderDate) {
					fd.append('reTenderDate', $scope.workDataTender.reTenderDate)
				}

				if ($scope.workDataTender.loaIssuedDate) {
					fd.append('loaIssuedDate', $scope.workDataTender.loaIssuedDate)
				}
				
						if ($scope.workDataTender.agreementDate) {
				fd.append('agreementDate', $scope.workDataTender.agreementDate)
			}
			
			if ($scope.workDataTender.agreementNo) {
				fd.append('agreementNo', $scope.workDataTender.agreementNo)
			}

				$loading.start('sample-1');

				var responsePromise = $http.post('addTenderWorkAgreement', fd, {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined
					}
				});


				responsePromise.success(function(data, status, headers, config) {
					$scope.reloadJqueryDatatablestatus();
					$rootScope.responseObject = data;
					$scope.workDataContractor = {};
					$scope.workDataContractor.workId = null;

					if ($rootScope.responseObject.successMessage != null) {
						$timeout(function() {
							$rootScope.responseObject.successMessage = null;
						}, 5000);

						$scope.workDataContractor.workId = $rootScope.responseObject.id;
						if (dTTTFile) {
							$scope.uploadedDrawingFiles($scope.workDataContractor.workId, dTTTFile);
						}


						if ($scope.saveTNext) {
							$scope.goToNextWizardTab();
						}

						$scope.loadContractorDetails();
						$scope.loadTSASDetails();
						$scope.loadWorkDetails('sec');
						$scope.loadWorkProgress();

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

			else {
				$scope.saveAsDraft = false;
				$scope.submit = false;
			}

		} else if ($scope.workDataTender.workStatusId == '3' || $scope.workDataTender.workStatusId == '4' || $scope.workDataTender.workStatusId == '5' || $scope.workDataTender.workStatusId == '14' || $scope.workDataTender.workStatusId == '7') {
			//  alert($scope.workDataTender.workStatusId);
			if ($scope.workDataTender.workStatusId > 2 && $scope.workDataTender.workStatusId <= 8) {
				//alert("ad");
				$scope.workTenderForm.$submitted = false;
			}


			if ($scope.workDataTender.workStatusId == '3') {
				//	alert($scope.workDataTender.eTenderNo+"<==$scope.workDataTender.eTenderNo------$scope.workDataTender.workStatusId"+$scope.workDataTender.workStatusId);
				if ($scope.workDataTender.eTenderNo == undefined) {
					alert('Please select E-Tender No');
					return false;
				}

				if ($scope.workDataTender.tenderCalledDate == undefined) {
					alert('Please select Tender Called Date');
					return false;
				}
			}

			if ($scope.workDataTender.workStatusId == '4') {
				if ($scope.workDataTender.tenderReceivedDate == undefined) {
					alert('Please select Tender Received Date');
					return false;
				}
			}

			/*if ($scope.workDataTender.workStatusId == '14') {
				if ($scope.workDataTender.reTenderDate == undefined) {
					alert('Please select Re-Tender Date');
					return false;
				}
			}*/
			if ($scope.workDataTender.workStatusId == '7') {
				if ($scope.workDataTender.loaIssuedDate == undefined) {
					alert('Please select LoA Issued Date');
					return false;
				}

			}


			$loading.start('sample-1');


			var fd = new FormData();


			if ($scope.workDataTender.workId) {
				fd.append('workId', $scope.workDataTender.workId);
			} else {


			}
			if ($scope.workDataTender.workOrderDate) {
				fd.append('workOrderDate', $scope.workDataTender.workOrderDate);
			}


			if ($scope.workDataTender.workStatusId) {

				fd.append('workStatusId', $scope.workDataTender.workStatusId)
			}


			if ($scope.workDataTender.rateStatus) {
				fd.append('rateStatus', $scope.workDataTender.rateStatus);
			}

			if ($scope.workDataTender.tenderPercentage) {
				fd.append('tenderPercentage', $scope.workDataTender.tenderPercentage);
			}



			if ($scope.workDataTender.contractAmount) {
				fd.append('contractAmount', $scope.workDataTender.contractAmount);
			}

			if ($scope.workDataTender.pacAmount) {
				fd.append('pacAmount', $scope.workDataTender.pacAmount);
				//	fd.append('pacAmount', (Math.round($scope.workDataTender.pacAmount * 100) / 100).toFixed(2));
			}



			if ($scope.workDataTender.contractTenure) {
				fd.append('contractTenure', $scope.workDataTender.contractTenure);
			}
			if ($scope.workDataTender.workCompletionDate) {
				fd.append('workCompletionDate', $scope.workDataTender.workCompletionDate);
			}

			if ($scope.workDataTender.remarks) {
				fd.append('remarks', $scope.workDataTender.remarks);
			}



			if ($scope.workDataTender.workRequestStatusId) {
				fd.append('workRequestStatusId', $scope.workDataTender.workRequestStatusId);
			}


			if ($scope.workDataTender.secureAmtStatus) {
				fd.append('secureAmtStatus', $scope.workDataTender.secureAmtStatus);
			}

			if ($scope.workDataTender.startDate) {
				fd.append('startDate', $scope.workDataTender.startDate)
			}

			if ($scope.workDataTender.endDate) {
				fd.append('endDate', $scope.workDataTender.endDate)
			}


			if ($scope.workDataTender.tenderCalledDate) {
				fd.append('tenderCalledDate', $scope.workDataTender.tenderCalledDate)
			}


			if ($scope.workDataTender.eTenderNo) {
				fd.append('eTenderNo', $scope.workDataTender.eTenderNo)
			}

			if ($scope.workDataTender.tenderReceivedDate) {
				fd.append('tenderReceivedDate', $scope.workDataTender.tenderReceivedDate)
			}

			if ($scope.workDataTender.reTenderDate) {
				fd.append('reTenderDate', $scope.workDataTender.reTenderDate)
			}

			if ($scope.workDataTender.loaIssuedDate) {
				fd.append('loaIssuedDate', $scope.workDataTender.loaIssuedDate)
			}
			
			if ($scope.workDataTender.agreementDate) {
				fd.append('agreementDate', $scope.workDataTender.agreementDate)
			}
			
			if ($scope.workDataTender.agreementNo) {
				fd.append('agreementNo', $scope.workDataTender.agreementNo)
			}

			$loading.start('sample-1');

			var responsePromise = $http.post('addTenderWorkAgreement', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				var savedStatusId = String($scope.workDataTender.workStatusId);
				$scope.reloadJqueryDatatablestatus();
				$rootScope.responseObject = data;
				$scope.workDataContractor = {};
				$scope.workDataContractor.workId = null;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);

					$scope.workDataContractor.workId = $rootScope.responseObject.id;
					$scope.successResponseTender = 'success';

					$scope.loadTSASDetails();
					$scope.loadWorkDetails('sec');
					$scope.loadContractorDetails();
					$scope.loadWorkProgress();

					$scope.moveToNextTenderStatusField(savedStatusId);





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
	$scope.uploadedDrawingFiles = function(workId, drpdffile) {

		var fd = new FormData();

		if (workId) {
			fd.append('workId', workId);
		}


		if (drpdffile) {
			fd.append('drawingFile', drpdffile);
		}

		$loading.start('sample-1');
		var responsePromise = $http.post('uploadedDrawingFiles', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.success(function(data, status, headers, config) {

		});


		responsePromise.error(function() {
			$rootScope.responseObject = {};
			$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
			$timeout(function() {
				$rootScope.responseObject.errorMessage = null;
			}, 10000);
			$loading.finish('sample-1');
		});


	};


	//
	$scope.createContractorDetails = function(isValid) {

		if (!$scope.canEditContractorDetails()) {
			alert("Please save Work Order Issued in Sanction Details before entering Contractor's Details.");
			return false;
		}

		if (!$scope.workDataContractor.workId && $routeParams.id) {
			$scope.workDataContractor.workId = $routeParams.id;
		}

		/*if (!isValid)
				return false;*/
//		if ($scope.workData.isTenders == 1) {
//			if (!$scope.workDataContractor.name) {
//				alert("Please enter contractor name.");
//				return false;
//			}
//
//			if (!$scope.workDataContractor.firmNameAddress) {
//				alert("Please enter contractor firm address.");
//				return false;
//			}
//		}







		if ($scope.saveCoDraft == true && $scope.saveCoNext == false) {

			$scope.workDataContractor.workRequestStatusId = 1;
		}

		if ($scope.saveCoDraft == true && $scope.saveCoNext == true) {

			$scope.workDataContractor.workRequestStatusId = 2;
		}


		if (confirm("Are you sure you want to save the data?")) {

			$loading.start('sample-1');

			var fd = new FormData();

			if ($scope.workDataContractor.workId) {
				fd.append('workId', $scope.workDataContractor.workId);
			} else if ($routeParams.id) {
				fd.append('workId', $routeParams.id);
			}

			if ($scope.workDataContractor.id) {
				fd.append('id', $scope.workDataContractor.id);
			}


			if ($scope.workDataContractor.name) {
				fd.append('name', $scope.workDataContractor.name);
			}



			if ($scope.workDataContractor.contactNo) {
				fd.append('contactNo', $scope.workDataContractor.contactNo);
			}



			if ($scope.workDataContractor.emailId) {
				fd.append('emailId', $scope.workDataContractor.emailId);
			}



			if ($scope.workDataContractor.firmNameAddress) {
				fd.append('firmNameAddress', $scope.workDataContractor.firmNameAddress);
			}



			if ($scope.workDataContractor.remarks) {
				fd.append('remarks', $scope.workDataContractor.remarks);
			}


			if ($scope.workDataContractor.pan) {
				fd.append('pan', $scope.workDataContractor.pan);
			}


			if ($scope.workDataContractor.gstin) {
				fd.append('gstin', $scope.workDataContractor.gstin);
			}


			if ($scope.workDataContractor.workRequestStatusId) {
				fd.append('workRequestStatusId', $scope.workDataContractor.workRequestStatusId);
			}



			$loading.start('sample-1');

			var responsePromise = $http.post('addContractorDetails', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 10000);
					$loading.finish('sample-1');
					return;
				}
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if ($rootScope.responseObject.id) {
						$scope.workDataContractor.workId = $rootScope.responseObject.id;
					}

					if ($scope.saveCoNext) {
						$scope.goToNextWizardTab();
					}

					$scope.loadContractorDetails();
					$scope.loadWorkProgress();
					$scope.loadTenderDetails();
					$scope.loadTSASDetails();
					$scope.loadWorkDetails('sec');
					$scope.gstinError = false;
					$scope.panError = false;
				}
				$loading.finish('sample-1');
			});

			responsePromise.error(function(response) {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = (response && response.data && response.data.errorMessage)
					? response.data.errorMessage
					: "Some error occured while saving the data";
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 10000);
				$loading.finish('sample-1');
			});

		}

		else {
			$scope.saveAsDraft = false;
			$scope.submit = false;
		}
	};

	$scope.createWorkProgressData = function(isValid, APPdfFile, mode, workprogressimages, WProdfFile) {


		if ($scope.workDataProgress.workStatusId == '10') {

			$scope.syncFinancialExpenditureTotals();

			if ($scope.workDataProgress.totalExpensess == null
				|| $scope.workDataProgress.totalExpensess === ''
				|| $scope.workDataProgress.totalExpensess === undefined) {
				alert("Please enter expenditure in the financial agency breakdown table");
				return;
			}


//			if ($scope.workDataProgress.workSubStatusId != null) {
//				if (WProdfFile) {
//					$scope.noFileError = (WProdfFile) ? false : true;
//					var maxSizeUpload = 25000000;// in bytes (here 5 MB)
//					if (WProdfFile) {
//						$scope.fileSizeErrorLW = (WProdfFile.size > maxSizeUpload) ? true : false;
//					}
//					if ($scope.noFileError)
//						return false;
//					if ($scope.fileSizeErrorLW)
//						return false;
//
//				} else {
//
//					if ($scope.fileExtentionErrorLW)
//						return false;
//					if ($scope.responseImage != 'yes') {
//						alert("Please select Image File");
//						return false;
//					}
//				}
//			}

		}




		if ($scope.saveProDraft == true && $scope.saveProNext == false) {

			$scope.workDataProgress.workRequestStatusId = 1;
		}

		if ($scope.saveProDraft == true && $scope.saveProNext == true) {

			$scope.workDataProgress.workRequestStatusId = 2;
		}



		if ($scope.workDataProgress.workStatusId == undefined) {
			if (!isValid)
				return false;
		}


		/*alert("call789") COMMECT BY SUMIT AS PER BA REQUIREMENT
				if ($scope.workDataProgress.workStatusId == 9) {
		alert("SU")
					if ($scope.workDataProgress.workSubStatusId == null) {
						alert("MIT")
						if (!isValid)
							return false;
					}
					
		alert("##")
					if (!$scope.workDataProgress.workSubDelayReasonId) {
						if (!isValid)
							return false;
					}
				}
		*/

		if ($scope.workDataProgress.workStatusId == '11') {
			if ($scope.workDataProgress.dateCompletion == null) {

				if (!isValid)
					return false;
			}

			/*if (!$scope.workDataProgress.expensessCurrentFy) {
				alert("Please enter expenses amount");
				return
			}*/



		}



		if ($scope.workDataProgress.workStatusId == '9') {

			if ($scope.workDataProgress.actionTakenDelay == null) {
				alert("Please Enter Action Taken for Delay");
				if (!isValid)
					return false;
			}

		}


		if ($scope.workDataProgress.workSubStatusId == 12) {
			if ($scope.workDataProgress.otherReasonDelay == null) {
				alert("Please Enter Other Reason for Delay ");
				if (!isValid)
					return false;
			}

		}


		if ($scope.workDataProgress.workStatusId == '10') {

			if ($scope.workDataProgress.stipulatedDateCompleted == null)
				//alert("Please Enter Stipulated Date of Completion );

				if (!isValid)
					return false;

		}


		//if ($scope.workDataProgress.workStatusId == '10') {
		//if ($scope.workDataProgress.likelyDateCompleted == null)

		//	if (!isValid)
		//	return false;

		//}




		if (confirm("Are you sure you want to save the data?")) {

			$loading.start('sample-1');
			$scope.syncFinancialExpenditureTotals();

			var saveFinancialFirst = ($scope.workDataProgress.workStatusId == '10'
				|| $scope.workDataProgress.workStatusId == '11');

			var financialSavePromise = saveFinancialFirst
				? $scope.saveFinancialAgency()
				: $q.when('SUCCESS');

			financialSavePromise.then(function(faResult) {
				if (faResult !== 'SUCCESS') {
					alert(faResult);
					$loading.finish('sample-1');
					return;
				}
				$scope.syncFinancialExpenditureTotals();
				$scope.submitWorkProgressForm(APPdfFile, WProdfFile);
			}, function() {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving financial expenditure";
				$loading.finish('sample-1');
			});

		}

		else {
			$scope.saveAsDraft = false;
			$scope.submit = false;
		}

	};

	$scope.submitWorkProgressForm = function(APPdfFile, WProdfFile) {

			var fd = new FormData();


			if ($scope.workDataProgress.workId) {
				//	alert("workId ===== " + $scope.workDataProgress.workId)
				fd.append('workId', $scope.workDataProgress.workId);
			}
			if ($scope.workDataProgress.workStatusId) {
				fd.append('workStatusId', $scope.workDataProgress.workStatusId);
			}



			if (APPdfFile) {
				fd.append('progressDocumentUpload', APPdfFile);
			}


			//if (!$scope.workDataProgress.workSubStatusId) {
			//	fd.append('workSubStatusId', $scope.workDataProgress.workSubStatusId);
			//}
			if ($scope.workDataProgress.workSubStatusId == "null" || $scope.workDataProgress.workSubStatusId == null || $scope.workDataProgress.workSubStatusId == undefined || $scope.workDataProgress.workSubStatusId == "" || $scope.workDataProgress.workSubStatusId == 0) {
				//alert("Please Select Progress Level");
				console.log("test is not defined");
			}
			else {
				fd.append('workSubStatusId', $scope.workDataProgress.workSubStatusId);

			}


			if ($scope.workDataProgress.actionTakenDelay) {

				fd.append('actionTakenDelay', $scope.workDataProgress.actionTakenDelay)
			}



			if ($scope.workDataProgress.stipulatedDateCompleted) {
				fd.append('stipulatedDateCompleted', $scope.workDataProgress.stipulatedDateCompleted);
			}




			if ($scope.workDataProgress.likelyDateCompleted) {
				fd.append('likelyDateCompleted', $scope.workDataProgress.likelyDateCompleted);
			}

			if ($scope.workDataProgress.otherReasonDelay) {
				fd.append('otherReasonDelay', $scope.workDataProgress.otherReasonDelay)
			}


			if ($scope.workDataProgress.dateCompletion) {
				fd.append('dateCompletion', $scope.workDataProgress.dateCompletion);
			}


			if (!$scope.workDataProgress.workSubDelayReasonId || $scope.workDataProgress.workSubDelayReasonId == "null" || $scope.workDataProgress.workSubDelayReasonId == undefined) {

			} else {
				fd.append('workSubDelayReasonId', $scope.workDataProgress.workSubDelayReasonId);
			}



			if ($scope.workDataProgress.expensessUptoMarch == "null" || $scope.workDataProgress.expensessUptoMarch == null || $scope.workDataProgress.expensessUptoMarch == undefined || $scope.workDataProgress.expensessUptoMarch == "" || $scope.workDataProgress.expensessUptoMarch == 0) {
				fd.append('expensessUptoMarch', '0');
			} else {
				fd.append('expensessUptoMarch', $scope.workDataProgress.expensessUptoMarch);
			}
			if ($scope.workDataProgress.totalExpensess == "null" || $scope.workDataProgress.totalExpensess == null || $scope.workDataProgress.totalExpensess == undefined || $scope.workDataProgress.totalExpensess === "") {
				fd.append('totalExpensess', '0');
				fd.append('total', '0');
			} else {
				fd.append('totalExpensess', $scope.workDataProgress.totalExpensess);
				fd.append('total', '1');
			}
			var currentTime = new Date()
			var year = currentTime.getFullYear()
			// var year ='2026';
			if (year) {

				fd.append('year', year);
			}

			if ($scope.workDataProgress.expensessCurrentFy == "null" || $scope.workDataProgress.expensessCurrentFy == null || $scope.workDataProgress.expensessCurrentFy == undefined || $scope.workDataProgress.expensessCurrentFy === "") {
				// Don't send 0 — omit the field when user left it blank
			} else {
				fd.append('expensessCurrentFy', $scope.workDataProgress.expensessCurrentFy);
			}


			if ($scope.workDataProgress.workRequestStatusId) {
				fd.append('workRequestStatusId', $scope.workDataProgress.workRequestStatusId);
			}

			if ($scope.workDataProgress.perc) {

				fd.append('perc', $scope.workDataProgress.perc);
			}


			if ($scope.workDataProgress.remarks) {
				fd.append('remarks', $scope.workDataProgress.remarks)
			}

			/*		if ($scope.workDataProgress.month) {
						fd.append('month', $scope.workDataProgress.month)
					}
		
					if ($scope.workDataProgress.financialYear) {
						fd.append('financialYear', $scope.workDataProgress.financialYear)
					}
		
					if ($scope.workDataProgress.monthlyProgress) {
						fd.append('monthlyProgress', $scope.workDataProgress.monthlyProgress)
					}
		*/

			var responsePromise = $http.post('addWorkProgress', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});
			console.log(responsePromise);
			responsePromise.then(function(response) {
				var data = response.data;
				$rootScope.responseObject = data;
				$scope.workDataCC = {};
				$scope.workDataCC.workId = null;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$scope.workDataProgress.remarks = null;

					if ($scope.workDataProgress.workStatusId == '11') {
						$scope.createWorkProExpensesData();
					}
					
					
					
					if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
						$scope.loadWorkFinancialAgencyList($scope.workDataProgress.workId);
					}

					if ($scope.workDataProgress.workSubStatusId == "null" || $scope.workDataProgress.workSubStatusId == null || $scope.workDataProgress.workSubStatusId == undefined || $scope.workDataProgress.workSubStatusId == "" || $scope.workDataProgress.workSubStatusId == 0) {

						$scope.workDataCC.workId = $rootScope.responseObject.id;

						if ($scope.saveProNext) {
							$scope.goToNextWizardTab();
						}

						$scope.loadCCDetails();
						$scope.loadContractorDetails();
						$scope.loadTenderDetails();
						$scope.loadTSASDetails();
						$scope.loadWorkDetails('sec');
						$scope.refreshWorkProgressAfterSave();

					} else {
						
						$scope.createWorkProSubStatusUploadingData(WProdfFile);
					}

					
				}
				$loading.finish('sample-1');
			}, function(error) {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
				console.error("Error saving work progress:", error);
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 10000);
				$loading.finish('sample-1');
			});

	};


	$scope.createWorkProSubStatusUploadingData = function(WProdfFile) {

		var fd = new FormData();


		if ($scope.workDataProgress.workId) {
			fd.append('workId', $scope.workDataProgress.workId);
		}




	if(WProdfFile){
			fd.append('file', WProdfFile);
}
		if ($scope.workDataProgress.workSubStatusId) {

			fd.append('workSubStatusId', $scope.workDataProgress.workSubStatusId);
		}

		if ($scope.workDataProgress.actionTakenDelay) {

			fd.append('actionTakenDelay', $scope.workDataProgress.actionTakenDelay);
		}

		if ($scope.workDataProgress.workStatusId) {

			fd.append('workStatusId', $scope.workDataProgress.workStatusId);
		}

		if ($scope.workDataProgress.remarks) {

			fd.append('remarks', $scope.workDataProgress.remarks);
		}

		if ($scope.workDataProgress.perc) {

			fd.append('perc', $scope.workDataProgress.perc);
		}

		if (!$scope.workDataProgress.workSubDelayReasonId || $scope.workDataProgress.workSubDelayReasonId == "null" || $scope.workDataProgress.workSubDelayReasonId == undefined) {

		} else {
			fd.append('workSubDelayReasonId', $scope.workDataProgress.workSubDelayReasonId);
		}

		$loading.start('sample-1');

		var responsePromise = $http.post('addWorkProSubStatusUploading', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.then(function(response) {
			var data = response.data;
			$rootScope.responseObject = data;
			$scope.workDataCC = {};
			$scope.workDataCC.workId = null;
			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);

				$scope.createWorkProExpensesData();
				$scope.refreshWorkProgressAfterSave();
				//	$scope.workDataCC.workId = $rootScope.responseObject.id;
				//$scope.finalCall = 'yes';
				//$scope.loadCCDetails();
				//$scope.loadContractorDetails();
				//$scope.loadTenderDetails();
				//$scope.loadTSASDetails();
				//$scope.loadWorkDetails('sec');
				//$scope.loadWorkProgress();

			}
			$loading.finish('sample-1');
		}, function(error) {
			$rootScope.responseObject = {};
			$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
			console.error("Error saving work progress sub-status:", error);
			$timeout(function() {
				$rootScope.responseObject.errorMessage = null;
			}, 10000);
			$loading.finish('sample-1');
		});




	};

	$scope.createWorkProExpensesData = function() {

		// Only save Expenditure Tracker when user explicitly entered a value
		// expensessCurrentFy may be auto-calculated from financial agency table
		// Only proceed if user has explicitly typed in the expense field
		var hasExpenseValue = $scope.workDataProgress.expensessCurrentFy != null
			&& $scope.workDataProgress.expensessCurrentFy !== ''
			&& $scope.workDataProgress.expensessCurrentFy !== 'null'
			&& String($scope.workDataProgress.expensessCurrentFy).trim() !== ''
			&& parseFloat($scope.workDataProgress.expensessCurrentFy) > 0;

		if (!hasExpenseValue) {
			// Nothing entered — skip saving to avoid 0-value rows
			return;
		}

		var currentTime = new Date()

		// returns the month (from 0 to 11)
		var month = currentTime.getMonth() + 1

		// returns the day of the month (from 1 to 31)
		var day = currentTime.getDate()

		// returns the year (four digits)
		var year = currentTime.getFullYear()
		//var year = '2026';
		var fd = new FormData();


		if ($scope.workDataProgress.workId) {
			fd.append('workId', $scope.workDataProgress.workId);
		}


		if ($scope.workDataProgress.expensessCurrentFy != null
			&& $scope.workDataProgress.expensessCurrentFy !== ''
			&& $scope.workDataProgress.expensessCurrentFy !== 'null') {
			fd.append('expensessCurrentFy', $scope.workDataProgress.expensessCurrentFy);
		} else if ($scope.workDataProgress.totalExpensess != null
			&& $scope.workDataProgress.totalExpensess !== ''
			&& $scope.workDataProgress.totalExpensess !== 'null'
			&& parseFloat($scope.workDataProgress.totalExpensess) > 0) {
			fd.append('expensessCurrentFy', $scope.workDataProgress.totalExpensess);
		} else {
			// No value entered — skip saving expenditure tracker row
			return;
		}
		if ($scope.workDataProgress.expensessUptoMarch == "null" || $scope.workDataProgress.expensessUptoMarch == null || $scope.workDataProgress.expensessUptoMarch == undefined || $scope.workDataProgress.expensessUptoMarch == "" || $scope.workDataProgress.expensessUptoMarch == 0) {
			fd.append('expensessUptoMarch', '0');
		} else {
			fd.append('expensessUptoMarch', $scope.workDataProgress.expensessUptoMarch);
		}

		if ($scope.workDataProgress.totalExpensess == "null" || $scope.workDataProgress.totalExpensess == null || $scope.workDataProgress.totalExpensess == undefined || $scope.workDataProgress.totalExpensess == "" || $scope.workDataProgress.totalExpensess == 0) {
			fd.append('totalExpensess', '0');
			//fd.append('total', '0');
		} else {
			fd.append('totalExpensess', $scope.workDataProgress.totalExpensess);
			//fd.append('total', '1');
		}

		if (year) {

			fd.append('year', year);
		}

		if (month) {

			fd.append('month', month);
		}


		$loading.start('sample-1');

		var responsePromise = $http.post('addWorkProExpensesData', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.success(function(data, status, headers, config) {
			$rootScope.responseObject = data;
			$scope.workDataCC = {};
			$scope.workDataCC.workId = null;
			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);

				$scope.workDataCC.workId = $rootScope.responseObject.id;
				$scope.loadWorkProgress();
				if ($scope.workDataProgress.workId) {
					$scope.loadExpensesList($scope.workDataProgress.workId);
				}
				//setTimeout(function(){
				//	var $active = $('.wizard .nav-tabs .nav-item .active');
				// var $activeli = $active.parent("li");
				//  $($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
				//  $($activeli).find('a[data-toggle="tab"]').addClass("disabled");
				//  $($activeli).next().find('a[data-toggle="tab"]').click();
				//		}, 500) ;


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




	};



	$scope.fetchWorkProgressImageDataList = function() {
		//		$scope.fileArr = [10];

		$loading.start('sample-1');
		var response = $http.get('fetchWorkProgressImageDataList');

		response.success(function(data, status, headers, config) {
			if ($scope.workData != undefined) {
				$scope.workData.workProgressImagesDataList = data;
			} else {
				$scope.workData = {};
				$scope.workData.workProgressImagesDataList = data;

			}
			$loading.finish('sample-1');
		});

	};

	$scope.addWorkProgressImages = function(index, file,) {

		$scope.workProgressData = {};
		//  $scope.workProgressImagesData={};
		if ((file && file != undefined && file != null)) {
			$scope.workProgressData["fillArr"] = file;
			if ($scope.workData.workProgressImagesData != null) {
				if ($scope.workProgressImagesData.length + ($scope.workData.workProgressImagesData).length > 4) {
					alert("you can upload maximim 5 Images!");
					return false;
				}
			}




			if ($scope.workProgressImagesData.length > 4 && $scope.workData.workProgressImagesData == undefined) {
				alert("you can upload maximim 5 Images!");
				return false;
			}


			$scope.workProgressImagesData.push({ ...$scope.workProgressData });

			//$scope.workProgressData.otherDocDate='';
			$scope.workProgressData = {};
			$('#fileupload input[type=file]').val("");


		}
		else {
			alert("Please Fill All Fields!.")
		}
	};



	$scope.removeOtherDocument = function(index) {
		console.log('bfhbfdbfjdbf' + index);
		$scope.workProgressImagesData.splice(index, 1);
	};



	$scope.uploadWorkProgressImages = function(id, fileArr) { //line 714
		$loading.start('sample-1');
		var fd = new FormData();

		if (fileArr) {
			for (var i = 0; i < 11; i++) {
				if (null != fileArr[i] && fileArr[i] != undefined) {


					fd.append('fileArr' + i, fileArr[i].fillArr);

				}
			}
		}
		if (id) {
			fd.append('id', id);
		}

		var responsePromise = $http.post('uploadWorkProgressImages', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}

		});
		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);
				if ($scope.workDataProgress.workStatusId == '11') {
					// Auto-move to next visible tab
					var $active = $('.wizard .nav-tabs .nav-item .active');
					var $activeli = $active.parent("li");
					var $nextTab = $($activeli).next().find('a[data-toggle="tab"]');
					
					if ($nextTab.length > 0 && $nextTab.is(':visible')) {
						$nextTab.removeClass("disabled");
						$nextTab.tab('show');
					}
				} else {
					$scope.successResponseProgress = 'success';
				}

			}
			if ($rootScope.responseObject.errorMessage != null) {
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

	$scope.fetchWorkProgressImagesDataList = function() {
		//		$scope.fileArr = [10];
		$loading.start('sample-1');
		var response = $http.get('fetchWorkProgressImagesDataList');

		response.success(function(data, status, headers, config) {
			if ($scope.workData != undefined) {
				$scope.workData.workProgressImagesDataList = data;
			} else {
				$scope.workData = {};
				$scope.workData.workProgressImagesDataList = data;

			}
			$loading.finish('sample-1');
		});

	};


	$scope.createCCDetails = function(isValid, CCPdfFile) {


		$scope.workDataCC.ccDate = $scope.workDataProgress.dateCompletion;


		if (CCPdfFile) {
			$scope.noFileError = (CCPdfFile) ? false : true;
			var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
			if (CCPdfFile) {
				$scope.fileSizeErrorLC = (CCPdfFile.size > maxSizeUpload2) ? true : false;
			}
			if ($scope.noFileError)
				return false;
			if ($scope.fileSizeErrorLC)
				return false;

		} else {
			if ($scope.fileExtentionErrorLC)
				return false;
			if (!$scope.workDataCC.ccFileId) {
				alert("Please select CC File");
				return false;
			}
		}

		if ($scope.workDataCC.ccDate == null || $scope.workDataCC.workStatusId == null) {
			if (!isValid)
				return false;

		}

		if ($scope.workDataCC.workStatusId == '12') {
			if (!$scope.workDataCC.dateHandOver  || !$scope.workDataCC.paymentStatus) {
				if (!isValid)
					return false;
			}
		}

		if ($scope.saveCCDraft == true && $scope.saveCCNext == false) {

			$scope.workDataCC.workRequestStatusId = 1;
		}

		if ($scope.saveCCDraft == true && $scope.saveCCNext == true) {

			$scope.workDataCC.workRequestStatusId = 2;
		}
		
		
					var fd = new FormData();

			if ($scope.workDataCC.workId) {
				fd.append('workId', $scope.workDataCC.workId);
			} else {
				fd.append('workId', $routeParams.id);
			}
			if ($scope.workDataCC.ccNo) {
				fd.append('ccNo', $scope.workDataCC.ccNo);
			}

			if (CCPdfFile) {
				fd.append('uploadCC', CCPdfFile);
			}

			if ($scope.workDataCC.ccDate) {
				fd.append('ccDate', $scope.workDataCC.ccDate);
			}

			if ($scope.workDataCC.remarks) {
				fd.append('remarks', $scope.workDataCC.remarks);
			}

			if ($scope.workDataCC.workStatusId) {
				fd.append('workStatusId', $scope.workDataCC.workStatusId);
			}

			if ($scope.workDataCC.workRequestStatusId) {
				fd.append('workRequestStatusId', $scope.workDataCC.workRequestStatusId);
			}

			if ($scope.workDataCC.dateHandOver) {
				fd.append('dateHandOver', $scope.workDataCC.dateHandOver);
			}

			if ($scope.workDataCC.handoverRemarks) {
				fd.append('handoverRemarks', $scope.workDataCC.handoverRemarks);
			}

			if ($scope.workDataCC.paymentStatus) {
				fd.append('paymentStatus', $scope.workDataCC.paymentStatus);
			}

		if (confirm("Are you sure you want to save the data?")) {

			$loading.start('sample-1');

			/*if($scope.saveAsDraft == true){
				 
				$scope.technicalSanctionStatusId = 2;
			}
			
			if($scope.submit == true){
				 
				$scope.technicalSanctionStatusId =  3;
			}*/








			var responsePromise = $http.post('addCCData', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);

					if ($scope.workDataCC.workStatusId === 1) {
						$window.location.href = '#viewCompletedWork';
					} else {
						if ($scope.saveCCNext) {
							$scope.goToNextWizardTab();
						}
						$scope.loadCCDetails();
						$scope.loadWorkDetails('sec');
						$scope.loadWorkProgress();
						// Reload the Work Status Record List table in CC tab
						$timeout(function() {
							if ($.fn.DataTable.isDataTable('#dynamic-table-workstatuscc')) {
								$('#dynamic-table-workstatuscc').DataTable().destroy();
							}
							if (typeof fetchWorkTenderStatuscc === 'function') {
								fetchWorkTenderStatuscc($scope.workData.workId || $routeParams.id);
							}
						}, 300);
					}

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

		else {
			$scope.saveAsDraft = false;
			$scope.submit = false;
		}
	};

	$scope.loadCCWorkStatusByFlag = function(flag) {

		$loading.start('sample-1');

		var response = $http.get('fetchWorkStatusByFlag/' + flag);
		response.success(function(data, status, headers, config) {
			$scope.workStatusCC = data;

			//if($scope.workDataCC.responseCC=='notCCIssued'){
			//	document.getElementById("workStatus[1]").disabled = true;
			//}
			$loading.finish('sample-1');
		});
	};





	$scope.loadWorkSubStatus = function(workStatusId) {
		console.log('WorkStatus.....' + workStatusId);
		$loading.start('sample-1');
		var response = $http.get('fetchSubWorkStatus');
		response.success(function(data, status, headers, config) {
			$scope.worksSubStatus = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkStatus1 = function() {

		$loading.start('sample-1');

		var response = $http.get('getWorkStatus');
		response.success(function(data, status, headers, config) {
			$scope.worksStatus = data;
			
			var y = $('#wsId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#workStatusId');

			}, 1000);
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkStatusByFlag = function(flag) {
		$loading.start('sample-1');

		var response = $http.get('fetchWorkStatusByFlag/' + flag);
		response.success(function(data, status, headers, config) {
			$scope.worksStatus = data;

			if (Array.isArray($scope.worksStatus)) {
				$scope.worksStatus.forEach(function(status) {
					var workStatusId = status.workStatusId; // Assuming each status object has workStatusId
					console.log('Processing workStatusId:', workStatusId);


				});
			}



			$loading.finish('sample-1');
		});
	};


	$scope.loadWorkStatusProgressByFlag = function(flag) {

		$loading.start('sample-1');

		var response = $http.get('fetchWorkStatusByFlag/' + flag);
		response.success(function(data, status, headers, config) {
			$scope.workProStatus = data;
			$loading.finish('sample-1');
		});
	};


	$scope.setBgColor = function(workstatusId) {
		console.log('COLOR...' + workstatusId);
		if (workstatusId == 9) {
			$scope.color = '#FF3333';
		}
		if (workstatusId == 10) {
			$scope.color = '#FFD700';
		}
		if (workstatusId == 11) {
			$scope.color = '#0FFF50';
		}
		if (workstatusId == 12) {
			$scope.color = '#87CEFA';
		}
		console.log('COLOR...' + $scope.color);
		$("#workStatus").css("background-color", $scope.color);


	};


	$scope.loadWorkSubStatusByWorkStatus = function(workSubStatusId, workstatusId) {
		$loading.start('sample-1');

		if ($scope.workDataProgress.workStatusId == 9) {
			workSubStatusId = 0;
		}


		var response = $http.get('fetchWorkSubStatusByWorkStatus/' + workSubStatusId + "/" + workstatusId);
		response.success(function(data, status, headers, config) {
			$scope.worksSubStatus = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadPercByWorkSubStatus = function(workSubStatusId) {

		$loading.start('sample-1');

		if ($scope.workData.workTypeId == '1') {

			var response = $http.get('fetchPercByWorkSubStatus/' + workSubStatusId);
			response.success(function(data, status, headers, config) {
				$scope.workPerc = data;
				$scope.workDataProgress.perc = $scope.workPerc.percentage;
				$loading.finish('sample-1');
			});

		} else {
			var response = $http.get('fetchPercByWorkSubStatusUpgrad/' + workSubStatusId);
			response.success(function(data, status, headers, config) {
				$scope.workPerc = data;
				$scope.workDataProgress.perc = $scope.workPerc.percentage;
				$loading.finish('sample-1');
			});
		}



	};


	$scope.ExpensesCaluculation = function() {
		$scope.workDataProgress.totalExpensess = $scope.workDataProgress.expensessUptoMarch + $scope.workDataProgress.expensessCurrentFy;
	}

	$scope.PercentageCaluculation = function() {

		if ($scope.workDataTender.pacAmount) {
			if ($scope.workDataTender.tenderPercentage) {
				if ($scope.workDataTender.rateStatus == 'Above') {
					//$scope.workDataTender.contractAmount = $scope.workDataTender.tenderPercentage + $scope.workDataTender.pacAmount * 0.01 * $scope.workDataTender.tenderPercentage;
					$scope.workDataTender.contractAmount = $scope.workDataTender.pacAmount * (1 + ($scope.workDataTender.tenderPercentage * 0.01));
					$scope.workDataTender.contractAmount = (Math.round($scope.workDataTender.pacAmount * (1 + ($scope.workDataTender.tenderPercentage * 0.01)) * 100) / 100).toFixed(2);
				} if ($scope.workDataTender.rateStatus == 'Below') {
					//$scope.workDataTender.contractAmount = $scope.workDataTender.tenderPercentage - $scope.workDataTender.pacAmount * 0.01 * $scope.workDataTender.tenderPercentage;
					//$scope.workDataTender.contractAmount = $scope.workDataTender.pacAmount * (1 - ($scope.workDataTender.tenderPercentage * 0.01));
					$scope.workDataTender.contractAmount = (Math.round($scope.workDataTender.pacAmount * (1 - ($scope.workDataTender.tenderPercentage * 0.01)) * 100) / 100).toFixed(2);

				}
			}

		}



	}



	$scope.DateCaluculationByMonth = function(workOrderDate, month) {
		// Split the input date string into day, month, and year components
		var dateComponents = workOrderDate.split('/');
		var day = parseInt(dateComponents[0]);
		var monthNumber = parseInt(dateComponents[1]) - 1; // Subtract 1 as months are zero-based in JavaScript
		var year = parseInt(dateComponents[2]);
		// Create a new Date object using the parsed components
		var startDate = new Date(year, monthNumber, day);
		// Calculate the new date by adding the specified number of months
		var newDate = new Date(startDate.setMonth(startDate.getMonth() + month));
		$scope.workDataTender.workCompletionDate = newDate;
	};


	$scope.loadWorkDetails = function(mode) {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkDetails/' + $routeParams.id);
		$scope.workDataTS = {};
		$scope.workDataTS.workId = null;
		$scope.workDataTender = {};
		$scope.workDataTender.workId = null;
		$scope.workDataContractor = {};
		$scope.workDataContractor.workId = null;
		$scope.workDataProgress = {};
		$scope.workDataProgress.workId = null;
		$scope.workDataCC = {};
		$scope.workDataCC.workId = null;
		$scope.workDataCC.workNo = null;


		response.success(function(data, status, headers, config) {
			$scope.workData = data;
			$scope.workData.role = $scope.workData.role;
			$scope.workData.DmRemakrs = $scope.workData.dmRemakrs;
			//alert("$scope.workData.isTenders=== " + $scope.workData.isTenders)


			$scope.workData.isTenders1 = $scope.workData.isTenders;
			$scope.checkOneYearCompletion();
			$scope.workDataTS.workId = $scope.workData.workId;
			$scope.workDataTender.workId = $scope.workData.workId;
			$scope.workDataContractor.workId = $scope.workData.workId;
			$scope.workDataProgress.workId = $scope.workData.workId;


			$scope.workDataCC.workId = $scope.workData.workId;
			$scope.workDataCC.workNo = $scope.workData.workNo;
			$scope.drawingStatus = $scope.workData.drawingStatus;
			$scope.workData.financialYear = $scope.workData.financialYear + "";
			$scope.workData.headId = $scope.workData.headId + "";
			$scope.workData.schemeId = $scope.workData.schemeId + "";
			$scope.workData.headStateId = $scope.workData.headStateId + "";
			$scope.workData.schemeStateId = $scope.workData.schemeStateId + "";
			$scope.workData.headNhmId = $scope.workData.headNhmId + "";
			$scope.workData.schemeNhmId = $scope.workData.schemeNhmId + "";
			$scope.workData.headEcpr2Id = $scope.workData.headEcpr2Id + "";
			$scope.workData.schemeEcpr2Id = $scope.workData.schemeEcpr2Id + "";


			//alert($scope.workData.schemeEcpr2Id);
			$scope.workData.headOthersId = $scope.workData.headOthersId + "";
			$scope.workData.schemeOthersId = $scope.workData.schemeOthersId + "";
			$scope.workData.workStatusId = $scope.workData.workStatusId + "";
			$scope.workData.workTypeId = $scope.workData.workTypeId + "";
			$scope.workData.workSubTypeId = $scope.workData.workSubTypeId + "";
			$scope.workData.workCategoryId = $scope.workData.workCategoryId + "";
			$scope.workData.categorySubTypeId = $scope.workData.categorySubTypeId + "";
			$scope.workData.implementationAgency = $scope.workData.implementationAgency + "";
			$scope.workData.implementationAgencyNameE = $scope.workData.implementationAgencyNameE + "";
			$scope.workData.workPriorityId = $scope.workData.workPriorityId + "";
			$scope.workData.workPriority = $scope.workData.workPriority + "";


			if ($scope.workData.implAgencyTypeId != "null" && $scope.workData.implementationAgencyId != "null") {
				$scope.workData.isRecommendedAgency = '0';
			} else {
				$scope.workData.isRecommendedAgency = '1';
			}


			$scope.loadDivisions();
			$scope.workData.divisionId = $scope.workData.divisionId + "";
			$scope.loadDistrictByDivision($scope.workData.divisionId);
			$scope.workData.districtCode = $scope.workData.districtCode + "";
			$scope.loadBlockByDistrictID($scope.workData.districtCode);
			$scope.workData.blockCode = $scope.workData.blockCode + "";
			$scope.loadGramPanchayatByBlockID($scope.workData.districtCode, $scope.workData.blockCode);
			$scope.loadLegislativeByDistrict($scope.workData.districtCode);
			$scope.workData.blockId = $scope.workData.blockId + "";
			$scope.workData.constituencyCode = $scope.workData.constituencyCode + "";
			$scope.workData.financialHeadId = $scope.workData.financialHeadId + "";
			$scope.workData.vidhanSabhaId = $scope.workData.vidhanSabhaId + "";
			
			
			
			// ===== FINANCIAL HEAD LIST FROM BACKEND =====
			$scope.workDataRows = [];

			if ($scope.workData.financialHeads && $scope.workData.financialHeads.length > 0) {

				$scope.workData.financialHeads.forEach(function(item) {
					$scope.workDataRows.push({
						financialAgencyId : item.id + "",
						financialHeadId: item.financialHeadId + "",
						cost: item.cost,
						totalCost: item.totalCost,
						availableFinancialHeads: angular.copy($scope.financialHeadsOriginal)
					});
				});

				// dropdown options update
				$scope.updateAllAvailableFinancialHeads();

			} else {
				$scope.addRow();
			}

			

			$scope.loadTSASDetails();
			//	$scope.loadTenderDetails($scope.workData.workStatusId);


			//if(mode == 'first')
			if (mode == undefined) {
				$scope.loadExpensesList($scope.workDataProgress.workId);
				$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);
				$scope.loadTSASRevisedList($scope.workDataTS.workId);
				$scope.loadWorkFinancialAgencyList($scope.workData.workId);


			}


			if (mode == 'first') {
				$scope.loadTSASRevisedList($scope.workDataTS.workId);
				$scope.loadExpensesList($scope.workDataProgress.workId);
				$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);
				$scope.loadWorkFinancialAgencyList($scope.workData.workId);
			}
			// $scope.loadExpensesList($scope.workDataProgress.workId); add by sumit 
			//	$scope.loadExpensesList($scope.workDataProgress.workId);
			//	$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);
			if ($scope.workData.workRequestStatusId == 2) {

				document.getElementById("draftbtn").disabled = true;
			}
			$scope.loadTotalIdCount($scope.workData.workId);

			$loading.finish('sample-1');
		});


		//var response = $http.get('https://data.covid19india.org/v4/min/data.min.json');
		//$loading.start('sample-1');
		//$scope.dataRes = {};
		//response.success(function(data, status, headers, config) {
		//	$scope.dataRes=data;
		//console.log('response...'+$scope.dataRes);
		//alert('gvdggvdgd'+$scope.dataRes);
		//$loading.finish('sample-1');
		//});


	};

	$scope.checkOneYearCompletion = function() {
		var oneYearFromGivenDate = new Date($scope.workData.startDate);
		oneYearFromGivenDate.setFullYear(oneYearFromGivenDate.getFullYear() + 1);

		// Calculate one month before the one-year anniversary
		var oneMonthBeforeOneYear = new Date(oneYearFromGivenDate);
		oneMonthBeforeOneYear.setMonth(oneMonthBeforeOneYear.getMonth() - 1);

		// Format dates as "dd-mm-yyyy"
		var formatDate = function(date) {
			var day = date.getDate();
			var month = date.getMonth() + 1; // Months are 0-indexed
			var year = date.getFullYear();

			return (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + year;
		};

		var formattedOneMonthBeforeOneYear = formatDate(oneMonthBeforeOneYear);
		var formattedOneYearFromGivenDate = formatDate(oneYearFromGivenDate);

		// Start checking on interval
		var currentDate = new Date();
		if (currentDate >= oneMonthBeforeOneYear && currentDate <= oneYearFromGivenDate) {
			Swal.fire({
				title: 'Your ' + $scope.workData.secureAmtStatus + ' is going to expire on ' + formattedOneYearFromGivenDate,
				icon: 'Alert',
				showCancelButton: false,
				confirmButtonText: 'OK',
			}).then(() => {
				//$scope.showForm = true;
				//location.reload();
			});
		}
	};

	$scope.loadTSASDetails = function(pageno) {

		$loading.start('sample-1');
		var response = $http.get('fetchTSASDetails/' + $routeParams.id);



		response.success(function(data, status, headers, config) {

			if (!data) {
				$scope.workDataTS.workStatus = 'notAsIssued';
				$scope.workDataTender.workStatus = 'notTenderIssued';
				$scope.workDataContractor.workStatus = 'notContractorIssued';
				$scope.workDataProgress.workStatus = 'notProgressIssued';

			} else {
				$scope.workDataTS = data;
				$scope.workDataTS.tsNo = $scope.workDataTS.tsNo + "";
				//var $active = $('.wizard .nav-tabs .nav-item .active');
				//  var $activeli = $active.parent("li");
				//  $($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
				// $($activeli).next().find('a[data-toggle="tab"]').show();
				// $loading.finish('sample-1');

				$scope.successRespondeTS = 'success';
				$scope.loadTenderDetails();
				//$scope.loadTSASRevisedList($scope.workDataTS.workId);

			}



			$loading.finish('sample-1');

		});



	};
	$scope.resetSearchForm = function() {
		setTimeout(function() {
			$scope.$apply(function() {
				reDraw();
			});
		}, 250);
	}


	$scope.loadWorkProgress = function() {
		//alert("call=============")
		$loading.start('sample-1');


		var response = $http.get('fetchWorkProgress/' + $routeParams.id);



		response.success(function(data, status, headers, config) {

			if (!data) {
				$scope.workDataProgress.workStatus = 'notProgressIssued';
				$scope.workDataProgress.workId = ($scope.workData && $scope.workData.workId)
					|| $routeParams.id;
				$scope.responseImage = 'no';
				$scope.workDataProgress.progressUpdated = 'notSelected';
				$scope.reloadStep5Tables();
			} else {




				$scope.workDataProgress = data;
				
				$scope.loadWorkSubStatusByWorkStatus($scope.workData.workSubTypeId, $scope.workDataProgress.workStatusId);
				//alert("callworkProgressCount=============" + $scope.workDataProgress.workProgressCount)
				//alert("$scope.workDataProgress.workStatusId===@@@@@@@@" + $scope.workDataProgress.workStatusId)
				if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
					//alert("$scope.workDataProgress.workProgressCount===" + $scope.workDataProgress.workProgressCount)
					if ($scope.workDataProgress.workProgressCount == 1) {
						document.getElementById("totalExpensess").disabled = true;
					} else if ($scope.workDataProgress.totalExpensess == null) {
						document.getElementById("totalExpensess").disabled = false;
					}
				}
				$scope.workDataProgress.month = $scope.workDataProgress.month + '';
				$scope.workDataProgress.financialYear = $scope.workDataProgress.financialYear + '';

				$scope.workDataProgress.workId = $scope.workDataProgress.workId + "";
				$scope.workDataProgress.workStatusId = $scope.workDataProgress.workStatusId + "";
				$scope.workDataProgress.workSubStatusId = $scope.workDataProgress.workSubStatusId + "";
				$scope.workDataProgress.workSubDelayReasonId = $scope.workDataProgress.workSubDelayReasonId + "";
				$scope.workDataProgress.progressUpdated = $scope.workDataProgress.workStatusId + "";
				$scope.workDataProgress.stipulatedDateCompleted = $scope.workDataTender.workCompletionDate ? $scope.workDataTender.workCompletionDate + "" : "";
				if ($scope.workDataProgress.workSubStatusId == '5' || $scope.workDataProgress.workSubStatusId == '6' || $scope.workDataProgress.workSubStatusId == '7' || $scope.workDataProgress.workSubStatusId == '8' || $scope.workDataProgress.workSubStatusId == '9' || $scope.workDataProgress.workSubStatusId == '10' || $scope.workDataProgress.workSubStatusId == '11') {
					$scope.responseImage = 'yes';

				}

				$scope.loadCCDetails();
				$scope.loadWorkSubStatusByWorkStatus($scope.workData.workSubTypeId, $scope.workDataProgress.workStatusId);
				$scope.loadWorkSubDelayReasonBySubWorkStatus($scope.workDataProgress.workSubStatusId);
				console.log('SubStatusWork....' + $scope.workDataProgress.workSubStatusId);
				
				$scope.reloadStep5Tables();

				if ($scope.finalCall == 'yes') {

				} else {
					if ($scope.workDataProgress.workSubStatusId == "null" || $scope.workDataProgress.workSubStatusId == null || $scope.workDataProgress.workSubStatusId == undefined || $scope.workDataProgress.workSubStatusId == "" || $scope.workDataProgress.workSubStatusId == 0) {
						if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
							$scope.successRespondeWS = 'success';
							$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);

						} else {

						}
					} else {
						$scope.successRespondeWS = 'success';
						//$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);
					}
				}





			}


			/*	if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
					if ($scope.workDataProgress.totalExpensess == null && $scope.workDataProgress.workProgressCount >= 1) {
						document.getElementById("totalExpensess").disabled = false;
					} else {
						document.getElementById("totalExpensess").disabled = true;
					}
	
				}*/





			//console.log('Financial total....' + $scope.workDataProgress.totalExpensess)

			$loading.finish('sample-1');

		});



	};

	$scope.initCompletionDate = function() {

		if ($scope.workDataProgress.dateCompletion) {

			$scope.workDataCC.ccDate = $scope.workDataProgress.dateCompletion;
		}
	};

	$scope.checkStatus = function(status, statusId) {



		if (status == 'CC Uploaded' && statusId != '12') {
			alert('After CC uploaded you cant be change other status.');
			false;
			$window.location.href = '#manageOngoingWorks';

		}




	};





	$scope.loadTenderDetails = function(workStatusId) {
		$loading.start('sample-1');
		var response = $http.get('fetchWorkTenderAgreement/' + $routeParams.id);



		response.success(function(data, status, headers, config) {
			if (!data.id) {
				$scope.workDataTender.workStatus = 'notTenderIssued';
				$scope.workDataContractor.workStatus = 'notContractorIssued';
				$scope.workDataProgress.workStatus = 'notProgressIssued';
				$scope.workDataTender.tenderUpdated = 'notSelected';
			} else {

				$scope.workDataTender = data;
				$scope.workDataTender.workStatusId = $scope.workDataTender.workStatusId + "";
				//$scope.workDataTender.workStatusId = $scope.workDataTender.workStatusId + "";
				$scope.workDataTender.eTenderNo = Number($scope.workDataTender.eTenderNo);
				$scope.workDataTender.rateStatus = $scope.workDataTender.rateStatus + "";
				$scope.workDataTender.sorYear = $scope.workDataTender.sorYear + "";
				$scope.workDataTender.tenderUpdated = $scope.workDataTender.workStatusId + "";
				$scope.workDataProgress.stipulatedDateCompleted = $scope.workDataTender.workCompletionDate ? $scope.workDataTender.workCompletionDate + "" : "";
				$scope.loadContractorDetails();

				if ($scope.workDataTender.tenderPercentage != null) {
					$scope.workData.isTender = 1;
				} else {
					$scope.workData.isTender = 0;
				}/*
					if ($scope.workDataTender.workStatusId == '3' || $scope.workDataTender.workStatusId == '4' || $scope.workDataTender.workStatusId == '5' || $scope.workDataTender.workStatusId == '6' || $scope.workDataTender.workStatusId == '7' && $scope.workData.isTender == 1) {
						$scope.workDataTender.workStatus = 'notTenderIssued';
						$scope.workDataContractor.workStatus = 'notContractorIssued';
	
	
					}
	
					if ($scope.workDataTender.workStatusId == '8' && $scope.workData.isTender == 0) {
						$scope.workDataTender.tenderUpdatedSix = 'notSelected';
					}*/








			}




			$loading.finish('sample-1');

		});



	};


	$scope.loadContractorDetails = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchContractorDetails/' + $routeParams.id);



		response.success(function(data, status, headers, config) {

			if (!data) {
				$scope.workDataContractor = $scope.workDataContractor || {};
				$scope.workDataContractor.workId = $routeParams.id;
				$scope.workDataContractor.workStatus = 'notContractorIssued';
				$scope.workDataProgress.workStatus = 'notProgressIssued';
				$scope.scrollStatusChange($scope.workDataContractor.workIdCount);
			} else {
				$scope.workDataContractor = data;
				$scope.scrollStatusChange($scope.workDataContractor.workIdCount);
				$scope.loadWorkProgress();

				if ($scope.workDataContractor.workRequestStatusId == 2) {

					document.getElementById("draftCobtn").disabled = true;
				}
			}
			$loading.finish('sample-1');
		});



	};

	$scope.loadCCDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchCCDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {

			if (!data) {
				$scope.workDataCC.responseCC = 'notCCIssued';
				$scope.workDataCC.ccUpdated = 'notSelected';
				// Set workId from route so first-time save has it
				$scope.workDataCC.workId = $routeParams.id;
			} else {

				$scope.workDataCC = data;
				$scope.workDataCC.workNo = $scope.workDataCC.workNo;
				$scope.workDataCC.workStatusId = $scope.workDataCC.workStatusId + "";
				$scope.workDataCC.ccUpdated = $scope.workDataCC.workStatusId + "";
				if ($scope.workDataCC.workStatusId == '13') {
					$scope.workDataCC.responseCC = 'CCIssued';
				}
				if ($scope.workDataCC.workRequestStatusId == 2) {

					document.getElementById("draftCCbtn").disabled = true;
				}


			}

			$loading.finish('sample-1');

		});



	};


	$scope.loadFullWorkDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchFullWorkDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.workDataPrint = data;

			$loading.finish('sample-1');

		});



	};

	$scope.editOngoingWork = function(isValid, tsDocument, asDocument, estDocument, ccDocument,
		workOrderDocument, fileArr, inspectionImageData, mlaDocument, reDocument, firstInstDocument, secInstDocument,
		finalInstDocument, atDocument, soDocument, riaDocument, wcDocument, canDocument, revEstDocument, revASDocument, revTSDocument) {
		//console.log($scope.workData);
		if (($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined)
			&& ($scope.uploadFileData.remarks != '' && $scope.uploadFileData.remarks != undefined)
			&& ($scope.uploadFileData.otherDocDate != '' && $scope.uploadFileData.otherDocDate != undefined) &&
			($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined) && $scope.model != undefined) {
			alert("Please Add Other Document By Clicking Add Button! ");
			return false;
		}

		if ($scope.workData.workStatusId == 16 && asDocument == null) {
			$scope.asDocument = true;
			return false;
		}

		if (!isValid || ($scope.maxSizeErrorAS) || ($scope.fileExtentionErrorAS) || ($scope.maxSizeErrorEst) || ($scope.fileExtentionErrorEst) || ($scope.maxSizeErrorCc) || ($scope.fileExtentionErrorCc) || ($scope.maxSizeErrorTS) || ($scope.fileExtentionErrorTS) || ($scope.maxSizeErrorWorkOrder) || ($scope.fileExtentionErrorWorkOrder) || ($scope.maxSizeErrorOtherDoc) || ($scope.fileExtentionErrorOtherDoc) || ($scope.fileExtentionErrorInspectionImages) || ($scope.maxSizeErrorInspectionImages))
			return false;

		if ((parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.estimatedAmt)) && ($scope.workData.schemeId != 8)) {

			alert("Amount released till date cannot be greater than Estimated Amount. Please check.");
			return false;
		}
		if ((parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.estimatedAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.tsAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.firstInstallAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.secondInstallmentAmt) > parseFloat($scope.workData.asAmt)) && ($scope.workData.schemeId != 8)) {

			alert("All Amounts should be less than or equal to the Administrative Sanction Amount. Please check.");
			return false;
		}

		if (Number.isNaN(parseFloat($scope.workData.finalInstallmentAmt))) {
			var finInstAmt = 0;

		} else {
			var finInstAmt = $scope.workData.finalInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.secondInstallmentAmt))) {
			var secInstAmt = 0;

		} else {
			var secInstAmt = $scope.workData.secondInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.firstInstallAmt))) {
			var firsInstAmt = 0;

		} else {
			var firsInstAmt = $scope.workData.firstInstallAmt;

		}

		if (((parseFloat(firsInstAmt) + parseFloat(secInstAmt) + parseFloat(finInstAmt)) > parseFloat($scope.workData.asAmt)) && ($scope.workData.schemeId != 8)) {

			alert("Total installment amount cannot be greater than Administrative Sanction Amount. Please check.");
			return false;
		}


		if (confirm("Are you sure you want to save the data?")) {

			document.getElementById("submit").disabled = true;

			$loading.start('sample-1');

			var responsePromise = $http.post('editOngoingWork', $scope.workData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if (tsDocument || asDocument || workOrderDocument || estDocument || ccDocument || mlaDocument || reDocument || firstInstDocument || secInstDocument || finalInstDocument || atDocument || soDocument || riaDocument || wcDocument || canDocument || revEstDocument || revASDocument || revTSDocument) {
						$scope.uploadWorkDocument($scope.workData.id, tsDocument, asDocument, estDocument, ccDocument, workOrderDocument, mlaDocument, reDocument, firstInstDocument, secInstDocument, finalInstDocument, atDocument, soDocument, riaDocument, wcDocument, canDocument, revEstDocument, revASDocument, revTSDocument, 'manageOngoingWorks');
					}
					if (fileArr.length > 0) {
						$scope.uploadOtherDoc($scope.workData.id, fileArr, 'manageOngoingWorks');

					} if (inspectionImageData.length > 0) {
						$scope.uploadInspectionImages($scope.workData.id, inspectionImageData, 'manageOngoingWorks');

					} else {
						$window.location.href = '#manageOngoingWorks';
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		}
	};

	$scope.loadLegacyWorkList = function() {

		$loading.start('sample-1');
		fetchLegacyWorkList();
	};

	$scope.fetchImageByCreatedDate = function(createdDate) {
		$loading.start('sample-1');
		var response = $http.get('fetchImagesByDateAndWorkId/' + $routeParams.id + '/' + createdDate);

		response.success(function(data, status, headers, config) {
			$scope.workGroupImagesDate = data;
			//	$scope.workGroupImagesDate.index
			$loading.finish('sample-1');

		});
	};

	$scope.loadOngoingWorkList = function() {

		$loading.start('sample-1');

		fetchWorksList();



	};


	$scope.loadAgencyWiseWorkList = function(mode) {

		$loading.start('sample-1');

		console.log('Mode....' + mode);

		var response = $http.get('fetchWorksListByAgency/' + mode);

		response.success(function(data, status, headers, config) {
			$scope.items = {};
			$scope.items = data;


			$loading.finish('sample-1');
		});






	};






	$scope.loadDrawingDataList = function(mode) {

		$loading.start('sample-1');

		console.log('Mode....' + mode);

		var response = $http.get('fetchWorksListByDrawing/' + mode);

		response.success(function(data, status, headers, config) {
			$scope.items = {};
			$scope.items = data;


			$loading.finish('sample-1');
		});






	};


	$scope.loadHandoverWorkList = function() {

		$loading.start('sample-1');

		fetchHandoverWorksList();



	};

	$scope.loadTSASRevisedList = function(workId) {

		//$loading.start('sample-1');

		fetchTSASRevisedList(workId);



	};

	$scope.reloadStep5Tables = function() {
		var step5WorkId = ($scope.workDataProgress && $scope.workDataProgress.workId)
			|| ($scope.workData && $scope.workData.workId)
			|| $routeParams.id;
		if (!step5WorkId) {
			return;
		}
		$timeout(function() {
			$scope.loadWorkProgressImagesList(step5WorkId);
			$scope.loadExpensesList(step5WorkId);
			$scope.loadWorkFinancialAgencyList(step5WorkId);
		}, 350);
	};

	$scope.refreshWorkProgressAfterSave = function() {
		$scope.successRespondeWS = 'success';
		$scope.loadWorkProgress();
	};

	$scope.loadWorkProgressImagesList = function(workId) {
		if (!workId) {
			return;
		}
		if (typeof window.fetchProgressImagesList !== 'function') {
			console.error('fetchProgressImagesList is not loaded. Check editWorkTables.js');
			return;
		}
		$loading.start('sample-1');
		$timeout(function() {
			try {
				window.fetchProgressImagesList(workId);
			} finally {
				$loading.finish('sample-1');
			}
		}, 0);
	};

	$scope.loadExpensesList = function(workId) {
		if (!workId || typeof window.fetchExpensesDataList !== 'function') {
			return;
		}
		$timeout(function() {
			window.fetchExpensesDataList(workId);
		}, 0);
	};

	$scope.loadWorkFinancialAgencyList = function(workId) {
		if (!workId || typeof window.fetchFinancialAgency !== 'function') {
			return;
		}
		$loading.start('sample-1');
		$timeout(function() {
			try {
				window.fetchFinancialAgency(workId);
			} finally {
				$loading.finish('sample-1');
			}
		}, 0);
	};


	$scope.loadASWorksList = function() {

		$loading.start('sample-1');
		fetchASWorksList();
	};

	$scope.loadSelectedASWorksList = function() {

		$loading.start('sample-1');
		fetchSelectedASWorksList();
	};

	$scope.loadAllParentAS = function() {

		$loading.start('sample-1');
		fetchAllParentAS();
	};



	$scope.saveSelectedAdministrativeSanction = function(workIds) {

		$scope.workData = {};
		//if (!isValid)
		//	return false;	

		if (confirm("Please Verify All Details Before Final Submit.")) {
			$loading.start('sample-1');



			if (localStorage.getItem('generate_as_data') != null) {
				$scope.workData = JSON.parse(localStorage.getItem('generate_as_data'));
				localStorage.removeItem('generate_as_data');
			}
			$scope.workData.workStatusId = 25;
			$scope.workData.workIds = workIds;
			//alert($scope.workData.workIds);
			if ($routeParams.parentAsId != null)
				$scope.workData.parentAdminSanctionId = $routeParams.parentAsId;

			$loading.start('sample-1');

			var responsePromise = $http.post('saveSelectedAdministrativeSanction', $scope.workData);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);

					$window.location.href = '#printSelectedAS/' + data.id;
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


	$scope.loadPrintDataForAsMain = function() {
		$loading.start('sample-1');
		if ($routeParams.workIds != null) {
			var response = $http.get('fetchWorkDetForPrintMainAs/' + $routeParams.workIds);
		} else if ($routeParams.parentAsId != null) {
			var response = $http.get('fetchWorkDetForPrintMainAs/' + $routeParams.parentAsId);
		}
		response.success(function(data, status, headers, config) {
			$scope.workData = {};
			$scope.workList = data;
			for (var i = 0; i < $scope.workList.length; i++) {
				$scope.workList.loggedInUserEmailId = $scope.workList[0].loggedInUserEmailId;
				$scope.workList.phoneNo = $scope.workList[0].phoneNo;
				$scope.workList.districtName = $scope.workList[0].districtName;
				$scope.workList.financialYear = $scope.workList[0].financialYear;
				$scope.workList.asGeneratedOn = $scope.workList[0].asGeneratedOn;
				$scope.workList.lctype = $scope.workList[0].lctype;
				$scope.workList.workTypeId = $scope.workList[0].workTypeId;
				$scope.workData.asIssuingAuthorityId = $scope.workList[0].asIssuingAuthorityId + "";
				$scope.workData.asIssuingAuthority = $scope.workList[0].asIssuingAuthority;
				$scope.workData.asIssuingAuthorityName = $scope.workList[0].asIssuingAuthorityName;
				$scope.workList.asUniqueDispatchId = $scope.workList[0].asUniqueDispatchId;
				if ($scope.workList[0].asNo != null) {
					$scope.workList.asNo = $scope.workList[0].asNo;
				} else {
					$scope.workList.asNo = $scope.workList[0].asUniqueDispatchId + "/जि.यो.सां/वि.नि./" + $scope.workList[0].financialYear + "/" + $scope.workList[0].districtName;
				}
				$scope.workList.asDate = $scope.workList[0].asDate;
				$scope.workData.specialCondition = $scope.workList[0].specialCondition;
				$scope.workData.lcName = $scope.workList[0].lcName;
				$scope.workData.asEndorsementAuthorityId = $scope.workList[0].asEndorsementAuthorityId + "";
				$scope.workData.asEndorsementAuthorityName = $scope.workList[0].asEndorsementAuthorityName;
				$scope.workData.asEndorsementAuthority = $scope.workList[0].asEndorsementAuthority;
				$scope.workData.parentAdminSanctionId = $scope.workList[0].parentAdminSanctionId;
				$scope.workList.districtNameH = $scope.workList[0].districtNameH;
				$scope.workData.workIds = $scope.workList[0].workIds;
			}

			$loading.finish('sample-1');
		});
	};

	$scope.displayPreviewGenerateAdministrativeSanctionInputForm = function(workIds) {
		$scope.workData = {};

		if (confirm("Are you sure to Generate Preview of AS File?")) {
			$loading.start('sample-1');
			$scope.workData.workIds = workIds;

			$window.location.href = '#printPreviewGenerateAS/' + workIds;
		}
	};

	$scope.displayPreviewSelectedAdministrativeSanction = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure to Generate Preview of AS File?")) {
			$loading.start('sample-1');

			if ($scope.saveAsDraft == true) {
				$scope.workData.workStatusId = 26;
			}
			if ($scope.workData.parentAdminSanctionId == null)
				$scope.workData.workIds = $routeParams.workIds;
			localStorage.setItem("generate_as_data", JSON.stringify($scope.workData));


			var responsePromise = $http.post('saveSelectedAdministrativeSanction', $scope.workData);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if ($scope.workData.workStatusId == 26) {
						$window.location.href = '#printPreviewSelectedAS/' + data.id;
					}
				}
				$loading.finish('sample-1');
			});
		}
	};
	$scope.loadPrintPreviewDataForAsMain = function() {
		$loading.start('sample-1');
		$scope.workIds = [];
		$scope.workIds = $routeParams.workIds;
		var response = $http.get('fetchWorkDetForPrintMainAs/' + $routeParams.parentAsId);
		response.success(function(data, status, headers, config) {
			$scope.workList = data;
			for (var i = 0; i < $scope.workList.length; i++) {

				$scope.workList.loggedInUserEmailId = $scope.workList[0].loggedInUserEmailId;
				$scope.workList.phoneNo = $scope.workList[0].phoneNo;
				$scope.workList.districtName = $scope.workList[0].districtName;
				$scope.workList.districtNameH = $scope.workList[0].districtNameH;
				$scope.workList.financialYear = $scope.workList[0].financialYear;
				$scope.workList.asGeneratedOn = $scope.workList[0].asGeneratedOn;
				$scope.workList.lctype = $scope.workList[0].lctype;
				$scope.workList.workTypeId = $scope.workList[0].workTypeId;
				$scope.workList.asUniqueDispatchId = parseInt($scope.workList[0].asUniqueDispatchId);
				if ($scope.workList[0].asNo != null) {
					$scope.workList.asNo = $scope.workList[0].asNo;
				} else {
					$scope.workList.asNo = $scope.workList[0].asUniqueDispatchId + "/जि.यो.सां/वि.नि./" + $scope.workList[0].financialYear + "/" + $scope.workList[0].districtName;
				}
				$scope.workList.asDate = $scope.workList[0].asDate;
				$scope.workList.lcName = $scope.workList[0].lcName;

				$scope.workList.workIds = $scope.workList[0].workIds;
				if (localStorage.getItem('generate_as_data') != null) {
					$scope.workData = JSON.parse(localStorage.getItem('generate_as_data'));

					$scope.workData.specialCondition = $scope.workData.specialCondition;

					if ($scope.workData.asIssuingAuthorityId != null) {
						$scope.loadIssuingAuthorityById($scope.workData.asIssuingAuthorityId);
					}

					if ($scope.workData.asEndorsementAuthorityId != null) {
						$scope.loadEndorsementAuthorityById($scope.workData.asEndorsementAuthorityId);
					}
				} else {
					$scope.workData = {};
				}

			}



			$loading.finish('sample-1');
		});
	};

	$scope.loadPrintPreviewGenerateASDataForAsMain = function() {
		$loading.start('sample-1');
		$scope.workIds = [];
		$scope.workIds = $routeParams.workIds;
		var response = $http.get('fetchWorkDetForPrintPreviewMainAs/' + $routeParams.workIds);
		response.success(function(data, status, headers, config) {
			$scope.workList = data;
			for (var i = 0; i < $scope.workList.length; i++) {
				$scope.workList.loggedInUserEmailId = $scope.workList[0].loggedInUserEmailId;
				$scope.workList.phoneNo = $scope.workList[0].phoneNo;
				$scope.workList.districtName = $scope.workList[0].districtName;
				$scope.workList.districtNameH = $scope.workList[0].districtNameH;
				$scope.workList.financialYear = $scope.workList[0].financialYear;
				$scope.workList.asGeneratedOn = $scope.workList[0].asGeneratedOn;
				$scope.workList.lctype = $scope.workList[0].lctype;
				$scope.workList.workTypeId = $scope.workList[0].workTypeId;
				$scope.workList.asUniqueDispatchId = parseInt($scope.workList[0].asUniqueDispatchId);
				if ($scope.workList[0].asNo != null) {
					$scope.workList.asNo = $scope.workList[0].asNo;
				} else {
					$scope.workList.asNo = $scope.workList[0].asUniqueDispatchId + "/जि.यो.सां/वि.नि./" + $scope.workList[0].financialYear + "/" + $scope.workList[0].districtName;
				}
				$scope.workList.asDate = $scope.workList[0].asDate;
				$scope.workList.lcName = $scope.workList[0].lcName;


				$scope.workList.workIds = $scope.workIds;
			}

			$loading.finish('sample-1');
		});
	};




	$scope.searchFunction = function() {
		$loading.start('sample-1');
		reDraw();
	};

	$scope.resetFunction = function() {
		$loading.start('sample-1');

		$timeout(function() {
			reDraw();
		}, 0);
	};

	$scope.deleteWork = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteWork/' + id);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageOngoingWorks';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$window.location.href = '#manageOngoingWorks';
				}
				$loading.finish('sample-1');
			});
		} else {

			return false;
		}
	};

	//

	$scope.deleteFile = function(id) {

		if (confirm("Are you sure to delete this File?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteFile/' + id);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$route.reload();
					$window.location.reload();
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 5000);
					$route.reload();
					$window.location.reload();
				}
				$loading.finish('sample-1');
			});
		} else {
			//$window.location.href = '#manageentrepreneur';
			return false;
		}
	};

	$scope.getCurrentFiscalYear = function() {
		//get current date
		var today = new Date();

		//get current month
		var curMonth = today.getMonth();

		var fiscalYr = "";
		if (curMonth > 3) { //
			var nextYr1 = (today.getFullYear() + 1).toString();
			//fiscalYr = today.getFullYear().toString() + "-" + nextYr1.charAt(2) + nextYr1.charAt(3);
			fiscalYr = today.getFullYear().toString() + "-" + nextYr1;
		} else {
			var nextYr2 = today.getFullYear().toString();
			//fiscalYr = (today.getFullYear() - 1).toString() + "-" + nextYr2.charAt(2) + nextYr2.charAt(3);
			fiscalYr = (today.getFullYear() - 1).toString() + "-" + nextYr2;
		}

		$scope.financialYear = fiscalYr;

		$scope.currentFinancialYear = fiscalYr;

		$scope.prevFinancialYear = (today.getFullYear() - 1).toString() + "-" + today.getFullYear().toString();
	};

	$scope.encryptFuncAngular = function(param) {
		return encryptFunc(param);
	}

	$scope.fetchLoggedInUser = function() {

		$loading.start('sample-1');

		$http.get('fetchLoggedInUser').then(function(response) {
			$scope.userData = response.data;
			if ($scope.userData.divisionId) {
				$scope.divisionId = $scope.userData.divisionId;
			}
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkTypes = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchWorkTypes');
		response.success(function(data, status, headers, config) {
			$scope.workTypes = data;
			
			var y = $('#wtId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#workType1');

			}, 1000);
			$loading.finish('sample-1');
		});
	};


	$scope.loadWorkSubtype = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkSubTypes');
		response.success(function(data, status, headers, config) {
			$scope.workSubTypes = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadWorkCategory = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkCategory');
		response.success(function(data, status, headers, config) {
			$scope.workCategories = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadBlock = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchBlock');
		response.success(function(data, status, headers, config) {
			$scope.blocks = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadlegislativeCont = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchlegislativeCont');
		response.success(function(data, status, headers, config) {
			$scope.legislativeConts = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadSubCategories = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSubCategories');
		response.success(function(data, status, headers, config) {
			$scope.subCategories = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadSchemes = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemes');
		response.success(function(data, status, headers, config) {
			$scope.schemes = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadSchemesByDesignation = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemesByDesignation');
		response.success(function(data, status, headers, config) {
			$scope.schemes = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadSchemesByDesignationForDpo = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemesByDesignation');
		response.success(function(data, status, headers, config) {
			$scope.schemes = data;
			$timeout(function() {
				$scope.workData.schemeId = "8";
			}, 50);
			$loading.finish('sample-1');
		});
	};

	$scope.loadFinancialYears = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchFinancialYear');
		response.success(function(data, status, headers, config) {
			$scope.financialYears = data;
			
				var y = $('#fyId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#financialYear1');

			}, 1000);

			$loading.finish('sample-1');
		});
	};

	$scope.loadSorYears = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchSorYear');
		response.success(function(data, status, headers, config) {
			$scope.sorYears = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadYears = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchYear');
		response.success(function(data, status, headers, config) {
			$scope.Years = data;
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

	$scope.loadImplementationAgencyType = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchImplAgencyType');
		response.success(function(data, status, headers, config) {
			$scope.implementationAgencyTypes = data;
			//			for(i = 0 ; i < $scope.implementationAgencyTypes.length; i++) {
			//				 var implAgencyTypes={};
			//				 implAgencyTypes.id = $scope.implementationAgencyTypes[i].id+'';
			//				 $scope.implementationAgencyTypes[i].push(implAgencyTypes);
			//				
			//				}
			$loading.finish('sample-1');
		});
	};

	$scope.loadImplAgenciesByType = function(implAgencyTypeId, districtName) {

		if (implAgencyTypeId && implAgencyTypeId != null && implAgencyTypeId != 'null' && implAgencyTypeId != '5') {
			$loading.start('sample-1');
			var response = $http.get('fetchImplAgenciesByType/' + implAgencyTypeId + '/' + districtName);
			response.success(function(data, status, headers, config) {
				$scope.implementationAgencies = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadImplAgenciesByTypeAndJP = function(implAgencyTypeId, districtName, jpId) {

		if (implAgencyTypeId && implAgencyTypeId != null && implAgencyTypeId != 'null' && jpId != null) {
			$loading.start('sample-1');
			var response = $http.get('fetchImplAgenciesByTypeAndJP/' + implAgencyTypeId + '/' + districtName + '/' + jpId);
			response.success(function(data, status, headers, config) {
				$scope.implementationAgencies = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadImplAgenciesByTypeRev = function(implAgencyTypeId, districtName) {

		if (implAgencyTypeId && implAgencyTypeId != null && implAgencyTypeId != 'null' && implAgencyTypeId != '5') {
			$loading.start('sample-1');
			var response = $http.get('fetchImplAgenciesByType/' + implAgencyTypeId + '/' + districtName);
			response.success(function(data, status, headers, config) {
				$scope.implementationAgenciesRev = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadImplAgenciesByTypeAndJPRev = function(implAgencyTypeId, districtName, jpId) {

		if (implAgencyTypeId && implAgencyTypeId != null && implAgencyTypeId != 'null' && jpId != null) {
			$loading.start('sample-1');
			var response = $http.get('fetchImplAgenciesByTypeAndJP/' + implAgencyTypeId + '/' + districtName + '/' + jpId);
			response.success(function(data, status, headers, config) {
				$scope.implementationAgenciesRev = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.assignIA = function(implAgencyTypeId, implementationAgencyId) {

		if (implAgencyTypeId == 3) {

			$scope.workData.implementationAgency = 'Zila Panchayat ' + $scope.workData.districtName;
		} else if (implAgencyTypeId == 4 || implAgencyTypeId == 5 || implAgencyTypeId == 6 || implAgencyTypeId == 7 || implAgencyTypeId == 8) {

			$scope.implementationAgencyObj = $scope.implementationAgencies.find(x => x.implementationAgencyId == implementationAgencyId);
			$scope.workData.implementationAgency = $scope.implementationAgencyObj.implementationAgencyNameE;
		}
	};

	$scope.assignIARev = function(implAgencyTypeId, implementationAgencyId) {

		if (implAgencyTypeId == 3) {

			$scope.workData.implementationAgencyRev = 'Zila Panchayat ' + $scope.workData.districtName;
		} else if (implAgencyTypeId == 4 || implAgencyTypeId == 5 || implAgencyTypeId == 6 || implAgencyTypeId == 7 || implAgencyTypeId == 8) {

			$scope.implementationAgencyObj = $scope.implementationAgenciesRev.find(x => x.implementationAgencyId == implementationAgencyId);
			$scope.workData.implementationAgencyRev = $scope.implementationAgencyObj.implementationAgencyNameE;
		}
	};

	$scope.assignIASubOffice = function(implAgencyTypeId, implementationAgencyId) {

		if (implAgencyTypeId == 3) {

			$scope.subOfficeData.implementationAgency = 'Zila Panchayat ' + $scope.subOfficeData.districtName;
		} else //if(implAgencyTypeId==4 || implAgencyTypeId==5 || implAgencyTypeId==6 || implAgencyTypeId==7 || implAgencyTypeId==8)
		{

			$scope.implementationAgencyObj = $scope.implementationAgencies.find(x => x.implementationAgencyId == implementationAgencyId);
			$scope.subOfficeData.implementationAgency = $scope.implementationAgencyObj.implementationAgencyNameE;
		}
	};

	$scope.loadSubEnggByIAAndDistrict = function(implementationAgencyId, districtName) {

		if (implementationAgencyId && implementationAgencyId != null && implementationAgencyId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchSubEnggByImplAgencyIdAndDistrict/' + implementationAgencyId + '/' + districtName);
			response.success(function(data, status, headers, config) {
				$scope.subEngg = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadSubEnggByIA = function(implementationAgencyId) {

		if (implementationAgencyId && implementationAgencyId != null && implementationAgencyId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchSubEnggByImplAgencyId/' + implementationAgencyId);
			response.success(function(data, status, headers, config) {
				$scope.subEngg = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWorkStatus = function() {
		if ($scope.workData && $scope.workData.scheme != 'DHS') {
			$loading.start('sample-1');
			var response = $http.get('fetchWorkStatus');
			response.success(function(data, status, headers, config) {
				$scope.workStatus = data;

				$loading.finish('sample-1');

			});
		}
	};





	$scope.loadWorkStatusByWorkType = function(workTypeId) {

		if ($scope.workData.schemeId != 8 && workTypeId != undefined) {
			$scope.workData.workStatusId = '';
			$loading.start('sample-1');
			var response = $http.get('fetchWorkStatusByWorkType/' + workTypeId);
			response.success(function(data, status, headers, config) {
				$scope.workStatus = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWorkCategory = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkCategory');
		response.success(function(data, status, headers, config) {
			$scope.workCategories = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkCategoryByWorkType = function(workTypeId) {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkCategoryByWorkType/' + workTypeId);
		response.success(function(data, status, headers, config) {
			$scope.workCategories = data;
			// if($scope.workCategories.length ==1){
			//     $scope.workData.workCategoryId = $scope.workCategories[1];

			// }
			// console.log('workCategories.length..'+$scope.workCategories.length);
			$loading.finish('sample-1');
		});
	};



	$scope.setRemove = function(workStatusId) {
		if (workStatusId == '13') {
			for (i = 0; i < $scope.workProStatus.length; i++) {
				if ($scope.items[i].name == 'Not Started' || $scope.items[i].name == 'In-Progress' || $scope.items[i].name == 'Completed') {
					$scope.items.shift();
				}
			}
		}

	};





	$scope.loadDistrictByDivision = function(divisionId) {
		$loading.start('sample-1');
		var response = $http.get('fetchDistrictByDivision/' + divisionId);
		response.success(function(data, status, headers, config) {
			$scope.districts = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadBlockByDistrictID = function(districtCode) {

		$loading.start('sample-1');
		var response = $http.get('fetchBlocksByDistrict/' + districtCode);
		response.success(function(data, status, headers, config) {
			$scope.blocks = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadGramPanchayatByBlockID = function(districtCode, blockCode) {
		$loading.start('sample-1');
		var response = $http.get('fetchGramPanchayatByBlockCode/' + districtCode + '/' + blockCode);
		response.success(function(data, status, headers, config) {
			$scope.grams = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadLegislativeByDistrict = function(districtId) {

		$loading.start('sample-1');
		var response = $http.get('fetchLegislativeByDistrict/' + districtId);
		response.success(function(data, status, headers, config) {
			$scope.legislativeConts = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkHeads = function(workPriorityId) {

		if (workPriorityId && workPriorityId != null && workPriorityId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchWorkHeadByPriorityType/' + workPriorityId);
			response.success(function(data, status, headers, config) {
				$scope.workHeads = data;
				$loading.finish('sample-1');
			});
		}
	};


	$scope.loadDivisions = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDivisions');
		response.success(function(data, status, headers, config) {
			$scope.divisions = data;
			$scope.workData.divisionId = '3';
			$loading.finish('sample-1');
		});
	};

	$scope.loadDivisionsWork = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDivisions');
		response.success(function(data, status, headers, config) {
			$scope.divisions = data;
			$scope.workData.divisionId = '3';
			$loading.finish('sample-1');
		});
	};


	$scope.loadDivisionsByDivisionId = function() {
		// var divisionId =$[[{division}]];
		$loading.start('sample-1');
		console.log('DivisionID...' + $scope.division);
		var response = $http.get('fetchDivisions');
		response.success(function(data, status, headers, config) {
			$scope.divisions = data;
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

	// Ensure workData is initialized

	$scope.loadDistrictsWork = function() {
		$loading.start('sample-1');
		$http.get('fetchDistricts').success(function(data) {
			$scope.districts = data;
			$scope.workData.districtCode = '461'; // Set districtCode to 461
			$scope.workData.districtId = '49';
			$scope.loadBlockByDistrictID(461);
			$loading.finish('sample-1');
		}).error(function() {
			$loading.finish('sample-1');
			console.error('Error fetching districts');
		});
	};




	$scope.loadAgencies = function() {

		$loading.start('sample-1');
		var responseOfficeType = $http.get('fetchConstructionAgencys');
		responseOfficeType.success(function(data, status, headers, config) {
			$scope.agencies = data;
			
			var y = $('#iaId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#implementationAgency');

			}, 1000);
			
			$loading.finish('sample-1');
		});
	};






	// Load blocks by districtId (for reports and manage works pages)
	$scope.loadBlocksByDistrict = function() {
		// Hardcoded to load Anuppur blocks - using districtCode "461"
		var districtCode = "461";
		
		$loading.start('sample-1');
		$http.get(getBaseUrl() + "/systemAdmin/fetchBlocksByDistrict/" + districtCode)
			.then(function(response) {
				$scope.blocks = response.data;
				console.log("Blocks loaded:", $scope.blocks);
				
				var y = $('#blId');
				y.addClass('btn-selected');
				
				// Refresh selectpicker after data loads
				setTimeout(function() {
					window.safeSelectpickerRefresh('#blockId');
					$loading.finish('sample-1');
				}, 1000);
			})
			.catch(function(error) {
				console.error("Block API Error:", error);
				$scope.blocks = [];
				$loading.finish('sample-1');
			});
	};

	// Watch for district change and reload blocks
	$scope.$watch('workData.districtId', function(newVal, oldVal) {
		if (newVal && newVal !== oldVal) {
			$scope.loadBlocksByDistrict();
		}
	});

	$scope.loadGramPanchayatByBlockId = function(blockId) {
		if (blockId && blockId != null && blockId != 'null') {
			$loading.start('sample-1');

			var response = $http.get('fetchGramPanchayatByBlockId/' + blockId);
			response.success(function(data, status, headers, config) {
				$scope.gramPanchayats = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadVillageByGPId = function(gpId) {
		if (gpId && gpId != null && gpId != 'null') {
			$loading.start('sample-1');

			var response = $http.get('fetchVillageByGramPanchayatId/' + gpId);
			response.success(function(data, status, headers, config) {
				$scope.villages = data;
				$loading.finish('sample-1');
			});
		}
	};

	/*$scope.loadPCs = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchParliamentConstituency');
		response.success(function(data, status, headers, config) {
			$scope.pcs = data;
			$loading.finish('sample-1');
		});
	};*/

	$scope.loadLCs = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchLegislativeConstituency');
		response.success(function(data, status, headers, config) {
			$scope.lcs = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadLCsByDistrict = function(districtName) {
		if (districtName != null) {
			$loading.start('sample-1');
			var response = $http.get('fetchLCsByDistrictName/' + districtName);
			response.success(function(data, status, headers, config) {
				$scope.lcs = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadOtherLCsByOtherDistrict = function(districtName) {
		if (districtName != null) {
			$loading.start('sample-1');
			var response = $http.get('fetchLCsByDistrictName/' + districtName);
			response.success(function(data, status, headers, config) {
				$scope.OtherLcs = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadPCsByDistrict = function(districtName) {

		$loading.start('sample-1');
		var response = $http.get('fetchPCsByDistrictName/' + districtName);
		response.success(function(data, status, headers, config) {
			$scope.pcs = data;
			$loading.finish('sample-1');
		});
	};

	$('#lightbox').on('shown.bs.modal', function() {
		var $lightbox = $('#lightbox');

		var $img = $lightbox.find('img');
		/*$lightbox.find('.modal-dialog').css({'width': $img.width()});*/
		$lightbox.find('.close').removeClass('hidden');
	});

	$scope.loadWorkImages = function(id) {

		$loading.start('sample-1');

		var response = $http.get('fetchWorkImages/' + id);

		response.success(function(data, status, headers, config) {
			$scope.workData.img1String = data.img1String;
			$scope.workData.img2String = data.img2String;
			$scope.workData.img3String = data.img3String;
			$scope.workData.img4String = data.img4String;
			$loading.finish('sample-1');
		});
	};

	$scope.addLegacyWorkData = function(isValid, tsDocument, asDocument, workOrderDocument, fileArr) {
		var mlaDocument = null;

		if (($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined)
			&& ($scope.uploadFileData.remarks != '' && $scope.uploadFileData.remarks != undefined)
			&& ($scope.uploadFileData.otherDocDate != '' && $scope.uploadFileData.otherDocDate != undefined) &&
			($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined) && $scope.model != undefined) {
			alert("Please Add Other Document By Clicking Add Button! ");
			return false;
		}

		if (!isValid || ($scope.maxSizeErrorAS) || ($scope.fileExtentionErrorAS) || ($scope.maxSizeErrorTS) || ($scope.fileExtentionErrorTS) || ($scope.maxSizeErrorWorkOrder) || ($scope.fileExtentionErrorWorkOrder) || ($scope.maxSizeErrorOtherDoc) || ($scope.fileExtentionErrorOtherDoc))
			return false;
		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.estimatedAmt)) {

			alert("Amount released till date cannot be greater than Estimated Amount. Please check.");
			return false;
		}

		if (Number.isNaN(parseFloat($scope.workData.finalInstallmentAmt))) {
			var finInstAmt = 0;

		} else {
			var finInstAmt = $scope.workData.finalInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.secondInstallmentAmt))) {
			var secInstAmt = 0;

		} else {
			var secInstAmt = $scope.workData.secondInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.firstInstallAmt))) {
			var firsInstAmt = 0;

		} else {
			var firsInstAmt = $scope.workData.firstInstallAmt;

		}

		if ((parseFloat(firsInstAmt) + parseFloat(secInstAmt) + parseFloat(finInstAmt)) > parseFloat($scope.workData.asAmt)) {

			alert("Total installment amount cannot be greater than Administrative Sanction Amount. Please check.");
			return false;
		}

		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.estimatedAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.tsAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.firstInstallAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.secondInstallmentAmt) > parseFloat($scope.workData.asAmt)) {

			alert("All Amounts should be less than or equal to the Administrative Sanction Amount. Please check.");
			return false;
		}

		if (confirm("Are you sure you want to save the data?")) {

			document.getElementById("submit").disabled = true;

			$loading.start('sample-1');

			var responsePromise = $http.post('addLegacyWorkData', $scope.workData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if (tsDocument || asDocument || workOrderDocument || estDocument || ccDocument || mlaDocument) {
						$scope.uploadWorkDocument(data.id, tsDocument, asDocument, estDocument, ccDocument, workOrderDocument, mlaDocument, null, null, null, null, null, null, null, null, null, null, null, 'manageLegacyData');
					} if (fileArr.length > 0) {
						$scope.uploadOtherDoc(data.id, fileArr, 'manageLegacyData');

					} else {
						$window.location.href = '#manageLegacyData';
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		}
	};

	$scope.loadWorkForm = function() {

		$http.get('fetchLoggedInUser').then(function(response) {

			var user = response.data;
			//$scope.workData = {};
			$loading.start('sample-1');
			if (localStorage.getItem('filters_data') != null) {
				$scope.workData = JSON.parse(localStorage.getItem('filters_data'));
				localStorage.removeItem('filters_data');
				//localStorage.clear();
			} else {
				$scope.workData = {};
			}
			$scope.workData.districtName = (user.districtName == '-' ? "" : user.districtName);
			if (user.divisionId != null && user.divisionId != 0) {
				$scope.loadDistrictsByDivision(user.divisionId);
			} else {
				$scope.loadDistricts();
				$scope.loadDivisions();
				$scope.loadAgencies();
			}
			$scope.loadBlocksByDistrict($scope.workData.districtName);
			$scope.loadLCsByDistrict($scope.workData.districtName);
			$scope.loadPCsByDistrict($scope.workData.districtName);
			$scope.loadULB($scope.workData.districtName);
			$scope.workData.schemeId = '8';
			$scope.workData.rType = '0';
			$scope.workData.isRecommendedAgency = '0';
			$scope.workData.workStatusId = '13';
		});
	}

	$scope.loadDistrictsByDivision = function(divisionId) {
		if (divisionId != null) {
			$loading.start('sample-1');
			var response = $http.get('fetchDistrictsByDivision/' + divisionId);
			response.success(function(data, status, headers, config) {
				$scope.districts = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWorkFormForFund = function() {

		$http.get('fetchLoggedInUser').then(function(response) {

			var user = response.data;
			$scope.fundData = {};
			$scope.fundData.districtId = user.districtId + "";
			$scope.fundData.districtName = user.districtName + "";
			$scope.loadLCsByDistrictIdC($scope.fundData.districtId);
		});
	}

	$scope.editLegacyData = function(isValid, tsDocument, asDocument, workOrderDocument, fileArr) {
		var mlaDocument = null;
		if (($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined)
			&& ($scope.uploadFileData.remarks != '' && $scope.uploadFileData.remarks != undefined)
			&& ($scope.uploadFileData.otherDocDate != '' && $scope.uploadFileData.otherDocDate != undefined) &&
			($scope.uploadFileData.noOfDocs != '' && $scope.uploadFileData.noOfDocs != undefined) && $scope.model != undefined) {
			alert("Please Add Other Document By Clicking Add Button! ");
			return false;
		}

		if (!isValid || ($scope.maxSizeErrorAS) || ($scope.fileExtentionErrorAS) || ($scope.maxSizeErrorTS) || ($scope.fileExtentionErrorTS) || ($scope.maxSizeErrorWorkOrder) || ($scope.fileExtentionErrorWorkOrder) || ($scope.maxSizeErrorOtherDoc) || ($scope.fileExtentionErrorOtherDoc))
			return false;

		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.estimatedAmt)) {
			alert("Amount released till date cannot be greater than Estimated Amount. Please check.");
			return false;
		}
		if (parseFloat($scope.workData.amtReleasedTillDate) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.estimatedAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.tsAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.firstInstallAmt) > parseFloat($scope.workData.asAmt)
			|| parseFloat($scope.workData.secondInstallmentAmt) > parseFloat($scope.workData.asAmt)) {

			alert("All Amounts should be less than or equal to the Administrative Sanction Amount. Please check.");
			return false;
		}

		if (Number.isNaN(parseFloat($scope.workData.finalInstallmentAmt))) {
			var finInstAmt = 0;

		} else {
			var finInstAmt = $scope.workData.finalInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.secondInstallmentAmt))) {
			var secInstAmt = 0;

		} else {
			var secInstAmt = $scope.workData.secondInstallmentAmt;

		}

		if (Number.isNaN(parseFloat($scope.workData.firstInstallAmt))) {
			var firsInstAmt = 0;

		} else {
			var firsInstAmt = $scope.workData.firstInstallAmt;

		}

		if ((parseFloat(firsInstAmt) + parseFloat(secInstAmt) + parseFloat(finInstAmt)) > parseFloat($scope.workData.asAmt)) {

			alert("Total installment amount cannot be greater than Administrative Sanction Amount. Please check.");
			return false;
		}

		if (confirm("Are you sure you want to save the data?")) {

			document.getElementById("submit").disabled = true;

			$loading.start('sample-1');

			var responsePromise = $http.post('editLegacyData', $scope.workData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					if (tsDocument || asDocument || workOrderDocument || estDocument || ccDocument || mlaDocument) {
						$scope.uploadWorkDocument($scope.workData.id, tsDocument, asDocument, estDocument, ccDocument, workOrderDocument, mlaDocument, null, null, null, null, null, null, null, null, null, null, null, 'manageLegacyData');
					}
					if (fileArr.length > 0) {
						$scope.uploadOtherDoc($scope.workData.id, fileArr, 'manageLegacyData');

					}
					else {
						$window.location.href = '#manageLegacyData';
					}
				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		}
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

	$scope.loadImplAgencyList = function() {

		$loading.start('sample-1');
		fetchImplAgencyList();
	};

	$scope.loadSchemeList = function() {

		$loading.start('sample-1');
		fetchSchemeList();
	};

	$scope.loadSorList = function() {

		$loading.start('sample-1');
		fetchSorList();
	};

	$scope.loadHeadList = function() {

		$loading.start('sample-1');
		fetchHeadList();
	};

	$scope.addImplAgency = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addImplAgency', $scope.implAgencyData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageImplAgency';
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


	$scope.addHead = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addHead', $scope.headData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageHead';
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
					$window.location.href = '#manageScheme';
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


	$scope.addSor = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addSor', $scope.sorData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSor';
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

	$scope.addSdr = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			console.log($scope.sdrData);
			var responsePromise = $http.post('addSdr', $scope.sdrData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSdr';
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


	$scope.editImplAgency = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.implAgencyData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editImplAgency', $scope.implAgencyData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageImplAgency';
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


	$scope.editHead = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.implAgencyData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editHead', $scope.headData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageHead';
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


	$scope.editScheme = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.implAgencyData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editScheme', $scope.schemeData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageScheme';
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


	$scope.editSor = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.implAgencyData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editSor', $scope.sorData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSor';
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

	$scope.deleteImplAgency = function(implAgencyId) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteImplAgency/' + implAgencyId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageImplAgency';
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


	$scope.deleteHead = function(workHeadId) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteHead/' + workHeadId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageHead';
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
	$scope.deleteRemark = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteRemarks/' + id);
			responsePromise.then(function(response) {
				var data = response.data;
				$rootScope.responseObject = data;
				$window.location.reload();
				$scope.loadDmRemarksForWorkId();
				$loading.finish('sample-1');
			}, function(error) {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Error deleting remark";
				console.error("Error deleting remark:", error);
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

$scope.deleteDepartmentRemark = function(id) {

		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteDepartmentRemarks/' + id);
			responsePromise.then(function(response) {
				var data = response.data;
				$rootScope.responseObject = data;
				$window.location.reload();
				$scope.loadDepartmentRemarksForWorkId();
				$loading.finish('sample-1');
			}, function(error) {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Error deleting department remark";
				console.error("Error deleting department remark:", error);
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};


	$scope.deleteScheme = function(workHeadId) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteScheme/' + workHeadId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageScheme';
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


	$scope.deleteSor = function(Id) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteSor/' + Id);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSor';
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


	$scope.loadImplAgencyDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchImplAgencyDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.implAgencyData = data;
			$scope.implAgencyData.implAgencyTypeId = $scope.implAgencyData.implAgencyTypeId + '';
			$loading.finish('sample-1');
		});
	};

	$scope.loadHeadDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchHeadDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.headData = data;

			$loading.finish('sample-1');
		});
	};

	$scope.loadSchemeDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemeDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.schemeData = data;

			$loading.finish('sample-1');
		});
	};

	$scope.loadSorDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSorDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.sorData = data;

			$loading.finish('sample-1');
		});
	};

	$scope.loadSubEnggList = function() {

		$loading.start('sample-1');
		fetchSubEnggList();
	};

	$scope.addSubEngg = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addSubEngg', $scope.subEnggData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSubEngg';
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

	$scope.editSubEngg = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.subEnggData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editSubEngg', $scope.subEnggData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSubEngg';
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

	$scope.deleteSubEngg = function(subEnggId) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteSubEngg/' + subEnggId);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSubEngg';
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

	$scope.loadSubEnggDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSubEnggDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.subEnggData = data;
			$scope.subEnggData.implAgencyId = $scope.subEnggData.implAgencyId + "";
			$scope.subEnggData.districtId = $scope.subEnggData.districtId + "";
			$loading.finish('sample-1');
		});
	};

	$scope.loadMLAList = function(lcId) {
		if (lcId && lcId != null && lcId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchMLAByLCId/' + lcId);
			response.success(function(data, status, headers, config) {
				$scope.mlas = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadMLAs = function(lcId, mlaHouse) {
		if ((lcId && lcId != null && lcId != 'null') && mlaHouse && mlaHouse != null && mlaHouse != 'null') {
			/*var lc;
			 if(otherLcId && otherLcId!=null && otherLcId!='null'){
				lc = otherLcId;
			}else{
				lc = lcId;
			}*/
			$loading.start('sample-1');
			var response = $http.get('fetchMLAByLCId/' + lcId + '/' + mlaHouse);
			response.success(function(data, status, headers, config) {
				$scope.mlas = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadMLAsByDistrictName = function(districtName, mlaHouse) {
		if ((districtName && districtName != null && districtName != 'null') && mlaHouse && mlaHouse != null && mlaHouse != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchMLAByDistrictName/' + districtName + '/' + mlaHouse);
			response.success(function(data, status, headers, config) {
				$scope.mlas = data;
				$loading.finish('sample-1');
			});
		}
	};
	$scope.changeMLAList = function(rType, lcId, mlaHouse, districtName, otherDistrictName) {

		$scope.mlas = '';
		$scope.workData.mla = '';
		if (rType == 0 && lcId != null && mlaHouse != null) {
			$scope.loadMLAs(lcId, mlaHouse);
		}
		else if (rType == 1 && districtName != null && mlaHouse != null) {
			$scope.loadMLAsByDistrictName(districtName, mlaHouse);
		}
		else if (rType == 2 && otherDistrictName != null && mlaHouse != null) {
			$scope.loadMLAsByDistrictName(otherDistrictName, mlaHouse);
		}
	}


	$scope.loadMLAsMultiSelect = function(lcId) {


		$loading.start('sample-1');
		if (typeof (lcId) != "undefined" && lcId.length > 0) {
			var response = $http.get('fetchMLAByLCIds/' + lcId);
			response.success(function(data, status, headers, config) {

				$scope.mlas = data;




				setTimeout(function() {
					$('#mlaName').selectpicker('refresh');
					$loading.finish('sample-1');
				}, 1000);

			});
		} else {

			$('#mlaName').val('');
			/*$('#villageId').val('');
			$('#habitatId').val('');*/
			$scope.fundData.mlaName = null;
			$scope.mlas = '';

			/*$scope.workData.villageStrIds = null;
			$scope.workData.habitatStrIds = null;*/

			/*$scope.gramPanchayats = '';
			$scope.villages='';
			$scope.habitations='';*/



			setTimeout(function() {
				$('#mlaId').selectpicker('refresh');
				/*$('#villageId').selectpicker('refresh');
				$('#habitatId').selectpicker('refresh');*/
				$loading.finish('sample-1');
			}, 1000);
		}
	};



	$scope.loadULB = function(districtName) {
		$loading.start('sample-1');
		var response = $http.get('fetchULBByDistrictName/' + districtName);
		response.success(function(data, status, headers, config) {
			$scope.ulbs = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadWard = function(ulb) {
		if (ulb && ulb != null && ulb != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchWardByULBCode/' + ulb);
			response.success(function(data, status, headers, config) {
				$scope.wards = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWardById = function(ulbId) {
		if (ulbId && ulbId != null && ulbId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchWardByULBId/' + ulbId);
			response.success(function(data, status, headers, config) {
				$scope.wards = data;
				$loading.finish('sample-1');
			});
		}
	};

	/*$scope.loadUserForm = function(){
		$scope.subEnggData = {};
		$http.get('fetchLoggedInUser').then(function(response){
			var user = response.data;
			$scope.subEnggData.districtId = user.districtId+""; 
		});
	};*/

	$scope.loadSubOfficeForm = function() {
		$scope.subOfficeData = {};
		$http.get('fetchLoggedInUser').then(function(response) {
			var user = response.data;
			$scope.subOfficeData.districtId = user.districtId + "";
			$scope.subOfficeData.districtName = (user.districtName == '-' ? "" : user.districtName);

			$scope.loadBlocksByDistrict($scope.subOfficeData.districtName);
			$scope.loadULB($scope.subOfficeData.districtName);
		});
	};

	$scope.uploadWorkDocument = function(id, tsDocument, asDocument, estDocument, ccDocument, workOrderDocument, mlaDocument, reDocument, firstInstDocument, secInstDocument, finalInstDocument, atDocument, soDocument, riaDocument, wcDocument, canDocument, revEstDocument, revASDocument, revTSDocument, page) { //line 714
		$loading.start('sample-1');
		var fd = new FormData();
		if (tsDocument) {
			fd.append('tsDocument', tsDocument);
		}
		if (asDocument) {
			fd.append('asDocument', asDocument);
		}
		if (estDocument) {
			fd.append('estDocument', estDocument);
		}
		if (ccDocument) {
			fd.append('ccDocument', ccDocument);
		}
		if (workOrderDocument) {
			fd.append('workOrderDocument', workOrderDocument);
		}
		if (id) {
			fd.append('id', id);
		}
		if (mlaDocument) {
			fd.append('mlaDocument', mlaDocument);
		}
		if (reDocument) {
			fd.append('reDocument', reDocument);
		}
		if (firstInstDocument) {
			fd.append('firstInstDocument', firstInstDocument);
		}
		if (secInstDocument) {
			fd.append('secInstDocument', secInstDocument);
		}
		if (finalInstDocument) {
			fd.append('finalInstDocument', finalInstDocument);
		}
		if (atDocument) {
			fd.append('atDocument', atDocument);
		}
		if (soDocument) {
			fd.append('soDocument', soDocument);
		}
		if (riaDocument) {
			fd.append('riaDocument', riaDocument);
		}
		if (wcDocument) {
			fd.append('wcDocument', wcDocument);
		}
		if (canDocument) {
			fd.append('canDocument', canDocument);
		}
		if (revEstDocument) {
			fd.append('revEstDocument', revEstDocument);
		}
		if (revTSDocument) {
			fd.append('revTSDocument', revTSDocument);
		}
		if (revASDocument) {
			fd.append('revASDocument', revASDocument);
		}
		var responsePromise = $http.post('addTSASDocuments', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}

		});
		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);
				$window.location.href = '#' + page;
			}
			if ($rootScope.responseObject.errorMessage != null) {
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




	$scope.uploadInspectionImages = function(id, fileArr, page) { //line 714
		$loading.start('sample-1');
		var fd = new FormData();
		if (fileArr) {
			for (var i = 0; i < 11; i++) {
				if (null != fileArr[i] && fileArr[i] != undefined) {
					var date = new Date(fileArr[i].otherDocDate),
						mnth = ("0" + (date.getMonth() + 1)).slice(-2),
						day = ("0" + date.getDate()).slice(-2);

					var convertedDate = [day, mnth, date.getFullYear()].join("/");

					fd.append('fileArr' + i, fileArr[i].fillArr);
					fd.append('otherDocDate' + i, convertedDate);
					fd.append('remarks' + i, fileArr[i].remarks);
				}
			}
		}
		if (id) {
			fd.append('id', id);
		}

		var responsePromise = $http.post('uploadInspectionImages', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}

		});
		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);
				$window.location.href = '#' + page;
			}
			if ($rootScope.responseObject.errorMessage != null) {
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

	$scope.removeInspectionImage = function(index) {

		$scope.InspectionImageData.splice(index, 1);
	};
	$scope.fetchInpectionImageDataList = function() {
		//		$scope.fileArr = [10];
		$loading.start('sample-1');
		var response = $http.get('fetchInpectionImageDataList');

		response.success(function(data, status, headers, config) {
			if ($scope.workData != undefined) {
				$scope.workData.inpectionImagesDataList = data;
			} else {
				$scope.workData = {};
				$scope.workData.inpectionImagesDataList = data;

			}
			$loading.finish('sample-1');
		});

	};
	$scope.addInspectionImages = function(index, inspectionData, file, id) {


		if ((file && file != undefined && file != null)
			&& (inspectionData.otherDocDate && inspectionData.otherDocDate != undefined
				&& inspectionData.otherDocDate != null)
			&& (inspectionData.remarks && inspectionData.remarks != undefined
				&& inspectionData.remarks != null)) {
			/*file.formattedDate=moment(uploadFileData.otherDocDate).utc().format('MM/DD/YYYY');;*/


			var date = new Date(inspectionData.otherDocDate);
			var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
			var day = ("0" + date.getDate()).slice(-2);
			file.formattedDate = [date.getFullYear(), mnth, day].join("-");

			inspectionData["fillArr"] = file;
			if ($scope.workData.inpectionData != undefined || $scope.workData.inspectionImageData != null) {
				if ($scope.inspectionImageData.length + ($scope.workData.inspectionImageData).length > 11) {
					alert("you can upload maximim 11 Images!");
					return false;
				}
			}
			if ($scope.inspectionImageData.length > 11 && $scope.workData.inspectionImageData == undefined) {
				alert("you can upload maximim 11 Images!");
				return false;
			}

			$scope.inspectionImageData.push({ ...inspectionData });



			$scope.inspectionData = {};
			$scope.inspectionData.otherDocDate = '';

			$('#fileupload input[type=file]').val("");
		}
		else {
			alert("Please Fill All Fields!.")
		}
	};

	$scope.uploadOtherDoc = function(id, fileArr, page) { //line 714
		$loading.start('sample-1');
		var fd = new FormData();
		/*List<MultipartFile> list=new List<MultipartFile>();*/
		if (fileArr) {
			/*for (var i = 0; i < fileArr.length; i++) {
			docArray.push(fileArr[i])
		}*/ for (var i = 0; i < 7; i++) {
				if (null != fileArr[i] && fileArr[i] != undefined) {
					var date = new Date(fileArr[i].otherDocDate),
						mnth = ("0" + (date.getMonth() + 1)).slice(-2),
						day = ("0" + date.getDate()).slice(-2);

					var convertedDate = [day, mnth, date.getFullYear()].join("/");

					fd.append('fileArr' + i, fileArr[i].fillArr);
					fd.append('noOfDocs' + i, fileArr[i].noOfDocs);
					fd.append('otherDocDate' + i, convertedDate);
					fd.append('remarks' + i, fileArr[i].remarks);
				}
			}


			/*	fd.append('fileArr1', fileArr[0].fillArr);
				fd.append('noOfDocs1', fileArr[0].noOfDocs);
				
				fd.append('fileArr2', fileArr[1].fillArr);
				fd.append('noOfDocs2', fileArr[1].noOfDocs);*/
			/*fd.append('fileArr', JSON.stringify(fileArr));*/
			/*fd.append('myList', myList);*/
			/*fd.append('fileArr', docArray);*/
		}
		if (id) {
			fd.append('id', id);
		}

		var responsePromise = $http.post('uploadOtherDoc', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}

		});
		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;

			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);
				$window.location.href = '#' + page;
			}
			if ($rootScope.responseObject.errorMessage != null) {
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


	$scope.downloadWorkDocument = function(documentId) {
		$window.open('downloadWorkDocument/' + documentId);
	};

	$scope.searchByWorkNameFilters = function() {
		$loading.start('sample-1');
		$scope.workDataFiltersParameters = $scope.workData;
		localStorage.setItem("filters", JSON.stringify($scope.workDataFiltersParameters));

		reDraw();
		$loading.finish('sample-1');

	};
	$scope.addOtherDocument = function(index, uploadFileData, file, id) {


		if ((file && file != undefined && file != null) && (uploadFileData.noOfDocs && uploadFileData.noOfDocs != undefined && uploadFileData.noOfDocs != null)
			&& (uploadFileData.otherDocDate && uploadFileData.otherDocDate != undefined && uploadFileData.otherDocDate != null)
			&& (uploadFileData.remarks && uploadFileData.remarks != undefined && uploadFileData.remarks != null)) {
			/*file.formattedDate=moment(uploadFileData.otherDocDate).utc().format('MM/DD/YYYY');;*/


			var date = new Date(uploadFileData.otherDocDate);
			var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
			var day = ("0" + date.getDate()).slice(-2);
			file.formattedDate = [date.getFullYear(), mnth, day].join("-");

			uploadFileData["fillArr"] = file;

			/*	var fileCount = 0;
				if($scope.workData.fileData){
					fileCount = ($scope.workData.fileData).length;
				}
				if($scope.FileData.length > (7-fileCount)){
					alert("you can upload maximim 7 document!");
					return false;	
				}*/

			/*var fileCount = 0;
			if($scope.workData.fileData){
				fileCount = ($scope.workData.fileData).length;
			}*/
			if ($scope.workData.fileData != undefined || $scope.workData.fileData != null) {
				if ($scope.FileData.length + ($scope.workData.fileData).length > 6) {
					alert("you can upload maximim 7 document!");
					return false;
				}
			}
			if ($scope.FileData.length > 6 && $scope.workData.fileData == undefined) {
				alert("you can upload maximim 7 document!");
				return false;
			}

			$scope.FileData.push({ ...uploadFileData });



			$scope.uploadFileData = {};
			$scope.uploadFileData.otherDocDate = '';

			$('#fileupload input[type=file]').val("");
		}
		else {
			alert("Please Fill All Fields!.")
		}
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
	$scope.dateFormatter = function(index) {

		var idx = "datetimepicker_" + index;
		//alert(idx);
		$('#' + idx).datetimepicker({

			maxDate: moment(),
			format: 'DD/MM/YYYY',
			useCurrent: false

		});

	};

	$scope.loadMlaRecommendedWorksList = function() {

		$loading.start('sample-1');
		fetchMlaRecommendedWorksList();
	};

	$scope.loadCitizenRequestData = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchCitizenRequestData/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.workData = {};
			$scope.workData = data;
			$scope.workData.schemeId = "8";
			$scope.workData.lcId = $scope.workData.lcId + "";

			$scope.loadMLAList($scope.workData.lcId);
			$scope.workData.mla = $scope.workData.mla + "";
			$scope.workData.blockId = $scope.workData.blockId + "";
			$scope.loadGramPanchayatByBlockId($scope.workData.blockId);
			$scope.workData.gpId = $scope.workData.gpId + "";
			$scope.loadVillageByGPId($scope.workData.gpId);
			$scope.workData.villageId = $scope.workData.villageId + "";
			$scope.loadWard($scope.workData.ulb);
			$loading.finish('sample-1');
		});
	};

	$scope.downloadDocument = function(documentId) {
		$window.open('downloadDocument/' + documentId);
	};

	$scope.loadBudgetDetList = function() {

		$loading.start('sample-1');
		fetchBudgetDetList();
	};

	$scope.loadLCsByDistrictIdC = function(districtId) {

		$loading.start('sample-1');
		var response = $http.get('fetchLCsByDistrictIdC/' + districtId);
		response.success(function(data, status, headers, config) {
			$scope.lcs = data;
			$loading.finish('sample-1');
			//lcId
			setTimeout(function() {
				$('#lcId').selectpicker('refresh');
			}, 1000);
		});
	};

	$scope.addMlaFund = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addMlaFund', $scope.fundData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageBudgetDetails';
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

	$scope.loadFund = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchFundById/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.fundData = data;
			/*$scope.workCatData.workTypeBean.workTypeId = $scope.workCatData.workTypeBean.workTypeId+""; */
			$scope.fundData.districtId = $scope.fundData.districtId + "";
			$scope.loadLCsByDistrictIdC($scope.fundData.districtId);
			$scope.fundData.lcId = $scope.fundData.lcId + "";
			$scope.loadMLAList($scope.fundData.lcId);
			$loading.finish('sample-1');
		});
	};

	//loadLegacyFund

	$scope.loadLegacyFund = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchLegacyFunds/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.legacyFund = data;
			$scope.amtTotal = $scope.sum($scope.legacyFund, 'amount');
			/*alert($scope.amtTotal);*/
			$loading.finish('sample-1');
		});
	};

	$scope.sum = function(items, prop) {
		if (items == null) {
			return 0;
		}
		return items.reduce(function(a, b) {
			return (b[prop] == null || !b[prop]) ? a : a + parseFloat(b[prop]);
		}, 0);
	};

	$scope.fetchMlaFundReport = function(districtId, lcId, mlaName, finYear) {



		if ((null == lcId || undefined == lcId || lcId == "") || (null == mlaName || undefined == mlaName || mlaName == "") || (null == finYear || undefined == finYear || finYear == "")) {
			/*alert("Please Select All Fields!")*/
			return false;
		}
		$loading.start('sample-1');

		/*var response = $http.get('fetchWorksForWorkBook/'+iaId+'/'+subEnggId+'/'+workStatusName);*/
		var response = $http.get('fetchMlaFundReport/' + districtId + '/' + lcId + '/' + mlaName + '/' + finYear);

		response.success(function(data, status, headers, config) {
			$scope.workData = data;
			$scope.asTotal = $scope.sum($scope.workData, 'asAmt');
			$scope.remainingAmount = $scope.budgetTotal - $scope.asTotal;
			$scope.budgtTotalAmount = $scope.budgetTotal;


			$loading.finish('sample-1');
		});
	};

	$scope.fetchMlaFundReportString = function(districtId, lcId, mlaName, finYear) {

		if ((null == lcId || undefined == lcId || lcId == "") || (null == mlaName || undefined == mlaName || mlaName == "") || (null == finYear || undefined == finYear || finYear == "")) {
			/*alert("Please Select All Fields!")*/
			return false;
		}
		$loading.start('sample-1');

		/*var response = $http.get('fetchWorksForWorkBook/'+iaId+'/'+subEnggId+'/'+workStatusName);*/
		var response = $http.get('fetchMlaFundReportString/' + districtId + '/' + lcId + '/' + mlaName + '/' + finYear);

		response.success(function(data, status, headers, config) {
			$scope.workData = data;
			$scope.asTotal = $scope.sum($scope.workData, 'asAmt');
			$scope.remainingAmount = $scope.budgetTotal - $scope.asTotal;
			$scope.budgtTotalAmount = $scope.budgetTotal;

			$loading.finish('sample-1');
		});
	};

	//loadMlaBudget

	$scope.loadMlaBudget = function(districtId, lcId, mlaName, finYear) {

		if (null != districtId && undefined != districtId
			&& null != lcId && undefined != lcId && null != mlaName && undefined != mlaName && null != finYear && undefined != finYear) {
			$loading.start('sample-1');
			var response = $http.get('loadMlaBudget/' + districtId + '/' + lcId + '/' + mlaName + '/' + finYear);
			response.success(function(data, status, headers, config) {
				/*	alert(data);*/
				/*alert(data[0].amount);*/
				$scope.budgetData = {};
				/*$scope.budgetData.amount=0;*/

				$scope.budgetData = data;
				$scope.budgetTotal = $scope.sum($scope.budgetData, 'amount');
				if ("" != $scope.budgetData && undefined != $scope.budgetData && null != $scope.budgetData) {
					$scope.budgetData.ammt = $scope.budgetData[0].amount;
				}
				else {
					$scope.budgetData.ammt = 0;
				}

				$loading.finish('sample-1');
			});
		}
	};


	$scope.loadMlaBudgetString = function(districtId, lcId, mlaName, finYear) {

		if (null != districtId && undefined != districtId
			&& null != lcId && undefined != lcId && null != mlaName && undefined != mlaName && null != finYear && undefined != finYear) {
			$loading.start('sample-1');
			var response = $http.get('loadMlaBudgetString/' + districtId + '/' + lcId + '/' + mlaName + '/' + finYear);
			response.success(function(data, status, headers, config) {
				/*	alert(data);*/
				/*alert(data[0].amount);*/
				$scope.budgetData = {};
				/*$scope.budgetData.amount=0;*/

				$scope.budgetData = data;
				$scope.budgetTotal = $scope.sum($scope.budgetData, 'amount');
				if ("" != $scope.budgetData && undefined != $scope.budgetData && null != $scope.budgetData) {
					$scope.budgetData.ammt = $scope.budgetData[0].amount;
				}
				else {
					$scope.budgetData.ammt = 0;
				}

				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadSubOfficeByIAId = function(implementationAgencyId, implAgencyTypeId) {

		if (implementationAgencyId && implementationAgencyId != null && implementationAgencyId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchSubOfficeByImplAgencyId/' + implementationAgencyId + '/' + implAgencyTypeId);
			response.success(function(data, status, headers, config) {
				$scope.subOffice = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadSubOfficeByIAIdRev = function(implementationAgencyId, implAgencyTypeId) {

		if (implementationAgencyId && implementationAgencyId != null && implementationAgencyId != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchSubOfficeByImplAgencyId/' + implementationAgencyId + '/' + implAgencyTypeId);
			response.success(function(data, status, headers, config) {
				$scope.subOfficeRev = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadSubOfficeByIAName = function(implementationAgency) {

		if (implementationAgency && implementationAgency != null && implementationAgency != 'null') {
			$loading.start('sample-1');
			var response = $http.get('fetchSubOfficeByImplAgencyName/' + implementationAgency);
			response.success(function(data, status, headers, config) {
				$scope.subOffice = data;
				$loading.finish('sample-1');
			});
		}
	};

	$scope.loadWorkStatusByScheme = function(schemeId) {

		var statusId = $scope.workData.workStatusId;
		if ($scope.workData.workStatusId == '' || $scope.workData.workStatusId == undefined) {
			statusId = 0;
		}
		$loading.start('sample-1');
		var response = $http.get('fetchWorkStatusByScheme/' + schemeId + '/' + statusId);
		response.success(function(data, status, headers, config) {
			$scope.workStatus = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkStatusByMLAScheme = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkStatusByMLAScheme');
		response.success(function(data, status, headers, config) {
			$scope.workStatus = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadSchemes = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSchemes');
		response.success(function(data, status, headers, config) {
			$scope.schemes = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadHeads = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchHeads');
		response.success(function(data, status, headers, config) {
			$scope.heads = data;
			$loading.finish('sample-1');
		});
	};





	$scope.loadSubCategoryByWorkCategory = function(workCategoryId) {
		//alert(categoryId+ 'Loades');
		if (null != workCategoryId && workCategoryId != "null") {
			//alert(officeTypeId);
			$loading.start('sample-1');

			var response = $http.get('fetchSubCategoryByCategory/' + workCategoryId);
			response.success(function(data, status, headers, config) {
				$scope.subCategories = data;
				$loading.finish('sample-1');
			});
		}

	};


	$scope.addSubOffice = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			var responsePromise = $http.post('addSubOffice', $scope.subOfficeData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSubOffice';
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

	$scope.editSubOffice = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.subEnggData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editSubOffice', $scope.subOfficeData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSubOffice';
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

	$scope.loadSubOfficeDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSubOfficeDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.subOfficeData = data;
			$scope.subOfficeData.implAgencyTypeId = $scope.subOfficeData.implAgencyTypeId + "";
			$scope.subOfficeData.implAgencyId = $scope.subOfficeData.implAgencyId + "";
			$scope.subOfficeData.districtId = $scope.subOfficeData.districtId + "";
			$scope.subOfficeData.districtName = $scope.subOfficeData.districtBean.districtName;

			$scope.loadImplAgenciesByType($scope.subOfficeData.implAgencyTypeId, $scope.subOfficeData.districtBean.districtName);
			$scope.subOfficeData.jpId = $scope.subOfficeData.jpId + '';
			$scope.loadImplAgenciesByTypeAndJP($scope.subOfficeData.implAgencyTypeId, $scope.subOfficeData.districtBean.districtName, $scope.subOfficeData.jpId)
			$loading.finish('sample-1');
		});
	};

	$scope.estCheckBoxAction = function() {

		if ($scope.workData.estCheckbox == true) {
			$scope.workData.recommendedAmt = '';
		}
	};

	$scope.loadIssuingAuthority = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchIssuingAuthorityFromDesignationTable');
		response.success(function(data, status, headers, config) {
			$scope.issuingAuthorities = data;
			$loading.finish('sample-1');
		});
	};
	$scope.loadEndorsementAuthority = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchEndorsementAuthorityFromDesignationTable');
		response.success(function(data, status, headers, config) {
			$scope.endorsementAuthorities = data;

			$loading.finish('sample-1');
		});
	};

	$scope.loadIssuingAuthorityById = function(id) {
		$loading.start('sample-1');
		var response = $http.get('fetchIssuingAuthorityFromDesignationTableById/' + id);
		response.success(function(data, status, headers, config) {
			$scope.issuingAuthority = data;
			$scope.workData.asIssuingAuthority = $scope.issuingAuthority.designation;
			$loading.finish('sample-1');
		});
	};
	$scope.loadEndorsementAuthorityById = function(id) {
		$loading.start('sample-1');
		var response = $http.get('fetchEndorsementAuthorityFromDesignationTableById/' + id);
		response.success(function(data, status, headers, config) {
			$scope.endorsementAuthority = data;
			$scope.workData.asEndorsementAuthority = $scope.endorsementAuthority.designation;
			$loading.finish('sample-1');
		});
	};


	$scope.chooseMandatoryFilters = function() {
		var flag = false;
		if ($scope.workData.workType != null && $scope.workData.financialYear != null && $scope.workData.lcId != null) {
			$scope.flag = true;
		}
	};

	$scope.deleteSdr = function(Id) {
		if (confirm("Are you sure to delete this entry?")) {
			$loading.start('sample-1');

			var responsePromise = $http.get('deleteSdr/' + Id);
			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSdr';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;

					}, 10000);

					/*		Swal.fire({
							title: $rootScope.responseObject.errorMessage,
							icon: 'error',
							showCancelButton: false,
							confirmButtonText: 'OK',
									})*/
					$window.location.href = '#manageSdr';
				}
				$loading.finish('sample-1');
			});
		} else {
			return false;
		}
	};

	$scope.editSdr = function(isValid) {

		if (!isValid)
			return false;

		if (confirm("Are you sure you want to save the data?")) {
			//$scope.implAgencyData.id = $routeParams.id;
			$loading.start('sample-1');
			var responsePromise = $http.post('editSdr', $scope.sdrData);

			responsePromise.success(function(data, status, headers, config) {

				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$window.location.href = '#manageSdr';
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

	$scope.loadSdrDetails = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchSdrDetails/' + $routeParams.id);

		response.success(function(data, status, headers, config) {
			$scope.sdrData = data;
			$scope.sdrData.workSubStatusId = $scope.sdrData.workSubStatusId + "";
			$loading.finish('sample-1');
		});
	};

	$scope.loadWorkSubDelayReasonBySubWorkStatus = function(workSubStatusId) {
		if (workSubStatusId == null || workSubStatusId === '' || workSubStatusId === 'null'
			|| workSubStatusId === undefined || workSubStatusId === 0 || workSubStatusId === '0') {
			$scope.worksSubDelayReason = [];
			return;
		}

		$loading.start('sample-1');
		var response = $http.get('fetchSubDelayReasonByWorkSubStatusId/' + workSubStatusId);
		response.success(function(data, status, headers, config) {
			$scope.worksSubDelayReason = data || [];
			$loading.finish('sample-1');
		});
		response.error(function() {
			$scope.worksSubDelayReason = [];
			$loading.finish('sample-1');
		});
	};

	$scope.loadSdrList = function() {

		$loading.start('sample-1');
		fetchSdrList();
	};


	// add by Sumit

	$scope.loadWorkCategoryByWorkType = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkCategoryByWorkType');
		response.success(function(data, status, headers, config) {
			$scope.workCategories = data;
			// if($scope.workCategories.length ==1){
			//     $scope.workData.workCategoryId = $scope.workCategories[1];

			// }
			// console.log('workCategories.length..'+$scope.workCategories.length);
			$loading.finish('sample-1');
		});
	};

	$scope.percOptions = [];
	for (var i = 5; i <= 100; i += 5) {
		$scope.percOptions.push(i);
	}

	$scope.handleSelectedValue = function() {
		// Access the selected value from the model workDataProgress.perc
		var selectedValue = $scope.workDataProgress.perc;
		// Do whatever you want with the selected value
		console.log("Selected value: " + selectedValue);
	};


	/*	$scope.workContractor = function() {
			
			alert("=============" + $scope.workData.isRecommendedAgency)
			if (workData.isRecommendedAgency == 1) {
	
				$scope.workDataContractor.name. = fale;
				$scope.workDataContractor.contactNo = fale;
				$scope.workDataContractor.emailId = fale;
				$scope.workDataContractor.firmNameAddress = fale;
				$scope.workDataContractor.remarks = fale;
				
			} else {
	
				
				$scope.workDataContractor.name = fale;
				$scope.workDataContractor.contactNo = fale;
				$scope.workDataContractor.emailId = fale;
				$scope.workDataContractor.firmNameAddress = fale;
				$scope.workDataContractor.remarks = fale;
	
			}
	
	
		}*/


	/*$scope.scrollStatusChange = function(id) {
			alert("scrollStatusChange"+id);
			
			//	if (workDataContractor.workIdCount==1)
				if (id==1)
				{
					
					$scope.workDataContractor.name. = fale;
				$scope.workDataContractor.contactNo = fale;
				$scope.workDataContractor.emailId = fale;
				$scope.workDataContractor.firmNameAddress = fale;
				$scope.workDataContractor.remarks = fale;
				alert("asdasdaad============="+id);
						angular.element(document.getElementById("name"))[0].disabled = true;
							angular.element(document.getElementById("contactNo"))[0].disabled = true;
								angular.element(document.getElementById("emailId"))[0].disabled = true;
									angular.element(document.getElementById("firmNameAddress"))[0].disabled = true;
										angular.element(document.getElementById("remarks"))[0].disabled = true;
					//$scope.billScrollBean.rejectionStatus="";
				//	angular.element(document.getElementById('rejectionStatus'))[0].disabled = true;
				}
				else
				{
					
					angular.element(document.getElementById("name"))[0].disabled = false;
							angular.element(document.getElementById("contactNo"))[0].disabled = false;
								angular.element(document.getElementById("emailId"))[0].disabled = false;
									angular.element(document.getElementById("firmNameAddress"))[0].disabled = false;
										angular.element(document.getElementById("remarks"))[0].disabled = false;
					$scope.billScrollBean.rejectionStatus="";
					angular.element(document.getElementById('rejectionStatus'))[0].disabled = true;
					angular.element(document.getElementById('paymentStatus'))[0].disabled = false;
				}
				
			};*/

	$scope.scrollStatusChange = function(id) {
		//alert("scrollStatusChange " + id);

		// Assuming workDataContractor exists on the scope and is an object
		if (id === 1) {
			//alert("Disabling fields as id is 1");

			// Set a scope variable to true which will be used in the view to disable fields
			$scope.isDisabled = true;
		} else {
			//alert("Enabling fields as id is not 1");

			// Set the scope variable to false to enable fields
			$scope.isDisabled = false;
		}
	};


	//fetchWorkTenderEndDate
	$scope.loadWorkTenderEndDate = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchWorkTenderEndDate');
		response.success(function(data, status, headers, config) {
			$scope.workTenderDate = data;
			// if($scope.workCategories.length ==1){
			//     $scope.workData.workCategoryId = $scope.workCategories[1];

			// }
			// console.log('workCategories.length..'+$scope.workCategories.length);
			$loading.finish('sample-1');
		});
	};


	$scope.loadFinancialYear = function() {
		$loading.start('sample-1');
		//console.log('Mode....' + mode);
		var response = $http.get('getFinancialYear');
		response.success(function(data, status, headers, config) {
			$scope.financialYear = data;
			$loading.finish('sample-1');
		});
	};


	$scope.loadStateIdOfMPsss = function() {
		alert("call=====")
		//	Pace.start();
		var response = $http.get('fetchStateIdOfMP');
		response.success(function(data, status, headers, config) {

			$scope.userData.agencyId = data;
			//alert("call function ===== $scope.userData.agencyId " + $scope.userData.agencyId)
			//	Pace.stop();
		});
	};




	/*$scope.loadOngoingWorkList = function() {
		$loading.start('sample-1');
		fetchWorksAaIssuedList();
	};*/

	$scope.$watch('workDataTender.remarks', function(newVal, oldVal) {
		if ($scope.workTenderForm && $scope.workTenderForm.remarks) {
			if (newVal && newVal.length > 1000) {
				$scope.workTenderForm.remarks.$setValidity('maxLength', false);
			} else {
				$scope.workTenderForm.remarks.$setValidity('maxLength', true);
			}
		}
	});

	$scope.$watch('workDataTS.asRemarks', function(newVal, oldVal) {
		if ($scope.workFormName && $scope.workFormName.asRemarks) {
			if (newVal && newVal.length > 1000) {
				$scope.workFormName.asRemarks.$setValidity('maxLength', false);
			} else {
				$scope.workFormName.asRemarks.$setValidity('maxLength', true);
			}
		}
	});

	$scope.$watch('workDataTS.tsRemarks', function(newVal, oldVal) {
		if ($scope.workFormName && $scope.workFormName.tsRemarks) {
			if (newVal && newVal.length > 1000) {
				$scope.workFormName.tsRemarks.$setValidity('maxLength', false);
			} else {
				$scope.workFormName.tsRemarks.$setValidity('maxLength', true);
			}
		}
	});

	$scope.$watch('workDataContractor.remarks', function(newVal, oldVal) {
		if ($scope.workFormContractor && $scope.workFormContractor.Remarks) {
			if (newVal && newVal.length > 1000) {
				$scope.workFormContractor.Remarks.$setValidity('maxLength', false);
			} else {
				$scope.workFormContractor.Remarks.$setValidity('maxLength', true);
			}
		}
	});

	$scope.$watch('workDataProgress.remarks', function(newVal, oldVal) {
		if ($scope.workFormProgress && $scope.workFormProgress.remarks) {
			if (newVal && newVal.length > 1000) {
				$scope.workFormProgress.remarks.$setValidity('maxLength', false);
			} else {
				$scope.workFormProgress.remarks.$setValidity('maxLength', true);
			}
		}
	});

	// only view for image
	$scope.loadWorkProgressDocumetnId = function(documentId) {

		$loading.start('sample-1');


		var response = $http.get('fetchWorkProgressDocumetnId/' + documentId);



		response.success(function(data, status, headers, config) {

			if (!data) {
				$scope.workDataProgress.workStatus = 'notStarted';
				$scope.responseImage = 'no';
				$scope.workDataProgress.progressUpdated = 'notSelected';
			} else {




				$scope.workDataProgress = data;

				//alert("callworkProgressCount=============" + $scope.workDataProgress.workProgressCount)
				//alert("$scope.workDataProgress.workStatusId===@@@@@@@@" + $scope.workDataProgress.workStatusId)
				if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
					//alert("$scope.workDataProgress.workProgressCount===" + $scope.workDataProgress.workProgressCount)
					if ($scope.workDataProgress.workProgressCount == 1) {
						document.getElementById("totalExpensess").disabled = true;
					} else if ($scope.workDataProgress.totalExpensess == null) {
						document.getElementById("totalExpensess").disabled = false;
					}
				}



				$scope.workDataProgress.workId = $scope.workDataProgress.workId + "";
				$scope.workDataProgress.workStatusId = $scope.workDataProgress.workStatusId + "";
				$scope.workDataProgress.workSubStatusId = $scope.workDataProgress.workSubStatusId + "";
				$scope.workDataProgress.workSubStatusNameE = $scope.workDataProgress.workSubStatusNameE + "";
				$scope.workDataProgress.workSubDelayReasonId = $scope.workDataProgress.workSubDelayReasonId + "";
				$scope.workDataProgress.progressUpdated = $scope.workDataProgress.workStatusId + "";
				$scope.workDataProgress.stipulatedDateCompleted = $scope.workDataTender.workCompletionDate ? $scope.workDataTender.workCompletionDate + "" : "";
				if ($scope.workDataProgress.workSubStatusId == '5' || $scope.workDataProgress.workSubStatusId == '6' || $scope.workDataProgress.workSubStatusId == '7' || $scope.workDataProgress.workSubStatusId == '8' || $scope.workDataProgress.workSubStatusId == '9' || $scope.workDataProgress.workSubStatusId == '10' || $scope.workDataProgress.workSubStatusId == '11') {
					$scope.responseImage = 'yes';

				}

				$scope.loadCCDetails();
				$scope.loadWorkSubStatusByWorkStatus($scope.workData.workSubTypeId, $scope.workDataProgress.workStatusId);
				$scope.loadWorkSubDelayReasonBySubWorkStatus($scope.workDataProgress.workSubStatusId);
				console.log('SubStatusWork....' + $scope.workDataProgress.workSubStatusId);

				if ($scope.finalCall == 'yes') {

				} else {
					if ($scope.workDataProgress.workSubStatusId == "null" || $scope.workDataProgress.workSubStatusId == null || $scope.workDataProgress.workSubStatusId == undefined || $scope.workDataProgress.workSubStatusId == "" || $scope.workDataProgress.workSubStatusId == 0) {
						if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
							$scope.successRespondeWS = 'success';
							$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);

						} else {

						}
					} else {
						$scope.successRespondeWS = 'success';
						//$scope.loadWorkProgressImagesList($scope.workDataProgress.workId);
					}
				}





			}
			/*	if ($scope.workDataProgress.workStatusId == '10' || $scope.workDataProgress.workStatusId == '11') {
					if ($scope.workDataProgress.totalExpensess == null && $scope.workDataProgress.workProgressCount >= 1) {
						document.getElementById("totalExpensess").disabled = false;
					} else {
						document.getElementById("totalExpensess").disabled = true;
					}
	
				}*/





			//console.log('Financial total....' + $scope.workDataProgress.totalExpensess)

			$loading.finish('sample-1');
		});
	};


	$scope.validatePanConsultant = function(pan) {
		if (pan != null && $.trim(pan) != "") {
			var filter = /^([A-Z]){5}([0-9]){4}([A-Z]){1}?$/;
			if (!filter.test(pan)) {
				$scope.panError = true;
				$scope.workDataContractor.pan = null;
			} else {
				$scope.panError = false;
			}
		} else {
			$scope.panError = false;
		}
	};



	$scope.validateGstinConsultant = function(gstin) {
		if (gstin != null && $.trim(gstin) != "") {
			var filter = /^([0][1-9]|[1-2][0-9]|[3][0-7])([A-Z]{5})([0-9]{4})([A-Z]{1}[1-9A-Z]{1})([Z]{1})([0-9A-Z]{1})+$/;
			if (!filter.test(gstin)) {
				$scope.gstinError = true;
				$scope.workDataContractor.gstin = null;
			} else {
				$scope.gstinError = false;
			}
		} else {
			$scope.gstinError = false;
		}
	};


	$scope.loadMonths = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchMonths');
		response.success(function(data, status, headers, config) {
			$scope.months = data;
			$loading.finish('sample-1');
		});
	};


	$scope.cancelFile = function(workId) {

		if (confirm("Are you sure you want to Cancel the Original TA/AA?")) {

			$loading.start('sample-1');



			var fd = new FormData();

			if ($scope.workDataTS.workId) {
				fd.append('workId', $scope.workDataTS.workId);
			}


			$loading.start('sample-1');

			var responsePromise = $http.post('addTSASWorkDataStatus', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				$scope.workDataTender = {};
				$scope.workDataTender.workId = null;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					$scope.workDataTender.workId = $rootScope.responseObject.id;
					$scope.successRespondeTS = 'success';
					//if (mode == 'Add') {
					$window.location.href = '#manageOngoingWorks';
					//	} if (mode == 'Edit') {
					//		var $active = $('.wizard .nav-tabs .nav-item .active');
					//		var $activeli = $active.parent("li");
					//		$($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
					//		$($activeli).next().find('a[data-toggle="tab"]').click();
					$scope.loadTenderDetails();
					$scope.loadWorkDetails('sec');


					//}


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



	$scope.cancelFileTS = function(id) {

		if (!$scope.workDataRTS) {
			$scope.workDataRTS = {};
		}

		if (confirm("Are you sure you want to Cancel the Revised TS?")) {

			$loading.start('sample-1');

			var fd = new FormData();

			if (id) {
				fd.append('id', id);
			}

			/*if ($scope.workDataRTS.id) {
				alert($scope.workDataRTS.id + "idiididi")
				fd.append('id', $scope.workDataRTS.id);
			}*/

			if ($scope.workData.workId) {
				fd.append('workId', $scope.workData.workId);
			}

			if ($scope.workDataTS.id) {
				fd.append('tsAsId', $scope.workDataTS.id);
			}








			$loading.start('sample-1');

			var responsePromise = $http.post('addTSReviseWorkDataStatus', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);




					$scope.workDataTS.workId = $rootScope.responseObject.id;
					$window.location.href = '#editWork/' + $scope.workDataTS.workId;

					// $scope.loadTSASRevisedList($scope.workDataTS.workId);





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

		else {

			$scope.submit = false;
		}
	};


	$scope.cancelFileAS = function(id) {

		if (!$scope.workDataRTS) {
			$scope.workDataRTS = {};
		}

		if (confirm("Are you sure you want to Cancel the Revised AA?")) {

			$loading.start('sample-1');

			var fd = new FormData();

			if (id) {
				fd.append('id', id);
			}

			if ($scope.workData.workId) {
				fd.append('workId', $scope.workData.workId);
			}

			if ($scope.workDataTS.id) {
				fd.append('tsAsId', $scope.workDataTS.id);
			}



			$loading.start('sample-1');

			var responsePromise = $http.post('addASReviseWorkDataStatus', fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;

				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$scope.successResponseRevised = 'success';
					$scope.workDataTS.workId = $rootScope.responseObject.id;
					$window.location.href = '#editWork/' + $scope.workDataTS.workId;
					//$scope.loadTSASRevisedList($scope.workDataTS.workId);

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

		else {

			$scope.submit = false;
		}


		$loading.start('sample-1');

		var fd = new FormData();

		if ($scope.workData.workId) {
			fd.append('workId', $scope.workData.workId);
		}

		if ($scope.workDataTS.id) {
			fd.append('tsAsId', $scope.workDataTS.id);
		}

		if ($scope.workDataTS.tsAsSataus) {
			fd.append('tsAsSataus', $scope.workDataTS.tsAsSataus);


		}

		$loading.start('sample-1');

		var responsePromise = $http.post('addASReviseWorkDataStatus', fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		});

		responsePromise.success(function(data, status, headers, config) {
			$rootScope.responseObject = data;

			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);


				$scope.successResponseRevised = 'success';
				$scope.workDataTS.workId = $rootScope.responseObject.id;
				$window.location.href = '#editWork/' + $scope.workDataTS.workId;
				//$scope.loadTSASRevisedList($scope.workDataTS.workId);


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


	$scope.loadTotalIdCount = function(workId) {
		$loading.start('sample-1');
		//alert("$scope.workData.id----------"+$scope.workData.id);
		//var workId	= $scope.workData.id;
		//alert("workId----------"+workId);
		var response = $http.get('getCountId/' + workId);
		response.success(function(data, status, headers, config) {
			$scope.countId = data;
			//		alert("$scope.countId----------"+$scope.countId);
			$scope.workDataTS.countId = $scope.countId;
			$scope.workDataRTS.countId = $scope.countId;
			//			alert("$scope.workDataTS.countId----------"+$scope.workDataTS.countId);
			$loading.finish('sample-1');
		});
	};

	$scope.remakrsChange = function() {

		if ($scope.workData.DmRemakrs == null) {
			$scope.workData.isDisabledDep = true;
		}



	};



	$scope.reloadedJqueryDatatable = function() {
		$loading.start('sample-1');
		tsDraw();
		$loading.finish('sample-1');
		$scope.$apply();
	};

	$scope.loadWorkForReport = function() {
		// Read departmentRemark from AngularJS route params if available
		if ($routeParams.departmentRemark && !window.urlDepartmentRemark) {
			window.urlDepartmentRemark = $routeParams.departmentRemark;
			localStorage.removeItem('work_filters');
		}
		// Parse filter params from hash (e.g. #manageOngoingWorks?departmentId=1)
		var hash = window.location.hash || '';
		var qIdx = hash.indexOf('?');
		if (qIdx !== -1) {
			var hashParams = new URLSearchParams(hash.substring(qIdx + 1));
			if (!window.urlDepartmentId && hashParams.get('departmentId')) {
				window.urlDepartmentId = hashParams.get('departmentId');
			}
			if (!window.urlFinancialYearId && hashParams.get('financialYearId')) {
				window.urlFinancialYearId = hashParams.get('financialYearId');
			}
			if (!window.urlWorkStatus && hashParams.get('workStatus')) {
				window.urlWorkStatus = hashParams.get('workStatus');
			}
			if (hashParams.get('implementationAgency')) {
				window.urlImplementationAgency = hashParams.get('implementationAgency');
			}
			if (hashParams.get('departmentRemark')) {
				window.urlDepartmentRemark = hashParams.get('departmentRemark');
			}
			if (hashParams.get('workStatusId')) {
				window.urlWorkStatusIds = hashParams.get('workStatusId');
			} else if (hashParams.get('departmentId') || hashParams.get('financialYearId') || hashParams.get('implementationAgency')) {
				window.urlWorkStatusIds = '';
				try {
					var wf = JSON.parse(localStorage.getItem('work_filters') || '{}');
					wf.workStatusId = '';
					localStorage.setItem('work_filters', JSON.stringify(wf));
				} catch (e) { /* ignore */ }
			}
		}
		$timeout(function() {
			if (window.initManageOngoingWorksSelectpickers) {
				window.initManageOngoingWorksSelectpickers();
			}
		}, 900);
	};

	$scope.loadWorkForInspectionReport = function() {
		fetchInspectionReport();
	};

	$scope.loadWorkWithLatestExpenses = function() {
		//	alert("call")
		fetchLatestExpenses();
	};

	$scope.loadDepartments = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchAllDepartment');
		response.success(function(data, status, headers, config) {
			$scope.department = data;

			var y = $('#deptId');
			if (y.length) y.addClass('btn-selected');

			setTimeout(function() {
				window.safeSelectpickerRefresh('#department');
			}, 1000);

			$loading.finish('sample-1');
		});
	};
	$scope.loadSubWorkType = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchWorkSubTypesList');
		response.success(function(data, status, headers, config) {
			$scope.subworktype = data;
			$loading.finish('sample-1');
		});
	};





	$scope.loadGeoTagData = function() {

		$loading.start('sample-1');

		var workid = Number($routeParams.id);
		var response = $http.get('getGeoTaggingForWork/' + workid);
		response.success(function(data, status, headers, config) {
			//alert("ad");
			$scope.geoWorkData = data;
			if (data && data.length) {
				initilizemap(data);
			}
			$loading.finish('sample-1');
		});
	};


	$scope.capsPan = function(value) {

		$scope.workDataContractor.pan = String(value).toUpperCase();


	};
	$scope.capitalise = function(value) {
		$scope.workDataContractor.pan = String(value).toUpperCase();
	};

	$scope.capsGSTIN = function(value) {
		$scope.workDataContractor.gstin = String(value).toUpperCase();
	};
	$scope.defaulttender = function() {
		// $scope.workData.isTender = '0';
	};


	$scope.loadUserList = function() {
		$loading.start('sample-1');
		if (typeof fetchUserList === 'function') {
			fetchUserList();
		} else {
			$timeout(function() {
				if (typeof fetchUserList === 'function') {
					fetchUserList();
				} else {
					console.error('fetchUserList is not defined yet');
					$loading.finish('sample-1');
				}
			}, 200);
		}
	};


	$scope.loadWorkTenderStatus = function() {
		$loading.start('sample-1');
		fetchWorkTenderStatus($routeParams.id);
		$loading.finish('sample-1');
	}

	$scope.loadWorkTenderStatuscc = function() {
		$loading.start('sample-1');
		fetchWorkTenderStatuscc($routeParams.id);
		$loading.finish('sample-1');
	}



	$scope.assignUser = function(workid, userid) {





		var responsePromise = $http.post('assignUserToWork/' + userid + '/' + workid);

		responsePromise.success(function(data, status, headers, config) {

			$rootScope.responseObject = data;
			reDraw();
			//$scope.loadWorkDetails('sec');
			if ($rootScope.responseObject.successMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.successMessage = null;
				}, 5000);


			}
			if ($rootScope.responseObject.errorMessage != null) {
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 5000);
			}

		});
	}


//	$scope.saveOrUpdateDmRemarkss = function() {
//		//alert("call-------")
//		var dmRemarkData = {
//			id: $scope.workData.dmRemarkId || null,   // agar update hai to id, warna null
//			remark: $scope.workData.DmRemakrs,
//			workId: $scope.workData.id
//		};
//
//		$http.post('saveOrUpdate', dmRemarkData)
//			.then(function(response) {
//				if (response.data.successMessage) {
//					alert(response.data.successMessage);  // ya toast success
//				} else if (response.data.errorMessage) {
//					alert("Error: " + response.data.errorMessage);  // ya toast error
//				}
//
//			}, function(error) {
//				console.error('Error saving DM Remarks:', error);
//				alert('Something went wrong while saving remarks.');
//			});
//	};

	$scope.downloadRemakrsDocument = function(id) {
		//console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentRemakrs/' + id);
	}


	$scope.getRemarksDetails = function(id) {
		$loading.start('sample-1');
		var response = $http.get('getRemarksDetailsById/' + id);
		response.success(function(data, status, headers, config) {
			$scope.workDataR = data;
			$scope.workDataR.remakr = data.remark;
			$scope.workDataR.id = data.id;
			$scope.workDataR.role = data.role;
			$scope.workDataR.roleCode = data.roleCode;
			$scope.workDataR.depertmentMasterId = data.depertmentMasterId + "";
			$scope.workDataR.DepartmentName = data.DepartmentName + "";
			//		$scope.workData.role = $scope.workD
			console.log("EDIT DATA : ", data);
console.log("EDIT ROLE : ", data.role);

			$loading.finish('sample-1');
		});
	};
	
		$scope.getDepartmentRemarksDetails = function(id) {
		$loading.start('sample-1');
		var response = $http.get('getDepartmentRemarksDetailsById/' + id);
		response.success(function(data, status, headers, config) {
			$scope.workDataR = data;
			$scope.workDataR.remakr = data.remark;
			$scope.workDataR.id = data.id;
			$scope.workDataR.role = data.role;
			$scope.workDataR.roleCode = data.roleCode;
			$scope.workDataR.depertmentMasterId = data.depertmentMasterId + "";
			$scope.workDataR.DepartmentName = data.DepartmentName + "";
			//		$scope.workData.role = $scope.workD
			console.log("EDIT DATA : ", data);
console.log("EDIT ROLE : ", data.role);

			$loading.finish('sample-1');
		});
	};

	$scope.getTrustedPdfUrl = function(path) {
		if (path && typeof path === 'string') {
			return $sce.trustAsResourceUrl(path);
		} else if (path && path.$$unwrapTrustedValue) {
			// already trusted value, return as-is
			return path;
		} else {
			console.warn("Invalid or missing path passed to getTrustedPdfUrl:", path);
			return null;
		}
	};
	$scope.downloadRemakrsDocument = function(id) {
		//console.log(" downloadDocument =" + documentId);
		$window.open('downloadDocumentRemakrs/' + id);
	}


	$scope.openAttachmentModal = function(id) {
		$scope.dmRemarksId = id;
		$scope.previewUrl = '';

		$http({
			method: 'GET',
			url: 'previewDocumentRemarks/' + id,
			responseType: 'arraybuffer'
		}).then(function(response) {
			var contentType = response.headers('Content-Type') || 'application/octet-stream';
			var contentDisposition = response.headers('Content-Disposition') || '';
			var filenameMatch = contentDisposition.match(/filename="(.+?)"/);
			var filename = filenameMatch ? filenameMatch[1] : '';

			var blob = new Blob([response.data], { type: contentType });
			var objectUrl = URL.createObjectURL(blob);

			if (contentType === 'application/pdf') {

				window.open(objectUrl, "_blank");
			} else if (filename.toLowerCase().endsWith('.pdf')) {
				window.open(objectUrl, "_blank");
			} else if (filename.toLowerCase().match(/\.(jpg|jpeg|png|gif)$/)) {

				$scope.previewUrlad = objectUrl;
				$('#myModal').modal('show');
			} else {
				alert('Unsupported file type: ' + contentType + ' (filename: ' + filename + ')');
			}

		}, function(error) {
			console.error('Error fetching file:', error);
			alert('Error loading attachment!');
		});
	};


$scope.loadDmRemarksForWorkId = function(workId) {

   $scope.roleName = window.roleName;
    if (!workId) {
      
        return;
    }

    $loading.start('sample-1');

    // ✅ USE workId ARGUMENT
    var response = $http.get('getDMRemarks/' + workId);

    response.success(function(data) {

        $scope.dmremarkslist = data;

        for (var i = 0; i < data.length; i++) {
            var remark = data[i];

         remark.dmRemarks = remark.dmRemarks || '';


            remark.createdDateObj = new Date(remark.createdDate);
        }
$scope.getRemarksDetails();
        $loading.finish('sample-1');
    });
};

$scope.loadDepartmentRemarksForWorkId = function(workId) {

   $scope.roleName = window.roleName;
    if (!workId) {
      
        return;
    }

    $loading.start('sample-1');

    // ✅ USE workId ARGUMENT
    var response = $http.get('getDepartmentRemarks/' + workId);

    response.success(function(data) {

        $scope.departmentremarkslist = data;

        for (var i = 0; i < data.length; i++) {
            var remark = data[i];

         remark.dmRemarks = remark.dmRemarks || '';


            remark.createdDateObj = new Date(remark.createdDate);
        }
$scope.getDepartmentRemarksDetails();
        $loading.finish('sample-1');
    });
};


	
	$scope.saveOrUpdateDmRemarks = function(isValid, dmattachment) {
		//	alert("Call DM Login Remarks" + $scope.workDataR.depertmentMasterId)
		//$scope.workData.dmStatus =$scope.workData.dmStatus;


		if ( isValid === ' ' || !isValid || isValid === null) {
			alert("Please fill required fields");
			return false;
		}

		// Validate Department Remarks dropdown is selected
		// if (!$scope.workDataR.remarkType || $scope.workDataR.remarkType === '') {
		// 	alert("Please select Remark type");
		// 	return false;
		// }




		if (dmattachment) {
			$scope.noFileError = (dmattachment) ? false : true;
			var maxSizeUpload = 25000000;// in bytes (here 5 MB)
			//var allowedExtensions = ['pdf', 'PDF'];
			if (dmattachment) {
				$scope.fileExtentionErrorDM = (dmattachment.size > maxSizeUpload) ? true : false;

			}

			if ($scope.noFileError)
				return false;
			if ($scope.fileExtentionErrorDM)
				return false;

		} else {
			//$scope.noFileError = false;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorDM)
				return false;
			if ($scope.workDataR.documentId) {
				alert("Please select File");
				return false;
			}
		}



		var formData = new FormData();
	//	alert("$scope.workDataR.id========== " + $scope.workDataR.id)
		if ($scope.workDataR.id) {
			formData.append("id", $scope.workDataR.id || '');
		}
		
		// If dropdown is NOT "Other", use the dropdown value as the remark
		var remarkValue = $scope.workDataR.remakr || '';
		if ($scope.workDataR.remarkType && $scope.workDataR.remarkType !== 'Other') {
			remarkValue = $scope.workDataR.remarkType;
		}
		formData.append("remark", remarkValue);
	//	alert("$scope.workDataR.departmentRemarks ========== " + $scope.workDataR.departmentRemarks)
		formData.append("departmentRemarks", $scope.workDataR.departmentRemarks || '');
		
		var finalWorkId = null;

		if ($scope.workId) {
			finalWorkId = $scope.workId;
		} else if ($scope.workData && $scope.workData.workId) {
			finalWorkId = $scope.workData.workId;
		}

		if (!finalWorkId) {
			alert("WorkId missing!");
			return;
		}

		// ✅ सिर्फ एक ही बार append होगा
		formData.append("workId", finalWorkId);
		if ($scope.workDataR.depertmentMasterId !== null &&
			$scope.workDataR.depertmentMasterId !== undefined &&
			$scope.workDataR.depertmentMasterId !== '') {

			formData.append("depertmentMasterId",
				$scope.workDataR.depertmentMasterId);
		}

		formData.append("dmStatus", $scope.workDataR.dmStatus || '');
		//alert(dmattachment)
		if (dmattachment) {
			formData.append("dmattachment", dmattachment);
		}
		else {
			//return;
		}


		if (confirm("Are you sure you want to save the data?")) {
			$scope.workDataR.dmStatus = $scope.workDataR.dmStatus;
			//document.getElementById("submit").disabled=true;

			$loading.start('sample-1');



			//var responsePromise = $http.post('saveOrUpdate', formData);
			var responsePromise = $http.post('saveOrUpdate', formData, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});
			responsePromise.success(function(data, status, headers, config) {

				$loading.start('sample-1');
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					
					$scope.workDataR = {};
					$scope.loadDmRemarksForWorkId(finalWorkId);
					

					$scope.workDataR.departmentRemakrs = "";
					$scope.workDataR.remakrs = "";
					var dmAttachmentEl = document.getElementById("dmremarksAttachment");
					if (dmAttachmentEl) {
						dmAttachmentEl.value = null;
					}

					//$window.location.href = '#manageOngoingWorks';
				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		} else {
			$scope.workDataR.dmStatus = null;
		}


	};
	
	
	$scope.saveOrUpdateDepartmentRemarks = function(isValid, dmattachment) {
		if ($scope.workForm) {
			$scope.workForm.$setSubmitted();
		}

		var deptMasterId = $scope.workDataR.depertmentMasterId;
		if (deptMasterId === null || deptMasterId === undefined || deptMasterId === ''
				|| deptMasterId === 'undefined' || deptMasterId === 'null') {
			alert("Please select Department Remarks");
			return false;
		}

		// When "Other" is selected, custom remark text is required
		if (String(deptMasterId) === '5') {
			var remarkText = ($scope.workDataR.departmentRemarkName || '').trim();
			if (!remarkText) {
				alert("Please enter remarks");
				return false;
			}
		}

		// if (!isValid) {
		// 	alert("Please fill required fields");
		// 	return false;
		// }




		if (dmattachment) {
			$scope.noFileError = (dmattachment) ? false : true;
			var maxSizeUpload = 25000000;// in bytes (here 5 MB)
			//var allowedExtensions = ['pdf', 'PDF'];
			if (dmattachment) {
				$scope.fileExtentionErrorDM = (dmattachment.size > maxSizeUpload) ? true : false;

			}

			if ($scope.noFileError)
				return false;
			if ($scope.fileExtentionErrorDM)
				return false;

		} else {
			//$scope.noFileError = false;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorDM)
				return false;
			if ($scope.workDataR.documentId) {
				alert("Please select File");
				return false;
			}
		}



		var formData = new FormData();
	//	alert("$scope.workDataR.id========== " + $scope.workDataR.id)
		if ($scope.workDataR.id) {
			formData.append("id", $scope.workDataR.id || '');
		}
		formData.append("remark", $scope.workDataR.remakr || '');
	//	alert("$scope.workDataR.departmentRemarks ========== " + $scope.workDataR.departmentRemarks)
		formData.append("departmentRemarkName", $scope.workDataR.departmentRemarkName || '');
		
		var finalWorkId = null;

		if ($scope.workId) {
			finalWorkId = $scope.workId;
		} else if ($scope.workData && $scope.workData.workId) {
			finalWorkId = $scope.workData.workId;
		}

		if (!finalWorkId) {
			alert("WorkId missing!");
			return;
		}

		// ✅ सिर्फ एक ही बार append होगा
		formData.append("workId", finalWorkId);
		if ($scope.workDataR.depertmentMasterId !== null &&
			$scope.workDataR.depertmentMasterId !== undefined &&
			$scope.workDataR.depertmentMasterId !== '') {

			formData.append("depertmentMasterId",
				$scope.workDataR.depertmentMasterId);
		}

		formData.append("dmStatus", $scope.workDataR.dmStatus || '');
		//alert(dmattachment)
		if (dmattachment) {
			formData.append("dmattachment", dmattachment);
		}
		else {
			//return;
		}


		if (confirm("Are you sure you want to save the data?")) {
			$scope.workDataR.dmStatus = $scope.workDataR.dmStatus;
			//document.getElementById("submit").disabled=true;

			$loading.start('sample-1');



			//var responsePromise = $http.post('saveOrUpdate', formData);
			var responsePromise = $http.post('saveOrUpdateDepartment', formData, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});
			responsePromise.success(function(data, status, headers, config) {

				$loading.start('sample-1');
				$rootScope.responseObject = data;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);
					
					$scope.workDataR = {};
					$scope.loadDepartmentRemarksForWorkId(finalWorkId);
					

					$scope.workDataR.departmentRemakrs = "";
					$scope.workDataR.remakrs = "";
					var dmAttachmentEl = document.getElementById("dmremarksAttachment");
					if (dmAttachmentEl) {
						dmAttachmentEl.value = null;
					}

					//$window.location.href = '#manageOngoingWorks';
				}
				if ($rootScope.responseObject.errorMessage != null) {
					alert($rootScope.responseObject.errorMessage);
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
		} else {
			$scope.workDataR.dmStatus = null;
		}


	};



	$scope.loadareaUserList = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchAreaOfficerRecord/' + $routeParams.id);
		response.success(function(data, status, headers, config) {
			$scope.useralist = data;
			//	$scope.useralist = $scope.useralist.reverse();

			$scope.useralist.forEach(function(user) {
				user.assignDate = new Date(user.assignDate); // Converts string to Date object
			});
			$loading.finish('sample-1');
		});
	};


	$scope.loadWorkPriority = function() {
		$loading.start('sample-1');
		$http.get('fetchWorkPriority')
			.then(function(response) {
				$scope.priorityies = response.data;
				
				var y = $('#wpId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#workPriorityId');

			}, 1000);
				
				
				$loading.finish('sample-1');
			})
			.catch(function(error) {
				console.error("Error fetching work priority:", error);
				$loading.finish('sample-1');
			});
	};

	$scope.loadDepartmentMasters = function() {
		$loading.start('sample-1');
		$http.get('getDepartmentMaster')
			.then(function(response) {
				$scope.departmentMasters = response.data;
				
				var y = $('#drId');
				y.addClass('btn-selected');

				setTimeout(function() {
					window.safeSelectpickerRefresh('#departmentRemark');
				}, 1000);
				
				$loading.finish('sample-1');
			})
			.catch(function(error) {
				console.error("Error fetching department masters:", error);
				$scope.departmentMasters = [];
				$loading.finish('sample-1');
			});
	};




	$scope.createWorkPriority = function(isValid, mode, ldPdfFile, AdPdfFile, form) {
		//	alert("Call DM Login Remarks")
		//$scope.workData.dmStatus =$scope.workData.dmStatus;






		if (ldPdfFile) {
			$scope.noFileError = (ldPdfFile) ? false : true;
			var maxSizeUpload = 25000000;// in bytes (here 5 MB)
			//var allowedExtensions = ['pdf', 'PDF'];
			if (ldPdfFile) {
				$scope.fileSizeErrorLd = (ldPdfFile.size > maxSizeUpload) ? true : false;

			}

			if ($scope.noFileError)
				return false;
			if ($scope.fileSizeErrorLd)
				return false;

		} else {
			//$scope.noFileError = false;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorLd)
				return false;
			if ($scope.workDataTS.tsFileId) {
				alert("Please select Technical Sanction File");
				return false;
			}
		}

		if (AdPdfFile) {

			$scope.noFile2Error = (AdPdfFile) ? false : true;

			var maxSizeUpload2 = 25000000;// in bytes (here 5 MB)
			if (AdPdfFile) {
				$scope.fileSizeErrorAd = (AdPdfFile.size > maxSizeUpload2) ? true : false;
			}
			if ($scope.noFile2Error)
				return false;
			if ($scope.fileSizeErrorAd)
				return false;

		} else {
			//$scope.noFile2Error = true;
			//$scope.workData.dmStatus = null;
			if ($scope.fileExtentionErrorAd)
				return false;
			if ($scope.workDataTS.asFileId) {
				alert("Please select Administration Approval File");
				return false;
			}
		}



		var fd = new FormData();

		if ($scope.workData.dmStatus) {
			fd.append('dmStatus', $scope.workData.dmStatus);
		}
		if ($scope.workData.DmRemakrs) {
			fd.append('DmRemakrs', $scope.workData.DmRemakrs);
		}
		if ($scope.workData.id) {
			fd.append('id', $scope.workData.id);
		}


		if ($scope.workData.workSubTypeId) {
			fd.append('workSubTypeId', $scope.workData.workSubTypeId);
		}



		if ($scope.workData.workName) {
			fd.append('workName', $scope.workData.workName);
		}

		if ($scope.workData.workTypeId) {
			fd.append('workTypeId', $scope.workData.workTypeId);
		}

		if ($scope.workData.financialYear) {
			fd.append('financialYear', $scope.workData.financialYear);
		}

		if ($scope.workData.isTender) {
			fd.append('isTender', $scope.workData.isTender);
		}

		if ($scope.workData.implementationAgency) {
			fd.append('implementationAgency', $scope.workData.implementationAgency);
		}

		if ($scope.workData.districtCode) {
			fd.append('districtCode', $scope.workData.districtCode);
		}

		if ($scope.workData.blockCode) {
			fd.append('blockCode', $scope.workData.blockCode);
		}

		if ($scope.workData.constituencyCode) {
			fd.append('constituencyCode', $scope.workData.constituencyCode);
		}

		if ($scope.workData.tsNo) {
			fd.append('tsNo', $scope.workData.tsNo);
		}

		if ($scope.workData.tsDate) {
			fd.append('tsDate', $scope.workData.tsDate);
		}

		if ($scope.workData.tsAmt) {
			fd.append('tsAmt', $scope.workData.tsAmt);
		}

		if (ldPdfFile) {
			fd.append('tsDocumentUpload', ldPdfFile);
		}

		if ($scope.workData.tsRemarks) {
			//		alert("$scope.workData.tsRemarks " + $scope.workData.tsRemarks)
			fd.append('tsRemarks', $scope.workData.tsRemarks);
		}

		// For Administrative Approval fields
		if ($scope.workData.asNo) {
			fd.append('asNo', $scope.workData.asNo);
		}

		if ($scope.workData.asDate) {
			fd.append('asDate', $scope.workData.asDate);
		}

		if ($scope.workData.asAmt) {
			fd.append('asAmt', $scope.workData.asAmt);
		}

		if (AdPdfFile) {
			fd.append('asDocumentUpload', AdPdfFile);
		}

		if ($scope.workData.asRemarks) {
			//	alert("$scope.workData.asRemarks" + $scope.workData.asRemarks)
			fd.append('asRemarks', $scope.workData.asRemarks);
		}

		if ($scope.workData.isTenders) {
			fd.append('isTenders', $scope.workData.isTenders);
		}
		if ($scope.workData.isTenders == false) {

			fd.append('isTenders', 0);
		}
		if ($scope.workData.isTenders == true) {

			fd.append('isTenders', 1);
		}


		if ($scope.workData.gramPanchayatCode) {
			fd.append('gramPanchayatCode', $scope.workData.gramPanchayatCode);
		}


		if ($scope.workData.workPriorityId) {
			fd.append('workPriorityId', $scope.workData.workPriorityId);
		}


		if (confirm("Are you sure you want to save the data?")) {
			$scope.workData.dmStatus = $scope.workData.dmStatus;
			//document.getElementById("submit").disabled=true;

			$loading.start('sample-1');



			var responsePromise = $http.post('addWorkData', fd, {

				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			});
			responsePromise.success(function(data, status, headers, config) {

				$loading.start('sample-1');
				$rootScope.responseObject = data;

				$scope.workDataTS = {};
				$scope.workDataTS.workId = null;
				//$scope.workDataTender = {};
				//$scope.workDataTender.workId = null;
				if ($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 5000);


					$scope.workDataTS = data;
					$scope.workDataTS.tsNo = $scope.workDataTS.tsNo + "";
					//$scope.loadTenderDetails();
					$scope.workDataTS.workId = $rootScope.responseObject.id;
					//if ($scope.workData.fileStatus == '1') {
					//	$scope.uploadedDrawingFiles($scope.workDataTS.workId, dTTTFile);
					//}
					if (mode == 'Add') {
						$scope.createTSASWorkData(isValid, ldPdfFile, AdPdfFile, mode);
						//$window.location.href = '#manageOngoingWorks';
					} if (mode == 'Edit') {
						$scope.createTSASWorkData(isValid, ldPdfFile, AdPdfFile, mode);
						$scope.loadTSASDetails();
						$scope.loadWorkDetails('sec');
						if ($scope.saveAndNext) {
							$scope.goToNextWizardTab();
						}
					}
					/*var $active = $('.wizard .nav-tabs .nav-item .active');
					var $activeli = $active.parent("li");
					$($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
					$($activeli).next().find('a[data-toggle="tab"]').click();
					$scope.loadWorkDetails('sec');
*/


				}
				if ($rootScope.responseObject.errorMessage != null) {
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
		} else {
			$scope.workData.dmStatus = null;
		}


	};

 
	$scope.loadFinancialHead = function() {
		$loading.start('sample-1');
		$http.get('fetchFinancialHead')
			.then(function(response) {
				$scope.financialHeads = response.data;
				
				
				var y = $('#fyhId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#financialHeadId1');

			}, 1000);
				
				$loading.finish('sample-1');
			})
			.catch(function(error) {
				console.error("Error fetching work priority:", error);
				$loading.finish('sample-1');
			});
	};

	$scope.loadVidhanSabha = function() {
		$loading.start('sample-1');
		$http.get('fetchVidhanSabha')
			.then(function(response) {
				$scope.vidhanSabhas = response.data;
				
					var y = $('#vsId');
			y.addClass('btn-selected');


			setTimeout(function() {
				window.safeSelectpickerRefresh('#vidhanSabhaId1');

			}, 1000);
				
				$loading.finish('sample-1');
			})
			.catch(function(error) {
				console.error("Error fetching work priority:", error);
				$loading.finish('sample-1');
			});
	};

	$scope.loadAssignedUsers = function() {
		$loading.start('sample-1');
		var response = $http.get('fetchAssignedUsers');
		response.success(function(data, status, headers, config) {
			$scope.AssignUsers = data;
			$loading.finish('sample-1');
		});
	};

	$scope.loadAssignUser = function(implementationAgency) {
		$loading.start('sample-1');
		var response = $http.get('fetchAssignUser/' + implementationAgency);
		response.success(function(data, status, headers, config) {
			$scope.AssignUsers = data;
			$loading.finish('sample-1');
		});
	};


	function getBaseUrl() {
		var path = window.location.pathname;
		var firstPart = path.split('/')[1];

		// If context path is 'anuppur', return it with slash
		if (firstPart === 'anuppur') {
			return '/' + firstPart;
		}

		// Else no context path (like in LIVE), return empty
		return '';
	}


	$scope.suggestedWorkNos = [];
	$scope.blocks = [];
	$scope.suggestedWorkNames = [];

	$scope.loadWorkNoSuggestions = function(keyword) {

		if (!keyword || keyword.length < 2) {
			$scope.suggestedWorkNos = [];
			return;
		}

		$http.get(getBaseUrl() + "/systemAdmin/suggestWorkNos?keyword=" + keyword)
			.then(function(response) {
				$scope.suggestedWorkNos = response.data;
			}, function(err) {
				console.error("Suggestion API Error:", err);
			});
	};

	$scope.selectWorkNo = function(no) {
		$scope.workNo = no;
		$scope.suggestedWorkNos = []; // hide dropdown
		window.reloadJqueryDatatables(); // refresh table
	};
	
	// Work Name Suggestions
	$scope.suggestedWorkNames = [];

	$scope.loadWorkNameSuggestions = function(keyword) {

		if (!keyword || keyword.length < 2) {
			$scope.suggestedWorkNames = [];
			return;
		}

		$http.get(getBaseUrl() + "/systemAdmin/suggestWorkNames?keyword=" + keyword)
			.then(function(response) {
				$scope.suggestedWorkNames = response.data;
			}, function(err) {
				console.error("Work Name Suggestion API Error:", err);
			});
	};

	$scope.selectWorkName = function(name) {
		$scope.workNameSearch = name;
		$scope.suggestedWorkNames = []; // hide dropdown
		window.reloadJqueryDatatables(); // refresh table
	};
	
	document.addEventListener("click", function(event) {
    var isClickInside = event.target.closest("#workNameFilter");   // input id
    var isList = event.target.closest(".suggestion-list-box");   // UL class
    
    if (!isClickInside && !isList) {
        $scope.suggestedWorkNames = [];
        $scope.$apply();   // update HTML
    }
});
	
	document.addEventListener("click", function(event) {
    var isClickInside = event.target.closest("#searchBoxVal");   // input id
    var isList = event.target.closest(".suggestion-list-box");   // UL class
    
    if (!isClickInside && !isList) {
        $scope.suggestedWorkNos = [];
        $scope.$apply();   // update HTML
    }
});

	



$scope.workDataRows = []; // Dynamic rows
$scope.financialHeadsOriginal = []; // Original list

// Load financial heads from API
$scope.loadFinancialHeadforAddAS = function(isEdit) {
    $loading.start('sample-1');
    $http.get('fetchFinancialHead')
        .then(function(response) {

            $scope.financialHeadsOriginal = angular.copy(response.data);
            
            
            if ($scope.workDataRows && $scope.workDataRows.length > 0) {
            angular.forEach($scope.workDataRows, function(row) {
                row.availableFinancialHeads = angular.copy($scope.financialHeadsOriginal);
            });
            $scope.updateAllAvailableFinancialHeads();
        }

            // पहली row add करो
            if (!$scope.workDataRows || $scope.workDataRows.length === 0) {
                $scope.addRow();
            }

            $loading.finish('sample-1');
        });
};


// Add new row
$scope.addRow = function () {
    const newRow = {
        financialHeadId: "",
        cost: "",
        availableFinancialHeads: angular.copy($scope.financialHeadsOriginal) // <-- Important
    };

    $scope.workDataRows.push(newRow);
    $scope.updateAllAvailableFinancialHeads();
};


// Remove row
//$scope.removeRow = function(index) {
//    $scope.workDataRows.splice(index, 1);
//    $scope.updateAllAvailableFinancialHeads();
//};


$scope.removeRow = function(index) {

    let row = $scope.workDataRows[index];
    let rowId = row.financialAgencyId || row.id;

    if (rowId) {

        $http.post("deleteFinancialAgencyRow?id=" + rowId)
            .then(function (res) {

                alert(res.data);

                // Remove from UI
                $scope.workDataRows.splice(index, 1);

                // Refresh dropdowns
                $scope.updateAllAvailableFinancialHeads();
            });
        $scope.workDataRows.splice(index, 1);

    } else {

        $scope.workDataRows.splice(index, 1);
        $scope.updateAllAvailableFinancialHeads();
    }
};



// Update available financial heads for **all rows**
$scope.updateAllAvailableFinancialHeads = function () {

    // Already selected heads
    const selectedIds = $scope.workDataRows
        .filter(r => r.financialHeadId && r.financialHeadId !== "")
        .map(r => parseInt(r.financialHeadId));

    // Update dropdown for each row
    $scope.workDataRows.forEach(row => {

        row.availableFinancialHeads = $scope.financialHeadsOriginal.filter(fh =>
            // Allow others except selected
            selectedIds.indexOf(fh.id) === -1 ||
            fh.id === parseInt(row.financialHeadId)
        );
    });
};






$scope.syncFinancialExpenditureTotals = function () {
	if (!$scope.workDataProgress) {
		return;
	}
	var newExp = 0;
	$("#dynamic-fa-table .cost-input").each(function () {
		newExp += parseFloat($(this).val()) || 0;
	});
	var base = $scope.workDataProgress.baseTotalExpensess;
	if (base === undefined || base === null || isNaN(base)) {
		base = parseFloat($scope.workDataProgress.totalExpensess) || 0;
	}
	$scope.workDataProgress.expensessCurrentFy = parseFloat(newExp.toFixed(2));
	$scope.workDataProgress.totalExpensess = parseFloat((base + newExp).toFixed(2));
};

$scope.saveFinancialAgency = function () {
	var deferred = $q.defer();
	var dataList = [];
	var totalExpenditure = 0;

	$("#dynamic-fa-table .cost-input").each(function () {
		var id = $(this).data("id");
		var workId = $(this).data("workid");
		var financialHeadId = $(this).data("financialheadid");
		var value = parseFloat($(this).val()) || 0;

		if (value <= 0) {
			return;
		}

		totalExpenditure += value;
		dataList.push({
			id: id,
			workId: workId,
			financialHeadId: financialHeadId,
			expenditure: value
		});
	});

	$scope.workDataProgress.expensessCurrentFy = parseFloat(totalExpenditure.toFixed(2));

	if (dataList.length === 0) {
		deferred.resolve('SUCCESS');
		return deferred.promise;
	}

	$.ajax({
		url: "saveFinancialAgencyEnteredCost",
		method: "POST",
		data: JSON.stringify(dataList),
		contentType: "application/json",
		success: function (res) {
			if (res !== 'SUCCESS') {
				deferred.resolve(res);
				return;
			}
			var base = $scope.workDataProgress.baseTotalExpensess;
			if (base === undefined || base === null || isNaN(base)) {
				base = parseFloat($scope.workDataProgress.totalExpensess) || 0;
			}
			$scope.workDataProgress.baseTotalExpensess = parseFloat((base + totalExpenditure).toFixed(2));
			$scope.workDataProgress.totalExpensess = $scope.workDataProgress.baseTotalExpensess;
			deferred.resolve('SUCCESS');
		},
		error: function () {
			deferred.reject();
		}
	});

	return deferred.promise;
};


$scope.getTotalCost = function () {
    let total = 0;

    angular.forEach($scope.workDataRows, function (row) {
        if (row.cost && !isNaN(row.cost)) {
            total += parseFloat(row.cost);
        }
    });

    return total.toFixed(2);
};


//--------added by aman start code
	// AngularJS Controller for handling password expiry and sidebar visibility
			$scope.verifyUserPasswordExpiry = function() {
				// Show a loading indicator
				$loading.start('sample-1');
//alert("---");
				$http.get('verifyUserPasswordExpiry')
					.then(function(response) {
						// Handle success response
						if (response.status === 200) {
							$scope.responseObject = response.data; // Assuming response.data contains ResponseObject
    						  var roleCode = response.data.roleCode;
    						 var targetUrl = getTargetUrlByRole(roleCode); // ⭐ ROLE BASED URL
							// Check for expired password
							if (response.data.successMessage.includes("Your password has expired")) {
							//	alert('aaaaaaaa');
								// Password is expired, hide the sidebar and redirect to change password page
								alert("Your password has expired. Please Update your Password");
								document.getElementById("sideNav").style.display = "none"; // Hide sidebar
								window.location.href = '#/changepassword'; // Redirect to change password page

							}
							// Check for password about to expire
							else if (response.data.successMessage.includes("Your password will expire")) {
								//alert('bbbbb');
								// Show confirmation dialog for the user to update the password
								if (!$window.confirm(response.data.successMessage)) {
									  window.location.href = targetUrl; // Redirect to dashboard if dismissed
								}
							}
							// Password is still valid
							else if (response.data.successMessage.includes("Your password is still valid")) {
								//alert('ccccccc');
								// Password is still valid, show the sidebar
								document.getElementById("sideNav").style.display = "block"; // Show sidebar
								  window.location.href = targetUrl;
							}
							else {
								alert(response.data.successMessage);

							}
						}
						$loading.finish('sample-1');
					});
			};


function getContextPath() {
    var path = window.location.pathname;
    var segments = path.split('/');
    if (segments.length > 1 && segments[1] !== '') {
        return '/' + segments[1];
    }
    return '';
}

function getLoginUrl() {
    return getContextPath() + '/login';
}

function getTargetUrlByRole(roleCode) {

    if (roleCode === 'ROLE_DM' || roleCode === 'ROLE_SYSTEM_ADMIN' || roleCode === 'ROLE_DEPARTMENT' || roleCode === 'ROLE_CEO') {
        return '#/manageOngoingWorks';
        
}  else if ( roleCode === 'ROLE_DISTRICT') {
        return '#/manageOngoingWorks';

    } 

    // fallback
    return getLoginUrl();
}

	$scope.wrongCurrentPassword = false;
$scope.currentPasswordChecked = false;

$scope.checkCurrentPassword = function () {

    if (!$scope.changePasswordData.currentPassword)
        return;

    var data = {
        currentPassword: $scope.changePasswordData.currentPassword // Send plain text - backend handles BCrypt
    };

    $http.post('validateCurrentPassword', data)
        .success(function (response) {

            if (response.errorMessage === 'INVALID_CURRENT_PASSWORD') {
                $scope.wrongCurrentPassword = true;
                $scope.currentPasswordChecked = false;
            } else {
                $scope.wrongCurrentPassword = false;
                $scope.currentPasswordChecked = true;
            }
        })
        .error(function () {
            $scope.wrongCurrentPassword = false;
        });
};

	$scope.loadDepartmentMaster = function() {

		$loading.start('sample-1');

		var response = $http.get('getDepartmentMaster');

		response.success(function(data, status, headers, config) {
			$scope.departmentMaster = data;
			$loading.finish('sample-1');
		});

		response.error(function() {
			$loading.finish('sample-1');
			alert("Failed to load Department Master");
		});
	};

	// ---- Department-wise Works Report ----

	$scope.deptWiseReportRows = [];
	$scope.deptWiseFilterData = { departmentIds: [], financialYearIds: [] };
	$scope.deptWiseStatusMap = {}; // flag number → comma-separated workStatusIds

	$scope.loadDeptWiseDepartmentOptions = function() {
		$http.get('fetchConstructionAgencys').then(function(response) {
			$scope.deptWiseDepartmentOptions = response.data;
			$timeout(function() {
				$('#deptWiseDeptFilter').selectpicker('refresh');
			}, 500);
		});
	};

	$scope.loadDeptWiseFyOptions = function() {
		// Load workStatus map first, then FY options
		$http.get('getWorkStatus').then(function(res) {
			var data = res.data;
			if (typeof data === 'string') { try { data = JSON.parse(data); } catch(e) {} }
			if (Array.isArray(data)) {
				var map = {};
				data.forEach(function(s) {
					var f = String(s.flag);
					if (!map[f]) map[f] = [];
					map[f].push(s.workStatusId);
				});
				// Store as comma-separated strings
				$scope.deptWiseStatusMap['1'] = (map['1'] || []).join(',');
				$scope.deptWiseStatusMap['2'] = (map['2'] || []).join(',');
				$scope.deptWiseStatusMap['3'] = (map['3'] || []).join(',');
			}
		});
		$http.get('fetchFinancialYear').then(function(response) {
			$scope.deptWiseFyOptions = response.data;
			$timeout(function() {
				$('#deptWiseFyFilter').selectpicker('refresh');
				$scope.fetchDeptWiseReport();
			}, 300);
		});
	};

	$scope.fetchDeptWiseReport = function() {
		var params = {};
		var agencyIds = $('#deptWiseDeptFilter').val();
		if (agencyIds && agencyIds.length > 0 && !(agencyIds.length === 1 && agencyIds[0] === '')) {
			params.agencyIds = agencyIds.join(',');
		}
		var fyIds = $('#deptWiseFyFilter').val();
		if (fyIds && fyIds.length > 0 && !(fyIds.length === 1 && fyIds[0] === '')) {
			params.financialYearIds = fyIds.join(',');
		}
		$http.get('fetchDepartmentWiseReport', { params: params }).then(function(res) {
			$scope.deptWiseReportRows = res.data;
		}, function(err) {
			console.error('Error fetching department wise report:', err);
			$scope.deptWiseReportRows = [];
		});
	};

	$scope.resetDeptWiseFilters = function() {
		$('#deptWiseDeptFilter').selectpicker('val', []);
		$('#deptWiseFyFilter').selectpicker('val', []);
		$scope.fetchDeptWiseReport();
	};

	$scope.getDeptWiseDetailUrl = function(row, workStatus) {
		var base = '#/manageOngoingWorks';
		var params = '?implementationAgency=' + row.implementationAgencyId + '&financialYearId=' + row.financialYearId;
		if (workStatus) {
			// Direct workStatusId mapping based on actual DB values
			var statusIdMap = { 'COMPLETED': '11', 'ONGOING': '10', 'NOT_STARTED': '9' };
			var sid = statusIdMap[workStatus];
			if (sid) params += '&workStatusId=' + sid;
		}
		return base + params;
	};

	// ---- Photo Update Report ----

	$scope.photoUpdateReportRows = [];

	$scope.loadPhotoUpdateDeptOptions = function() {
		$http.get('fetchImplAgency').then(function(response) {
			$scope.photoUpdateDeptOptions = response.data;
			$timeout(function() {
				$('#photoUpdateDeptFilter').selectpicker('refresh');
				$scope.fetchPhotoUpdateReportData();
			}, 300);
		});
	};

	$scope.fetchPhotoUpdateReportData = function() {
		var params = {};
		var deptIds = $('#photoUpdateDeptFilter').val();
		if (deptIds && deptIds.length > 0 && !(deptIds.length === 1 && deptIds[0] === '')) {
			params.departmentIds = deptIds.join(',');
		}
		$http.get('fetchPhotoUpdateReport', { params: params }).then(function(res) {
			$scope.photoUpdateReportRows = res.data;
		}, function(err) {
			console.error('Error fetching photo update report:', err);
			$scope.photoUpdateReportRows = [];
		});
	};

	$scope.resetPhotoUpdateFilters = function() {
		$('#photoUpdateDeptFilter').selectpicker('val', []);
		$scope.fetchPhotoUpdateReportData();
	};

	$scope.getPhotoUpdateDetailUrl = function(row) {
		return '#/manageOngoingWorks?implementationAgency=' + row.departmentId;
	};

	// ---- DM Remark-wise Report ----

	$scope.dmRemarkWiseReportRows = [];

	$scope.loadIssueTypeOptions = function() {
		$http.get('getDepartmentMaster').then(function(response) {
			$scope.issueTypeOptions = response.data;
			$timeout(function() {
				$('#issueTypeFilter').selectpicker('refresh');
			}, 300);
		}, function(err) {
			console.error('Error loading issue type options:', err);
		});
	};

	$scope.loadDeptNameOptions = function() {
		$http.get('fetchImplAgency').then(function(response) {
			$scope.deptNameOptions = response.data;
			$timeout(function() {
				$('#deptNameFilter').selectpicker('refresh');
			}, 300);
		}, function(err) {
			console.error('Error loading department name options:', err);
		});
	};

	$scope.fetchDmRemarkWiseReportData = function() {
		var params = {};
		var deptMasterIds = $('#issueTypeFilter').val();
		if (deptMasterIds && deptMasterIds.length > 0 && !(deptMasterIds.length === 1 && deptMasterIds[0] === '')) {
			params.deptMasterIds = deptMasterIds.join(',');
		}
		var implAgencyIds = $('#deptNameFilter').val();
		if (implAgencyIds && implAgencyIds.length > 0 && !(implAgencyIds.length === 1 && implAgencyIds[0] === '')) {
			params.implAgencyIds = implAgencyIds.join(',');
		}
		$http.get('fetchDmRemarkWiseReport', { params: params }).then(function(res) {
			$scope.dmRemarkWiseReportRows = res.data;
		}, function(err) {
			console.error('Error fetching DM remark wise report:', err);
			$scope.dmRemarkWiseReportRows = [];
		});
	};

	$scope.resetDmRemarkFilters = function() {
		$('#issueTypeFilter').selectpicker('val', []);
		$('#deptNameFilter').selectpicker('val', []);
		$scope.fetchDmRemarkWiseReportData();
	};

	$scope.getDmRemarkDetailUrl = function(row) {
		return '#/manageOngoingWorks?departmentRemark=' + row.departmentMasterId;
	};

	$scope.goToDmRemarkDetail = function(row) {
		window.location.href = '#/manageOngoingWorks?departmentRemark=' + row.departmentMasterId;
	};});

