/**
 * This Class crates a Interface for Add Blog
 * @author Mohammed Ali Anis, Hamze Ali
 * 
 */
package code;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class AddSectionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSectionFrame frame = new AddSectionFrame();
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
	public AddSectionFrame() throws SQLException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 588, 130);
		contentPane.add(panel);
		panel.setLayout(null);

		JFormattedTextField subsectionField = new JFormattedTextField();
		subsectionField.setBounds(290, 67, 292, 27);
		panel.add(subsectionField);

		JLabel sectionLabel = new JLabel("Section :");
		sectionLabel.setBounds(16, 47, 61, 16);
		panel.add(sectionLabel);

		JLabel subsectionLabel = new JLabel("Subsection :");
		subsectionLabel.setBounds(293, 47, 87, 16);
		panel.add(subsectionLabel);

		JButton addSectionBtn = new JButton("Add Section");
		addSectionBtn.setIcon(new ImageIcon(AddSectionFrame.class.getResource("/code/icons/tab.png")));
		addSectionBtn.setBounds(279, 96, 101, 29);
		panel.add(addSectionBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.setIcon(new ImageIcon(AddSectionFrame.class.getResource("/code/icons/cancel.png")));
		closeBtn.setBounds(147, 96, 101, 29);
		panel.add(closeBtn);

		// //////////////////////////////////////////////////////////////////////////

		ResultSet r = null;
		ArrayList<String> sectionList = new ArrayList<String>();
		/*
		 * Select DISTINCT section_type From Section
		 */
		// Note : With Select DISTINCT, can select the values just for one time also in
		// duplicate case !
		String Query = "Select DISTINCT section_type From Section";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
//		sectionList.add("+ Create new section +");
		while (r.next()) {

			String section_type = r.getString("section_type");

			sectionList.add(section_type);

		}

		String[] sectionArr = sectionList.toArray(new String[sectionList.size()]);

		JComboBox sectionComboBox = new JComboBox(sectionArr);
		sectionComboBox.setBounds(6, 68, 278, 27);
		panel.add(sectionComboBox);

		JRadioButton radioBtn = new JRadioButton("Create New Section");
		radioBtn.setBounds(6, 17, 153, 23);
		panel.add(radioBtn);

		textField = new JTextField();
		textField.setBounds(16, 67, 262, 26);
		panel.add(textField);
		textField.setVisible(false);
		textField.setColumns(10);

		radioBtn.addActionListener(l -> {

			if (radioBtn.isSelected()) {
				textField.setVisible(true);
				sectionComboBox.setVisible(false);
			} else if (!radioBtn.isSelected()) {
				sectionComboBox.setVisible(true);
				textField.setVisible(false);
			}
		});

		addSectionBtn.addActionListener(l -> {
			String section = null;
			String subsection = null;

			if (radioBtn.isSelected()) {
				section = String.valueOf(textField.getText());
				subsection = subsectionField.getText();

			} else {
				section = String.valueOf(sectionComboBox.getSelectedItem());
				subsection = subsectionField.getText();
			}

			try {
				if (radioBtn.isSelected()
						&& (textField.getText().equals("") || subsectionField.getText().toString().equals(""))) {
					JOptionPane.showMessageDialog(null, "You have to fill all field first !", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!radioBtn.isSelected() && subsectionField.getText().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "You have to fill the subsection field first !", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					sqlConnection.sqlAddSection(section, subsection);
					System.out.println("Section : " + section + ", Subsection : " + subsection + " has beed added ");
				}
				dispose();

			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		closeBtn.addActionListener(l -> {
			dispose();

		});
	}
}