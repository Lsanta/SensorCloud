$(document).ready(function () {

    var m_level;

    if(localStorage.getItem("auto") == "true") {
      m_level = localStorage.getItem("level");
    }       
    else if( localStorage.getItem("auto") == "false"){
     m_level = localStorage.getItem("level");
    }

    if(m_level < 5) {
        $(".none").css("display","none");
    }

    var split = location.href.split("?");
    var one = split[1].split("=");
    var board_no = one[1];
    var two = split[2].split("=");
    var site_id = two[1];

    console.log(board_no);



    //목록으로 버튼을 눌렀을 시 
    $("#returnlistbutton").on("click" , function(e){
        
        window.location.href="site.html?sid="+site_id; 
        
     });

     //수정완료 버튼을 눌렀을 시 

     $("#modifybutton").on("click" , function(e){
        
        if (confirm("수정을 완료 하시겠습니까?") == true){    //확인
            
            //입력값 + site_id + user_id 불러오기
            var checktitle = $("#ttitle").val();

            var status = $("#sse option:selected").val();
            
            if(status=="open"){
                var status = 0;
            }

            else if(status=="fixed"){
                var status =1;
            }
            
            var content = $("#tcontent").val();
          
          

            var checkcontent = { //유저가 입력한 정보를 js오브젝트로
                title : checktitle, 
                board_content :content,
                board_status : status,
                site_id : site_id,
                board_no: board_no
             };

            $.ajax({
                type : "POST",
                url : "http://183.106.6.74:8080/app/checklist/mcheckmodify",
                data : JSON.stringify(checkcontent),
                contentType : "application/json; charset=UTF-8",
                dataType:'text',
                success : function(result){
                    alert("수정완료");
                    window.location.href="site.html?sid="+site_id; 
                  
                 }
                });
            

        }else{   //취소
            return;
        }
        
     });



    $("#tcontent").textinput({
        autogrow: false
    });
    // var copyarr;
   

     //썸네읿 불러오기
     $.ajax({
        type: "POST",
        url: "http://183.106.6.74:8080/app/checklist/mgetAttachList",
        data: board_no,
        async: false,
        contentType: "application/json; charset=UTF-8",
        success: function (arr) {
            console.log(arr);
            var str = "";
            copyarr = arr;
            $(arr).each(function (i, attach) {
                //<img class='delete1' src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
                //image type 


                if (attach.fileType) {
                    // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
                    var fileCallPath = encodeURIComponent(attach.file_Path + "/s_" + attach.uuid + "_" + attach.file_name);
                    
                    str += "<li data-path='"+attach.file_Path+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.file_name+"' data-type='"+attach.fileType+"'><div>";
                    str += "<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName="+fileCallPath+"'/>";//172.26.2.227 //183.106.6.74:8080
                    str += "</div>";
                    str += "</li>";
                    str += "<div id='deletespot'>";
                    str += "<span data-file=\'" + fileCallPath + "\' data-type='image' data-uuid=\'" +attach.uuid + "\'>x</span>";
                    str += "</div>";
                


                } else {
                    alert("picture display fail");
                }

            });//end each

            $(".uploadResult ul").html(str);
            //   파일삭제로 인해 요소값이 비어있는경우 썸네일 삭제
            //   if (!$('#uploadResult ul').length) {
            //     $('.uploadResult ul').remove();
            // }

        } // success 함수 종료
    }); // ajax함수 종료


    // 사용자가 선택한 사진의 경로
    var file_path = null;

    /** 카메라 혹은 갤러리 호출 */
    function takePicture(source) {
        // 표시영역을 비운다.

        // $(".uploadResult ul").empty();
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

                alert("수정페이지 카메라 이미지 경로" + file_path);

                //수정시 이미지 파일을 추가 할 시 바로 c 경로에 저장 + 썸네일 불러오기
                if (file_path != null) {
                    uploadP(file_path);
                }

                function uploadP(file_path) {

                    alert("갤러리 이미지 업로드 후 c경로에 upload를 위한"+board_no);
                    /** 파일경로로부터 이름만 추출한다. */
                    //string.substring( start, end ) start필수 end 없으면 끝까지


                    var name = file_path.substring(file_path.lastIndexOf('/') + 1);

                    // 안드로이드는 파일이름 뒤에 ?123234234 형식의 내용이 붙어 오는 경우가 있으므로,
                    // 이 경우 ? 이하 내용을 잘라버린다.

                    var p = name.toLowerCase().lastIndexOf('?');
                    if (p > -1) {
                        name = name.substring(0, p);
                    }

                    // 안드로이드는 확장자가 없는 경우가 있으므로, 이 경우 확장자를 강제로 추가한다.
                    if (name.toLowerCase().lastIndexOf('.') < 0) {
                        name += '.jpg';
                    }

                    /** 업로드 옵션 */
                    var options = new FileUploadOptions();
                    // <input type='file' name='photo' > 과 같은 원리
                    options.fileKey = "file";
                    // 서버에 인식시킬 파일의 이름
                    options.fileName = name;
                    // 서버에 인식시킬 파일의 종류
                    options.mimeType = 'image/jpeg';
                    // 파일의 원본 데이터를 비교하지 않는다.
                    //post형식
                    options.httpMethod = 'POST';
                    options.trustAllHosts = true;
                    options.chunkedMode = false;
                    // 전송후 통신 종료 예약
                    options.headers = { Connection: "close" };

                    var params = {};
                    params.value1 = file_path;
                    params.value2 = board_no;

                    options.params = params;

                    /** 파일 전송 객체 생성 */
                    var ft = new FileTransfer();

                    /** 업로드를 수행한다.(전송할 파일 경로, 서버 주소, 성공콜백, 실패콜백, 옵션) *///"http://183.106.6.74:8080/app/checklist/insertfile"
                    ft.upload(file_path, "http://183.106.6.74:8080/app/checklist/insertfile", //183.106.6.74:8080

                        function(r) {
                            alert(r.responseCode + "upload is complete");

                            //수정시 갤러리 이미지 업로드 후 다시 읽어옴 
                            //썸네읿 불러오기
                            $.ajax({
                                type: "POST",
                                url: "http://183.106.6.74:8080/app/checklist/mgetAttachList",
                                data: board_no,
                                async: false,
                                contentType: "application/json; charset=UTF-8",
                                success: function (arr) {
                                    console.log(arr);
                                    var str = "";
                                    copyarr = arr;
                                    $(arr).each(function (i, attach) {
                                        //<img class='delete1' src='https://www.sensorcloud.site:8443/display?fileName="+fileCallPath+"'>";
                                        //image type 


                                        if (attach.fileType) {
                                            // var fileCallPath = encodeURIComponent( attach.file_path+ "/s_"+attach.uuid + "_"+attach.file_name);
                                            var fileCallPath = encodeURIComponent(attach.file_Path + "/s_" + attach.uuid + "_" + attach.file_name);


                                            str += "<li data-path='" + attach.file_Path + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.file_name + "' data-type='" + attach.fileType + "' >";
                                            str += "<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName=" + fileCallPath + "'/>" //183.106.6.74:8080
                                            str += "</li>";
                                            str += "<div id='deletespot'>";
                                            str += "<span data-file=\'" + fileCallPath + "\' data-type='image' data-uuid=\'" +attach.uuid + "\'>x</span>";
                                            str += "</div>";

                                        } else {
                                            alert("picture display fail");
                                        }

                                    });//end each

                                    $(".uploadResult ul").html(str);
                                    //   파일삭제로 인해 요소값이 비어있는경우 썸네일 삭제
                                    //   if (!$('#uploadResult ul').length) {
                                    //     $('.uploadResult ul').remove();
                                    // }

                                } // success 함수 종료
                            }); // ajax함수 종료


                        },//function(r) 성공 콜백함수 끝
                        function (error) {
                            if (error.code == FileTransferError.FILE_NOT_FOUND_ERR) {
                                alert("file " + error.source + " not found");
                            } else if (error.code == FileTransferError.INVALID_URL_ERR) {
                                alert("url " + error.target + " invalid");
                            } else if (error.code == FileTransferError.CONNECTION_ERR) {
                                alert("connection error");
                            } else {
                                alert("unknown error");
                            }
                        },
                        options);
                }


                // 이미지 화면 표시

                // str += "<li>";
                // str += "<img src="+file_path+"/>";
                // str += "</li>";
                // str +="<div id='deletespot'>";
                // str +="<span>x</span>";
                // str += "</div>";

                // $(".uploadResult ul").html(str);


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
        type: "POST",
        url: "http://183.106.6.74:8080/app/siterepairview", //183.106.6.74:8080
        data: board_no,
        contentType: "application/json; charset=UTF-8",
        success: function (result) {

            $.each(result, function (_i, q) {

                console.log(q.board_content);
                console.log(q.title);
                console.log(q.reg_date);
                console.log(q.board_status);

                if (q.board_status == 0) {
                    var board_status = "open";
                }

                else if (q.board_status == 1) {
                    var board_status = "fixed";
                }  else if (q.board_status == 1) {
                    
                    var board_status = "close";
                }


                $("#ttitle").val(q.title);
                $("#sse").val(board_status).attr("selected", "selected");
                $("#regdate").html(q.reg_date);

                $("#tcontent").val(q.board_content);


            });
        }
    });

   


    //썸네일 클릭 시 원본이미지 띄우기
    $(".uploadResult").on("click", "li", function (e) {

        var liObj = $(this);

        var path = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));


        showImage(path.replace(new RegExp(/\\/g), "/"));

        function showImage(fileCallPath) {
            $(".bigPictureWrapper").css("display", "flex").show();

            $(".bigPicture")
                .html("<img src='http://183.106.6.74:8080/app/checklist/mdisplay?fileName=" + fileCallPath + "'>")//183.106.6.74:8080 
                .animate({ width: '100%', height: '100%' }, 1000);

        }

        $(".bigPictureWrapper").on("click", function (e) {
            $(".bigPicture").animate({ width: '0%', height: '0%' }, 1000);
            setTimeout(function () {
                $('.bigPictureWrapper').hide();
            }, 1000);
        });


    });

    //span x 버튼 클릭시 파일 삭제 
    //파일이 업로드 후에 생성되기 때문에 이벤트 위임방식으로 처리 ajax를 이용해 첨부파일의 경로와 이름 , 파일의 종류를 전송
    $(".uploadResult").on("click", "span", function (e) {

        var targetFile = $(this).data("file");
        var type = $(this).data("type");
        var uuid = $(this).data("uuid");
        console.log(uuid);

        $.ajax({
            url: 'http://183.106.6.74:8080/app/checklist/mdeleteFile',//183.106.6.74:8080
            data: { fileName: targetFile, type: type, boardno: board_no , uuid:uuid },
            dataType: 'text',
            type: "POST",
            success: function (result) {
                alert(result);
                //사진 삭제후 화면 갱신
                window.location.href="checkviewmodify.html?board_no="+board_no+"?site_id="+site_id;

                //파일삭제후 해당 썸네일 부분을 삭제한다.
                //  $(".uploadResult *").remove();
                
            }

        });//파일삭제 ajax end

    });


});