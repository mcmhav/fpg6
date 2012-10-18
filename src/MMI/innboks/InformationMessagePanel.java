package innboks;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import objectTypes.Message;

public class InformationMessagePanel extends javax.swing.JPanel {
	private JLabel tittelLabel, beskrivelseLabel;
	private JTextField tittelFelt;
	private JTextArea beskrivelsesFelt;
	private JButton okButton;
	private Inbox innboks;
	private Message message;
	private MessagePanel messagePanel;
	
	public InformationMessagePanel(Message message, Inbox innboks) {
		super();
		this.innboks = innboks;
		this.message = message;
		this.messagePanel = innboks.getMeldingPanel();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(250, 450));
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			{
				c.insets = new Insets(5,5,5,5);
				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.WEST;
				tittelLabel = new JLabel("Tittel:");
				add(tittelLabel, c);
			}
			{
			
				c.anchor = GridBagConstraints.EAST;
				tittelFelt = new JTextField(message.getName());
				tittelFelt.setEditable(false);
				tittelFelt.setColumns(18);
				add(tittelFelt,c);
			}
			{
				c.gridx = 0;
				c.gridy = 1;
				c.anchor = GridBagConstraints.WEST;
				beskrivelseLabel = new JLabel("Beskrivelse:");
				add(beskrivelseLabel,c);
			}
			{
				c.gridy = 2;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.CENTER;
				beskrivelsesFelt = new JTextArea(message.getDescription());
				beskrivelsesFelt.setLineWrap(true);
				beskrivelsesFelt.setEditable(false);
				JScrollPane sp = new JScrollPane(beskrivelsesFelt);
				sp.setPreferredSize(new Dimension(240,150));
				add(sp, c);
			}
			{
				c.gridy = 3;
				c.anchor = GridBagConstraints.WEST;
				okButton = new JButton("Ok");
				okButton.addActionListener(new OkButtonPressed());
				add(okButton, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteMessage(){
		
		try{
			boolean b = innboks.getKalendersystem().getClientService().deleteMessage(Integer.parseInt(message.getId()));
			if(b){
				innboks.getMessageList().setSelectedValue(null, false);
				innboks.getModel().removeElement(message);
				messagePanel.setModel(null);
				innboks.getUserEvents().removeMessage(message);
				innboks.getKalendersystem().updateNumberOfMessages();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public class OkButtonPressed implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			deleteMessage();
		}
	}
}