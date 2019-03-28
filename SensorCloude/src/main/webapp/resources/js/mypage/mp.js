$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});

	$('.update').click(function() {
		window.location.href = "mypage/modifymypage";
	});

	$(".mpmodify-submit").on('click', function() {

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
				}
			}
		});

	});

	$('.confirm-modify').click(function() {

		var value = $('input[name=modify_name]').val();

		var id = $("#id").val();
		var password = $("#password").val();
		var passwordconfirm = $("#passwordconfirm").val();
		var modify_name = $("#modify_name").val();
		var phonenumber = $("#phonenumber").val();
		
		alert(password);
		alert(id);
		alert(phonenumber);
		var query = {
				user_id:id,
				password:password,
				name:modify_name,
				phone:phonenumber
		}

		if (id = "") {
			document.getElementById("inhere1").innerHTML = "아이디를 입력해 주세요";
		}

		if (password = "") {
			document.getElementById("inhere2").innerHTML = "패스워드를 입력해 주세요";
		}
		if (passwordconfirm != password) {
			document.getElementById("inhere3").innerHTML = "패스워드가 일치하지 않습니다";
		}
		if (modify_name = "") {
			document.getElementById("inhere4").innerHTML = "이름을 입력해주세요";

		}

		if (phonenumber = "") {
			document.getElementById("inhere5").innerHTML = "휴대폰 번호를 입력해주세요";
		}

		
			
		

		$.ajax({

			type : "POST",
			data : query,
			url : "/mypage/mypagemodifymyinfo",
			success : function(data) {
				alert(data);
				if (data == "success") {
					alert("업데이트 완료");

					window.location.href = "/mypage";
				}
			}
		});

	});

});
