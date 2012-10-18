package managerTests;
import static org.junit.Assert.assertEquals;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Room;
import objectTypes.TimeFrame;
import org.junit.Test;
import connection.DbConnection;
import Managers.MeetingManager;
public class MeetingManagerTest {

	private DbConnection c = new DbConnection("10.0.0.107", "Fellesprosjekt", "gruppe6", "entotre");
	private MeetingManager meetingManager = new MeetingManager(c);
	private Employee emil = new Employee("emiltho", "emilPassord", "Emil Thobroe");
	private Employee ola = new Employee("olabor", "olaPassord", "Ola Borten");
	private Employee kari = new Employee("karisve", "kariPassord", "Kari Svendssen");
	private Employee truls = new Employee("trulspet", "trulsPassord", "Truls Pettersen");
	private Room moterom1 = new Room("Møterom 1", "pt 7");
	private Room moterom2 = new Room("Møterom 2", "pt 3");
	private Event bilTilVerksted = new Event(1,"Bil til verksted", null, emil, null, "Knuts verksted", "Eu-godkjenning");
	private Meeting kriseMote = getKriseMote();
	
	@Test
	public void testInsertMeetingAndDeleteMeeting() {
		Date start = new java.sql.Date(new java.util.Date().getTime());
		Date end = new java.sql.Date(new java.util.Date().getTime());
		TimeFrame time = new TimeFrame(start, end);
		Employee creator = new Employee("emiltho", "emilPassord", "Emil Thobroe");
		Employee e1 = new Employee("olabor", "olaPassord", "Ola Borten");
		Employee e2 = new Employee("karisve", "kariPassord", "Kari Svendssen");
		Employee e3 = new Employee("trulspet", "trulsPassord", "Truls Pettersen");
		ArrayList<Employee> participants = new ArrayList<Employee>();
		Room room = new Room("P15", "Flott");
		participants.add(e1);
		participants.add(e2);
		participants.add(e3);

		Meeting meeting = new Meeting("Feil", time, creator, room, "NTNT", "VIKTIG", participants);
		meetingManager.insertObject(meeting);
		meetingManager.deleteObject(meeting);
	}
	
	@Test
	public void testGetAllMeetings() {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		meetings.add(kriseMote);
		assertEquals(meetings.get(0).getTitle(), meetingManager.getAllMeetings(kari).get(0).getTitle());
	}
	
	@Test
	public void testInsertAndDeleteEvent() {
		
	}
	
	@Test
	public void testGetEvent() {
		
	}
	
	private Meeting getKriseMote() {
		ArrayList<Employee> participants = new ArrayList<Employee>();
		participants.add(kari);
		participants.add(truls);
		Meeting m = new Meeting(1, "Krisemøte", null, ola, moterom1, "Trondheim", "krise", participants);
		return m;
	}
}
