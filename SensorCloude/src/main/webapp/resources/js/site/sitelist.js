/*현장 리스트*/

/* 테이블 클릭 후 이동*/
$(document).on("click", "#site tr" , function(){
	 
	 var tr = $("#site tr").index(this);
	 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
	 window.location.href = "/site/" + n  ;
	 
});


/*pagination*/
$(document).ready(function() {
	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});

});




/*현장 등록*/
