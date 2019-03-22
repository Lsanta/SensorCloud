/**
 * 
 */

$(document).ready(function(){
		
	 $(document).on("click", "#site > tr" , function(){
		 alert("안녕");

		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 window.location.href = "site" + "/1"  ;
		 
	 });
});
