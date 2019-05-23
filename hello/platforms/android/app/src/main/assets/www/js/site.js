var a;
var x;
var y;
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

         $("#confirm").click(function(){

            window.location.href="writecheck.html?sid="+site_id;
        
        });

      $.ajax({
            type : "POST",
            url : "http://52.79.242.145:8080/app/sitemain",
            data : site_id,
            async:false,
            contentType : "application/json; charset=UTF-8",
            success : function(result01){
                a = result01;
// var sa = a.site[0]['address'];
var sa = a[0].address;            
//var sitemainname = a.site[0]['site_name'];
var sitemainname = a[0].site_name;

var str="";
str +='<p style="margin-top: -7px;">'+ sitemainname + '</p>';
$("#sn").html(str);
    getData(sa);
            } // success 함수 종료
      }); // ajax함수 종료

      $.ajax({
        type : "POST",
        url : "http://52.79.242.145:8080/app/sitemainsensor",
        data : site_id,
        async:false,
        contentType : "application/json; charset=UTF-8",
        success : function(result){
           
          
           if(result.name == ''){
                    var str12="";
                    str12 +='<p>'+ '데이터없음' + '</p>';
                    $("#chartdiv").html(str12);
           }else{
       //////////////////////////////////////////////////////////////////////////////////
				/* 									그래프 그리기 시작									*/
				// Themes begin
				
				//////////////////////////////////////////////////////////////////////////////////
				/* 									그래프 그리기 시작									*/
				// Themes begin
				am4core.useTheme(am4themes_animated);
				// Themes end
				
				// Create chart
				var chart = am4core.create("chartdiv", am4charts.XYChart);
					
				var data = [];
				var color = ["#890E0E","#850C8E","#26BC9E","#ACFA58","#58FAD0","#81BEF7","#5858FA","#FA58F4","#2E2E2E"];
				var colorNum = 0;
				var price = new Array(result.name.length-1);  //초기값
		/* 		for(var i = 0; i < price.length; i++){
					price[i] = 0;
				} */
				var quantity = 1000; //정밀도??
				
				///////////////////////////////////////////////////////////////
				/*					데이터 불러올곳(db값 넣을곳)						*/
				
				 for(var i = 0; i < result.name.length; i++){
								
					for(var j = 0; j < result.graph.length; j++){
						if(result.name[i].sensor_name == result.graph[j].sensor_name){
							data.push({ [result.name[i].sensor_name] : result.graph[j].cur_date, [result.name[i].sensor_name+"_data"] : result.graph[j].sensing_data});
						}	
					}
					
					
				}
				
					//////////////////////////////////////////////////////////////
					
					chart.data = data; // 값들을 차트에 그리기 위해 data 배열에 json으로 넣는 부분
					
					///////////////////////////////////////////////////////////////
					/*					           축 관련 설정들 	 						*/
					var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
					dateAxis.renderer.grid.template.location = 100;  //x축 간격? 줄 생기는 밀집도 같은거
					dateAxis.renderer.labels.template.fill = am4core.color("#000000");  //x축 글씨 색깔
		
					var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
					valueAxis.tooltip.disabled = true;
					valueAxis.renderer.labels.template.fill = am4core.color("#000000");  //y축 글씩 색깔
					valueAxis.renderer.minWidth = 40; // 표 크기같은데 숫자 키우면 표 작아짐
			
					/////////////////////////////////////////////////////////////
					
					///////////////////////////////////////////////////////////////
					/*					그래프 그리는곳								*/
	 				for(var i = 0; i < result.name.length; i++){
	 					
						var series = chart.series.push(new am4charts.LineSeries());
						series.name = result.name[i].sensor_name;	//별의미없는듯 그냥 이름
						series.dataFields.dateX = result.name[i].sensor_name;	//data배열안에 json 검색하는거인듯
						series.dataFields.valueY = result.name[i].sensor_name+"_data"; 
						series.name = result.name[i].sensor_name;	//센서 타입 그 각도기 이런거 적히는 곳
						series.tooltipText = result.name[i].sensor_name+" : {valueY.value}";	//뭔지 잘모르겠음
						//series.fill = am4core.color("#FF0000");
						if(colorNum > color.length){
							colorNum = 0;
						}
						series.stroke = am4core.color(color[colorNum]);
						series.strokeWidth = 1.3;
				
						
						
						colorNum++;
	 				}
				
				
				
				chart.cursor = new am4charts.XYCursor(); //마우스 올렸을때 데이터 나오게 하는 함수
				chart.legend = new am4charts.Legend();	//그래프 바로밑에 그래프 각각 나오게 해주고 누를때 이벤트 발생
	
	
           }


               

				
			
        } // success 함수 종료
  }); // ajax함수 종료


  $.ajax({
    type : "POST",
    url : "http://52.79.242.145:8080/app/sitedata",
    data : site_id,
    async:false,
    contentType : "application/json; charset=UTF-8",
    success : function(data){
   
        if(data.data == ''){
            var str13="";
            str13 +='<p>'+ '데이터없음' + '</p>';
            $("#sensing-data").html(str13);
   }else{
//위에 첫 tr(데이터 이름)
var str = ""; 
str +='<tr>';
str +='<td>시간</td>';
 $.each(data.name ,function(i,s){
    str +='<td>'+s.sensor_name+'</td>';
    $("#sensing-data-head").html(str);
});
 str +='</tr>';
 $("#sensing-data-head").html(str);	
 
 //데이터들
 var str2 = "";
 var headername = new Array();
 for(var i=0; i < $("#sensing-data-head td").length; i++){
     headername[i] = $("#sensing-data-head td").eq(i).text();
    
 }
 
 var time = new Array();
 for(var i=0; i < data.data.length; i++){
     time[i] = data.data[i].cur_date;
 }
 
 var single2 = time.filter( (item, idx, array) => {
        return array.indexOf( item ) === idx ;
    });
     
 
 
 
 for(var i=0; i < single2.length; i++){
     str2 +='<tr>';
     str2 +='<td>'+single2[i]+'</td>';
     
     for(var j=0; j < data.data.length; j++) {
         if ( single2[i] === data.data[j].cur_date){
             str2 += '<td>'+data.data[j].sensing_data+'</td>'
         }
     }

     str2 +='</tr>';
      $("#sensing-data").html(str2);	
 }


   }
	

    } // success 함수 종료
}); // ajax함수 종료






