/**
 * This Class with methode to Backup the DB to CSV files
 * @author Mohammed Ali Anis, Hamze Ali
 */
package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

public class dbBackUp {
	public static File theDir;

	public static void backUp() throws SQLException, IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String folderName = formatter.format(date).toString();

		String path = "src/csv";

		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		//Make Folder with the Subsection name if it doesn't exist
		theDir = new File(absolutePath + "/" + folderName);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}

		// File path and name.
		File filePath = new File(theDir.toString());
		String fileName = filePath.toString();

		csvBlog(fileName);
		csvMedia(fileName);
		csvUser(fileName);
		csvSection(fileName);
		csvUser(fileName);
		csvRole(fileName);
		csvUser_Role(fileName);
		csvUser_Section(fileName);

		System.out.println("Data export successful.");

	}

	/**
	 * To Export Blog Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvBlog(String fileName) throws SQLException, IOException {
		String Query = "Select * From Blog";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/Blog.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
						r.getInt(6), r.getInt(7), r.getString(8), r.getString(9), r.getString(10), r.getInt(11),
						r.getInt(12), r.getInt(13), r.getInt(14));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export Media Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvMedia(String fileName) throws SQLException, IOException {
		String Query = "Select * From Media";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/Media.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getString(1), r.getInt(2), r.getInt(3), r.getString(4));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export Role Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvRole(String fileName) throws SQLException, IOException {
		String Query = "Select * From Role";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/Role.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getString(2));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export Section Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvSection(String fileName) throws SQLException, IOException {
		String Query = "Select * From Section";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/Section.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getString(2), r.getString(3));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export User Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvUser(String fileName) throws SQLException, IOException {
		String Query = "Select * From User";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/User.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
						r.getString(6));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export User_Role Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvUser_Role(String fileName) throws SQLException, IOException {
		String Query = "Select * From User_Role";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/User_Role.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getInt(2));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

	/**
	 * To Export User_Section Table to CSV file
	 * 
	 * @param fileName
	 */
	public static void csvUser_Section(String fileName) throws SQLException, IOException {
		String Query = "Select * From User_Section";
		Statement s = sqlConnection.connect().createStatement();
		ResultSet r = s.executeQuery(Query);

		try {

			// Open CSV file.
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + "/User_Section.csv"));

			// Add table headers to CSV file.
			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.DEFAULT.withHeader(r.getMetaData()).withQuoteMode(QuoteMode.ALL));

			// Add data rows to CSV file.
			while (r.next()) {

				csvPrinter.printRecord(r.getInt(1), r.getInt(2));
			}

			// Close CSV file.
			csvPrinter.flush();
			csvPrinter.close();

		} catch (SQLException e) {

			// Message stating export unsuccessful.
			System.out.println("Data export unsuccessful.");
			System.exit(0);

		}
		s.close();
		r.close();
	}

}