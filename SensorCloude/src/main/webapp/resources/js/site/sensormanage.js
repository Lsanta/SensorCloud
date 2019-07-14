$(document).ready(function() {

	var newURL =  window.location.pathname;
	var url = newURL.split('/');

	$(document).on("click", "#sensorlist tr", function() {

		var openWin = window.open("/site/installsensormod/"+url[2], "pop",
				 "width=500,height=510,top="+(screen.availHeight/2-350)+",left="+(screen.availWidth/2-300)+"resizable=yes");
		
		var tr = $(this);
		var sensor_sn = tr.children().eq(0).addClass("1");
		var program_var = tr.children().eq(1).addClass("2");
		var upper_limit = tr.children().eq(2).addClass("3");
		var lower_limit = tr.children().eq(3).addClass("4");
		var sensor_id = tr.children().eq(4).addClass("5");


	});

	////////////////////*pagination*//////////////////////////////////

	$("#add").click(function(){
		window.open("/site/installsensoradd/"+url[2], "pop",
		"width=500,height=620,top="+(screen.availHeight/2-350)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	
	var num = 0;
	if(url[3] != 'search1'){

		if(url[4] != null){
			$("#"+url[4]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/site/1/sensormanage/"+num;
		});

		$('.pagination-newer').click(function(){
			if(1 > parseInt(url[4])-1 )
				window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4]));
			else
				window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4])-1);
		});

		$('.pagination-older').click(function(){
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4])+1);
		});
		
		$(".firstpage").click(function(){
			window.location.href = "/site/"+url[2]+"/sensormanage/1";
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/site/"+url[2]+"/sensormanage/"+$("#lastNum").text();
		});

	} else {
		if(url[4] != null){
			$("#"+url[4]+"").addClass('pagination-active');
		}

		$('.pagination-inner a').on('click', function() {		
			var a = $(".pagination-inner a").index(this);
			var b = $(".pagination-inner a:eq("+a+")").attr("id");
			num = b;
			window.location.href = "/site/1/search1/"+num+"/"+url[5]+"/"+url[6];
		});

		$('.pagination-newer').click(function(){
			
			if(1 > parseInt(url[4])-1 )
				window.location.href = "/site/"+url[2]+"/search1/"+(parseInt(url[4]))+"/"+url[5]+"/"+url[6];
			else
				window.location.href = "/site/"+url[2]+"/search1/"+(parseInt(url[4])-1)+"/"+url[5]+"/"+url[6];
		});

		$('.pagination-older').click(function(){
			
			window.location.href = "/site/"+url[2]+"/search1/"+(parseInt(url[4])+1)+"/"+url[5]+"/"+url[6];
		});
				
		$(".firstpage").click(function(){
			window.location.href = "/site/"+url[2]+"/search1/1/"+url[5]+"/"+url[6];
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/site/"+url[2]+"/search1/"+$("#lastNum").text()+"/"+url[5]+"/"+url[6];
		});

	} // else 종료

	
	$("#search").click(function(){
		if(url[2] != 'search1'){
			var page = 1; // 현재 페이지 번호
			var searchType = $("#search-select option:selected").val();
			var keyword = $("#keyword").val();
			
			if(keyword.trim() == ""){
				alert("검색내용을 입력하세요");
				$("#keyword").focus();
				$("#keyword").val("");
				return false;
			}
			
			window.location.href = "/site/"+url[2]+"/search1/"+page+"/"+searchType+"/"+keyword;
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
			
			window.location.href = "/site/"+url[2]+"/search1/"+page+"/"+searchType+"/"+keyword;
		}

	}); //검색 이벤트 종료
	$('#keyword').keypress(function(event){
	     if ( event.which == 13 ) {
	         $('#search').click();
	     
	     }
	});
	
	$("#modify").click(function(){
		var site_id = $("#modify").attr('class');
		window.location.href = "/site/sitemodify/"+site_id;
	});
	
	$(".status > img").click(function() {
		var stat = $(".status > img").attr("alt");
		
		if(stat == "stop") {
			
			if( window.confirm("현장을 활성화 하시겠습니까?") ) {
				var site_id = $("#modify").attr('class');
				window.location.href = "/site/statusChange/"+site_id;		
			} 	
			
		} else if(stat == "start" || stat == "error") {
			if( window.confirm("현장 비활성화 시 설치되어있던 센서가 삭제되며 다시 복구 할 수 없습니다. 진짜 비활성화 하시겠습니까?") ) {
				
				var site_id = $("#modify").attr('class');
				window.location.href = "/site/statusChange/"+site_id;				
			} 
		} 
		
	}); // status 종료
});