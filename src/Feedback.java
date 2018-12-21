import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Responsible for User's feedback
 */
public class Feedback {
	private final int id;
	private final String guest;
	private final String feedback;
	private final Date time;

	public Feedback(int id, String guest, String feedback, Date time) {
		this.id = id;
		this.guest = guest;
		this.feedback = feedback;
		this.time = time;
	}

	public int getId() {
		return id;
	}
	
	public String getGuest() {
		return guest;
	}
	
	public String getFeedback() {
		return feedback;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String toString() {
		String s = "Username: " + guest +"\nFeedback ID: " + id + "\n on: " + 
	new SimpleDateFormat("MM/dd/yyyy").format(time) + "\nFeedback: " + feedback;
		
		return s;
	}
}
