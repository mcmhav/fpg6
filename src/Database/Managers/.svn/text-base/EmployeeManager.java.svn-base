package Managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DbConnection;
import objectTypes.Employee;

public class EmployeeManager implements IObjektManager<Employee>{
private DbConnection connection;
	public EmployeeManager(DbConnection connection) {
		this.connection = connection;
	}
	@Override
	public Integer insertObject(Employee o) {
		return new Integer(-1);
		
	}

	@Override
	public void updateObject(Employee o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObject(Employee o) {
		// TODO Auto-generated method stub
		
	}
	
	public Employee getObject(Object o) {
		try {
		String userName = (String)o;
		String query = "SELECT * FROM Ansatt WHERE Brukernavn = '" + userName+ "';";
		ResultSet rs = connection.getResultSetFromQuery(query);
		rs.next();
		String name = rs.getString("Navn");
		String password = rs.getString("Passord");
		return new Employee(userName, password, name);
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Employee> getAllEmployees(){
		ArrayList<Employee> ansatte = new ArrayList<Employee>();
		String query = "SELECT * FROM Ansatt;";
		ResultSet rs = connection.getResultSetFromQuery(query);
		
		try {
			while(rs.next()){
				ansatte.add(new Employee(rs.getString("Brukernavn"), rs.getString("Passord"), rs.getString("Navn")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ansatte;
	}
	@Override
	public void deleteObject(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
