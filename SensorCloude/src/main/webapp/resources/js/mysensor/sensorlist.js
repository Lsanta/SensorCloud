$(document).ready(function() {

	
	$("#add").click(function(){
		window.open("/mysensor/mysensoradd", "pop",
		"width=570,height=420,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	
	$(".delete").on('click', function(){
		var sn = $(this).parent();
		var sn2 = sn.siblings().first();
		var sn3 = sn2.text(); // 선택한 것 중 센서 번호

		var query = {
				sensor_sn : sn3
		}
		
		$.ajax({
			  type : "POST",
			  url : "/mysensor/mysensordelete.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					  alert("삭제되었습니다");
					  location.reload();
				  } else{
					  alert("삭제에 실패했습니다");
				  }
			  }
			  
		}); // ajax 종료
		
	});
	
	
	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
});