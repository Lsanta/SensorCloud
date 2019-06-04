  // 장치 초기화가 완료된 직후 실행됨
  $(document).on('deviceready', function() {
    //Push 메시지가 수신된 경우 호출될 이벤트 등록
    FCMPlugin.onNotification(function(data){

        if(data.type == "Timeline") { //data.Type이 타임라인 이면 ( 타임라인 앱 푸쉬) 실행 

            // 필요한 값만 추출한다.       
            if(data.wasTapped){
            //Notification was received on device tray and tapped by the user.
                alert( "어디" + JSON.stringify(data) );
                //백그라운드로 왔으면
                if(localStorage.getItem("id") != null && sessionStorage.getItem("id") != null )
                location.href = "timeline.html";

            }else{
             //Notification was received in foreground. Maybe the user needs to be notified.
             //포그라운드  
             alert( "여기" + JSON.stringify(data) );
            
            Swal.fire({
                title: '제목?',
                text: "내용!",
                type: 'warning',
                customClass: 'popup : swal2-popup',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '이동!'
        }).then((result) => {
            if (result.value) {
            Swal.fire(
                '타임라인페이지로 이동합니다!',
                '내용',
                'success'
            )
            location.href = "timeline.html";
        }else {
            Swal.fire(
                '메인화면으로 이동합니다!',
                '내용',
                'error'
            )
            if(localStorage.getItem("id") != null && sessionStorage.getItem("id") != null ){
                location.href = "main.html";
            }
            
        }
        }) //swal 끝
    } // 포그라운드 끝



    } // data.Type이 타임라인 이면 ( 타임라인 앱 푸쉬) 실행 끝 
    
    else { // data.Type site 이면 임계값 관련 앱 푸쉬 실행
        if(data.wasTapped){
            //Notification was received on device tray and tapped by the user.
                alert( "어디" + JSON.stringify(data) );
                //백그라운드로 왔으면
                location.href = "timeline.html";
            }else{
            
            }


    } // data.Type site 이면 임계값 관련 앱 푸쉬 실행 끝

        

    }); //onnotificaiton 끝

}); // end deviceready