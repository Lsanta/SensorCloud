/**
 * 
 */
$(document).ready(function(){

	//select 의 값이 바뀌면 ? 
	$('.kindSelect').change(function(){
		$("#sensorcount").val(0);
		var a = $('.kindSelect option:selected').val();
		$("#sensorcount").attr("max",a);
		
		var b = $('.kindSelect option:selected').text();
		
	});
	
});