package serverOS;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestServerOS extends JFrame{
	
	ServerOS serverOS;
	JLabel label;
	
	public TestServerOS(int port){
		super("Serverklient (Simen er kul");
		serverOS = new ServerOS(port);
		label = new JLabel("Du er n\u00E5 tilkoblet server p\u00E5 port: " + port);
		add(label);
		setVisible(true);
		pack();
		validate();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		serverOS.listen();
//		setSize(new Dimension(320, 200));
	}
	
	public static void main(String[] args) {
		TestServerOS testServerOS = new TestServerOS(6789);
		
	}
}
