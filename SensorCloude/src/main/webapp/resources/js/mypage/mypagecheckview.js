$(document).ready(function(){
	
	//
	 var site_id = $("#site_id").text();
	 console.log(site_id);
	
	 var board_no = $("#board_no").text();
	 
	 console.log(board_no);
	 ///board_status : checkstatus,
	

	 //수정버튼 클릭시
	 $('#mb').click(function() {
		 
		
		//url[4] = board_no , url[2] = site_id
		window.location.href = "/checkboard/checkmod3/"+board_no;
		 
		 
		  
	 });
	 
	 
	 $("#db").click(function(){
			//삭제 버튼을 클릭하면 board_no를 통해 게시글 삭제 + 첨부파일도 삭제해야한다.
			
			window.location.href = "/checkboard/checkdel3/"+board_no;
			
		});
	 
	 
	
	
	
});