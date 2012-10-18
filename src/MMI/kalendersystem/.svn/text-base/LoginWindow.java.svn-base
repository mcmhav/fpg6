package kalendersystem;

import innboks.InformationMessagePanel.OkButtonPressed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import objectTypes.Employee;

import clientOS.ClientOS;
import clientOS.ClientService;

public class LoginWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel headerLabel, brukernavnLabel, feilBrukernavnLabel, 
					passordLabel, feilPassordLabel, capsLabel;
	private JButton loginButton, avbrytButton;
	private JPasswordField passordField;
	private JTextField brukernavnField;
	private ImageIcon ikon = new ImageIcon(getClass().getResource("warningIcon.png"));
	
	private ClientService clientService;
	
	private int port = 6789;
	private String address = "127.0.0.1";
	
	public LoginWindow(){
		ClientOS clientOS = new ClientOS(port, address);
		clientService = new ClientService(clientOS);
		
		
		setPreferredSize(new Dimension(320, 200));
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		Font overskrift = new Font("Arial", Font.BOLD, 14);
		c.insets = new Insets(3, 5, 3, 5);

		headerLabel = new JLabel("Login:");
		brukernavnLabel = new JLabel("Brukernavn:");
		passordLabel = new JLabel("Passord:");
		capsLabel = new JLabel("Caps Lock er p\u00E5", ikon, JLabel.LEFT);
		brukernavnField = new JTextField();
		passordField = new JPasswordField();
		avbrytButton = new JButton("Avbryt");
		loginButton = new JButton("Login");

		headerLabel.setFont(overskrift);
		c.gridx = 2;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(headerLabel, c);
	
		c.gridx = 1;
		c.gridy = 2;
		add(brukernavnLabel, c);
	
		c.gridx = 1;
		c.gridy = 3;
		add(passordLabel, c);
	
	
		brukernavnField.setColumns(18);
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 3;
		add(brukernavnField, c);
	
	
		passordField.setColumns(18);
		c.gridwidth = 3;
		c.gridx = 2;
		c.gridy = 3;
		add(passordField, c);
		passordField.addKeyListener(new TextAction());
		passordField.addActionListener(new enterListener());
		
		capsLabel.setVisible(false);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 4;
		add(capsLabel, c);
		
		feilPassordLabel = new JLabel("Feil passord");
		feilPassordLabel.setForeground(Color.RED);
		feilPassordLabel.setVisible(false);
		add(feilPassordLabel, c);
		
		feilBrukernavnLabel = new JLabel("Ugyldig brukernavn");
		feilBrukernavnLabel.setForeground(Color.RED);
		feilBrukernavnLabel.setVisible(false);
		add(feilBrukernavnLabel, c);
		
		avbrytButton.addActionListener(new AvbrytButtonListener());
		c.insets = new Insets(3, 10, 3, 0);
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 4;
		c.anchor = GridBagConstraints.EAST;
		add(avbrytButton, c);
		
		loginButton.addActionListener(new LoginButtonListener());
		c.insets = new Insets(3, 0, 3, 5);
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 4;
		c.anchor = GridBagConstraints.EAST;
		add(loginButton, c);								
		
		brukernavnField.requestFocus();
		
		pack();
		validate();
	}
	
	private void checkLoginInfo(){
		feilBrukernavnLabel.setVisible(false);
		feilPassordLabel.setVisible(false);
		capsLabel.setVisible(false);
		char[] c = passordField.getPassword();
		String password = "";
		for(int i = 0; i < c.length; i++){
			password += c[i];
		}
		ArrayList<Employee> emp = clientService.getAllEmployees();
		boolean feilpassord = false;
		for(Employee e : emp){
			if(e.getUserName().equals(brukernavnField.getText())){
				feilpassord = true;
				if(e.getPassword().equals(password)){
					Kalendersystem ks = new Kalendersystem(e, emp, clientService);
					dispose();
				}
			}
		}
		if(feilpassord){
			feilPassordLabel.setVisible(true);
		}
		else{
			feilBrukernavnLabel.setVisible(true);
		}
	}
	
	private class LoginButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			checkLoginInfo();
		}
	}
	
	private class AvbrytButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	private class enterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checkLoginInfo();
		}
		
	}
	
	
	class TextAction implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			try{
				boolean capsIsLocked = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				feilBrukernavnLabel.setVisible(false);
				feilPassordLabel.setVisible(false);
				capsLabel.setVisible(capsIsLocked);
			}
			catch (Exception e) {
				System.err.println("Funker ikke for Cake!!");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args){
			LoginWindow lw = new LoginWindow();
	}
	
}

