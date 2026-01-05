//Wizard

$('#workFormId .nexxt').mouseup(function () {
   /* $('#workFormId').parsley().whenValidate({
        group: 'block-' + curIndex()
    }).done(function () {
        var $active = $('.wizard .nav-tabs .nav-item .active');
        var $activeli = $active.parent("li");
        $($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
        $($activeli).next().find('a[data-toggle="tab"]').click();
    });*/
	/*var ok = $('#workFormId').parsley().validate({ group: 'block-' + curIndex() });
    if(ok == true){
    	  Swal.fire(
  				  'Validated!',
  				  'Data has been validated successfully',
  				  'success'
  				)
        $(".wizard-inner .nav-link").removeClass("active");
  		  var $active = $('.wizard .nav-tabs .nav-item .active');
          var $activeli = $active.parent("li");
          $($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
          $($activeli).next().find('a[data-toggle="tab"]').click();
      }
      else{
    		  Swal.fire(
    				  'Error!',
    				  'please fill all mandatory fields',
    				  'error'
    				)
      }*/
   
});


$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
    var $target = $(e.target);
    if ($target.hasClass('disabled')) {
        return false;
    }
});

var $sections = $('.tab-pane');
function curIndex() {
   
    return $sections.index($sections.filter('.active'));
}
$sections.each(function (index, section) {
    $(section).find(':input').attr('data-parsley-group', 'block-' + index);
});

$(".next-step").click(function (e) {
	
    var $active = $('.wizard .nav-tabs .nav-item .active');
    var $activeli = $active.parent("li");
    $($activeli).next().find('a[data-toggle="tab"]').removeClass("disabled");
    $($activeli).next().find('a[data-toggle="tab"]').click();
});

$(".prev-step").click(function (e) {
	
    var $active = $('.wizard .nav-tabs .nav-item .active');
    var $activeli = $active.parent("li");
    $($activeli).prev().find('a[data-toggle="tab"]').removeClass("disabled");
    $($activeli).prev().find('a[data-toggle="tab"]').click();
    $($activeli).find('a[data-toggle="tab"]').addClass("disabled");
});
$(".prev-step1").click(function (e) {
	
    var $active = $('.wizard .nav-tabs .nav-item .active');
    var $activeli = $active.parent("li");
    $($activeli).prev().find('a[data-toggle="tab"]').removeClass("disabled");
    $($activeli).prev().find('a[data-toggle="tab"]').click();
});

// Comment by Sumit

/*window.Parsley.addValidator('maxFileSize', {
	  validateString: function(_value, maxSize, parsleyInstance) {
	    if (!window.FormData) {
	      alert('You are making all developpers in the world cringe. Upgrade your browser!');
	      return true;
	    }
	    var files = parsleyInstance.$element[0].files;
	    return files.length != 1  || files[0].size <= maxSize * 1024;
	  },
	  requirementType: 'integer',
	  messages: {
	    en: 'This file should not be larger than specified Max Size.',
	  }
	});*/

$(document).ready(function () {
    var C_T = $('a[data-toggle="tab"].active').attr('href');
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        C_T = $(e.target).attr('href');
       /* if(C_T!=""){
            $.cookie('activeTab', C_T);
        }	*/
    });
    /*$('.stopVald').on('shown.bs.tab', function () {
    	  $('#workFormId').parsley().destroy();
    });*/
    // if(typeof($.cookie('activeTab'))!="undefined"){
    //     var activeTab = $.cookie('activeTab');
    //     $('a[href="' + activeTab + '"]').removeClass('disabled').tab('show');
    //     $('a[href="' + activeTab + '"]').parent("li").prevAll().find('a[data-toggle="tab"]').removeClass("disabled");;
    // } else {
    //     //alert('undefined');
    // }
});
/*$("#workFormId").on("submit", function(e){
    $(this).parsley().validate();
    if($(this).parsley().isValid()){
  	  Swal.fire(
				  'Saved!',
				  'Data has been filled successfully',
				  'success'
				)
      $(".wizard-inner .nav-link").removeClass("active");
    }
    else(
  		  Swal.fire(
  				  'Error!',
  				  'please fill mandatory fields',
  				  'danger'
  				)
    )
    //e.preventDefault();
  })*/

// comment by Sumit
/*window.ParsleyValidator.addValidator('fileextension', function (value, requirement) {
    var fileExtension = value.split('.').pop().toLowerCase();
    
    return fileExtension === requirement;
}, 32).addMessage('en', 'fileextension', 'Please upload valid format File.');*/







