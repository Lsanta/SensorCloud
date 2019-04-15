/**
 * 
 */

$(document).ready(function(){
	//작성자 와 세션에있는 로그인 아이디를 비교해서 버튼 보여주거나 안보여주기
	var name = $("#writername").text();
	console.log(name);
	var id = $("#id").text();
	console.log(id);
	
	if(name != id) {
		$("#mod").hide();
		$("#del").hide();
	}
	
	$("#mod").click(function(){
		//수정 버튼을 클릭하면 글쓰기 폼에 해당 정보를 같이 넘겨줌
		
		var newURL =  window.location.pathname;
		var url = newURL.split('/');
		console.log(url);
		
		//url[4] = board_no , url[2] = site_id
		window.location.href = "/checkboard/checkmod/"+url[4]+"/"+url[2];
	});
	
	$("#del").click(function(){
		//삭제 버튼을 클릭하면 board_no를 통해 게시글 삭제 + 첨부파일도 삭제해야한다.
		var newURL =  window.location.pathname;
		var url = newURL.split('/');
		
		window.location.href = "/checkboard/checkdel/"+url[4]+"/"+url[2];
		
	});
});