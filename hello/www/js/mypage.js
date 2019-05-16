$(document).ready(function() {
	//점검이력 불러오기
	$.ajax({
	   type : "POST",
	   url : "http://39.127.7.58:8080/app/mypage/mypagemain",
	   data : {pagenum : 1},
	   success : function(result){
			 console.log(result);
			 var page="";
			 var str = "";  
			 $.each(result.mpcheckList,function(i,s){
			 str +='<tr>';
			 str +='<td>'+s.site_name+'</td>';      // 현장이름
			 str +='<td>'+s.title+'</td>';          // 제목                       
			 str +='<td>'+s.board_status+'</td>';   // 상태
			 str +='<td>'+s.reg_date+'</td>';       // 날짜
			 str +='</tr>';

			 $("#mypagechecklist").html(str);
				
		  });
			   if (result.criteria.prev) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.startPage - 1) + ');" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>'
				}
				for (var i = result.criteria.startPage; i <= result.criteria.endPage; i++) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + i + ');">' + i + '</a></li>'
				}
				if (result.criteria.next) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.endPage + 1) + ');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>'
				}
				$(".pagination").empty();
				$(".pagination").prepend(page);
				$("html, body").animate({ scrollTop: 0 }, 1);
		 
		  
		  } // success 함수 종
	}); // ajax함수

	$('.mpmodify-level').on('click', function() {
	   $("#content").load("usermodify.html");
	});


  function page(index) {
  $.ajax({
	   type : "POST",
	   url : "http://39.127.7.58:8080/app/mypage/mypagemain",
	   data : {pagenum : index},
	   success : function(result){
			 console.log(result)
			 var page="";
			 var str = ""; 
			 $.each(result.mpcheckList,function(i,s){
			 str +='<tr>';
			 str +='<td>'+s.site_name+'</td>';      // 현장이름
			 str +='<td>'+s.title+'</td>';          // 제목
			 str +='<td>'+s.board_status+'</td>';   // 상태
			 str +='<td>'+s.reg_date+'</td>';       // 날짜
			 str +='</tr>';

			 $("#mypagechecklist").html(str);
				
		  });
			   if (result.criteria.prev) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.startPage - 1) + ');" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>'
				}
				for (var i = result.criteria.startPage; i <= result.criteria.endPage; i++) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + i + ');">' + i + '</a></li>'
				}
				if (result.criteria.next) {
				   page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.endPage + 1) + ');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>'
				}
				$(".pagination").empty();
				$(".pagination").prepend(page);
				$("html, body").animate({ scrollTop: 0 }, 1);
	 
		  } // success 함수 종
		}); // ajax함수
 	}
});


//점검이력 글 하나 클릭 시
$(document).on("click", "#mypagechecklist tr", function() {

	// 클릭한 행을 tr 변수로
	var tr = $("#mypagechecklist tr").index(this);

	var board_no = $("#mypagechecklist tr:eq(" + tr + ") td:eq(4)").text();
	// 내가클릭한 테이블의 행을 판별해야하기위해 board_no 정보를 넘긴다
	
	window.location.href = "/checkboard/" + board_no;
});


$(document).ready(function() {
	//비밀번호 확인버튼 클릭 시
	$(".mpmodify-submit").on('click', function() {
		login();
	});

	function login() {
		var passwd = $("#pass").val();
		console.log(passwd);
		
		if (passwd == "") {
			alert("사용자 정보 수정을위해 비밀번호를 입력해주세요");
		}

		var query = {
			id: window.sessionStorage.getItem("key"),
			password : $("#pass").val()
		};
		alert(query);

		$.ajax({
			type : "POST",
			url : "http://39.127.7.58:8080/app/mypage/mypageconfirmpasswd.do",
			data : query,
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				alert(data);
				if (data == "success") {
					alert("비밀번호 일치");
					window.location.href = "http://39.127.7.58:8080/app/mypage/usermodify";
				} else {
					alert("비밀번호가 틀렸습니다.");
				}
			}
		});
	}

	// 패스워드에 커서를 두고 엔터키(모바일용)를 누르면 로그인 함 **해야함!
	$("#pass").keydown(function(key) {
		if (key.keyCode == 13) {
			login();
		}
	});
	
});

// $(document).ready(function() {
// 	//승급요청 확인 클릭시
// 	$("#ok").click(function(){
// 		 alert("1");
// 		 var id = sessionStorage.getItem('id');
// 		 var select = $("#select_level option:selected").val();
		
// 		 var query = {
// 			 id : id,
// 			 mlevel : select
// 		 }

// 		 console.log(query);

// 		 $.ajax({
// 			type : "POST",
// 			url : "http://39.127.7.58:8080/app/mypage/levelup",
// 			data : query,
// 			contentType : "application/json; charset=UTF-8",
// 			success : function(data) {
// 				alert(data);
				
// 			}
// 		});

// 	});
// });