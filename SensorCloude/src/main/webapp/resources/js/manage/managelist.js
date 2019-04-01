$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
	$(document).on("click", "#user tr" , function(){
		 
		 var tr = $("#user tr").index(this);
		 var id = $("#user tr:eq("+tr+") td:eq(0)").text();		 
		 goPopup(id);
		 
	});
	function goPopup(id) {
		var pop = window.open("/manage/usermodify/"+ id, "pop",
				"width=570,height=520,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");		
	}
	


});


/*function goPopup() {
	var pop = window.open("/manage/usermodify", "pop",
			"width=570,height=520, resizable=yes");		
}
*/

