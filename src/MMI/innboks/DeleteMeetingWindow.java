package innboks;

import innboks.DeleteParticipantWindow.ButtonListener;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import objectTypes.*;

public class DeleteMeetingWindow extends JDialog{
	private JLabel confirmationMessage;
	private JButton okButton, avbrytButton;
	private Message message;
	private Inbox innboks;
	
	public DeleteMeetingWindow(Inbox innboks, Message message){
		super();
		this.innboks = innboks;
		this.message = message;
		initGUI();
	}

	private void initGUI() {
		try{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setPreferredSize(new Dimension(350, 200));
			setTitle("Slette møte");
			setLayout(new GridBagLayout());
			setLocationRelativeTo(innboks);
			setAlwaysOnTop(true);
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.insets = new Insets(25,100,0,100);
			confirmationMessage = new JLabel("Slette " + message.getMeeting().getTitle() + "?");
			add(confirmationMessage, c);
			
			c.gridwidth = 1;
			c.gridy = 1;
			c.insets = new Insets(20,75,25,0);
			okButton = new JButton("OK");
			okButton.addActionListener(new ButtonListener());
			add(okButton,c);
			
			c.gridx = 1;
			c.insets = new Insets(20,0,25,50);
			avbrytButton = new JButton("Avbryt");
			avbrytButton.addActionListener(new ButtonListener());
			add(avbrytButton,c);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(okButton)){
				try{
					innboks.getKalendersystem().getUserEvents().removeEvent(message.getMeeting());
					boolean b = innboks.getKalendersystem().getClientService().deleteMessage(Integer.parseInt(message.getId()));
						innboks.getMessageList().setSelectedValue(null, false);
						innboks.getModel().removeElement(message);
						innboks.getMeldingPanel().setModel(null);
						innboks.getUserEvents().removeMessage(message);
						innboks.getKalendersystem().updateNumberOfMessages();
						innboks.getKalendersystem().updateEvents();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				dispose();
			}
			else if(arg0.getSource().equals(avbrytButton)){
				dispose();
			}
		}
	}
}

