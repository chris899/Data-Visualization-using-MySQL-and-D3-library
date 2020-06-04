package main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScatterPlot extends Chart{
	
	private Parameters parameter;
	private FileWriter writer;
	private Statement statement1;
	private Statement statement2;
	
	public ScatterPlot(Parameters parameter) throws IOException {
		this.parameter = parameter;
		this.writer = new FileWriter("./scatterplot.csv");
		this.statement1 = null;
		this.statement2 = null;
	}
	
	public void plot() throws SQLException, IOException, URISyntaxException{
		int j = (int) Math.ceil((double)(parameter.getEndYear() - parameter.getStartYear() + 1) / parameter.getYearOrganization());
		System.out.println("j einai" + j);
		if (parameter.getYearOrganization() == 1) j = 1;
		writer.write("SepalLength,SepalWidth,PetalLength,PetalWidth,Name\n");
		statement1 = parameter.getConnection().createStatement();
		statement2 = parameter.getConnection().createStatement();
		String query1 = "";
		String query2 = "";
		for(int i = 0; i < j; i++) {
			if(j != 1) {
				int yearFrom =  parameter.getStartYear() + parameter.getYearOrganization()*i;
				int yearTo   =  yearFrom + parameter.getYearOrganization() -1;
				if(i == j-1) yearTo = parameter.getEndYear(); //  swsth teleutaia xronologia sto teleutaio perasma
				query1 = getQuery1("AVG(",") ",parameter.getIndicator1(), yearFrom, yearTo);
				query2 = getQuery1("AVG(",") ",parameter.getIndicator2(), yearFrom, yearTo);
				String yearT = String.valueOf(yearFrom) + "-" + String.valueOf(yearFrom).substring(2);
				getResults(query1, query2,yearT);
			}
			else {
				query1 = getQuery1("","",parameter.getIndicator1(), parameter.getStartYear(), parameter.getEndYear());
				query2 = getQuery1("","",parameter.getIndicator2(), parameter.getStartYear(), parameter.getEndYear());    
				getResults(query1, query2,"");	
			}

		}	
	    writer.close();	
	    ScatterHtml sp = new ScatterHtml();
	    sp.printScatterHtml(parameter.getIndicator1(), parameter.getIndicator2());
	    openHtml("scatterPlot.html");
	}
	
	private void getResults(String query1, String query2,String yearT ) throws SQLException, IOException {
		System.out.println(query1);
		System.out.println(query2);
	    ResultSet rs1 = statement1.executeQuery(query1);
	    ResultSet rs2 = statement2.executeQuery(query2);
	    while(rs1.next() && rs2.next()){
	    	System.out.println("elaaaaaa " + rs1.getString("year_percentage"));
		   	writer.write(rs1.getString("year_percentage").toString()+ ","+rs2.getString("year_percentage").toString()+","+"1.4,0.2,Gio-Melody\n");
		}
	    
	}
	
	private String getQuery1(String str1,String str2, String indicator, int yearFrom, int yearTo ) {
		String query = "SELECT "+ str1 + "year_percentage " + str2 + "AS year_percentage FROM countries,indicators " +
				 "WHERE countries.idId = indicators.id " + 
			     "AND country_name = '" + parameter.getCountry() + "' "  + "AND indicator_name = '" + indicator + "' " + 
			     "AND years>= " + yearFrom + " AND years<= " + yearTo + " ";
		return query;
	}
}
