import java.util.Date;

/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Customer requests 
 */
public class CleaningService {
	private final int taskId;
	private final String task;
	private final int roomID;
	private final Date time;
	private final int resId;
	private final double price;

	public CleaningService(int taskId, String task, int roomID, int resId, Date time, double price) {
		this.taskId = taskId;
		this.task = task;
		this.roomID = roomID;
		this.time = time;
		this.resId = resId;
		this.price = price;
	}
	public int getTaskId() {
		return taskId;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public int getResId() {
		return resId;
	}
	
	public String getTask() {
		return task;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String toString() {
		return "Task ID: " + taskId + " \nRoom ID: " + roomID + " \nOrdered on: " + time.toString() + " \nTask: " + task; 
	}
}
