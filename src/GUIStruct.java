import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Reusable Panel Components 
 */
public class GUIStruct extends JPanel {
	private ArrayList<JTextField> popper;
	private ArrayList<JTextArea> piper;
	private ArrayList<JComboBox> boxx;
	private GridBagConstraints gridbg;
	
	private ArrayList<JList> list1;
	private Model model;
	private GUI view1;
	
	public GridBagConstraints getConstraints() {
		return gridbg;
	}
	
	public GUIStruct(GUI view1) {
		this.setLayout(new GridBagLayout());
		gridbg = new GridBagConstraints();
		gridbg.fill = GridBagConstraints.BOTH;
		gridbg.weightx = 1;
		popper = new ArrayList<JTextField>();
		piper = new ArrayList<JTextArea>();
		boxx = new ArrayList<JComboBox>();
		this.view1 = view1;
		model = view1.getModel();
		
		list1 = new ArrayList<JList>();
		
		
	}

	public void moveTo(String text, int textSize, final String destination, int x, int y) {
		JButton button = new JButton(text);
		button.setFont(new Font("Tahoma", Font.BOLD, textSize));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearComponents();
				view1.switchPanel(destination);
			}
		});
		addComponent(button, x, y);
	}
	

	public void clearComponents() {
		for (JComboBox cb : boxx)
			cb.setSelectedIndex(0);
		for (JTextField tf : popper)
			tf.setText("");
		
		
		for (JList l : list1) 
			l.setListData(new Object[1]);
		
		for (JTextArea ta : piper) {
			ta.setText("");
			ta.setCaretPosition(0);
		}
	}
	
	public JLabel putLbl(String text, int size, String align, Color foreground, Color background, int x, int y) {
		JLabel label = new JLabel(text);
		if (foreground != null)
			label.setForeground(foreground);
		
		if (background != null) {
			label.setBackground(background);
			label.setOpaque(true);
		}
		if (align.equals("left"))
			label.setHorizontalAlignment(SwingConstants.LEFT);
		else if (align.equals("center"))
				label.setHorizontalAlignment(SwingConstants.CENTER);
		else
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		
		label.setFont(new Font("Tahoma", Font.BOLD, size));
		addComponent(label, x, y);
		return label;
	}
	
	public void addComponent(JComponent comp, int x, int y) {
		gridbg.gridx = x; gridbg.gridy = y;
		add(comp, gridbg); addComponent(comp);
	}
	public void addComponent(JComponent comp) {
		
		if (comp instanceof JComboBox)
			{
			boxx.add((JComboBox) comp);
			comp.setBackground(Color.lightGray);
			comp.setForeground(Color.blue);
			}
		if (comp instanceof JTextField)
			{
			popper.add((JTextField) comp);
			comp.setBackground(Color.lightGray);
			comp.setForeground(Color.blue);
			}
		if (comp instanceof JList)
			{
			list1.add((JList) comp);
			comp.setBackground(Color.lightGray);
			comp.setForeground(Color.blue);
			}
		if (comp instanceof JTextArea)
			{
			piper.add((JTextArea) comp);
			comp.setBackground(Color.lightGray);
			comp.setForeground(Color.blue);
			}
		
	}
	
	public void logOutBtn(int textSize, final String backTo, int x, int y) {
		JButton button = new JButton("Sign Out");
		button.setFont(new Font("Tahoma", Font.BOLD, textSize));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setcurrentUserAcc(null);
				clearComponents();
				view1.switchPanel(backTo);
			}
		});
		addComponent(button, x, y);
	}

}
