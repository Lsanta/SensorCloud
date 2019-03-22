/**
 * 
 */

$( "#nav-btn" ).click(function() {
  $( "nav" ).toggle( "slow" );
});

$( window ).resize(function() {
	   //창크기 변화 감지
		if( $(window).width() <= 1000){
			$("#nav").css("display","none");
		} else{
			$("#nav").css("display","block");
			$("#menu").css("display","block");
		}
	});

