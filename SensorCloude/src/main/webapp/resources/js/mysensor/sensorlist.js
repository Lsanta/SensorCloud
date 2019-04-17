$(document).ready(function() {

	
	$("#add").click(function(){
		window.open("/mysensor/mysensoradd", "pop",
		"width=570,height=650,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	

	 $(document).on("click", "#list tr" , function(){
		 
		var openWin = window.open("/mysensor/mysensormod", "pop",
				 "width=570,height=650,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
		
		var tr = $(this);
		var sensor_sn = tr.children().eq(0).addClass("1");
		var sensor_kind = tr.children().eq(1).addClass("2");
		var model_name = tr.children().eq(2).addClass("3");
		var voltage = tr.children().eq(3).addClass("4");
		var manufacturer = tr.children().eq(4).addClass("5");
			
	 });
	

	
	 /*pagination*/
		var num = 0;

		var newURL =  window.location.pathname;

		var url = newURL.split('/');
		if(typeof url[2] != 'search'){
			
			if(url[2] != null){
				$("#"+url[2]+"").addClass('pagination-active');
			}

			$('.pagination-inner a').on('click', function() {
				var a = $(".pagination-inner a").index(this);
				num = a;
				window.location.href = "/mysensor/"+(num+1);
			});

			$('.pagination-newer').click(function(){
				if(1 > parseInt(url[2])-1 )
					window.location.href = "/mysensor/"+(parseInt(url[2]));
				else
					window.location.href = "/mysensor/"+(parseInt(url[2])-1);
			});

			$('.pagination-older').click(function(){
				if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
					window.location.href = "/mysensor/"+(parseInt(url[2]));
				else
					window.location.href = "/mysensor/"+(parseInt(url[2])+1);
			});
		} else {
//			
//			if(url[3] != null){
//				$("#"+url[3]+"").addClass('pagination-active');
//			}
//
//			$('.pagination-inner a').on('click', function() {
//				var a = $(".pagination-inner a").index(this);
//				num = a;
//				window.location.href = "/mysensor/"+(num+1);
//			});
//
//			$('.pagination-newer').click(function(){
//				if(1 > parseInt(url[3])-1 )
//					window.location.href = "/mysensor/"+(parseInt(url[3]));
//				else
//					window.location.href = "/mysensor/"+(parseInt(url[3])-1);
//			});
//
//			$('.pagination-older').click(function(){
//				if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[3])+1 )
//					window.location.href = "/mysensor/"+(parseInt(url[3]));
//				else
//					window.location.href = "/mysensor/"+(parseInt(url[3])+1);
//			});
		}
	
		$("#search").click(function(){
			var page = url[2]; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			if( searchType == "종류"){
				searchType = "sensor_kind";
			} else if( searchType == "제조사"){
					searchType = "manufacturer";
			}
			
			
			var keyword = $("#keyword").val();
			
			console.log(page);
			console.log(searchType);
			console.log(keyword);
			
			window.location.href = "/mysensor/search/"+page+"/"+searchType+"/"+keyword;
			
			
		});
	
});