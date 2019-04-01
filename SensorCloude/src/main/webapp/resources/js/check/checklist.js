$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});


	$(document).on("click", "#checklist tr" , function(){

		//클릭한 행을 tr 변수로 
		var tr = $("#checklist tr").index(this);

		var s =$("#checklist tr:eq("+tr+") td:eq(5)").text();
		//내가클릭한 테이블의 행을 판별해야하기위해  board_no 정보를 넘긴다 
		window.location.href = "/checkboard/" + s ;
	});
	
/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	console.log(newURL.substring(7,8));
	if(newURL.substring(7,8) != null){
		$("#"+newURL.substring(7,8)+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/check/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(newURL.substring(7,8))-1 )
			window.location.href = "/check/"+(parseInt(newURL.substring(7,8)));
		else
			window.location.href = "/check/"+(parseInt(newURL.substring(7,8))-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(newURL.substring(7,8))+1 )
			window.location.href = "/check/"+(parseInt(newURL.substring(7,8)));
		else
			window.location.href = "/check/"+(parseInt(newURL.substring(7,8))+1);
	});

});