$(document).ready(function() {
  
    var boardno = location.href.split("=");
    var board_no =boardno[1];
   console.log(board_no);

   $.ajax({
        type : "POST",
        url : "http://52.79.242.145:8080/app/siterepairview",
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


//ajax로변경
//   $.getJSON("http://39.127.7.58:8080/app/checklist/mgetAttachList" ,{board_no : board_no},function(arr){


//     //[CheckBoardFileVO(board_no=260, uuid=d45e4139-79b3-447a-80d7-948a178fedb6, 
//       //file_Path=2019/06/07, file_name=.Pic.jpg, fileType=true)]

//    var str ="";
//     //jQuery를 사용해 배열을 관리하고자 할 때 each() 메서드/ /$.each() 메서드의 첫번째 매개변수로 배열이나 객체 선언

//     //i = index, attach :item
//    $(arr).each(function(i , attach){

//     //image type 
//     alert("파일패스"+attach.file_Path+"uuid"+attach.uuid+"filename"+attach.file_name);
//     if(attach.fileType){
//         // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
//         var fileCallPath = encodeURIComponent(attach.file_Path+"/s_"+attach.uuid+"_"+attach.file_name);
//         alert(fileCallPath);
//         str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"' ><div>";
//         str += "<img src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
//         str += "</div>";
//         str += "</li>";
//     }else{
//         alert("picture display fail");
//     }


//     });//end each

//     $(".uploadResult ul").html(str);
//     }); //end getjson

$.ajax({
    type : "POST",
    url : "http://39.127.7.58:8080/app/checklist/mgetAttachList",
    data : board_no,
    async : false,
    contentType : "application/json; charset=UTF-8",
    success : function(arr){
         console.log(arr);
         var str = "";
         
         $(arr).each(function(i , attach){
            //<img class='delete1' src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
            //image type 
            alert("파일패스"+attach.file_Path+"uuid"+attach.uuid+"filename"+attach.file_name);

            if(attach.fileType){
                // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
                var fileCallPath = encodeURIComponent(attach.file_Path+"/s_"+attach.uuid+"_"+attach.file_name);
                alert("이미지src에 들어갈 파일경로"+fileCallPath);

                str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"' ><div>";
                //"<img class='delete1' src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
                str += "<img src='http://39.127.7.58:8080/display?fileName="+fileCallPath+"'/>";
                str += "</div>";
                str += "</li>";
            }else{
                alert("picture display fail");
            }
    
            });//end each
            
            $(".uploadResult ul").html(str);

    } // success 함수 종료
}); // ajax함수 종료


    
    });