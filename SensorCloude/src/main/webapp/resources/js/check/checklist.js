$(document).ready(function() {

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

	var url = newURL.split('/');
	if(url[2] != 'search'){

		if(url[2] != null){
			$("#"+url[2]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/check/"+num;
		});

		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[2])-1 )
				window.location.href = "/check/"+(parseInt(url[2]));
			else
				window.location.href = "/check/"+(parseInt(url[2])-1);
		});

		$('.pagination-older').click(function(){
			window.location.href = "/check/"+(parseInt(url[2])+1);
		});
		$(".firstpage").click(function(){
			window.location.href = "/check/1";
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/check/"+$("#lastNum").text();
		});

	} else{
		if(url[3] != null){
			$("#"+url[3]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/checkboard/search/"+num+"/"+url[4]+"/"+url[5];
		});
		
		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[3])-1 )
				window.location.href = "/checkboard/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
			else
				window.location.href = "/checkboard/search/"+(parseInt(url[3])-1)+"/"+url[4]+"/"+url[5];
		});

		$('.pagination-older').click(function(){
			window.location.href = "/checkboard/search/"+(parseInt(url[3])+1)+"/"+url[4]+"/"+url[5];
		});
		
		$(".firstpage").click(function(){
			window.location.href = "/checkboard/search/1/"+url[4]+"/"+url[5];
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/checkboard/search/"+$("#lastNum").text()+"/"+url[4]+"/"+url[5];
		});
	} // else 종료

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

			window.location.href = "/checkboard/search/"+page+"/"+searchType+"/"+keyword;
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

			window.location.href = "/checkboard/search/"+page+"/"+searchType+"/"+keyword;
		}



	}); // 검색 이벤트 종료



	$("#dateChange").change(function(){

		var data = $("#dateChange > option:selected").val();
		window.location.href = "/checkboard/dataSearch/1/"+ data;
	});

	if(url[2] == "dataSearch"){


		var opt_vals = [];
		$("#dateChange > option").each(function(){
			opt_vals.push($(this).val()); 
		});

		for(var i=0; i < opt_vals.length; i++ ){
			var optV = opt_vals[i];

			if(optV == Number(url[4]) )
				$("#dateChange > option").eq(i).prop("selected", true);
		} 


		if(url[3] != null){
			$("#"+url[3]+"").addClass('pagination-active');
		}

	
		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/checkboard/dataSearch/"+num+"/"+url[4];
		});

		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[3])-1 )
				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3]))+"/"+url[4];
			else
				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])-1)+"/"+url[4];
		});

//		$('.pagination-older').click(function(){
//			if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[3])+1)
//				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])+"/"+url[4]);
//			else
//				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])+1)+"/"+url[4];
//		});
		$('.pagination-older').click(function(){
			window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])+1)+"/"+url[4];
		});
		
		$(".firstpage").click(function(){
			window.location.href = "/checkboard/dataSearch/1/"+url[4];
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/checkboard/dataSearch/"+$("#lastNum").text()+"/"+url[4];
		});
	}
	
	$('#keyword').keypress(function(event){
		if ( event.which == 13 ) {
			$('#search').click();

		}
	});

//	/*화면 줄어들 때 리스트 자르기 */
//	var tr = $("#checklist tr:gt(4)");
//
//	if($(window).width() <= 700){
//		tr.addClass("none");
//	} 
//
//	$( window ).resize(function(){
//		if($(window).width() <= 700){
//			tr.addClass("none");	
//		} else{
//			tr.removeClass("none");
//		} 
//	});

});