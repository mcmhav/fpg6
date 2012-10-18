package kalendersystem;

import innboks.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clientOS.*;

import objectTypes.*;

public class Kalendersystem extends JFrame {
	private JPanel velgeUkePanel;
	private InnboksPanel innboksPanel;
	private DetailsPanel detaljerPanel;
	private weekCal2 ukp;
	private JLabel headerLabel;
	private JLinkButton refreshLabelButton;
	private JButton sammenlignButton, singleView, logOutButton;
	private GridBagConstraints cc;
	private UserEvents userEvents;
	private UserEvents userEvents2;
	private Kalendersystem ks;
	private int w;
	private ArrayList<Employee> employeeList;
	private Kalendersystem selfReference;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Kalendersystem(Employee user, ArrayList<Employee> emps, ClientService clientService) {
		super();
		this.userEvents = new UserEvents(user, true, clientService);
		this.ks = this;
		this.employeeList = emps;
		initGUI();
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
		selfReference = this;
	}
	
	public void reload(){
		userEvents = new UserEvents(userEvents.getUser(), true, userEvents.getClientService());
		if (userEvents2 != null)
			userEvents2 = new UserEvents(userEvents2.getUser(), false, userEvents2.getClientService());
	}
	
	public ArrayList<Employee> getAllEmployees() {
		return employeeList;
	}

	public void updateEvents() {
		userEvents.loadInfo(true);	//true? laste inn meldinger og?
		makeNewWeekSingle();
	}

	public void addEventToSystem(Event e) {
		int eid = (Integer) userEvents.getClientService().getClientOS().sendObjectAndGetResponse(e);
		e.setId(eid);
		userEvents.addEvent(e);
	}

	class UkesValgPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel ukeTekstLabel;
		private JLinkButton backLB, prevPrevLB, prevLB, currentLB, nextLB, nextNextLB, forwardLB;

		UkesValgPanel() {
			super();
			initGUI();
		}

		private int getChosenWeekMinus(int i){
			int week = ukp.getChoosenWeekReal();
			if(week - i == 0){
				return 52;
			}
			if(week - i == -1){
				return 51;
			}
			return week - i;
		}

		private int getChosenWeekPlus(int i){
			int week = ukp.getChoosenWeekReal();
			if(week + i == 53){
				return 1;
			}
			if(week + i == 54){
				return 2;
			}
			return week + i;
		}

