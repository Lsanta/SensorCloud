/**
 * 
 */
$( window ).resize(function() {
	   //창크기 변화 감지
		if( $(window).width() >= 1000){
			$("#nav").css("display","block");
			$("#menu").css("display","block");
			
		} else{
			$("#nav").css("display","none");
		}
	});

$( "#nav-btn" ).click(function() {
	  $( "#nav" ).toggle( "slow" );
	  $("#menu").css("display","block");
	});

$("#down").click(function(){
	 $("#down").css("display","none");
	 $("#up").css("display","inline");
//	 $("#num").css("color","#2b99ff");
	 $(".phonenumber").css("display","block");
	});

$("#up").click(function(){
	 $("#up").css("display","none");
	 $("#down").css("display","inline");
//	 $("#num").css("color","#e3e2e2");
	 $(".phonenumber").css("display","none");
});

$("#num").click(function(){
	var down = $("#down");

	if( down.css('display') == "inline" ) {
		 $("#down").css("display","none");
		 $("#up").css("display","inline");
		 $(".phonenumber").css("display","block");
	} else{
		 $("#up").css("display","none");
		 $("#down").css("display","inline");
		 $(".phonenumber").css("display","none");
	}
});

$(document).on("click", "h1" , function(){
	 /*현장 아이디를 가지고와서 /1자리에 넣어야함 */
	 var sid = $("#sid").text();
	 
	 window.location.href = "/site/" + sid  ;
	 
});