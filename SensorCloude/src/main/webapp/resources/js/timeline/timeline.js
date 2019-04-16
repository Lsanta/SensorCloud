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
	var num = 0;

	var newURL =  window.location.pathname;

	var url = newURL.split('/');
	if(url[2] != null){
		$("#"+url[2]+"").addClass('pagination-active');
	}

	$('.pagination-inner a').on('click', function() {
		var a = $(".pagination-inner a").index(this);
		num = a;
		window.location.href = "/timeline/"+(num+1);
	});

	$('.pagination-newer').click(function(){
		if(1 > parseInt(url[2])-1 )
			window.location.href = "/timeline/"+(parseInt(url[2]));
		else
			window.location.href = "/timeline/"+(parseInt(url[2])-1);
	});

	$('.pagination-older').click(function(){
		if(parseInt($('.pagination-inner a:last').text()) < parseInt(url[2])+1 )
			window.location.href = "/timeline/"+(parseInt(url[2]));
		else
			window.location.href = "/timeline/"+(parseInt(url[2])+1);
	});

	
	
	
	});