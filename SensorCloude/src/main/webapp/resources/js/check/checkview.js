$(document).ready(function(){

	 var writer_id = $("#writerid").text();//글쓴이		
	 var site_id = $("#site_id").text();
	 console.log(writer_id);
	
	 var board_no = $("#board_no").text();
	 
	 console.log(board_no);
	 ///board_status : checkstatus,
	 
	 var user_id =  $("#id").text(); //세션아이디
	 console.log(user_id);
	 
	 
	//현재 접속한 아이디와 점검이력 글쓴이의 아이디와 같지 않을시 수정버튼 hide
	 if(writer_id == user_id || user_id == "admin"){
		 $("#mb").show();
		 $("#db").show();
	 } else {
		 $("#mb").hide();
		 $("#db").hide();
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
	 
	
	
});