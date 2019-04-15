$(document).ready(function() {

	$(document).on("click", "#checklist tr" , function(){

		//클릭한 행을 tr 변수로 
		var tr = $("#checklist tr").index(this);

		var s =$("#checklist tr:eq("+tr+") td:eq(5)").text();
		//내가클릭한 테이블의 행을 판별해야하기위해  board_no 정보를 넘긴다 
		alert(s);
		window.location.href = "/checkboard/" + s ;
	});
	
/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[2] != null){
		$("#"+url[2]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/check/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/check/"+(parseInt(url[2]));
		else
			window.location.href = "/check/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/check/"+(parseInt(url[2]));
		else
			window.location.href = "/check/"+(parseInt(url[2])+1);
	});
	
});