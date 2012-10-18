package Managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import connection.DbConnection;
import objectTypes.Employee;
import objectTypes.Room;
import objectTypes.TimeFrame;

public class RoomManager implements IObjektManager<Room> {

	private DbConnection connection;
	private TimeFrameManager timeFrameManager;
	public RoomManager(DbConnection conn){
		this.connection = conn;
		timeFrameManager = new TimeFrameManager(conn);
		
	}
	
	@Override
	public Integer insertObject(Room o) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Navn", o.getName());
		map.put("Beskrivelse", o.getDescription());
		return (Integer)connection.insertElement("Oppforing", map);

	}

	@Override
	public void updateObject(Room o) {
		// TODO Auto-generated method stub
		deleteObject(o);
		insertObject(o);
		
	}

	@Override
	public void deleteObject(Room o) {
		connection.deleteObject("Lokale", o.getName(), "Navn");
		
	}

	public Room getObject(String navn) {
		if(!navn.equals("")) {
			String query = "Select * FROM Lokale WHERE Navn = '" + navn + "';";
			ResultSet rs  = connection.getResultSetFromQuery(query);
			Room r = null;
			try {
				rs.next();
				r = new Room(rs.getString("Navn"), rs.getString("Beskrivelse"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return r;
		}
		return null;
	}
	
	public ArrayList<Room> getAllRooms(){
		ArrayList<Room> rom = new ArrayList<Room>();
		String query = "SELECT * FROM Lokale;";
		ResultSet rs = connection.getResultSetFromQuery(query);
		
		try {
			while(rs.next()){
				rom.add(new Room(rs.getString("Navn"), rs.getString("Beskrivelse")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rom;
	}
	
	public boolean roomIsAvailable(Room room, TimeFrame timeFrame) {
		ArrayList<TimeFrame> timeFramesForRoom = timeFrameManager.getAllTimeFrames(room);
		if(timeFrameManager.isAvailable(timeFrame, timeFramesForRoom)) {
			return true;
		}
		return false;
			
	}
	
	
	public ArrayList<Room> getAvialiableRooms(ArrayList<Room> rooms,TimeFrame timeFrame) {
		ArrayList<Room> aviableRooms = new ArrayList<Room>();
		
		for(Room room : rooms) {
			if (roomIsAvailable(room, timeFrame)) {
				aviableRooms.add(room);
			}
		}
		return aviableRooms;
	}

	@Override
	public Room getObject(Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObject(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
