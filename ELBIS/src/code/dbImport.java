/**
 * This Class with methode to Import a Backup from csv to our DB
 * @author Mohammed Ali Anis, Hamze Ali
 */
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class dbImport {

	public static void dbImport(String version) throws SQLException {

		String path = "src/csv";

		File file = new File(path);
		String absolutePath = file.getAbsolutePath();

		File theDir = new File(absolutePath + "/" + version);

		// File path and name.
		File filePath = new File(theDir.toString());
		String fileName = filePath.toString() + "/";

		Blog(fileName);
		Media(fileName);
		Section(fileName);
		User(fileName);
		userRole(fileName);
		userSection(fileName);

		System.out.println("Imported");

	}

	/**
	 * To Import Section Table to DB 
	 * 
	 * @param path
	 */
	public static void Section(String path) throws SQLException {

		File file = new File(path + "Section.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();

			}
			String query2 = "DELETE FROM Section;";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO Section (section_id, section_type, subsection) " + "VALUES ("
							+ arr[0].replace("\"", "") + ",'" + arr[1].replace("\"", "") + "','"
							+ arr[2].replace("\"", "") + "');";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	/**
	 * To Import Blog Table to DB
	 * 
	 * @param path
	 */
	public static void Blog(String path) throws SQLException {

		File file = new File(path + "Blog.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String query2 = "DELETE FROM Blog;";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO Blog " + "VALUES ('" + arr[0].replace("\"", "") + "','"
							+ arr[1].replace("\"", "") + "','" + arr[2].replace("\"", "") + "','"
							+ arr[3].replace("\"", "") + "','" + arr[4].replace("\"", "") + "','"
							+ arr[5].replace("\"", "") + "','" + arr[6].replace("\"", "") + "','"
							+ arr[7].replace("\"", "") + "','" + arr[8].replace("\"", "") + "','"
							+ arr[9].replace("\"", "") + "','" + arr[10].replace("\"", "") + "','"
							+ arr[11].replace("\"", "") + "','" + arr[12].replace("\"", "") + "','"
							+ arr[13].replace("\"", "") + "');";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}
	
	/**
	 * To Import Media Table to DB
	 * 
	 * @param path
	 */
	public static void Media(String path) throws SQLException {

		File file = new File(path + "Media.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String query2 = "DELETE FROM Media;";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO Media " + "VALUES ('" + arr[0].replace("\"", "") + "','"
							+ arr[1].replace("\"", "") + "','" + arr[2].replace("\"", "") + "','"
							+ arr[3].replace("\"", "") + "');";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	/**
	 * To Import User Table to DB
	 * 
	 * @param path
	 */
	public static void User(String path) throws SQLException {

		File file = new File(path + "User.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String query2 = "DELETE FROM User Where username NOT IN (Select username from User NATURAL JOIN Role NATURAL JOIN User_Role Where User.user_id IN ( Select user_id from Role NATURAL Join User_Role Where role_type='Admin'));";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO User " + "VALUES ('" + arr[0].replace("\"", "") + "','"
							+ arr[1].replace("\"", "") + "','" + arr[2].replace("\"", "") + "','"
							+ arr[3].replace("\"", "") + "','" + arr[4].replace("\"", "") + "','"
							+ arr[5].replace("\"", "") + "');";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
					e.getMessage();
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	/**
	 * To Import User_Role Table to DB
	 * 
	 * @param path
	 */
	public static void userRole(String path) throws SQLException {

		File file = new File(path + "User_Role.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String query2 = "DELETE FROM User_Role Where role_id != '1';";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO User_Role " + "VALUES (" + arr[0].replace("\"", "") + ","
							+ arr[1].replace("\"", "") + ");";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
					
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	/**
	 * To Import User_Section Table to DB
	 * 
	 * @param path
	 */
	public static void userSection(String path) throws SQLException {

		File file = new File(path + "User_Section.csv");
		Statement s = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			try {

				s = sqlConnection.connect().createStatement();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String query2 = "DELETE FROM User_Section;";
			s.execute(query2);
			s.close();

			while ((line = br.readLine()) != null) {

				try {
					String[] arr = line.split(",");
					String sql = "INSERT INTO User_Section " + "VALUES ('" + arr[0].replace("\"", "") + "','"
							+ arr[1].replace("\"", "") + "');";
					s.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

			}
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

}