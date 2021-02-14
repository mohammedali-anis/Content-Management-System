/**
 * This Class crates a Interface for all Admin functions such as managing Users, Blogs and Sections
 * @author Alex, Sophie
 * 
 */

package code;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class AdminFrame extends JFrame {

	private JPanel panel;
	private JTable userTable;
	private JScrollPane scrollPane;

	private JButton ManageUserBtn;
	private JButton logoutBtn;
	private JButton deleteUserBtn;
	private JButton manageSection;

	private JTable sectionTable;
	private JScrollPane sectionScrollPane;
	private JButton deleteSectionBtn;
	private JLabel welcomeLabel;
	private Register register = null;

	private JButton renameSectionBtn;
	private JButton editUserBtn;
	private static String username;
	private static String user_id;
	private static String section_id;
	private static String section_type;
	File[] folderArr;
	String absolutePath;
	File file;
	String path;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame(username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for the Admin Frame
	 * 
	 * @param AUsername the USer name of the Admin loged in
	 */

	@SuppressWarnings("serial")
	public AdminFrame(String AUsername) throws SQLException {
		this.username = AUsername;
		System.err.println(username);
		setTitle("Admin Page");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		panel = new JPanel();
		setSize(800, 500);

		/**
		 * User table which shows all Users
		 * 
		 */
		DefaultTableModel tableModel = new DefaultTableModel(
				new String[] { "User ID", "First Name", "Last Name", "Username", "Role" }, 0);

		userTable = new JTable(tableModel);
		userTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		userTable.getColumnModel().getColumn(3).setPreferredWidth(130);

		panel.add(new JScrollPane(userTable));

		/** Set editable=false; */
		userTable.setDefaultEditor(Object.class, null);

		panel.setLayout(null);

		scrollPane = new JScrollPane(userTable);
		scrollPane.setBounds(35, 98, 641, 156);
		panel.add(scrollPane);
		getContentPane().add(panel);
		userTable.setRowSelectionAllowed(true);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/** Disable Draging Columns */
		userTable.getTableHeader().setReorderingAllowed(false);

		userTable.getTableHeader().setOpaque(false);

		sqlConnection.sqlUserTable(tableModel);

		DefaultTableCellRenderer renderer;
		renderer = (DefaultTableCellRenderer) userTable.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int columnIndex = 0; columnIndex < userTable.getModel().getColumnCount(); columnIndex++) {
			userTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
		}

		/**
		 * Adding all Buttons to the Frame
		 * 
		 */

		ManageUserBtn = new JButton("Manage User");
		ManageUserBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/user.png")));
		ManageUserBtn.setBounds(35, 425, 117, 29);
		panel.add(ManageUserBtn);

		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/elkenrothLogo.png")));
		iconLabel.setBounds(729, 17, 50, 65);
		panel.add(iconLabel);

		JButton addUserBtn = new JButton("Add User");
		addUserBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/add-friend.png")));
		addUserBtn.setBounds(35, 266, 91, 29);
		addUserBtn.setVisible(false);
		panel.add(addUserBtn);

		deleteUserBtn = new JButton("Delete Selected User");
		deleteUserBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/delete.png")));
		deleteUserBtn.setBounds(504, 266, 172, 29);
		deleteUserBtn.setVisible(false);
		panel.add(deleteUserBtn);

		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/refresh-page-option.png")));
		refreshBtn.setBounds(688, 166, 91, 29);
		refreshBtn.setVisible(false);
		panel.add(refreshBtn);

		/**
		 * ActionListener for the Refresh Button to keep track of changes done in the DB
		 * Loads the most recent Version of the User table of the db into the Frame
		 * table
		 */
		refreshBtn.addActionListener(l -> {
			user_id = null;
			username = null;
			try {
				tableModel.setRowCount(0);
				sqlConnection.sqlUserTable(tableModel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/**
		 * To get the User ID when clicking on the Row via MousAdapter
		 * 
		 */

		userTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				user_id = userTable.getValueAt(userTable.getSelectedRow(), 0).toString();
				System.out.println(user_id);
				username = userTable.getValueAt(userTable.getSelectedRow(), 3).toString();
				System.out.println(username);
			}
		});

		/**
		 * Sectionmanagement table
		 * 
		 */

		DefaultTableModel SectionTableModel = new DefaultTableModel(
				new String[] { "Section ID", "Section", "Subsection" }, 0);

		sectionTable = new JTable(SectionTableModel);

		panel.add(new JScrollPane(sectionTable));

		/** Set editable=false; */
		sectionTable.setDefaultEditor(Object.class, null);

		panel.setLayout(null);

		sectionScrollPane = new JScrollPane(sectionTable);
		sectionScrollPane.setBounds(35, 98, 641, 156);
		panel.add(sectionScrollPane);
		getContentPane().add(panel);
		sectionTable.setRowSelectionAllowed(true);
		sectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/** Disable Draging Columns */

		sectionTable.getTableHeader().setReorderingAllowed(false);

		sectionTable.getTableHeader().setOpaque(false);
		sqlConnection.sqlSectionTable(SectionTableModel);

		DefaultTableCellRenderer renderer2;
		renderer2 = (DefaultTableCellRenderer) sectionTable.getTableHeader().getDefaultRenderer();
		renderer2.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer rightRenderer2 = new DefaultTableCellRenderer();
		rightRenderer2.setHorizontalAlignment(JLabel.CENTER);

		for (int columnIndex = 0; columnIndex < sectionTable.getModel().getColumnCount(); columnIndex++) {
			sectionTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer2);
		}

		JButton addSectionBtn = new JButton("Add Section");
		addSectionBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/tab.png")));
		addSectionBtn.setBounds(35, 266, 100, 29);
		addSectionBtn.setVisible(false);
		panel.add(addSectionBtn);

		deleteSectionBtn = new JButton("Delete Selected Section");
		deleteSectionBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/delete copy.png")));
		deleteSectionBtn.setBounds(504, 266, 172, 29);
		deleteSectionBtn.setVisible(false);
		panel.add(deleteSectionBtn);

		JButton refreshSectionBtn = new JButton("Refresh");
		refreshSectionBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/refresh-page-option.png")));
		refreshSectionBtn.setBounds(688, 166, 91, 29);
		refreshSectionBtn.setVisible(false);
		panel.add(refreshSectionBtn);

		/**
		 * ActionListener for the Refresh Button to keep track of changes done in the DB
		 * Loads the most recent Version of the Section table of the db into the Frame
		 * table
		 */

		refreshSectionBtn.addActionListener(l -> {
			section_id = null;
			section_type = null;
			try {
				SectionTableModel.setRowCount(0);
				sqlConnection.sqlSectionTable(SectionTableModel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		sectionScrollPane.setVisible(false);

		sectionTable.setRowSelectionAllowed(true);

		/**
		 * To get the Section ID when clicking on the Row via MousAdapter
		 * 
		 */
		sectionTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				section_id = sectionTable.getValueAt(sectionTable.getSelectedRow(), 0).toString();
				System.out.println(section_id);

				section_type = sectionTable.getValueAt(sectionTable.getSelectedRow(), 1).toString();
				System.out.println(section_type);
			}
		});

		logoutBtn = new JButton("Logout");
		logoutBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/exit-logout-2857.png")));
		logoutBtn.setBounds(664, 425, 117, 29);

		/**
		 * ActionListener for LogoutButton let the User retunr to the LoginFrame
		 */
		logoutBtn.addActionListener(l -> {

			Login login = new Login();
			dispose();
			login.setVisible(true);

		});
		panel.add(logoutBtn);

		manageSection = new JButton("Manage Section");
		manageSection.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/sections.png")));
		manageSection.setBounds(192, 425, 142, 29);
		panel.add(manageSection);

		welcomeLabel = new JLabel("New label");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setBounds(18, 22, 377, 46);

		welcomeLabel.setText(username + " (Admin)");
		panel.add(welcomeLabel);

		renameSectionBtn = new JButton("Rename Selected Section");
		renameSectionBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/edit copy.png")));
		renameSectionBtn.setBounds(240, 266, 172, 29);
		renameSectionBtn.setVisible(false);
		panel.add(renameSectionBtn);

		editUserBtn = new JButton("Edit Selected User");
		editUserBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/edit.png")));
		editUserBtn.setBounds(240, 266, 155, 29);
		editUserBtn.setVisible(false);
		panel.add(editUserBtn);

		JButton exportBtn = new JButton("Backup");
		exportBtn.setForeground(Color.BLACK);
		exportBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/csv.png")));
		exportBtn.setBounds(358, 425, 80, 29);
		panel.add(exportBtn);

		JButton importBtn = new JButton("Import");
		importBtn.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/sql1.png")));
		importBtn.setBounds(578, 425, 80, 29);
		panel.add(importBtn);

		JComboBox<String> exportDataComboBox = new JComboBox<String>();
		path = "src/csv";

		file = new File(path);
		absolutePath = file.getAbsolutePath();

		folderArr = new File(absolutePath).listFiles();

		for (File folders : folderArr) {
			if (folders.isDirectory()) {

				exportDataComboBox.addItem(folders.getName());
			}
		}
		exportDataComboBox.setMaximumRowCount(3);

		exportDataComboBox.setBounds(438, 426, 141, 27);

		panel.add(exportDataComboBox);

		/**
		 * ActionListener for manageSection makes all buttons needed to manage sections
		 * visible
		 */

		manageSection.addActionListener(l -> {
			scrollPane.setVisible(false);
			userTable.setVisible(false);
			addUserBtn.setVisible(false);
			deleteUserBtn.setVisible(false);
			refreshBtn.setVisible(false);
			editUserBtn.setVisible(false);

			sectionScrollPane.setVisible(true);
			sectionTable.setVisible(true);
			addSectionBtn.setVisible(true);
			deleteSectionBtn.setVisible(true);
			refreshSectionBtn.setVisible(true);
			renameSectionBtn.setVisible(true);

		});

		userTable.setVisible(false);
		scrollPane.setVisible(false);
		/**
		 * ActionListener for ManageUserButton makes all buttons needed to manage Users
		 * visible
		 */

		ManageUserBtn.addActionListener(l -> {

			scrollPane.setVisible(true);
			userTable.setVisible(true);
			addUserBtn.setVisible(true);
			deleteUserBtn.setVisible(true);
			refreshBtn.setVisible(true);
			editUserBtn.setVisible(true);

			sectionScrollPane.setVisible(false);
			sectionTable.setVisible(false);
			addSectionBtn.setVisible(false);
			deleteSectionBtn.setVisible(false);
			refreshSectionBtn.setVisible(false);
			renameSectionBtn.setVisible(false);

			/**
			 * creates new Register needed to add Users
			 *
			 */
			try {
				register = new Register();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			addUserBtn.addActionListener(w -> {

				register.setVisible(true);

			});

		});

		/**
		 * ActionListener for AddSectionButton which opens an new AddSectionFrame
		 */
		addSectionBtn.addActionListener(l -> {

			AddSectionFrame addsectionframe = null;
			try {
				addsectionframe = new AddSectionFrame();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			addsectionframe.setVisible(true);

		});
		/**
		 * ActionListener for deleteSectionButton which opens an new DeleteSectionFrame
		 * via the constructor of the deleteSectionFrame
		 * 
		 * @param section_id, section_type Type and id of section selected in the table
		 */
		deleteSectionBtn.addActionListener(l -> {

			DeleteSectionFrame deletesectionframe = null;
			if (section_id != null && !section_id.isEmpty()) {
				deletesectionframe = new DeleteSectionFrame(section_id, section_type);
				deletesectionframe.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a section", "Warning", JOptionPane.WARNING_MESSAGE);

		});

		/**
		 * ActionListener for editUSerButton which opens an new EditUserFrame via the
		 * constructor of EditUserFrame
		 * 
		 * @param user_id of the User selected in the table
		 */

		editUserBtn.addActionListener(l -> {

			EditUserFrame edituserframe = null;
			try {
				edituserframe = new EditUserFrame(user_id);
				edituserframe.setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Please select a user", "Warning", JOptionPane.WARNING_MESSAGE);
			}

		});

		/**
		 * ActionListener for deleteUSerButton which opens an new deleteUserFrame via
		 * the constructor of DeleteUserFrame
		 * 
		 * @param user_id of the User selected in the table
		 */

		deleteUserBtn.addActionListener(w -> {
			DeleteUserFrame deleteuser = null;
			if (user_id != null && !user_id.isEmpty()) {
				deleteuser = new DeleteUserFrame(user_id, username);
				deleteuser.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a user", "Warning", JOptionPane.WARNING_MESSAGE);

		});
		/**
		 * ActionListener for renameSectionButton which opens an new renameSectionFrame
		 * via the constructor of editSectionFrame
		 * 
		 * @param section_id, section_type Type and id of section selected in the table
		 */

		renameSectionBtn.addActionListener(l -> {

			EditSectionFrame editsectionframe = null;
			if (section_id != null && !section_id.isEmpty()) {
				editsectionframe = new EditSectionFrame(section_id, section_type);
				editsectionframe.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a section", "Warning", JOptionPane.WARNING_MESSAGE);
		});

		/**
		 * ActionListener for exportButton which exports the current state of the DB
		 * into a csv File by using the dbBackUp.backup
		 */
		exportBtn.addActionListener(l -> {

			exportDataComboBox.removeAllItems();

			int n = JOptionPane.showConfirmDialog(null, "Would you want export a Backup ?", "Export Backup",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				try {
					try {
						dbBackUp.backUp();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,
							"backup is successfully created to " + dbBackUp.theDir.toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			path = "src/csv";

			file = new File(path);
			absolutePath = file.getAbsolutePath();

			folderArr = new File(absolutePath).listFiles();

			for (File folders : folderArr) {
				if (folders.isDirectory()) {

					exportDataComboBox.addItem(folders.getName());
				}
			}
			exportDataComboBox.setMaximumRowCount(3);

		});
		/**
		 * ActionListener for importButton imports a saved state of a DB stored in a csv
		 * file into the Db by using the dbImport.import
		 */
		importBtn.addActionListener(l -> {

			int n = JOptionPane.showConfirmDialog(null, "Would you want import a Backup ?", "Import Backup",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				String version = exportDataComboBox.getSelectedItem().toString();
				try {
					dbImport.dbImport(version);
					JOptionPane.showMessageDialog(null, "Data has been successfully imported");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

	}
}
