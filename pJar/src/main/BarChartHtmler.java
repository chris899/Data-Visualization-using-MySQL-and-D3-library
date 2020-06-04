package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BarChartHtmler {

	private ArrayList<ArrayList<Contents>> indicators = new ArrayList<ArrayList<Contents>>();
	
	public BarChartHtmler(ArrayList<ArrayList<Contents>> indic) {
		this.indicators = indic;
	}
	
	public void printWr() {
		try {	
			PrintWriter	printWriter = new PrintWriter(new FileOutputStream("./barChart.html"));
			printWriter.println(" <!DOCTYPE html>\r\n" + 
					"<meta charset=\"utf-8\">\r\n" + 
					"<style>\r\n" + 
					"\r\n" + 
					"body {\r\n" + 
					"  font: 10px sans-serif;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					".axis path,\r\n" + 
					".axis line {\r\n" + 
					"  fill: none;\r\n" + 
					"  stroke: #000;\r\n" + 
					"  shape-rendering: crispEdges;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					".x.axis path {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"</style>\r\n" + 
					"<body>\r\n" + 
					"<script src=\"https://d3js.org/d3.v3.min.js\"></script>\r\n" + 
					"<script>\r\n" + 
					"\r\n" + 
					"var margin = {top: 20, right: 20, bottom: 30, left: 40},\r\n" + 
					"    width = 960 - margin.left - margin.right,\r\n" + 
					"    height = 500 - margin.top - margin.bottom;\r\n" + 
					"\r\n" + 
					"var x0 = d3.scale.ordinal()\r\n" + 
					"    .rangeRoundBands([0, width], .1);\r\n" + 
					"\r\n" + 
					"var x1 = d3.scale.ordinal();\r\n" + 
					"\r\n" + 
					"var y = d3.scale.linear()\r\n" + 
					"    .range([height, 0]);\r\n" + 
					"\r\n" + 
					"var xAxis = d3.svg.axis()\r\n" + 
					"    .scale(x0)\r\n" + 
					"    .tickSize(0)\r\n" + 
					"    .orient(\"bottom\");\r\n" + 
					"\r\n" + 
					"var yAxis = d3.svg.axis()\r\n" + 
					"    .scale(y)\r\n" + 
					"    .orient(\"left\");\r\n" + 
					"\r\n" + 
					"var color = d3.scale.ordinal()\r\n" + 
					"    .range([\"#ca0020\",\"#f4a582\",\"#d5d5d5\",\"#92c5de\",\"#0571b0\"]);\r\n" + 
					"\r\n" + 
					"var svg = d3.select('body').append(\"svg\")\r\n" + 
					"    .attr(\"width\", width + margin.left + margin.right)\r\n" + 
					"    .attr(\"height\", height + margin.top + margin.bottom)\r\n" + 
					"  .append(\"g\")\r\n" + 
					"    .attr(\"transform\", \"translate(\" + margin.left + \",\" + margin.top + \")\");\r\n" + 
					"\r\n" + 
					"d3.json(\"./data.json\", function(error, data) {\r\n" + 
					"\r\n" + 
					"  var categoriesNames = data.map(function(d) { return d.categorie; });\r\n" + 
					"  var rateNames = data[0].values.map(function(d) { return d.rate; });\r\n" + 
					"\r\n" + 
					"  x0.domain(categoriesNames);\r\n" + 
					"  x1.domain(rateNames).rangeRoundBands([0, x0.rangeBand()]);\r\n" + 
					"  y.domain([0, d3.max(data, function(categorie) { return d3.max(categorie.values, function(d) { return d.value; }); })]);\r\n" + 
					"\r\n" + 
					"  svg.append(\"g\")\r\n" + 
					"      .attr(\"class\", \"x axis\")\r\n" + 
					"      .attr(\"transform\", \"translate(0,\" + height + \")\")\r\n" + 
					"      .call(xAxis);\r\n" + 
					"\r\n" + 
					"  svg.append(\"g\")\r\n" + 
					"      .attr(\"class\", \"y axis\")\r\n" + 
					"      .style('opacity','0')\r\n" + 
					"      .call(yAxis)\r\n" + 
					"  .append(\"text\")\r\n" + 
					"      .attr(\"transform\", \"rotate(-90)\")\r\n" + 
					"      .attr(\"y\", 6)\r\n" + 
					"      .attr(\"dy\", \".71em\")\r\n" + 
					"      .style(\"text-anchor\", \"end\")\r\n" + 
					"      .style('font-weight','bold')\r\n" + 
					"      .text(\"Year percentage\");\r\n" + 
					"\r\n" + 
					"  svg.select('.y').transition().duration(500).delay(1300).style('opacity','1');\r\n" + 
					"\r\n" + 
					"  var slice = svg.selectAll(\".slice\")\r\n" + 
					"      .data(data)\r\n" + 
					"      .enter().append(\"g\")\r\n" + 
					"      .attr(\"class\", \"g\")\r\n" + 
					"      .attr(\"transform\",function(d) { return \"translate(\" + x0(d.categorie) + \",0)\"; });\r\n" + 
					"\r\n" + 
					"  slice.selectAll(\"rect\")\r\n" + 
					"      .data(function(d) { return d.values; })\r\n" + 
					"  .enter().append(\"rect\")\r\n" + 
					"      .attr(\"width\", x1.rangeBand())\r\n" + 
					"      .attr(\"x\", function(d) { return x1(d.rate); })\r\n" + 
					"      .style(\"fill\", function(d) { return color(d.rate) })\r\n" + 
					"      .attr(\"y\", function(d) { return y(0); })\r\n" + 
					"      .attr(\"height\", function(d) { return height - y(0); })\r\n" + 
					"      .on(\"mouseover\", function(d) {\r\n" + 
					"          d3.select(this).style(\"fill\", d3.rgb(color(d.rate)).darker(2));\r\n" + 
					"      })\r\n" + 
					"      .on(\"mouseout\", function(d) {\r\n" + 
					"          d3.select(this).style(\"fill\", color(d.rate));\r\n" + 
					"      });\r\n" + 
					"\r\n" + 
					"  slice.selectAll(\"rect\")\r\n" + 
					"      .transition()\r\n" + 
					"      .delay(function (d) {return Math.random()*1000;})\r\n" + 
					"      .duration(1000)\r\n" + 
					"      .attr(\"y\", function(d) { return y(d.value); })\r\n" + 
					"      .attr(\"height\", function(d) { return height - y(d.value); });\r\n" + 
					"\r\n" + 
					"  //Legend\r\n" + 
					"  var legend = svg.selectAll(\".legend\")\r\n" + 
					"      .data(data[0].values.map(function(d) { return d.rate; }).reverse())\r\n" + 
					"  .enter().append(\"g\")\r\n" + 
					"      .attr(\"class\", \"legend\")\r\n" + 
					"      .attr(\"transform\", function(d,i) { return \"translate(0,\" + i * 20 + \")\"; })\r\n" + 
					"      .style(\"opacity\",\"0\");\r\n" + 
					"\r\n" + 
					"  legend.append(\"rect\")\r\n" + 
					"      .attr(\"x\", width - 18)\r\n" + 
					"      .attr(\"width\", 18)\r\n" + 
					"      .attr(\"height\", 18)\r\n" + 
					"      .style(\"fill\", function(d) { return color(d); });\r\n" + 
					"\r\n" + 
					"  legend.append(\"text\")\r\n" + 
					"      .attr(\"x\", width - 24)\r\n" + 
					"      .attr(\"y\", 9)\r\n" + 
					"      .attr(\"dy\", \".35em\")\r\n" + 
					"      .style(\"text-anchor\", \"end\")\r\n" + 
					"      .text(function(d) {return d; });\r\n" + 
					"\r\n" + 
					"  legend.transition().duration(500).delay(function(d,i){ return 1300 + 100 * i; }).style(\"opacity\",\"1\");\r\n" + 
					"\r\n" + 
					"});\r\n" + 
					"\r\n" + 
					"</script>\r\n" + 
					"");
			printWriter.close();
			
			PrintWriter	dataJsonFile = new PrintWriter(new FileOutputStream("./data.json"));
			dataJsonFile.println("[\r\n");
			
			int switcher = indicators.size();
			int arlistIndex = -1;
			
			for(ArrayList<Contents> fc: indicators) {
				int endpoint = 0;
				arlistIndex++;
				for(Contents cs : fc) {
					
					
					dataJsonFile.println(
							"    {\r\n" + 
							"        \"categorie\": \""+cs.getYear1()+"\", \r\n" + 
							"        \"values\": [\r\n" );
					
					for(int j = 0; j<switcher; j++) {
						dataJsonFile.println(
								"            {\r\n" + 
								"                \"value\": "+indicators.get(j).get(endpoint).getPososto()+", \r\n" + 
								"                \"rate\": \""+indicators.get(j).get(endpoint).getIndicatorName()+"\"\r\n" ); 
						if(j==switcher-1) {
							dataJsonFile.println(	"            } \r\n");
						}else {
							dataJsonFile.println(	"            }, \r\n");
						}
					}
					dataJsonFile.println("        ]\r\n");
					
					if(endpoint == fc.size()-1 ) {
						dataJsonFile.println(	"            } \r\n");
					/*	dataJsonFile.println(	
							"            {\r\n" + 
							"                \"value\": 4, \r\n" + 
							"                \"rate\": \"IndicatorName3\"\r\n" + 
							"            }");*/
						
						
					}else {
						
						dataJsonFile.println(	"            }, \r\n");
					}
							
					
					
			
				/*	if(endpoint==contents.size()-1) {
						dataJsonFile.println("    } \r\n"); 
					}else {
						dataJsonFile.println("    }, \r\n"); 
					}
					*/
					endpoint++;
			
				}
				break;
			}
			
			
			dataJsonFile.println("]");
			
			dataJsonFile.close();
			
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
     }
	}
}