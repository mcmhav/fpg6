package Managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import objectTypes.Employee;
import objectTypes.Meeting;
import objectTypes.Message;
import connection.DbConnection;

public class ParticipantsManager {
	private EmployeeManager employeeManager;
	private DbConnection connection;
	public ParticipantsManager(DbConnection connection) {
		this.connection = connection;
		this.employeeManager = new EmployeeManager(connection);
	}
	
	
	//Brukes i MeetingManager for å finne alle møter hvor en employee er deltaker
	public ArrayList<Integer> getAllParticipantListNumbers(Employee employee) {
		ArrayList<Integer> meetingList = new ArrayList<Integer>();
		String query = "SELECT * FROM Deltagerliste WHERE Brukernavn = '" + employee.getUserName() + "' AND Svar ='ja';";
		try{
			ResultSet rs = connection.getResultSetFromQuery(query);
			while(rs.next()) {
				meetingList.add(Integer.parseInt(rs.getString("Deltagerlistenr")));
			}
			return meetingList;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		
		
		
	}
	
	
	public Meeting.Status getStatus(int i) {
		String query = "SELECT * FROM Deltagerliste WHERE Deltagerlistenr = " + i + ";";
		ResultSet rs = connection.getResultSetFromQuery(query);
		boolean allAccepted = true;
		try {
			while(rs.next()) {
				if (rs.getString("Svar").equals("nei")) return Meeting.Status.ONE_OR_MORE_DECLINED;
				if(!rs.getString("Svar").equals("ja")) allAccepted = false;
			}
			
			if(allAccepted) return Meeting.Status.ALL_ACCEPTED;
			return Meeting.Status.WAITING_FOR_ANSWER;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void setParticipantAnswerToNo(Meeting m, Employee e) {
		String query = "UPDATE Deltagerliste SET Svar = 'nei' WHERE Deltagerlistenr = " + m.getId() + " AND Brukernavn = '" + e.getUserName() + "';";
		connection.executeQuery(query);
	}
	
	public void removeParticipantFromMeeting(Meeting m, Employee e) {
		String query = "DELETE FROM Deltagerliste WHERE Deltagerlistenr = " +m.getId() + " AND Brukernavn = '" + e.getUserName() + "';";
		connection.executeQuery(query);
	}
	public ArrayList<Employee> getAllParticipants(int participantsListID) {
		try {
			ArrayList<Employee> participants = new ArrayList<Employee>();
			String query = "SELECT * FROM Deltagerliste WHERE Deltagerlistenr = " + participantsListID +";";
			ResultSet rs = connection.getResultSetFromQuery(query);
			while(rs.next()) {
				participants.add(employeeManager.getObject(rs.getString(3)));
			}
			return participants;
		}catch(SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	
	//Usikker på denne metoden....
	public void participantAnswer(Employee e, Meeting m, String reply){
		if(reply.equals("ja") || reply.equals("nei") || reply.equals("tom")) {
			String query = "UPDATE Deltagerliste SET Svar = '" + reply + "' WHERE Deltagerlistenr = " + m.getId() + " AND Brukernavn = '" + e.getUserName() + "';";
			connection.executeUpdate(query);
			
		}
		
		else System.err.println("Wrong format in reply, either 'ja', 'nei', or 'tom'");
	}
	
}
