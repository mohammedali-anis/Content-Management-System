/**
 * This Class crates a Interface for all Editor functions such as managing Users and Blogs 
 * @author Alex, Mohammed Ali Anis, Sophie
 * 
 */
package code;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

public class EditorFrame extends JFrame {

	private JPanel panel;

	private JButton ManageBlogBtn;
	private JTable userTable;
	private JScrollPane scrollPane;
	private JButton ManageUserBtn;
	private JButton logoutBtn;
	private JButton deleteUserBtn;
	private JLabel welcomeLabel;
	private JButton editUserBtn;
	private JTable blogTable;
	private JButton deleteBlogBtn;
	private JScrollPane blogScrollPane;
	private Register register = null;
	private static String username;
	private static String deleteUsername;
	private static String blog_title;
	private static String user_id;
	private static String blog_id;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorFrame frame = new EditorFrame(username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for the Editor Frame
	 * 
	 * @param AUsername the User name of the Editor loged in
	 */
	@SuppressWarnings("serial")
	public EditorFrame(String AUsername) throws SQLException {
		this.username = AUsername;
		System.err.println(username);
		setTitle("Editor Page");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		panel = new JPanel();
		setSize(800, 500);

		/**
		 * 
		 * For Manage User
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

		userTable.setVisible(false);
		scrollPane.setVisible(false);
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
		ManageUserBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/user.png")));
		ManageUserBtn.setBounds(195, 425, 117, 29);
		panel.add(ManageUserBtn);

		JButton addUserBtn = new JButton("Add User");
		addUserBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/add-friend.png")));
		addUserBtn.setBounds(35, 266, 91, 29);
		addUserBtn.setVisible(false);
		panel.add(addUserBtn);

		deleteUserBtn = new JButton("Delete Selected User");
		deleteUserBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/delete.png")));
		deleteUserBtn.setBounds(504, 266, 172, 29);
		deleteUserBtn.setVisible(false);
		panel.add(deleteUserBtn);

		JButton refreshUserBtn = new JButton("Refresh");
		refreshUserBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/refresh-page-option.png")));
		refreshUserBtn.setBounds(690, 157, 91, 29);
		refreshUserBtn.setVisible(false);
		panel.add(refreshUserBtn);

		JButton refreshBlogBtn = new JButton("Refresh");
		refreshBlogBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/refresh-page-option.png")));
		refreshBlogBtn.setBounds(690, 225, 91, 29);
		refreshBlogBtn.setVisible(false);
		panel.add(refreshBlogBtn);

		/** To get the User ID when clicking on the Row */

		userTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				user_id = userTable.getValueAt(userTable.getSelectedRow(), 0).toString();
				deleteUsername = userTable.getValueAt(userTable.getSelectedRow(), 3).toString();
				System.out.println(user_id);
			}
		});

		/**
		 * ActionListener for the Refresh Button to keep track of changes done in the DB
		 * Loads the most recent Version of the User table of the db into the Frame
		 * table
		 */
		refreshUserBtn.addActionListener(l -> {
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
		 * 
		 * For Manage Blog
		 */

		ManageBlogBtn = new JButton("Manage Blog");
		ManageBlogBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/blog copy.png")));
		ManageBlogBtn.setBounds(361, 425, 117, 29);
		panel.add(ManageBlogBtn);

		DefaultTableModel blogTableModel = new DefaultTableModel(new String[] { "Blog ID", "Blog Title", "From", "To",
				"Section ID", "SubSection", "Website", "Terminal", "User ID" }, 0);

		blogTable = new JTable(blogTableModel);

		panel.add(new JScrollPane(blogTable));

		/** Set editable=false; */
		blogTable.setDefaultEditor(Object.class, null);

		panel.setLayout(null);

		blogScrollPane = new JScrollPane(blogTable);
		blogScrollPane.setBounds(35, 98, 641, 156);
		panel.add(blogScrollPane);
		getContentPane().add(panel);
		blogTable.setRowSelectionAllowed(true);
		blogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/** Disable Draging Columns */
		blogTable.getTableHeader().setReorderingAllowed(false);
		blogTable.getTableHeader().setOpaque(false);

		DefaultTableCellRenderer renderer2;
		renderer2 = (DefaultTableCellRenderer) blogTable.getTableHeader().getDefaultRenderer();
		renderer2.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer rightRenderer2 = new DefaultTableCellRenderer();
		rightRenderer2.setHorizontalAlignment(JLabel.CENTER);

		for (int columnIndex = 0; columnIndex < blogTable.getModel().getColumnCount(); columnIndex++) {
			blogTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer2);
		}

		JButton addBlogBtn = new JButton("Add Blog");
		addBlogBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/blog.png")));
		addBlogBtn.setBounds(35, 266, 91, 29);
		addBlogBtn.setVisible(false);
		panel.add(addBlogBtn);

		JButton editBLogBtn = new JButton("Edit Blog");
		editBLogBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/blogging.png")));
		editBLogBtn.setBounds(181, 266, 91, 29);
		editBLogBtn.setVisible(false);
		panel.add(editBLogBtn);

		deleteBlogBtn = new JButton("Delete Selected Blog");
		deleteBlogBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/website.png")));
		deleteBlogBtn.setBounds(325, 266, 172, 29);
		deleteBlogBtn.setVisible(false);
		panel.add(deleteBlogBtn);

//		  To get the Blog ID when clicking on the Row	//

		blogTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				blog_id = blogTable.getValueAt(blogTable.getSelectedRow(), 0).toString();
				System.out.println(blog_id);
				blog_title = blogTable.getValueAt(blogTable.getSelectedRow(), 1).toString();
				System.out.println(blog_title);
			}
		});

		blogTable.setRowSelectionAllowed(true);

		blogTable.setVisible(false);
		blogScrollPane.setVisible(false);

		/**
		 * Close for Manage Blog
		 */

		logoutBtn = new JButton("Logout");
		logoutBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/exit-logout-2857.png")));
		logoutBtn.setBounds(664, 425, 117, 29);

		/**
		 * ActionListener for LogoutButton let the Editor return to the LoginFrame
		 */
		logoutBtn.addActionListener(l -> {

			Login login = new Login();
			dispose();
			login.setVisible(true);

		});
		panel.add(logoutBtn);

		welcomeLabel = new JLabel("New label");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setBounds(18, 22, 377, 46);

		welcomeLabel.setText(username + " (Editor)");
		panel.add(welcomeLabel);

		editUserBtn = new JButton("Edit Selected User");
		editUserBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/edit.png")));
		editUserBtn.setBounds(240, 266, 155, 29);
		editUserBtn.setVisible(false);
		panel.add(editUserBtn);

		JRadioButton acceptedRadionBtn = new JRadioButton("Accepted Blogs");
		acceptedRadionBtn.setBounds(672, 134, 128, 23);
		panel.add(acceptedRadionBtn);

		JRadioButton pendingRadioBtn = new JRadioButton("Pending Blogs");
		pendingRadioBtn.setBounds(672, 158, 128, 23);
		panel.add(pendingRadioBtn);

		JRadioButton rejectedRadioBtn = new JRadioButton("Rejected Blogs");
		rejectedRadioBtn.setBounds(672, 181, 128, 23);
		panel.add(rejectedRadioBtn);

		ButtonGroup group = new ButtonGroup();
		group.add(acceptedRadionBtn);
		group.add(pendingRadioBtn);
		group.add(rejectedRadioBtn);
		acceptedRadionBtn.setVisible(false);
		pendingRadioBtn.setVisible(false);
		rejectedRadioBtn.setVisible(false);

		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(AdminFrame.class.getResource("/code/icons/elkenrothLogo.png")));
		iconLabel.setBounds(729, 17, 50, 65);
		panel.add(iconLabel);

		JButton checkBtn = new JButton("Allow / Disallow");
		checkBtn.setIcon(new ImageIcon(EditorFrame.class.getResource("/code/icons/decision-making.png")));
		checkBtn.setBounds(548, 266, 128, 29);
		panel.add(checkBtn);
		checkBtn.setVisible(false);

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
		register.roleComboBox.setSelectedIndex(1);
		register.roleComboBox.setEnabled(false);

		/**
		 * ActionListener for ManageUserButton makes all buttons needed to manage Users
		 * visible
		 */
		ManageUserBtn.addActionListener(l -> {

			scrollPane.setVisible(true);
			userTable.setVisible(true);
			addUserBtn.setVisible(true);
			deleteUserBtn.setVisible(true);
			refreshUserBtn.setVisible(true);
			editUserBtn.setVisible(true);

			blogScrollPane.setVisible(false);
			blogTable.setVisible(false);
			addBlogBtn.setVisible(false);
			deleteBlogBtn.setVisible(false);
			refreshBlogBtn.setVisible(false);
			editBLogBtn.setVisible(false);
			acceptedRadionBtn.setVisible(false);
			pendingRadioBtn.setVisible(false);
			rejectedRadioBtn.setVisible(false);
			checkBtn.setVisible(false);

			addUserBtn.addActionListener(w -> {

				register.setVisible(true);

			});

		});

		/**
		 * ActionListener for editUserButton which opens an new EditUserFrame via the
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
				deleteuser = new DeleteUserFrame(user_id, deleteUsername);
				deleteuser.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a user", "Warning", JOptionPane.WARNING_MESSAGE);

		});

		/**
		 * ActionListener for ManageBlogButton makes all buttons needed to manage Blogs
		 * visible
		 */
		ManageBlogBtn.addActionListener(l -> {

			blogScrollPane.setVisible(true);
			blogTable.setVisible(true);
			addBlogBtn.setVisible(true);
			deleteBlogBtn.setVisible(true);
			refreshBlogBtn.setVisible(true);
			editBLogBtn.setVisible(true);
			acceptedRadionBtn.setVisible(true);
			pendingRadioBtn.setVisible(true);
			rejectedRadioBtn.setVisible(true);
			checkBtn.setVisible(true);

			scrollPane.setVisible(false);
			userTable.setVisible(false);
			addUserBtn.setVisible(false);
			deleteUserBtn.setVisible(false);
			refreshUserBtn.setVisible(false);
			editUserBtn.setVisible(false);

		});

		/**
		 * ActionListener for editBlogButton which opens an new AddBlogFrame via the
		 * constructor of AddBlogFrame
		 * 
		 * @param username of the User who creates the blog
		 */
		addBlogBtn.addActionListener(l -> {

			AddBlogFrame addBlogFrame = null;
			try {
				addBlogFrame = new AddBlogFrame(username);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			addBlogFrame.setVisible(true);
		});

		/**
		 * ActionListener for editBlogButton which opens an new EditBlogFrame via the
		 * constructor of EditBlogFrame
		 * 
		 * @param username, blog_id of the Blog selected in the table
		 */
		editBLogBtn.addActionListener(l -> {

			EditBlogFrame editBLogFrame = null;

			if (blog_id != null && !blog_title.isEmpty()) {
				try {
					editBLogFrame = new EditBlogFrame(username, blog_id);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				editBLogFrame.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a blog", "Warning", JOptionPane.WARNING_MESSAGE);

		});

		/**
		 * ActionListener for deleteBlogButton which opens an new DeleteBlogFrame via
		 * the constructor of the deleteBlogFrame
		 * 
		 * @param blog_id id of the Blog selected in the table
		 */
		deleteBlogBtn.addActionListener(l -> {
			DeleteBlogFrame deleteBlogFrame = new DeleteBlogFrame(blog_id);
			if (blog_id != null && !blog_title.isEmpty()) {
				try {
					deleteBlogFrame.setVisible(true);
					//sqlConnection.sqlDeleteBlog(blog_id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(panel, "Please select a blog", "Warning", JOptionPane.WARNING_MESSAGE);
		});

		/**
		 * ActionListener for acceptedRadioButton sets the blog status to accepted
		 */
		acceptedRadionBtn.addActionListener(l -> {

			try {
				blogTableModel.setRowCount(0);
				sqlConnection.sqlBlogTableAccepted(username, blogTableModel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/**
		 * ActionListener for pendingRadioButton sets the blog status to pending
		 */
		pendingRadioBtn.addActionListener(l -> {

			try {
				blogTableModel.setRowCount(0);
				sqlConnection.sqlBlogTablePending(username, blogTableModel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/**
		 * ActionListener for rejectedRadioButton sets the blog status to rejected
		 */
		rejectedRadioBtn.addActionListener(l -> {

			try {
				blogTableModel.setRowCount(0);
				sqlConnection.sqlBlogTableRejected(username, blogTableModel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/**
		 * ActionListener for the refreshBlogButton to keep track of changes done in the
		 * DB Loads the status of the Blog table of the db into the Frame table
		 */
		refreshBlogBtn.addActionListener(l -> {
			blog_id = null;
			blog_title = null;
			if (acceptedRadionBtn.isSelected()) {

				try {
					blogTableModel.setRowCount(0);
					sqlConnection.sqlBlogTableAccepted(username, blogTableModel);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (pendingRadioBtn.isSelected()) {

				try {
					blogTableModel.setRowCount(0);
					sqlConnection.sqlBlogTablePending(username, blogTableModel);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (rejectedRadioBtn.isSelected()) {

				try {
					blogTableModel.setRowCount(0);
					sqlConnection.sqlBlogTableRejected(username, blogTableModel);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		/**
		 * ActionListener for checkButton which opens an new checkFrame via the
		 * constructor of the checkFrame
		 * 
		 * @param blog_id, username, blog_title username, id and blog_title of the Blog
		 *                 selected in the table
		 */
		checkBtn.addActionListener(l -> {

			CheckFrame checkframe = null;

			if (blog_id != null && !blog_title.isEmpty()) {
				try {
					checkframe = new CheckFrame(username, blog_id, blog_title);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				checkframe.setVisible(true);
			} else
				JOptionPane.showMessageDialog(panel, "Please select a blog", "Warning", JOptionPane.WARNING_MESSAGE);

		});

	}
}