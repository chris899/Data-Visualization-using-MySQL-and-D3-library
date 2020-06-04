package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import main.BarChart;
import main.Parameters;
import main.ScatterPlot;
import main.TimelinePlot;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class Client {

	private JFrame frame;
	private JComboBox<String> cbIndicator;
	private JComboBox<String> cbYearFrom;
	private JComboBox<String> cbYearTo;
	private JComboBox<String> cbCountries;
	private JComboBox<String> cbChart;
	private JComboBox<String> cbPrezi;
	private Object indicator;
	private Object country;
	Connection conn = null;
	JScrollPane sc = new JScrollPane();
	private ArrayList<String> CountriesIndicators = new ArrayList<String>();
	private int startYear;
	private int endYear;
	private int yearOrganization;
	FileWriter wt;
	Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private String pass,user;
	public Client() throws SQLException, IOException, InterruptedException {
		File file = new File("./db_username_password.txt"); 
		  
		  
		  this.pass="";
		  this.user = "";
		  String st; 
		  try {
			 BufferedReader br = new BufferedReader(new FileReader(file)); 
			int metritis = 0;
			 while ((st = br.readLine()) != null) { 
			    if(metritis==0) {
				 user=st;
				 
			    }
			    if(metritis==1) {
					 pass=st;
				    }
			    metritis++;
		
			  }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  Process makeDatabase = Runtime.getRuntime().exec("cmd /c python ./test.py "+user+" "+pass);
		  makeDatabase.waitFor();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private void initialize() throws SQLException, IOException, InterruptedException {
		frame = new JFrame();
		connectDB();
		Runtime.getRuntime().exec("python -m http.server 1337");		
		frame.setBounds(100, 100, 800, 800);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		cbIndicator = new JComboBox<String>();
		cbIndicator.setBounds(165, 121, 292, 24);
		frame.getContentPane().add(cbIndicator);

		
		JLabel lblYearFrom = new JLabel("Select Year From:");
		lblYearFrom.setBounds(10, 224, 147, 15);
		frame.getContentPane().add(lblYearFrom);
		
		cbYearFrom = new JComboBox<String>();
		cbYearFrom.setBounds(165, 224, 292, 24);
		frame.getContentPane().add(cbYearFrom);
		loadYears(cbYearFrom);
		
		JLabel lblSelectYearTo = new JLabel("Select Year To:");
		lblSelectYearTo.setBounds(10, 272, 137, 15);
		frame.getContentPane().add(lblSelectYearTo);
		
		cbYearTo = new JComboBox<String>();
		cbYearTo.setBounds(165, 272, 292, 24);
		frame.getContentPane().add(cbYearTo);
		loadYears(cbYearTo);
		
		JLabel lblSelectCountries = new JLabel("Select Countries:");
		lblSelectCountries.setBounds(10, 75, 137, 25);
		frame.getContentPane().add(lblSelectCountries);

		
		cbCountries = new JComboBox<String>();
		loadCountries();
		cbCountries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadIndicators(cbCountries.getSelectedItem().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		cbCountries.setBounds(165, 75, 292, 24);
		frame.getContentPane().add(cbCountries);



		
		JLabel lblSelectIndicator = new JLabel("Select Indicators:");
		lblSelectIndicator.setBounds(10, 121, 125, 15);
		frame.getContentPane().add(lblSelectIndicator);
		
		JLabel lblSelectChart = new JLabel("Select Chart:");
		lblSelectChart.setBounds(10, 32, 125, 15);
		frame.getContentPane().add(lblSelectChart);
		
		cbChart = new JComboBox<String>();
		cbChart.setBounds(165, 32, 292, 24);
		frame.getContentPane().add(cbChart);
		loadCharts();
		
		JLabel lblSelectPresentation = new JLabel("Select Presentation:");
		lblSelectPresentation.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSelectPresentation.setBounds(10, 160, 159, 35);
		frame.getContentPane().add(lblSelectPresentation);
		
		
		cbPrezi = new JComboBox<String>();
		cbPrezi.setBounds(165, 170, 292, 24);
		frame.getContentPane().add(cbPrezi);
		loadPrezi();
		
		JButton btnAddToChart = new JButton("Add To Chart");
		btnAddToChart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					loadToChart();
				}
				JList<Object> list = new JList<Object>(CountriesIndicators.toArray());
				sc.setViewportView(list);
			}
		});
		btnAddToChart.setBounds(550, 75, 147, 25);
		frame.getContentPane().add(btnAddToChart);
		
		JButton btnRun = new JButton(" Run ");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String chart = cbChart.getSelectedItem().toString();
				startYear  = Integer.parseInt(cbYearFrom.getSelectedItem().toString());
				endYear  = Integer.parseInt(cbYearTo.getSelectedItem().toString());
				String prezi  = (String)cbPrezi.getSelectedItem();
				yearOrganization = checkSelectedPresentation(prezi);
				try {
					checkSelectedChart(chart);
				} catch (SQLException | IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CountriesIndicators = new ArrayList<String>();
				JList<Object> list = new JList<Object>(CountriesIndicators.toArray());
				sc.setViewportView(list);
				
			}
			

		});
		btnRun.setBounds(10, 337, 117, 25);
		frame.getContentPane().add(btnRun);
		

		
		sc.setBounds(469, 117, 319, 170);
		frame.getContentPane().add(sc);
		JList<Object> list = new JList<Object>(CountriesIndicators.toArray());
		sc.setViewportView(list);
		
	}	
	
	private void checkSelectedChart(String chart) throws SQLException, IOException, URISyntaxException {
		if(chart.equals("Bar Chart")) {
			try {
				barChart();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(chart.equals("Scatter Plot")) {
			try {
				scatterPlot();
			} catch (IOException | InterruptedException | URISyntaxException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(chart.equals("Timeline")){
			try {
				lineChart();
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private int checkSelectedPresentation(String organize) {
		int choice;
		if(organize.equals("5 years")) {
			System.out.println("mesa sto 5 years");
			 choice = 5;
		}else if(organize.equals("10 years")) {
			System.out.println("mesa sto 10 years");
			choice = 10;
		}else if(organize.equals("20 years")){
			System.out.println("mesa sto 20 yearr");
			choice = 20;
		}else {
			System.out.println("mesa sto 1 year");
			choice = 1;
		}
		return choice;
	}
	
	private void loadToChart() {
		indicator = cbIndicator.getSelectedIndex();
		Object indicatorz = cbIndicator.getSelectedItem();
		country = cbCountries.getSelectedItem();
		String co = country.toString();
		co = co + "#" + indicatorz + "";
		if(!CountriesIndicators.contains(co) && !indicator.equals(0)) {
			CountriesIndicators.add(co);
		}
	}

	private void lineChart() throws IOException, InterruptedException, SQLException, URISyntaxException {
		Parameters parameter = new Parameters();
		parameter.setStartYear(startYear);
		parameter.setEndYear(endYear);
		parameter.setYearOrganization(yearOrganization);
		parameter.setConnection(connection);
		parameter.setCountriesIndicators(CountriesIndicators);
		TimelinePlot tp = new TimelinePlot(parameter);
		tp.plot();
	}
	
	private void scatterPlot() throws IOException, InterruptedException, URISyntaxException, SQLException {
			Parameters parameter = new Parameters();
			String[] array1 = CountriesIndicators.get(0).toString().split("#");
			String[] array2 = CountriesIndicators.get(1).toString().split("#");
			String country = array1[0];
			String indicator1 = array1[1];
			String indicator2 = array2[1];
			parameter.setCountry(country);
			parameter.setStartYear(startYear);
			parameter.setEndYear(endYear);
			parameter.setIndicator1(indicator1);
			parameter.setIndicator2(indicator2);
			parameter.setYearOrganization(yearOrganization);
			parameter.setConnection(connection);
			ScatterPlot scatterPlot = new ScatterPlot(parameter);
			scatterPlot.plot();
	}
	
	private void barChart() throws URISyntaxException, IOException, SQLException {
		System.out.println("inside barChart");
		Parameters parameter = new Parameters();
		parameter.setStartYear(startYear);
		parameter.setEndYear(endYear);
		parameter.setYearOrganization(yearOrganization);
		parameter.setConnection(connection);
		parameter.setCountriesIndicators(CountriesIndicators);
		BarChart bc = new BarChart(parameter);
		bc.plot();
	}

	private void connectDB() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/countriesdata?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST";
		String username = this.user;
		String password = this.pass;
		//String password = "chrisGR1995";

		System.out.println("Connecting database...");
		connection = DriverManager.getConnection(url, username, password);

	}
	private void loadPrezi() {
		cbPrezi.addItem(" ");
		cbPrezi.addItem("5 years");
		cbPrezi.addItem("10 years");
		cbPrezi.addItem("20 years");
	}
	private void loadCharts() {
		cbChart.addItem("  ");
		cbChart.addItem("Bar Chart");
		cbChart.addItem("Scatter Plot");
		cbChart.addItem("Timeline");
	}
	
	private void loadCountries() throws SQLException {
		cbCountries.addItem(" ");
		Statement stmt = null;
		stmt = connection.createStatement();
		String sql = "SELECT DISTINCT country_name FROM countries";
	     ResultSet rs = stmt.executeQuery(sql);
	     //STEP 5: Extract data from result set
	     while(rs.next()){
	    	 
	   	  cbCountries.addItem(rs.getString("country_name"));
	     }
	     rs.close();

	}
	private void loadIndicators(String str) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		String sql = "SELECT indicator_name FROM countries WHERE country_name = '" + str + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	     
	    cbIndicator.removeAllItems();
	    cbIndicator.addItem(" ");
	    while(rs.next()){
	   	    cbIndicator.addItem(rs.getString("indicator_name"));
	    }
	    frame.getContentPane().add(cbIndicator);
	    rs.close();
	}
	
	private void loadYears(JComboBox<String> cb) throws SQLException {
		cb.addItem(" ");
		Statement stmt = null;
		stmt = connection.createStatement();
		String sql = "SELECT DISTINCT years FROM indicators ORDER BY years";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()){
	   	    cb.addItem(rs.getString("years"));
	    }
	    rs.close();	
	}
}
