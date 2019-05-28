$(document).ready(function() {

	var newURL =  window.location.pathname;
	var url = newURL.split('/');

	$(document).on("click", "#sensorlist tr", function() {

		// 클릭한 행을 tr 변수로
		var tr = $("#sensorlist tr").index(this);

		var sensor_sn = $("#sensorlist tr:eq(" + tr + ") td:eq(0)").text();
		var sensor_kind = $("#sensorlist tr:eq(" + tr + ") td:eq(1)").text();

		window.location.href = "/site/"+ url[2] +"/sensormodify/" + sensor_sn;
		alert(sensor_sn);

	});

	////////////////////*pagination*//////////////////////////////////

	$("#add").click(function(){
		window.location.href = "/site/"+ url[2] +"/sensoradd/1";
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
			window.location.href = "/site/"+url[2]+"/sensormanage/"+(parseInt(url[4]));
		});
		
		$(".lastpage").click(function(){
			alert($("#lastNum").text());
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
			window.location.href = "/site/"+url[2]+"/search1/"+(parseInt(url[4]))+"/"+url[5]+"/"+url[6];
		});
		
		$(".lastpage").click(function(){
			alert("gd");
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
});