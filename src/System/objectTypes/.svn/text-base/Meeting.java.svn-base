package objectTypes;

import java.io.Serializable;
import java.util.ArrayList;

public class Meeting extends Event implements Serializable{
	
	public enum Status {WAITING_FOR_ANSWER, ONE_OR_MORE_DECLINED, ALL_ACCEPTED}
	private ArrayList<Employee> participants;
	private Status status;
	
	public Meeting () {
		super();
		this.participants = null;
	}
	
	public Meeting (String title, TimeFrame time, Employee creator, Room room,
				String place, String comment, ArrayList<Employee> participants) {
		super(title, time, creator, room, place, comment);
		this.participants = participants;
	}
	
	public Meeting (int id,String title, TimeFrame time, Employee creator, Room room,
			String place, String comment, ArrayList<Employee> participants) {
	super(id,title, time, creator, room, place, comment);
	this.participants = participants;
}
	
	public ArrayList<Employee> getParticipants() {
		return this.participants;
	}

	public void setParticipants(ArrayList<Employee> participants) {
		this.participants = participants;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	public String toString(){
		return getTitle();
		
	}
}
