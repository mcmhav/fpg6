package kalendersystem;

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

import objectTypes.Employee;

public class SammenlignKalendere extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel headerLabel;
	private JList ansatteList;
	private JScrollPane ansatteListScroll;
	private JButton avbrytButton, okButton;
	private Kalendersystem calendarSystem;
	
	SammenlignKalendere(Kalendersystem calendarSystem) {
		super();
		initGUI();
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
		this.calendarSystem = calendarSystem;
		fillList();
	}
	
	private void fillList() {
		ArrayList<Employee> list = calendarSystem.getAllEmployees();
		for (Employee e : list) {
			((DefaultListModel) ansatteList.getModel()).addElement(e);
		}
	}
	
	private void initGUI() {
		try {
			this.setResizable(false);
			this.setTitle("Sammenlign");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			setLayout(new GridBagLayout());
			{
				headerLabel = new JLabel("Velg en ansatt: ");
				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 2;
				getContentPane().add(headerLabel, c);
				c.gridwidth = 1;
			}
			{
				ansatteList = new JList();
				ansatteListScroll = new JScrollPane(ansatteList);
				ansatteList.setModel(new DefaultListModel());
				ansatteList.setCellRenderer(new ParticipantListRenderer());
				ansatteListScroll.setPreferredSize(new Dimension(150, 200));
				c.gridx = 0;
				c.gridy = 1;
				c.gridwidth = 2;
				getContentPane().add(ansatteListScroll, c);
				c.gridwidth = 1;
			}
			{
				avbrytButton = new JButton("Avbryt");
				avbrytButton.addActionListener(new Close());
				c.gridx = 0;
				c.gridy = 2;
				c.anchor = GridBagConstraints.WEST;
				getContentPane().add(avbrytButton, c);
			}
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ConfirmButtonListener());
				c.gridx = 1;
				c.gridy = 2;
				c.anchor = GridBagConstraints.EAST;
				getContentPane().add(okButton, c);
			}
			setSize(400, 300);
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JButton getOk(){
		return okButton;
	}
	
	private class Close implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
		
	}
	
	private class ConfirmButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			Employee chosenEmployee = (Employee) ansatteList.getSelectedValue();
			calendarSystem.compareCalendars(chosenEmployee);
			dispose();
		}
	}
}
