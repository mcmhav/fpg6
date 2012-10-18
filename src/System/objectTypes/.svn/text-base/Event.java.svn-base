package objectTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable{
	private String title;
	private TimeFrame time;
	private Employee creator;
	private Room room;
	private String place; //WTF
	private String comment;
	private int id;
	
	public Event() {
		title = null; //mA bli satt, 
		time = new TimeFrame(new Date(), new Date());
		creator = null;
		room = null;
		place = ""; //Disse trener ikke bli satt, derfor ikke null
		comment = "";
	}
	
	public Event(String title, TimeFrame time, Employee creator, Room room, String place, String comment) {
		this.title = title;
		this.time = time;
		this.room = room;
		this.creator = creator; 
		this.creator = creator;
		this.room = room;
		this.place = place;
		this.comment = comment;
	}
	
	public Event(int id,String title, TimeFrame time, Employee creator, Room room, String place, String comment) {
		this.id = id;
		this.title = title;
		this.time = time;
		this.room = room; //testing
		this.creator = creator; //testing
		this.creator = creator;
		this.room = room;
		this.place = place;
		this.comment = comment;
	}
	
	public String getTitle() {
		return title;
	}
	
//	public Date getStartTime() {
//		return time.getStartTime();
//	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
//	public Date getEndTime() {
//		return time.getEnd();
//	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TimeFrame getTime() {
		return this.time;
	}
	public void setTime(TimeFrame time) {
		this.time = time;
	}
	public Employee getCreator() { 
		return creator;
	}
	public void setCreator(Employee creator) {
		this.creator = creator;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String toString(){
		return title;
	}
}