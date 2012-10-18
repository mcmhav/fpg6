package serverOS;

import Managers.EmployeeManager;
import Managers.EventManager;
import Managers.MeetingManager;
import Managers.MessageManager;
import Managers.ParticipantsManager;
import Managers.RoomManager;
import connection.DbConnection;
import objectTypes.Command;
import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Message;
import objectTypes.MessageType;
import objectTypes.Room;

public class ObjectManager {
	private CommandManager commandManager;
	private DbConnection dbConnection;
	private EmployeeManager employeeManager;
	private EventManager eventManager;
	private MessageManager messageManager;
	private MeetingManager meetingManager;
	private RoomManager roomManager;
	private ParticipantsManager participantsManager;
	public ObjectManager(DbConnection dbConnection) {
		this.dbConnection = dbConnection;
		this.commandManager = new CommandManager(dbConnection);
		employeeManager = new EmployeeManager(dbConnection);
		eventManager = new EventManager(dbConnection);
		messageManager = new MessageManager(dbConnection);
		meetingManager = new MeetingManager(dbConnection, messageManager);
		roomManager = new RoomManager(dbConnection);
		participantsManager = new ParticipantsManager(dbConnection);
	}
	
	public Object manageObject(Object o) {
		if(o instanceof Command) {
			return commandManager.manageCommandAndGetObject((Command)o);
		}
		else if(o instanceof Meeting) {
			Meeting m =  (Meeting)o;
			Integer i = meetingManager.insertObject(m);
			m.setId(i);
			return m.getId();
		}

		else if(o instanceof Event) {
			return new Integer(eventManager.insertObject((Event)o));
		}

		else {
			System.out.println("KJente ikke igjen objektet");
			return null;
		}
	}
	
	
}
