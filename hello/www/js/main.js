$(document).ready(function() {
  

  function showLocation(position){
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
  
  
        var query = {
          xlatitude : position.coords.latitude,
          xlongitude : position.coords.longitude
          }
          var quer = JSON.stringify(query);
  
            naver.maps.Service.reverseGeocode({
            coords: new naver.maps.LatLng(latitude, longitude),
        }, function(status, response) {
            if (status !== naver.maps.Service.Status.OK) {
                return alert('Something wrong!');
            }
    
            var result = response.v2, // 검색 결과의 컨테이너
                items = result.results; // 검색 결과의 배열
              var tes =items[0].region;
              
              var str3="";
               $.each(tes,function(i,a){
                  
                  if(i != "area0") {
                    str3 = str3 + tes[i].name + " ";
                  }
               })
               console.log(str3);
               $("#mylocation").html("현재 위치 : "+str3);
            // do Something
  
            $.ajax({
              type : "POST",
              url : "http://39.127.7.58:8080/app/appmain",
              data : quer,
              contentType : "application/json; charset=UTF-8",
              success : function(list){
                console.log(list);
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
                    str +='<td>'+Math.round(s.z/1000)+'km'+'</td>';
                    str +='</tr>';
       
                     $("#aaa").html(str);
                 });
              
              } // success 함수 종료
       
        }); // ajax함수
        }); // response
  
       
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
     var id;
     if(localStorage.getItem("auto") == "true") {
        id = localStorage.getItem("id");
     } else if( localStorage.getItem("auto") == "false"){
        id = sessionStorage.getItem("id");
     }

     var query = {
        token : to,
        id : id
     }
  
     $.ajax({
         type : "POST",
         url : "http://39.127.7.58:8080/app/send/AppTokenSave.do",
         data : JSON.stringify(query),
         contentType : 'application/json',
         success : function(){
           }
        });
     });
  });