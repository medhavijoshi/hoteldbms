
import java.util.ArrayList;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * User accounts
 */
public class UserAccount {
	final private String username; // cannot be changed once account is created
	private String firstName;
	private String lastName;
	private String identity;
	private ArrayList<Reservation> reservations;

	public UserAccount(String firstName, String lastName, String username, String identity) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.identity = identity;
		reservations = new ArrayList<Reservation>();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getIdentity() {
		return identity;
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
}
