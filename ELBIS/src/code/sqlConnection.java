/**
 * This Class to do almost of SQL Queries 
 * @author Mohammed Ali Anis, Hamze Ali
 * 
 */
package code;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To connect to the sqlite DB  
 */
public class sqlConnection {

	private static int[] intArrs;

	/*
	 * To Connect into the DB
	 */
	public static Connection connect() {

		Connection connect = null;

		try {

			String path = "src/db/ProPraEdited.db";

			File file = new File(path);
			String absolutePath = file.getAbsolutePath();
			connect = DriverManager.getConnection("jdbc:sqlite:" + absolutePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connect;

	}

	/**
	 * Add new User
	 * 
	 * @param Firstname,
	 * @param Lastname,
	 * @param Username,
	 * @param Password,
	 * @param Email,
	 * @param selectedSections,
	 * @param role_type         all parameters for the new addedUser
	 */
	public static void sqlAddUser(String Firstname, String Lastname, String Username, String Password, String Email,
			ArrayList<String> selectedSections, String role_type) throws Exception {

		try {

			Connection con1 = connect();
			String query1 = " insert into User (first_name, last_name, username, password, email)" + " values ('"
					+ Firstname + "', '" + Lastname + "', '" + Username + "', '" + Password + "', '" + Email + "');";
			PreparedStatement posted1 = con1.prepareStatement(query1);
			posted1.execute();

			con1.close();
			posted1.close();

			Connection con2 = connect();
			/*
			 * INSERT INTO User_Section (user_id, section_id) SELECT user_id , section_id
			 * FROM User Natural Join Section Where email ='Email AND subsection=section_id;
			 */
			Statement posted2 = null;
			for (int i = 0; i < selectedSections.size(); i++) {
				String query2 = "INSERT INTO User_Section (user_id, section_id) SELECT user_id , section_id FROM User Natural Join Section Where email='"
						+ Email + "' AND subsection='" + selectedSections.get(i) + "';";

				posted2 = con2.createStatement();
				posted2.execute(query2);
			}
			con2.close();
			posted2.close();

			Connection con3 = connect();
			/*
			 * Insert Into User_Role ( user_id, role_id ) SELECT user_id , role_id From User
			 * Natural Join Role Where email='example' AND role_type='User';
			 */
			Statement posted3 = null;
			for (int i = 0; i < selectedSections.size(); i++) {
				String query3 = "INSERT INTO User_Role (user_id, role_id) SELECT user_id , role_id FROM User Natural Join Role Where email='"
						+ Email + "' AND role_type='" + role_type + "';";

				posted3 = con3.createStatement();
				posted3.execute(query3);
			}
			con3.close();
			posted3.close();

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
			// JOptionPane.ERROR_MESSAGE);
			// e.printStackTrace();
		}

	}

	/**
	 * To know if the logedin person is User, Editor or Admin role_id=1 -> Admin
	 * role_id=2 -> Editor role_id=3 -> User
	 * 
	 * @param username,
	 * @param password
	 */
	public static int sqlLogin(String username, String password) throws SQLException {
		boolean ergebniss = false;
		ResultSet r = null;
		// SELECT user_id from User natural join User_Role
		// Where username='username' AND password='password'
		String Query = "Select role_id From User Natural Join User_Role where username='" + username
				+ "' AND password='" + password + "'";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		int Role = -1;
		while (r.next()) {
			Role = Integer.parseInt(r.getString("role_id"));
		}
		s.close();
		return Role;
	}

	/**
	 * to show the UsersInfos into Table in the Admin Page
	 * 
	 * @param tableModel
	 * @throws sqlException
	 */
	public static void sqlUserTable(DefaultTableModel tableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * SELECT user_id from User natural join User_Role Where username='username' AND
		 * password='password'
		 */
		String Query = "Select user_id, first_name, last_name, username,role_id From User Natural Join User_Role Where role_id != '1';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String user_id = String.valueOf(r.getString("user_id"));
			String first_name = r.getString("first_name");
			String last_name = r.getString("last_name");
			String username = r.getString("username");
			String role_id = r.getString("role_id");

			if (role_id.equals("1")) {
				role_id = "Admin";
			} else if (role_id.equals("2")) {
				role_id = "Editor";
			} else if (role_id.equals("3")) {
				role_id = "User";
			}
			String[] tbData = { user_id, first_name, last_name, username, role_id };

			tableModel.addRow(tbData);
		}

	}

	/**
	 * to show the SectionInfos into Table in the Admin Page
	 * 
	 * @param sectionTableModel
	 * @throws sqlException
	 * 
	 */
	public static void sqlSectionTable(DefaultTableModel sectionTableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * Select * From Section
		 */
		String Query = "Select * From Section";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String section_id = String.valueOf(r.getString("section_id"));
			String section_type = r.getString("section_type");
			String subsection = r.getString("subsection");

			String[] tbData2 = { section_id, section_type, subsection };

			sectionTableModel.addRow(tbData2);
		}

	}

