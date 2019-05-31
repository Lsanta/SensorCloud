$(document).on("click", "#mypagechecklist tr", function() {

	// 클릭한 행을 tr 변수로
	var tr = $("#mypagechecklist tr").index(this);

	var board_no = $("#mypagechecklist tr:eq(" + tr + ") td:eq(4)").text();
	// 내가클릭한 테이블의 행을 판별해야하기위해 board_no 정보를 넘긴다
	
	window.location.href = "/checkboard/" + board_no;
});

$(document).ready(function() {
	

 ////////////////////*pagination*//////////////////////////////////
	
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');

	if(url[2] != null){
		$("#"+(url[2])+"").addClass('pagination-active');
	}
	
	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		var b = $(".pagination-inner a:eq("+a+")").attr("id");
		num = b;
		window.location.href = "/mypage/"+num;
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/mypage/"+(parseInt(url[2]));
		else
			window.location.href = "/mypage/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
			window.location.href = "/mypage/"+(parseInt(url[2])+1);
	});
	
	$(".firstpage").click(function(){
		window.location.href = "/mypage/1";
	});
	
	$(".lastpage").click(function(){
		window.location.href = "/mypage/"+$("#lastNum").text();
	});
	
	////////////////////////////////////////////////////////////////////
	
	
	
	$('.update').click(function() {
		window.location.href = "/mypage/modifymypage";
	});
	$('.up').click(function(){
		window.open("/mypage/levelup", "pop",
				"width=570,height=420,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});

	function login() {

		var passwd = $("#pass").val();

		if (passwd = "") {
			alert("사용자 정보 수정을위해 비밀번호를 입력해주세요");
			$("#pass").focus();
			return;
		}

		var query = {
				password : $("#pass").val()
		};

		$.ajax({
			type : "POST",
			dataType : "text",
			data : query,
			url : "/mypage/mypageconfirmpasswd.do",
			success : function(data) {
				if (data == "success") {
					alert("비밀번호 일치");
					window.location.href = "/mypage/modifymyinfo";
				} else {
					alert("비밀번호가 틀렸습니다.");
				}
			}
		});
	}
	
	$(".mpmodify-submit").on('click', function() {
		login();
	});

	$('.confirm-modify').click(function() {

				var value = $('input[name=modify_name]').val();

				var id = $("#id").val();
				var password = $("#password").val();
				var passwordconfirm = $("#passwordconfirm").val();
				var modify_name = $("#modify_name").val();
				var phonenumber = $("#phonenumber").val();

				if (passwordconfirm != password) {
					document.getElementById("inhere2").innerHTML = "비밀번호확인이 일치하지 않습니다";
				} else if (passwordconfirm == password) {

					var query = {
							user_id : id,
							name : modify_name,
							password : password,
							phone : phonenumber
					}

					$.ajax({
						type : "POST",
						data : query,
						url : "/mypage/mypagemodifymyinfo",
						success : function(data) {
							if (data == "success") {
								window.location.href = "/mypage/1";
							}
							if (data == "false") {
								document.getElementById("inhere1").innerHTML = "수정 정보를 모두 입력해 주세요";
							}
						}
					});
				}
			});
	


	// 

	// 패스워드에 커서를 두고 엔터키를 누르면 로그인 함
	$("#pass").keydown(function(key) {
		if (key.keyCode == 13) {
			login();
		}
	});

});
