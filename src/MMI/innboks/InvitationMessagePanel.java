package innboks;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import objectTypes.Event;
import objectTypes.Message;
import objectTypes.TimeFrame;

public class InvitationMessagePanel extends javax.swing.JPanel {
	private JLabel tittelLabel, beskrivelseLabel;
	private JTextField tittelFelt;
	private JTextArea beskrivelsesFelt;
	private JButton godtaKnapp, avslaaKnapp;
	private Inbox innboks;
	private Message message;
	private MessagePanel messagePanel;

	public InvitationMessagePanel(Message message, Inbox innboks) {
		super();
		this.message = message;
		this.innboks = innboks;
		this.messagePanel = innboks.getMeldingPanel();
		initGUI();
		TimeFrame tf = message.getMeeting().getTime();
		beskrivelsesFelt.setText(message.getDescription()+"\nDen "+tf.getDateString()+" kl "+tf.getStartHourString()+"-"+tf.getEndHourString());
	}
	
	public Inbox getInbox(){
		return innboks;
	}
	
	public Message getMessage(){
		return message;
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(250, 450));
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			{
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.WEST;
				tittelLabel = new JLabel("Tittel:");
				add(tittelLabel, c);
			}
			{
				
				c.anchor = GridBagConstraints.EAST;
				tittelFelt = new JTextField(message.getName());
				tittelFelt.setEditable(false);
				tittelFelt.setColumns(18);
				add(tittelFelt,c);
			}
			{
				c.gridx = 0;
				c.gridy = 1;
				c.anchor = GridBagConstraints.WEST;
				beskrivelseLabel = new JLabel("Beskrivelse:");
				add(beskrivelseLabel,c);
			}
			{
				c.gridy = 2;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.WEST;
				beskrivelsesFelt = new JTextArea(message.getDescription());
				beskrivelsesFelt.setLineWrap(true);
				beskrivelsesFelt.setEditable(false);
				JScrollPane sp = new JScrollPane(beskrivelsesFelt);
				sp.setPreferredSize(new Dimension(240,150));
				add(sp, c);
			}
			{	
				c.gridx = 0;
				c.gridy = 3;
				c.anchor = GridBagConstraints.EAST;
				avslaaKnapp = new JButton("Avsl\u00E5");
				avslaaKnapp.addActionListener(new ButtonPressed());
				add(avslaaKnapp,c);
			}
			{
				c.gridx = 1;
				c.anchor = GridBagConstraints.WEST;
				godtaKnapp = new JButton("Godta");
				godtaKnapp.addActionListener(new ButtonPressed());
				add(godtaKnapp,c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMessage() {
		try{
			boolean b = innboks.getKalendersystem().getClientService().deleteMessage(Integer.parseInt(message.getId()));
//			if(b){
				innboks.getMessageList().setSelectedValue(null, false);
				innboks.getModel().removeElement(message);
				messagePanel.setModel(null);
				innboks.getUserEvents().removeMessage(message);
				innboks.getKalendersystem().updateNumberOfMessages();
//			}
		}
		catch(Exception e){
			e.printStackTrace();
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
	public class ButtonPressed implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(godtaKnapp)){
				ArrayList<Event> events = innboks.getKalendersystem().getUserEvents().getAllEvents();
				boolean doesMeetingCrash = false;
				ArrayList<Event> crashingEvents = new ArrayList<Event>();
				for(Event event : events){
					if(!timesFit(message.getMeeting().getTime(),event.getTime())){
						doesMeetingCrash = true;
						crashingEvents.add(event);
					}
				}
				if(!doesMeetingCrash){
					innboks.getKalendersystem().getClientService().setParticipantAnswer(message.getRecipient(), message.getMeeting(), "ja");
					deleteMessage();
					innboks.getKalendersystem().updateEvents();
				}
				else{
					meetingCrashes(crashingEvents);
				}
			}
			else if(e.getSource().equals(avslaaKnapp)){
				innboks.getKalendersystem().getClientService().setParticipantAnswer(message.getRecipient(), message.getMeeting(), "nei");
				
				deleteMessage();
			}
		}
	}
}