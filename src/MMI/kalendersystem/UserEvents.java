package kalendersystem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import clientOS.ClientOS;
import clientOS.ClientService;

import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Message;
import objectTypes.TimeFrame;

public class UserEvents {
	private Employee user;
	private ArrayList<Event> events;
	private ArrayList<Message> messages;
	private ClientService clientService;

	public UserEvents() {
		user = null;
		events = new ArrayList<Event>();
		messages = new ArrayList<Message>();
	}
	
	public UserEvents(Employee user, boolean loadMessages, ClientService clientService) { 
		this.user = user;
		this.clientService = clientService;
		this.events = new ArrayList<Event>();
		messages = new ArrayList<Message>();
		loadInfo(loadMessages);
	}
	
	public ArrayList<Event> getAllEvents() {
		return events;
	}
	
	public ClientService getClientService() {
		return clientService;
	}
	
	private void writeEventList() {
		for (Event e : events) {
			System.out.println('"'+e.getTitle()+'"'+" den "+e.getTime().getDateString()+" kl "+e.getTime().getStartHourString()+" til "+e.getTime().getEndHourString());
		}
	}
	
	public void loadInfo(boolean loadMessages) {
		events = clientService.getAllEvents(user);
//		writeEventList();
		if (loadMessages)
			messages = clientService.getAllMessages(user);
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public Employee getUser(){
		return user;
	}
	public void removeEvent(Event e) {
		events.remove(e);
		clientService.deleteEventOrMeeting(e, user);
	}
	
	public void addEvent(Event e) {
		events.add(e);
		//sett inn e i databasen
	}
	public void removeMessage(Message m){
		messages.remove(m);
		//TODO: lage
	}

	public ArrayList<Event> getEventsOnDay(TimeFrame timeFrame) {
		ArrayList<Event> returnedEvents = new ArrayList<Event>();
		for (Event e : events) {
			if (e.getTime().isOnSameDay(timeFrame))
				returnedEvents.add(e);
		}
		return returnedEvents;
	}
	
	public ArrayList<Event> getEventsOnDay(Calendar day) {
		int date = day.get(Calendar.DAY_OF_MONTH);
		int month = day.get(Calendar.MONTH);
		int year = day.get(Calendar.YEAR);
		
		TimeFrame timeFrame = new TimeFrame(date, month, year, 0, 0);
		ArrayList<Event> returnEvents = new ArrayList<Event>();
		for (Event event : events) {
			if (event.getTime().isOnSameDay(timeFrame))
				returnEvents.add(event);
		}
		return returnEvents;
	}
	
	public ArrayList<Event> getOwnedEvents() {
		ArrayList<Event> returnedEvents = new ArrayList<Event>();
		Iterator<Event> it = events.iterator();
		while (it.hasNext()) {
			Event e = it.next();
			if (e.getCreator().equals(user))
				returnedEvents.add(e);
		}
		return returnedEvents;
	}

	public void removeParticipant(Meeting meeting, Employee sender) {
		if(events.contains(meeting)){
			Meeting m = (Meeting) events.get(events.indexOf(meeting));
			events.remove(meeting);
			ArrayList<Employee> tmp = m.getParticipants();
			if(tmp.contains(sender))
				tmp.remove(sender);
			m.setParticipants(tmp);
		}
	}
}