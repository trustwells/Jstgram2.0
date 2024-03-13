import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Database {
	// JDBC driver parameters

	public final static String hostname = "XXXXXXXXXX";
	public final static String username = "XXXXXXXXXX"; // your database username
	public final static String password = "XXXXXXXXXX"; // your database password
	public final static String url = "jdbc:mysql://" + hostname + "/" + username;

	static int result = 0;
	static String currentUser;
	static LocalDateTime now;

	// Just a testing method to see if the database connection works
	public static void Test() {

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "select * from Post natural join Visibility natural join Account;";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);

			rs = prep.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("userId");
				String username = rs.getString("username");

				System.out.println(userId + "\t" + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Displays the number of users on the platform
	public static void UserCount() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "select count(userId) from Account;";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);

			rs = prep.executeQuery();

			while (rs.next()) {
				int number = rs.getInt("count(userId)");
				System.out.print(number);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Checks credentials entered in the database
	public static void Login(String u, String p) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "select userId, userName from Account where username=? and password=?";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, u);
			prep.setString(2, p);
			rs = prep.executeQuery();

			if (rs.next()) {
				System.out.println("Login Sucessful");
				currentUser = rs.getString(2);
				result = 1;
				// Views.accountWindow();
			}

			else {
				System.out.println("Username or password incorrect, please try again");
				result = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Displays the Posts according to certain User
	public static void ViewPosts() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "select * from Post natural join Visibility natural join Account where visibilityUser=(select userId from Account where username=?) order by postTime;";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, currentUser);
			rs = prep.executeQuery();

			while (rs.next()) {
				String username = rs.getString("username");
				String postText = rs.getString("postText");
				String postTime = rs.getString("postTime");
				System.out.println(postText);
				System.out.print("           ");
				System.out.print(username);
				System.out.print(",   ");
				System.out.println(postTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void VisibilityWindow() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "select username from Account where userId in(select visibilityUser from Account natural join Visibility where username=? and visibilityUser!=(select userId from Account where username=?));";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, currentUser);
			prep.setString(2, currentUser);
			rs = prep.executeQuery();

			while (rs.next()) {
				String username = rs.getString("username");
				System.out.println(username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Adds a post
	public static void AddPost(String message) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		now = LocalDateTime.now();
		String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String s = "insert into Post(postText, postTime, userId) values (?, ?, (select userId from Account where username=?));";
		PreparedStatement prep = null;

		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, message);
			prep.setString(2, time);
			prep.setString(3, currentUser);
			prep.executeUpdate();
			System.out.println("New Post Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void AddVisibleUser(String v) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "insert into Visibility(userId, visibilityUser) values ((select userId from Account where username=?), (select userId from Account where username=?));";
		PreparedStatement prep = null;


		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, currentUser);
			prep.setString(2, v);
			prep.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void DeleteUser(String deleteUser) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String s = "delete from Visibility where visibilityUser=(select userId from Account where username=?) and userId=(select userId from Account where username=?);";
		PreparedStatement prep = null;


		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, deleteUser);
			prep.setString(2, currentUser);
			prep.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (prep != null)
				prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}