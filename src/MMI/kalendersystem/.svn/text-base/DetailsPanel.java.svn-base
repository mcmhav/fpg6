package kalendersystem;


import innboks.MeetingCrashesWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.IllegalTimeFrameException;
import objectTypes.Meeting;
import objectTypes.Room;
import objectTypes.TimeFrame;

public class DetailsPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newEventButton, deleteButton, reserveRoomButton, addParticipantsButton, confirmButton;
	private JTextField dateField, startTimeField, endTimeField, placeField, titleField;
	private JTextArea descriptionArea;
	private JList participantList;
	private JScrollPane descriptionScroll, participantScroll;
	private JLabel titleLabel, dateLabel, startTimeLabel, endTimeLabel, placeLabel, descriptionLabel, participantsLabel, userMessageLabel;
	private Event displayedEvent;
	private Employee user;
	private Room reservedRoom = null;
	private boolean nocrash;
	
	private DetailsPanel selfReference;
	private Kalendersystem calendarSystem;
	
	private static final String defaultDateText = "dd.mm.yyyy";
	private static final String defaultStartText = "hh";
	private static final String defaultEndText = "hh";
	
	private static final String eventSavedMessage = "Oppf\u00F8ring lagret.";
	private static final String wrongTimeFormatMessage = "Feilformatert dato.";
	private static final String fillInFieldsMessage = "Fyll inn feltene.";
	private static final String noTitleMessage = "Fyll inn en tittel.";
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new DetailsPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void alertUser(String string, Color color) {
		userMessageLabel.setText(string);
		userMessageLabel.setVisible(true);
		userMessageLabel.requestFocus();
		userMessageLabel.setForeground(color);
	}
	
	public void setReservedRoom(Room room) {
		this.reservedRoom = room;
		placeField.setText(reservedRoom.getName());
		placeField.setEditable(false);
		//smilefjes
	}
	
	public String getEventSavedMessage(){
		return eventSavedMessage;
	}
	
	public void setUser(Employee user) {
		this.user = user;
	}
	
	public Employee getUser() {
		return this.user;
	}
	
	public DetailsPanel() {
		super();
		initGUI();
		selfReference = this;
	}
	
	public DetailsPanel(Kalendersystem calendarSystem) {
		super();
		initGUI();
		this.calendarSystem = calendarSystem;
		selfReference = this;
	}
	
	public void addParticipants(ArrayList<Employee> participants) {
		DefaultListModel dfm = new DefaultListModel();
		Iterator<Employee> it = participants.iterator();
		while (it.hasNext()) {
			dfm.addElement(it.next());
		}
		participantList.setModel(dfm);
	}
	
	public Event getDisplayedEvent(){
		return displayedEvent;
	}
	
	private void setDefaultValues() {
		titleField.setText("");
		dateField.setText(defaultDateText);
		startTimeField.setText(defaultStartText);
		endTimeField.setText(defaultEndText);
		descriptionArea.setText("");
		placeField.setText("");
		reservedRoom = null;
		participantList.setModel(new DefaultListModel());
		
		
		dateField.setForeground(Color.GRAY);
		startTimeField.setForeground(Color.GRAY);
		endTimeField.setForeground(Color.GRAY);
	}
	
	public void setDate(int startHour, int endHour, String date) {
		startTimeField.setText(""+startHour);
		endTimeField.setText(""+endHour);
		dateField.setText(date);
	}
	
	private void enableFields() {
		titleField.setEditable(true);
		dateField.setEditable(true);
		startTimeField.setEditable(true);
		endTimeField.setEditable(true);
		descriptionArea.setEditable(true);
		participantList.setEnabled(true);
		placeField.setEditable(true);
	}
	
	private void disableButtons() {
		deleteButton.setEnabled(false);
		confirmButton.setEnabled(false);
		reserveRoomButton.setEnabled(false);
		addParticipantsButton.setEnabled(false);
	}
	
	private void enableButtons() {
		deleteButton.setEnabled(true);
		confirmButton.setEnabled(true);
		reserveRoomButton.setEnabled(true);
		addParticipantsButton.setEnabled(true);
	}
	
	private void disableFields() {
		titleField.setEditable(false);
		dateField.setEditable(false);
		startTimeField.setEditable(false);
		endTimeField.setEditable(false);
		descriptionArea.setEditable(false);
		participantList.setEnabled(false);
		placeField.setEditable(false);
	}
	
	public void setDisplayedEvent(Event e) {
		if (e == null) {
			enableFields();
			enableButtons();
//			setDisplayedEvent(null);
			displayedEvent = null;
			setDefaultValues();
			alertUser(fillInFieldsMessage, Color.BLACK);
			deleteButton.setEnabled(false);
			return;
		}
		if(e instanceof Meeting){
			if (!e.getCreator().getName().equals(getCalendarSystem().getUserEvents().getUser().getName())) {
				disableFields();
				disableButtons();
				displayedEvent = e;
				deleteButton.setEnabled(true);
			} else {
				enableFields();
				enableButtons();
				displayedEvent = e;
				addParticipantsButton.setEnabled(false);
			}
		}else{
			enableFields();
			enableButtons();
			addParticipantsButton.setEnabled(false);
			displayedEvent = e;
		}
		setValuesAccordingToEvent(e);
	}
	
	private void setValuesAccordingToEvent(Event e) {
		titleField.setText(e.getTitle());
		dateField.setText(e.getTime().getDateString());
		startTimeField.setText(e.getTime().getStartHourString());
		endTimeField.setText(e.getTime().getEndHourString());
		descriptionArea.setText(e.getComment());
		descriptionArea.setEditable(true);
		if(e.getRoom() != null){
			placeField.setText(""+e.getRoom());
		}
		else if(e.getPlace() != null){
			placeField.setText(e.getPlace());
		}
		if(e instanceof Meeting){
			addParticipants(((Meeting) e).getParticipants());
		}
		
		
		
		
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(320, 450));
			setLayout(new GridBagLayout());
			Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			setBorder(BorderFactory.createTitledBorder(border, "Detaljer"));
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(0, 2, 8, 2);
			c.anchor = GridBagConstraints.WEST;
			TimeFieldListener tfl = new TimeFieldListener();
			{
				newEventButton = new JButton();
				c.gridx = 1;
				c.gridy = 1;
				c.gridwidth = 3;
				add(newEventButton, c);
				newEventButton.setText("Ny oppf\u00F8ring");
				newEventButton.addActionListener(new NyOppforingButtonListener());
				c.gridwidth = 1;
			}
			{
				deleteButton = new JButton("Slett");
				deleteButton.addActionListener(new SlettButtonListener());
				c.gridx = 5;
				c.gridy = 1;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.EAST;
				add(deleteButton, c);
				c.anchor = GridBagConstraints.WEST;
				c.gridwidth = 1;
				deleteButton.setEnabled(false);
			}
			{	
				c.insets = new Insets(8, 2, 2, 2);
				titleLabel = new JLabel("Tittel: ");
				c.gridx = 1;
				c.gridy = 2;
				add(titleLabel, c);
			}
			{
				titleField = new JTextField();
				titleField.setColumns(21);
				c.gridx = 2;
				c.gridwidth = 5;
				c.gridy = 2;
				add(titleField, c);
				c.gridwidth = 1;
			}
			{
				c.insets = new Insets(2, 2, 2, 2);
				dateLabel = new JLabel();
				c.gridx = 1;
				c.gridy = 3;
				add(dateLabel, c);
				dateLabel.setText("Dato: ");
			}
			{
				dateField = new JTextField(4);
				dateField.setColumns(6);
				c.gridx = 2;
				c.gridy = 3;
				dateField.addFocusListener(tfl);
				add(dateField, c);
			}
			{
				startTimeLabel = new JLabel("Start: ");
				c.gridx = 3;
				c.gridy = 3;
				add(startTimeLabel, c);
			}
			{
				startTimeField = new JTextField(4);
				startTimeField.setColumns(2);
				c.gridx = 4;
				c.gridy = 3;
				startTimeField.addFocusListener(tfl);
				add(startTimeField, c);
			}
			{
				endTimeLabel = new JLabel("Slutt: ");
				c.gridx = 5;
				c.gridy = 3;
				add(endTimeLabel, c);
			}
			{
				endTimeField = new JTextField();
				endTimeField.setColumns(2);
				c.gridx = 6;
				c.gridy = 3;
				endTimeField.addFocusListener(tfl);
				add(endTimeField, c);
			}
			{
				c.insets = new Insets(10, 2, 0, 2);
				descriptionLabel = new JLabel("Beskrivelse: ");
				c.gridwidth = 2;
				c.gridx = 1;
				c.gridy = 4;
				add(descriptionLabel, c);
				c.gridwidth = 1;
			}
			{
				c.insets = new Insets(2, 2, 12, 2);
				descriptionArea = new JTextArea(5, 25);
				descriptionScroll = new JScrollPane(descriptionArea);
				c.gridx = 1;
				c.gridwidth = 6; 
				c.gridy = 5;
				add(descriptionScroll, c);
				c.gridwidth = 1;
			}
			{
				placeLabel = new JLabel("Sted: ");
				c.gridx = 1;
				c.gridy = 6;
				add(placeLabel, c);
			}
			{
				placeField = new JTextField(10);
				c.gridx = 2;
				c.gridy = 6;
				c.gridwidth = 3;
				add(placeField, c);
				c.gridwidth = 1;
				
			}
			{
				reserveRoomButton = new JButton("Reserver rom");
				reserveRoomButton.addActionListener(new ReserveRoomListener());
				c.gridx = 3;
				c.gridy = 6;
				c.gridwidth = 4;
				c.anchor = GridBagConstraints.EAST;
				add(reserveRoomButton, c);
				c.anchor = GridBagConstraints.WEST;
				c.gridwidth = 1;
			}
			{
				c.insets = new Insets(20, 2, 0, 2);
				participantsLabel = new JLabel("Deltagere: ");
				c.gridwidth = 2;
				c.gridx = 1;
				c.gridy = 7;
				add(participantsLabel, c);
				c.gridwidth = 1;
			}
			{
				c.insets = new Insets(8, 2, 2, 2);
				addParticipantsButton = new JButton("Legg til deltagere");
				addParticipantsButton.addActionListener(new addParticipantsListener());
				c.gridwidth = 4;
				c.gridx = 3;
				c.gridy = 7;
				c.anchor = GridBagConstraints.EAST;
				add(addParticipantsButton, c);
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
			}
			{
				c.insets = new Insets(2, 2, 6, 2);
				participantList = new JList(new DefaultListModel());
				participantScroll = new JScrollPane(participantList);
				participantScroll.setPreferredSize(new Dimension(280, 90));
				participantList.setCellRenderer(new ParticipantListRenderer());
				c.gridwidth = 6;
				c.gridx = 1;
				c.gridy = 8;
				add(participantScroll, c);
				c.gridwidth = 1;
			}
			{
				userMessageLabel = new JLabel("");
				userMessageLabel.setForeground(Color.RED);
				userMessageLabel.setFocusable(true);
				userMessageLabel.addFocusListener(new UserMessageLabelListener());
				c.gridwidth = 3;
				c.gridx = 1;
				c.gridy = 9;
				add(userMessageLabel, c);
				c.gridwidth = 1;
			}
			{
				confirmButton = new JButton("Bekreft");
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.EAST;
				c.gridx = 5;
				c.gridy = 9;
				add(confirmButton, c);
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				confirmButton.addActionListener(new ConfirmButtonListener());
			}
			setDefaultValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkFields() {
		String titleString = titleField.getText();
		if (titleString.equals("")) {
			alertUser(noTitleMessage, Color.RED);
			return false;
		}
		return true;
	}
	
	private TimeFrame getTimeFrameFromFields() throws IllegalTimeFrameException {
		String datestring = dateField.getText();
		String startstring = startTimeField.getText();
		String endstring = endTimeField.getText();
		TimeFrame returnedTimeFrame;
		try {
			returnedTimeFrame = new TimeFrame(datestring, startstring, endstring);
			return returnedTimeFrame;
		} catch (IllegalTimeFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		datestring = datestring.replace('.', '/');
//		String[] splittedDate = datestring.split("/");
//		int day = Integer.parseInt(splittedDate[0]);
//		int month = Integer.parseInt(splittedDate[1])-1;
//		int year = Integer.parseInt(splittedDate[2])-1900;
//		System.out.println(""+day+"."+month+"."+year);
//		return (new TimeFrame(new Date(year, month, day, Integer.parseInt(startstring), 0), 
//				new Date(year, month, day, Integer.parseInt(endstring), 0)));
		return null;
		
	}
	
	private class TimeFieldListener implements FocusListener {
		private String getDefaultText(JTextField source) {
			if (source.equals(dateField))
				return defaultDateText;
			else if (source.equals(startTimeField))
				return defaultStartText;
			else if (source.equals(endTimeField))
				return defaultEndText;
			return "";
		}
		
		public void focusGained(FocusEvent e) {
			String text = getDefaultText((JTextField) e.getSource());
			
			if(((JTextField) e.getSource()).getText().equals(text)){
				((JTextField) e.getSource()).setText("");
			}
			else{
				((JTextField) e.getSource()).setSelectionStart(0);
				((JTextField) e.getSource()).setSelectionEnd(((JTextField) e.getSource()).getText().length());
			}
			((JTextField) e.getSource()).setForeground(Color.BLACK);
		}
		public void focusLost(FocusEvent e) {
			String text = getDefaultText((JTextField) e.getSource());
			
			if(((JTextField) e.getSource()).getText().equals("")){
				((JTextField) e.getSource()).setText(text);
				((JTextField) e.getSource()).setForeground(Color.GRAY);
			}
		}
	}
	
	private class ReserveRoomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			try {
				TimeFrame tf = getTimeFrameFromFields();
				new ReserveRoom(selfReference, tf);
			} catch (Exception e) {
				alertUser(wrongTimeFormatMessage, Color.RED);
			}
		}
	}
	
	private class addParticipantsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			new AddParticipants(selfReference);
		}
	}
	
	private class UserMessageLabelListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {}
		
		@Override
		public void focusLost(FocusEvent e) {
			((JLabel) e.getSource()).setVisible(false);
		}
	}
	
	private class SlettButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			if (displayedEvent == null){
				return;
			}
