package Managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import connection.DbConnection;
import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Meeting.Status;
import objectTypes.Room;
import objectTypes.TimeFrame;

public class EventManager implements IObjektManager<Event> {
	private EmployeeManager employeeManager;
	private RoomManager roomManager;
	private DbConnection connection;
	private ParticipantsManager participantManager;
	private MeetingManager meetingManager;
	private MessageManager messageManager;
	public EventManager(DbConnection conn){
		this.connection = conn;
		this.messageManager = new MessageManager(conn);
		this.meetingManager = new MeetingManager(conn,messageManager);
		this.participantManager = new ParticipantsManager(conn);
		this.roomManager = new RoomManager(connection);
		this.employeeManager = new EmployeeManager(connection);
	}

	public ArrayList<Event> getAllEvents(Employee e) {
		try {
			ArrayList<Integer> partisipantsListNumbers = participantManager.getAllParticipantListNumbers(e); //Henter ut alle ID'ene til m�ter e er deltaker i
			String listOfNumbersSqlStyle = generateStringForParticipantNumbers(partisipantsListNumbers);
			ArrayList<Event> events = new ArrayList<Event>();
			
			String query = "SELECT * FROM Oppforing WHERE ";
			
			if(partisipantsListNumbers.size() != 0) 
				query += "Deltagerlistenr IN  " + listOfNumbersSqlStyle + " OR ";
			
			query += " Brukernavn = '" +e.getUserName()+ "';";
			ResultSet rs = connection.getResultSetFromQuery(query);
			while(rs.next()) {
				events.add((Event)getMeetingFromResultSet(rs)); //Alle m�tene hvor emp er deltaker
			}
			
			return events;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Integer insertObject(Event event) {
		TimeFrame timeFrame = event.getTime();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Tittel", event.getTitle());
		map.put("StartTidspunkt", timeFrame.getStartDateAsSQLString()); 
		map.put("SluttTidspunkt", timeFrame.getEndDateAsSQLString());
		map.put("Brukernavn", event.getCreator().getUserName());
		if(event.getRoom() != null){
			map.put("LokaleNavn", event.getRoom().getName());
		}	
		map.put("Sted", event.getPlace());
		map.put("Beskrivelse", event.getComment());
		return (Integer)connection.insertElement("Oppforing", map);
		
	}

	@Override
	public void updateObject(Event o) {
		deleteObject(o);
		insertObject(o);
		// 
		
	}

	@Override
	public void deleteObject(Event o) {
		connection.deleteObject("Oppforing", o.getTime(), "Tidspunkt");
		// TODO Auto-generated method stub
		
	}

	@Override
	public Meeting getObject(Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObject(Integer id) {
		String query = "DELETE FROM Oppforing WHERE id = " + id + ";";
		connection.executeQuery(query);
	}
	
	
	//WARNING: STYGG
	public void deleteEventOrMeeting(Event event, Employee employee) {
		int id = event.getId();
		String query = "SELECT * FROM Oppforing WHERE id = "+ id +";";
		ResultSet rs = connection.getResultSetFromQuery(query);
		System.out.println(query);
		try {
			rs.next();	
			if(rs.getInt("Deltagerlistenr") == id) { //Det er et meeting, ikke event
				Meeting meeting = (Meeting)event;
				if(rs.getString("Brukernavn").equals(employee.getUserName())) { //Det er oppretter som sletter m�tet.
					meetingManager.deleteObject(id);
					messageManager.deleteMeetingMessages(meeting);
					messageManager.sendMeetingDeletedWarning(employee, meeting);
				}
				else {
					participantManager.setParticipantAnswerToNo(meeting, employee);
					messageManager.sendInformationMessageToEmployees(employee, meeting);
					messageManager.sendDeclineMessageToCreator(employee, meeting);
				}
			}
			else this.deleteObject(id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Room room = null;
			Meeting.Status status = null;
			if (roomManager.getObject(rs.getString("LokaleNavn")) != null) {
				room = roomManager.getObject(rs.getString("LokaleNavn"));
			}
			Employee employee = employeeManager.getObject(creatorUserName);
			ArrayList<Employee> participants = new ArrayList<Employee>();
			if(rs.getInt("Deltagerlistenr") != -1) {
				participants = participantManager.getAllParticipants(rs.getInt("Deltagerlistenr"));
				status = participantManager.getStatus(rs.getInt("Deltagerlistenr"));
				
			}
			
			Meeting m = new Meeting(id,title, timeFrame, employee, room, place, comment, participants);
			m.setStatus(status);
			return m;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
