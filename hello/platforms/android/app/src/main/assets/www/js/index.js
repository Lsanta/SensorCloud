if(window.localStorage.getItem("key") != null && window.localStorage.getItem("key2") != null) {
  window.location.href="push.html";
}

$(document).ready(function(){

  $("#login").click(function(e){
      //e.preventDefault();
      alert("클릭");

      var query = {
          id : $("#id").val(),
          password : $("#pass").val()
      }

      var log = JSON.stringify(query);
      alert(log);
      
      $.ajax({
            type : "POST",
            url : "http://39.127.7.58:8080/app/mlog.do", 
            data : log,
            contentType : "application/json; charset=UTF-8",
            success : function(result){
              var sig = JSON.parse(result);
              console.log(sig)
              if(sig.signal == "ok"){
                  alert("로그인 성공");
                  window.localStorage.setItem("key", sig.id);
                  window.localStorage.setItem("key2", sig.password);
                  window.location.href="main.html";
              } else if (sig.signal == "passfail"){ 
                  alert("비밀번호 틀림");
              } else {
                alert("아이디 틀림");
              }

            }, // success 함수 종료
            error : function(request){
              alert(request);
              alert("에러");
            }

      }); // ajax함수 종료

  });
});


