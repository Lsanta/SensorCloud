if(window.sessionStorage.getItem("id") != null || window.localStorage.getItem("id") != null) {
  window.location.href="main.html";
}

$(document).ready(function(){
  
 $(document).on('deviceready', function() {
    
    var Permission = window.plugins.Permission;
  
    // verify grant for a permission
    var permission = ['android.permission.ACCESS_COARSE_LOCATION','android.permission.ACCESS_FINE_LOCATION'];
    Permission.has(permission, function(results) {
      if(!results[permission]) {
        Permission.request(permission,function(results) {
          if (results['android.permission.ACCESS_COARSE_LOCATION','android.permission.ACCESS_FINE_LOCATION']) {
            // permission is granted
            cordova.plugins.diagnostic.isLocationEnabled(function(enabled){
              if(enabled) {
                   //true이기 때문에 그냥 실행?
              } else {
                   if(confirm('GPS가 꺼져 있습니다 설정창으로 이동하시겠습니까?') == true){
                    cordova.plugins.diagnostic.switchToLocationSettings();
                   }else{
                    return false;
                   }
                 
              }
              //cordova.plugins.diagnostic.switchToLocationSettings();
           }, function(error){
              alert("The following error occurred: "+error);
           }); 
          } else {cordova.plugins.diagnostic.isLocationEnabled(function(enabled){
            if(enabled) {
                 //true이기 때문에 그냥 실행?
            } else {
                 if(confirm('GPS가 꺼져 있습니다 설정창으로 이동하시겠습니까?') == true){
                  cordova.plugins.diagnostic.switchToLocationSettings();
                 }else{
                  return false;
                 }
               
            }
            //cordova.plugins.diagnostic.switchToLocationSettings();
         }, function(error){
            alert("The following error occurred: "+error);
         }); 
        }},alert)
      }
    }, alert)

    
  });


  $("#login").click(function(e){
      // e.preventDefault();
      var id = $("#id").val();
      var password = $("#pass").val();

      if(id == ""){
        alert("아이디를 입력하세요.");
        $("#id").focus();
        return false; 
      }
    if(password == ""){
        alert("비밀번호를 입력하세요.");
        $("#pass").focus();
        return false;
      }   

      var query = {
          id : $("#id").val(),
          password : $("#pass").val()
      }

      $.ajax({
            type : "POST",
            url : "https://52.79.242.145:8443/app/login/mlog", 
            //url : "http://39.127.7.58:8080/app/login/mlog", 
            data : query,
            contentType : "application/json; charset=UTF-8",
            success : function(result){ 
              
              if(result.signal == "ok"){
                if( $("input:checkbox[name='auto']").is(":checked") ) {
                  window.localStorage.setItem("id", result.id);
                  window.localStorage.setItem("password", result.password);
                  window.localStorage.setItem("auto", "true");
                }  else {
                  window.sessionStorage.setItem("id", result.id);
                  window.sessionStorage.setItem("password", result.password);
                  window.localStorage.setItem("auto", "false");
                }
                
                if(localStorage.getItem("level")) { localStorage.removeItem("level"); }
                window.localStorage.setItem("level", result.level); 
                window.location.href="main.html";

              } else if (result.signal == "passfail"){ 
                  alert("비밀번호가 일치하지 않습니다.");
              } else {
                alert("아이디가 일치하지 않습니다.");
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


// 엔터키 눌렀을 때 로그인 (보류) 
//  function login(){

//   var id = $("#id").val();
//   var password = $("#pass").val();

//   if(id == ""){
//     alert("아이디를 입력하세요.");
//     $("#id").focus();
//     return false; 
//   }
//   if(password == ""){
//     alert("비밀번호를 입력하세요.");
//     $("#pass").focus();
//     return false;
//   }   
  
//   var query = {
//     id : $("#id").val(),
//     password : $("#pass").val()
//   }

// $.ajax({
//       type : "POST",
//       // url : "http://52.79.242.145:8080/app/login/mlog", 
//       url : "http://39.127.7.58:8080/app/login/mlog", 
//       data : query,
//       contentType : "application/json; charset=UTF-8",
//       success : function(result){ 
//         // var sig = JSON.parse(result);
//         // alert(sig);
//         // var aa = JSON.stringify(result);
//         // alert(aa);
//         if(result.signal == "ok"){
//             window.sessionStorage.setItem("id", result.id);
//             window.sessionStorage.setItem("password", result.password);
//             window.location.href="main.html";
//         } else if (result.signal == "passfail"){ 
//             alert("비밀번호 틀림");
//         } else {
//           alert("아이디 틀림");
//         }

//       }, // success 함수 종료
//       error : function(xhr, ajaxOptions, thrownError){
//         if( xhr.status == 0 ||  xhr.status == 200){
//             alert(xhr.statusText);
//         }
//         alert(" 오류 : " + xhr.status + "\n" +
//                "메시지 :" + xhr.statusText + "\n" +
//                "응답 :" + xhr.responseText + "\n" + thrownError);
//        }

//     }); // ajax함수 종료
//  }

// // 패스워드에 커서를 두고 엔터키(모바일용)를 누르면 로그인 함 **해야함!
// $("#pass").keyup(function(key) {
//   if (key.keyCode == 13) {
//     login();
//   }
// });

// //아이디에 커서를 두고 엔터키를 누르면 로그인 함
// $("#id").keyup(function(key) {
//   if (key.keyCode == 13) {
//      login();
//   }
// });


});


