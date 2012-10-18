package objectTypes;

import java.awt.Color;
import java.io.Serializable;
import java.util.Date;

public class TimeFrame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3397022225386035533L;
	private Date start;
	private Date end;
	
	
	public TimeFrame(){}
	
	public TimeFrame(String dateString, String startString, String endString) throws IllegalTimeFrameException {
		dateString = dateString.replace('.', '/');
		String[] splittedDateString = dateString.split("/");
		int year = Integer.parseInt(splittedDateString[2]);
		int month = Integer.parseInt(splittedDateString[1]);
		int day = Integer.parseInt(splittedDateString[0]);
		int startHour = Integer.parseInt(startString);
		int endHour = Integer.parseInt(endString);
		
		String dateRegexp = "(0[1-9]|[12][0-9]|3[01]|[1-9])[/.](0[1-9]|1[012]|[1-9])[/.]20\\d\\d";
		String hourRegexp = "(2[0-4]|[01][0-9]|[0-9])";
		
		if (!(dateString.matches(dateRegexp) && startString.matches(hourRegexp) && endString.matches(hourRegexp))) {
			throw new IllegalTimeFrameException("Bad format");
		}
		
		if (startHour >= endHour) throw new IllegalTimeFrameException("Start is after end");
		
		this.start = new Date(year, month, day, startHour, 0);
		this.end = new Date(year, month, day, endHour, 0);
	}
	
	public TimeFrame(int day, int month, int year, int startHour, int endHour) {
//		month = month - 1;
//		year = year - 1900;
		start = new Date(year, month, day, startHour, 0);
		end = new Date(year, month, day, endHour, 0);
	}
	
	
	public TimeFrame (Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	public int getDayOfMonth() {
		return start.getDate();
	}
	
	public int getMonth() {
		return start.getMonth()+1;
	}
	
	public int getYear() {
		return start.getYear()+1900;
	}
	
	public int getStartHour() {
		return start.getHours();
	}
	
	public int getEndHour() {
		return end.getHours();
	}
	
	public TimeFrame(String start, String end) {
		this(getDateFromString(start),getDateFromString(end));
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date slutt) {
		this.end = slutt;
	}
	
	public String getStartHourString() {
		return ""+start.getHours();
	}
	
	public String getEndHourString() {
		return ""+end.getHours();
	}
	
	public String getDateString() {
		int day = start.getDate();
		int month = start.getMonth();
		int year = start.getYear();
		return ""+day+"."+month+"."+year;
	}
	
	public String getStartDateAsSQLString() {
		return getDateAsString(start);
	}
	
	public String getEndDateAsSQLString() {
		return getDateAsString(end);
	}
	
	private static Date getDateFromString(String s) {
		String firstPart = s.split(" ")[0];
		String secondPart = s.split(" ")[1];
		String[] date = firstPart.split("-");
		String[] time = secondPart.split(":");
		
		int year = Integer.parseInt(date[0]);
		int month =  Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);
		int second = Integer.parseInt(time[2].substring(0, time[2].length()-2)); //TODO FIKS!
		
		return new Date(year, month, day, hour, minute, second);
	}
	//2007-05-05 00:00:00
	public String getDateAsString(Date date) {
		Date start = date;
		return start.getYear() + "-" + start.getMonth() + "-" + start.getDate() + " " + start.getHours() +":"+start.getMinutes()+":"+start.getSeconds();
	}
	
//	public boolean isOnSameDay(Date date) {
//		if (start.getDay() != date.getDay())
//			return false;
//		if (start.getMonth() != date.getMonth())
//			return false;
//		if (start.getYear() != date.getYear())
//			return false;
//		return true;
//	}
	
	public boolean isOnSameDay(TimeFrame timeFrame) {
		if (this.getDayOfMonth() != timeFrame.getDayOfMonth())
			return false;
		if (this.getMonth() != timeFrame.getMonth())
			return false;
		if (this.getYear() != timeFrame.getYear())
			return false;
		return true;
	}
	
	public boolean timesFit(TimeFrame ourTimeFrame, TimeFrame busyTimeFrame) {
		Date ourStart = ourTimeFrame.getStart();
		Date ourEnd = ourTimeFrame.getEnd();
		Date busyStart = busyTimeFrame.getStart();
		Date busyEnd = busyTimeFrame.getEnd();
		
		if(ourStart.before(busyStart) && ((ourEnd.before(busyStart) || ourEnd.equals(busyStart)))) return true;
		if(ourStart.after(busyEnd) || ourStart.equals(busyEnd)) return true;
		
		return false;
		
	}
	
}
