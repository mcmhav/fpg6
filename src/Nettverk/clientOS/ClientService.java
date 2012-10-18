package clientOS;

import java.util.ArrayList;

import objectTypes.Command;
import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Message;
import objectTypes.Room;
import objectTypes.TimeFrame;
public class ClientService {
	private ClientOS clientOS;

	public ClientService(ClientOS clientOS) {
		this.clientOS = clientOS;
	}
	
	public ClientOS getClientOS() {
		return clientOS;
	}

	//FUNKER
	public ArrayList<Event> getAllEvents(Employee employee) {
		/**
		 * Opprett en kommando som skal sendes via clientOS. For alle metodene i denne klassen skal det
		 * kun sendes Command. Alle kommandoer må ha satt 'command' - felt via de konstante stringene
		 * i kommandklassen som er overens med metodenavnet i denne klassen (Command.GETALLEVENTS).
		 * 
		 * Hvis du skal sende med et object som argument legger du til denne som attribute i kommandoen.
		 * 
		 * Deretter lager vi en arraylist med objekt som generics og henter får en instans av 
		 * arraylist-klassen fra clientOS.sendObjectAndGetResponse. Vi itererer gjennom alle
		 * objektene i denne listen, caster de til Meeting og putter de i vår arraylist med Meeting.
		 */
		
		Command c = new Command(Command.GETALLEVENTS, employee);
		ArrayList<Event> events = new ArrayList<Event>();
		try{
			for(Object o: (ArrayList<Object>)clientOS.sendObjectAndGetResponse(c)) {
				events.add((Event)o);
			}
		}catch(ClassCastException cce) {
			System.out.println("Error while casting arraylist of Meetings");
			cce.printStackTrace();
		}
		
		return events;
	}

	//FUNKER
	public ArrayList<Message> getAllMessages(Employee employee) {
		Command c = new Command(Command.GETALLMESSAGES, employee);
		ArrayList<Message> messages = new ArrayList<Message>();
		try{
			for(Object o: (ArrayList<Object>) clientOS.sendObjectAndGetResponse(c)) {
				messages.add((Message)o);
			}
		}catch(ClassCastException cce) {
			System.out.println("Error while casting arraylist of Messages");
			cce.printStackTrace();
		}

		return messages;
	}
	
	public boolean deleteEventOrMeeting(Event event, Employee employee) {
		Command c = new Command(Command.DELETEEVENTORMEETING);
		c.attribute = event;
		c.attribute2 = employee;
		
		Command response = (Command)clientOS.sendObjectAndGetResponse(c);
		if(response.command.equals(Command.TRUE)) return true;
		return false;
		
	}
	
	public boolean deleteEvent(Integer i) {
		Command c = new Command(Command.DELETEEVENT, i);
		Command response = (Command)clientOS.sendObjectAndGetResponse(c);
		if(response.command.equals(Command.TRUE)) return true;
		return false;
	}
	
	public void removeParticipant(Meeting m, Employee e) {
		Command c = new Command(Command.REMOVEPARTICIPANT);
		c.attribute = m;
		c.attribute2 = e;
		clientOS.sendObjectAndGetResponse(c);
	}
	
	//FUNKER
	public boolean deleteMessage(Integer messageId) {
		Command c = new Command(Command.DELETEMESSAGE, messageId);
		Command response = (Command)clientOS.sendObjectAndGetResponse(c);
		if(response.command.equals(Command.TRUE)) return true;
		return false;
	}

	//FUNKER
	public boolean setParticipantAnswer(Employee employee, Meeting meeting, String reply) {
		Command c = new Command(Command.SETANSWER, employee);
		c.attribute2 = meeting;
		c.attribute3 = reply;
		Command response = ((Command)clientOS.sendObjectAndGetResponse(c));
		if(response.command.equals(Command.TRUE)) return true;
		return false;
	}
	
	public boolean updateMeeting(Meeting m) {
		Command c = new Command(Command.UPDATEMEETING, m);
		Command response = ((Command)clientOS.sendObjectAndGetResponse(c));
		if(response.command.equals(Command.TRUE)) return true;
		return false;
	}

	//FUNKER
	public ArrayList<Employee> getAllEmployees() {
		Command c = new Command(Command.GETALLEMPLOYEES);
		ArrayList<Employee> employees = new ArrayList<Employee>();
		ArrayList<Employee> tempArray = (ArrayList<Employee>) clientOS.sendObjectAndGetResponse(c);
		
		for (Employee e : tempArray) {
			employees.add(e);
		}
		return employees;
	}

	public ArrayList<Room> getAllFreeRooms(TimeFrame timeFrame) {
		Command c = new Command(Command.GETALLFREEROOMS, timeFrame);
		return (ArrayList<Room>) clientOS.sendObjectAndGetResponse(c);
	}

	public boolean deleteMeeting(int id) {
		Command c = new Command(Command.DELETEMEETING, (Integer)id);
		Command response = (Command)clientOS.sendObjectAndGetResponse(c);
		if(response.command.equals(Command.TRUE)) return true;
		return false;
	}

	public void reserveRoom(Room r, TimeFrame timeFrame) {

	}
	
	public ArrayList<Room> getAllRooms() {
		Command c = new Command(Command.GETALLROOMS);
		return (ArrayList<Room>) clientOS.sendObjectAndGetResponse(c);
	}




}
