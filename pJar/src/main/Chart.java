package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Chart {
	
	public Chart() {
		
	}
	
	/*public void plot() {
		
	}*/
	public ArrayList<ArrayList<Contents>> getDiataksiEtwn(int etos, ArrayList<ArrayList<Contents>> indi, String chart, int endYear) {
		ArrayList<ArrayList<Contents>> diatEtwn = new ArrayList<ArrayList<Contents>>();
		ArrayList<Contents> temp = new ArrayList<Contents>();
		int year;
		String year3 = "";
		
		for(ArrayList<Contents> ccc: indi) {
			int thesiListas = etos;
			int startThesi = 0;
			temp = new ArrayList<Contents>();
			
			double mesosOros = 0;
			for(Contents c: ccc) {	
				if(thesiListas%etos==0) {
					double mO = 0;
					mO = mesosOros/(thesiListas-startThesi);
					
					if(etos!=1) {
						if(chart == "TimelinePlot") {
							year = ccc.get(startThesi).getYear();
							temp.add(new Contents(ccc.get(0).getIndicatorName(), year, mO ) );
						}else if(chart == "BarChart"){
							String year1 = ccc.get(startThesi).getYear1();
							int year2 = Integer.parseInt(year1) + etos - 1;
							if(Integer.parseInt(year1) <= endYear && endYear <= year2) {
								year3 = String.valueOf(endYear).substring(2);
								System.out.println("year ="+ year3);
							}
							else {
								year3 = String.valueOf(year2).substring(2);
							}
							temp.add(new Contents(ccc.get(0).getIndicatorName(), year1+ "-"+ year3, mO ) );

						}
					}else {
						if(chart == "TimelinePlot") {
							temp.add(new Contents(ccc.get(0).getIndicatorName(), ccc.get(startThesi).getYear(), mO ) );
						}else if(chart == "BarChart") {
							temp.add(new Contents(ccc.get(0).getIndicatorName(), ccc.get(startThesi).getYear1(), mO ) );
						}	
					}
					startThesi = thesiListas;
					mesosOros = 0;
						
				}
				mesosOros += c.getPososto();
				thesiListas++;
			}
			diatEtwn.add(temp);
		}
		
		for(ArrayList<Contents> a: diatEtwn) {
		System.out.println(a.get(0).getPososto());
		}
		
		return diatEtwn;
	}

	public String getQuery(String country, String indicator, int yearFrom, int yearTo) {
		String query = "SELECT  years,year_percentage" +
		        " FROM countries,indicators " + 
		        "WHERE countries.idId = indicators.id " +
		        "AND country_name = '" + country + "' AND indicator_name = '" + indicator + "' " +
		        "AND years>= " + yearFrom + " AND years<= " + yearTo + " ";  
		return query;
	}
	
	/*public void queryResults(String query, String str) throws SQLException {
		ResultSet rs = statement1.executeQuery(query);
		conts = new  ArrayList<Contents>();
		while(rs.next()) {
			conts.add(new Contents(str, Integer.parseInt(rs.getString("years").toString()), Double.parseDouble(rs.getString("year_percentage").toString()) ));
		}			
		fullcontents.add(conts);
	}*/
	
	
	public void openHtml(String path) throws MalformedURLException, IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URL("http://localhost:1337//" + path).toURI());	
	}
}
