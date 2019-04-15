$(document).ready(function() {

	
	$("#add").click(function(){
		window.open("/mysensor/mysensoradd", "pop",
		"width=570,height=650,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	

	 $(document).on("click", "#list tr" , function(){
		 
		var openWin = window.open("/mysensor/mysensormod", "pop",
				 "width=570,height=650,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
		
		var tr = $(this);
		var sensor_sn = tr.children().eq(0).addClass("1");
		var sensor_kind = tr.children().eq(1).addClass("2");
		var model_name = tr.children().eq(2).addClass("3");
		var voltage = tr.children().eq(3).addClass("4");
		var manufacturer = tr.children().eq(4).addClass("5");
			
	 });
	

	
	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
});