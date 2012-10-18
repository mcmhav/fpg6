package innboks;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import objectTypes.Message;
import objectTypes.TimeFrame;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DeclineMessagePanel extends javax.swing.JPanel {
	private JLabel tittelLabel, datoLabel, tidLabel, beskrivelseLabel;
	private JTextField tittelFelt, datoFelt, tidFelt;
	private JTextArea beskrivelsesFelt;
	private JButton endreTidKnapp, slettDeltagerKnapp, slettMoteKnapp;
	private Inbox innboks;
	private Message message;
	private MessagePanel messagePanel;
	
	public DeclineMessagePanel(Message message, Inbox innboks) {
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
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				tittelLabel = new JLabel("Tittel:");
				add(tittelLabel, c);
			}
			{
				c.gridx = 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.EAST;
				tittelFelt = new JTextField(message.getName());
				tittelFelt.setEditable(false);
				tittelFelt.setColumns(16);
				add(tittelFelt,c);
			}
			{		
				c.insets = new Insets(15, 5, 5, 5);
				c.gridx = 0;
				c.gridy = 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				datoLabel = new JLabel("Dato:");
				add(datoLabel, c);
			}
			{
				
			}
			{
				TimeFrame timeFrame = message.getMeeting().getTime();
				String dateString = timeFrame.getDateString();
				int startHour = timeFrame.getStartHour();
				int endHour = timeFrame.getEndHour();
				datoFelt = new JTextField(dateString + " Start: " + startHour + " Slutt: " + endHour);
//				tidFelt = new JTextField("blabla");
				c.gridx = 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.EAST;
				datoFelt.setEditable(false);
				datoFelt.setColumns(16);
//				c.anchor = GridBagConstraints.EAST;
				add(datoFelt, c);
			}
			{
				c.insets = new Insets(0, 5, 5, 5);
				c.gridx = 1;
				c.gridy = 2;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.EAST;
				endreTidKnapp = new JButton("Endre Tid");
				endreTidKnapp.addActionListener(new ButtonPressed());
				add(endreTidKnapp,c);
			}
			{
				c.insets = new Insets(5, 5, 0, 5);
				c.gridx = 0;
				c.gridy = 3;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.WEST;
				beskrivelseLabel = new JLabel("Beskrivelse:");
				add(beskrivelseLabel,c);
			}
			{
				c.insets = new Insets(0, 5, 5, 5);
				c.gridy = 4;
				c.gridx = 0;
				c.anchor = GridBagConstraints.WEST;
				beskrivelsesFelt = new JTextArea(message.getDescription());
				beskrivelsesFelt.setLineWrap(true);
				beskrivelsesFelt.setEditable(false);
				JScrollPane sp = new JScrollPane(beskrivelsesFelt);
				sp.setPreferredSize(new Dimension(240,150));
				add(sp, c);
			}
		
			{	
				c.insets = new Insets(5, 5, 5, 5);
				c.gridx = 0;
				c.gridy = 5;
				c.anchor = GridBagConstraints.WEST;
				c.gridwidth = 2;
				slettDeltagerKnapp = new JButton("Fjern deltager");
				slettDeltagerKnapp.addActionListener(new ButtonPressed());
				add(slettDeltagerKnapp,c);
			}
			{
				c.gridx = 1;
				c.gridy = 5;
				c.anchor = GridBagConstraints.EAST;
				c.gridwidth = 1;
				slettMoteKnapp = new JButton("Slett Møte");
				slettMoteKnapp.addActionListener(new ButtonPressed());
				add(slettMoteKnapp, c);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class ButtonPressed implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(endreTidKnapp)){
				ChangeTimeWindow etv = new ChangeTimeWindow(innboks, message);
				etv.pack();
				etv.setVisible(true);
			}
			else if(e.getSource().equals(slettDeltagerKnapp)){
				DeleteParticipantWindow sdv = new DeleteParticipantWindow(innboks, message);
				sdv.pack();
				sdv.setVisible(true);
			}
			else if(e.getSource().equals(slettMoteKnapp)){
				DeleteMeetingWindow smv = new DeleteMeetingWindow(innboks, message);
				smv.pack();
				smv.setVisible(true);
			}
		}
	}
}
