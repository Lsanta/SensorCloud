$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});

	$('.update').click(function() {
		window.location.href = "/mypage/modifymypage";
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

		
	
		if (passwordconfirm != password) {
			document.getElementById("inhere2").innerHTML = "비밀번호확인이 일치하지 않습니다";
		}

		else if(passwordconfirm == password) {
			
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

						window.location.href = "/mypage";
					}
					
					if( data == "false"){
						document.getElementById("inhere1").innerHTML = "수정 정보를 모두 입력해 주세요";
					}

				}
			});
		
		}
		
	

	});

});
