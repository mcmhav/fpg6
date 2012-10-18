package innboks;

import java.awt.*;
import javax.swing.*;

import objectTypes.Message;
import objectTypes.MessageType;

public class MessageListRenderer extends JLabel implements ListCellRenderer{
	final ImageIcon declinedMeetingIcon = new ImageIcon(getClass().getResource("declineMeetingIcon.png"));
	final ImageIcon infoMessageIcon = new ImageIcon(getClass().getResource("infoMessageIcon.png"));
	final ImageIcon newMeetingIcon = new ImageIcon(getClass().getResource("newMeetingIcon.png"));
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean CellHasFocus) {
		Message m = (Message) value;
		if(m.getType().equals(MessageType.DECLINEMESSAGE)){
			setText(m.getMeeting().getTitle()+": "+m.getSender().getName());
		}
		else{
			setText(m.toString());
		}
		if(m.getType().equals(MessageType.INFORMATIONMESSAGE)){
			setIcon(infoMessageIcon);
		}
		else if(m.getType().equals(MessageType.INVITATIONMESSAGE)){
			setIcon(newMeetingIcon);
		}
		else if(m.getType().equals(MessageType.DECLINEMESSAGE)){
			setIcon(declinedMeetingIcon);
		}
		if(isSelected){
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setToolTipText(m.getDescription());
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}
