//Created By Yousra
var dms = angular.module('dms', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
dms.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
	}]);

// Fix relative URLs in AngularJS $http calls (base href conflict)
dms.config(['$httpProvider', function($httpProvider) {
	var ajaxBase = window.__BASE_HREF_AJAX_BASE || '';
	var apiPattern = /^(fetch|add|edit|delete|save|upload|do|get|update|remove|approve|reject|download|generate|print|send|verify|check|validate|submit|cancel|assign|transfer|bulk|export|import|mobilelogin|changeWork|createWork)/i;
	$httpProvider.interceptors.push(function() {
		return {
			request: function(config) {
				if (config.url && 
					!config.url.match(/^(\/|https?:\/\/|data:|#)/) &&
					apiPattern.test(config.url)) {
					config.url = ajaxBase + config.url;
				}
				return config;
			}
		};
	});
}]);

dms.run(['Idle', function(Idle) {
Idle.watch();
}]);
dms
	.config( ['$routeProvider', function($routeProvider) {
		$routeProvider

			.when('/workBookAndPhotos', {
				templateUrl: 'workBookAndPhotos',
				controller : 'AdminController'
			})
			.otherwise({
				redirectTo: '/'
			});
	}]);
