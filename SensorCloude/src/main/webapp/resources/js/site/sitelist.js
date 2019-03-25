/**
 * 
 */

/**
 * 
 */

$(document).ready(function(){
		
	 $(document).on("click", "#site > tr" , function(){
		 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
		 var sid = $("#sid").text();
		 alert(sid);
		 window.location.href = "site/" + sid  ;
		 
	 });
});

$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});

});