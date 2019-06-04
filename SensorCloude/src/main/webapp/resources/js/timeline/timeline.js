$(document).ready(function(){
	$('textarea').keyup(function() {

		var characterCount = $(this).val().length,
		current = $('#current'),
		maximum = $('#maximum'),
		theCount = $('#the-count');

		current.text(characterCount);

	});


	/*글 등록*/
	$("#submit").click(function(){
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

	$(".delete").on('click', function() {

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

	$(".modify").on('click',function() {

		var div = $(this).parent().attr('id');

		var content = $(this).parent();
		var content2 = content.siblings().first();
		var content3 = content2.text();

		var query = {
				content : content3
		}

		window.location.href = "/timeline/timelinemodify?content="+ content3+"/"+div;

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