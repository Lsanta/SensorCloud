// Themes begin
am4core.useTheme(am4themes_animated);
// Themes end

// Create chart
var chart = am4core.create("chartdiv", am4charts.XYChart);

var data = [];
var price1 = 500, price2 = 1200;  //초기값
var quantity = 100000; //정밀도??

///////////////////////////////////////////////////////////////
/*					데이터 불러올곳(db값 넣을곳)						*/

for (var i = 0; i < 360; i++) {
  price1 += Math.round((Math.random() < 0.5 ? 1 : -1) * Math.random() * 100);
  data.push({ date1: new Date(2015, 0, i), price1: price1 });
}
for (var i = 0; i < 360; i++) {
  price2 += Math.round((Math.random() < 0.5 ? 1 : -1) * Math.random() * 100);
  data.push({ date2: new Date(2015, 0, i), price2: price2 });
}

//////////////////////////////////////////////////////////////

chart.data = data; // 값들을 차트에 그리기 위해 data 배열에 json으로 넣는 부분

///////////////////////////////////////////////////////////////
/*					           축 관련 설정들 	 						*/
var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
dateAxis.renderer.grid.template.location = 10;  //x축 간격? 줄 생기는 밀집도 같은거
dateAxis.renderer.labels.template.fill = am4core.color("#000000");  //x축 글씨 색깔

var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
valueAxis.tooltip.disabled = true;
valueAxis.renderer.labels.template.fill = am4core.color("#000000");  //y축 글씩 색깔
valueAxis.renderer.minWidth = 40; // 표 크기같은데 숫자 키우면 표 작아짐

/////////////////////////////////////////////////////////////#7401DF


///////////////////////////////////////////////////////////////
/*					그래프 그리는곳								*/

var series = chart.series.push(new am4charts.LineSeries());
series.name = "2015";	//별의미없는듯 그냥 이름
series.dataFields.dateX = "date1";	//data배열안에 json 검색하는거인듯
series.dataFields.valueY = "price1";
series.name = "각도기1";	//센서 타입 그 각도기 이런거 적히는 곳
series.tooltipText = "{valueY.value}";	//뭔지 잘모르겠음
series.fill = am4core.color("#0101DF");
series.stroke = am4core.color("#0101DF");
//series.strokeWidth = 3;

var series2 = chart.series.push(new am4charts.LineSeries());
series2.name = "2015";
series2.dataFields.dateX = "date2";
series2.dataFields.valueY = "price2";
series2.name = "각도기2";
series2.yAxis = valueAxis;
series2.xAxis = dateAxis;
series2.tooltipText = "{valueY.value}";
series2.fill = am4core.color("#0101DF");
series2.stroke = am4core.color("#0101DF");

//////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////
/*							범위 설정하는곳						*/

var range = valueAxis.createSeriesRange(series);
range.value = 1000;		//하한값
range.endValue = 6000;	//상한값
range.contents.stroke = am4core.color("#FF0000");	//정상범위일때 나오는 색깔
range.contents.fill = range.contents.stroke;

var range2 = valueAxis.createSeriesRange(series2);
range2.value = 1000;
range2.endValue = 6000;
range2.contents.stroke = am4core.color("#FF0000");
range2.contents.fill = range.contents.stroke;

//////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////
/*						그래프 위에 스크롤바						*/

var scrollbarX = new am4charts.XYChartScrollbar();
scrollbarX.series.push(series);
scrollbarX.series.push(series2);
chart.scrollbarX = scrollbarX;

//////////////////////////////////////////////////////////////

chart.cursor = new am4charts.XYCursor(); //마우스 올렸을때 데이터 나오게 하는 함수
chart.legend = new am4charts.Legend();	//그래프 바로밑에 그래프 각각 나오게 해주고 누를때 이벤트 발생