/*현장 리스트*/

/* 테이블 클릭 후 이동*/
$(document).on("click", "#site tr" , function(){
	 
	 var tr = $("#site tr").index(this);
	 var n = $("#site tr:eq("+tr+") td:eq(4)").text();
	 window.location.href = "/site/" + n  ;
	 
});


$(document).ready(function() {
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
		num = a;
		window.location.href = "/sitelist/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/sitelist/"+(parseInt(url[2]));
		else
			window.location.href = "/sitelist/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/sitelist/"+(parseInt(url[2]));
		else
			window.location.href = "/sitelist/"+(parseInt(url[2])+1);
	});
} else {
	if(url[3] != null){
		$("#"+url[3]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/site/search/"+(num+1)+"/"+url[4]+"/"+url[5];
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[3])-1 )
			window.location.href = "/site/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
		else
			window.location.href = "/site/search/"+(parseInt(url[3])-1)+"/"+url[4]+"/"+url[5];
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[3])+1 )
			window.location.href = "/site/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
		else
			window.location.href = "/site/search/"+(parseInt(url[3])+1)+"/"+url[4]+"/"+url[5];
	});
} // else 종료
	
	/*검색 기능 */
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
			
			window.location.href = "/site/search/"+page+"/"+searchType+"/"+keyword;
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
			
			window.location.href = "/site/search/"+page+"/"+searchType+"/"+keyword;
		}
	}); // 검색 이벤트 종료
	
	$('#keyword').keypress(function(event){
	     if ( event.which == 13 ) {
	         $('#search').click();
	     
	     }
	});
	
	/*화면 줄어들 때 리스트 자르기 */
	var tr = $("#site tr:gt(3)");
	
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