$(document).ready(function(){
	$('textarea').keyup(function() {
	    
		  var characterCount = $(this).val().length,
		      current = $('#current'),
		      maximum = $('#maximum'),
		      theCount = $('#the-count');
		    
		  current.text(characterCount);
		      
		});

	
	
	$("#submit").click(function(){
				var textarea  = $("#textarea").val();
				var query = {user_id:$("#id").val()
						};
				
		alert(textarea);
		
		$.ajax({
			  type : "POST",
			  url : "/timeline/timeline.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
					  alert("ㅇ");
			
					  location.reload();
				  } else{
					  alert("ㄴ");
					
				  }
			  }
			  
		});
		
	}); 
	
	
	
	
	
});