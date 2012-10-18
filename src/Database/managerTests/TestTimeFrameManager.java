package managerTests;

import java.util.ArrayList;
import java.util.Date;

import Managers.TimeFrameManager;
import static org.junit.Assert.assertEquals;
import objectTypes.TimeFrame;

import org.junit.Test;


public class TestTimeFrameManager {
	private TimeFrameManager tfm = new TimeFrameManager();
	
	@Test
	public void testIsAvailable() {
		Date one = new Date(2011, 1, 1, 01, 00, 00);
		Date two = new Date(2011, 1, 1, 02, 00, 00);
		Date three = new Date(2011, 1, 1, 03, 00, 00);
		Date four = new Date(2011, 1, 1, 04, 00, 00);
		Date five = new Date(2011, 1, 1, 05, 00, 00);
		Date six = new Date(2011, 1, 1, 06, 00, 00);
		Date seven = new Date(2011, 1, 1, 07, 00, 00);
		
		TimeFrame oneToTwo = new TimeFrame(one, two);
		TimeFrame oneTothree = new TimeFrame(one, three);
		TimeFrame threeToFour = new TimeFrame(three, four);
		TimeFrame twoToThree = new TimeFrame(two, three);
		TimeFrame fourToFive = new TimeFrame(four, five);
		TimeFrame fourToSix = new TimeFrame(four, six);
		TimeFrame sixToSeven = new TimeFrame(six, seven);
		TimeFrame fiveToSix = new TimeFrame(five,six);
		TimeFrame oneToSeven = new TimeFrame(one, seven);
		
		ArrayList<TimeFrame> busyTimeFrames = new ArrayList<TimeFrame>();
		busyTimeFrames.add(threeToFour);
		busyTimeFrames.add(fourToFive);
		
		
		assertEquals(true, tfm.isAvailable(oneToTwo, busyTimeFrames));
		assertEquals(true, tfm.isAvailable(twoToThree, busyTimeFrames));
		assertEquals(true, tfm.isAvailable(fiveToSix, busyTimeFrames));
		assertEquals(false, tfm.isAvailable(fourToFive, busyTimeFrames));
		assertEquals(false, tfm.isAvailable(oneToSeven, busyTimeFrames));
	}
	

}
