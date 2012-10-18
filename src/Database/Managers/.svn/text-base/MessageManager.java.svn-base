package Managers;

import objectTypes.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Managers.IObjektManager;

import javax.sql.rowset.JdbcRowSet;

import objectTypes.Employee;
import objectTypes.Meeting;
import objectTypes.Message;
import objectTypes.MessageType;
import connection.DbConnection;


public class MessageManager implements IObjektManager<Message>{

	private DbConnection connection;
	private EmployeeManager employeeManager;
	private MeetingManager meetingManager;
	public MessageManager(DbConnection connection) {
		this.connection = connection;
		this.meetingManager = new MeetingManager(connection,this);
		this.employeeManager = new EmployeeManager(connection);
	}

	public ArrayList<Message> getAllMessages(Employee e) {
		ArrayList<Message> messageList = new ArrayList<Message>();
		try{
			String query = "SELECT * FROM Melding WHERE Mottaker = '"+ e.getUserName() + "';";
			
			ResultSet rs = connection.getResultSetFromQuery(query);
			while (rs.next()) {
				messageList.add(getMessageFromResultSet(rs));
			}

			return messageList;
		}catch(SQLException sqlE) {
			sqlE.printStackTrace();
			return null;
		}
		
	}
	
	public void deleteMeetingMessages(Meeting m) {
		String query = "DELETE FROM Melding WHERE OppforingId = " + m.getId() + " AND Type != 'INFORMATIONMESSAGE';";
		connection.executeQuery(query);
	}

	public void sendInformationMessageToEmployees(Employee employee,Meeting m) {
		ArrayList<Employee> employees = m.getParticipants();
		for(Employee participant : employees) {
			if(!participant.getUserName().equals(employee.getUserName())) {
				insertObject(new Message(m.getTitle(),MessageType.INFORMATIONMESSAGE , employee.getName() + " har avsl\u00e5tt m\u00f8tet: " + m.getTitle(), employee, participant, m));
			}
				
		}
	}
	

	private Message getMessageFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("Mid");
			String name = rs.getString("Tittel");
			Employee sender = employeeManager.getObject(rs.getString("Sender"));
			Employee user = employeeManager.getObject(rs.getString("Mottaker"));
			String type = rs.getString("Type");
			Meeting meeting = null;
			if(!type.equals(MessageType.INFORMATIONMESSAGE)) {
				meeting = meetingManager.getObject(rs.getInt("OppforingId"));
			}
			else meeting = new Meeting();
			String description = rs.getString("Beskrivelse");
			return new Message(String.valueOf(id),name,type ,description,sender,user,meeting);
		}catch(SQLException q) {
			q.printStackTrace();
			return null;
		}
	}
	@Override
	public Integer insertObject(Message message) {
		HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("Tittel", message.getName());
		messageMap.put("Beskrivelse", message.getDescription());
		messageMap.put("Sender", message.getSender().getUserName());
		messageMap.put("Mottaker", message.getRecipient().getUserName()); //TODO fiks n�r messsage blir oppdatert
		messageMap.put("Type", message.getType());
		messageMap.put("OppforingId", message.getMeeting().getId());
		return (Integer)connection.insertElement("Melding", messageMap);
	}

	@Override
	public void updateObject(Message o) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteObject(Message o) {
		connection.deleteObject("Melding",Integer.parseInt(o.getId()) ,"Mid");
	}


	@Override
	public Message getObject(Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteMessage(int mid){
		connection.deleteObject("Melding", mid, "Mid");
	}

	@Override
	public void deleteObject(Integer id) {
		// TODO Auto-generated method stub
		
	}	

	public void sendDeclineMessageToCreator(Employee employee, Meeting meeting) {
		Message declineMessage = new Message(meeting.getTitle(),MessageType.DECLINEMESSAGE, 
				employee.getName() + "har avsl\u00e5tt din invitasjon til m\u00f8tet " + meeting.getTitle()
				, employee, meeting.getCreator(), meeting);
		insertObject(declineMessage);
	}
	
	public void sendMeetingDeletedWarning(Employee employee, Meeting meeting) {
		for(Employee participant: meeting.getParticipants()) {
			insertObject(new Message(meeting.getTitle(), MessageType.INFORMATIONMESSAGE, 
					employee.getName() + " har avlyst m\u00f8tet " + meeting.getTitle() + ".", 
					employee, participant, meeting));
		}
	}


}
