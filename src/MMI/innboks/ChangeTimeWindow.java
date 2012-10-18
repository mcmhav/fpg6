package innboks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import objectTypes.*;
import objectTypes.Event;



public class ChangeTimeWindow extends JDialog {
	private JLabel confirmationMessage, newDateLabel, newSTLabel, newETLabel, errorLabel;
	private JTextField newDate, newStartTime, newEndTime;
	private JButton okButton, avbrytButton;
	private Message message;
	private Meeting m;
	private Inbox innboks;
	private final static String dateFieldText = "DD/MM/YYYY";
	private final static String timeFieldText = "HH";
	
	public ChangeTimeWindow(Inbox innboks, Message message){
		super();
		m = message.getMeeting();
		this.innboks = innboks;
		this.message = message;
		initGUI();
	}
	
	public Inbox getInbox(){
		return innboks;
	}
	
	public Message getMessage(){
		return message;
	}
	
	public Meeting getMeeting(){
		return m;
	}
	
	private void initGUI() {
		try{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setPreferredSize(new Dimension(350, 200));
			setTitle("Endre tidspunkt");
			setLayout(new GridBagLayout());
			setLocationRelativeTo(innboks);
			setAlwaysOnTop(true);
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridy = 0;
			c.gridx = 0;
			c.gridwidth = 6;
			c.insets = new Insets(0, 0, 20, 0);
			confirmationMessage = new JLabel("Skriv inn ny dato, starttidspunkt og slutttidspunkt:");
			add(confirmationMessage,c);
			
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0,0,15,0);
			newDateLabel = new JLabel("Dato:");
			add(newDateLabel, c);
			
			c.gridx = 1;
			newDate = new JTextField(dateFieldText);
			newDate.setText(message.getMeeting().getTime().getDateString());
			newDate.setForeground(Color.GRAY);
			newDate.setColumns(8);
			newDate.addFocusListener(new DateFieldListener());
			add(newDate,c);
			
			c.gridx = 2;
			newSTLabel = new JLabel(" Start:");
			add(newSTLabel, c);
			
			c.gridx = 3;
			newStartTime = new JTextField(timeFieldText);
			newStartTime.setText(""+message.getMeeting().getTime().getStartHour());
			newStartTime.setColumns(5);
			newStartTime.setForeground(Color.GRAY);
			newStartTime.addFocusListener(new StartTimeFieldListener());
			add(newStartTime,c);
			
			c.gridx = 4;
			newETLabel = new JLabel(" Slutt:");
			add(newETLabel, c);
			
			c.gridx = 5;
			newEndTime = new JTextField(timeFieldText);
			newEndTime.setText(""+message.getMeeting().getTime().getEndHour());
			newEndTime.setColumns(5);
			newEndTime.setForeground(Color.GRAY);
			newEndTime.addFocusListener(new EndTimeFieldListener());
			add(newEndTime,c);
			
			c.gridy = 2;
			c.gridx = 0;
			c.gridwidth = 3;
			okButton = new JButton("Ok");
			okButton.addActionListener(new ButtonListener());
			add(okButton,c);
			okButton.requestFocus();
				
			c.gridx = 2;
			avbrytButton = new JButton("Avbryt");
			avbrytButton.addActionListener(new ButtonListener());
			add(avbrytButton,c);
			
			c.gridy = 3;
			c.gridwidth = 6;
			c.gridx = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(0,0,5,0);
			errorLabel = new JLabel("Ugyldig input.");
			errorLabel.setForeground(Color.RED);
			errorLabel.setVisible(false);
			add(errorLabel,c);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class DateFieldListener implements FocusListener{
		public void focusGained(FocusEvent arg0) {
			if(newDate.getText().equals(dateFieldText)){
				newDate.setText("");
			}
			else{
				newDate.setSelectionStart(0);
				newDate.setSelectionEnd(newDate.getText().length());
			}
			newDate.setForeground(Color.BLACK);	
		}
		public void focusLost(FocusEvent arg0) {
			if(newDate.getText().equals("")){
				newDate.setText(dateFieldText);
				newDate.setForeground(Color.GRAY);
			}
		}
	}
	
	private class StartTimeFieldListener implements FocusListener{
		public void focusGained(FocusEvent arg0) {
			if(newStartTime.getText().equals(timeFieldText)){
				newStartTime.setText("");
			}
			else{
				newStartTime.setSelectionStart(0);
				newStartTime.setSelectionEnd(newStartTime.getText().length());
			}
			newStartTime.setForeground(Color.BLACK);
		}
		public void focusLost(FocusEvent arg0) {
			if(newStartTime.getText().equals("")){
				newStartTime.setText(timeFieldText);
				newStartTime.setForeground(Color.GRAY);
			}
		}
	}
	
	private class EndTimeFieldListener implements FocusListener{
		public void focusGained(FocusEvent arg0) {
			if(newEndTime.getText().equals(timeFieldText)){
				newEndTime.setText("");
			}
			else{
				newEndTime.setSelectionStart(0);
				newEndTime.setSelectionEnd(newEndTime.getText().length());
			}
			newEndTime.setForeground(Color.BLACK);	
		}
		public void focusLost(FocusEvent arg0) {
			if(newEndTime.getText().equals("")){
				newEndTime.setText(timeFieldText);
				newEndTime.setForeground(Color.GRAY);
			}
		}
	}
	
	public void deleteMessage() {
		try{
			boolean b = innboks.getKalendersystem().getClientService().deleteMessage(Integer.parseInt(message.getId()));
			if(b){
				innboks.getMessageList().setSelectedValue(null, false);
				innboks.getModel().removeElement(message);
				innboks.getMeldingPanel().setModel(null);
				innboks.getUserEvents().removeMessage(message);
				innboks.getUserEvents().removeMessage(message);
				innboks.getKalendersystem().updateNumberOfMessages();
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
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
	private void meetingCrashes(ArrayList<Event> crashingEvents){
		MeetingCrashesWindow mcw = new MeetingCrashesWindow(this, crashingEvents);
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(okButton)){
				try{
					TimeFrame newTimeFrame = new TimeFrame(newDate.getText(), newStartTime.getText(), newEndTime.getText());
					m.setTime(newTimeFrame);
					
					ArrayList<Event> events = innboks.getKalendersystem().getUserEvents().getAllEvents();
					boolean doesMeetingCrash = false;
					ArrayList<Event> crashingEvents = new ArrayList<Event>();
					for(Event event : events){
						if(!timesFit(m.getTime(),event.getTime())){
							doesMeetingCrash = true;
							crashingEvents.add(event);
						}
					}
					if(!doesMeetingCrash){
						innboks.getKalendersystem().getClientService().updateMeeting(m);
						innboks.getKalendersystem().updateEvents();
						deleteMessage();
					}
					else{
						innboks.getKalendersystem().getClientService().updateMeeting(m);
						meetingCrashes(crashingEvents);
					}
					
					dispose();
				}
				catch(Exception e){
					errorLabel.setVisible(true);
				}
				
				
			}
			else if(arg0.getSource().equals(avbrytButton)){
				dispose();
			}
		}
	}
}
