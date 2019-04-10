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
	 
	 if($(window).width() <= 700){
			tr.addClass("none");
		} 
	 
	 $( window ).resize(function(){
		 if($(window).width() <= 700){
				tr.addClass("none");
			} else{
				tr.removeClass("none");
			} 
	 });

});
