import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Responsible for DB Interactions
 */
public class Model {
	// Variable for the Gregorian calendar object
	public static final GregorianCalendar TODAY = new GregorianCalendar();
	// Varibales used to make connection to the mysql database using Connection utility class
	private Connection connection = ConnectionUtility.getConnectionByDriverManager();
	private Statement statement = ConnectionUtility.getStatement(connection);
	// variables for utility of this model
	private UserAccount currentUserAcc;
	private String curIdentity;
	private ArrayList<Reservation> reservations;
	private ArrayList<ChangeListener> actlisteners;

	/**
	 * Constructs the database. Loads the serialized accounts and reservations.
	 */
	public Model() {
		TODAY.clear(Calendar.HOUR);
		TODAY.clear(Calendar.MINUTE);
		TODAY.clear(Calendar.SECOND);
		TODAY.clear(Calendar.MILLISECOND);

		actlisteners = new ArrayList<>();
		currentUserAcc = null;
		curIdentity = null;
		reservations = new ArrayList<Reservation>();
	}

	/**
	 * Adds a reservation made by a user to the hotel management database
	 * @param variables are the ones needed to create a reservation using the current User's account
	 * @return returns true is the reservation is made otherwise false
	 */
	public boolean addReservation(int roomId, String checkIn, String checkOut) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date d1 = fm.parse(checkIn);
		Date d2 = fm.parse(checkOut);
		long diff = Math.abs(d2.getTime() - d1.getTime());
		long resDate = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		int numOfDays = (int) resDate;
		String sqlQuery = String.format("insert into reservations(roomId, guest, startDate, endDate, numOfDays) values ('%s','%s',%s,%s,%s)",
				roomId, currentUserAcc.getUsername(), sqlToDate(checkIn), sqlToDate(checkOut), numOfDays);
		try {
			statement.execute(sqlQuery);
			setcurrentUserAcc(currentUserAcc.getUsername());
			ArrayList<Reservation> res = currentUserAcc.getReservations();
			reservations.add(res.get(res.size() - 1));
			update();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Adds a a user account to the hotel management database
	 * @param username, password, firstname, lastname, age and identity are variables needed to create the account
	 * @return returns true is the account is added otherwise false
	 */
	public boolean addUserAccount(String username, String password, String firstName, String lastName, int age, String identity) {		
		username = username.replace("'", "''");
		password = password.replace("'", "''");
		firstName = firstName.replace("'", "''");
		lastName = lastName.replace("'", "''");
		String sqlQuery = String.format("INSERT INTO USER(userName,password,firstName,lastName,age,userRole)"
				+ " VALUES('%s','%s','%s','%s',%s,'%s')", 
				username, password, firstName, lastName, age, identity);

		try {
			statement.execute(sqlQuery);
			setcurrentUserAcc(username);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * Gets all reservation made by the user to the hotel management database
	 * @return returns an array list of reservations by that user
	 */
	public ArrayList<Reservation> getAllReservations() {
		ArrayList<Reservation> resList = new ArrayList<Reservation>();

		String operation = "select canceled, guest, resId, room.roomId, startDate, endDate, numOfDays, totalPrice, pricepernight, types "
				+ "from room right outer join reservation on room.roomid = reservation.roomid ";
		try {
			ResultSet rs = statement.executeQuery(operation);
			while (rs.next()) {
				Room room = new Room(rs.getInt("roomid"), rs.getDouble("pricePerNight"), rs.getString("types"));
				Reservation res = new Reservation(rs.getInt("resId"), rs.getString("guest"), room, rs.getDate("startdate"), rs.getDate("enddate"), rs.getInt("numOfDays"), rs.getDouble("totalPrice"), rs.getBoolean("canceled"));
				resList.add(res);
			}
			rs.close();
			return resList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets all reservation that were checked out of the hotel management database
	 * @return returns an array list of the reservations that were checked out 
	 */
	public ArrayList<Reservation> getCheckedoutRes() {
		ArrayList<Reservation> resList = new ArrayList<Reservation>();

		String operation = "select canceled, guest, resId, room.roomId, startDate, endDate, numOfDays, totalPrice, pricePerNight, types from room right outer join reservations on room.roomid = reservations.roomid where canceled <> true";
		try {
			ResultSet rs = statement.executeQuery(operation);
			while (rs.next()) {
				Room room = new Room(rs.getInt("roomid"), rs.getDouble("pricePerNight"), rs.getString("types"));
				Reservation res = new Reservation(rs.getInt("resId"), rs.getString("guest"), room, rs.getDate("startdate"), rs.getDate("enddate"), rs.getInt("numOfDays"), rs.getDouble("totalPrice"), rs.getBoolean("canceled"));
				resList.add(res);
			}
			rs.close();
			return resList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets all the reservations made to the hotel management database
	 * @return returns an array list of reservations by all users
	 */
	public ArrayList<Reservation> getManagerReservations(String orderBy, Double min, Double max) {
		ArrayList<Reservation> full = new ArrayList<Reservation>();

		String operation = "select canceled, guest, resId, room.roomId, startDate, endDate, numOfDays, totalPrice, pricePerNight, types from room right outer join reservations on room.roomId = reservations.roomId";

		operation += " order by " + orderBy;

		try {
			ResultSet rs = statement.executeQuery(operation);
			while (rs.next()) {
				Room room = new Room(rs.getInt("roomId"), rs.getDouble("pricePerNight"), rs.getString("types"));
				Reservation res = new Reservation(rs.getInt("resId"), rs.getString("guest"), room, rs.getDate("startdate"), rs.getDate("enddate"), rs.getInt("numOfDays"), rs.getDouble("totalPrice"), rs.getBoolean("canceled"));
				full.add(res);
			}
			rs.close();
			return full;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sets current user to the specified username to the hotel management database
	 */
	public void setcurrentUserAcc(String username) {
		if (username == null) {
			currentUserAcc = null;
			curIdentity = null;
		}
		else {
			currentUserAcc = getCurUserAccount(username);
			curIdentity = currentUserAcc.getIdentity();
		}
		update();
	}
	

	public boolean addFeedback(String guest, String complaintTest) {	
		String sqlQuery = String.format("INSERT INTO FEEDBACK(guest,feedback)"
				+ " VALUES('%s','%s')", 
				currentUserAcc.getUsername(), complaintTest);

		try {
			statement.execute(sqlQuery);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Feedback> getFeedback() {
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		String sqlQuery = "SELECT * FROM FEEDBACK";

		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				feedbacks.add(new Feedback(rs.getInt("feedbackID"), rs.getString("guest"),rs.getString("feedback"),
						rs.getDate("time")));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return feedbacks;
	}
	

	/**
	 * Cancels a reservation made by a user to the hotel management database
	 * @param variables are the ones needed to cancel a reservation using the current User's account
	 * @return returns true is the reservation is canceled otherwise false
	 */
	public boolean cancelReservation(Reservation reserve) {
		String sqlQuery = "UPDATE reservations SET canceled = true where resId = " + reserve.getReservationId(); 
		try {
			statement.execute(sqlQuery);
			setcurrentUserAcc(currentUserAcc.getUsername());
			update();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getAverages() {
		String avgAge = "select avg(age) from user", 
		avgNumRes = "select avg(counter) from (select count(*) as counter from reservations group by guest) counts",
		avgPrice = "select avg(pricePerNight) from room", result = "";
	//avgTotalResPrice = "select avg(totalPrice) from reservations";
		try {
			ResultSet rs = statement.executeQuery(avgAge);
			if (rs.next())
				result += "Mostly users were of age: " + rs.getString(1);
			rs.close();
			rs = statement.executeQuery(avgNumRes);
			if (rs.next())
				result += "\n\n********************************************************************\n\nNumber of reservations made by guests at an average: " + rs.getString(1);
			rs.close();
			rs = statement.executeQuery(avgPrice);
			if (rs.next())
				result += "\n\n********************************************************************\n\nAverage price of rooms: " + rs.getString(1);
			rs.close();
//			rs = statement.executeQuery(avgTotalResPrice);
//			if (rs.next())
//				result += "\nAverage total price of reservations: " + rs.getString(1);
//			rs.close();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addCleaningService(Reservation r, String task, double price) {	
		String sqlQuery = String.format("INSERT INTO CleaningSERVICE(task,roomId,resId,price)"
				+ " VALUES('%s','%d','%d','%f')", 
				task, r.getRoom().getRoomId(), r.getReservationId(), price);

		try {
			statement.execute(sqlQuery);
			update();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<CleaningService> getCleaningService() {
		String sqlQuery = "select * from cleaningservice left outer join "
				+ "reservations on cleaningservice.resId = reservations.resId ";
		ArrayList<CleaningService> roomservice = new ArrayList<CleaningService>();
		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("completedBy") == null)
					roomservice.add(new CleaningService(rs.getInt("cleaningId"), rs.getString("task"), rs.getInt("roomId"), rs.getInt("resId"), rs.getDate("time"), rs.getDouble("price")));
			}
			return roomservice;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean completeTask(String task, int id, int roomId, int resId, double price) {
		String sql = "insert into archive_cleaningservice (roomId, task, resId, price) values ('" + roomId + "', '"+ task +"', ' "+ resId + "', '" + price +"')" ;
		String sqlQuery = "delete from cleaningservice where cleaningid = " + id;
		try {
			statement.execute(sql);
			statement.execute(sqlQuery);
			update();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * converts the string to date format
	 * @param date is the date string
	 * @return returns the date
	 */
	public String sqlToDate(String date) {
		return "str_to_date('" + date + "', '%m/%d/%Y')";
	}
	
	/**
	 * gets the current identity of the user
	 * @return returns the current identity
	 */
	public String getCurrentIdentity() {
		return curIdentity;
	}
	
	/**
	 * sets the user identity to the specified role
	 * @param identity is the role the identity is to be set to
	 */
	public void setCurrentIdentity(String identity) {
		curIdentity = identity;
		update();
	}
	
	/**
	 * gets the current user account
	 * @return returns the current user account
	 */
	public UserAccount getcurrentUserAcc() {
		return currentUserAcc;
	}

	/**
	 * gets all the reservations
	 * @return returns an array list of reservations
	 */
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * clears out all the reservations 
	 */
	public void clearReservations() {
		reservations = new ArrayList<Reservation>();
	}
	
	public void addChangeListener(ChangeListener listener) {
		actlisteners.add(listener);
	}

	/**
	 * Lets it know about any updates that were made
	 */
	private void update() {
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : actlisteners)
			listener.stateChanged(event);
	}
	
	/**
	 * gets archived feedbacks from the hotel management db.
	 * @return returns an array list of the archived feedbacks
	 */
	public ArrayList<Feedback> getArchivedFeedbacks() {
		// TODO Auto-generated method stub
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		String sqlQuery = "SELECT * FROM archive_feedback";

		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				feedbacks.add(new Feedback(rs.getInt("feedbackID"), rs.getString("guest"),rs.getString("feedback"),
						rs.getDate("time")));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return feedbacks;
	}

	/**
	 * gets archived cleaning tasks from the hotel management db.
	 * @return returns an array list of the archived cleaning tasks
	 */
	public ArrayList<CleaningService> getArchivedCleaningOrders() {
		// TODO Auto-generated method stub
		ArrayList<CleaningService> service = new ArrayList<CleaningService>();
		String sqlQuery = "SELECT * FROM archive_cleaningservice";

		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				service.add(new CleaningService(rs.getInt("cleaningID"), rs.getString("task"), rs.getInt("roomId"), rs.getInt("resId"),
						rs.getDate("time"), rs.getDouble("price")));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return service;
	}
	
	/**
	 * Adds a cleaning task made by a user to the archives db of the hotel management database
	 * @param variables are the ones needed to archive a cleaning task 
	 * @return returns true is the task is archived otherwise false
	 */
	public boolean addToArchiveCleaning(Reservation r, String task, double price){	
		String sqlQuery = String.format("INSERT INTO archive_CleaningSERVICE(task,roomId,resId,price)"
				+ " VALUES('%s','%d','%d','%f')", 
				task, r.getRoom().getRoomId(), r.getReservationId(), price);

		try {
			statement.execute(sqlQuery);
			update();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets current user from the hotel management database
	 */
	public UserAccount getCurUserAccount(String username) {
		UserAccount acc = null;
		String queryAccount = "SELECT firstname, lastname, username, userrole FROM USER WHERE username = '" + username + "'"; 
		String operation = "select guest, canceled, resId, room.roomId, startDate, endDate, numOfDays, totalPrice, pricePerNight, types from room right outer join reservations on room.roomid = reservations.roomid where guest ='" + username + "'";
		try {
			ResultSet rs = statement.executeQuery(queryAccount);
			while (rs.next()) {
				acc = new UserAccount(rs.getString("firstname"), rs.getString("lastname"), 
						rs.getString("username"), rs.getString("userrole"));
			}
			rs.close();
			rs = statement.executeQuery(operation);
			while (rs.next()) {
				Room r = new Room(rs.getInt("roomid"), rs.getDouble("pricePerNight"), rs.getString("types"));
				acc.getReservations().add(new Reservation(rs.getInt("resId"), rs.getString("guest"), r, 
						rs.getDate("startdate"), rs.getDate("enddate"), rs.getInt("numOfDays"), rs.getDouble("totalPrice"), 
						rs.getBoolean("canceled")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return acc;
	}

	/**
	 * gets the users from the hotel management database
	 * @param numOfReservations is the number of reservations
	 * @return returns an array list of user accounts
	 */
	public ArrayList<UserAccount> getSpecificResUsers(Integer numOfReservations) {
		ArrayList<UserAccount> list = new ArrayList<UserAccount>();
		String queryUser = "";
		ArrayList<String> usernames = new ArrayList<String>();
	
		if (numOfReservations == null)
			queryUser = "select username from user";
		else
			queryUser = "select username from user right outer join (select guest, resId, room.roomId, startDate, endDate, numOfDays, totalPrice, pricepernight, types from room right outer join reservations on room.roomid = reservations.roomid group by guest having count(*) >= " + numOfReservations + ") as reservations on user.username = reservations.guest";
		try {
			ResultSet rs = statement.executeQuery(queryUser);
			while (rs.next()) 
				usernames.add(rs.getString("username"));
			rs.close();
			
			for (String s : usernames) 
				list.add(getCurUserAccount(s));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * checks if the user is present in the hotel management database
	 * @param username is the username of the user
	 * @return returns true if the user is present otherwise false
	 */
	public boolean checkPresence(String username) {
		String sqlQuery = "select username from user";
		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("username").equals(username)) return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * checks if the user's password is present and matches the one in the hotel management database
	 * @param username is the username of the user
	 * @param password is the password of the user
	 * @return returns true if the username and password match to the ones in the db otherwise false
	 */
	public boolean matchPassword(String username, String password) {
		String sqlQuery = "select password from user where userName = '" +username+ "'";
		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			if (rs.next() && rs.getString("password").equals(password)) {
				rs.close(); return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	/**
	 * gets the available (non booked) rooms from the hotel management database
	 * @param checkin is the check in date
	 * @param checkout is the check out date
	 * @return returns an array list of user accounts
	 */
	public ArrayList<Room> getAvailableRooms(String checkin, String checkout) {
		ArrayList<Room> rooms = new ArrayList<>();
		String checkIn = sqlToDate(checkin);
		String checkOut = sqlToDate(checkout);
		String sqlQuery = "select * from room where roomId not in (select distinct room.roomId from room left outer join reservations on room.roomId = reservations.roomId "
				+ "where " + checkIn + " = reservations.startdate or " + checkIn + " = reservations.enddate or " + checkOut + "= reservations.startdate"
				+ " or " + checkOut + " = reservations.enddate or " + "(reservations.startdate < " + checkOut + " and reservations.enddate > " + checkIn + ") or (" + checkIn + " < reservations.startdate and " + checkOut + " > reservations.startdate) or (" + checkIn + " < reservations.enddate and " + checkOut + " > reservations.enddate))";
		try {
			ResultSet rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				rooms.add(new Room(rs.getInt("roomid"), rs.getDouble("pricepernight"), rs.getString("types")));
			}
			rs.close();
			return rooms;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
