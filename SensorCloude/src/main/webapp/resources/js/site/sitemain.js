$(document).ready(function(){
		
	 $(document).on("click", "h1" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 var sid = $("#sid").text();
		 
		 window.location.href = "/site/" + sid  ;
		 
	 });
});