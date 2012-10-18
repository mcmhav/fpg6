package Managers;

import java.sql.*;
import java.util.*;
import java.util.Date;

import objectTypes.Room;
import objectTypes.TimeFrame;
import connection.DbConnection;

public class TimeFrameManager {
	private DbConnection connection;
	public TimeFrameManager(DbConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * KUN FOR TESTING
	 */
	public TimeFrameManager(){}
	
	public ArrayList<TimeFrame> getAllTimeFrames(Room room) {
		ArrayList<TimeFrame> timeFrames = new ArrayList<TimeFrame>();
		String query = "SELECT StartTidspunkt, SluttTidspunkt FROM Oppforing WHERE LokaleNavn = '"+ room.getName() +"';";
		ResultSet rs = connection.getResultSetFromQuery(query);
		try {
			while(rs.next()) {
				timeFrames.add(getTimeFrameFromResultSet(rs));
			}
			return timeFrames;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public TimeFrame getTimeFrameFromResultSet(ResultSet rs) {
			TimeFrame tf = new TimeFrame();
				try {
					return new TimeFrame(rs.getString("StartTidspunkt"),rs.getString("SluttTidspunkt"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	}
	
	public boolean isAvailable(TimeFrame timeFrame, ArrayList<TimeFrame> timeFrames) {
		Date start = timeFrame.getStart();
		Date end = timeFrame.getEnd();

		for (int i = 0; i < timeFrames.size(); i++) {
			if(!timesFit(timeFrame,timeFrames.get(i))) return false;
		}
		return true;
		
	}
	private boolean timesFit(TimeFrame ourTimeFrame, TimeFrame busyTimeFrame) {
		Date ourStart = ourTimeFrame.getStart();
		Date ourEnd = ourTimeFrame.getEnd();
		Date busyStart = busyTimeFrame.getStart();
		Date busyEnd = busyTimeFrame.getEnd();
		
		if(ourStart.before(busyStart) && (ourEnd.before(busyEnd) || ourEnd.equals(busyStart))) return true;
		if(ourStart.after(busyEnd) || ourStart.equals(busyEnd)) return true;
		
		return false;
		
	}
}


