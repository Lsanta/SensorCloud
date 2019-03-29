/**
 * 
 */
$(document).ready(function(){
	
	//체크박스 맨위 누르면 전부다 선택 or 해제
	$('input[name=checkAll]').on('change', function(){
		 $('input[name=selected]').prop('checked', this.checked);
	});
	
	//+버튼 클릭
	$("#plus-btn").click(function(){
		//팝업창으로 입력폼 띄우고 정보 전달 후 Insert
		var add = window.open("/alarmadd","add","width=570,height=420, scrollbars=no, resizable=no")
	});
});
