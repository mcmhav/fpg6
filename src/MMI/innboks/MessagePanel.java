
package innboks;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import objectTypes.Message;
import objectTypes.MessageType;

public class MessagePanel extends JPanel{
	private JPanel panel;
	private Inbox innboks;
	
	public MessagePanel(Inbox innboks){
		this.innboks = innboks;	
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(250,450));
		add(panel);
	}
	
	public void setModel(Message melding){
		if(melding != null){
			String type = melding.getType();
			if(type.equals(MessageType.INFORMATIONMESSAGE)){
				remove(panel);
				panel = new InformationMessagePanel(melding, innboks);
				add(panel);
				innboks.validate();
			}
			else if(type.equals(MessageType.INVITATIONMESSAGE)){
				remove(panel);
				panel = new InvitationMessagePanel(melding, innboks);
				add(panel);
				innboks.validate();
			}
			else if(type.equals(MessageType.DECLINEMESSAGE)){
				remove(panel);
				panel = new DeclineMessagePanel(melding, innboks);
				add(panel);
				innboks.validate();
			}
		}
		else{
			remove(panel);
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(250,450));
			add(panel);
			innboks.validate();
		}
	}
}
