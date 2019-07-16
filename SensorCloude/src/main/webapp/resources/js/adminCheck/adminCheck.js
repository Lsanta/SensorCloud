$(document).ready(function() {

	$(document).on("click", "#checklist0 div.d-flex" , function(){
		var s = $(this).children().eq(0).attr("id");
		
		window.location.href = "/adminCheckPage/checkboard/" + s ;
	});
	
	$(document).on("click", "#checklist1 div.d-flex" , function(){

		var s = $(this).children().eq(0).attr("id");

		//내가클릭한 테이블의 행을 판별해야하기위해  board_no 정보를 넘긴다 
		window.location.href = "/adminCheckPage/checkboard/" + s ;
	});
	
	$(document).on("click", "#checklist2 div.d-flex" , function(){

		var s = $(this).children().eq(0).attr("id");

		//내가클릭한 테이블의 행을 판별해야하기위해  board_no 정보를 넘긴다 
		window.location.href = "/adminCheckPage/checkboard/" + s ;
	});

	/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	
	var status = 0;
	

	if(url[2] != 'checkManagesearch'){	

		if(url[2] != null){	
			$("#openPage #"+url[2]+"").addClass('pagination-active');
			$("#fixedPage #"+url[3]+"").addClass('pagination-active');
			$("#closePage #"+url[4]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() { // 밑에 숫자 클릭 시 
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			var clickPage = $(this).parent().parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/adminCheckPage/"+num+"/"+(parseInt(url[3]))+"/"+ (parseInt(url[4]));
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+num+"/"+ (parseInt(url[4]));
			} else if(clickPage == "closePage") {
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+num;
			}
		});
		
		$('.pagination-newer').click(function(){ //이전(<) 버튼 클릭 시 
			
			var clickPage = $(this).parent().attr("id");
			if(clickPage == "openPage") {
				if(1 > parseInt(url[2])-1 )
					window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
				else
					window.location.href = "/adminCheckPage/"+(parseInt(url[2])-1)+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
			} else if(clickPage == "fixedPage") { 
				if(1 > parseInt(url[3])-1 )
					window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
				else
					window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3])-1)+"/"+(parseInt(url[4]));
			}  else if(clickPage == "closePage") {
				if(1 > parseInt(url[4])-1 )
					window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
				else
					window.location.href =  "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4])-1);
			}
			
		});

		$('.pagination-older').click(function(){ // 다음(>) 버튼 클릭 시 
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/adminCheckPage/"+(parseInt(url[2])+1)+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3])+1)+"/"+(parseInt(url[4]));
			}  else if(clickPage == "closePage") {
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4])+1);
			}
		});
		$(".firstpage").click(function(){ // 처음으로 ( << ) 버튼 클릭시 
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/adminCheckPage/1/"+ (parseInt(url[3])) + "/" + (parseInt(url[4]));
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/adminCheckPage/"+ (parseInt(url[2])) + "/1/" + (parseInt(url[4]));
			}  else if(clickPage == "closePage") {
				window.location.href = "/adminCheckPage/"+ (parseInt(url[2])) + "/" + (parseInt(url[3])) + "/1";
			}
			
			
		});
		
		$(".lastpage").click(function(){ // 마지막으로 ( >> ) 버튼 클릭시 
			var clickPage = $(this).parent().attr("id");
			if(clickPage == "openPage") {
				window.location.href = "/adminCheckPage/"+$("#lastNum0").text()+"/"+(parseInt(url[3]))+"/"+(parseInt(url[4]));
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+ $("#lastNum1").text()+"/"+(parseInt(url[4]));
			}  else if(clickPage == "closePage") {
				window.location.href = "/adminCheckPage/"+(parseInt(url[2]))+"/"+(parseInt(url[3]))+"/"+ $("#lastNum2").text();
			}
		});

	} else { 
		if(url[3] != null){
			$("#openPage #"+url[3]+"").addClass('pagination-active');
			$("#fixedPage #"+url[4]+"").addClass('pagination-active');
			$("#closePage #"+url[5]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			var clickPage = $(this).parent().parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/checkManagesearch/"+num+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+num+"/"+ (parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "closePage") {
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+num+"/"+url[6]+"/"+url[7];
			}
		
		});
		
		$('.pagination-newer').click(function(){
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				if(1 > parseInt(url[3])-1 )
					window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
				else
					window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3])-1)+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "fixedPage") { 
				if(1 > parseInt(url[4])-1 )
					window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
				else
					window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4])-1)+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			}  else if(clickPage == "closePage") {
				if(1 > parseInt(url[5])-1 )
					window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
				else
					window.location.href =  "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5])-1)+"/"+url[6]+"/"+url[7];
			}
		});

		$('.pagination-older').click(function(){  // 다음(>) 버튼 클릭 시 
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3])+1)+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4])+1)+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			}  else if(clickPage == "closePage") {
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5])+1)+"/"+url[6]+"/"+url[7];
			}
			
		});
		
		$(".firstpage").click(function(){
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/checkManagesearch/1/"+ (parseInt(url[4])) + "/" + (parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/checkManagesearch/"+ (parseInt(url[3])) + "/1/" + (parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			}  else if(clickPage == "closePage") {
				window.location.href = "/checkboard/checkManagesearch/"+ (parseInt(url[3])) + "/" + (parseInt(url[4])) + "/1"+"/"+url[6]+"/"+url[7];
			}
		});
		
		$(".lastpage").click(function(){
			var clickPage = $(this).parent().attr("id");
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/checkManagesearch/"+$("#lastNum0").text()+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+ $("#lastNum1").text()+"/"+(parseInt(url[5]))+"/"+url[6]+"/"+url[7];
			}  else if(clickPage == "closePage") {
				window.location.href = "/checkboard/checkManagesearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+$("#lastNum2").text()+"/"+url[6]+"/"+url[7];
			}
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

			window.location.href = "/checkboard/checkManagesearch/1/1/1/"+searchType+"/"+keyword;
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

			window.location.href = "/checkboard/checkManagesearch/1/1/1/"+page+"/"+searchType+"/"+keyword;
		}



	}); // 검색 이벤트 종료
	if(url[2] == "checkManagesearch") {
		$("#pills-today0").addClass('active show');
		$("#pills-today1").addClass('active show');
		$("#pills-today2").addClass('active show');	
	}
	
	if(url[1] == "adminCheckPage" && url[5] == null) {
		$("#pills-today0").addClass('active show');
		$("#pills-today1").addClass('active show');
		$("#pills-today2").addClass('active show');	
	} 
	
	if(url[2] == "admindataSearch") {
		if(url[6] == 0) {
			$("#pills-today0").addClass('active show');
		} else if(url[6] == 1) {
			$("#pills-month0").addClass('active show');
		} else if(url[6] == 3){
			$("#pills-tmonth0").addClass('active show');
		}
		
		if(url[7] == 0) {
			$("#pills-today1").addClass('active show');
		} else if(url[7] == 1) {
			$("#pills-month1").addClass('active show');
		} else if(url[7] == 3){
			$("#pills-tmonth1").addClass('active show');
		}
		
		if(url[8] == 0) {
			$("#pills-today2").addClass('active show');
		} else if(url[8] == 1) {
			$("#pills-month2").addClass('active show');
		} else if(url[8] == 3){
			$("#pills-tmonth2").addClass('active show');
		}
		
	}
	
	$("#pills-today0").click(function(){
		// open인 게시판 전체보기 클릭 시 
		// page0 / page1 / page2 / data0 / data1 / data2 / status
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/0/0/0";
		} else {
			window.location.href = "/checkboard/admindataSearch/1/"+url[4]+"/"+url[5]+"/0/"+url[7]+"/"+url[8]+"/0";
		}
	});
	
	$("#pills-month0").click(function(){
		// open인 게시판 1개월 클릭 시 
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/1/0/0/0";
		} else {
		window.location.href = "/checkboard/admindataSearch/1/"+url[4]+"/"+url[5]+"/1/"+url[7]+"/"+url[8]+"/0";
		}
		
	});
	
	$("#pills-tmonth0").click(function(){
		// open인 게시판 3개월 클릭 시 
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/3/0/0/0";
		} else {
		window.location.href = "/checkboard/admindataSearch/1/"+url[4]+"/"+url[5]+"/3/"+url[7]+"/"+url[8]+"/0";
		}
	});
	
	$("#pills-today1").click(function(){
		// fixed인 게시판 전체보기 클릭 시 
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/0/0/1";
		} else {
		window.location.href = "/checkboard/admindataSearch/"+url[3]+"/1/"+url[5]+"/"+url[6]+"/0/"+url[8]+"/1";
		}
	});
	
	$("#pills-month1").click(function(){
		// fixed인 게시판 1개월 클릭 시
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/1/0/1";
		} else {
		window.location.href = "/checkboard/admindataSearch/"+url[3]+"/1/"+url[5]+"/"+url[6]+"/1/"+url[8]+"/1";
		}
	});
	
	$("#pills-tmonth1").click(function(){
		// fixed인 게시판 3개월 클릭 시
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/3/0/1";
		} else {
		window.location.href = "/checkboard/admindataSearch/"+url[3]+"/1/"+url[5]+"/"+url[6]+"/3/"+url[8]+"/1";
		}
	});
	
	$("#pills-today2").click(function(){
		// close인 게시판 전체보기 클릭 시
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/0/0/2";
		} else {
		window.location.href = "/checkboard/admindataSearch/"+url[3]+"/"+url[4]+"/1/"+url[6]+"/"+url[7]+"/0/2";
		}
	});
	
	$("#pills-month2").click(function(){
		// close인 게시판 1개월 클릭 시
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/0/1/2";
		} else {
		window.location.href = "/checkboard/admindataSearch/"+url[3]+"/"+url[4]+"/1/"+url[6]+"/"+url[7]+"/1/2";
		}
	});
	
	$("#pills-tmonth2").click(function(){
		// close인 게시판 3개월 클릭 시
		if(url[2] != 'admindataSearch') {
			window.location.href = "/checkboard/admindataSearch/1/1/1/0/0/3/2";
		} else {
			window.location.href = "/checkboard/admindataSearch/"+url[3]+"/"+url[4]+"/1/"+url[6]+"/"+url[7]+"/3/2";
		}
		
	});
	
	
	if(url[2] == "admindataSearch"){

		if(url[3] != null){
			$("#openPage #"+url[3]+"").addClass('pagination-active');
			$("#fixedPage #"+url[4]+"").addClass('pagination-active');
			$("#closePage #"+url[5]+"").addClass('pagination-active');
		}

	
		$('.pagination-inner a').on('click', function() { // 밑에 숫자 클릭 시 
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			var clickPage = $(this).parent().parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/admindataSearch/"+num+"/"+(parseInt(url[4])) + "/" + (parseInt(url[5])) + "/" + (parseInt(url[6])) +  "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+ num + "/" + (parseInt(url[5]))+ "/" + (parseInt(url[6])) +  "/" (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
			} else if(clickPage == "closePage") {
				window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4])) + "/" + num + "/" + (parseInt(url[6])) +  "/" (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
			}
		});

		$('.pagination-newer').click(function(){ //이전(<) 버튼 클릭 시 
			
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				if(1 > parseInt(url[3])-1 )
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
				else
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3])-1)+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
			} else if(clickPage == "fixedPage") { 
				if(1 > parseInt(url[4])-1 )
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
				else
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4])-1)+"/"+(parseInt(url[5]))+"/"(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
			}  else if(clickPage == "closePage") {
				if(1 > parseInt(url[5])-1 )
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
				else
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5])-1)+"/"(parseInt(url[6])) + "/" + (parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
			}
			
		});

