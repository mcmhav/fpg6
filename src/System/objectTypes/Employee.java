package objectTypes;

import java.io.Serializable;

public class Employee implements Serializable{
	private String userName;
	private String password;
	private String name;
	
	public Employee() {}
	
	public Employee(String userName, String password, String name) {
		this.userName = userName;
		this.password = password;
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return getName();
	}
}

