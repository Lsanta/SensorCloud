$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	$('.update').on('click', function() {
		window.location.href = "modifymypage";
	});
	
	$(".mypage mpmodify-submit").on('click',function() {
		
		var passwd = $("#pass").val();
		
		if(passwd =""){
			alert("사용자 정보 수정을위해 비밀번호를 입력해주세요");
			$("#pass").focus();
			return;
		}
		
		var query = {password: $("#pass").val()};
		
		$.ajax({
			
			type :"POST",
			dataType :"text",
			data : query,
			url : "/mypage/mypageconfirmpasswd.do",
			success : function(data) {
				 if(data == "success")
	                 alert("비밀번호 일치");
	             
			}
		});
		
		
	});
	
});