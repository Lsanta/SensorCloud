/**
 * 
 */
$(document).ready(function(){
	//작성자 와 세션에있는 로그인 아이디를 비교해서 버튼 보여주거나 안보여주기
	var name = $("#writername").text();
	var id = $("#id").text();
	var level = $("#level").text(); //레벨
	
	if(name != id) {
		$("#mod").hide();
		$("#del").hide();
	}
	
	 if(name == user_id ) {
		 $("#ce").show();
		 $("#ceCheck").hide(); 
		  if( level >= 5) {
			  $("#ce").show();
			  $("#ceCheck").show();
		  }
		 
	 } else if ( level >= 5) {
		 $("#ce").hide();
		 $("#ceCheck").show();
	 } 	 
	 
	 if( $('.sse').text() == "close") {
		 $("#ce").hide();
		 $("#ceCheck").hide();
	 }
	
	$("#mod").click(function(){
		//수정 버튼을 클릭하면 글쓰기 폼에 해당 정보를 같이 넘겨줌
		
		var newURL =  window.location.pathname;
		var url = newURL.split('/');
		
		//url[4] = board_no , url[2] = site_id
		window.location.href = "/checkboard/checkmod/"+url[4]+"/"+url[2];
	});
	
	$("#del").click(function(){
		//삭제 버튼을 클릭하면 board_no를 통해 게시글 삭제 + 첨부파일도 삭제해야한다.
		var newURL =  window.location.pathname;
		var url = newURL.split('/');
		
		window.location.href = "/checkboard/checkdel/"+url[4]+"/"+url[2];
		
	});
	
	 $("#ce").click(function() {
		 if($('.sse').text() == "fixed") {
			 alert("이미 처리완료 된 글입니다.");
			 return false;
		 }
		 if(confirm('해당 글을 처리완료 하시겠습니까?') == true){
			 window.location.href = "/checkboard/sitefixed/"+ url[4] + "/" + url[2];
            }else{
             return false;
            }
		 
		
	 });
	 
	 $("#ceCheck").click(function() {
		 if(confirm('해당 글을 완료확인 하시겠습니까?') == true){
			 window.location.href = "/checkboard/sitecloseUpdate/"+ url[4] + "/" + url[2];
            }else{
             return false;
            }
		
	 });
});