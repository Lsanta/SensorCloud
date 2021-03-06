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
               $(".line-b").show();
               $("#mylocation").html(str3);
            //do Something
  
            $.ajax({
              type : "POST",
              url : "http://183.106.6.74:8080/app/appmain", 
              //url : "http://39.127.7.58:8080/app/appmain",
              data : quer,
              contentType : "application/json; charset=UTF-8",
              success : function(list){
                console.log(list);
                 var str="";
                 $.each(list,function(i,s){
                  str +='<div class="sitelist">';
                  str +='<h3>'+s.site_name+'</h3>';
                  switch(s.site_status){
                     case 0 : str +='<td>'+ "<img class='status' src='img/gray.png'>" +'</td>'; break;
                     case 1 : str +='<td>'+ "<img class='status' src='img/green.png'>" +'</td>'; break;
                     case 2 : str +='<td>'+ "<img class='status' src='img/red.svg'>" +'</td>'; break;
                     default  : str +='<td>'+'null'+'</td>'; break;
                     }
                  str +='<p style="display : none">'+s.site_id+'</p>';
                  str +='<p>'+s.address+'</p>';
                  str +='<h5>'+Math.round(s.z/1000)+'km'+'</h5>';
                  str +='</div>';
                     str +='<tr>';
                    
                  //   str +='<td>'+s.site_name+'</td>';
                  //   //str +='<td>'+s.address+'</td>';
                  //   // str +='<td>'+s.start_date+'</td>';
                  //   str +='<td style="display : none">'+s.site_id+'</td>';
                  //   str +='<td>'+Math.round(s.z/1000)+'km'+'</td>';
                  //   str +='</tr>';
                     
                     $("#site").html(str);
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
    
  
    
  
  $(document).on("click", ".sitelist" , function(){
  
   if(window.localStorage.getItem("level") == 1) {
      alert("2등급부터 볼 수 있는 페이지입니다. 승급요청을 해주세요." )
      location.reload();
      return false;
   }

   //  var tr = $(".sitelist").index(this);
    var site_id =  $(this).children().eq(2).text();

   //  var site_id = $(".sitelist:eq("+tr+") td:eq(0)").text();
   //  alert(site_id);
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
         url : "http://183.106.6.74:8080/app/send/AppTokenSave.do",
         //url : "http://39.127.7.58:8080/app/send/AppTokenSave.do",
         data : JSON.stringify(query),
         contentType : 'application/json',
         success : function(){
           }
        });
     });
  });