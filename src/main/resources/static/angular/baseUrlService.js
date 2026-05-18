// Base URL Service for handling context path
var dms = angular.module('dms');

dms.factory('BaseUrlService', function() {
    return {
        getBaseUrl: function() {
            var path = window.location.pathname;
            var firstPart = path.split('/')[1];

            // If context path is 'anuppur', return it with slash
            if (firstPart === 'anuppur') {
                return '/' + firstPart;
            }

            // Else no context path (like in LIVE), return empty
            return '';
        }
    };
});

// Also make it available as a global function for non-Angular code
window.getBaseUrl = function() {
    var path = window.location.pathname;
    var firstPart = path.split('/')[1];

    if (firstPart === 'anuppur') {
        return '/' + firstPart;
    }

    return '';
};
