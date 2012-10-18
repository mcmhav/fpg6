package kalendersystem;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objectTypes.Room;
import objectTypes.TimeFrame;

public class ReserveRoom extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList romList;
	private JButton reserverButton, avbrytButton;
	private JLabel endLabel, startLabel, dateLabel;
	private JTextField endField, dateField, startField;
	private JTextArea descriptionArea;
	private JScrollPane romListScroll, beskrivelseAreaScroll;
	private JLabel headerLabel;
	private DetailsPanel detailsPanel;
	private TimeFrame timeFrame;
	
	public ReserveRoom(DetailsPanel dp, TimeFrame tf) {
		super();
		initGUI();
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);
		this.detailsPanel = dp;
		this.timeFrame = tf;
		
		loadRooms();
		writeTime();
		romList.setSelectedIndex(0);
	}
	
	private void writeTime() {
		dateField.setText(timeFrame.getDateString());
		startField.setText(timeFrame.getStartHourString());
		endField.setText(timeFrame.getEndHourString());
//		dateField.setText(this.timeFrame.getStart().getDate()+"."+(this.timeFrame.getStart().getMonth()+1)+"."+(this.timeFrame.getStart().getYear()+1900));
//		startField.setText(""+this.timeFrame.getStart().getHours());
//		endField.setText(""+this.timeFrame.getEnd().getHours());
	}
	
	private void loadRooms() {
		//TODO DB: hent ledige rom 
		//eksempel:
		ArrayList<Room> roomlist = detailsPanel.getCalendarSystem().getClientService().getAllFreeRooms(timeFrame);
		for (Room r : roomlist) {
			((DefaultListModel) romList.getModel()).addElement(r);
		}
	}
	
	private class RomListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			descriptionArea.setText(((Room) romList.getModel().getElementAt(romList.getSelectedIndex())).getDescription());
		}
	}
	
	private class ReserveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Room selectedRoom = (Room) romList.getModel().getElementAt(romList.getSelectedIndex());
			detailsPanel.setReservedRoom(selectedRoom);
			dispose();
		}
	}
	
	private class CloseAL implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLayout(new GridBagLayout());
			setTitle("Reserver rom:");
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			c.anchor = GridBagConstraints.WEST;
			{
				headerLabel = new JLabel("Velg et ledig rom:");
				c.gridx = 1;
				c.gridy = 0;
				headerLabel.setFont(new Font("arial", Font.BOLD, 16));
				getContentPane().add(headerLabel, c);
			}
			{
				ListModel romListModel = 
					new DefaultListModel();
				romList = new JList();
				romListScroll = new JScrollPane(romList);
				romListScroll.setPreferredSize(new Dimension(150, 100));
				romList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				romList.addListSelectionListener(new RomListListener());
				romList.setCellRenderer(new RoomListRenderer());
				c.gridx = 1;
				c.gridy = 1;
				c.gridheight = 2;
				c.fill = c.BOTH;
				getContentPane().add(romListScroll, c);
				romList.setModel(romListModel);
				romList.setLayout(null);
				c.gridheight = 1;
			}
			{
				descriptionArea = new JTextArea(10, 25);
				beskrivelseAreaScroll = new JScrollPane(descriptionArea);
				descriptionArea.setLineWrap(true);
				c.gridx = 2;
				c.gridy = 2;
				c.gridwidth = 7;
				getContentPane().add(beskrivelseAreaScroll, c);
				c.gridwidth = 1;
			}
			{
				dateField = new JTextField(6);
				c.gridx = 3;
				c.gridy = 1;
				getContentPane().add(dateField, c);
			}
			{
				startField = new JTextField(3);
				c.gridx = 5;
				c.gridy = 1;
				getContentPane().add(startField, c);
			}
			{
				endField = new JTextField(3);
				c.gridx = 7;
				c.gridy = 1;
				getContentPane().add(endField, c);
				c.fill = c.NONE;
			}
			{
				dateLabel = new JLabel();
				c.gridx = 2;
				c.gridy = 1;
				getContentPane().add(dateLabel, c);
				dateLabel.setText("Dato:");
			}
			{
				startLabel = new JLabel();
				c.gridx = 4;
				c.gridy = 1;
				getContentPane().add(startLabel, c);
				startLabel.setText("Start:");
			}
			{
				endLabel = new JLabel();
				c.gridx = 6;
				c.gridy = 1;
				getContentPane().add(endLabel, c);
				endLabel.setText("Slutt:");
			}
			{
				reserverButton = new JButton();
				c.gridx = 7;
				c.gridy = 3;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.EAST;
				c.fill = c.BOTH;
				getContentPane().add(reserverButton, c);
				reserverButton.setText("Reserver rom");
				reserverButton.addActionListener(new ReserveButtonListener());
				c.anchor = GridBagConstraints.WEST;
				c.gridwidth = 1;
			}
			{
				avbrytButton = new JButton();
				c.gridx = 5;
				c.gridy = 3;
				c.gridwidth = 2;
				getContentPane().add(avbrytButton, c);
				avbrytButton.setText("Avbryt");
				avbrytButton.addActionListener(new CloseAL());
				c.gridwidth = 1;
			}
			
			pack();
			//setSize(520, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
