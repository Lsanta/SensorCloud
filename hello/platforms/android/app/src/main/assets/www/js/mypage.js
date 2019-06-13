var id;

if(localStorage.getItem("auto") == "true") {
   id = localStorage.getItem("id");
} else if( localStorage.getItem("auto") == "false"){
   id = sessionStorage.getItem("id");
}

$(document).ready(function() {
	//프로필
	var query = {
		 id: id
	};
	$.ajax({
			type : "POST",
			url : "http://52.79.242.145:8080/app/mypage/usermodify",
			//url : "http://39.127.7.58:8080/app/mypage/usermodify",
			data : query,
			dataType : 'json',
			contentType : "application/json; charset=UTF-8",
			success : function(result7){
					console.log(result7);
				 var str3="";
				 $.each(result7,function(i,s){
						str3 +='<p class="n1">'+s[0].name+'</p>';
					str3 +='<p class="n2">'+'등급'+s[0].m_level+'</p>';
					str3 +='<p class="n3">';
					switch(s[0].m_level) {
						 case 1: str3 +='<span>'+'(권한없음)'+'</span>'; break;
						 case 2: str3 +='<span>'+'(읽기권한)'+'</span>'; break;
						 case 3: str3 +='<span>'+'(쓰기권한)'+'</span>'; break;
						 case 4: str3 +='<span>'+'(수정권한)'+'</span>'; break;
						 case 5: str3 +='<span>'+'(관리자)'+'</span>'; break;
					}
					str3 +='</p>';
	
					 $(".name01").html(str3);

					 localStorage.removeItem("level");
					 localStorage.setItem("level",s[0].m_level);
				 });
			}
	});

			 $.ajax({
				type : "POST",
				url : "http://52.79.242.145:8080/app/mypage/mgetAttachListmypage",
				//url : "http://39.127.7.58:8080/app/mypage/mgetAttachListmypage",
				data : id,
				async : false,
				contentType : "application/json; charset=UTF-8",
				success : function(arr){
					 console.log(arr);
					 var str = "";
					 
					 $(arr).each(function(i, attach){
							//image type
							if(attach.filetype){
								 var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
								
								 str += "<li class='delete' data-path='"+attach.file_path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.filetype+"' ><div>";
								 str += "<img class='delete1' src='http://52.79.242.145:8080/display?fileName="+fileCallPath+"'>";
								 str += "</div>";
								 str += "</li>";
							
							} else {
								 str += "<img src='./img/manager.svg' id='img'>";
								 
							}
							 
						});
						
						$(".cell ul").html(str);
	
				} // success 함수 종료
		}); // ajax함수 종료
			
	
		 //점검이력 불러오기
		 $.ajax({
				type : "POST",
				url : "http://52.79.242.145:8080/app/mypage/mypagemain",
				//url : "http://39.127.7.58:8080/app/mypage/mypagemain",
				async:false,
				data : {pagenum : 1, id : id},
				success : function(result){
						var page="";
						var str = "";  
						$.each(result.mpcheckList,function(i,s){
						str +='<tr>';
						str +='<td>'+s.site_name+'</td>';      // 현장이름
						str +='<td>'+s.title+'</td>';          // 제목                       
					 //  str +='<td>'+s.board_status+'</td>';   // 상태
							switch(s.board_status){
							case 0 : str +='<td>'+ 'open' +'</td>'; break;
							case 1 : str +='<td>'+ 'fixed' +'</td>'; break;
							case 2 : str +='<td>'+ 'closed' +'</td>'; break;
							default  : str +='<td>'+'null'+'</td>'; break;
							}
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
	
	});
	
	function page(index) {
		 $.ajax({
				 type : "POST",
				url : "http://52.79.242.145:8080/app/mypage/mypagemain",
				//url : "http://39.127.7.58:8080/app/mypage/mypagemain",
				 async:false,
				 data : {pagenum : index,  id : id},
				 success : function(result){
						 
							var page="";
							var str = ""; 
							$.each(result.mpcheckList,function(i,s){
							str +='<tr>';
							str +='<td>'+s.site_name+'</td>';      // 현장이름
							str +='<td>'+s.title+'</td>';          // 제목
					 //    str +='<td>'+s.board_status+'</td>';   // 상태
							switch(s.board_status){
							case 0 : str +='<td>'+ 'open' +'</td>'; break;
							case 1 : str +='<td>'+ 'fixed' +'</td>'; break;
							case 2 : str +='<td>'+ 'closed' +'</td>'; break;
							default  : str +='<td>'+'null'+'</td>'; break;
							}
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
	
	
	//점검이력 글 하나 클릭 시
	$(document).on("click", "#mypagechecklist tr", function() {
	
		 // 클릭한 행을 tr 변수로
		 var tr = $("#mypagechecklist tr").index(this);
	
		 var board_no = $("#mypagechecklist tr:eq(" + tr + ") td:eq(4)").text();
		 // 내가클릭한 테이블의 행을 판별해야하기위해 board_no 정보를 넘긴다
		 
		 window.location.href = "/checkboard/" + board_no; //바꾸기~~
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
	
				var user_id;
				if(localStorage.getItem("auto") == "true") {
				   user_id = localStorage.getItem("id");
				} else if( localStorage.getItem("auto") == "false"){
				   user_id = sessionStorage.getItem("id");
				}

				var query = {
					 user_id: user_id,
					 password : $("#pass").val()
				};
				console.log(query);
				$.ajax({
					 type : "POST",
					 url : "http://52.79.242.145:8080/app/mypage/mypageconfirmpasswd.do",
					 //url : "http://39.127.7.58:8080/app/mypage/mypageconfirmpasswd.do",
					 async: false,
					 data: query,
					 contentType : "application/json; charset=UTF-8",
					 dataType : 'text',
					 success : function(data) {
							
							if (data == "success") {
								 alert("비밀번호 일치");
								//  window.location.href = "http://52.79.242.145:8080/app/mypage/usermodify";
								// window.location.href = "http://39.127.7.58:8080/app/mypage/usermodify";
								window.location.href = "usermodify.html";
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
	
	$(document).ready(function() {
		 //승급요청 확인 클릭시
		 $("#ok").click(function(){
		
				 var select = $("#select_level option:selected").val();
				
				 var query = {
						id : id,
						mlevel : select
				 }
	
				 var payload = {
					 message : "앱에서" + id + "님의" + select + "등급 승급요청이 왔습니다."
				 }   
	
				 $.ajax({
					 type : "POST",
					 url : "http://52.79.242.145:8080/app/mypage/levelup",
					 //url : "http://39.127.7.58:8080/app/mypage/levelup",
					 data : JSON.stringify(query),
					 contentType : "application/json; charset=UTF-8",
					 dataType : 'text',
					 success : function(data) {
							if(data == "success"){
								 alert("승급요청에 성공했습니다.");
	
								 //푸쉬 메시지 요청(웹으로)
								 $.ajax({
											 type : "POST",
											 url : "http://52.79.242.145:8080/app/send/message.do",
											 //url : "http://39.127.7.58:8080/app/send/message.do",
											 data : JSON.stringify(payload),
											 contentType : "application/json; charset=UTF-8",
											 success : function(data) {
											 }
								 }); //ajax 종료
								 $.mobile.changePage("#tab2",{ transition: "flip", changeHash: false });
	
							} else if (data == "false"){
								 alert("승급요청에 실패했습니다. 다시 시도해 주세요.");
								 $.mobile.changePage("#tab2",{ transition: "flip", changeHash: false });
							}
							
					 }
				}); //ajax 종료
	
		 }); // 클릭 이벤트 종료
	}); 
	$(document).ready(function() {
		$("#logout").click(function(){ 
			if(confirm("로그아웃 하시겠습니까?")){
			
				$.ajax({
					type : "POST",
					url : "http://52.79.242.145:8080/app/mypage/deleteappToken",
					//url : "http://39.127.7.58:8080/app/mypage/deleteappToken",
					data : id,
					contentType : "text/plain; charset=UTF-8",
					dataType : 'text',
					success : function(data) {
					if(localStorage.getItem("auto") == "true") {	
						localStorage.removeItem("id")
						localStorage.removeItem("password");
						localStorage.removeItem("level");
					} else if( localStorage.getItem("auto") == "false"){ 
						sessionStorage.removeItem("id");
						sessionStorage.removeItem("password"); 
						sessionStorage.removeItem("level");
					}
						localStorage.removeItem("auto");
						window.location.href="index.html";
					}
				});
		
		} else {
			window.location.href="#tab1";
		}
		});
	});
	