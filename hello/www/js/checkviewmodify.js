$(document).ready(function() {
    


    $("#tcontent").textinput({
        autogrow: false
   });
    // var copyarr;
     var boardno = location.href.split("=");
     var board_no =boardno[1];
     console.log(board_no);
 

     var file_path = null;

      /** 카메라 혹은 갤러리 호출 */
      function takePicture(source) {
        // 표시영역을 비운다.

        $(".uploadResult ul").empty();
        file_path = null;

        navigator.camera.cleanup();

        navigator.camera.getPicture(
            // 사진 가져오기에 성공한 경우 호출될 함수
            function (choice) {
                // 사진파일의 경로를 전역변수에 보관한다.
                file_path = choice;

                // Cordova의 WebView가 이미지를 캐시하는 것을 방지하기 위하여
                // 파일 경로 뒤에 timestamp를 덧붙인다.
                if (file_path.lastIndexOf('?') < 0) {//문자열에서
                    // 탐색하는 문자열이 마지막으로 등장하는 위치에 대한 index를 반환
                    file_path += "?" + new Date().getTime();
                }

                alert("수정페이지 카메라 이미지 경로"+file_path);

                // 이미지 화면 표시
                str += "<li>";
                str += "<img src='"+file_path +"'/>"
                str += "</li>";
                str +="<div id='deletespot'>";
                 str +="<span data-file=\'"+file_path+"\' data-type='image'>x</span>";
                 str += "</div>";

                $(".uploadResult ul").html(str);

                // var img = $("#repairpicture2").attr('src', file_path);
                // 외부 저장소(SD카드)의 최상위 경로 /storage/emulated/0
                // $("#result").append(info).append(img);
            },
            // 사진 가져오기에 실패한 경우 호출될 함수
            function (message) {
                alert('Failed because: ' + message);
            },
            // 카메라,갤러리 호출 옵션
            {
                destinationType: Camera.DestinationType.FILE_URI,   // 리턴타입 형식(파일경로)
                encodingType: Camera.EncodingType.JPEG, // 이미지 형식
                quality: 100,            // 퀄리티 (0~100)
                sourceType: source,      // 카메라 or 갤러리 설정
                allowEdit: true,        // 가져오기 완료 후 편집 여부
                correctOrientation: true, // 카메라를 세로로 촬영한 경우 이미지 방향을 회전시킴
                saveToPhotoAlbum: true,
                popoverOptions: CameraPopoverOptions,
                allowEdit: true
            }
        );
    }

    $(function () {
        // 카메라 버튼 이벤트 정의
        $("#cameraTakePicture").click(function () {
            takePicture(Camera.PictureSourceType.CAMERA);
        });

        // 갤러리 버튼 이벤트 정의
        $("#cameraGetPicture").click(function () {
            takePicture(Camera.PictureSourceType.PHOTOLIBRARY);
        });
    });



     //점검이력 글 내용 불러오기 
    $.ajax({
     type : "POST",
     url : "http://183.106.6.74:8080/app/siterepairview",
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
     url : "http://183.106.6.74:8080/app/checklist/mgetAttachList",
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
                 str += "<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'/>"
                 str += "</li>";
                 str +="<div id='deletespot'>";
                 str +="<span data-file=\'"+fileCallPath+"\' data-type='image'>x</span>";
                 str += "</div>";
                 
             }else{
                 alert("picture display fail");
             }
     
             });//end each
             
            $(".uploadResul t ul").html(str);
            //   파일삭제로 인해 요소값이 비어있는경우 썸네일 삭제
            //   if (!$('#uploadResult ul').length) {
            //     $('.uploadResult ul').remove();
            // }
 
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
     
     //span x 버튼 클릭시 수정 버튼 누르기 전 미리 파일 삭제 
     //파일이 업로드 후에 생성되기 때문에 이벤트 위임방식으로 처리 ajax를 이용해 첨부파일의 경로와 이름 , 파일의 종류를 전송
     $(".uploadResult").on("click" , "span" ,function(e){

        var targetFile = $(this).data("file");
        var type = $(this).data("type");
        console.log(targetFile);

        $.ajax({
            url : 'http://183.106.6.74:8080/app/checklist/mdeleteFile',
            data :{fileName : targetFile , type:type ,board_no : board_no},
            dataType : 'text',
            type:"POST",
            success: function(result){
                alert(result);
                //파일삭제후 동적으로 업로드 썸네일 부분을 삭제한다.
                $(".uploadResult *").remove();
            }

        });//파일삭제 ajax end

     });
 
     
     });
