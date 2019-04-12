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
		
		window.location.href = "/checkboard/checkmod/"+url[4];

	
	});
});