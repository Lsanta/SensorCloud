$(document).ready(function() {
//내정보 불러오기

var query = {
    id: window.sessionStorage.getItem("id")
};

$.ajax({
    type : "POST",
    url : "http://39.127.7.58:8080/app/mypage/usermodify",
    data : query,
    dataType : 'json',
    contentType : "application/json; charset=UTF-8",
    success : function(result){
        console.log(result);
       var str="";
       $.each(result,function(i,s){
          str +='<div id=inhere1 style="color:red;"></div>';
          str +='<label>'+'아이디'+'</label>'; 
          str +='<input type="text" name="modify_name" id="id" disabled value='+s[0].user_id+'></br></br> ';
          str +='<label>'+'비밀번호'+'</label>';
          str +='<input type="password" name="modify_name" id="password"></br>';
          str +='<label>'+'비밀번호 확인'+'</label>'; 
          str +='<div id="inhere2" style="color:red;"></div>';
          str +='<input type="password" name="modify_name" id="passwordconfirm"><br><br>';
          str +='<label>'+'이름'+'</label>';
          str +='<input type="text" name="name" id="modify_name" value='+s[0].name+'><br><br>';
          str +='<label>'+'이메일'+'</label>';
          str +='<input type="text" name="modify_name" id="email" disabled value='+s[0].email+'><br><br>';
          str +='<label>'+'휴대폰번호'+'</label>';
          str +='<input type="text" name="modify_name" id="phonenumber" value='+s[0].phone+'><br><br>';
          str +='<input type="button" name="confirm-modify" id="confirm-modify" class="confirm-modify" value="확인">';

           $(".usermodify-card").html(str);
       });
    }
});

//내정보 수정 버튼 클릭시
$('.confirm-modify').click(function() {

    var value = $('input[name=modify_name]').val();

    var id = $("#id").val();
    var password = $("#password").val();
    var passwordconfirm = $("#passwordconfirm").val();
    var modify_name = $("#modify_name").val();
    var phonenumber = $("#phonenumber").val();

    if (passwordconfirm != password) {
        document.getElementById("inhere2").innerHTML = "비밀번호확인이 일치하지 않습니다";
    } else if (passwordconfirm == password) {

        var query = {
                user_id : id,
                name : modify_name,
                password : password,
                phone : phonenumber
        }

        $.ajax({
            type : "POST",
            data : query,
            url : "http://39.127.7.58:8080/app/mypage/usermodify",
            success : function(data) {
                if (data == "success") {
                    window.location.href = "http://39.127.7.58:8080/app/mypage/mypagemain";
                }
                if (data == "false") {
                    document
                    .getElementById("inhere1").innerHTML = "수정 정보를 모두 입력해 주세요";
                }
            }
        });
    }
});

});