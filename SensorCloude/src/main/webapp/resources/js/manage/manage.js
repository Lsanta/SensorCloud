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
				"width=570,height=600,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");

	}


	/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[2] != 'search'){
		if(url[2] != null){
			$("#"+url[2]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/manage/"+num;
		});
		
		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[2])-1 )
				window.location.href = "/manage/"+(parseInt(url[2]));
			else
				window.location.href = "/manage/"+(parseInt(url[2])-1);
		});

		$('.pagination-older').click(function(){
			window.location.href = "/manage/"+(parseInt(url[2])+1);
		});

	} else {
		if(url[3] != null){
			$("#"+url[3]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/manage/search/"+num+"/"+url[4]+"/"+url[5];
		});

		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[3])-1 )
				window.location.href = "/manage/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
			else
				window.location.href = "/manage/search/"+(parseInt(url[3])-1)+"/"+url[4]+"/"+url[5];
		});

		$('.pagination-older').click(function(){
			window.location.href = "/manage/search/"+(parseInt(url[3])+1)+"/"+url[4]+"/"+url[5];
		});
	} //else 종료

	$("#search").click(function(){	

		if(url[2] != 'search'){
			var page = 1; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			var keyword = $("#keyword").val();

			if(keyword.trim() == ""){
				alert("검색내용을 입력하세요");
				$("#keyword").val("");
				$("#keyword").focus();
				return false;
			}

			window.location.href = "/manage/search/"+page+"/"+searchType+"/"+keyword;
		} else{
			var page = 1; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			var keyword = $("#keyword").val();

			if(keyword.trim() == ""){
				alert("검색내용을 입력하세요");
				$("#keyword").val("");
				$("#keyword").focus();
				return false;
			}

			window.location.href = "/manage/search/"+page+"/"+searchType+"/"+keyword;
		}
	}); // 검색 이벤트 종료
	$('#keyword').keypress(function(event){
		if ( event.which == 13 ) {
			$('#search').click();

		}
	});
});