package kalendersystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class LeggTilDeltagere extends javax.swing.JFrame {
	private JLabel headerLabel;
	private JButton leggTilButton, avbrytButton, okButton, fjernButton;
	private JList alleAnsatteList, deltagereList;
	private JScrollPane alleAnsatteListScroll, deltagereListScroll;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LeggTilDeltagere inst = new LeggTilDeltagere();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public LeggTilDeltagere() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			{
				ListModel alleAnsatteListModel = 
					new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
				c.gridx = 0;
				c.gridy = 1;
				c.gridheight = 1;
				c.fill = c.BOTH;
				alleAnsatteList = new JList();
				alleAnsatteListScroll = new JScrollPane(alleAnsatteList);
				alleAnsatteListScroll.setPreferredSize(new Dimension(150, 200));
				getContentPane().add(alleAnsatteListScroll, c);
				alleAnsatteList.setModel(alleAnsatteListModel);
				c.gridheight = 1;
			}
			{
				ListModel deltagereListModel = 
					new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
				deltagereList = new JList();
				deltagereListScroll = new JScrollPane(deltagereList);
				deltagereListScroll.setPreferredSize(new Dimension(150, 200));
				c.gridx = 2;
				c.gridy = 1;
				c.gridheight = 1;
				getContentPane().add(deltagereListScroll, c);
				deltagereList.setModel(deltagereListModel);
				c.gridheight = 1;
			}
			{
				leggTilButton = new JButton();
				c.gridx = 1;
				c.gridy = 1;
				c.fill = c.HORIZONTAL;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(30, 2, 2, 2);
				getContentPane().add(leggTilButton, c);
				leggTilButton.setText(">>");
			}
			{
				fjernButton = new JButton();
				c.gridx = 1;
				c.gridy = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(2, 2, 30, 2);
				getContentPane().add(fjernButton, c);
				fjernButton.setText("<<");
				c.insets = new Insets(2, 2, 2, 2);
			}
			{
				okButton = new JButton();
				c.gridx = 2;
				c.gridy = 2;
				c.fill = c.NONE;
				c.anchor = GridBagConstraints.EAST;
				getContentPane().add(okButton, c);
				okButton.setText("OK");
			}
			{
				avbrytButton = new JButton();
				c.gridx = 2;
				c.gridy = 2;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(2, 2, 2, 30);
				getContentPane().add(avbrytButton, c);
				avbrytButton.setText("Avbryt");
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

}