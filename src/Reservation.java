import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Customer reservations 
 */
public class Reservation {
	private int reservationId;
	private String username;
	private Room room;
	private Date beginDay;
	private Date endDay;
	private int numOfDays;
	private double totalPrice;	
	private boolean notBooked;

	public Reservation(int reservationId, String username, Room room, Date beginDay, Date endDay, int numOfDays, double totalPrice, boolean notBooked) {
		this.reservationId = reservationId;
		this.username = username;
		this.room = room;
		this.beginDay = beginDay;
		this.endDay = endDay;
		this.numOfDays = numOfDays;
		this.totalPrice = numOfDays * room.getPricePerNight();
		this.notBooked = notBooked;
	}

	public int getReservationId() {
		return reservationId;
	}

	public Room getRoom() {
		return room;
	}

	public Date getBeginDate() {
		return beginDay;
	}

	public Date getEndDate() {
		return endDay;
	}

	public int getNumOfDays() {
		return numOfDays;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public String getGuest() {
		return username;
	}
	
	public boolean getNotBooked() {
		return notBooked;
	}
	
	/**
	 * String representation of reservation information
	 * @return the reservation information
	 */
	public String toString() {
		if(numOfDays<=1)
		{
			numOfDays = 1;
		}
		
		return String.format("%s \n%s to %s \nPrice: %d days X $%.2f nights = "
				+ "$%.2f", room.toString(), 
				new SimpleDateFormat("MM/dd/yyyy").format(beginDay),
				new SimpleDateFormat("MM/dd/yyyy").format(endDay),
				numOfDays, room.getPricePerNight(), numOfDays * room.getPricePerNight());
	}
	
}
