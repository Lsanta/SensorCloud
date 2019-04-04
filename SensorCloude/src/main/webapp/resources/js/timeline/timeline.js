$(document).ready(function(){
	$('textarea').keyup(function() {
	    
		  var characterCount = $(this).val().length,
		      current = $('#current'),
		      maximum = $('#maximum'),
		      theCount = $('#the-count');
		    
		  current.text(characterCount);
		      
		});

	
	/*글 등록*/
	$("#submit").click(function(){
				var textarea  = $("#textarea").val();
				var query = {content:$("#textarea").val()};
				
		alert(textarea);
		
		$.ajax({
			  type : "POST",
			  url : "/timeline/timeline.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					  alert("타임라인이 등록되었습니다.");
					  location.reload();
				  } else{
					  alert("타임라인 등록에 실패하셨습니다.");
					  location.reload();
					
				  }
			  }
			  
		});
		
	}); 	
});