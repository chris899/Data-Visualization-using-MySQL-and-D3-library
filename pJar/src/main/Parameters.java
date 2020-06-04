package main;

import java.sql.Connection;
import java.util.ArrayList;

public class Parameters {
	
	private int startYear;
	private int endYear;
	private int yearOrganization;
	private String indicator1;
	private String indicator2;
	private String country;
	private Connection connection;
	private ArrayList<String> countriesIndicators = new ArrayList<String>();
	
	public ArrayList<String> getCountriesIndicators() {
		return countriesIndicators;
	}
	
	public void setCountriesIndicators(ArrayList<String> countriesIndicators) {
		this.countriesIndicators = countriesIndicators;
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Parameters() {
		
	}
	public String getIndicator1() {
		return indicator1;
	}

	public void setIndicator1(String indicator1) {
		this.indicator1 = indicator1;
	}

	public String getIndicator2() {
		return indicator2;
	}

	public void setIndicator2(String indicator2) {
		this.indicator2 = indicator2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	public int getStartYear() {
		return startYear;
	}


	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}


	public int getEndYear() {
		return endYear;
	}


	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}


	public int getYearOrganization() {
		return yearOrganization;
	}


	public void setYearOrganization(int yearOrganization) {
		this.yearOrganization = yearOrganization;
	}	
}
