$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
	$(document).on("click", "#user tr" , function(){
		 
		 var tr = $("#user tr").index(this);
		 var id = $("#user tr:eq("+tr+") td:eq(0)").text();		 
		 goPopup(id);
		 
	});
	function goPopup(id) {
		var pop = window.open("/manage/usermodify/"+ id, "pop",
				"width=570,height=520,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");		
	}
	
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
		window.location.href = "/manage/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/manage/"+(parseInt(url[2]));
		else
			window.location.href = "/manage/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/manage/"+(parseInt(url[2]));
		else
			window.location.href = "/manage/"+(parseInt(url[2])+1);
	});

});

