$(document).ready(function(){
	
	//

	 var writer_id = $("#writerid").text();//글쓴이		
	 var site_id = $("#site_id").text();
	 console.log(writer_id);
	
	 var board_no = $("#board_no").text();
	 
	 console.log(board_no);
	 ///board_status : checkstatus,
	 
	 var user_id =  $("#user_id").text(); //세션아이디
	 console.log(user_id);
	 
	 
	//현재 접속한 아이디와 점검이력 글쓴이의 아이디와 같지 않을시 수정버튼 hide
	 if(writer_id != user_id){
		 $("#mb").hide();
		 $("#db").hide();
	 }
	 
	 

	 //수정버튼 클릭시
	 $('#mb').click(function() {
		 
		
		//url[4] = board_no , url[2] = site_id
		window.location.href = "/checkboard/checkmod2/"+board_no;
		 
		 
		  
	 });
	 
	 
	 $("#db").click(function(){
			//삭제 버튼을 클릭하면 board_no를 통해 게시글 삭제 + 첨부파일도 삭제해야한다.
			
			window.location.href = "/checkboard/checkdel2/"+board_no;
			
		});
	 
	 
	
	
	
});