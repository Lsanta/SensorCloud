$(document).ready(function() {

	$('.pagination-inner a').on('click', function() {
		$(this).siblings().removeClass('pagination-active');
		$(this).addClass('pagination-active');
	});
	
	
	$(document).on("click", "#user tr" , function(){
		 
		 var tr = $("#user tr").index(this);
		 var id = $("#user tr:eq("+tr+") td:eq(0)").text();		 
		 goPopup(id);
		 
	}); 
	
	function goPopup(id) {
		var pop = window.open("/manage/usermodify/"+ id, "pop",
				"width=570,height=530,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");	
		
		/*사용자 수정*/
		$('#confirm-modify').on('click', function() {
			var query = {
					modify_name : $("#modify_name").val(),
					phonenumber : $("#phonenumber").val(),
					level : $('options[target.selectedIndex].value').val()
			}
			
			console.log(query);
			
			$.ajax({
				type : "POST",
				url : "/usermanage/updateuser.do",
				data : query,
				success : function(data) {
					if (data == "success") {
						alert("수정완료");
						window.location.href = "/manage"
					} else{
						  alert("추가에 실패했습니다");
					}
				}
			
			});	// ajax 종료
		}); // #confirm-modify 종료
	}

	
	/*pagination*/
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[2] != null){
		$("#"+url[2]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/manage/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/manage/"+(parseInt(url[2]));
		else
			window.location.href = "/manage/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/manage/"+(parseInt(url[2]));
		else
			window.location.href = "/manage/"+(parseInt(url[2])+1);
	});

});

