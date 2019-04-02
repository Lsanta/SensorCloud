$(document).ready(function() {

/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[4] != null){
		$("#"+url[4]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/site/1/siterepair/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[4])-1 )
			window.location.href = "/site/"+url[2]+"/siterepair/"+(parseInt(url[4]));
		else
			window.location.href = "/site/"+url[2]+"/siterepair/"+(parseInt(url[4])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[4])+1 )
			window.location.href = "/site/"+url[2]+"/siterepair/"+(parseInt(url[4]));
		else
			window.location.href = "/site/"+url[2]+"/siterepair/"+(parseInt(url[4])+1);
	});

});