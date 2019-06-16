
$(document).ready(function() {
    // var copyarr;
      var boardno = location.href.split("=");
      var board_no =boardno[1];

   //수정버튼 눌렀을 
    $("#modifybutton").on("click" , function(e){
    
        window.location.href="checkviewmodify.html?board_no="+board_no;
        
     });
 
     //점검이력 글 내용 불러오기 
    $.ajax({
     type : "POST",
     url : "http://15.164.166.25:80/app/siterepairview",
     data : board_no,
     contentType : "application/json; charset=UTF-8",
     success : function(result){
         
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
 
 //썸네읿 불러오기
 $.ajax({
     type : "POST",
     url : "http://15.164.166.25:80/app/checklist/mgetAttachList",
     data : board_no,
     async : false,
     contentType : "application/json; charset=UTF-8",
     success : function(arr){
          console.log(arr);
          var str = "";
          copyarr = arr;
          $(arr).each(function(i , attach){
             if(attach.fileType){
                 // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
                 var fileCallPath = attach.file_Path+"/s_"+attach.uuid+"_"+attach.file_name;
                 // alert(fileCallPath);
                
                 str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"'><div>";
                 str += "<img src='http://15.164.166.25:80/app/checklist/mdisplay?fileName="+fileCallPath+"'/>";
                 str += "</div>";
                 str += "</li>";

             }else{
                 alert("이미지 불러오기가 실패했습니다.");
             }
     
             });//end each
             
             $(".uploadResult ul").html(str);

             //파일삭제로 인해 요소값이 비어있는경우 썸네일 삭제
            //  if (!$('#uploadResult ul').length) {
                
            //     $('.uploadResult ul').remove();
            //   }
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
                .html("<img src='http://15.164.166.25:80/app/checklist/mdisplay?fileName="+fileCallPath+"'>")
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