/**
* This Class creates an Interface to edit already existing Users
* @author Alex, Sophie
*
*/


package code;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class EditUserFrame extends JFrame {

	private JPanel contentPane;
	private static JTextField firstnameField;
	private static JTextField lastnameField;
	private static JTextField usernameField;
	private static JPasswordField passwordField;
	private static JPasswordField confirmPasswordField;
	private static JTextField emailField;
	private static JButton updateBtn;
	private static String user_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditUserFrame frame = new EditUserFrame(user_id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public EditUserFrame(String AUser_id) throws SQLException {
		setResizable(false);
		this.user_id = AUser_id;
		System.err.println(user_id);
		setTitle("Edit User");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 350);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel firstnameLabel = new JLabel("First Name :");
		firstnameLabel.setBounds(20, 28, 92, 16);
		panel.add(firstnameLabel);

		JLabel lastnameLabel = new JLabel("Last Name :");
		lastnameLabel.setBounds(20, 56, 74, 16);
		panel.add(lastnameLabel);

		JLabel usernameLabel = new JLabel("Username :");
		usernameLabel.setBounds(20, 84, 74, 16);
		panel.add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setBounds(20, 182, 74, 16);
		panel.add(passwordLabel);

		JLabel confirmPasswrodLabel = new JLabel("Confirm Password :");
		confirmPasswrodLabel.setBounds(20, 210, 130, 16);
		panel.add(confirmPasswrodLabel);

		firstnameField = new JTextField();
		firstnameField.setBounds(149, 23, 130, 26);
		panel.add(firstnameField);
		firstnameField.setColumns(10);

		lastnameField = new JTextField();
		lastnameField.setColumns(10);
		lastnameField.setBounds(149, 51, 130, 26);
		panel.add(lastnameField);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(149, 79, 130, 26);
		panel.add(usernameField);

		passwordField = new JPasswordField();
		passwordField.setBounds(149, 177, 130, 21);
		passwordField.setEchoChar((char) 0);
		panel.add(passwordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(149, 205, 130, 21);
		confirmPasswordField.setEchoChar((char) 0);
		panel.add(confirmPasswordField);

		updateBtn = new JButton("Update");
		updateBtn.setIcon(new ImageIcon(EditUserFrame.class.getResource("/code/icons/edit.png")));
		updateBtn.setBounds(303, 121, 117, 29);
		panel.add(updateBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.setIcon(new ImageIcon(EditUserFrame.class.getResource("/code/icons/cancel.png")));
		closeBtn.setBounds(303, 149, 117, 29);
		panel.add(closeBtn);

		closeBtn.addActionListener(l -> {
			dispose();

		});

		JLabel emailLabel = new JLabel("E-Mail :");
		emailLabel.setBounds(20, 117, 74, 16);
		panel.add(emailLabel);

		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(149, 112, 130, 26);
		panel.add(emailField);

		JList sectionList = new JList();
		sectionList.setVisibleRowCount(4);
		sectionList.setBounds(106, 271, 140, 165);
		panel.add(sectionList);

		DefaultListModel model = new DefaultListModel();
		ArrayList<String> section = sqlConnection.getSection();
		model.addAll(section);
		sectionList.setModel(model);

		panel.add(new JScrollPane(sectionList));

		// panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(sectionList);
		scrollPane.setBounds(149, 249, 191, 84);
		panel.add(scrollPane);
		getContentPane().add(panel);

		JLabel sectionLabel = new JLabel("Sections :");
		sectionLabel.setBounds(20, 251, 92, 16);
		panel.add(sectionLabel);

		JLabel roleLabel = new JLabel("Role :");
		roleLabel.setBounds(20, 145, 74, 16);
		panel.add(roleLabel);

		JComboBox roleComboBox = new JComboBox();
		roleComboBox.setModel(new DefaultComboBoxModel(new String[] { "Editor", "User" }));
		roleComboBox.setBounds(149, 141, 130, 26);
		panel.add(roleComboBox);

		ArrayList<String> dataList = sqlConnection.sqlGetUserData(user_id, sectionList);
		firstnameField.setText(dataList.get(0));
		lastnameField.setText(dataList.get(1));
		usernameField.setText(dataList.get(2));
		emailField.setText(dataList.get(3));
		roleComboBox.setSelectedItem(dataList.get(4));
		passwordField.setText(dataList.get(5));
		confirmPasswordField.setText(dataList.get(5));
		/**ActionListener for updateBtn 
		 * updates the edited User to the DB using sqlConnection.sqlUpdateUser
		 * @param user_id
		 */
		updateBtn.addActionListener(l -> {
			if (passwordField.getText().equals(confirmPasswordField.getText())) {

				ArrayList<String> selectedSectionList = (ArrayList<String>) sectionList.getSelectedValuesList();

				try {

					sqlConnection.sqlUpdateUser(user_id, firstnameField.getText(), lastnameField.getText(),
							usernameField.getText(), passwordField.getText(), emailField.getText(), selectedSectionList,
							roleComboBox.getSelectedItem().toString());
					System.out.println("User Updated");

				} catch (Exception e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(null, "Password does not match !", "Error", JOptionPane.ERROR_MESSAGE);
			dispose();

		});

	}
}