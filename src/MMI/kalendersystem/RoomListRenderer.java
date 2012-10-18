package kalendersystem;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import objectTypes.Room;

public class RoomListRenderer extends JLabel implements ListCellRenderer{
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean CellHasFocus) {
		Room r = (Room) value;
		setText(r.getName());
		if(isSelected){
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setToolTipText(r.getDescription());
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}
