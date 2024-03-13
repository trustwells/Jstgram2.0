

public class Views {
	public static final String ANSI_RESET = "\u001B[0m";

	public static final String ANSI_Green = "\u001B[32m";

	public static final String ANSI_Yellow = "\u001B[33m";

	public static final String ANSI_Blue = "\u001B[34m";

	public static final String ANSI_Cyan = "\u001B[36m";

	public static void mainWindow() {
		System.out.println(ANSI_Cyan + " ========================================");
		System.out.println("|         Welcome to Jstgram 2.0!        |");
		System.out.println("|                                        |");
		System.out.println("|              *************             |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|              *     *                   |");
		System.out.println("|              *******                   |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.print("| Current number of users in database: ");
		Database.UserCount();
		System.out.println(" |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	public static void accountWindow() {
		System.out.println(ANSI_Cyan + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|    Posts (P)                           |");
		System.out.println("|    Post Visibility (V)                 |");
		System.out.println("|    Quit (Q)                            |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.print("    Current User: ");
		System.out.println(Database.currentUser);
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	public static void accountPosts() {
        System.out.println(ANSI_Yellow + " ========================================");
        System.out.println("|    My Posts and visible posts          |");
        System.out.println("|                                        |" + ANSI_Cyan);
        Database.ViewPosts();
        System.out.println(ANSI_Yellow + "|                                        |");
        System.out.println("|       Back (B) or New Post (+)         |");
        System.out.println("|                                        |");
        System.out.print("          Current User: ");
		System.out.println(Database.currentUser);
		System.out.println("|                                        |");
        System.out.println(" ========================================" + ANSI_RESET);
	}
	
	
	public static void VisibilityWindow() {
		System.out.println(ANSI_Cyan + " ========================================");
		System.out.println("| My posts are visible to the following: |");
		System.out.print(ANSI_Green + "");
		Database.VisibilityWindow();
		System.out.println(ANSI_Cyan + "|                                        |");
		System.out.println("| Add a user (+)                         |");
		System.out.println("| Remove a user (-)                      |");
		System.out.println("| Back (B)                               |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
}