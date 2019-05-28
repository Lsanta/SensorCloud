$(document).ready(function() {
   function getLocation() {
      if (navigator.geolocation) { // GPS를 지원하면
        navigator.geolocation.getCurrentPosition(function(position) {
          console.log(position.coords.latitude);
          console.log(position.coords.longitude);
          var query = {
            xlatitude : position.coords.latitude,
            xlongitude : position.coords.longitude
            }
            var quer = JSON.stringify(query);

          $.ajax({
            type : "POST",
            // url : "http://52.79.242.145:8080/app/appmain",
            url : "http://39.127.7.58:8080/app/appmain",
            data : quer,
            contentType : "application/json; charset=UTF-8",
            success : function(list){
               var str="";
               $.each(list,function(i,s){
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
function showLocation(position){
          var query = {
            xlatitude : position.coords.latitude,
            xlongitude : position.coords.longitude
            }
            var quer = JSON.stringify(query);

          $.ajax({
            type : "POST",
            //url : "http://52.79.242.145:8080/app/appmain",
            url : "http://39.127.7.58:8080/app/appmain",
            data : quer,
            contentType : "application/json; charset=UTF-8",
            success : function(list){
               var str="";
               $.each(list,function(i,s){
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
}

function errorHandler(){
  alert('GPS를 지원하지 않습니다');
}

function getLocation(){
  if(navigator.geolocation){
    var options = {
      enableHighAccuracy: true, 
      maximumAge        : 30000, 
      timeout           : 27000
    };
    geoLoc = navigator.geolocation;
    watchID = geoLoc.watchPosition(showLocation, errorHandler, options);
  }else{
    alert("Sorry, browser does not support geolocation!");

  }
}

getLocation();

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
      //  url : "http://52.79.242.145:8080/app/send/AppTokenSave.do",
       url : "http://39.127.7.58:8080/app/send/AppTokenSave.do",
       data : JSON.stringify(query),
       contentType : 'application/json',
       success : function(){
         }
      });
   });
});