	/**
	 * to show the SectionInfos into Table in the Admin Page
	 * 
	 * @param username
	 * @param blogTableModel
	 * @throws sqlException
	 * 
	 */
	public static void sqlBlogTable(String username, DefaultTableModel blogTableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * Select blog_id, blog_title,fromDate, toDate,section_id, subsection From Blog
		 * Natural Join User Natural Join Section Where username='username';
		 */
		String Query = "Select blog_id, blog_title,fromDate, toDate,section_id, subsection, website, terminal, approved From Blog Natural Join User Natural Join Section Where username = '"
				+ username + "';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String blog_id = String.valueOf(r.getString("blog_id"));
			String blog_title = r.getString("blog_title");
			String fromDate = r.getString("fromDate");
			String toDate = r.getString("toDate");
			String section_id = r.getString("section_id");
			String subsection = r.getString("subsection");
			String website = r.getString("website");
			String terminal = r.getString("terminal");
			String approved = r.getString("approved");

			String[] tbData = { blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal,
					approved };

			blogTableModel.addRow(tbData);
		}

	}

	/**
	 * to Show All Section during the register process in Register Class
	 * 
	 * 
	 */
	public static ArrayList<String> getSection() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();

		ResultSet r = null;
		// SELECT subsection From Section
		String Query = "SELECT subsection From Section";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			list.add(r.getString("subsection"));
		}
		s.close();
		return list;
	}

	/**
	 * @use adding blog attributes to blog adding image_path to media adding
	 *      video_path to media
	 *
	 * @param username
	 * @param title
	 * @param content
	 * @param website
	 * @param terminal
	 * @param subsection
	 * @param from
	 * @param to
	 * @param imageArrSplit
	 * @param videoArrSplit
	 */
	/*
	 * To Add Blog from User
	 */
	public static void sqlAddBlog(String username, String title, String content, String website, String terminal,
			String subsection, String from, String to, String[] imageArrSplit, String[] videoArrSplit) {

		Connection con1 = connect();
		/*
		 * insert into Blog (blog_title, blog_description, fromDate, toDate, user_id,
		 * section_id, website, terminal, approved, checkedby_user_id, editedby_user_id)
		 * values ( 'title', 'content', '2020', '2021', (Select user_id from User where
		 * username = 'ezgi.tüfek'), (Select section_id From Section Where subsection
		 * ='Java'),website, terminal, '?', '?', '-');
		 */

		try {
			String query1 = " insert into Blog (blog_title, blog_description, fromDate, toDate, user_id, section_id, website, terminal, approved, checkedby_user_id, editedby_user_id)"
					+ " values ('" + title + "', '" + content + "', '" + from + "', '" + to
					+ "', ( Select user_id from User Where username = '" + username
					+ "'), ( Select section_id From Section Where subsection = '" + subsection + "'), '" + website
					+ "', '" + terminal + "', '?', '?', '-');";
			PreparedStatement posted1 = con1.prepareStatement(query1);
			posted1.execute();

			System.out.println("image length " + imageArrSplit.length);
			System.out.println("video length " + imageArrSplit.length);

			if (imageArrSplit.length > 1) {

				/*
				 * INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Image', ( Select
				 * blog_id From Blog Where blog_title = title), 'imageArrSplit[]');
				 * 
				 */
				for (int i = 0; i < imageArrSplit.length; i++) {
					System.out.println("image " + imageArrSplit[i]);
					String query2 = "INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Image', ( Select blog_id From Blog Where blog_title = '"
							+ title + "'), '" + imageArrSplit[i] + "');";

					posted1 = con1.prepareStatement(query2);
					posted1.execute();
				}

			}

			if (videoArrSplit.length > 1) {

				/*
				 * INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Video', ( Select
				 * blog_id From Blog Where blog_title = title), 'imageArrSplit[]');
				 * 
				 */

				for (int i = 0; i < videoArrSplit.length; i++) {
					System.out.println("video " + videoArrSplit[i]);
					String query3 = "INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Video', ( Select blog_id From Blog Where blog_title = '"
							+ title + "'), '" + videoArrSplit[i] + "');";

					posted1 = con1.prepareStatement(query3);
					posted1.execute();
				}

			}
			con1.close();
			posted1.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Add section
	 * 
	 * @param section_type name of the new section
	 * @param subsection   name of the subsection if a subsection is wanted
	 */
	/*
	 * To Add Section from Admin Class
	 */
	public static void sqlAddSection(String section_type, String subsection) throws Exception {

		try {

			Connection con1 = connect();
			String query1 = " insert into Section (section_type, subsection)" + " values ('" + section_type + "', '"
					+ subsection + "');";
			PreparedStatement posted1 = con1.prepareStatement(query1);
			posted1.execute();

			con1.close();
			posted1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @use getting attributes from database at tables Blog and Media and adding to
	 *      arraylist
	 * @param blog_id
	 * @return ArrayList with the attributes
	 * @throws SQLException
	 */
	/*
	 * To show the data already written by clicking on Edit Blog from User
	 */
	public static ArrayList<String> sqlGetBlogData(String blog_id) throws SQLException {
		ArrayList<String> dataList = new ArrayList<String>();
		ResultSet r1 = null;
		/*
		 * Select blog_title, blog_description, fromDate, toDate, subsection, website,
		 * terminal From Blog Natural Join Section Where blog_id= 34 AND section_id =
		 * section_id;
		 */
		String Query1 = "Select blog_title, blog_description, fromDate, toDate, subsection, website, terminal From Blog Natural Join Section where blog_id = '"
				+ blog_id + "' AND section_id = section_id;";
		Statement s1 = sqlConnection.connect().createStatement();
		r1 = s1.executeQuery(Query1);
		while (r1.next()) {
			String blog_title = r1.getString("blog_title");
			String blog_description = r1.getString("blog_description");
			String fromDate = r1.getString("fromDate");
			String toDate = r1.getString("toDate");
			String subsection = r1.getString("subsection");
			String website = r1.getString("website");
			String terminal = r1.getString("terminal");
			dataList.add(blog_title);
			dataList.add(blog_description);
			dataList.add(fromDate);
			dataList.add(toDate);
			dataList.add(subsection);
			dataList.add(website);
			dataList.add(terminal);
		}
		ArrayList<String> imageListPath = new ArrayList<String>();
		ResultSet r2 = null;
		/*
		 * Select blog_title, blog_description, fromDate, toDate, subsection, website,
		 * terminal From Blog Natural Join Section Where blog_id= 34 AND section_id =
		 * section_id;
		 */
		String Query2 = "Select file_path From Blog Natural Join Media where blog_id = '" + blog_id
				+ "' AND media_type = 'Image';";
		Statement s2 = sqlConnection.connect().createStatement();
		r2 = s2.executeQuery(Query2);
		while (r2.next()) {
			String file_path = r2.getString("file_path");
			imageListPath.add(file_path);
		}
		String imagePath = "";
		for (int i = 0; i < imageListPath.size(); i++) {
			imagePath = imagePath + ", " + imageListPath.get(i);
		}
		dataList.add(imagePath);

		ArrayList<String> videoListPath = new ArrayList<String>();
		ResultSet r3 = null;
		/*
		 * Select blog_title, blog_description, fromDate, toDate, subsection, website,
		 * terminal From Blog Natural Join Section Where blog_id= 34 AND section_id =
		 * section_id;
		 */
		String Query3 = "Select file_path From Blog Natural Join Media where blog_id = '" + blog_id
				+ "' AND media_type = 'Video';";
		Statement s3 = sqlConnection.connect().createStatement();
		r3 = s3.executeQuery(Query3);
		while (r3.next()) {
			String file_path = r3.getString("file_path");
			videoListPath.add(file_path);
		}
		String videoPath = "";
		for (int i = 0; i < videoListPath.size(); i++) {
			videoPath = videoPath + ", " + videoListPath.get(i);
		}
		dataList.add(videoPath);

		s1.close();
		s2.close();
		s3.close();
		return dataList;
	}

	/**
	 * 
	 * @param blog_id
	 * @param username
	 * @param title
	 * @param content
	 * @param website
	 * @param terminal
	 * @param subsection
	 * @param from
	 * @param to
	 * @param imageArrSplit
	 * @param videoArrSplit
	 * @throws SQLException
	 */
	/*
	 * To Update a Blog from the User
	 */
	public static void sqlUpdateBlog(String blog_id, String username, String title, String content, String website,
			String terminal, String subsection, String from, String to, String[] imageArrSplit, String[] videoArrSplit)
			throws SQLException {

		Connection con1 = connect();
		/*
		 * Update Blog SET blog_title = 'title', blog_description = 'content', fromDate
		 * = 'from', toDate = 'to', editedby_user_id = ( Select user_id from User Where
		 * username = 'username'), section_id = ( Select section_id from Section Where
		 * subsection = 'subsection' ), website = 'website', terminal = 'terminal',
		 * approved = '?' Where blog_id = 'blog_id';
		 */

		try {
			String query1 = "Update Blog SET blog_title = '" + title + "', blog_description = '" + content
					+ "', fromDate = '" + from + "', toDate = '" + to
					+ "', editedby_user_id = ( Select user_id from User Where username = '" + username
					+ "'), section_id = ( Select section_id from Section Where subsection = '" + subsection
					+ "' ), website = '" + website + "', terminal = '" + terminal
					+ "', approved = '?' Where blog_id = '" + blog_id + "';";
			PreparedStatement posted1 = con1.prepareStatement(query1);
			posted1.execute();

			con1.close();
			posted1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Connection con2 = connect();
		/*
		 * DELETE FROM Media WHERE blog_id = 'blog_id'
		 */

		try {
			String query2 = "DELETE FROM Media Where blog_id = '" + blog_id + "';";
			PreparedStatement posted2 = con2.prepareStatement(query2);
			posted2.execute();

			con2.close();
			posted2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (imageArrSplit.length > 1) {

			Connection con3 = connect();
			/*
			 * INSERT INTO Media (media_type, blog_id, file_path) Values ('Image', blog_id,
			 * imageArrSplit[i]);
			 */
			Statement posted3 = null;
			for (int i = 0; i < imageArrSplit.length; i++) {
				String query3 = "INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Image', '" + blog_id
						+ "' , '" + imageArrSplit[i] + "');";

				posted3 = con3.createStatement();
				posted3.execute(query3);
			}

		}

		if (videoArrSplit.length > 1) {

			Connection con4 = connect();
			/*
			 * INSERT INTO Media (media_type, blog_id, file_path) Values ('Video', blog_id,
			 * videoArrSplit[i]);
			 */
			Statement posted4 = null;
			for (int i = 0; i < videoArrSplit.length; i++) {
				String query4 = "INSERT INTO Media (media_type, blog_id, file_path) Values ( 'Video', '" + blog_id
						+ "' , '" + videoArrSplit[i] + "');";

				posted4 = con4.createStatement();
				posted4.execute(query4);
			}
			con4.close();
			posted4.close();

		}

	}

	/**
	 * To show the data already written by clicking on Edit Selected User from Admin
	 * 
	 * @param user_id     id of the selected User
	 * @param sectionList
	 * @return all User data into a ArrayList to be writen in a Table
	 */
	public static ArrayList<String> sqlGetUserData(String user_id, JList<?> sectionList) throws SQLException {
		ArrayList<String> dataList = new ArrayList<String>();
		ResultSet r1 = null;
		/*
		 * Select first_name, last_name, username, email, role_type, password From User
		 * Natural Join Role Natural Join User_Role where user_id = user_id AND
		 * role_type = ( Select role_type From Role Natural Join user_role Where user_id
		 * = user_id );
		 */
		String Query1 = "Select first_name, last_name, username, email, role_type, password From User Natural Join Role Natural Join User_Role where user_id = '"
				+ user_id + "' AND role_type = ( Select role_type From Role Natural Join user_role Where user_id = '"
				+ user_id + "');";
		Statement s1 = sqlConnection.connect().createStatement();
		r1 = s1.executeQuery(Query1);
		while (r1.next()) {
			String first_name = r1.getString("first_name");
			String last_name = r1.getString("last_name");
			String username = r1.getString("username");
			String email = r1.getString("email");
			String role_type = r1.getString("role_type");
			String password = r1.getString("password");
			dataList.add(first_name);
			dataList.add(last_name);
			dataList.add(username);
			dataList.add(email);
			dataList.add(role_type);
			dataList.add(password);
		}
		r1.close();
		s1.close();

		ResultSet r2 = null;
		/*
		 * Select subsection From User Natural Join User_Section Natural Join Section
		 * where user_id = 511;
		 */
		String Query2 = "Select subsection From User Natural Join User_Section Natural Join Section where user_id = '"
				+ user_id + "';";
		Statement s2 = sqlConnection.connect().createStatement();
		r2 = s2.executeQuery(Query2);
		ArrayList<String> list = new ArrayList<String>();
		while (r2.next()) {
			String subsection = r2.getString("subsection");
			list.add(subsection);
		}
		Object[] objArr = list.toArray();
		sectionList.getSelectionModel().setSelectionInterval(0, objArr.length - 1);
		r2.close();
		s2.close();

		ResultSet r3 = null;
		String Query3 = "select id from (SELECT row_number() over () as id, subsection, section_id from section)"
				+ "where subsection in (Select subsection From User_Section Natural Join Section where user_id = "
				+ user_id + " )";
		Statement s3 = sqlConnection.connect().createStatement();
		r3 = s3.executeQuery(Query3);

		ArrayList<String> list2 = new ArrayList<String>();
		while (r3.next()) {
			String id = r3.getString("id");
			list2.add(id);
//			char c[] = new char[list2.size()];
//			for (int i = 0; i < c.length; i++) {
//				c[i] = list2.get(i).charAt(0);
//			}

			int b[] = new int[list2.size()];
			for (int i = 0; i < list2.size(); i++) {
				b[i] = Integer.parseInt(list2.get(i)) - 1;
			}

//			int a[] = new int[c.length];
//			for (int i = 0; i < c.length; i++) {
//				a[i] = Character.getNumericValue(c[i] - 1);
//			}
			sectionList.setSelectedIndices(b);
		}

		r3.close();
		s3.close();
		return dataList;
	}

	/**
	 * To Update User edited by the admin
	 * 
	 * @param user_id,
	 * @param first_name,
	 * @param last_name,
	 * @param username,
	 * @param password,
	 * @param email,
	 * @param selectedSections,
	 * @param role_type         these are all the Updated informations for the User
	 *                          which are stored to the DB
	 */
	public static void sqlUpdateUser(String user_id, String first_name, String last_name, String username,
			String password, String email, ArrayList<String> selectedSections, String role_type) throws SQLException {

		Connection con1 = connect();
		/*
		 * Update User SET first_name = first_name, last_name = last_name, username =
		 * username, email = email, password = password Where user_id = user_id;
		 */
		try {
			String query1 = "UPDATE User SET first_name = '" + first_name + "', last_name = '" + last_name
					+ "', username = '" + username + "', email = '" + email + "', password ='" + password
					+ "' Where user_id = '" + user_id + "';";
			PreparedStatement posted1 = con1.prepareStatement(query1);
			posted1.execute();

			con1.close();
			posted1.close();

			Connection con2 = connect();
			/*
			 * Insert Into User_Role ( user_id, role_id ) SELECT user_id , role_id From User
			 * Natural Join Role Where email='example' AND role_type='User';
			 */
			Statement posted2 = null;
			for (int i = 0; i < selectedSections.size(); i++) {
				String query2 = "UPDATE User_Role SET role_id = ( Select role_id From Role Where role_type = '"
						+ role_type + "') Where user_id = '" + user_id + "';";

				posted2 = con2.createStatement();
				posted2.execute(query2);
			}
			con2.close();
			posted2.close();

			Connection con3 = connect();
			/*
			 * DELETE FROM User_Section WHERE user_id = user_id;
			 */
			Statement posted3;
			String query3 = "DELETE FROM User_Section Where user_id = '" + user_id + "';";
			posted3 = con3.createStatement();
			posted3.execute(query3);

			con3.close();
			posted3.close();

			Connection con4 = connect();
			/*
			 * INSERT INTO User_Section (user_id, section_id) SELECT user_id , section_id
			 * FROM User Natural Join Section Where user_id = user_id AND
			 * subsection=section_id;
			 */
			Statement posted4 = null;
			for (int i = 0; i < selectedSections.size(); i++) {
				String query4 = "INSERT INTO User_Section (user_id, section_id) SELECT user_id , section_id FROM User Natural Join Section Where user_id='"
						+ user_id + "' AND subsection='" + selectedSections.get(i) + "';";

				posted4 = con4.createStatement();
				posted4.execute(query4);
			}
			con4.close();
			posted4.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * TO Delete Subsection
	 * 
	 * @param section_id of the subsection which needs to be deleted
	 */
	public static void sqlDeleteSubsection(String section_id) throws SQLException {
		Connection con1 = connect();
		/*
		 * (Section Table) DELETE FROM Section WHERE section_id = section_id;
		 */
		Statement posted1;
		String query1 = "DELETE FROM Section WHERE section_id = '" + section_id + "';";
		posted1 = con1.createStatement();
		posted1.execute(query1);

		/*
		 * (User_Section Table) DELETE FROM User_Section WHERE section_id = section_id;
		 */
		String query2 = "DELETE FROM User_Section WHERE section_id = '" + section_id + "';";
		posted1 = con1.createStatement();
		posted1.execute(query2);

		/*
		 * (Blog Table) DELETE FROM Blog WHERE section_id = section_id;
		 */
		String query3 = "DELETE FROM Blog WHERE section_id = '" + section_id + "';";
		posted1 = con1.createStatement();
		posted1.execute(query3);

		con1.close();
		posted1.close();

		System.out.println("Subsection has been deleted");

	}

	/**
	 * TO Delete Section
	 * 
	 * @param section_type
	 */
	public static void sqlDeleteSection(String section_type) throws SQLException {

		Connection con1 = connect();
		/*
		 * DELETE FROM User_Section WHERE section_type = section_type;
		 */
		Statement posted1;
		posted1 = con1.createStatement();

		ResultSet r1 = null;
		/*
		 * Select section_id from Section Where section_type = section_type;
		 */
		String Query1 = "Select section_id from Section Where section_type = '" + section_type + "';";
		r1 = posted1.executeQuery(Query1);
		while (r1.next()) {
			String section_id = r1.getString("section_id");
			/*
			 * DELETE FROM User_Section WHERE section_id = section_id;
			 */
			String query2 = "DELETE FROM User_Section WHERE section_id = '" + section_id + "';";
			posted1 = con1.createStatement();
			posted1.execute(query2);

			/*
			 * DELETE FROM Blog WHERE section_id = section_id;
			 */
			String query3 = "DELETE FROM Blog WHERE section_id = '" + section_id + "';";
			posted1 = con1.createStatement();
			posted1.execute(query3);

		}

		/*
		 * DELETE FROM Section WHERE section_type = section_type;
		 */
		String query4 = "DELETE FROM Section WHERE section_type = '" + section_type + "';";
		posted1.execute(query4);

		con1.close();
		posted1.close();

		System.out.println("Section with the belongs Subsections has been deleted");

	}

	/**
	 * TO Rename Subsection
	 * 
	 * @param section_id,
	 * @param newName
	 */
	public static void sqlRenameSubsection(String section_id, String newName) throws SQLException {
		Connection con1 = connect();
		/*
		 * UPDATE Section SET subsection = newName Where section_id = section_id;
		 * 
		 */
		String query1 = "UPDATE Section SET subsection = '" + newName + "' Where section_id = '" + section_id + "';";
		PreparedStatement posted1 = con1.prepareStatement(query1);
		posted1.execute();

		con1.close();
		posted1.close();

		System.out.println("Subsection has new name");

	}

	/**
	 * TO Rename Section
	 * 
	 * @param section_type,
	 * @param newName
	 */
	public static void sqlRenameSection(String section_type, String newName) throws SQLException {
		Connection con1 = connect();
		/*
		 * UPDATE Section SET section_type = newName Where section_type = section_type;
		 * 
		 */
		String query1 = "UPDATE Section SET section_type = '" + newName + "' Where section_type = '" + section_type
				+ "';";
		PreparedStatement posted1 = con1.prepareStatement(query1);
		posted1.execute();

		con1.close();
		posted1.close();

		System.out.println("Section has new name");

	}

	/**
	 * To Delete User
	 * 
	 * @param AUser_id
	 */
	public static void sqlDeleteUser(String AUser_id) throws Exception {
		int user_id = Integer.parseInt(AUser_id);

		try {
			// Delete User From User Table
			Connection c = connect();
			String query1 = "Delete from User where user_id = '" + user_id + "';";
			PreparedStatement posted1 = c.prepareStatement(query1);
			posted1.execute();
			posted1.close();
			// Delete User From User_Role Table
			String query2 = "Delete from User_Role where user_id = '" + user_id + "';";
			PreparedStatement posted2 = c.prepareStatement(query2);
			posted2.execute();
			posted2.close();
			// Delete User From User_Section Table
			String query3 = "Delete from User_Section where user_id = '" + user_id + "';";
			PreparedStatement posted3 = c.prepareStatement(query3);
			posted3.execute();
			posted1.close();

			c.close();
			System.out.println("User has been Deleted with the user_section and user_role");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To Delete Blog
	 * 
	 * @param blog_id
	 */
	public static void sqlDeleteBlog(String blog_id) throws Exception {

		try {
			// Delete Blog from Blog Table
			Connection c = connect();
			String query1 = "Delete from Blog where blog_id = '" + blog_id + "';";
			PreparedStatement posted1 = c.prepareStatement(query1);
			posted1.execute();
			posted1.close();
			// Delete Blog's Media From Media Table
			String query2 = "Delete from Media where blog_id = '" + blog_id + "';";
			PreparedStatement posted2 = c.prepareStatement(query2);
			posted2.execute();
			posted2.close();

			c.close();
			System.out.println("Blog has been Deleted with the belongs Media");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To show the pendings Blogs in the Edtor Frame, Pendings Blog are in the waiting list
	 * 
	 * @param username,
	 * @param blogTableModel
	 */
	public static void sqlBlogTablePending(String username, DefaultTableModel blogTableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * Select blog_id, blog_title,fromDate, toDate,section_id, subsection, website,
		 * terminal, user_id, approved From Blog Natural Join User Natural Join Section
		 * Natural Join User_Section Where Blog.section_id in ( Select section_id From
		 * Section Natural Join User Natural Join User_Section Where username =
		 * 'username' ) AND approved='?';
		 */
		String Query = "Select blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal, user_id From Blog Natural Join User Natural Join Section Natural Join User_Section Where Blog.section_id in ( Select section_id From Section Natural Join User Natural Join User_Section Where username = '"
				+ username + "' ) AND approved = '?';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String blog_id = String.valueOf(r.getString("blog_id"));
			String blog_title = r.getString("blog_title");
			String fromDate = r.getString("fromDate");
			String toDate = r.getString("toDate");
			String section_id = r.getString("section_id");
			String subsection = r.getString("subsection");
			String website = r.getString("website");
			String terminal = r.getString("terminal");
			String user_id = r.getString("user_id");

			String[] tbData = { blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal,
					user_id };

			blogTableModel.addRow(tbData);
		}

	}

	/**
	 * To show the Accepted Blogs in the Edtor Frame
	 * 
	 * @param username,
	 * @param blogTableModel
	 */
	public static void sqlBlogTableAccepted(String username, DefaultTableModel blogTableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * Select blog_id, blog_title,fromDate, toDate,section_id, subsection, website,
		 * terminal, user_id, approved From Blog Natural Join User Natural Join Section
		 * Natural Join User_Section Where Blog.section_id in ( Select section_id From
		 * Section Natural Join User Natural Join User_Section Where username =
		 * 'username' ) AND approved='Yes';
		 */
		String Query = "Select blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal, user_id From Blog Natural Join User Natural Join Section Natural Join User_Section Where Blog.section_id in ( Select section_id From Section Natural Join User Natural Join User_Section Where username = '"
				+ username + "' ) AND approved = 'Yes';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String blog_id = String.valueOf(r.getString("blog_id"));
			String blog_title = r.getString("blog_title");
			String fromDate = r.getString("fromDate");
			String toDate = r.getString("toDate");
			String section_id = r.getString("section_id");
			String subsection = r.getString("subsection");
			String website = r.getString("website");
			String terminal = r.getString("terminal");
			String user_id = r.getString("user_id");

			String[] tbData = { blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal,
					user_id };

			blogTableModel.addRow(tbData);
		}
	}

	/**
	 * To show the Rejected Blogs in the Edtor Frame
	 * 
	 * @param username,
	 * @param blogTableModel
	 */
	public static void sqlBlogTableRejected(String username, DefaultTableModel blogTableModel) throws SQLException {

		ResultSet r = null;

		/*
		 * Select blog_id, blog_title,fromDate, toDate,section_id, subsection, website,
		 * terminal, user_id, approved From Blog Natural Join User Natural Join Section
		 * Natural Join User_Section Where Blog.section_id in ( Select section_id From
		 * Section Natural Join User Natural Join User_Section Where username =
		 * 'username' ) AND approved='No';
		 */
		String Query = "Select blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal, user_id From Blog Natural Join User Natural Join Section Natural Join User_Section Where Blog.section_id in ( Select section_id From Section Natural Join User Natural Join User_Section Where username = '"
				+ username + "' ) AND approved = 'No';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {
			String blog_id = String.valueOf(r.getString("blog_id"));
			String blog_title = r.getString("blog_title");
			String fromDate = r.getString("fromDate");
			String toDate = r.getString("toDate");
			String section_id = r.getString("section_id");
			String subsection = r.getString("subsection");
			String website = r.getString("website");
			String terminal = r.getString("terminal");
			String user_id = r.getString("user_id");

			String[] tbData = { blog_id, blog_title, fromDate, toDate, section_id, subsection, website, terminal,
					user_id };

			blogTableModel.addRow(tbData);
		}

	}

	/**
	 * To Accept the  Blogs , make in DB Blog.approved='Yes'
	 * 
	 * @param blog_id,
	 * @param username
	 */
	public static void sqlAcceptBlog(String blog_id, String username) throws SQLException {

		Connection con1 = connect();
		/*
		 * UPDATE Blog SET approved = 'Yes' Where blog_id = blog_id ;
		 */
		String query1 = "UPDATE Blog SET approved = 'Yes', checkedby_user_id = ( Select user_id From User Where username = '"
				+ username + "') Where blog_id = '" + blog_id + "';";

		PreparedStatement posted1 = con1.prepareStatement(query1);
		posted1.execute();

		con1.close();
		posted1.close();

		System.out.println("Blog has been Accepted");
	}

	/**
	 * To Reject the  Blogs , make in DB Blog.approved='No'
	 * 
	 * @param blog_id,
	 * @param username
	 */
	public static void sqlRejectBlog(String blog_id, String username) throws SQLException {

		Connection con1 = connect();
		/*
		 * UPDATE Blog SET approved = 'No' Where blog_id = blog_id ;
		 */
		String query1 = "UPDATE Blog SET approved = 'No', checkedby_user_id = ( Select user_id From User Where username = '"
				+ username + "') Where blog_id = '" + blog_id + "';";

		PreparedStatement posted1 = con1.prepareStatement(query1);
		posted1.execute();

		con1.close();
		posted1.close();

		System.out.println("Blog has been Rejected");
	}

}