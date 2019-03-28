// Themes begin
am4core.useTheme(am4themes_animated);
// Themes end

// Create chart
var chart = am4core.create("chartdiv", am4charts.XYChart);

var data = [];
var price1 = 1000, price2 = 1200;
var quantity = 30000;
for (var i = 0; i < 360; i++) {
  price1 += Math.round((Math.random() < 0.5 ? 1 : -1) * Math.random() * 100);
  data.push({ date1: new Date(2015, 0, i), price1: price1 });
}
for (var i = 0; i < 360; i++) {
  price2 += Math.round((Math.random() < 0.5 ? 1 : -1) * Math.random() * 100);
  data.push({ date2: new Date(2015, 0, i), price2: price2 });
}

chart.data = data;

var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
dateAxis.renderer.grid.template.location = 0;
dateAxis.renderer.labels.template.fill = am4core.color("#000000");

var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
valueAxis.tooltip.disabled = true;
valueAxis.renderer.labels.template.fill = am4core.color("#000000");
valueAxis.renderer.minWidth = 60;


var series = chart.series.push(new am4charts.LineSeries());
series.name = "2015";
series.dataFields.dateX = "date1";
series.dataFields.valueY = "price1";
series.name = "각도기1";
series.tooltipText = "{valueY.value}";
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

var range = valueAxis.createSeriesRange(series);
range.value = 1000;
range.endValue = 6000;
range.contents.stroke = am4core.color("#FF0000");
range.contents.fill = range.contents.stroke;

var range2 = valueAxis.createSeriesRange(series2);
range2.value = 1000;
range2.endValue = 6000;
range2.contents.stroke = am4core.color("#FF0000");
range2.contents.fill = range.contents.stroke;

var scrollbarX = new am4charts.XYChartScrollbar();
scrollbarX.series.push(series);
scrollbarX.series.push(series2);
chart.scrollbarX = scrollbarX;

chart.cursor = new am4charts.XYCursor();
chart.legend = new am4charts.Legend();