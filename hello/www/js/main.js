$(document).ready(function() {
   function getLocation() {
      if (navigator.geolocation) { // GPS를 지원하면
        navigator.geolocation.getCurrentPosition(function(position) {
          alert(position.coords.latitude + ' ' + position.coords.longitude);
          console.log(position);
        }, function(error) {
          console.error(error);
        }, {
          enableHighAccuracy: false,
          maximumAge: 0,
          timeout: Infinity
        });
      } else {
        alert('GPS를 지원하지 않습니다');
      }
    }
    getLocation();

    $.ajax({
       type : "POST",
       url : "http://52.79.242.145:8080/app/mmain",
       contentType : "application/json; charset=UTF-8",
       success : function(result){
       
          var str="";
          $.each(result,function(i,s){
             str +='<tr>';
             switch(s.site_status){
             case 0 : str +='<td>'+ "<img src='img/gray.svg'>" +'</td>'; break;
             case 1 : str +='<td>'+ "<img src='img/green.svg'>" +'</td>'; break;
             case 2 : str +='<td>'+ "<img src='img/red.svg'>" +'</td>'; break;
             default  : str +='<td>'+'null'+'</td>'; break;
             }
             str +='<td>'+s.site_name+'</td>';
             str +='<td>'+s.address+'</td>';
             str +='<td>'+s.start_date+'</td>';
             str +='<td style="display : none">'+s.site_id+'</td>';
             str +='</tr>';

              $("#aaa").html(str);
          });
       } // success 함수 종료

 }); // ajax함수


 
$(document).on("click", "#aaa tr" , function(){

  var tr = $("#aaa tr").index(this);
  var site_id = $("#aaa tr:eq("+tr+") td:eq(4)").text();
  window.location.href = "site.html?sid=" + site_id;
  });
});

$(document).on('deviceready', function() {
   FCMPlugin.getToken(function(token){
  
   var to = token;
   var id = sessionStorage.getItem("id");

   var query = {
      token : to,
      id : id
   }

   $.ajax({
       type : "POST",
       url : "http://52.79.242.145:8080/app/send/AppTokenSave.do",
       data : JSON.stringify(query),
       contentType : 'application/json',
       success : function(){
         }
      });
   });
});
 