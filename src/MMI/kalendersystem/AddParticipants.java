package kalendersystem;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javax.swing.WindowConstants;

import clientOS.ClientOS;
import clientOS.ClientService;

import objectTypes.Employee;

public class AddParticipants extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel headerLabel;
	private JButton addButton, cancelButton, confirmButton, removeButton;
	private JList allEmployeesList, participantList;
	private JScrollPane allEmployeesListScroll, participantListScroll;
	private DetailsPanel detailsPanel;
	
	public AddParticipants(DetailsPanel detailsPanel) {
		super();
		initGUI();
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);
		this.detailsPanel = detailsPanel;
		fillAllEmployees();
	}
	
	private void fillAllEmployees() {
		ArrayList<Employee> receivedList = detailsPanel.getCalendarSystem().getAllEmployees();
		for (Employee e : receivedList) {
			if (!detailsPanel.getCalendarSystem().getUserEvents().getUser().equals(e))
				((DefaultListModel) allEmployeesList.getModel()).addElement(e);
		}
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLayout(new GridBagLayout());
			setTitle("Legg til deltagere: ");
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			{
				c.gridx = 0;
				c.gridy = 1;
				c.gridheight = 1;
				c.fill = c.BOTH;
				allEmployeesList = new JList();
				allEmployeesListScroll = new JScrollPane(allEmployeesList);
				allEmployeesList.setCellRenderer(new ParticipantListRenderer());
				allEmployeesListScroll.setPreferredSize(new Dimension(150, 200));
				getContentPane().add(allEmployeesListScroll, c);
				allEmployeesList.setModel(new DefaultListModel());
				c.gridheight = 1;
			}
			{
				participantList = new JList();
				participantList.setCellRenderer(new ParticipantListRenderer());
				participantListScroll = new JScrollPane(participantList);
				participantListScroll.setPreferredSize(new Dimension(150, 200));
				c.gridx = 2;
				c.gridy = 1;
				c.gridheight = 1;
				getContentPane().add(participantListScroll, c);
				participantList.setModel(new DefaultListModel());
				c.gridheight = 1;
			}
			{
				addButton = new JButton();
				c.gridx = 1;
				c.gridy = 1;
				c.fill = c.HORIZONTAL;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(2, 2, 30, 2);
				getContentPane().add(addButton, c);
				addButton.setText(">>");
				addButton.addActionListener(new AddParticipantListener());
			}
			{
				removeButton = new JButton();
				c.gridx = 1;
				c.gridy = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(30, 2, 2, 2);
				getContentPane().add(removeButton, c);
				removeButton.setText("<<");
				removeButton.addActionListener(new RemoveParticipantListener());
				c.insets = new Insets(2, 2, 2, 2);
			}
			{
				confirmButton = new JButton();
				c.gridx = 2;
				c.gridy = 2;
				c.fill = c.NONE;
				c.anchor = GridBagConstraints.EAST;
				getContentPane().add(confirmButton, c);
				confirmButton.setText("OK");
				confirmButton.addActionListener(new ConfirmButtonListener());
			}
			{
				cancelButton = new JButton();
				c.gridx = 2;
				c.gridy = 2;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(2, 2, 2, 30);
				getContentPane().add(cancelButton, c);
				cancelButton.setText("Avbryt");
				cancelButton.addActionListener(new CancelButtonListener());
				c.anchor = GridBagConstraints.WEST;
			}
			{
				headerLabel = new JLabel();
				headerLabel.setFont(new Font("arial", Font.BOLD, 16));
				c.gridx = 0;
				c.gridy = 0;
				getContentPane().add(headerLabel, c);
				headerLabel.setText("Legg til deltagere:");
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class CancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();	
		}
	}
	
	private class ConfirmButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			ArrayList<Employee> selectedEmployees = new ArrayList<Employee>();
			DefaultListModel participantListModel = (DefaultListModel) participantList.getModel();
			for (int i = 0; i < participantListModel.getSize(); i++) {
				selectedEmployees.add((Employee) participantListModel.get(i));
			}
			detailsPanel.addParticipants(selectedEmployees);
			dispose();
		}
	}
	
	private class AddParticipantListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			int[] selectedIndices = allEmployeesList.getSelectedIndices();
			DefaultListModel participantListModel = (DefaultListModel) participantList.getModel();
			DefaultListModel allEmployeesListModel = (DefaultListModel) allEmployeesList.getModel();
			for (int i = 0; i < selectedIndices.length; i++) {
				participantListModel.addElement(allEmployeesListModel.get(selectedIndices[i]));
			}
			for (int i = selectedIndices.length - 1; i >= 0; i--) {
				allEmployeesListModel.remove(selectedIndices[i]);
			}
		}
	}
	
	private class RemoveParticipantListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			int[] selectedIndices = participantList.getSelectedIndices();
			DefaultListModel participantListModel = (DefaultListModel) participantList.getModel();
			DefaultListModel allEmployeesListModel = (DefaultListModel) allEmployeesList.getModel();
			for (int i = 0; i < selectedIndices.length; i++) {
				allEmployeesListModel.addElement(participantListModel.get(selectedIndices[i]));
			}
			for (int i = selectedIndices.length - 1; i >= 0; i--) {
				participantListModel.remove(selectedIndices[i]);
			}
		}
	}
}