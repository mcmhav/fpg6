package innboks;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objectTypes.*;

import kalendersystem.*;


public class Inbox extends javax.swing.JFrame {
	private JList meldingListe;
	private JLabel overskrift;
	private MessagePanel meldingsPanel;
	private JButton lukkKnapp;
	private DefaultListModel model;
	private UserEvents userEvents;
	private Kalendersystem ks;
	
	
	public DefaultListModel getModel() {
		return model;
	}
	public JList getMessageList(){
		return meldingListe;
	}
	
	public UserEvents getUserEvents(){
		return userEvents;
	}
	public MessagePanel getMeldingPanel(){
		return meldingsPanel;
	}
	public Kalendersystem getKalendersystem(){
		return ks;
	}

	
	public Inbox(Kalendersystem ks) {
		super();
		this.userEvents = ks.getUserEvents();
		this.ks = ks;
		initGUI();
		pack();
	}
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			addWindowListener(new windowListener());
			setTitle(userEvents.getUser().getName() + "s Innboks");
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			{
				c.gridx = 0;
				c.gridy = 0;
				c.insets = new Insets(5,5,5,5);
				overskrift = new JLabel("Meldinger:");
				add(overskrift, c);
			}
			{
				c.gridy = 1;
				model = new DefaultListModel();
				meldingListe = new JList();
				meldingListe.setModel(model);
				meldingListe.addListSelectionListener(new ListSelection());
				meldingListe.setCellRenderer(new MessageListRenderer());
				ArrayList<Message> m = userEvents.getMessages();
				if (m != null){
					Iterator<Message> it = m.iterator();
					while(it.hasNext()){
						model.addElement(it.next());
					}
				}
				JScrollPane sp = new JScrollPane(meldingListe);
				sp.setPreferredSize(new Dimension(250, 450));
				add(sp, c);
			}
			{
				c.gridx = 1;
				c.anchor = GridBagConstraints.NORTHWEST;
				meldingsPanel = new MessagePanel(this);
				add(meldingsPanel, c);
			}
			{
				c.gridx = 0;
				c.gridy = 2;
				c.anchor = GridBagConstraints.WEST;
				lukkKnapp = new JButton("Lukk");
				lukkKnapp.addActionListener(new CloseButton());
				add(lukkKnapp, c);
			}
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private class windowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			 ks.updateNumberOfMessages();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class CloseButton implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	public class ListSelection implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			meldingsPanel.setModel((Message) meldingListe.getSelectedValue());
		}
	}
}
