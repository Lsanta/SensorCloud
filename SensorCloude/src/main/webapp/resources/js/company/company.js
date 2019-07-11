$(document).ready(function() {
	
	$("#list tr").mouseover(function(event){
		$("#snameDiv").empty();		
		
		var cid = $(this).children().eq(3).text();
		var query = {
				company_num : cid
		}
		
		$.ajax({
			type : 'POST',
			url : '/company/companyJSON',
			data : query,
			dataType : 'JSON',
			success : function(data) {
				console.log(data);
				
				var str = "";
				
				$.each(data,function(i,s){
					str += "<ul id='sitenamelist'>";
					str += "<li>"+data[i].site_name+"</li>";
					str += "<li style='display:none'>"+data[i].site_id+"</li>";
					str += "</ul>";
				});
				
				var snameList = "";
				snameList += "<ul class='timeline'>";
				snameList += "<li class='timeline-inverted'>";
				snameList += "<div class='timeline-panel'>";
				snameList += "<div class='timeline-body'>"+str+"</div></div></li>"
				snameList += "</ul>";
				
				$("#snameDiv").html(snameList);
			}
		});
	});
	
	

	
	$("#companyadd").click(function(){
		window.open("/company/companyadd", "pop",
		"width=500,height=660,top="+(screen.availHeight/2-350)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	

	 $(document).on("click", "#list tr" , function(){
		 
		var openWin = window.open("/company/companymod", "pop",
				 "width=500,height=400,top="+(screen.availHeight/2-350)+",left="+(screen.availWidth/2-300)+"resizable=yes");
		
		var tr = $(this);
		var name = tr.children().eq(0).addClass("1");
		var reg_number = tr.children().eq(1).addClass("2");
		var company_num = tr.children().eq(2).addClass("3");
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
				window.location.href = "/company/"+num;
			});

			$('.pagination-newer').click(function(){
				if(1 > parseInt(url[2])-1 )
					window.location.href = "/company/"+(parseInt(url[2]));
				else
					window.location.href = "/company/"+(parseInt(url[2])-1);
			});

			$('.pagination-older').click(function(){
				window.location.href = "/company/"+(parseInt(url[2])+1);
			});
			
			$(".firstpage").click(function(){
				window.location.href = "/company/1";
			});
			
			$(".lastpage").click(function(){
				window.location.href = "/company/"+$("#lastNum").text();
			});
		} else {
			
			if(url[3] != null){
				$("#"+(url[3])+"").addClass('pagination-active');
			}
			
			$('.pagination-inner a').on('click', function() {
				var a = $(".pagination-inner a").index(this);
				var b = $(".pagination-inner a:eq("+a+")").attr("id");
				num = b;
				window.location.href = "/company/search/"+num+"/"+url[4]+"/"+url[5];
			});

			$('.pagination-newer').click(function(){
				if(1 > parseInt(url[3])-1 )
					window.location.href = "/company/search/"+(parseInt(url[3]))+"/"+url[4]+"/"+url[5];
				else
					window.location.href = "/company/search/"+(parseInt(url[3])-1)+"/"+url[4]+"/"+url[5];
			});

			$('.pagination-older').click(function(){
					window.location.href = "/company/search/"+(parseInt(url[3])+1)+"/"+url[4]+"/"+url[5];
			});
			
			$(".firstpage").click(function(){
				window.location.href = "/company/search/1/"+url[4]+"/"+url[5];
			});
			
			$(".lastpage").click(function(){
				window.location.href = "/company/search/"+$("#lastNum").text()+"/"+url[4]+"/"+url[5];
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
				
				window.location.href = "/company/search/"+page+"/"+searchType+"/"+keyword;
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
				
				window.location.href = "/company/search/"+page+"/"+searchType+"/"+keyword;
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