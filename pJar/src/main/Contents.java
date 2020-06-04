package main;

public class Contents {
	
	private String indicatorName;
	private String year1;
	private int year;
	private double pososto;
	
	public Contents(String indicatorName, int year, double pososto) {
		this.year = year;
		this.indicatorName = indicatorName;
		this.pososto = pososto;
	}
	
	public Contents(String indicatorName, String year1, double pososto) {
		this.year1 = year1;
		this.indicatorName = indicatorName;
		this.pososto = pososto;
	}
	
	public String getIndicatorName() {
		
		return this.indicatorName;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public double getPososto() {
		return this.pososto;
	}
	
	public String getYear1() {
		return year1;
	}

	public void setYear1(String year1) {
		this.year1 = year1;
	}
	
	
}