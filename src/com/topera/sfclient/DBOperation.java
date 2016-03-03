package com.topera.sfclient;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBOperation {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://diamondback1.clnatmi6o6y6.us-west-1.rds.amazonaws.com:3306/diamondback";

	// Database credentials
	static final String USER = "diamondback";
	static final String PASS = "passw0rd";

	static final String OLD_FORMAT = "yyyy-MM-dd";
	static final String NEW_FORMAT = "MM/dd/yyyy";

	// August 12, 2010
	static String oldDateString = "12/08/2010";
	static String newDateString;

	public static String changeFormat(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
		
		newDateString = sdf.format(date);
		return newDateString;
	}

	public static List<String> getWorkstations() {
		Connection conn = null;
		String fileName = "";
		List<String> workstations = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println(new Date() + "Connecting to database...");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String sql;
			sql = "select distinct workstation,datetime from diamondback_metadata where salesforceProcid is null";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				fileName = rs.getString("workstation") + "#" + changeFormat(rs.getDate("datetime"));
				workstations.add(fileName);
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workstations;

	}

	public static void updateWorkStation(String wrkstn, String sfProcid) {

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println(new Date() + "Connecting to database...");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String sql;
			sql = "update diamondback_metadata set salesforceProcid = '" + sfProcid + "' where workstation='" + wrkstn
					+ "'";
			stmt.execute(sql);

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}// end FirstExample