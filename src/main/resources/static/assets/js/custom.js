
$('.logo-slider').slick({
	slidesToShow: 6,
	slidesToScroll: 1,
	dots: false,
	arrows: false,
	autoplay: true,
	autoplaySpeed: 1500,
	speed: 2000,
	responsive: [{
			breakpoint: 1600,
			settings: {
				slidesToShow: 6
			}
		},
		{
			breakpoint: 992,
			settings: {
				slidesToShow: 3
			}
		},
		{
			breakpoint: 600,
			settings: {
				slidesToShow: 2
			}
		}
	]
});


$(document).ready(function () {

	$('#gotop').click(function (e) {
		e.preventDefault();
		$("html, body").animate({
			scrollTop: 0
		}, 800);
		return false;
	});
});


