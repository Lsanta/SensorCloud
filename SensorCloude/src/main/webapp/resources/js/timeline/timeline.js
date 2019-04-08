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
				

		
		$.ajax({
			  type : "POST",
			  url : "/timeline/timeline.do",
			  data : query,
			  success : function(data){
				  if( data == "success"){
						  location.reload();
				  } else{
				  location.reload();
					
				  }
			  }
			  
		});
		
	}); 
	

	
	
	
	});