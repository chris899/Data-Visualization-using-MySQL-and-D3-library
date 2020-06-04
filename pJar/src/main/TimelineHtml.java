package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TimelineHtml {

	private ArrayList<ArrayList<Contents>> fullcontents = new ArrayList<ArrayList<Contents>>();
	
	public TimelineHtml(ArrayList<ArrayList<Contents>> fc) {
		this.fullcontents = fc;
	}
	
	public void makeLineHtml() {
		
		try {
			PrintWriter	printWriter = new PrintWriter(new FileOutputStream("./linechart.js"));
			printWriter.println("var data = [\r\n" );
			int endpoint = 0;
			int maxYear = 0;
			int yearMaxPointer=0;
			int posostoMaxPointer = 0;
			double maxPososto = 0;
			for(ArrayList<Contents> con: fullcontents ) {
				printWriter.println("    {\r\n" + 
						"      name: \""+con.get(0).getIndicatorName()+"\",\r\n" + 
						"      values: [" );
				int contentsEndpoint = 0;
				
				for(Contents c : con) {
					
					if(c.getPososto()>maxPososto) {
						maxPososto = c.getPososto();
						posostoMaxPointer = endpoint;
					}
					if(c.getYear()>maxYear) {
						maxYear = c.getYear();
						yearMaxPointer = endpoint;
					}
					//printWriter.println("        {date: \""+ c.getYear() +"\", price: \""+ c.getPososto() +"\"},\r\n");
					if(contentsEndpoint == con.size()-1) {
						printWriter.println("     {date: \""+ c.getYear() +"\", price: \""+ c.getPososto() +"\"}");
					}else {
						printWriter.println("        {date: \""+ c.getYear() +"\", price: \""+ c.getPososto() +"\"},");
					}
					contentsEndpoint ++;
				}
				
				if(endpoint == fullcontents.size()-1) {
					printWriter.println(    
							"      ]\r\n" + 
							"    }\r\n"); 
				}else {
					printWriter.println(    
							"      ]\r\n" + 
							"    },\r\n"); 
				}
				endpoint ++;
			}
							
					
			printWriter.println( 	"  ];\r\n" + 
					"  \r\n" + 
					"  var width = 800;\r\n" + 
					"  var height = 500;\r\n" + 
					"  var margin = 50;\r\n" + 
					"  var duration = 250;\r\n" + 
					"  \r\n" + 
					"  var lineOpacity = \"0.50\";\r\n" + 
					"  var lineOpacityHover = \"0.85\";\r\n" + 
					"  var otherLinesOpacityHover = \"0.1\";\r\n" + 
					"  var lineStroke = \"2.5px\";\r\n" + 
					"  var lineStrokeHover = \"2.5px\";\r\n" + 
					"  \r\n" + 
					"  var circleOpacity = '0.85';\r\n" + 
					"  var circleOpacityOnLineHover = \"0.25\"\r\n" + 
					"  var circleRadius = 3;\r\n" + 
					"  var circleRadiusHover = 6;\r\n" + 
					"  \r\n" + 
					"  \r\n" + 
					"  /* Format Data */\r\n" + 
					"  var parseDate = d3.timeParse(\"%Y\");\r\n" + 
					"  data.forEach(function(d) { \r\n" + 
					"    d.values.forEach(function(d) {\r\n" + 
					"      d.date = parseDate(d.date);\r\n" + 
					"      d.price = +d.price;    \r\n" + 
					"    });\r\n" + 
					"  });\r\n" + 
					"  \r\n" + 
					"  \r\n" + 
					"  /* Scale */\r\n" + 
					"  var xScale = d3.scaleTime()\r\n" + 
					"    .domain(d3.extent(data["+yearMaxPointer+"].values, d => d.date))\r\n" + 
					"    .range([0, width-margin]);\r\n" + 
					"  \r\n" + 
					"  var yScale = d3.scaleLinear()\r\n" + 
					"    .domain([0, d3.max(data["+posostoMaxPointer+"].values, d => d.price)])\r\n" + 
					"    .range([height-margin, 0]);\r\n" + 
					"  \r\n" + 
					"  var color = d3.scaleOrdinal(d3.schemeCategory10);\r\n" + 
					"  \r\n" + 
					"  /* Add SVG */\r\n" + 
					"  var svg = d3.select(\"#chart\").append(\"svg\")\r\n" + 
					"    .attr(\"width\", (width+margin)+\"px\")\r\n" + 
					"    .attr(\"height\", (height+margin)+\"px\")\r\n" + 
					"    .append('g')\r\n" + 
					"    .attr(\"transform\", `translate(${margin}, ${margin})`);\r\n" + 
					"  \r\n" + 
					"  \r\n" + 
					"  /* Add line into SVG */\r\n" + 
					"  var line = d3.line()\r\n" + 
					"    .x(d => xScale(d.date))\r\n" + 
					"    .y(d => yScale(d.price));\r\n" + 
					"  \r\n" + 
					"  let lines = svg.append('g')\r\n" + 
					"    .attr('class', 'lines');\r\n" + 
					"  \r\n" + 
					"  lines.selectAll('.line-group')\r\n" + 
					"    .data(data).enter()\r\n" + 
					"    .append('g')\r\n" + 
					"    .attr('class', 'line-group')  \r\n" + 
					"    .on(\"mouseover\", function(d, i) {\r\n" + 
					"        svg.append(\"text\")\r\n" + 
					"          .attr(\"class\", \"title-text\")\r\n" + 
					"          .style(\"fill\", color(i))        \r\n" + 
					"          .text(d.name)\r\n" + 
					"          .attr(\"text-anchor\", \"middle\")\r\n" + 
					"          .attr(\"x\", (width-margin)/2)\r\n" + 
					"          .attr(\"y\", 5);\r\n" + 
					"      })\r\n" + 
					"    .on(\"mouseout\", function(d) {\r\n" + 
					"        svg.select(\".title-text\").remove();\r\n" + 
					"      })\r\n" + 
					"    .append('path')\r\n" + 
					"    .attr('class', 'line')  \r\n" + 
					"    .attr('d', d => line(d.values))\r\n" + 
					"    .style('stroke', (d, i) => color(i))\r\n" + 
					"    .style('opacity', lineOpacity)\r\n" + 
					"    .on(\"mouseover\", function(d) {\r\n" + 
					"        d3.selectAll('.line')\r\n" + 
					"                      .style('opacity', otherLinesOpacityHover);\r\n" + 
					"        d3.selectAll('.circle')\r\n" + 
					"                      .style('opacity', circleOpacityOnLineHover);\r\n" + 
					"        d3.select(this)\r\n" + 
					"          .style('opacity', lineOpacityHover)\r\n" + 
					"          .style(\"stroke-width\", lineStrokeHover)\r\n" + 
					"          .style(\"cursor\", \"pointer\");\r\n" + 
					"      })\r\n" + 
					"    .on(\"mouseout\", function(d) {\r\n" + 
					"        d3.selectAll(\".line\")\r\n" + 
					"                      .style('opacity', lineOpacity);\r\n" + 
					"        d3.selectAll('.circle')\r\n" + 
					"                      .style('opacity', circleOpacity);\r\n" + 
					"        d3.select(this)\r\n" + 
					"          .style(\"stroke-width\", lineStroke)\r\n" + 
					"          .style(\"cursor\", \"none\");\r\n" + 
					"      });\r\n" + 
					"  \r\n" + 
					"  \r\n" + 
					"  /* Add circles in the line */\r\n" + 
					"  lines.selectAll(\"circle-group\")\r\n" + 
					"    .data(data).enter()\r\n" + 
					"    .append(\"g\")\r\n" + 
					"    .style(\"fill\", (d, i) => color(i))\r\n" + 
					"    .selectAll(\"circle\")\r\n" + 
					"    .data(d => d.values).enter()\r\n" + 
					"    .append(\"g\")\r\n" + 
					"    .attr(\"class\", \"circle\")  \r\n" + 
					"    .on(\"mouseover\", function(d) {\r\n" + 
					"        d3.select(this)     \r\n" + 
					"          .style(\"cursor\", \"pointer\")\r\n" + 
					"          .append(\"text\")\r\n" + 
					"          .attr(\"class\", \"text\")\r\n" + 
					"          .text(`${d.price}`)\r\n" + 
					"          .attr(\"x\", d => xScale(d.date) + 5)\r\n" + 
					"          .attr(\"y\", d => yScale(d.price) - 10);\r\n" + 
					"      })\r\n" + 
					"    .on(\"mouseout\", function(d) {\r\n" + 
					"        d3.select(this)\r\n" + 
					"          .style(\"cursor\", \"none\")  \r\n" + 
					"          .transition()\r\n" + 
					"          .duration(duration)\r\n" + 
					"          .selectAll(\".text\").remove();\r\n" + 
					"      })\r\n" + 
					"    .append(\"circle\")\r\n" + 
					"    .attr(\"cx\", d => xScale(d.date))\r\n" + 
					"    .attr(\"cy\", d => yScale(d.price))\r\n" + 
					"    .attr(\"r\", circleRadius)\r\n" + 
					"    .style('opacity', circleOpacity)\r\n" + 
					"    .on(\"mouseover\", function(d) {\r\n" + 
					"          d3.select(this)\r\n" + 
					"            .transition()\r\n" + 
					"            .duration(duration)\r\n" + 
					"            .attr(\"r\", circleRadiusHover);\r\n" + 
					"        })\r\n" + 
					"      .on(\"mouseout\", function(d) {\r\n" + 
					"          d3.select(this) \r\n" + 
					"            .transition()\r\n" + 
					"            .duration(duration)\r\n" + 
					"            .attr(\"r\", circleRadius);  \r\n" + 
					"        });\r\n" + 
					"  \r\n" + 
					"  \r\n" + 
					"  /* Add Axis into SVG */\r\n" + 
					"  var xAxis = d3.axisBottom(xScale).ticks(5);\r\n" + 
					"  var yAxis = d3.axisLeft(yScale).ticks(5);\r\n" + 
					"  \r\n" + 
					"  svg.append(\"g\")\r\n" + 
					"    .attr(\"class\", \"x axis\")\r\n" + 
					"    .attr(\"transform\", `translate(0, ${height-margin})`)\r\n" + 
					"    .call(xAxis);\r\n" + 
					"  \r\n" + 
					"  svg.append(\"g\")\r\n" + 
					"    .attr(\"class\", \"y axis\")\r\n" + 
					"    .call(yAxis)\r\n" + 
					"    .append('text')\r\n" + 
					"    .attr(\"y\", 15)\r\n" + 
					"    .attr(\"transform\", \"rotate(-90)\")\r\n" + 
					"    .attr(\"fill\", \"#000\")\r\n" + 
					"    .text(\"Total values\");");
			
				printWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}