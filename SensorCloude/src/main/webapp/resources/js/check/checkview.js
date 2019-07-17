$(document).ready(function(){

	 var writer_id = $("#writerid").text();//글쓴이		
	 var site_id = $("#site_id").text();
	
	 var board_no = $("#board_no").text();
	 ///board_status : checkstatus,
	 
	 var user_id =  $("#id").text(); //세션아이디
	 var level = $("#level").text(); //레벨
	 
	//현재 접속한 아이디와 점검이력 글쓴이의 아이디와 같지 않을시 수정버튼 hide
	 if(writer_id == user_id){
		 $("#mb").show();
		 $("#db").show();
	 } else {
		 $("#mb").hide();
		 $("#db").hide();
	 }
	 
	 if(writer_id == user_id ) {
		 
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
	 

	 //수정버튼 클릭시
	 $('#mb').click(function() {
		 
		if(url[1] == "mypage") {
			window.location.href = "/mypage/checkmod2/"+board_no;
		} else if(url[1] == "adminCheckPage"){
			window.location.href = "/adminCheckPage/checkmod2/"+board_no;
		} else {
			//url[4] = board_no , url[2] = site_id
			window.location.href = "/checkboard/checkmod2/"+board_no;
		}
		  
	 });
	 
	 
	 $("#db").click(function(){
			//삭제 버튼을 클릭하면 board_no를 통해 게시글 삭제 + 첨부파일도 삭제해야한다.
		 if(confirm('해당 글을 삭제하시겠습니까?') == true){
			 window.location.href = "/checkboard/checkdel2/"+board_no;
            }else{
             return false;
            }

		});
	 
	 $("#ce").click(function() {
		 if(confirm('해당 글을 처리완료 하시겠습니까?') == true){
			 window.location.href = "/checkboard/fixedUpdate/"+board_no;
            }else{
             return false;
            }
		 
		
	 });
	 
	 $("#ceCheck").click(function() {
		 if(confirm('해당 글을 완료확인 하시겠습니까?') == true){
			 window.location.href = "/checkboard/closeUpdate/"+board_no;
            }else{
             return false;
            }
		
	 });
	 
	
	
});