		void initGUI() {
			try {		
				setPreferredSize(new Dimension(500, 30));
				setLayout(new GridBagLayout());
				{
					ukeTekstLabel = new JLabel("Uke:");
					add(ukeTekstLabel);
				}
				{
					backLB = new JLinkButton("<");
					backLB.addActionListener(new ChooseWeekAL(-1));
					add(backLB);
				}
				{
					prevPrevLB = new JLinkButton(""+getChosenWeekMinus(2));
					prevPrevLB.addActionListener(new ChooseWeekAL(-2));
					add(prevPrevLB);
				}
				{
					prevLB = new JLinkButton(""+getChosenWeekMinus(1));
					prevLB.addActionListener(new ChooseWeekAL(-1));
					prevLB.setFont(new Font("sansserif", Font.BOLD, 15));
					add(prevLB);
				}
				{
					currentLB = new JLinkButton(""+ukp.getChoosenWeekReal());
					currentLB.addActionListener(new ChooseWeekAL(0));
					currentLB.setFont(new Font("sansserif", Font.BOLD, 20));
					add(currentLB);
				}
				{
					nextLB = new JLinkButton(""+getChosenWeekPlus(1));
					nextLB.addActionListener(new ChooseWeekAL(+1));
					nextLB.setFont(new Font("sansserif", Font.BOLD, 15));
					add(nextLB);
				}
				{
					nextNextLB = new JLinkButton(""+getChosenWeekPlus(2));
					nextNextLB.addActionListener(new ChooseWeekAL(+2));
					add(nextNextLB);
				}
				{
					forwardLB = new JLinkButton(">");
					forwardLB.addActionListener(new ChooseWeekAL(1));
					add(forwardLB);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class InnboksPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel iconLabel;
		private JLinkButton nyeMeldingerLB;

		InnboksPanel() {
			super();
			initGUI();
		}

		void initGUI() {
			try {
				setPreferredSize(new Dimension(200, 30));
				setLayout(new GridBagLayout());
				{
					iconLabel = new JLabel();
					ImageIcon img = new ImageIcon(getClass().getResource("mailicon.png"));
					iconLabel.setIcon(img);
					add(iconLabel);
				}
				{
					nyeMeldingerLB = new JLinkButton(userEvents.getMessages().size() + " nye meldinger");
					nyeMeldingerLB.addActionListener(new inboxLBListener());
					add(nyeMeldingerLB);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void setButtonText(){
			nyeMeldingerLB.setText(userEvents.getMessages().size() + " nye meldinger");
		}
	}

	private void initGUI() {
		try {
			setResizable(false);
			setTitle(userEvents.getUser().getName() + "s kalender");
			//			setPreferredSize(new Dimension(1500, 1000));
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			setLayout(new GridBagLayout());
			{
				headerLabel = new JLabel("Kalendersystem");
				headerLabel.setFont(new Font("arial", Font.BOLD, 16));
				c.gridx = 0;
				c.gridy = 0;
				c.anchor = GridBagConstraints.WEST;
				getContentPane().add(headerLabel, c);
			}
			{
				innboksPanel = new InnboksPanel();
				//				innboksPanel.setPreferredSize(new Dimension(200, 30));
				//				innboksPanel.setBackground(Color.RED);
				c.anchor = GridBagConstraints.WEST;
				c.gridx = 2;
				c.gridy = 0;
				getContentPane().add(innboksPanel, c);
			}
			{
				c.insets = new Insets(8, 2, 8, 2);
				ukp = new weekCal2(0);
				ukp.getWeek().getSelectionModel().addListSelectionListener(new ListSL());
				ukp.getWeek().getColumnModel().getSelectionModel().addListSelectionListener(new ListSL());
				ukp.setEvents(userEvents);
				c.gridx = 0;
				c.gridwidth = 2;
				c.gridy = 1;
				getContentPane().add(ukp, c);
				cc=c;
				c.gridwidth = 1;
			}
			{
				velgeUkePanel = new UkesValgPanel();
				//				velgeUkePanel.setPreferredSize(new Dimension(500, 30));
				//				velgeUkePanel.setBackground(Color.GREEN);
				c.gridx = 0;
				c.gridy = 2;
				c.anchor = GridBagConstraints.WEST;
				getContentPane().add(velgeUkePanel, c);

			}
			{
				sammenlignButton = new JButton("Sammenlign kalendere");
				sammenlignButton.addActionListener(new sammenlignAL());
				c.gridx = 1;
				c.gridy = 2;
				c.anchor = GridBagConstraints.EAST;
				getContentPane().add(sammenlignButton, c);
			}
			singleView = new JButton("Enkel Kalender");
			singleView.addActionListener(new simpleAL());
			singleView.setEnabled(false);
			c.gridx = 1;
			c.gridy = 2;
			c.anchor = GridBagConstraints.CENTER;
			getContentPane().add(singleView, c);
			{
				detaljerPanel = new DetailsPanel(this);
//				detaljerPanel.getbekreftButton().addActionListener(new Merge());
				//				detaljerPanel.setPreferredSize(new Dimension(320, 400));
				//				detaljerPanel.setBackground(Color.CYAN);
				c.gridx = 2;
				c.gridy = 1;
				getContentPane().add(detaljerPanel, c);
			}
			{
				c.gridx = 2;
				c.gridy = 2;
				c.anchor = GridBagConstraints.EAST;
				c.insets = new Insets(0, 0, 0, 103);
				refreshLabelButton = new JLinkButton("");
				refreshLabelButton.addActionListener(new RefreshButtonListener());
				refreshLabelButton.setIcon(new ImageIcon(getClass().getResource("refreshicon.jpg")));
				getContentPane().add(refreshLabelButton, c);
			}
			{
				c.gridx = 2;
				c.gridy = 2;
				c.insets = new Insets(0, 0, 0, 22);
				logOutButton = new JButton("Logg ut");
				c.anchor = GridBagConstraints.EAST;
				logOutButton.addActionListener(new logOutAL());
				getContentPane().add(logOutButton, c);
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void makeNewWeekSingle(){
		int w = ukp.getChoosenWeek();
		getContentPane().remove(ukp);
		ukp = new weekCal2(w);
		ukp.setEvents(userEvents);
		ukp.getWeek().getSelectionModel().addListSelectionListener(new ListSL());
		ukp.getWeek().getColumnModel().getSelectionModel().addListSelectionListener(new ListSL());
		cc.gridx = 0;
		cc.gridwidth = 2;
		cc.gridy = 1;
		getContentPane().add(ukp,cc);
		pack();
	}
	
	private class logOutAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			LogOutConfirmationFrame lc = new LogOutConfirmationFrame(ks);
		}
	}
	
	private class RefreshButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			reload();
			makeNewWeekSingle();
			updateNumberOfMessages();
		}
	}
	
	private class simpleAL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int w = ukp.getChoosenWeek();
			getContentPane().remove(ukp);
			ukp = new weekCal2(w);
			ukp.setEvents(userEvents);
			ukp.getWeek().getSelectionModel().addListSelectionListener(new ListSL());
			ukp.getWeek().getColumnModel().getSelectionModel().addListSelectionListener(new ListSL());
			cc.gridx = 0;
			cc.gridwidth = 2;
			cc.gridy = 1;
			getContentPane().add(ukp,cc);
			pack();
			
			singleView.setEnabled(false);
		}
		
	}
	public ClientService getClientService(){
		return userEvents.getClientService();
	}

	public ClientOS getClientOS() {
		return userEvents.getClientService().getClientOS();
	}

	public UserEvents getUserEvents(){
		return userEvents;
	}

	public void updateNumberOfMessages(){
		innboksPanel.setButtonText();
	}

	public weekCal2 getWeekcal(){
		return ukp;
	}
	
	private class inboxLBListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//ArrayList<Message> m = new ArrayList<Message>();
			//m.add(new Message("1", MessageType.INVITATIONMESSAGE, "hei", new Employee("ad","aawd","dwad"),new Employee("ad","aawd","dwad"), new Meeting("Budsjettm\u00F8te", null, null, null, null, null, null)));
			//m.add(new Message("2", MessageType.DECLINEMESSAGE,"Arne Arnesen har meldt avbud p\u00E5 Dugn\u00E6d",new Employee("Arnearn","passord","Arne Arnesen"),new Employee("ad","aawd","dwad"), new Meeting("dugn\u00E6d", null, null, null, null, null, null)));
			//m.add(new Message("3", "Referat",MessageType.INFORMATIONMESSAGE,"hade",new Employee("ad","aawd","dwad"),new Employee("ad","aawd","dwad")));
			Inbox ib = new Inbox(ks); 
			ib.setVisible(true);
			ib.setLocationRelativeTo(null);
		}
	}

