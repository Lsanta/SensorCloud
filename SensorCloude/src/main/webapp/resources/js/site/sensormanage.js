$(document).ready(function() {



	$("#add").click(function(){
	

		window.location.href = "/site/1/sensoradd/";
	});
	

	var num = 0;
	if(url[3] != 'search'){
	
	if(url[4] != null){
		$("#"+url[4]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/site/1/sensormanage/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[4])-1 )
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4]));
		else
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[4])+1 )
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4]));
		else
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4])+1);
	});
	
  } else {
	  if(url[4] != null){
			$("#"+url[4]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			num = a;
			window.location.href = "/site/1/search/"+(num+1)+"/"+url[5]+"/"+url[6];
		});

		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[4])-1 )
				window.location.href = "/site/"+url[2]+"/search/"+(parseInt(url[4]))+"/"+url[5]+"/"+url[6];
			else
				window.location.href = "/site/"+url[2]+"/search/"+(parseInt(url[4])-1)+"/"+url[5]+"/"+url[6];
		});

		$('.pagination-older').click(function(){
			if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
				window.location.href = "/site/"+url[2]+"/search/"+(parseInt(url[4])+"/"+url[5]+"/"+url[6]);
			else
				window.location.href = "/site/"+url[2]+"/search/"+(parseInt(url[4])+1)+"/"+url[5]+"/"+url[6];
		});

  } // else 종료
});