package innboks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import kalendersystem.DetailsPanel;
import kalendersystem.Kalendersystem;

import objectTypes.Employee;
import objectTypes.Event;
import objectTypes.Meeting;
import objectTypes.Message;

public class MeetingCrashesWindow extends JDialog{
	private JLabel infoLabel;
	private JList eventList;
	private JButton okButton, avbrytButton;
	private DefaultListModel model;
	private InvitationMessagePanel imp = null;
	private ChangeTimeWindow ctm = null;
	private DetailsPanel dp = null;
	private ArrayList<Event> evt;
	private Event newEvent;
	
	public MeetingCrashesWindow(InvitationMessagePanel invitationMessagePanel, ArrayList<Event> evt) {
		super();
		model = new DefaultListModel();
		this.evt = evt;
		for(Event e : evt){
			model.addElement(e);
		}
		imp = invitationMessagePanel;
		initGUI();
	}
	
	public MeetingCrashesWindow(ChangeTimeWindow ctm, ArrayList<Event> evt) {
		super();
		model = new DefaultListModel();
		this.evt = evt;
		for(Event e : evt){
			model.addElement(e);
		}
		this.ctm = ctm;
		initGUI();
	}
	
	public MeetingCrashesWindow(ArrayList<Event> evt) {
		super();
		model = new DefaultListModel();
		for(Event e : evt){
			model.addElement(e);
		}
		initGUI();
	}
	
	public MeetingCrashesWindow(DetailsPanel dp, ArrayList<Event> evt, Event newEvent) {
		super();
		model = new DefaultListModel();
		this.evt = evt;
		for(Event e : evt){
			model.addElement(e);
		}
		this.dp = dp;
		this.newEvent = newEvent;
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Endre tidspunkt");
		setLayout(new GridBagLayout());
		setLocationRelativeTo(imp);
		setAlwaysOnTop(true);
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		infoLabel = new JLabel("F\u00F8lgende hendelser vil slettes:");
		add(infoLabel, c);
		
		c.gridy = 1;
		eventList = new JList(model);
		JScrollPane sp = new JScrollPane(eventList);
		sp.setPreferredSize(new Dimension(120,150));
		add(sp, c);
		
		c.gridy = 2;
		c.gridwidth = 1;
		okButton = new JButton("Ok");
		okButton.addActionListener(new okButtonListener());
		add(okButton, c);
		
		c.gridx = 1;
		avbrytButton = new JButton("Avbryt");
		avbrytButton.addActionListener(new avbrytButtonListener());
		add(avbrytButton, c);
		
		pack();
		setVisible(true);
	}
	
	private void okIfInvitationMessage(){
		imp.getInbox().getKalendersystem().getClientService().setParticipantAnswer(imp.getMessage().getRecipient(), imp.getMessage().getMeeting(), "ja");
		for(Event e : evt){
			if (e instanceof Meeting){
				imp.getInbox().getKalendersystem().getClientService().setParticipantAnswer(imp.getMessage().getRecipient(), (Meeting) e, "nei");
			}
			imp.getInbox().getKalendersystem().getClientService().deleteEventOrMeeting(e, imp.getInbox().getUserEvents().getUser());
		}
		imp.getInbox().getKalendersystem().updateEvents();
		imp.deleteMessage();
	}
	
	private void okIfChangeTimeWindow(){
		ctm.getInbox().getKalendersystem().getClientService().updateMeeting(ctm.getMeeting());
		for(Event e : evt){
			if (e instanceof Meeting){
				ctm.getInbox().getKalendersystem().getClientService().setParticipantAnswer(imp.getMessage().getRecipient(), (Meeting) e, "nei");
			}
			else{
				ctm.getInbox().getKalendersystem().getClientService().deleteEventOrMeeting(e, imp.getInbox().getUserEvents().getUser());
			}
		}
		ctm.getInbox().getKalendersystem().updateEvents();
		ctm.deleteMessage();
	}
	private void okIfDetailsPanel(){
		for(Event e : evt){
			if(e instanceof Meeting){
				if(!e.getCreator().getName().equals(dp.getCalendarSystem().getUserEvents().getUser().getName())){
					dp.getCalendarSystem().getClientService().setParticipantAnswer(dp.getCalendarSystem().getUserEvents().getUser(), (Meeting) e, "nei");
					dp.getCalendarSystem().reload();
				}
				else{
					dp.getCalendarSystem().getUserEvents().removeEvent(e);
				}
			}
			else{
				dp.getCalendarSystem().getUserEvents().removeEvent(e);
			}
		}
		if(dp.getNcrash()&&dp.getDisplayedEvent()!=null){
			if(dp.getCalendarSystem().getWeekcal().isTwo()&&dp.getCalendarSystem().getWeekcal().getcx()%2==0){
				dp.getCalendarSystem().getUserEvents().removeEvent(dp.getDisplayedEvent());
			}else{
				dp.getCalendarSystem().getUserEvents().removeEvent(dp.getDisplayedEvent());
			}
		}
		dp.alertUser(dp.getEventSavedMessage(), new Color(0,130,0));
		dp.getCalendarSystem().addEventToSystem(newEvent);
//		dp.getCalendarSystem().getClientService().updateMeeting(newEvent);
		dp.getCalendarSystem().updateEvents();
	}
	
	private class okButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(imp!=null){
				okIfInvitationMessage();
			}
			else if(ctm!=null){
				okIfChangeTimeWindow();
			}
			else if(dp!=null){
				okIfDetailsPanel();
			}
			dispose();
		}
		
	}
	private class avbrytButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
}
