/**
 * CS157A Hotel Management System
 * @author Medhavi Joshi
 * 
 * Hotel rooms
 */

public class Room {
	private int roomId;
	private double pricePerNight;
	private String types;
	
	public Room(int roomId, double pricePerNight, String types) {
		this.roomId = roomId;
		this.pricePerNight = pricePerNight;
		this.types = types;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public double getPricePerNight() {
		return pricePerNight;
	}
	
	public String getRoomType() {
		return types;
	}
	
	public String toString() {
		return types + "\n" + " $" + pricePerNight + " per night";
	}
}
