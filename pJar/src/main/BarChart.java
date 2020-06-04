package main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BarChart extends Chart{
	private Parameters parameter;
	private Statement statement1;
	private ArrayList<Contents> contents = new ArrayList<Contents>();
	private ArrayList<ArrayList<Contents>> fullContents = new ArrayList<ArrayList<Contents>>();

	public BarChart(Parameters parameter) throws IOException {
		this.parameter = parameter;
		statement1 = null;
	}
	
	public void plot() throws SQLException, MalformedURLException, IOException, URISyntaxException{
		statement1 = parameter.getConnection().createStatement();
		String query = "";
	for(String obj : parameter.getCountriesIndicators()) {
		String[] str = obj.toString().split("#");
		query = getQuery(str[0],  str[1], parameter.getStartYear(), parameter.getEndYear());
		getResults(query,str[1]);
	}
	ArrayList<ArrayList<Contents>> full = getDiataksiEtwn(parameter.getYearOrganization(), fullContents, "BarChart", parameter.getEndYear());
	BarChartHtmler htmler = new BarChartHtmler(full);
	htmler.printWr();
	openHtml("barChart.html");
	}
	
	private void getResults(String query, String indicator) throws NumberFormatException, SQLException {
		ResultSet rs = statement1.executeQuery(query);
		contents = new  ArrayList<Contents>();
		while(rs.next()) {
			contents.add(new Contents(indicator, rs.getString("years"), Double.parseDouble(rs.getString("year_percentage").toString()) ));
		}
		fullContents.add(contents);
	}

}