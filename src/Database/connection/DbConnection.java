package connection;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import objectTypes.Event;

public class DbConnection {

	private Connection con = null;
	private Statement statement= null;
	private ResultSet rs = null;


	/**
	 * 	Oppretter en tilkobling til database oppgitt som parameter.
	 * @param server e.g hakonfam.dyndns.org
	 * @param database e.g Fellesprosjekt
	 * @param userName
	 * @param password
	 */
	public DbConnection(String server, String database, String userName, String password) {
		String url = "jdbc:mysql://" + server + "/" + database;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			con = DriverManager.getConnection(url,userName,password);
			statement = con.createStatement();

		} catch (Exception ex) {
			System.out.println("Connection to DB failed: "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * @param table
	 * @param values
	 * @return returns the auto-generated id of the element
	 */
	public int insertElement(String table, HashMap<String, Object> values) {
		Iterator<String> keyIterator = values.keySet().iterator();
		Iterator<Object> valueIterator = values.values().iterator();
		Object tmpObject = null;
		int setSize = values.keySet().size();
		String query = "INSERT INTO " + table + "(";

		for(int i = 0; i < setSize; i++) {
			query += keyIterator.next();
			if(i != setSize -1) query += ","; //Slik at vi ikke fŒr komma bak bakerste verdi
		}

		query += ") VALUES (";
		for(int i = 0; i <setSize; i++) {
			tmpObject = valueIterator.next();
			query += insertValueString(tmpObject);
			if(i != setSize -1) query += ","; //Slik at vi ikke fŒr komma bak bakerste verdi
		}
		query += ");";
		
		try {
			statement.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getIdFromInsertedObject(table, values);
	}
	
	public void executeUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeQuery(String query) {
		try {
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteObject(String table, Object primaryKey, String primaryKeyColumn) {
		String query = "DELETE FROM "  + table + " WHERE " + primaryKeyColumn + " = " + insertValueString(primaryKey) + ";";
		try {
			statement.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getIdFromInsertedObject(String table, HashMap<String, Object> values) {
		Iterator<String> keyIterator = values.keySet().iterator();
		Iterator<Object> valueIterator = values.values().iterator();
		int setSize = values.keySet().size();
		String query = "SELECT * FROM " + table + " WHERE ";
		for(int i  = 0; i < setSize; i++) {
			query += keyIterator.next() + " = " + insertValueString(valueIterator.next());
			if(i != setSize -1) query += " AND "; //Slik at vi ikke fŒr 'and' bak bakerste verdi
		}
		
		query += ";";
		
		try {
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public String insertValueString(Object o) {
		if(isString(o)) return "'" + (String)o + "'";
		else return ""+(Integer)o;
	}
	/**
	 * 
	 * @param o
	 * @return False hvis det er en int	
	 */
	public boolean isString(Object o) {
		try {
			String s = (String)o;
			
		} catch (ClassCastException cce) {
			//cce.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet getResultSetFromQuery(String query) {
		try {
			return con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}


