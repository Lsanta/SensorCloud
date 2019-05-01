/**
 * 
 */
$(document).ready(function(){
		
	 $(document).on("click", "#site tr" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 var tr = $("#site tr").index(this);
		 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
		 window.location.href = "/site/" + n  ;
		 
	 });
	    
	 var tr = $("#site tr:gt(3)");
	 var div = $(".grid .images:gt(2)");
	 
	 if($(window).width() <= 700){
			tr.addClass("none");
			div.removeClass().addClass("none");
		} 
	 
	 $( window ).resize(function(){
		 if($(window).width() <= 700){
				tr.addClass("none");
				div.removeClass().addClass("none");
			} else{
				tr.removeClass("none");
				div.removeClass().addClass("images");
			} 
	 });
	 
	 $(document).on("click", ".images" , function(){
		 
		var title = $(".images").index(this);
		var n = $(".images:eq("+title+") span:eq(0)").text();

			
			alert(n);
		 window.location.href = "/checkboard/" + n  ;
		 
	 });
	 
});
