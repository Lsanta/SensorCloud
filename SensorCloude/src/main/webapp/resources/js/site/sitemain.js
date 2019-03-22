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
	});


$("#up").click(function(){
	 $("#up").css("display","none");
	 $("#down").css("display","inline");
});

$("#number").click(function(){
	
});