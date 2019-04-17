$(document).ready(function() {

	var newURL =  window.location.pathname;
	var url = newURL.split('/');

	$(document).on("click", "#repairchecklist tr", function() {

	// 클릭한 행을 tr 변수로
	var tr = $("#repairchecklist tr").index(this);

	var a = $("#repairchecklist tr:eq(" + tr + ") td:eq(5)").text();
	// 내가클릭한 테이블의 행을 판별해야하기위해 board_no 정보를 넘긴다
	window.location.href = "/site/"+ url[2] +"/sitecheckview/" + a;
});

$("#write").click(function(){
	alert("글쓰기 클릭");
	var newURL =  window.location.pathname;
	var url = newURL.split('/');
	window.location.href = "/checkboard/checkadd/"+url[2];
});


	/*pagination*/
	var num = 0;
	if(url[3] != 'search'){
	
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
	
$("#search").click(function(){	
		
		if(url[3] != 'search'){
			var page = 1; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			var keyword = $("#keyword").val();
			
			if(keyword == ""){
				alert("검색내용을 입력하세요");
				return false;
			}
			
			window.location.href = "/site/"+url[2]+"/search/"+page+"/"+searchType+"/"+keyword;
		} else{
			var page = 1; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			var keyword = $("#keyword").val();
			
			if(keyword == ""){
				alert("검색내용을 입력하세요");
				return false;
			}
			
			window.location.href = "/site/"+url[2]+"/search/"+page+"/"+searchType+"/"+keyword;
		}
		
		
		
	}); // 검색 이벤트 종료

});