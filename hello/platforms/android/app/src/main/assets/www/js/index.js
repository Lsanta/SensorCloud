if(window.localStorage.getItem("key") != null && window.localStorage.getItem("key2") != null) {
  window.location.href="main.html";
}

$(document).ready(function(){

  $("#login").click(function(e){
      // e.preventDefault();
      
      var query = {
          id : $("#id").val(),
          password : $("#pass").val()
      }

      var log = JSON.stringify(query);
      alert(log);
      
      $.ajax({
            type : "POST",
            url : "http://39.127.7.58:8080/app/mlog", 
            data : query,
            contentType : "application/json; charset=UTF-8",
            success : function(result){ 
              alert(result);
              // var sig = JSON.parse(result);
              // alert(sig);
              // var aa = JSON.stringify(result);
              // alert(aa);
              if(result.signal == "ok"){
                  alert("로그인 성공");
                  window.localStorage.setItem("key", result.id);
                  window.localStorage.setItem("key2", result.password);
                  window.location.href="main.html";
              } else if (sig.signal == "passfail"){ 
                  alert("비밀번호 틀림");
              } else {
                alert("아이디 틀림");
              }

            }, // success 함수 종료
            error : function(xhr, ajaxOptions, thrownError){
              if( xhr.status == 0 ||  xhr.status == 200){
                  alert(xhr.statusText);
              }
              alert(" 오류 : " + xhr.status + "\n" +
                     "메시지 :" + xhr.statusText + "\n" +
                     "응답 :" + xhr.responseText + "\n" + thrownError);
            }

      }); // ajax함수 종료

  });
});

// function loadTweets() {
//   var request = new XMLHttpRequest();
//   var id = $("#id").val();
//   var password = $("#pass").val()
//   request.open("GET", "http://39.127.7.58:8080/app/mlog2.do?"+id+"="+password+"", true);
//   request.onreadystatechange = function() {//Call a function when the state changes.
//       if (request.readyState == 4) {
//           if (request.status == 200 || request.status == 0) {
//               var tweets = JSON.parse(request.responseText);
//               window.location.href="main.html";
//           }
//       }
//   }
//   console.log("asking for tweets");
//   request.send();
// }


