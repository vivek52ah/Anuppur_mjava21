// Check if module already exists, if not create it
var dms;
try {
	dms = angular.module('dms');
} catch(e) {
	// Module doesn't exist, create it
	dms = angular.module('dms', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
}

// Configure $http to handle relative API URLs only
 dms.config(['$httpProvider', function($httpProvider) {
 	var ajaxBase = window.__BASE_HREF_AJAX_BASE || '';
 	var apiPattern = /^(fetch|add|edit|delete|save|upload|do|get|update|remove|approve|reject|download|generate|print|send|verify|check|validate|submit|cancel|assign|transfer|bulk|export|import|mobilelogin|changeWork|createWork)/i;
 	$httpProvider.interceptors.push(function() {
 		return {
 			request: function(config) {
 				if (config.url && !config.url.match(/^(\/|https?:\/\/|data:|#)/) && apiPattern.test(config.url)) {
 					config.url = ajaxBase + config.url;
				}
				return config;
			}
		};
	});
}]);

dms.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
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

dms.run(['Idle', function(Idle) {
Idle.watch();
}]);

window.initManageOngoingWorksSelectpickers = function() {
	var $ = window.jQuery;
	if (!$ || typeof $.fn.selectpicker !== 'function') {
		return false;
	}
	var configs = {
		'#financialYear1': { actionsBox: true, noneSelectedText: '--Select FY of Sanction--' },
		'#workType1':       { actionsBox: true, noneSelectedText: '--Select Work Type--' },
		'#workStatusId':    { actionsBox: true, noneSelectedText: '--Select Work Status--' },
		'#implementationAgency': { actionsBox: true, noneSelectedText: '--Select Executive Agency--' },
		'#department':      { actionsBox: true, liveSearch: true, noneSelectedText: '--Select Department--' },
		'#departmentRemark':{ actionsBox: true, noneSelectedText: '--Search by Issue Type--' },
		'#financialHeadId1':{ actionsBox: true, noneSelectedText: '--Select Financing Agency--' },
		'#vidhanSabhaId1':  { actionsBox: true, noneSelectedText: '--Select Vidhan Sabha--' },
		'#blockId':         { actionsBox: true, liveSearch: true, noneSelectedText: '--Select Block--' }
	};
	var modelMap = {
		'#financialYear1': 'financialYear',
		'#workType1': 'workType',
		'#workStatusId': 'workStatusId',
		'#implementationAgency': 'implementationAgency',
		'#department': 'department',
		'#departmentRemark': 'departmentRemark',
		'#financialHeadId1': 'financialHeadId',
		'#vidhanSabhaId1': 'vidhanSabhaId',
		'#blockId': 'blockId'
	};
	function updateSelectpickerCaption($el, config) {
		var selectedText = $el.find('option:selected').filter(function() {
			return $(this).val() !== '';
		}).map(function() {
			return $.trim($(this).text());
		}).get();
		if (!selectedText.length) {
			var $selectedItems = $el.closest('.bootstrap-select').find('.dropdown-menu li.selected:not(.disabled) .text');
			selectedText = $selectedItems.map(function() {
				return $.trim($(this).text());
			}).get();
		}
		var caption = selectedText.length ? selectedText.join(', ') : config.noneSelectedText;
		var $picker = $el.closest('.bootstrap-select');
		var $button = $picker.find('> .dropdown-toggle');
		var $inner = $button.find('.filter-option-inner-inner');
		if ($inner.length) {
			$inner.text(caption);
		} else {
			$button.find('.filter-option').text(caption);
		}
		$button.attr('title', caption);
		$button.toggleClass('bs-placeholder', selectedText.length === 0);
	}
	function renderSelectedCaption($el, config) {
		[0, 80, 250, 600].forEach(function(delay) {
			setTimeout(function() {
			if ($el.data('selectpicker')) {
				$el.selectpicker('render');
			}
			updateSelectpickerCaption($el, config);
			}, delay);
		});
	}
	function renderAllManageWorkCaptions() {
		Object.keys(configs).forEach(function(selector) {
			var $select = $(selector);
			if ($select.length) {
				updateSelectpickerCaption($select, configs[selector]);
			}
		});
	}
	function startManageWorkCaptionInterval() {
		if (window.manageWorkStaticCaptionInterval) {
			return;
		}
		window.manageWorkStaticCaptionInterval = setInterval(function() {
			if ((window.location.hash || '').indexOf('manageOngoingWorks') === -1) {
				return;
			}
			renderAllManageWorkCaptions();
		}, 300);
	}

	Object.keys(configs).forEach(function(sel) {
		var $el = $(sel);
		if (!$el.length) return;
		try {
			if ($el.data('selectpicker')) {
				$el.selectpicker('refresh');
			} else {
				$el.selectpicker(configs[sel]);
			}

			// Force Angular ng-model sync when selectpicker selection changes
			$el.off('changed.bs.select.ngSync change.ngSync refreshed.bs.select.ngSync loaded.bs.select.ngSync')
				.on('changed.bs.select.ngSync change.ngSync refreshed.bs.select.ngSync loaded.bs.select.ngSync', function() {
				var element = this;
				var scope = angular.element(element).scope();
				if (scope) {
					scope.$applyAsync(function() {
						if (scope.workData && modelMap[sel]) {
							scope.workData[modelMap[sel]] = $(element).val() || [];
						}
					});
				}
				// Update the button title to show selected items
				renderSelectedCaption($(element), configs[sel]);
			});
			renderSelectedCaption($el, configs[sel]);
		} catch (e) {
			console.warn('selectpicker init failed for ' + sel, e);
		}
	});
	$(document)
		.off('click.manageWorkSelectCaption', '.bootstrap-select .dropdown-menu li a')
		.on('click.manageWorkSelectCaption', '.bootstrap-select .dropdown-menu li a', function() {
			var $select = $(this).closest('.bootstrap-select').find('select');
			var selector = $select.length && $select.attr('id') ? '#' + $select.attr('id') : null;
			if (selector && configs[selector]) {
				var clickedText = $.trim($(this).find('.text').text() || $(this).text());
				var $button = $select.closest('.bootstrap-select').find('> .dropdown-toggle');
				var $inner = $button.find('.filter-option-inner-inner');
				if (clickedText) {
					if ($inner.length) {
						$inner.text(clickedText);
					} else {
						$button.find('.filter-option').text(clickedText);
					}
					$button.attr('title', clickedText).removeClass('bs-placeholder');
				}
				renderSelectedCaption($select, configs[selector]);
			}
		});
	startManageWorkCaptionInterval();
	return true;
};

window.refreshSelectpickerAfterLoad = function(selector) {
	var $ = window.jQuery;
	if (!$ || typeof $.fn.selectpicker !== 'function') return;
	// Small delay so Angular finishes rendering ng-repeat options
	setTimeout(function() {
		var $el = $(selector);
		if (!$el.length) return;
		try {
			if ($el.data('selectpicker')) {
				$el.selectpicker('refresh');
			} else {
				$el.selectpicker();
			}
		} catch(e) {}
	}, 50);
};

window.safeSelectpickerRefresh = function(selector) {
	if (typeof window.refreshSelectpickerAfterLoad === 'function') {
		window.refreshSelectpickerAfterLoad(selector);
	}
};

window.safeSelectpickerRefresh = function(selector) {
	if (typeof window.refreshSelectpickerAfterLoad === 'function') {
		window.refreshSelectpickerAfterLoad(selector);
	}
};

// ngRoute loads templates into ng-view but does not execute inline <script> tags.
// Re-run inline scripts after each view load so DataTables/filters initialize.
dms.run(['$rootScope', '$timeout', function($rootScope, $timeout) {
	$rootScope.$on('$viewContentLoaded', function() {
		$timeout(function() {
			var view = document.querySelector('[ng-view]');
			if (!view) {
				return;
			}
			var scripts = view.getElementsByTagName('script');
			for (var i = 0; i < scripts.length; i++) {
				var script = scripts[i];
				if (script.src) {
					continue;
				}
				var code = script.text || script.textContent || script.innerHTML;
				if (!code || !code.trim()) {
					continue;
				}
				try {
					if (window.jQuery && jQuery.globalEval) {
						jQuery.globalEval(code);
					} else {
						// eslint-disable-next-line no-eval
						(function() { eval(code); })();
					}
				} catch (e) {
					console.error('Error executing ng-view script:', e);
				}
			}
			// Run route init after inline scripts (ng-init runs before scripts in ng-view)
			var hash = window.location.hash || '';
			var scope = angular.element(view).scope();
			if (scope) {
				if (hash.indexOf('manageusers') !== -1 && typeof initManageUsersTable === 'function') {
					scope.loadUserList();
				} else if ((hash.indexOf('managePendingUsers') !== -1 || hash.indexOf('manageDepartmentUser') !== -1)
						&& (typeof fetchUserList === 'function' || typeof initDepartmentUserTable === 'function')) {
					scope.loadUserList();
				} else if (hash.indexOf('manageOngoingWorks') !== -1 && typeof scope.loadWorkForReport === 'function') {
					scope.loadWorkForReport();
				}
			}
			if (hash.indexOf('manageOngoingWorks') !== -1) {
				setTimeout(function() {
					window.initManageOngoingWorksSelectpickers();
				}, 900);
			}
			if (hash.indexOf('departmentWiseWorksReport') !== -1 && typeof initDeptWiseReportSelectpickers === 'function') {
				initDeptWiseReportSelectpickers();
			}
			if (hash.indexOf('photoUpdateReport') !== -1 && typeof initPhotoUpdateReportSelectpickers === 'function') {
				initPhotoUpdateReportSelectpickers();
			}
			if (hash.indexOf('dmRemarkWiseReport') !== -1 && typeof initDmRemarkReportSelectpickers === 'function') {
				initDmRemarkReportSelectpickers();
			}
		}, 50);
	});
}]);

dms.run(['$rootScope', '$timeout', function($rootScope, $timeout) {

	// Cleanup all DataTables when leaving any route to prevent CSS/JS conflicts
	$rootScope.$on('$routeChangeStart', function(event, next, current) {
		if ($.fn && $.fn.DataTable) {
			$.fn.DataTable.tables({ visible: false, api: true }).destroy();
		}
		// Remove any lingering modal backdrops
		$('.modal-backdrop').remove();
		$('body').removeClass('modal-open');
		$('body').css('padding-right', '');
	});

	$rootScope.$on('$routeChangeSuccess', function(event, current) {
		if (!current || !current.$$route || current.$$route.originalPath !== '/manageOngoingWorks') {
			return;
		}
		// Init selectpickers once after route loads — data loaders will refresh individually
		$timeout(function() {
			window.initManageOngoingWorksSelectpickers();
		}, 600);
		// Re-initialize the DataTable every time manageOngoingWorks route loads
		$timeout(function() {
			if (typeof window.initManageOngoingWorks === 'function') {
				window.initManageOngoingWorks();
			}
		}, 900);
	});

	window.addEventListener('pageshow', function(event) {
		var hash = window.location.hash || '';
		if (event.persisted && hash.indexOf('manageOngoingWorks') !== -1) {
			setTimeout(function() {
				window.initManageOngoingWorksSelectpickers();
				if (typeof window.initManageOngoingWorks === 'function') {
					window.initManageOngoingWorks();
				}
			}, 300);
		}
	});
}]);
dms
	.config( ['$routeProvider', function($routeProvider) {
		$routeProvider
		
			
//			.when('/changepassword', {
//				templateUrl: 'changepassword',
//				controller : 'CommonController'
//			})
.when('/changepassword', {
    templateUrl: rootTemplateUrl('changepassword'),
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
			
			.when('/manageusers', {
				templateUrl:  'manageusers',
				controller : 'SystemAdminController'
			})
			.when('/manageLegacyData',{
				templateUrl: rootTemplateUrl('manageLegacyData'),
				controller : 'CommonController'
			})
			.when('/manageOngoingWorks',{
				templateUrl: rootTemplateUrl('manageOngoingWorks'),
				controller : 'CommonController'
			})
			.when('/departmentWiseWorksReport',{
				templateUrl: rootTemplateUrl('departmentWiseWorksReport'),
				controller : 'CommonController'
			})
			.when('/photoUpdateReport',{
				templateUrl: rootTemplateUrl('photoUpdateReport'),
				controller : 'CommonController'
			})
			.when('/dmRemarkWiseReport',{
				templateUrl: rootTemplateUrl('dmRemarkWiseReport'),
				controller : 'CommonController'
			})
			.when('/reports', {
				templateUrl: rootTemplateUrl('reports'),
				controller : 'CommonController'
			})
			.when('/inspectionReport', {
				templateUrl: rootTemplateUrl('inspectionReport'),
				controller : 'CommonController'
			})
			.when('/workExpenditureReport', {
				templateUrl: rootTemplateUrl('workExpenditureReport'),
				controller : 'CommonController'
			})
			.when('/viewCompletedWork',{
				templateUrl: rootTemplateUrl('viewCompletedWork'),
				controller : 'CommonController'
			})
			.when('/manageAsWorks',{
				templateUrl: rootTemplateUrl('manageAsWorks'),
				controller : 'CommonController'
			})
	
			.when('/manageAllParentAS',{
				templateUrl : rootTemplateUrl('manageAllParentAS'),
				controller : 'CommonController'
			})
			.when(
				'/printSelectedAS/:parentAsId',
					{
						templateUrl : function(params) {
						return rootTemplateUrl('printSelectedAS/' + params.parentAsId);
						},
						controller : 'CommonController'
				})
				.when(
				'/printPreviewGenerateAS/:workIds',
					{
						templateUrl : function(params) {
						return rootTemplateUrl('printPreviewGenerateAS/' + params.workIds);
						},
						controller : 'CommonController'
				})
				.when(
				'/printPreviewSelectedAS/:parentAsId',
					{
						templateUrl : function(params) {
						return rootTemplateUrl('printPreviewSelectedAS/' + params.parentAsId);
						},
						controller : 'CommonController'
				})
				
			.when('/addLegacyWork', {
				templateUrl: rootTemplateUrl('addLegacyWork'),
				controller : 'CommonController'
			})
			.when('/addLegacyDataRoute', {
			templateUrl: 'addLegacyDataMapping',
			controller : 'EEController'
		     })
			.when('/addNewWork', {
				templateUrl: rootTemplateUrl('addNewWork'),
				controller : 'CommonController'
			})
			.when('/editOngoingWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editOngoingWork/' + params.id); }, 
				controller : 'CommonController'
			})
			
			.when('/editWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editWork/' + params.id); },
				controller : 'CommonController'
			})

			.when('/viewWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('viewWork/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/viewWorkData/:id', {
				templateUrl: function(params){ return rootTemplateUrl('viewWorkData/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/editLegacyData/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editLegacyData/' + params.id); }, 
				controller : 'CommonController'
			})
			
			.when('/manageImplAgency', {
				templateUrl: rootTemplateUrl('manageImplAgency'),
				controller : 'CommonController'
			})
			
			.when('/manageHead', {
				templateUrl: rootTemplateUrl('manageHead'),
				controller : 'CommonController'
			})
			
			.when('/manageScheme', {
				templateUrl: rootTemplateUrl('manageScheme'),
				controller : 'CommonController'
			})
			
			.when('/manageSor', {
				templateUrl: rootTemplateUrl('manageSor'),
				controller : 'CommonController'
			})
			.when('/editImplAgencyForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editImplAgencyForm/' + params.id); }, 
				controller : 'CommonController'
			})
			
			.when('/editHeadForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editHeadForm/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/editSchemeForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editSchemeForm/' + params.id); }, 
				controller : 'CommonController'
			})
			
			.when('/editSorForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editSorForm/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/addImplAgencyForm', {
				templateUrl: rootTemplateUrl('addImplAgencyForm'),
				controller : 'CommonController'
			})
			
			.when('/addHeadForm', {
				templateUrl: rootTemplateUrl('addHeadForm'),
				controller : 'CommonController'
			})
			
			.when('/addSchemeForm', {
				templateUrl: rootTemplateUrl('addSchemeForm'),
				controller : 'CommonController'
			})
			
			.when('/addSorForm', {
				templateUrl: rootTemplateUrl('addSorForm'),
				controller : 'CommonController'
			})
			
			.when('/manageSubEngg', {
				templateUrl: rootTemplateUrl('manageSubEngg'),
				controller : 'CommonController'
			})
			.when('/editSubEnggForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editSubEnggForm/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/addSubEnggForm', {
				templateUrl: rootTemplateUrl('addSubEnggForm'),
				controller : 'CommonController'
			})
			
			.when('/subEnggPhotoUploadReport', {
				templateUrl: rootTemplateUrl('subEnggPhotoUploadReport'),
				controller : 'CommonController'
			})
			.when('/implAgencyPhotoUploadReport', {
				templateUrl: rootTemplateUrl('implAgencyPhotoUploadReport'),
				controller : 'CommonController'
			})
			.when('/mlaRecommendedWorks', {
				templateUrl: rootTemplateUrl('mlaRecommendedWorks'),
				controller : 'CommonController'
			})
			
			
			.when('/approveAndCreateWork/:id', {
				templateUrl: function(params){ return rootTemplateUrl('approveAndCreateWork/' + params.id); }, 
				controller : 'CommonController'
			})
			
			.when('/viewMlaRecommendedWorkDet/:id', {
				templateUrl: function(params){ return rootTemplateUrl('viewMlaRecommendedWorkDet/' + params.id); }, 
				controller : 'CommonController'
			})
				.when('/manageBudgetDetails', {
				templateUrl: rootTemplateUrl('manageBudgetDetails'),
				controller : 'CommonController'
			})
			.when('/addFundForm', {
				templateUrl: rootTemplateUrl('addFundForm'),
				controller : 'CommonController'
			})
			.when('/editFundForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editFundForm/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/viewFund/:id', {
				templateUrl: function(params){ return rootTemplateUrl('viewFund/' + params.id); }, 
				controller : 'CommonController'
			})
			
				.when('/mlaFundReport', {
				templateUrl: rootTemplateUrl('mlaFundReport'),
				controller : 'CommonController'
			})
			
			.when('/printForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('printForm/' + params.id); }, 
				controller : 'CommonController'
			})
			.when('/manageSdr', {
				templateUrl: rootTemplateUrl('manageSdr'),
				controller : 'CommonController'
			})
			.when('/addSdrForm', {
				templateUrl: rootTemplateUrl('addSdrForm'),
				controller : 'CommonController'
			})
			.when('/editSdrForm/:id', {
				templateUrl: function(params){ return rootTemplateUrl('editSdrForm/' + params.id); }, 
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
			
			.when('/userchangepassword', {
				templateUrl: rootTemplateUrl('userchangepassword'),
				controller: 'CommonController'
			})

			.otherwise({
				redirectTo: '/'
			});
	}]);