//		$('.pagination-older').click(function(){
//			if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[3])+1)
//				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])+"/"+url[4]);
//			else
//				window.location.href = "/checkboard/dataSearch/"+(parseInt(url[3])+1)+"/"+url[4];
//		});
		$('.pagination-older').click(function(){ // 다음(>) 버튼 클릭 시 
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3])+1)+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
			} else if(clickPage == "fixedPage") { 
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4])+1)+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
			}  else if(clickPage == "closePage") {
					window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5])+1)+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
			}
		});
		
		$(".firstpage").click(function(){ // 처음( << ) 버튼 클릭 시 
			var clickPage = $(this).parent().attr("id");
			
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/admindataSearch/1/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/admindataSearch/"+ (parseInt(url[3])) + "/1/" + (parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
			}  else if(clickPage == "closePage") {
				window.location.href = "/checkboard/admindataSearch/"+ (parseInt(url[3])) + "/" + (parseInt(url[4])) + "/1"+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
			}
		});
		
		$(".lastpage").click(function(){// 마지막( >> ) 버튼 클릭 시 
			var clickPage = $(this).parent().attr("id");
			if(clickPage == "openPage") {
				window.location.href = "/checkboard/admindataSearch/"+$("#lastNum0").text()+"/"+(parseInt(url[4]))+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/0";
			} else if(clickPage == "fixedPage") { 
				window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+ $("#lastNum1").text()+"/"+(parseInt(url[5]))+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/1";
			}  else if(clickPage == "closePage") {
				window.location.href = "/checkboard/admindataSearch/"+(parseInt(url[3]))+"/"+(parseInt(url[4]))+"/"+$("#lastNum2").text()+"/"+(parseInt(url[6])) + "/" +(parseInt(url[7])) + "/" + (parseInt(url[8])) +"/2";
			}
		});
	}
	
	$('#keyword').keypress(function(event){
		if ( event.which == 13 ) {
			$('#search').click();

		}
	});

	/*화면 줄어들 때 리스트 자르기 */
	var tr = $("#checklist tr:gt(4)");

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