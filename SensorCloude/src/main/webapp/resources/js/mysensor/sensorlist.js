$(document).ready(function() {

	
	$("#add").click(function(){
		window.open("/mysensor/mysensoradd", "pop",
		"width=570,height=420,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	
	
	 $(document).on("click", "#list tr" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		var tr = $(this);
		var sensor_sn = tr.children().eq(0).text();
		var sensor_kind = tr.children().eq(1).text();
		var model_name = tr.children().eq(2).text();
		var voltage = tr.children().eq(3).text();
		var manufacturer = tr.children().eq(4).text(); 
		
		
		var openWin = window.open("/mysensor/mysensormod", "pop",
					  "width=570,height=420,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
		
		openWin.document.getElementById("sensor_sn").value = document.getElementById("sensor_sn").value;
		openWin.document.getElementById("sensor_kind").value = document.getElementById("sensor_kind").value;
		openWin.document.getElementById("model_name").value = document.getElementById("model_name").value;
		openWin.document.getElementById("voltage").value = document.getElementById("voltage").value;
		openWin.document.getElementById("manufacturer").value = document.getElementById("manufacturer").value;
		

		
	 });
	
	
	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
});