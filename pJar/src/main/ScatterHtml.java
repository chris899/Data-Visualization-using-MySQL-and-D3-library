package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ScatterHtml {
	
	public ScatterHtml() {
		
	}
	
	public void printScatterHtml(String indicator1 , String indicator2) throws FileNotFoundException {
		PrintWriter	printWriter = new PrintWriter(new FileOutputStream("./scatterPlot.html"));
		printWriter.println("<!DOCTYPE html>\n" + 
				"<html lang=\"en\">\n" + 
				"<head>\n" + 
				"	<meta charset=\"UTF-8\">\n" + 
				"	<title>Scatterplot d3v4</title>\n" + 
				"	<style type=\"text/css\">\n" + 
				"		body{\n" + 
				"			margin: 0;\n" + 
				"			font-family: arial, sans;\n" + 
				"		}\n" + 
				"\n" + 
				"		.label{\n" + 
				"			font-size: 11px;\n" + 
				"		}\n" + 
				"\n" + 
				"		.legend text,\n" + 
				"		.axis text {\n" + 
				"			font-size: 13px;\n" + 
				"			fill: #333;\n" + 
				"		}\n" + 
				"\n" + 
				"		.axis path,\n" + 
				"		.axis line{\n" + 
				"			fill: none;\n" + 
				"			stroke-width: 1px;\n" + 
				"			stroke: #777;\n" + 
				"		}\n" + 
				"\n" + 
				"		.circle{\n" + 
				"			fill-opacity: 0.65;\n" + 
				"		}\n" + 
				"\n" + 
				"		.bubble{\n" + 
				"			opacity: 1;\n" + 
				"			transition: opacity 0.3s;\n" + 
				"		}\n" + 
				"\n" + 
				"		.bubble:hover text{\n" + 
				"			opacity: 1;\n" + 
				"		}\n" + 
				"\n" + 
				"		.bubble:hover circle{\n" + 
				"			fill-opacity: 1;\n" + 
				"		}\n" + 
				"\n" + 
				"		.legend rect{\n" + 
				"			fill-opacity: 0.75;\n" + 
				"		}\n" + 
				"\n" + 
				"		.legeng:hover rect{\n" + 
				"			fill-opacity:1;\n" + 
				"		}\n" + 
				"	</style>\n" + 
				"	<script type=\"text/javascript\" src=\"https://d3js.org/d3.v4.min.js\"></script>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"<script type=\"text/javascript\">\n" + 
				"	var margin = {top: 30, right: 50, bottom: 40, left:90};\n" + 
				"	var width = 980 - margin.left - margin.right;\n" + 
				"	var height = 500 - margin.top - margin.bottom;\n" + 
				"\n" + 
				"	var svg = d3.select('body')\n" + 
				"		.append('svg')\n" + 
				"		.attr('width', width + margin.left + margin.right )\n" + 
				"		.attr('height', height + margin.top + margin.bottom + 50)\n" + 
				"	.append('g')\n" + 
				"		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');\n" + 
				"\n" + 
				"\n" + 
				"	// The API for scales have changed in v4. There is a separate module d3-scale which can be used instead. The main change here is instead of d3.scale.linear, we have d3.scaleLinear.\n" + 
				"	var xScale = d3.scaleLinear()\n" + 
				"		.range([0, width]);\n" + 
				"\n" + 
				"	var yScale = d3.scaleLinear()\n" + 
				"		.range([height, 0]);\n" + 
				"\n" + 
				"	// square root scale.\n" + 
				"	var radius = d3.scaleSqrt()\n" + 
				"		.range([3,5]);\n" + 
				"\n" + 
				"	// the axes are much cleaner and easier now. No need to rotate and orient the axis, just call axisBottom, axisLeft etc.\n" + 
				"	var xAxis = d3.axisBottom()\n" + 
				"		.scale(xScale);\n" + 
				"\n" + 
				"	var yAxis = d3.axisLeft()\n" + 
				"		.scale(yScale);\n" + 
				"\n" + 
				"	// again scaleOrdinal\n" + 
				"	var color = d3.scaleOrdinal(d3.schemeCategory20);\n" + 
				"\n" + 
				"	d3.csv('scatterplot.csv', function(error, data){\n" + 
				"		data.forEach(function(d){\n" + 
				"			 d.SepalLength = +d.SepalLength;\n" + 
				"			 d.SepalWidth = +d.SepalWidth;\n" + 
				"			 d.PetalLength = +d.PetalLength;\n" + 
				"			 d.Species = d.Name;\n" + 
				"		});\n" + 
				"\n" + 
				"		xScale.domain(d3.extent(data, function(d){\n" + 
				"			return d.SepalLength;\n" + 
				"		})).nice();\n" + 
				"\n" + 
				"		yScale.domain(d3.extent(data, function(d){\n" + 
				"			return d.SepalWidth;\n" + 
				"		})).nice();\n" + 
				"\n" + 
				"		radius.domain(d3.extent(data, function(d){\n" + 
				"			return d.PetalLength;\n" + 
				"		})).nice();\n" + 
				"\n" + 
				"		// adding axes is also simpler now, just translate x-axis to (0,height) and it's alread defined to be a bottom axis. \n" + 
				"		svg.append('g')\n" + 
				"			.attr('transform', 'translate(0,' + height + ')')\n" + 
				"			.attr('class', 'x axis')\n" + 
				"			.call(xAxis);\n" + 
				"\n" + 
				"		// y-axis is translated to (0,0)\n" + 
				"		svg.append('g')\n" + 
				"			.attr('transform', 'translate(0,0)')\n" + 
				"			.attr('class', 'y axis')\n" + 
				"			.call(yAxis);\n" + 
				"\n" + 
				"		var narray = JSON.stringify(data[0,0,0,0,0]).split(\":\")\n" + 
				"		var bubble = svg.selectAll('.bubble')\n" + 
				"			.data(data)\n" + 
				"			.enter().append('circle')\n" + 
				"			.attr('class', 'bubble')\n" + 
				"			.attr('cx', function(d){return xScale(d.SepalLength);})\n" + 
				"			.attr('cy', function(d){ return yScale(d.SepalWidth); })\n" + 
				"			.attr('r', function(d){ return radius(d.PetalLength); })\n" + 
				"			.style('fill', function(d){ return color(d.Species); })\n" + 
				"			.on('mouseover', function () {\n" + 
				"				d3.select(this)\n" + 
				"				.transition()\n" + 
				"				.duration(500)\n" + 
				"				.attr('r',20)\n" + 
				"				.attr('stroke-width',3)\n" + 
				"			})\n" + 
				"			.on('mouseout', function () {\n" + 
				"				d3.select(this)\n" + 
				"				.transition()\n" + 
				"				.duration(500)\n" + 
				"				.attr('r',5)\n" + 
				"				.attr('stroke-width',1)\n" + 
				"			});\n" + 
				"		bubble.append('title')\n" + 
				"			.attr('x', function(d){ return radius(d.PetalLength); })\n" + 
				"			.text(function(d){\n" + 
				"				return  d.SepalLength + \"\\n\" + d.SepalWidth;\n" + 
				"			});\n" + 
				"		\n" + 
				"		// adding label. For x-axis, it's at (10, 10), and for y-axis at (width, height-10).\n" + 
				"		svg.append('text')\n" + 
				"			.attr('x', -400)\n" + 
				"			.attr('y', -40)\n" + 
				"			.attr(\"transform\", \"rotate(-90)\")\n" + 
				"			.attr('class', 'label')\n" + 
				"			.text( '" + indicator1 + "');\n\n" + "svg.append('text')\n" + 
						"			.attr('x', width - 200)\n" + 
						"			.attr('y', height + 50)\n" + 
						"			.attr('text-anchor', 'end')\n" + 
						"			.attr('class', 'label')\n" + 
						"			.text('" + indicator2 + "');\n\n" + "var legend = svg.selectAll('legend')\n" + 
								"			.data(color.domain())\n" + 
								"			.enter().append('g')\n" + 
								"			.attr('class', 'legend')\n" + 
								"			.attr('transform', function(d,i){ return 'translate(-250,' + i * 20 + ')'; });\n" + 
								"\n" + 
								"		// give x value equal to the legend elements. \n" + 
								"		// no need to define a function for fill, this is automatically fill by color.\n" + 
								"		/*legend.append('rect')\n" + 
								"			.attr('x', width)\n" + 
								"			.attr('width', 18)\n" + 
								"			.attr('height', 18)\n" + 
								"			.style('fill', color);\n" + 
								"\n" + 
								"		// add text to the legend elements.\n" + 
								"		// rects are defined at x value equal to width, we define text at width - 6, this will print name of the legends before the rects.\n" + 
								"		legend.append('text')\n" + 
								"			.attr('x', width - 6)\n" + 
								"			.attr('y', 9)\n" + 
								"			.attr('dy', '.35em')\n" + 
								"			.style('text-anchor', 'end')\n" + 
								"			.text(function(d){ return d; });*/\n" + 
								"\n" + 
								"\n" + 
								"		// d3 has a filter fnction similar to filter function in JS. Here it is used to filter d3 components.\n" + 
								"		legend.on('click', function(type){\n" + 
								"			d3.selectAll('.bubble')\n" + 
								"				.style('opacity', 0.15)\n" + 
								"				.filter(function(d){\n" + 
								"					return d.Species == type;\n" + 
								"				})\n" + 
								"				.style('opacity', 1);\n" + 
								"		})\n" + 
								"\n" + 
								"\n" + 
								"	})\n" + 
								"\n" + 
								"</script>\n" + 
								"	\n" + 
								"</body>\n" + 
								"</html>");
		printWriter.close();
	}
}

