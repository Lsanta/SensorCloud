
$(document).ready(function() {
  
    // var copyarr;
     var boardno = location.href.split("=");
     var board_no =boardno[1];
     console.log(board_no);
 


     //점검이력 글 내용 불러오기 
    $.ajax({
     type : "POST",
     url : "http://39.127.7.58:8080/app/siterepairview",
     data : board_no,
     contentType : "application/json; charset=UTF-8",
     success : function(result){
         
         alert("수정위한 점검내용");
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
          
            
            $("#ttitle").val(q.title);
            $("#sse").val(board_status).attr("selected","selected");
            $("#regdate").html(q.reg_date);
          
            $("#tcontent").val(q.title);
           
         
     });
     }
 });
 
 //썸네읿 불러오기
 $.ajax({
     type : "POST",
     url : "http://39.127.7.58:8080/app/checklist/mgetAttachList",
     data : board_no,
     async : false,
     contentType : "application/json; charset=UTF-8",
     success : function(arr){
          console.log(arr);
          var str = "";
          copyarr = arr;
          $(arr).each(function(i , attach){
             //<img class='delete1' src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
             //image type 
             
 
             if(attach.fileType){
                 // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
                 var fileCallPath = encodeURIComponent(attach.file_Path+"/s_"+attach.uuid+"_"+attach.file_name);

                
                 str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"' >";
                 str += "<img src='http://39.127.7.58:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'/>"+"<span data-file=\'"+fileCallPath+"\' data-type='image'>x</span>";
                 str += "</li>";
             }else{
                 alert("picture display fail");
             }
     
             });//end each
             
             $(".uploadResult ul").html(str);
 
     } // success 함수 종료
 }); // ajax함수 종료
 
 
 
     //썸네일 클릭 시 원본이미지 띄우기
     $(".uploadResult").on("click" , "li" , function(e){
 
         var liObj = $(this);
 
         var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
 
    
            showImage(path.replace(new RegExp(/\\/g),"/"));
        
            function showImage(fileCallPath){       
                $(".bigPictureWrapper").css("display","flex").show();
                
                $(".bigPicture")
                .html("<img src='http://39.127.7.58:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'>")
                .animate({width:'100%', height: '100%'}, 1000);
                
              }
    
              $(".bigPictureWrapper").on("click", function(e){
                $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
                setTimeout(function(){
                  $('.bigPictureWrapper').hide();
                }, 1000);
              });
        
 
     });  
 
     
     });