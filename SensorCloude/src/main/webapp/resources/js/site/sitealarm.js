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
			
		 var row ="<tr id='example'>"
			 row += '<td><input type="tel" class="line" name="tel" id="tel"></td>';
		 	 row += '<td><input type="text" class="line" name="company" id="company"></td>';
			 row += '<td><input type="text" class="line" name="name" id="name"></td>';
		 	 row += '<td><input type="button" value="추가" id="add">';
		 	 row += '<input type="button" value="삭제" id="remove"></td>';
		 	 row += "</tr>";	
		 	 
		 	$("#member").append(row);
	 }); // +버튼 누르는거 종료
	
	
	$(document).on("click","#add",function(){
		//주소창 잘라서 site_id 뽑았는데 내가봐도 구림
		var newURL =  window.location.pathname;
		var sid = newURL.split("/");
		sid2 = sid[2].split("/");
		
		var site_id = sid2[0];
		
		var name = $("#name").val();
		var tel = $("#tel").val();
		var company = $("#company").val();
		
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
		$("#example").remove();
	}); // 삭제 버튼 클릭 종료
	
	
	
	$(document).on("dblclick","#member>tbody>tr",function(){
		
		var location = $(this).children();
		$(this).css("background-color","aliceblue");
		$(this).siblings().css("background-color","white");

		$("#tel").val(location.eq(0).text());
		$("#company").val(location.eq(1).text());
		$("#name").val(location.eq(2).text());
		
		$("#tel").removeAttr("disabled");
		$("#company").removeAttr("disabled");
		$("#name").removeAttr("disabled");
		
	}); // 더블클릭 햇을시 종료
	

	$("#submit").click(function(){
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
		var query = { alarm_content : alarm_content, site_id : site_id}
		
		
		$.ajax({
			  type : "POST",
			  url : "/site/alarmadd.do",
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
		
	}); // 전송했을시 종료
	
});



