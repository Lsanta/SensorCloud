  // 장치 초기화가 완료된 직후 실행됨
  $(document).on('deviceready', function() {
    //Push 메시지가 수신된 경우 호출될 이벤트 등록
    FCMPlugin.onNotification(function(data){
        // 필요한 값만 추출한다.
        var data2 = JSON.stringify(data);
        alert(data2);
        
        if(data.wasTapped){
        //Notification was received on device tray and tapped by the user.
            alert( "어디" + JSON.stringify(data) );
            //백그라운드로 왔으면
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
            '새페이지로!',
            '내용',
            'success'
        )
        location.href = "timeline.html";
    }else {
        Swal.fire(
            '메인화면으로!',
            '내용',
            'error'
        )
        location.href = "main.html";
    }
}) //swal 끝
        } // 포그라운드 끝

    }); //onnotificaiton 끝

}); // end deviceready