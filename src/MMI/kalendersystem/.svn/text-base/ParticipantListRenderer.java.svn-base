package kalendersystem;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import objectTypes.Employee;

public class ParticipantListRenderer extends JLabel implements ListCellRenderer{
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean CellHasFocus) {
		Employee e = (Employee) value;
		setText(e.getName());
		if(isSelected){
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setToolTipText(e.getUserName());
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}
