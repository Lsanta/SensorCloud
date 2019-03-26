/**
 * 
 */

$(document).ready(function(){
		
	 $(document).on("click", "#site tr" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 var tr = $("#site tr").index(this);
		 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
		 alert(n);
		 window.location.href = "/site/" + n  ;
		 
	 });
});