//			calendarSystem.getClientService().deleteEventOrMeeting(displayedEvent, user);
			if(calendarSystem.getWeekcal().isTwo()&&calendarSystem.getWeekcal().getcx()%2==0){
				calendarSystem.getUserEvents().removeEvent(displayedEvent);
			}else{
				calendarSystem.getUserEvents().removeEvent(displayedEvent);
			}
			setDisplayedEvent(null);
			setDefaultValues();
			calendarSystem.makeNewWeekSingle();
			//slettFraDatabasen(displayedEvent);	//metoden m� ogs� hvis m�te sende meldinger osv
		}
	}
	
	private class NyOppforingButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			setDisplayedEvent(null);
			setDefaultValues();
			alertUser(fillInFieldsMessage, Color.BLACK);
		}
	}
	
	public JButton getbekreftButton(){
		return confirmButton;
	}
	
	private class ConfirmButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			if (!checkFields())
				return;
			TimeFrame timeFrame;
			try {
				timeFrame = getTimeFrameFromFields();
			}
			catch (IllegalTimeFrameException e) {
				alertUser(wrongTimeFormatMessage, Color.RED);
				return;
			}
			Event event;
			if (participantList.getModel().getSize() == 0) {
				event = new Event();
			} else {
				event = new Meeting();
				ArrayList<Employee> list = new ArrayList<Employee>();
				for (int i = 0; i < participantList.getModel().getSize(); i++) {
					list.add((Employee) participantList.getModel().getElementAt(i));
				}
				((Meeting) event).setParticipants(list);
				calendarSystem.updateEvents();
				//tiden sendes helt riktig til databasen
			}
			event.setTitle(titleField.getText());
			event.setTime(timeFrame);
			event.setCreator(calendarSystem.getUserEvents().getUser());
			event.setComment(descriptionArea.getText());
			if (reservedRoom == null) {
				event.setPlace(placeField.getText());
			} else {
				event.setRoom(reservedRoom);
			}
			ArrayList<Event> events = calendarSystem.getUserEvents().getAllEvents();
			boolean doesMeetingCrash = false;
			ArrayList<Event> crashingEvents = new ArrayList<Event>();
			nocrash = true;
			for(Event e : events){
				if(!event.getTime().timesFit(event.getTime(),e.getTime())){
					doesMeetingCrash = true;
					crashingEvents.add(e);
					if(e.equals(displayedEvent)){
						nocrash = false;
					}
				}
			}
			if(doesMeetingCrash){
				MeetingCrashesWindow mcw = new MeetingCrashesWindow(selfReference, crashingEvents, event);
			}else if(displayedEvent!=null){
				if(calendarSystem.getWeekcal().isTwo()&&calendarSystem.getWeekcal().getcx()%2==0){
					calendarSystem.getUserEvents().removeEvent(displayedEvent);
				}else{
					calendarSystem.getUserEvents().removeEvent(displayedEvent);
				}
//				setDisplayedEvent(null);
//				setDefaultValues();
				calendarSystem.updateEvents();
				alertUser(eventSavedMessage, new Color(0,130,0));
				calendarSystem.addEventToSystem(event);
				calendarSystem.updateEvents();
			}else{
				alertUser(eventSavedMessage, new Color(0,130,0));
				calendarSystem.addEventToSystem(event);
				calendarSystem.updateEvents();
			}
		}
	}
	
	
	public boolean getNcrash(){
		return nocrash;
	}

	public Kalendersystem getCalendarSystem() {
		return calendarSystem;
	}
}
