$(document).on("click", "#mypagechecklist tr", function() {

	// 클릭한 행을 tr 변수로
	var tr = $("#mypagechecklist tr").index(this);

	var board_no = $("#mypagechecklist tr:eq(" + tr + ") td:eq(4)").text();
	// 내가클릭한 테이블의 행을 판별해야하기위해 board_no 정보를 넘긴다
	
	window.location.href = "/mypage/checkboard/" + board_no;
});

$(document).ready(function() {
	

 ////////////////////*pagination*//////////////////////////////////
	
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');

	if(url[2] != null){
		$("#"+(url[2])+"").addClass('pagination-active');
	}
	
	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		var b = $(".pagination-inner a:eq("+a+")").attr("id");
		num = b;
		window.location.href = "/mypage/"+num;
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/mypage/"+(parseInt(url[2]));
		else
			window.location.href = "/mypage/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
			window.location.href = "/mypage/"+(parseInt(url[2])+1);
	});
	
	$(".firstpage").click(function(){
		window.location.href = "/mypage/1";
	});
	
	$(".lastpage").click(function(){
		window.location.href = "/mypage/"+$("#lastNum").text();
	});
	
	////////////////////////////////////////////////////////////////////
	
	
	
	$('.update').click(function() {
		window.location.href = "/mypage/modifymypage";
	});
	
	$('.up').click(function(){
		window.open("/mypage/levelup", "pop",
				"width=570,height=420,top="+(screen.availHeight/2-300)+",left="+(screen.availWidth/2-300)+"resizable=yes");
	});
	
	   $("#btnno").on("click",function(e){
		      
//		    var targetFile = $(".delete1").data("file");
		    
		  	var targetFile1 = $(".delete1").attr('src');
		  	var targetFile = targetFile1.split("=");
		  	var user_id = $(".zzz").text();
		  	
		    var type = $(".delete").data("type");
		    
		    var targetLi = $(".delete").closest("ul");

		    $.ajax({
		      url: '/deleteFilemypage',
		      data: {fileName: targetFile[1], type:type ,user_id:user_id},
		      dataType:'text',
		      type: 'POST',
		        success: function(result){  
		           targetLi.remove();
		           location.reload();
		         }
		    }); //$.ajax
		   });
	


	// 

	// 패스워드에 커서를 두고 엔터키를 누르면 로그인 함
	$("#pass").keydown(function(key) {
		if (key.keyCode == 13) {
			login();
		}
	});

});
