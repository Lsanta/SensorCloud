$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});


	$(document).on("click", "#user>tr>td:not(:last-child)" , function(){

		var tr = $("#user tr").index(this);
		var id = $("#user tr:eq("+tr+") td:eq(0)").text();
		goPopup(id);

	}); 

	function goPopup(id) {
		var pop = window.open("/manage/usermodify/"+ id, "pop",
				"width=500,height=630,top="+(screen.availHeight/2-350)+",left="+(screen.availWidth/2-300)+"resizable=yes");

	}

	


	
	$(".accept").on("click", function() { 
		var level = $(this).parent().siblings().eq(4).text();
		var re_level =  $(this).parent().siblings().eq(5).text();
		var user_id =  $(this).parent().siblings().eq(0).text();
		
		var query = {
				user_id : user_id,
				re_level :re_level
		}
		
		 if(confirm('현재 사용자의 등급은'+' ' + level +'등급 ' + '입니다' + '\n' + '사용자가 요청한 ' + re_level + '등급 ' + '으로 승급을 수락하시겠습니까?') == true){
		
			 
			 $.ajax({
					type : "POST",
					url : "/manage/userlevelup.do",
					data : query,
					success : function(data) {
						alert("사용자의 승급이 완료 되었습니다.");
						location.reload();
					}
				}); // ajax 종료
          }else{
			return false;
         }
		});
	
	$(".cancel").on("click", function() { 
		
		var user_id =  $(this).parent().siblings().eq(0).text();
		
		var query = {
				user_id : user_id,
		}
		 if(confirm('취소하시겠습니까?') == true){
         
			 $.ajax({
					type : "POST",
					url : "/manage/releveldel.do",
					data : query,
					success : function(data) {
						alert("사용자의 승급요청이 취소 되었습니다.");
						location.reload();
					}
				}); // ajax 종료
          }else{
           return false;
          }
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
		
		$(".firstpage").click(function(){
			window.location.href = "/manage/1";
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/manage/"+$("#lastNum").text();
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
		
		$(".firstpage").click(function(){
			window.location.href = "/manage/search/1/"+url[4]+"/"+url[5];
		});
		
		$(".lastpage").click(function(){
			window.location.href = "/manage/search/"+$("#lastNum").text()+"/"+url[4]+"/"+url[5];
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
	
	/*화면 줄어들 때 리스트 자르기 */
	var tr = $("#user tr:gt(4)");
	
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