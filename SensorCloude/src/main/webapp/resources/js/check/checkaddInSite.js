/**
 * 
 */

$(document).ready(function(){
	var newURL =  window.location.pathname;
	var url = newURL.split('/');
	
	//수정하기를 눌렀을때
	$(".modify").click(function(){
		
	});
	
	//목록으로를 눌렀을때
	$(".back").click(function(){
		alert("되어라");
		window.location.href="/site/"+url[4]+"/siterepair/1";
	});
	
});