/*현장 리스트*/

/* 테이블 클릭 후 이동*/
$(document).on("click", "#site tr" , function(){
	 
	 var tr = $("#site tr").index(this);
	 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
	 window.location.href = "/site/" + n  ;
	 
});


/*검색 기능 */




/*pagination*/
$(document).ready(function() {
	var num = 0;

	var newURL =  window.location.pathname;
	var url = newURL.split('/');
	if(url[2] != null){
		$("#"+url[2]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/sitelist/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/sitelist/"+(parseInt(url[2]));
		else
			window.location.href = "/sitelist/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/sitelist/"+(parseInt(url[2]));
		else
			window.location.href = "/sitelist/"+(parseInt(url[2])+1);
	});
	
	
	
});




/*현장 등록*/
