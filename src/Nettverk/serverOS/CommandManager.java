package serverOS;

import java.util.ArrayList;
import connection.DbConnection;
import Managers.*;
import objectTypes.*;

public class CommandManager {
	private EmployeeManager employeeManager;
	private EventManager eventManager;
	private MessageManager messageManager;
	private MeetingManager meetingManager;
	private TimeFrameManager timeFrameManager;
	private RoomManager roomManager;
	private ParticipantsManager participantsManager;

	public CommandManager(DbConnection connection) {
		employeeManager = new EmployeeManager(connection);
		eventManager = new EventManager(connection);
		messageManager = new MessageManager(connection);
		meetingManager = new MeetingManager(connection,messageManager);
		roomManager = new RoomManager(connection);
		participantsManager = new ParticipantsManager(connection);
		timeFrameManager = new TimeFrameManager(connection);
	}

	public Object manageCommandAndGetObject(Command c) {

		if(c.command.equals(Command.DELETEMESSAGE)) {
			messageManager.deleteMessage((Integer)c.attribute);
			return new Command(Command.TRUE);
		}
		else if(c.command.equals(Command.DELETEEVENT)) {
			eventManager.deleteObject((Integer)c.attribute);
			return new Command(Command.TRUE);
		}
		
		else if(c.command.equals(Command.DELETEEVENTORMEETING)) {
			eventManager.deleteEventOrMeeting((Event)c.attribute, (Employee)c.attribute2);
			return new Command(Command.TRUE);
		}
		

		else if(c.command.equals(Command.GETALLEMPLOYEES)) {
			return employeeManager.getAllEmployees();
		}

		
		else if(c.command.equals(Command.GETALLEVENTS)) {
			if(c.attribute instanceof Employee) {
				Employee employee = (Employee)c.attribute;
				ArrayList<Event> events = eventManager.getAllEvents(employee);
				return events;
			} else System.out.println("Feil attributt satt p\u00E5 getAllEvents-kommando");
			return null;
		}

		
		else if(c.command.equals(Command.GETALLFREEROOMS)) {
			if(c.attribute instanceof TimeFrame) {
				TimeFrame t = (TimeFrame)c.attribute;
				ArrayList<Room> allRooms = roomManager.getAllRooms();
				allRooms = roomManager.getAvialiableRooms(allRooms,t);
				return allRooms;
			} else System.out.println("Feil attributt satt p\u00E5 getAllFreeRooms-kommando");
			return null;
		}

		
		else if(c.command.equals(Command.GETALLMESSAGES)) {
			if(c.attribute instanceof Employee) {
				Employee employee = (Employee)c.attribute;
				ArrayList<Message> messages = messageManager.getAllMessages(employee);
				return messages;
			} else System.out.println("Feil attributt satt p\u00E5 getAllMessages-kommando");
			return null;
		}
		

		else if(c.command.equals(Command.DELETEMEETING)) {
			if(c.attribute instanceof Meeting) {
				meetingManager.deleteObject((Integer)c.attribute);
				return new Command(Command.TRUE);
			}else System.out.println("Feil attributt satt p\u00E5 removeEvent-kommando");
			return null;
		}


		else if(c.command.equals(Command.SETANSWER)) {
			if(c.attribute instanceof Employee && c.attribute2 instanceof Meeting && c.attribute3 instanceof String) {
				Employee e = (Employee)c.attribute;
				Meeting m = (Meeting)c.attribute2;
				String s = (String)c.attribute3;
				participantsManager.participantAnswer(e, m, s);
				if(s.equals("nei")) {
					messageManager.sendDeclineMessageToCreator(e,m);
				}
				return new Command(Command.TRUE);

			}else System.out.println("Feil attributt satt p\u00E5 removeEvent-kommando");
			return null;
		}
		
		else if(c.command.equals(Command.REMOVEPARTICIPANT)) {
			if(c.attribute instanceof Meeting && c.attribute2 instanceof Employee) {
				participantsManager.removeParticipantFromMeeting((Meeting)c.attribute, (Employee)c.attribute2);
				return new Command(Command.TRUE);
			} else System.out.println("Feil attributt p\u00E5 removeevent");
			return new Command(Command.FALSE);
		}
		
		//Sletter det opprinnelige m�tet og oppretter et nytt. 
		else if(c.command.equals(Command.UPDATEMEETING)) {
			if(c.attribute instanceof Meeting) {
				Meeting meeting = (Meeting)c.attribute;
				meetingManager.deleteObject(meeting.getId());
				meetingManager.insertObject(meeting);
				return new Command(Command.TRUE);
			}
			return null;
		}
		
		
		else if(c.command.equals(Command.GETALLROOMS)) {
			ArrayList<Room> rooms = roomManager.getAllRooms();
			return rooms;
		}

		else {
			System.out.println("Feil format p\u00E5 commando inn til server: " + c.command);
			return null;
		}

	}


}
