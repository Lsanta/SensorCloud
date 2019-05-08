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
		
		if(url[2] != 'search'){
			
			if(url[2] != null){
				$("#"+(url[2])+"").addClass('pagination-active');
			}
			
			$('.pagination-inner a').on('click', function() {
				var a = $(".pagination-inner a").index(this);
				var b = $(".pagination-inner a:eq("+a+")").attr("id");
				num = b;
				window.location.href = "/mysensor/"+num;
			});

			$('.pagination-newer').click(function(){
				if(1 > parseInt(url[2])-1 )
					window.location.href = "/mysensor/"+(parseInt(url[2]));
				else
					window.location.href = "/mysensor/"+(parseInt(url[2])-1);
			});

			$('.pagination-older').click(function(){
				window.location.href = "/mysensor/"+(parseInt(url[2])+1);
			});
		} else {
			
			if(url[3] != null){
				$("#"+(url[3])+"").addClass('pagination-active');
			}
			
			$('.pagination-inner a').on('click', function() {
				var a = $(".pagination-inner a").index(this);
				var b = $(".pagination-inner a:eq("+a+")").attr("id");
				num = b;
				window.location.href = "/mysensor/search/"+num+"/"+url[4]+"/"+url[5];
			});

			$('.pagination-newer').click(function(){
				if(1 > parseInt(url[3])-1 )
					window.location.href = "/mysensor/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
				else
					window.location.href = "/mysensor/search/"+(parseInt(url[3])-1)+"/"+url[4]+"/"+url[5];
			});

			$('.pagination-older').click(function(){
					window.location.href = "/mysensor/search/"+(parseInt(url[3])+1)+"/"+url[4]+"/"+url[5];
			});
		}
	
		$("#search").click(function(){
			if(url[2] != 'search'){
				var page = 1; // 현재 페이지 번호
				var searchType = $("#search-select option:selected").val();
				var keyword = $("#keyword").val();
				
				if(keyword.trim() == ""){
					alert("검색내용을 입력하세요");
					$("#keyword").focus();
					$("#keyword").val("");
					return false;
				}
				
				window.location.href = "/mysensor/search/"+page+"/"+searchType+"/"+keyword;
			} else{
				var page = 1; // 현재 페이지 번호
				var searchType = $("#search-select option:selected").val();
				var keyword = $("#keyword").val();
				
				if(keyword.trim() == ""){
					alert("검색내용을 입력하세요");
					$("#keyword").focus();
					$("#keyword").val("");
					return false;
				}
				
				window.location.href = "/mysensor/search/"+page+"/"+searchType+"/"+keyword;
			}

		}); //검색 이벤트 종료
		$('#keyword').keypress(function(event){
		     if ( event.which == 13 ) {
		         $('#search').click();
		     
		     }
		});
		
		/*화면 줄어들 때 리스트 자르기 */
		var tr = $("#list tr:gt(3)");
		
		if($(window).width() <= 700){
				tr.addClass("none");
			} 
		 
		 $( window ).resize(function(){
			 if($(window).width() <= 700){
					tr.addClass("none");	
				} else{
					tr.removeClass("none");
				} 
		 });
});