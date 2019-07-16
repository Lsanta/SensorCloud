
$(document).ready(function() {
    var split = location.href.split("?");
    var one = split[1].split("=");
    var board_no = one[1];
    if( split[2] != null) {
    var two = split[2].split("=");
    var site_id = two[1];



    // var copyarr;
    //   var boardno = location.href.split("=");
    //   var board_no =boardno[1];

    //  var siteid = site_id[2];

   //수정버튼 눌렀을 시
    $("#modifybutton").on("click" , function(e){
        
        window.location.href="checkviewmodify.html?board_no="+board_no+"?site_id="+site_id;
        
     });

     //삭제버튼 눌럿을 시 deletebutton
 
     $("#deletebutton").on("click" , function(e){
    
        
        var boardno = board_no;

        if (confirm("정말 삭제하시겠습니까?") == true){    //확인
            
          
            $.ajax({
                type : "POST",
                url : "http://183.106.6.74:8080/app/checklist/mdeletepost",
                data : {boardno: boardno},
                dataType:'text',
                success : function(result){
                    window.location.href="site.html?sid="+site_id; 
                  
                 }
                });
            

        }else{   //취소
            return;
        }

      
        
     });
     //점검이력 글 내용 불러오기 
    $.ajax({
     type : "POST",
     url : "http://183.106.6.74:8080/app/siterepairview",
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
            }  else if(q.board_status == 2){
                var board_status = "close";
            }
            
            $("#title").html(q.title);
            $("#status").html(board_status);
            $("#regdate").html(q.reg_date);
            $("#content").html(q.board_content);
           
            var user_id = q.user_id;
            
            console.log(user_id);

            var id;
            var m_level;

            if(localStorage.getItem("auto") == "true") {
              id = localStorage.getItem("id");
              m_level = localStorage.getItem("level");
            }       
            else if( localStorage.getItem("auto") == "false"){
             id = sessionStorage.getItem("id");
             m_level = localStorage.getItem("level");
            }

            if(user_id != id)  {
                if(m_level < 5) {
                    $('#modifybutton').css("display","none");
                    $('#deletebutton').css("display","none");
                }
            }
         
     });

     }
 });
 
 //썸네읿 불러오기
 $.ajax({
     type : "POST",
     url : "http://183.106.6.74:8080/app/checklist/mgetAttachList",
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
                //  alert(fileCallPath);
                
                 str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"'><div>";
                 str += "<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'/>";//172.26.2.227 //183.106.6.74:8080
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
                .html("<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'>")
                .animate({width:'100%', height: '100%'}, 1000);
                
              }
    
              $(".bigPictureWrapper").on("click", function(e){
                $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
                setTimeout(function(){
                  $('.bigPictureWrapper').hide();
                }, 1000);
              });
        
 
     });  
    } else { //마이페이지에서 클릭
    
    // var copyarr;
    //   var boardno = location.href.split("=");
    //   var board_no =boardno[1];

    //  var siteid = site_id[2];

   //수정버튼 눌렀을 시
    $("#modifybutton").on("click" , function(e){
        
        window.location.href="checkviewmodify.html?board_no="+board_no;
        
     });

     //삭제버튼 눌럿을 시 deletebutton
 
     $("#deletebutton").on("click" , function(e){
    
        
        var boardno = board_no;

        if (confirm("정말 삭제하시겠습니까?") == true){    //확인
            
          
            $.ajax({
                type : "POST",
                url : "http://183.106.6.74:8080/app/checklist/mdeletepost",
                data : {boardno: boardno},
                dataType:'text',
                success : function(result){
                    window.location.href="mypage.html"; 
                  
                 }
                });
            

        }else{   //취소
            return;
        }

      
        
     });
     //점검이력 글 내용 불러오기 
    $.ajax({
     type : "POST",
     url : "http://183.106.6.74:8080/app/siterepairview",
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
            }  else if(q.board_status == 2){
                var board_status = "close";
            }
            
            $("#title").html(q.title);
            $("#status").html(board_status);
            $("#regdate").html(q.reg_date);
            $("#content").html(q.board_content);
           
            var user_id = q.user_id;
            
            console.log(user_id);

            var id;
            var m_level;

            if(localStorage.getItem("auto") == "true") {
              id = localStorage.getItem("id");
              m_level = localStorage.getItem("level");
            }       
            else if( localStorage.getItem("auto") == "false"){
             id = sessionStorage.getItem("id");
             m_level = localStorage.getItem("level");
            }

            if(user_id != id)  {
                if(m_level < 5) {
                    $('#modifybutton').css("display","none");
                    $('#deletebutton').css("display","none");
                }
            }
         
     });

     }
 });
 
 //썸네읿 불러오기
 $.ajax({
     type : "POST",
     url : "http://183.106.6.74:8080/app/checklist/mgetAttachList",
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
                //  alert(fileCallPath);
                
                 str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"'><div>";
                 str += "<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'/>";//172.26.2.227 //183.106.6.74:8080
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
                .html("<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'>")
                .animate({width:'100%', height: '100%'}, 1000);
                
              }
    
              $(".bigPictureWrapper").on("click", function(e){
                $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
                setTimeout(function(){
                  $('.bigPictureWrapper').hide();
                }, 1000);
              });
        
 
     });  
    }
     
 });