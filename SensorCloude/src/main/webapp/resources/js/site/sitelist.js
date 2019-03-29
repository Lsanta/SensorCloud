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
	
	var newURL =  window.location.pathname;
	
	console.log(newURL.substring(9,10));
	if(newURL.substring(9,10) != null){
		$("#"+newURL.substring(9,10)+"").addClass('pagination-active');
	}
	
	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/sitelist"+(num+1);
	});
	
	$('.pagination-newer').click(function(){
		if(1 > parseInt(newURL.substring(9,10))-1 )
			window.location.href = "/sitelist"+(parseInt(newURL.substring(9,10)));
		else
			window.location.href = "/sitelist"+(parseInt(newURL.substring(9,10))-1);
	});
	
	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(newURL.substring(9,10))+1 )
			window.location.href = "/sitelist"+(parseInt(newURL.substring(9,10)));
		else
			window.location.href = "/sitelist"+(parseInt(newURL.substring(9,10))+1);
	});

});




/*현장 등록*/
