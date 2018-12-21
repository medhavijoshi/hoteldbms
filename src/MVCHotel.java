/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Main entry point for application
 */

public class MVCHotel {
	
	public static void main(String args[]) {
		Model model = new Model();
		GUI view = new GUI(model);
	}
}