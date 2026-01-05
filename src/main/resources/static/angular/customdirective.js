var dms = angular.module('dms');

dms.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			//in bytes (here 5 MB)
			var maxSizeUpload = 5242880;// 5 MB
			var maxSizeUploadPhotos = 1048576;// 1 MB
			var allowedExtensions = ['pdf', 'PDF', 'jpeg', 'JPEG', 'jpg', 'JPG'];
			var allowedExtensionsForImage = ['png', 'PNG','jpeg', 'JPEG', 'jpg', 'JPG'];

			element.bind('change', function(){

				if (element[0].files[0]) {
					var fileSize = element[0].files[0].size;
					var fileExtension = element[0].files[0].name.substring(element[0].files[0].name.lastIndexOf('.') + 1);		        	

					/*scope.maxSizeError = (fileSize > maxSizeUpload);
					scope.fileExtentionError = (allowedExtensions.indexOf(fileExtension) < 0);							
					if (scope.maxSizeError == false && scope.fileExtentionError == false) {
						scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
					}*/
					
					switch (attrs.fileModel) {
					case "asDocument":
						scope.maxSizeErrorAS = (fileSize > maxSizeUpload);
						scope.fileExtentionErrorAS = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.fileExtentionErrorAS == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}							
					break;
					case "estDocument":
						scope.maxSizeErrorEst = (fileSize > maxSizeUpload);
						scope.fileExtentionErrorEst = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.fileExtentionErrorEst == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}							
					break;
					case "ccDocument":
						scope.maxSizeErrorCc = (fileSize > maxSizeUpload);
						scope.fileExtentionErrorCc = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.fileExtentionErrorCc == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}							
					break;
					case "tsDocument":
						scope.maxSizeErrorTS = (fileSize > maxSizeUpload);					
						scope.fileExtentionErrorTS = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.maxSizeErrorTS == false && scope.fileExtentionErrorTS == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}					
					break;
					case "workOrderDocument":
						scope.maxSizeErrorWorkOrder = (fileSize > maxSizeUpload);					
						scope.fileExtentionErrorWorkOrder = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.maxSizeErrorWorkOrder == false && scope.fileExtentionErrorWorkOrder == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}					
					break;
				
					case "inspectionImages":
						scope.maxSizeErrorInspectionImages = (fileSize > maxSizeUpload);					
						scope.fileExtentionErrorInspectionImages  = (allowedExtensionsForImage.indexOf(fileExtension) < 0);							
						if (scope.maxSizeErrorInspectionImages == false && scope.fileExtentionErrorInspectionImages == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}					
					break;
					//model.File
					case "model.File":
						scope.maxSizeErrorOtherDoc = (fileSize > maxSizeUpload);					
						scope.fileExtentionErrorOtherDoc = (allowedExtensions.indexOf(fileExtension) < 0);							
						if (scope.maxSizeErrorOtherDoc == false && scope.fileExtentionErrorOtherDoc == false) {
							scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
						}					
					break;
					default:
					break;
					}	
				}
				/*scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });*/
			});
		}
	};
}]);