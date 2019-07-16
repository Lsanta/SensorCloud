

$(document).ready(function(){
	
	$.ajax({
		type : 'POST',
		url : '/timeJSON',
		data : {},
		dataType : 'JSON',
		success : function(data) {
			var str = ""
			var color = ['','feed-item-secondary','feed-item-waring','feed-item-danger','feed-item-info','feed-item-success' ];
			var stylesheet = "";
			
			$.each(data,function(i,s){
					var num = i % 6;
//					str += "<li class='feed-item "+color[num]+"'>";
					str += "<li class='feed-item'>";
					if($("#sessionid").text() == data[i].user_id) 
						str += "<time class='date'><p style='display:none'>"+data[i].timeline_n+"</p><span class='name'>"+data[i].name+"</span><a class='modify'>수정 |</a> <a class='delete'>삭제</a>" + "<span class='time'>"+data[i].time +"</span></time>";
					else 
						str += "<time class='date'><p style='display:none'>"+data[i].timeline_n+"</p><span class='name'>"+data[i].name + "</span><span class='time'>"+data[i].time +"</span></time>";
					str += "<span class='text'>"+data[i].content+"</span>";
					str += "</li>";
					$('.activity-feed').html(str);	
			});
			
			for(var i=0; i < data.length; i++) {
				var li = $(".activity-feed").children().eq(i);
				stylesheet = ".add"+i+":after{background-color : "+ data[i].color +" !important}";
				$('#dd').append(stylesheet);
				
				li.addClass('add'+i);
			}
		}
	});
	
	
	$('textarea').keyup(function() {

		var characterCount = $(this).val().length,
		current = $('#current'),
		maximum = $('#maximum'),
		theCount = $('#the-count');

		current.text(characterCount);

	});


	/*글 등록*/
	$("#submit").click(function(){
		
		if($("#level").text() <= 2) {
			alert("권한이 없습니다. 3등급(쓰기권한)이상이 작성가능합니다");
			return false;
		}
		
		var textarea  = $("#textarea").val();
		var query = {content:$("#textarea").val()};



		$.ajax({
			type : "POST",
			url : "/timeline/timeline.do",
			data : query,
			success : function(data){
				if( data == "success"){
					alert("등록되었습니다.");
					location.reload();
					
					$.ajax({
                        type : "POST",
                        url : "/app/send/WebTimelinemessage.do",
                        data : query,
                        contentType : "application/json; charset=UTF-8",
                        success : function(data) {
                        }
                 }); //ajax 종료
				} else{
					location.reload();

				}
			}

		});

	}); 

	$(document).on('click','.delete', function() {
		
		if (confirm("정말 삭제하시겠습니까?") == true) {

		} else {
			return;
		}

		if (true) {
			var timeline_n = $(this).siblings().eq(0).text();
			var query = {
					timeline_n : timeline_n
			}

			$.ajax({
				type : "POST",
				url : "/timeline/timelinedelete.do",
				data : query,
				success : function(data) {
					if (data == "success") {
						alert("삭제되었습니다.");
						location.reload();
					} else {
						alert("삭제에 실패했습니다.");
					}
				}

			}); // ajax 종료
			/*}*/
		}

	});

	$(document).on('click','.modify',function() {
		
		var range = $(this).parent().next();
		var text = $(this).parent().next().text(); //수정 누른 글 내용
		$(this).remove();
		range.html(
		"<textarea  name='textarea' id='textarea2' maxlength='100' autofocus></textarea> " +
		"<input type='button' id='check2' class='check2' value='수정' />" +
		"<input type='button' class='delete2' value='취소'/> " +
		"<div id='the_count2'>" +
		"<span id='current2'>"+0+"</span><span id='maximum2'>"+ '/'+100+"</span></div>");
		
		var textarea = range.children().eq(0).text(text);
		
		var current = range.children().eq(3).children().eq(0);
		var currentNum = range.children().eq(0).text().length;
		
		current.text(currentNum);
		
//		var div = $(this).parent().attr('id');
//		
//		var content = $(this).parent();
//		var content2 = content.siblings().first();
//		var content3 = content2.text();
//
//		var query = {
//				content : content3
//		}
//
//		window.location.href = "/timeline/timelinemodify?content="+ content3+"/"+div;

	});
	
	$(document).on('keyup','#textarea2',function() {

		var characterCount = $(this).val().length;
		current2 = $('#current2');
		maximum = $('#maximum2');
		theCount = $('#the-count2');

		current2.text(characterCount);

	});
	
	$(document).on('click','.delete2',function(){
		if (confirm("수정을 취소하시겠습니까?") == true) {
			window.location.href = "/time/1"
		} else {
			return;
		}
	});
	
	$(document).on('click','#check2',function(){
		var textarea  = $("#textarea2").val();
		var query = {
			content: textarea,
			timeline_n:$('#check2').parent().siblings().first().children().eq(0).text()
		};
		
		$.ajax({
			  type : "POST",
			  url : "/timeline/timelinemodify.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					alert("수정에 성공하였습니다.");
					window.location.href = "/time/1";
				  } else{
					alert("수정이 실패하였습니다.");
				
				  }
			  }
		
		}); 
	});
	
	$('#textarea').keypress(function(event){
		if ( event.which == 13 ) {
			$('#submit').click();

		}
	});

	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[2] != null){
		$("#"+url[2]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		var b = $(".pagination-inner a:eq("+a+")").attr("id");
		num = b;
		window.location.href = "/time/"+num;
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/time/"+(parseInt(url[2]));
		else
			window.location.href = "/time/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		window.location.href = "/time/"+(parseInt(url[2])+1);
	});
	
	$(".firstpage").click(function(){
		window.location.href = "/time/1";
	});
	
	$(".lastpage").click(function(){
		window.location.href = "/time/"+$("#lastNum").text();
	});


});