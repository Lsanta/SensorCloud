/**
 * 
 */
$(document).ready(function(){
	
	// site/1/sitealarm
	var newURL =  window.location.pathname;
	
	//체크박스 맨위 누르면 전부다 선택 or 해제
	$('input[name=checkAll]').on('change', function(){
		 $('input[name=selected]').prop('checked', this.checked);
	});
	
		//팝업창으로 입력폼 띄우고 정보 전달 후 Insert
		//window.open(newURL + "/add" ,"add","width=300,height=300, scrollbars=no, resizable=yes");
		
	$(document).on("click","#plus-btn",function(){
		
		 var row ="<tr class='example'>"
			 row += '<td class="line"><input type="tel" name="tel" id="tel"></td>';
		 	 row += '<td class="line"><input type="text" name="company" id="company"></td>';
			 row += '<td class="line"><input type="text" name="name" id="name"></td>';
		 	 row += '<td class="line"><input type="button" value="추가" id="add">';
		 	 row += '<input type="button" value="취소" id="remove"></td>';
		 	 row += "</tr>";	
		 	 
		 	$("#member").append(row);
	 }); // +버튼 누르는거 종료
	
	
	$(document).on("click","#add",function(){
		//주소창 잘라서 site_id 뽑았는데 내가봐도 구림
		
		
		var newURL =  window.location.pathname;
		var sid = newURL.split("/");
		sid2 = sid[2].split("/");
		
		var site_id = sid2[0];
	
		
		var a = $(this);
		var tel = a.parent().siblings().eq(0).children().val();
		var company = a.parent().siblings().eq(1).children().val();
		var name = a.parent().siblings().eq(2).children().val();
		
		if(tel == "" || company == "" || name == ""){
			alert("빈칸을 입력하세요.");
			return false;
		}
		
		
		var query = { name : name, tel : tel, company : company, site_id : site_id}
		
		
		$.ajax({
			  type : "POST",
			  url : "/site/alarmMemberadd.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					  alert("추가되었습니다");
					  $("#example").remove();
					  location.reload();
				  } else{
					  alert("추가에 실패했습니다");
				  }
			  }
			  
		}); // ajax 종료
		
	}); // 추가 버튼 클릭 종료
	
	$(document).on("click","#remove",function(){
		$(".example").remove();
	}); // 삭제 버튼 클릭 종료
	
	
	
	$(document).on("click","#amember>.select td:not(:last-child)",function(){
		
		var openWin = window.open("/site/sitealarmmod", "pop",
				 "width=400,height=420,top="+(screen.availHeight/2-330)+",left="+(screen.availWidth/2-500)+"resizable=yes");
		
		$(".modifybox").css("display","block");
		
		var tr = $(this).parent();
		var tel2 = tr.children().eq(0).addClass("1");
		var company2 = tr.children().eq(1).addClass("2");
		var name2 = tr.children().eq(2).addClass("3");
		var alarmNum = tr.children().eq(3).addClass("4");
		
//		$("#tel2").val(location.eq(0).text());
//		$("#company2").val(location.eq(1).text());
//		$("#name2").val(location.eq(2).text());
//		$("#alarmNum").val(location.eq(3).text());
		
		$("#tel2").removeAttr("disabled");
		$("#company2").removeAttr("disabled");
		$("#name2").removeAttr("disabled");
			
	}); // 클릭 햇을시 종료	
	
	$("#send").click(function(){
		//주소창 잘라서 site_id 뽑았는데 내가봐도 구림
		var newURL =  window.location.pathname;
		var sid = newURL.split("/");
		sid2 = sid[2].split("/");
		
		var site_id = sid2[0];
		var alarm_content = $("#textarea").val();
		
		
		if( alarm_content == ""){
			alert("문자내용을 적어주세요");
			 $("#textarea").focus();
			 return;
		}
	
		var array = {};
		
		for(var i=0; i < $('input:checkbox[name="selected"]').length; i++ ) {
			 
			
			if( $('input:checkbox[name="selected"]').eq(i).is(":checked") ) {
				
				array[i] = $('input:checkbox[name="selected"]').parent().eq(i).siblings().eq(0).text();
				
			}
		}
					
		var query = { 
				alarm_content : alarm_content, 
				site_id : site_id, 
				send : array
			}
		
		
		var data = JSON.stringify(query);
		
		$.ajax({
			  type : "POST",
			  url : "/SMS/alarmadd.do",
			  data : data,
			  contentType: 'application/json; charset=utf-8',
			  success : function(data){
				  if( data == "success"){
					  alert("문자가 전송 되었습니다.");
					  $("#example").remove();
					  location.reload();
				  } else{
					  alert("문자전송에 실패했습니다.");
				  }
			  }
			  
		}); // ajax 종료
		
	}); // 전송했을시 종료
		
	//수정 버튼 클릭시 연락망 내 사람 정보 변경
	$("#mod").click(function(){
		
		var query = {
			tel : $("#tel2").val(),
			company : $("#company2").val(),
			name : $("#name2").val(),
			alarm_m_no : $("#alarmNum").val()
		}
		
		
		
		$.ajax({
			  type : "POST",
			  url : "/site/alarmmod.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					  alert("업데이트 성공");
					  $(".example").remove();
					  window.opener.location.reload();
					  window.close();
				  } else{
					  alert("수정에 실패했습니다");
				  }
			  }
			  
		}); // ajax 종료
		
	});
	
	//삭제 버튼 클릭시 연락망 내 사람 정보 삭제
	$("#drop").click(function(){
		var query = {
				alarm_m_no : $("#alarmNum").val()
			}

			$.ajax({
				  type : "POST",
				  url : "/site/alarmdel.do",
				  data : query,
				  success : function(data){
					  if( data == "success"){
						  alert("삭제 성공");
						  $(".example").remove();
						  window.opener.location.reload();
						  window.close();
					  } else{
						  alert("삭제에 실패했습니다");
					  }
				  }
				  
			}); // ajax 종료
		});
	
		/*글자수 제한*/
		$('textarea').keyup(function() {
		    
			  var characterCount = $(this).val().length,
			      current = $('#current'),
			      maximum = $('#maximum'),
			      theCount = $('#the-count');
			    
			  current.text(characterCount);
			      
			});
		
		$("#modify").click(function(){
			var site_id = $("#modify").attr('class');
			window.location.href = "/site/sitemodify/"+site_id;
		});
		
		$(".status > img").click(function() {
			var stat = $(".status > img").attr("alt");
			
			if(stat == "stop") {
				
				if( window.confirm("현장을 활성화 하시겠습니까?") ) {
					var site_id = $("#modify").attr('class');
					window.location.href = "/site/statusChange/"+site_id;		
				} 	
				
			} else if(stat == "start" || stat == "error") {
				if( window.confirm("현장 비활성화 시 설치되어있던 센서가 삭제되며 다시 복구 할 수 없습니다. 진짜 비활성화 하시겠습니까?") ) {
					
					var site_id = $("#modify").attr('class');
					window.location.href = "/site/statusChange/"+site_id;				
				} 
			} 
			
		}); // status 종료
	
});






