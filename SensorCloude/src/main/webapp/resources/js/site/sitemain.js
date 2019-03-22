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