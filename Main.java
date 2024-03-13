import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String username;
		String password;
		Scanner s = new Scanner(System.in);
		String message;
		String visibleUser;
		String deleteUser;
		
		// Main Window
		int continuance = 0;
		while (continuance == 0) {
			if (Database.result == 0) {
				Views.mainWindow();
				
				// Entering username
				System.out.println("Enter your username: ");
				username = s.nextLine();
				System.out.println("Enter your password: ");
				password = s.nextLine();

				// Goes and checks for credentials in Database
				try {
					Database.Login(username, password);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			
			//Successful Login
			else if (Database.result == 1) {
				Views.accountWindow();
				String choice;
				choice = s.nextLine();

				// Brings user to Post screen
				if (choice.equals("P") || choice.equals("p")) {
					
					Views.accountPosts();
					choice = s.nextLine();
					//Return to Post Screen
					if (choice.equals("B") || choice.equals("b")) {
						continue;
					}
					//Create new post
					else if (choice.equals("+")) {
						System.out.println("Enter a message");
						message = s.nextLine();
						Database.AddPost(message);
						continue;
					}
					
				}
					// Brings user to visibility window
				if (choice.equals("V") || choice.equals("v")) {
					Views.VisibilityWindow();
					choice = s.nextLine();
					
					if (choice.equals("B") || choice.equals("b")) {
						continue;
					}
					//Add a visible user
					else if (choice.equals("+")) {
						System.out.println("Who do you want to add?");
						visibleUser = s.nextLine();
						Database.AddVisibleUser(visibleUser);
						continue;
					}
					//Remove a visible user
					else if (choice.equals("-")) {
						System.out.println("Who do you want to remove?");
						deleteUser = s.nextLine();
						Database.DeleteUser(deleteUser);
						continue;
					}
				}
				//Quits the program
				if (choice.equals("Q") || choice.equals("q")) {
					System.exit(0);
				}
				
			}
		}
		s.close();

	}
}
