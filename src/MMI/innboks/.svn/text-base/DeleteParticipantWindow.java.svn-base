package innboks;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import objectTypes.*;

public class DeleteParticipantWindow extends JDialog{
	private JLabel confirmationMessage;
	private JButton okButton, avbrytButton;
	private Employee sender;
	private Message message;
	private Inbox innboks;
	
	public DeleteParticipantWindow(Inbox innboks, Message message){
		super();
		this.innboks = innboks;
		this.sender = message.getSender();
		this.message = message;
		initGUI();
	}

	private void initGUI() {
		try{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setPreferredSize(new Dimension(350, 200));
			setTitle("Slette deltager");
			setLayout(new GridBagLayout());
			setLocationRelativeTo(innboks);
			setAlwaysOnTop(true);
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.insets = new Insets(25,20,0,20);
			confirmationMessage = new JLabel("Slette " + sender.getName() + " fra møtets deltagerliste?");
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
					innboks.getKalendersystem().getClientService().removeParticipant(message.getMeeting(), message.getSender());
					innboks.getKalendersystem().getUserEvents().removeParticipant(message.getMeeting(),message.getSender());
					innboks.getKalendersystem().reload();
					boolean b = innboks.getKalendersystem().getClientService().deleteMessage(Integer.parseInt(message.getId()));
					if(b){
						innboks.getMessageList().setSelectedValue(null, false);
						innboks.getModel().removeElement(message);
						innboks.getMeldingPanel().setModel(null);
						
						innboks.getUserEvents().removeMessage(message);
						innboks.getKalendersystem().updateNumberOfMessages();
					}
				}
				catch(Exception e){
					//e.printStackTrace();
				}
				dispose();
			}
			else if(arg0.getSource().equals(avbrytButton)){
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		//Message m = new Message("2", "Arne declined",MessageType.DECLINEMESSAGE,"ARNE VIL IKKE VÆRE MED",new Employee("Arnearn","passord","Arne Arnesen"));
		//Employee u = new Employee("kalkl","10231","Kalle Klukk");
		//SlettDeltagerVindu inst = new SlettDeltagerVindu(u, m);
		//inst.setLocationRelativeTo(null);
		//inst.setVisible(true);
	}
}
