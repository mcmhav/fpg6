package Managers;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import objectTypes.Employee;
import objectTypes.Meeting;
import objectTypes.Message;
import objectTypes.MessageType;
import objectTypes.Room;
import objectTypes.TimeFrame;
import connection.DbConnection;

public class MeetingManager implements IObjektManager<Meeting>{
	private EmployeeManager employeeManager;
	private ParticipantsManager particiapantsManager;
	private RoomManager roomManager;
	private MessageManager messageManager;
	private DbConnection connection;
	public MeetingManager(DbConnection connection, MessageManager messageManager) {
		this.connection = connection;
		this.messageManager = messageManager;
		this.roomManager = new RoomManager(connection);
		this.employeeManager = new EmployeeManager(connection);
		this.particiapantsManager = new ParticipantsManager(connection);
	}

	public ArrayList<Meeting> getAllMeetings(Employee e) {
		try {
			ArrayList<Integer> partisipantsListNumbers = particiapantsManager.getAllParticipantListNumbers(e); //Henter ut alle ID'ene til m�ter e er deltaker i
			String listOfNumbersSqlStyle = generateStringForParticipantNumbers(partisipantsListNumbers);
			ArrayList<Meeting> meetings = new ArrayList<Meeting>();
			
			String query = "SELECT * FROM Oppforing WHERE ";
			
			if(partisipantsListNumbers.size() != 0) 
				query += "Deltagerlistenr IN  " + listOfNumbersSqlStyle + " OR ";
			
			query += " Brukernavn = '" +e.getUserName()+ "';";
			ResultSet rs = connection.getResultSetFromQuery(query);
			while(rs.next()) {
				meetings.add(getMeetingFromResultSet(rs)); //Alle m�tene hvor emp er deltaker
			}
			
			return meetings;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private Meeting getMeetingFromResultSet(ResultSet rs) {
		try {
				int id = rs.getInt("id");
				String title = rs.getString("Tittel");
				String start = rs.getString("StartTidspunkt");
				String end = rs.getString("SluttTidspunkt");
				TimeFrame timeFrame = new TimeFrame(start, end);
				String creatorUserName = rs.getString("Brukernavn");
				String place = rs.getString("Sted");
				String comment = rs.getString("Beskrivelse");
				Room room = roomManager.getObject(rs.getString("LokaleNavn"));
				Employee employee = employeeManager.getObject(creatorUserName);
				ArrayList<Employee> participants = new ArrayList<Employee>();
				if(rs.getInt("Deltagerlistenr") != -1) {
					participants = particiapantsManager.getAllParticipants(rs.getInt("Deltagerlistenr"));
				}
				
				Meeting m = new Meeting(id,title, timeFrame, employee, room, place, comment, participants);
				return m;	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Integer insertObject(Meeting meeting) {
		HashMap<String, Object> meetingValues = new HashMap<String, Object>();
		TimeFrame timeFrame = meeting.getTime();
		meetingValues.put("Tittel", meeting.getTitle());
		meetingValues.put("Brukernavn", meeting.getCreator().getUserName());
		meetingValues.put("Beskrivelse", meeting.getComment());
		meetingValues.put("StartTidspunkt", timeFrame.getStartDateAsSQLString()); 
		meetingValues.put("SluttTidspunkt", timeFrame.getEndDateAsSQLString()); 
		meetingValues.put("Sted", meeting.getPlace());
		if(meeting.getRoom() != null){
			meetingValues.put("LokaleNavn", meeting.getRoom().getName());
		}
		int id = connection.insertElement("Oppforing", meetingValues);
		meeting.setId(id);
		insertParticipants(meeting);
		sendInvitationToAllParticipants(meeting);
		//insertDatesToMeeting(o, id); 
		return (Integer)id;
	
	}
                        
	private String generateStringForParticipantNumbers(ArrayList<Integer> list) {
		String tmp = "(";
		for(int i = 0; i < list.size(); i++) {
			tmp+= list.get(i);
			if(i!= list.size()-1) tmp += ",";
		}
		tmp+= ")";
		return tmp;
		
	}
	
	@Override
	public void updateObject(Meeting meeting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObject(Integer id) {
		String query = "DELETE FROM Oppforing WHERE id =  " + id + ";";
		connection.executeUpdate(query);
		query = "DELETE FROM Deltagerliste WHERE Deltagerlistenr = " + id + ";";
		connection.executeUpdate(query);
		query = "DELETE FROM Message WHERE OppforingId = " + id + ";";
		connection.executeUpdate(query);
	}

	@Override
	public Meeting getObject(Object primaryKey) {
		try {
			String query = "SELECT * FROM Oppforing WHERE id  = "+  (Integer)primaryKey + ";";
			ResultSet rs = connection.getResultSetFromQuery(query);
			rs.next();
			return getMeetingFromResultSet(rs);
		}catch(SQLException sqlE) {
			sqlE.printStackTrace();
			return null;
		}
		
	}
	public void updateMeeting(Meeting m){
		String query = "UPDATE Oppforing SET ";
		query += "Tittel = " + m.getTitle() + ",";
		query += "Brukernavn = " + m.getTitle() + ",";
		query += "Beskrivelse = " + m.getTitle() + ",";
		query += "LokaleNavn = " + m.getTitle() + ",";
		query += "Sted = " + m.getTitle() + ",";
		query += "StartTidsPunkt = " + m.getTitle() + ",";
		query += "SluttTidsPunkt = " + m.getTitle() + ",";
		query += "WHERE id = " + m.getId() +";";
		if(m.getParticipants().size() > 0) {
			deleteParticipants(m); //Slett de gamle slik at vi ikke f�r dobbelt opp
			insertParticipants(m);
		}
		connection.executeUpdate(query);
	}
	
	private void insertParticipants(Meeting meeting) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		if(meeting.getParticipants().size() > 0) {
			connection.executeUpdate("UPDATE Oppforing SET Deltagerlistenr = " + meeting.getId() + " WHERE id = " + meeting.getId() + ";");
			for(Employee participant : meeting.getParticipants()) {
				values.put("Deltagerlistenr", meeting.getId());
				values.put("Brukernavn", participant.getUserName());
				connection.insertElement("Deltagerliste", values);
			}
		}
	}
	
	private void deleteParticipants(Meeting meeting) {
		String query = "DELETE FROM Deltagerliste WHERE Deltagerlistenr = " + meeting.getId() + ";";
		connection.executeQuery(query);
	}

	@Override
	public void deleteObject(Meeting o) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendInvitationToAllParticipants(Meeting m) {
		for(Employee e : m.getParticipants()) {
			Message invitationMessage = new Message(m.getTitle(),MessageType.INVITATIONMESSAGE, m.getComment(),
					m.getCreator(), e, m);
			messageManager.insertObject(invitationMessage);
		}
	}
}
