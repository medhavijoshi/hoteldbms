import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Responsible for Application's GUI 
 */
public class GUI {
	/**
	 * @param mainWindow is the main window containing all the layouts
	 * @param smDate is a simple date format variable that assigns a specific date format to a string
	 * @param view is the main view generated 
	 * @param panelWindows are the panel windows that are navigated from and within
	 * @param arrangeWindows are the windows arranged in the card layout
	 */
	private JFrame mainWindow;
	private SimpleDateFormat smDate = new SimpleDateFormat("MM/dd/yyyy");
	final GUI view = this;
	private Model model;
	private JPanel panelWindows;
	private CardLayout arrangeWindows;

	/**
	 * This method builds the frame and adds all the layouts to it
	 * @param model is the main model that we use here that contains of all the functionalities
	 */
	public GUI(Model model) {
		this.model = model;
		mainWindow = new JFrame("Hotel Management System");
		panelWindows = new JPanel(arrangeWindows = new CardLayout());
		panelWindows.add(getSigninPanel(), "Sign in");
		panelWindows.add(getIdentityPanel(), "Identity");
		panelWindows.add(getRegisterPanel(), "Register");
		panelWindows.add(getPickPanel("Guest"), "Guest");
		panelWindows.add(getPickPanel("Manager"), "Manager");
		panelWindows.add(getPickPanel("Cleaning Service"), "Cleaning Service");
		panelWindows.add(getReserveRoomPanel(), "Book");
		panelWindows.add(getBillPanel(), "Bill");
		panelWindows.add(getCancelViewPanel(), "CancelView");
		panelWindows.add(getOrderCleaningServicePanel(), "Order");
		panelWindows.add(getFeedbackPanel(), "Feedback");
		panelWindows.add(getReservationsPanel(), "Reservations");
		panelWindows.add(getAvgDataPanel(), "Employee Stats");
		panelWindows.add(getArchivePanel(), "History");
		panelWindows.add(getUsersPanel(), "Users");
		panelWindows.add(getArchiveCleaningPanel(), "HistoryCleaning");
		panelWindows.add(getCheckOutPanel(), "Check out");
		panelWindows.add(getFeedbacksPanel(), "Feedbacks");		
		panelWindows.add(getCleaningServicePanel(), "Tasks");
		mainWindow.add(panelWindows); 
		mainWindow.setSize(500, 500);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);
	}

	/**
	 * gets the Archive Cleaning panel
	 * It contains all the cleaning orders that are completed by the cleaners
	 * @return returns the archive cleaning panel component
	 */
	private Component getArchiveCleaningPanel() {
		// TODO Auto-generated method stub
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();

		panel.setBackground(Color.pink);
		gridd.gridwidth = 2;
		gridd.ipady = 30;
		panel.putLbl("Archived Cleaning Orders ", 24, "center", Color.lightGray, Color.BLACK, 0, 0);
		gridd.ipady = 0;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Here are the cleaning orders that have been completed by the cleaners</html>", 12, "left", null, null, 0, 1);

		gridd.gridwidth = 1;
		gridd.weighty = 1;
		
		gridd.gridheight = 10;
		gridd.gridwidth = 20;
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 4);
		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				list.setListData(model.getArchivedCleaningOrders().toArray());
			};
		});

		panel.moveTo("Back to main menu", 12, "Manager", 0, 30); 
		return panel;
	}

	/*
	 * returns the main model
	 */
	public Model getModel() {
		return model;
	}

	/*
	 * switches the panel window to a specified panel
	 */
	public void switchPanel(String panelName) {
		if (panelName.equals("Sign in"))
			mainWindow.setSize(500, 500);
		else 
			mainWindow.setSize(600, 600);
		mainWindow.setLocationRelativeTo(null);
		arrangeWindows.show(panelWindows, panelName);
	}

	/**
	 * gets the Archive Feedbacks panel
	 * It contains all the feedbacks that have been in the database for more than 20 days
	 * @return returns the archive feedback panel component
	 */
	private JPanel getArchivePanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();

		panel.setBackground(Color.pink);
		gridd.gridwidth = 2;
		gridd.ipady = 30;
		panel.putLbl("Archive", 24, "center", Color.LIGHT_GRAY, Color.BLACK, 0, 0);

		gridd.ipady = 0;

		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Here are the feedbacks"
				+ "that have been in the database for more than 20 days.</html>", 12, "left", null, null, 0, 1);

		gridd.gridwidth = 1;
		gridd.weighty = 1;
		
		gridd.gridheight = 10;
		gridd.gridwidth = 20;
		final JTextArea list = new JTextArea();
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		list.setEditable(false);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 3, 1);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = "";
				ArrayList<Feedback> feedbacks = model.getArchivedFeedbacks();
				if (feedbacks != null){
					output += "Number of feedbacks: " + feedbacks.size();
					for (Feedback c : feedbacks)
						output += "\n\n" + "Guest Name: " + c.getGuest() +"\n"+ "Feedback: " + c.getFeedback();
					list.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});


		panel.moveTo("Back to main menu", 12, "Manager", 0, 4);
		return panel;
	}

	/**
	 * gets the Sign in panel so that the user can login 
	 * @return returns the sign in panel component
	 */
	private JPanel getSigninPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		gridd.weighty = 1;
		gridd.gridwidth = 2;
		gridd.ipady = 15;
		panel.setBackground(Color.pink);
		panel.putLbl("Sign In", 36, "center", Color.LIGHT_GRAY, Color.BLACK, 0, 0);

		gridd.insets = new Insets(10,15,5,15);
		gridd.weightx = 0;
		gridd.gridwidth = 1;
		gridd.gridheight = 1;
		gridd.ipady = 0;
		panel.putLbl("Enter Username:", 18, "center", Color.blue, null, 0, 2);
		final JTextField usernameField = new JTextField();
		panel.addComponent(usernameField, 1, 2);

		gridd.insets = new Insets(5,15,5,15);

		panel.putLbl("Enter Password:", 18, "center", Color.blue, null, 0, 3);
		final JPasswordField passwordField = new JPasswordField();
		panel.addComponent(passwordField, 1, 3);

		gridd.gridwidth = 2;
		JButton signinBtn = new JButton("Sign In");
		signinBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		signinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				if (username.length() < 6 || username.length() > 12) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Invalid username!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!model.checkPresence(username)) {	
					JOptionPane.showMessageDialog(new JFrame(), 
							"This username does not not exist.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!model.matchPassword(username, password)) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"Incorrect password!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					panel.clearComponents();
					model.setcurrentUserAcc(username);
					view.switchPanel(model.getCurrentIdentity());
				}
			}
		});
		panel.addComponent(signinBtn, 0, 4);

		panel.moveTo("Register", 14, "Identity", 0, 5);

		return panel;
	}

	/**
	 * gets the Identity panel so that the user can select what its identity is: (Guest, Manager or Cleaner) 
	 * @return returns the identity panel component
	 */
	private JPanel getPickPanel(String identity) {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		gridd.weighty = 1;
		gridd.insets = new Insets(10,10,10,10);
		panel.setBackground(Color.pink);

		final JLabel profile = new JLabel();
		profile.setForeground(Color.blue);
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event)
			{
				if (model.getcurrentUserAcc() != null) {
					UserAccount user = model.getcurrentUserAcc();
					profile.setText("<html>Welcome! " + user.getFirstName() 
					+ "<br>Username: " + user.getUsername()
					+ "<br>Identity: " + user.getIdentity());
					
				}
			}
		});
		panel.addComponent(profile, 0, 0);

		panel.logOutBtn(14, "Sign in", 1, 0);

		gridd.gridwidth = 2;
		if (identity.equalsIgnoreCase("manager")) {
			panel.moveTo("Bookings", 12, "Reservations", 0, 1);
			panel.moveTo("Feedbacks", 12, "Feedbacks", 0, 2); 
			panel.moveTo("All Users", 12, "Users", 0, 3);
			panel.moveTo("Average Figures", 12, "Employee Stats", 0, 4);
			panel.moveTo("Feedback Archives", 12, "History", 0, 5);
			panel.moveTo("Cleaning Service History", 12, "HistoryCleaning", 0, 6);
			panel.moveTo("Check Out a Guest", 12, "Check out", 0, 7);
		}
		else if (identity.equalsIgnoreCase("guest")) {
			panel.moveTo("Book a reservation", 14, "Book", 0, 1);
			panel.moveTo("View or Cancel Bookings", 14, "CancelView", 0, 2);
			panel.moveTo("Order Cleaning Service", 14, "Order", 0, 3);
			panel.moveTo("Write a Feedback", 14, "Feedback", 0, 4);
		}
		else if (identity.equalsIgnoreCase("cleaning service")) {
			panel.moveTo("Services To Do", 14, "Tasks", 0, 1);
		}

		return panel;
	}

	/**
	 * gets the reservation panel so that the user can make bookings 
	 * @return returns the reservation panel component
	 */
	private JPanel getReserveRoomPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		gridd.gridwidth = 2;
		panel.setBackground(Color.pink);
		gridd.ipady = 30;
		panel.putLbl("Reserve a Room", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.insets = new Insets(10, 10, 10, 10);
		gridd.gridwidth = 1;
		gridd.ipady = 0;
		panel.putLbl("Check-in (MM/DD/YYYY):", 12, "left", null, null, 0, 2);
		panel.putLbl("Check-out (MM/DD/YYYY):", 12, "left", null, null, 0, 3);

		gridd.gridwidth = 1;
		final JTextField checkIn = new JTextField();
		panel.addComponent(checkIn, 1, 2);

		final JTextField checkOut = new JTextField();
		panel.addComponent(checkOut, 1, 3);

		gridd.gridwidth = 2;
		gridd.weighty = 1;
		@SuppressWarnings({ "rawtypes"})
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 4);

		gridd.weighty = 0;

		JButton searchBtn = new JButton("See available rooms");
		searchBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		searchBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override() 
			public void actionPerformed(ActionEvent e) {
				list.setListData(new Object[1]);
				String in = checkIn.getText();
				String out = checkOut.getText();
				GregorianCalendar inCal;
				GregorianCalendar outCal;
				if (in.length() == 10 && out.length() == 10) {
					inCal = checkDateformat(in);
					outCal = checkDateformat(out);
					if (inCal != null && outCal != null) {
						if (inCal.before(Model.TODAY) || outCal.before(Model.TODAY))
							JOptionPane.showMessageDialog(new JFrame(),
									"Please enter a date that is today or later than today :P", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (outCal.before(inCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"Check in date is after check out date :(", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (calculateDays(inCal, outCal) > 60)
							JOptionPane.showMessageDialog(new JFrame(),
									"cannot make this reservation :(", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (inCal.equals(outCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"check in day is the same as check out day :(", "Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							if (model.getAvailableRooms(in, out) != null)
								list.setListData(model.getAvailableRooms(in, out).toArray());
						}
					}
					else 
						JOptionPane.showMessageDialog(new JFrame(),
								"Invalid format detected!", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(searchBtn, 2, 3);

		gridd.gridwidth = 1;
		JButton confirmBtn = new JButton("Select Room");
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Room room = (Room)list.getSelectedValue();
				if (room != null) {
					try {
						if (model.addReservation(room.getRoomId(), checkIn.getText(), checkOut.getText())) {
							int response = JOptionPane.showConfirmDialog(
									new JFrame(), "<html>The room is booked! :)<br>"
											+ "More bookings?</html>",
											"Confirmation", JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.NO_OPTION) switchPanel("Bill");
							if (response == JOptionPane.YES_OPTION) ;
							panel.clearComponents();
						}
					} catch (HeadlessException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(new JFrame(),
							"Please select a room.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(confirmBtn, 0, 6);

		JButton doneBtn = new JButton("Finish");
		doneBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getReservations().isEmpty()) 
					JOptionPane.showMessageDialog(new JFrame(),
							"There are no bookings.", "Error",
							JOptionPane.ERROR_MESSAGE);
				else {
					panel.clearComponents();
					switchPanel("Bill");
				}
			}
		});
		panel.addComponent(doneBtn, 1, 6);

		gridd.gridwidth = 2;
		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getReservations().isEmpty()) {
					panel.clearComponents();
					view.switchPanel("Guest");
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Finish the current transaction before moving forward", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(backBtn, 2, 6);

		return panel;
	}
	
	/**
	 * gets the identity panel so that the user can select guest, manager or cleaning service options 
	 * @return returns the identity panel component
	 */
	private JPanel getIdentityPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		gridd.weighty = 1;

		panel.setBackground(Color.pink);
		panel.putLbl("Pick a category", 24, "center", Color.LIGHT_GRAY, Color.BLACK, 0, 0);

		gridd.insets = new Insets(10,25,10,25);
		JButton custBtn = new JButton("Guest");
		custBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		custBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setCurrentIdentity("Guest");
				view.switchPanel("Register");
			}
		});
		panel.addComponent(custBtn, 0, 1);

//		JButton mgrBtn = new JButton("Manager");
//		mgrBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
//		mgrBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				model.setCurrentIdentity("Manager");
//				view.switchPanel("Register");
//			}
//		});
//		panel.addComponent(mgrBtn, 0, 2);

		JButton rsBtn = new JButton("Cleaning Service");
		rsBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setCurrentIdentity("Cleaning Service");
				view.switchPanel("Register");
			}
		});
		panel.addComponent(rsBtn, 0, 3);

		panel.moveTo("Back", 20, "Sign in", 0, 11);

		return panel;
	}
	
	/**
	 * gets the Bills panel so that the user can see the transaction receipt 
	 * @return returns the bill panel component
	 */
	private JPanel getBillPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.gridwidth = 1;
		gridd.ipady = 30;
		panel.putLbl("Bill", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.weighty = 1;
		gridd.insets = new Insets(10,10,10,10);
		final JTextArea Bill = new JTextArea();
		Bill.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(Bill,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) Bill.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 2);
		panel.addComponent(Bill);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!model.getReservations().isEmpty()) {
					UserAccount user = model.getcurrentUserAcc();
					String text = "Username: " + user.getUsername() + "\nName: " + user.getFirstName() 
					+ " " + user.getLastName() + "\nReservations made: " + model.getReservations().size();

					double price = 0;
					int i = 1;
					for (Reservation r : model.getReservations()) {
						text += String.format("\n\nReservation # %d\n%s", i, r.toString());
						price += r.getTotalPrice();
						i++;
					}

					text += String.format("\n\nTotal: $%.2f", price);

					Bill.setText(text);
				}
			};
		});

		gridd.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearReservations();
				panel.clearComponents();
				view.switchPanel("Guest");
			}
		});
		panel.addComponent(backBtn, 0, 3);
		return panel;
	}
	
	/**
	 * gets the users panel to see all the users (Manager view) 
	 * @return returns the users panel component
	 */
	private JPanel getUsersPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.gridwidth = 2;
		gridd.ipady = 30;
		panel.putLbl("Users", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.gridwidth = 2;
		gridd.weighty = 0;
		JButton searchBtn = new JButton("View All Users");
		searchBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		panel.addComponent(searchBtn, 0, 3);
		
		JButton searchGuestsBtn = new JButton("View All Guest Users");
		searchGuestsBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		panel.addComponent(searchGuestsBtn, 0, 4);

		gridd.weighty = 1;
		final JTextArea list = new JTextArea();
		list.setEditable(false);
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) list.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 5);
		panel.addComponent(list);

		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer num = null;

				try {
					ArrayList<UserAccount> users = model.getSpecificResUsers(num);
					if (users != null)
						list.setText(formatAllUsers(users));
					else 
						JOptionPane.showMessageDialog(new JFrame(), 
								"There is an error. Kindly check your selections.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Invalid input", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		searchGuestsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer num = null;

				try {
					ArrayList<UserAccount> users = model.getSpecificResUsers(num);
					if (users != null)
						list.setText(formatGuestUsers(users));
					else 
						JOptionPane.showMessageDialog(new JFrame(), 
								"There is an error. Kindly check your selections.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Invalid input", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		gridd.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentIdentity());
			}
		});
		panel.addComponent(backBtn, 0, 6);

		return panel;
	}

	/**
	 * gets the view or cancel panel so that the user can view or cancel a room reservation
	 * @return returns the view or cancel panel component
	 */
	private JPanel getCancelViewPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.gridwidth = 1;
		gridd.ipady = 30;
		panel.putLbl("View or Cancel a Booking", 24, "center", Color.lightGray,Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Below are all your bookings.<br>To cancel a "
				+ "booking: Click on the one you want to cancel. Click Cancel.<br>"
				+ "If you have no bookings, then you'd see an empty box below.</html>", 12, "left", 
				null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		gridd.weighty = 1;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<Reservation> notCanceled = new ArrayList<Reservation>();
				if (model.getcurrentUserAcc() != null) {
					for (Reservation r : model.getcurrentUserAcc().getReservations())
						if (!r.getNotBooked())
							notCanceled.add(r);

					list.setListData(notCanceled.toArray());
				}
				else list.setListData(new Reservation[0]);
			}
		});

		gridd.weighty = 0;
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to cancel this reservation?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						if (!model.cancelReservation((Reservation) list.getSelectedValue()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"There is an error. Kindly check your selections.", "Error", 
									JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
		});
		panel.addComponent(cancelButton, 0, 3);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Guest");
			}
		});
		panel.addComponent(backBtn, 0, 4);
		return panel;
	}

	/**
	 * gets all the reservations from the users (Manager view)
	 * @return returns the reservation panel component
	 */
	private JPanel getReservationsPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.gridwidth = 4;
		gridd.ipady = 30;
		gridd.weighty = 0;
		panel.putLbl("Reservations", 24, "center", Color.lightGray, Color.BLACK, 0, 0);

		gridd.gridwidth = 2;
		JButton roomBtn = new JButton("Arrange acccording to Room numbers");
		roomBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		panel.addComponent(roomBtn, 0, 3);
		JButton customerBtn = new JButton("Arrange acccording to Guests");
		roomBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		panel.addComponent(customerBtn, 0, 2);

		gridd.weightx = 1;
		gridd.gridwidth = 4;
		gridd.weighty = 1;
		gridd.insets = new Insets(10,10,10,10);
		final JTextArea list = new JTextArea();
		list.setEditable(false);
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) list.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 4);
		panel.addComponent(list);

		roomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				try {
					
					ArrayList<Reservation> res = model.getManagerReservations("types", min, max);
					if (res != null)
						list.setText(formatReservations(res));
					else 
						JOptionPane.showMessageDialog(new JFrame(), 
								"There is an error. Kindly check your selections.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Invalid input", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		customerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				ArrayList<Reservation> res = model.getManagerReservations("guest", min, max);
				if (res != null)
					list.setText(formatReservations(res));
				else
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
			}
		});

		gridd.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentIdentity());
			}
		});
		panel.addComponent(backBtn, 0, 5);

		return panel;
	}

	
	/**
	* Get view of cleaning service requested by Guest
	 * @return returns the reservation panel component
	 */
	private JPanel getViewCleaningServicePanel(){
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);
		gridd.weightx = 1;
		gridd.weighty = 0;

		panel.putLbl("View of Cleaning Service (Guest)", 16, "center", null, null, 0, 0);

		gridd.gridwidth = 20;
		final JTextArea list = new JTextArea();
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		list.setEditable(false);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 1);

		
		

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = "";
				ArrayList<CleaningService> clean = model.getCleaningService();
				if (clean != null){
					output += "Number of tasks: " + clean.size();
					for (CleaningService c : clean)
						output += "\n\n" + "Task Id: " + c.getTaskId() +  " Room Id: " + c.getRoomID() + " Task: " + c.getTask();
					list.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		panel.moveTo("Back", 16, "Cleaning Service", 0, 30);
		return panel;
	}

	/**
	* Gets the view of register panel requested by Guest/Manager/CleaningService
	 * @return returns the register panel component
	 */
	private JPanel getRegisterPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		gridd.weighty = 1;
		gridd.gridwidth = 3;
		panel.setBackground(Color.pink);

		gridd.weightx = 0;
		gridd.insets = new Insets(5,25,5,25);
		gridd.gridwidth = 1;
		panel.putLbl("Enter first name", 14, "left", null, null, 0, 1);
		panel.putLbl("Enter last name", 14, "left", null, null, 0, 3);
		panel.putLbl("Enter username", 14, "left", null, null, 0, 5);
		panel.putLbl("Enter password (min 8 characters long)", 14, "left", null, null, 0, 7);
		panel.putLbl("Select age", 14, "left", null, null, 0, 9);
		panel.moveTo("Back to previous menu", 12, "Identity", 0, 11);

		gridd.weightx = 1;
		gridd.gridwidth = 3;
		final JTextField firstName = new JTextField();
		panel.addComponent(firstName, 0, 2);

		final JTextField lastName = new JTextField();
		panel.addComponent(lastName, 0, 4);

		final JTextField username = new JTextField();
		panel.addComponent(username, 0, 6);

		final JPasswordField password = new JPasswordField();
		panel.addComponent(password, 0, 8);

		List<String> age = new ArrayList<String>();
		age.add("Select Age");
		for (int i = 18; i < 100; ++i)
			age.add(String.valueOf(i));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox ageComboBox = new JComboBox(age.toArray());
		panel.addComponent(ageComboBox, 0, 10);

		JButton registerBtn = new JButton("Register");
		registerBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "<html>Make sure what you entered is valid:<br>";

				String first = firstName.getText();
				if (first.isEmpty() || first.length() > 15) {
					firstName.setText("");
					validEntry = false;
					errors += "First name<br>";
				}

				String last = lastName.getText();
				if (last.isEmpty() || last.length() > 15) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name<br>";
				}

				String login = username.getText();
				if (login.length() < 6 || login.length() > 12 || model.checkPresence(login)) {
					username.setText("");
					validEntry = false;
					errors += "Username<br>";
				}

				String pass = new String(password.getPassword()); 
				if (pass.length() > 20 && pass.length() < 8) {
					password.setText("");
					validEntry = false;
					errors += "Password<br>";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageComboBox.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age<br>";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.addUserAccount(login, pass, first, last, age, model.getCurrentIdentity()))
						view.switchPanel(model.getCurrentIdentity());
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"There is an error. Kindly check your selections.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerBtn, 1, 11);

		return panel;
	}
	
	/**
	* Lets a guest write a feedback for the stay.
	 * @return returns the feedbacks panel component
	 */
	private JPanel getFeedbackPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.weighty = 1;
		gridd.ipady = 30;
		panel.putLbl("Feedback", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>We appreciate your feedback. "
				+ "<br>Please take a few minutes and "
				+ "write us a review.<html>", 12, "left", null, null, 0, 1);

		final JTextArea complaint = new JTextArea();
		complaint.setWrapStyleWord(true);
		complaint.setLineWrap(true);
		panel.addComponent(complaint, 0, 2);

		gridd.weighty = 0;
		panel.moveTo("Back", 16, "Guest", 0, 3);

		JButton SumbitButton = new JButton("Sumbit");
		SumbitButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		SumbitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String complaintTest = complaint.getText();
				if (complaintTest.isEmpty() || complaintTest.length() < 1 || complaintTest.length() > 100) 
					validEntry = false;

				if (validEntry) {
					panel.clearComponents();
					if (model.addFeedback(model.getcurrentUserAcc().getUsername(), complaintTest)) {	
						JOptionPane.showMessageDialog(new JFrame(), "Thanks! Your feedback has been received.", 
								"Result", JOptionPane.DEFAULT_OPTION);
						view.switchPanel(model.getCurrentIdentity());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"There is an error. Kindly check your selections.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), "enter feedback between 1 and 100 characters.", 
							"Error", JOptionPane.ERROR_MESSAGE);
			};
		});
		panel.addComponent(SumbitButton, 0, 4);

		return panel;
	}

	/**
	* Gets the view of average statistics/data requested by Manager
	 * @return returns the average data panel component
	 */
	private JPanel getAvgDataPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.weighty = 1;
		gridd.ipady = 10;
		panel.putLbl("Average Figures", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Below are the results of some useful calculated averages: <html>", 12, "center", Color.LIGHT_GRAY, Color.black, 0, 1);

		final JTextArea data1 = new JTextArea();
		data1.setWrapStyleWord(true);
		data1.setLineWrap(true);
		data1.setLineWrap(true);
		data1.setWrapStyleWord(true);
		data1.setOpaque(false);
		data1.setEditable(false);
		data1.setBorder(new EmptyBorder(10,10,2,2));
		data1.setBackground(Color.black);
		data1.setForeground(Color.LIGHT_GRAY);
		panel.addComponent(data1, 0, 2);

		gridd.weighty = 0;
		gridd.gridx = 1;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Manager");
			}
		});
		panel.addComponent(backBtn, 0, 3);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = model.getAverages();
				if (output != null){
					data1.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		return panel;
	}
	
	/**
	* Get view of all the feedbacks provided by Guests (Manager view)
	 * @return returns the feedbacks panel component
	 */
	private JPanel getFeedbacksPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.ipady = 30;
		gridd.gridwidth = 2;
		panel.putLbl("Guest Feedbacks", 24, "center", Color.lightGray, Color.BLACK, 0, 0);

		gridd.weightx = 0;
		gridd.ipady = 0;
		gridd.gridheight = 100;
		gridd.gridwidth = 1;
		gridd.insets = new Insets(10,10,10,10);
		
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel(model.getCurrentIdentity());
			}
		});
		panel.addComponent(backBtn, 0, 30);

		
		gridd.gridheight = 10;
		gridd.gridwidth = 20;
		final JTextArea list = new JTextArea();
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		list.setEditable(false);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 1);

		
		

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = "";
				ArrayList<Feedback> feedbacks = model.getFeedback();
				if (feedbacks != null){
					output += "Number of feedbacks: " + feedbacks.size();
					for (Feedback c : feedbacks)
						output += "\n\n" + c.toString();
					list.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		return panel;
	}

	/**
	* Get view of cleaning service requested by Guest
	 * @return returns the cleaning service panel component
	 */
	private JPanel getCleaningServicePanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.ipady = 20;
		panel.putLbl("Cleaning Service Tasks", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.weightx = 0;
		gridd.ipady = 0;
		gridd.gridwidth = 1;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("Select a task", 12, "left", null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		gridd.weighty = 1;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<CleaningService> current = model.getCleaningService();
				if (current != null) 
					list.setListData(current.toArray());
				else {
					list.setListData(new CleaningService[0]);
					JOptionPane.showMessageDialog(new JFrame(), 
							"There is an error. Kindly check your selections.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton submitBtn = new JButton("Complete");
		submitBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to complete this task?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						CleaningService rs = (CleaningService)list.getSelectedValue();
						if (!model.completeTask(rs.getTask(),rs.getTaskId(), rs.getRoomID(), rs.getResId(), rs.getPrice()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"There is an error. Kindly check your selections.", "Error", 
									JOptionPane.ERROR_MESSAGE);
					}
				}
			};
		});
		panel.addComponent(submitBtn, 0, 3);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Cleaning Service");
			}
		});
		panel.addComponent(backBtn, 0, 4);
		
		return panel;
	}

	/**
	* Get view of reservations (Manager view) and Manager can check out any guest accordingly
	 * @return returns the check out panel component
	 */
	private JPanel getCheckOutPanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();
		panel.setBackground(Color.pink);

		gridd.ipady = 30;
		panel.putLbl("Check Out", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.gridwidth = 1;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Select a reservation to check out.</html>", 12, "left", null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		gridd.weighty = 1;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<Reservation> current = model.getCheckedoutRes();
				if (current != null) 
					list.setListData(current.toArray());
				else list.setListData(new Reservation[0]);
			}
		});

		gridd.weighty = 0;
		JButton checkOutBtn = new JButton("Check Out");
		checkOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to check out this reservation?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						if (!model.cancelReservation((Reservation) list.getSelectedValue()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"There is an error. Kindly check your selections.", "Error", 
									JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
		});
		panel.addComponent(checkOutBtn, 0, 3);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Manager");
			}
		});
		panel.addComponent(backBtn, 0, 4);
		return panel;
	}

	/*
	 * checks the specified date format
	 */
	private GregorianCalendar checkDateformat(String input) {
		try {
			smDate.setLenient(false);
			GregorianCalendar cal = new GregorianCalendar();
			Date d = smDate.parse(input);
			cal.setTime(d);

			return cal;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * calculates the number of days between the provided dates
	 */
	private int calculateDays(GregorianCalendar checkIn, GregorianCalendar checkOut) {
		GregorianCalendar days = (GregorianCalendar) checkIn.clone();
		int num = 0;
		while (!days.equals(checkOut)) {
			days.add(Calendar.DATE, 1);
			num++;
		}
		return num;
	}

	/*
	 * formats the reservations to be displayed a certain way
	 */
	private String formatReservations(ArrayList<Reservation> res) {
		String result = "Total reservations: " + res.size();
		if (!res.isEmpty()) {
			for (Reservation r : res) {
				String in = smDate.format(r.getBeginDate());
				String out = smDate.format(r.getEndDate());

				result += "\n\nReservation # " + r.getReservationId()
				+ "\nUsername: " + r.getGuest()
				+ "\nRoom: " + r.getRoom().getRoomType()
				+ "\nCheck In: " + in
				+ "\nCheck Out: " + out
				+ "\nPrice: $" + Double.toString(r.getTotalPrice());

				if (r.getNotBooked())
					result += "\nThis reservation has been canceled";
			}
		}
		return result;
	}

	/*
	 * formats the username, first name and the identity to be displayed when logging in
	 */
	private String formatAllUsers(ArrayList<UserAccount> users) {
		String result = "Total users: " + users.size();
		if (!users.isEmpty()) {
			for (UserAccount a : users) {
				result += "\n\nUsername: " + a.getUsername()
				+ "\nName: " + a.getFirstName() + " " + a.getLastName()
				+ "\nUser identity: " + a.getIdentity();
				if (a.getIdentity().equals("Guest")) {
					int count = 0;
					for (Reservation r : a.getReservations())
						if (!r.getNotBooked()) count++;
					result += "\nNumber of Reservations: " + count;
				}
			}
		}
		return result;
	}
	
	/*
	 * formats all the guest users to be displayed in the view Users panel (Manager view)
	 */
	private String formatGuestUsers(ArrayList<UserAccount> users) {
		
		int num = 0;
		String result = "Below are all the reservations arranged according to the guest names:";
		if (!users.isEmpty()) {
			for (UserAccount a : users) {
				
				if (a.getIdentity().equals("Guest")) {
					int count =0;
					num++;
					for (Reservation r : a.getReservations())
						if (!r.getNotBooked()) count++;
					result += "\n\n Guest: " + a.getFirstName() + "\n Number of Reservations: " + count;
				}
				
			}
		}
		return result;
	}

	/**
	* Get view of ordering cleaning service requested by the Guest
	 * @return returns the ordering cleaning service panel component
	 */
	private JPanel getOrderCleaningServicePanel() {
		final GUIStruct panel = new GUIStruct(this);
		GridBagConstraints gridd = panel.getConstraints();

		panel.setBackground(Color.pink);
		gridd.ipady = 10;
		gridd.gridwidth = 2;
		panel.putLbl("Order Cleaning Service", 24, "center", Color.lightGray, Color.black, 0, 0);

		gridd.ipady = 0;
		gridd.insets = new Insets(10,10,10,10);
		panel.putLbl("<html>Select reservation and choose cleaning service.<br>"
				+ "Reservation must be within 20 days from today.</html>", 12, "left", null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setFixedCellHeight(16);
		JScrollPane listScroller = new JScrollPane(list);
		gridd.weighty = 3;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		List<String> Item = new ArrayList<String>();
		Item.add("Select a service");
		Item.add("Bedding");
		Item.add("Toiletries");
		Item.add("FloorCleaning");
		final JComboBox ServiceComboBox = new JComboBox(Item.toArray());
		panel.addComponent(ServiceComboBox, 0, 3);

		gridd.gridwidth = 1;
		JButton submitBtn = new JButton("Order");
		submitBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = (String)ServiceComboBox.getSelectedItem();
				double price = 0;
				if (service.equals("Bedding"))
					price = 10;
				else if (service.equals("FloorCleaning"))
					price = 15;
				else if (service.equals("Toiletries"))
					price = 20;
				else 
					JOptionPane.showMessageDialog(new JFrame(), 
							"Please pick a service.", "Error", 
							JOptionPane.ERROR_MESSAGE);

				if (!list.isSelectionEmpty() && price != 0) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Please confirm your selection :)",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						if (!model.addCleaningService((Reservation)list.getSelectedValue(), service, price)) 
							JOptionPane.showMessageDialog(new JFrame(), 
									"There is an error. Kindly check your selections.", "Error", 
									JOptionPane.ERROR_MESSAGE);
						else
							{
							JOptionPane.showMessageDialog(new JFrame(), 
									"Cleaning service ordered", "Result", JOptionPane.DEFAULT_OPTION);}
					}
				}

			}
		});
		panel.addComponent(submitBtn, 1, 4);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<Reservation> notCanceled = new ArrayList<Reservation>();
				if (model.getcurrentUserAcc() != null) {
					for (Reservation r : model.getcurrentUserAcc().getReservations()) {						
							notCanceled.add(r);
					}
					list.setListData(notCanceled.toArray());
				}
				else list.setListData(new Reservation[0]);
			}
		});

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Guest");
			}
		});
		panel.addComponent(backBtn, 0, 4);

		return panel;
	}
}