	private class sammenlignAL implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new SammenlignKalendere(selfReference);
			//			sk.getOk().addActionListener(new Sammenlign(new Employee("anakinsky", "thedarkside", "Anakin Skywalker")));
		}
	}

	public void compareCalendars(Employee employee) {
		int w = ukp.getChoosenWeek();
		getContentPane().remove(ukp);
		ukp = new weekCal2(w, 2);
		ukp.getWeek().getSelectionModel().addListSelectionListener(new ListSL());
		ukp.getWeek().getColumnModel().getSelectionModel().addListSelectionListener(new ListSL());
		cc.gridx = 0;
		cc.gridwidth = 2;
		cc.gridy = 1;
		getContentPane().add(ukp,cc);
		pack();

		userEvents2 = new UserEvents(employee, false, userEvents.getClientService());

		ukp.setEventDouble(userEvents,userEvents2);
		singleView.setEnabled(true);
		pack();
	}

	private Event getEventOnStartTime(ArrayList<Event> events, int start){
		for(Event event : events){
			int startTime = event.getTime().getStartHour();
			if(startTime == start){
				return event;
			}
		}
		return null;
	}

	private class ListSL implements ListSelectionListener{	
		

		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			if(ukp.isTwo()){
				String stringDate = ukp.getDatesd()[ukp.getChosenDate()];
				int startTime = ukp.getStartTime();
				String[] times = stringDate.split("/");
				int day = Integer.parseInt(times[0]);
				int month = Integer.parseInt(times[1]);
				int year = Integer.parseInt(times[2]);
				TimeFrame date = new TimeFrame(day, month, year, 0, 0);
	//			int endTime = 0;
				ArrayList<Event> eventsOnDay = new ArrayList<Event>();
				ArrayList<Event> eventsOnDay2 = new ArrayList<Event>();
				eventsOnDay.addAll(userEvents.getEventsOnDay(date));
				eventsOnDay2.addAll(userEvents2.getEventsOnDay(date));
				Event event = getEventOnStartTime(eventsOnDay, startTime);
				Event event2 = getEventOnStartTime(eventsOnDay2, startTime);
				if(event != null){
					detaljerPanel.setDisplayedEvent(event);
					
				}else if(event2 != null){
					detaljerPanel.setDisplayedEvent(event2);
				}else{
					detaljerPanel.setDisplayedEvent(event);
					int end = ukp.getEndTime();
//					if(ukp.getEndTime()==24){
//						end = 0;
//					}
					detaljerPanel.setDate(startTime, end, stringDate);
				}
			}else{
				String stringDate = ukp.getDates()[ukp.getChosenDate()];
				int startTime = ukp.getStartTime();
				String[] times = stringDate.split("/");
				int day = Integer.parseInt(times[0]);
				int month = Integer.parseInt(times[1]);
				int year = Integer.parseInt(times[2]);
				TimeFrame date = new TimeFrame(day, month, year, 0, 0);
	//			int endTime = 0;
				ArrayList<Event> eventsOnDay = new ArrayList<Event>();
				eventsOnDay.addAll(userEvents.getEventsOnDay(date));
				Event event = getEventOnStartTime(eventsOnDay, startTime);
				if(event != null){
					detaljerPanel.setDisplayedEvent(event);
					ukp.setOneSelect();
				}else{
					ukp.setMultipleSelect();
					detaljerPanel.setDisplayedEvent(event);
					int end = ukp.getEndTime();
//					if(ukp.getEndTime()==24){
//						end = 0;
//					}
					detaljerPanel.setDate(startTime, end, stringDate);
				}
			}
		}

	}

	private class ChooseWeekAL implements ActionListener{

		int we;

		public ChooseWeekAL(int a){
			we=a;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			w = ukp.getChoosenWeek()+we;
			getContentPane().remove(ukp);
			if(ukp.isTwo()){
				ukp = new weekCal2(w,2);
				userEvents2 = new UserEvents(userEvents2.getUser(), false, userEvents.getClientService());
				ukp.setEventDouble(userEvents, userEvents2);
			}else{
				ukp = new weekCal2(w);
				ukp.setEvents(userEvents);
			}
			//			ukp = new weekCal2(w);
			//			ukp.setEvents(userEvents);

			ukp.getWeek().getSelectionModel().addListSelectionListener(new ListSL());
			ukp.getWeek().getColumnModel().getSelectionModel().addListSelectionListener(new ListSL());
			cc.gridx = 0;
			cc.gridwidth = 2;
			cc.gridy = 1;
			getContentPane().add(ukp,cc);
			pack();

			getContentPane().remove(velgeUkePanel);
			velgeUkePanel = new UkesValgPanel();
			cc.gridx = 0;
			cc.gridy = 2;
			cc.anchor = GridBagConstraints.WEST;
			getContentPane().add(velgeUkePanel, cc);


			pack();
		}

	}

	private class Merge implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int[] columns = {ukp.getcx()};
			int a = ukp.getEndTime();
			ukp.getCellAtt().combine(ukp.getRows(), columns);
			ukp.getWeek().setValueAt((""+ukp.getStartTime()+"-"+a), ukp.getStartTime(), ukp.getcx());
			ukp.getWeek().clearSelection();
			ukp.getWeek().revalidate();
			ukp.getWeek().repaint();
		}
	}
}