$.ajax({
            type : "POST",
            url : "http://52.79.242.145:8080/app/siterepairlist",
            data : site_id,
            async : false,
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

      

function getData(sa){
        result =  naver.maps.Service.geocode({
            query: sa
        },  function(status, response) {
            if (status !== naver.maps.Service.Status.OK) {
                return alert('Something wrong!');
            }
            
            var resultmap = response.v2, // 검색 결과의 컨테이너
                items = resultmap.addresses; // 검색 결과의 배열
                x = eval(items[0].x);
                y = eval(items[0].y);
                var point = new naver.maps.Point(x,y);     
                

                 drawMap(x,y,sa);
            });
            
}
       
function drawMap(x,y,sa){
        
       // do Something
     
        var HOME_PATH = window.HOME_PATH || '.';
        var cityhall = new naver.maps.LatLng(y,x),

 map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(y,x),
    zoom: 10
}),

marker = new naver.maps.Marker({
    map: map,
    position: cityhall
});

var contentString = [
    '<div class="iw_inner">',
    '   <h3>'+sa+'</h3>',
    '</div>'
].join('');

var infowindow = new naver.maps.InfoWindow({
    content: contentString,
    anchorSkew: true
});

naver.maps.Event.addListener(marker, "click", function(e) {
    if (infowindow.getMap()) {
        infowindow.close();
    } else {
        infowindow.open(map, marker);
    }
});

infowindow.open(map, marker);
}

//센서패널
$(document).on("click", "#spanel" , function(){
    $.ajax({
    type : "POST",
    url : "http://52.79.242.145:8080/app/installsensor",
    data: site_id,
    contentType : "application/json; charset=UTF-8",
    success : function(data){
        console.log(data);
        var str = ""; 
        $.each(data,function(i,s){
            str +='<p>'+s.sensor_name+'</p>';

           
        });
        $("#sensorlist ul").html(str);
    } // success 함수 종료

}); // ajax함수
}); // click