$(document).ready(function() {

	
	$("#add").click(function(){
		window.open("/mysensor/mysensoradd", "pop",
		"width=570,height=420, resizable=yes");
	});
	
	$(".delete").on('click', function(){
		var sn = $(this).parent();
		var sn2 = sn.siblings().first();
		var sn3 = sn2.text(); 
			
		console.log(sn3);
		
		
		var query = {
				sensor_sn : $("#sensor_sn").val(),
		}
	});
	
	
	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
});