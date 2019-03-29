/*현장 리스트*/

/* 테이블 클릭 후 이동*/
$(document).on("click", "#site tr" , function(){
	 
	 var tr = $("#site tr").index(this);
	 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
	 window.location.href = "/site/" + n  ;
	 
});


/*pagination*/
$(document).ready(function() {
	var num = 0;
	
	$('.pagination-inner a').on('click', function() {
		alert("gd");
		$(".pagination-inner a:eq("+num+")").removeClass('pagination-active');
		var a = $(".pagination-inner a").index(this);
		num = a;
		
		$(this).addClass('pagination-active');

		var query = {num:$(this).text()};
		
		window.location.href = "/sitelist"+(num+1);
	});

	
});




/*현장 등록*/
