$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
	$(document).on("click", "#checklist tr" , function(){
		
		//클릭한 행을 tr 변수로 
		 var tr = $("#checklist tr").index(this);
		 
		 alert(2222);
		 
		 var s =$("#checklist tr:eq("+tr+") td:eq(5) ").text();
		 
		 
		 //내가클릭한 테이블의 행을 판별해야하기위해  board_no 정보를 넘긴다 
		 window.location.href = "/checkboard/" + s ;
		 
	 });
	
	

});