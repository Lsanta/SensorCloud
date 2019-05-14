var a;
function getQueryStringObject() {
          var a = window.location.search.substr(1).split('&');
                if (a == "") return { };
             var b = {};
             for (var i = 0; i < a.length; ++i) {
                     var p = a[i].split('=', 2);
                 if (p.length == 1)
                        b[p[0]] = "";
                 else
                     b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
             }
       return b;
      }

         var qs = getQueryStringObject();
         var site_id = qs.sid;


      $.ajax({
            type : "POST",
            url : "http://39.127.7.58:8080/app/sitemain",
            data : site_id,
            async:false,
            contentType : "application/json; charset=UTF-8",
            success : function(result01){
    a = result01;
            } // success 함수 종료
      }); // ajax함수 종료
var sa = a[0]['address'];
var sitemainname = a[0]['site_name'];

var str="";
str +='<p style="margin-top: -7px;">'+ sitemainname + '</p>';
$("#sn").html(str);


$.ajax({
            type : "POST",
            url : "http://39.127.7.58:8080/app/siterepairlist",
            data : site_id,
            async:false,
            contentType : "application/json; charset=UTF-8",
            success : function(result02){
           var str1="";
               $.each(result02,function(i,q){


                  str1 +='<tr>';
                  str1 +='<td>'+q.title+'</td>';
                  switch(q.board_status){
                  case 0 : str1 +='<td>'+ 'open' +'</td>'; break;
                  case 1 : str1 +='<td>'+ 'fixed' +'</td>'; break;
                  case 2 : str1 +='<td>'+ 'closed' +'</td>'; break;
                  default  : str1 +='<td>'+'null'+'</td>'; break;
                  }
                  str1 +='<td>'+q.name+'</td>';
                  str1 +='<td>'+q.reg_date+'</td>';
                str1 +='<td style="display : none">'+q.site_id+'</td>';
                  str1 +='</tr>';

                   $("#bbb").html(str1);
});
            } // success 함수 종료
      }); // ajax함수 종료

    //function get_pointer (해당주소,대상지도 id,title) {
    function get_pointer (adress,getid,title) {
      naver.maps.Service.geocode({
            address: adress
        }, function(status, response) {
            if (status !== naver.maps.Service.Status.OK) {
                //return alert('Something wrong!');
                console.log('주소에러');

            }
            var result = response.result, // 검색 결과의 컨테이너

                items = result.items; // 검색 결과의 배열

              // do Something

            var x = eval(items[0].point.x);

            var y = eval(items[0].point.y);


            var HOME_PATH = window.HOME_PATH || '.';


            var cityhall = new naver.maps.LatLng(y, x),

                map = new naver.maps.Map('map', {

                    center: cityhall.destinationPoint(0, 500),

                    zoom: 10

                }),

                marker = new naver.maps.Marker({

                    map: map,

                    position: cityhall

                });


            var contentString = [

                '<div class="iw_inner">',

                '   <h6>'+title+'</h6>',

                '   <p style="display:none;">'+adress+'</p>',

                '</div>'

            ].join('');



            var infowindow = new naver.maps.InfoWindow({

                content: contentString

            });
              naver.maps.Event.addListener(marker, "click", function(e) {
                if (infowindow.getMap()) {
                    infowindow.close();
                } else {
                    infowindow.open(map, marker);
                }
            });

              infowindow.open(map, marker);

        });

    }
    /* 마커중복 사용안됨 */

    get_pointer(sa,'map',sa);
