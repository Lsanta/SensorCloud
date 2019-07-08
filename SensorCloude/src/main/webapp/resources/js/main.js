/**
 * 
 */
$(document).ready(function(){
		
	 $(document).on("click", ".col-sm-6" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 var n = $(this).find('p').eq(1).text();
		 window.location.href = "/site/" + n  ;
		 
	 });
	    
	 var tr = $("#site tr:gt(4)");
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

			
		
		 window.location.href = "/checkboard/" + n  ;
		 
	 });
	 
});
