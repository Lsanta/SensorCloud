$(document).ready(function() {
  
    var boardno = location.href.split("=");
    var board_no =boardno[1];
   console.log(board_no);
   
   $.ajax({
        type : "POST",
        url : "https://www.sensorcloud.site:8443/app/siterepairview",
        data : board_no,
        contentType : "application/json; charset=UTF-8",
        success : function(result){
            
            alert("점검이력 내용");
           $.each(result,function(i,q){
                

               console.log(q.board_content);
               console.log(q.title);
               console.log(q.reg_date);
               console.log(q.board_status);

               if(q.board_status==0){
                   var board_status = "open";
               }

               else if(q.board_status==1){
                   var board_status = "fixed";
               }
             
               $("#title").html(q.title);
               $("#status").html(board_status);
               $("#regdate").html(q.reg_date);
               $("#content").html(q.board_content);
              
            
});
        }
  });
    
    });