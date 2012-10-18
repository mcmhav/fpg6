package objectTypes;

import java.io.Serializable;


public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6129918178782247403L;
	private String id, name, description;
	private Meeting meeting = null;
	private Employee sender, user;
	private String type;
	
	public Message(String name,String id, String type, String description, Employee sender, Employee user){
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.sender = sender;
		this.user = user;
		if(type.equals(MessageType.INVITATIONMESSAGE)){
			setDescription();
		}
	}
	
	

	public Message(String name, String type, String description, Employee sender, Employee user, Meeting meeting){
		this.name = name;
		this.meeting = meeting;
		this.type = type;
		this.description = description;
		this.sender = sender;
		this.user = user;
		if(type.equals(MessageType.INVITATIONMESSAGE)){
			setDescription();
		}
	}
	
	public Message(String id,String name, String type, String description, Employee sender, Employee user, Meeting meeting){
		this.id = id;
		this.name = name;
		this.meeting = meeting;
		this.type = type;
		this.description = description;
		this.sender = sender;
		this.user = user;
		if(type.equals(MessageType.INVITATIONMESSAGE)){
			setDescription();
		}
	}
	

	public String getId() {
		return id;
	}
	public Employee getRecipient() {
		return user;
	}

	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	private void setDescription() {
		String desc = "Du har blitt invitert til m\u00F8tet " + getMeeting().getTitle() + " av " + getMeeting().getCreator().getName() + ".";
		this.description = desc;
	}
	public Employee getSender(){
		return sender;
	}
	public Meeting getMeeting(){
		return meeting;
	}
	public String toString() {
		return getName();
	}
}
