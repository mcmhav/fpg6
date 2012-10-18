package kalendersystem;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LogOutConfirmationFrame extends JDialog{
	private JButton okButton, avbrytButton;
	private JLabel confirmationMessage;
	private Kalendersystem ks;
	
	public LogOutConfirmationFrame(Kalendersystem ks){
		super();
		this.ks = ks;
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(150, 100));
		setTitle("");
		setLayout(new GridBagLayout());
		setLocationRelativeTo(ks);
		setAlwaysOnTop(true);
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 2;
		confirmationMessage = new JLabel("Logge ut?");
		add(confirmationMessage, c);
		
		c.gridwidth = 1;
		c.gridy = 1;
		okButton = new JButton("Ok");
		okButton.addActionListener(new okButtonAL());
		add(okButton,c);
		
		c.gridx = 1;
		avbrytButton = new JButton("Avbryt");
		avbrytButton.addActionListener(new avbrytButtonAL());
		add(avbrytButton,c);
		
		pack();
		setVisible(true);
		okButton.requestFocus();
	}
	
	private class okButtonAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			LoginWindow lw = new LoginWindow();
			ks.dispose();
			dispose();
		}
		
	}
	
	private class avbrytButtonAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
}
