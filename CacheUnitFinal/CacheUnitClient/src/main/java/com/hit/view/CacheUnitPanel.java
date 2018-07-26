package main.java.com.hit.view;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CacheUnitPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();

	private CacheUnitView parent;
	
	private JFrame mainFrame;
	private JButton buttonLoad = new JButton("Load Request");
	private JButton buttonShow = new JButton("Show Statistics");
	private JLabel lblCapacity = new JLabel("Capacity:");
	private JLabel lblAlgo = new JLabel("Algorithm:");
	private JLabel lblNumReq = new JLabel("Total number of requests: ");
	private JLabel lblTotalDMs = new JLabel("Total number of DataModels (GET/delete/update REQUESTS):");
	private JLabel lblTotalSwaps = new JLabel("Total number of DataModel swaps (from Cache to Disk):");
	private JLabel lblcapacityResp = new JLabel("No server connection");
	private JLabel lblalgoResp = new JLabel("No server connection");
	private JLabel lblnumberReqResp = new JLabel("to get this information, press \"Show Statistics\" button");
	private JLabel lbltotalDMsResp = new JLabel("to get this information, press \"Show Statistics\" button");
	private JLabel lbltotalSwapsResp = new JLabel("to get this information, press \"Show Statistics\" button");
	private JLabel lblEmpty = new JLabel();
	private JLabel lblEmpty1 = new JLabel();
	private JPanel panel;

	public CacheUnitPanel(CacheUnitView parent) {
		this.parent = parent;
	}
	
	public void createAndShowGUI() {
		mainFrame = new JFrame("Cache Unit UI by Lilya Styagluk & Yevgeny Volodarsky");

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 15, 1));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		panel.add(buttonLoad);
		panel.add(buttonShow);
		panel.add(lblEmpty);
		panel.add(lblEmpty1);
		panel.add(lblCapacity);
		panel.add(lblcapacityResp);
		panel.add(lblalgoResp);
		panel.add(lblAlgo);
		panel.add(lblalgoResp);
		panel.add(lblNumReq);
		panel.add(lblnumberReqResp);
		panel.add(lblTotalDMs);
		panel.add(lbltotalDMsResp);
		panel.add(lblTotalSwaps);
		panel.add(lbltotalSwapsResp);

		mainFrame.add(panel);
		mainFrame.setSize((int) (width * 0.35), (int) (height * 0.5));

		mainFrame.pack();
		mainFrame.setVisible(true);

		buttonLoad.addActionListener(this);
		buttonShow.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			String command = e.getActionCommand();
			
			if(command.equals("Load Request"))
			{
				parent.updateUIData("LOAD");
			}
			else if (command.equals("Show Statistics"))
			{
				parent.updateUIData("STATISTICS");
			}
		}
	}

	public void setVisible() {
		mainFrame.setVisible(true);
	}

	public JLabel getLblCapacityResp() {
		return lblcapacityResp;
	}

	public JLabel getLblAlgoResp() {
		return lblalgoResp;
	}

	public JLabel getLblNumOfReqResp() {
		return lblnumberReqResp;
	}

	public JLabel getLblNumOfDMResp() {
		return lbltotalDMsResp;
	}

	public JLabel getLblNumOfSwapResp() {
		return lbltotalSwapsResp;
	}
}
