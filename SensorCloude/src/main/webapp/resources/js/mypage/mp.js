$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	$('.update').on('click', function() {
		window.location.href = "modifymypage";
	})
	
});