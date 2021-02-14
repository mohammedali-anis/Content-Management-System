/**
 * @author Mohammed Ali Anis
* This Class creates an Interface for the Admin to create new Sections and Subsections
*
*/
package code;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class EditSectionFrame extends JFrame {

	private JPanel contentPane;
	private static String section_id;
	private static String section_type;
	private JTextField renameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditSectionFrame frame = new EditSectionFrame(section_id, section_type);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditSectionFrame(String ASection_id, String ASection_type) {
		setResizable(false);
		this.section_id = ASection_id;
		this.section_type = ASection_type;
		System.err.println("section_id : " + section_id);
		System.err.println("section_type : " + section_type);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 186);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setIcon(new ImageIcon(EditSectionFrame.class.getResource("/code/icons/cancel.png")));
		cancelBtn.setBounds(125, 151, 86, 29);
		panel.add(cancelBtn);

		JButton renameBtn = new JButton("Rename");
		renameBtn.setIcon(new ImageIcon(EditSectionFrame.class.getResource("/code/icons/edit copy.png")));
		renameBtn.setBounds(216, 151, 79, 29);
		panel.add(renameBtn);

		JRadioButton subsectionRadioBtn = new JRadioButton("Rename the selected subsection");
		subsectionRadioBtn.setBounds(6, 20, 256, 23);
		subsectionRadioBtn.setSelected(true);
		panel.add(subsectionRadioBtn);

		JRadioButton sectionRadioBtn = new JRadioButton("Rename the selected section");
		sectionRadioBtn.setBounds(6, 54, 334, 23);
		panel.add(sectionRadioBtn);

		ButtonGroup group = new ButtonGroup();
		group.add(subsectionRadioBtn);
		group.add(sectionRadioBtn);

		renameField = new JTextField();
		renameField.setBounds(98, 99, 262, 26);
		panel.add(renameField);
		renameField.setColumns(10);

		JLabel renameLabel = new JLabel("New Name :");
		renameLabel.setBounds(13, 104, 86, 16);
		panel.add(renameLabel);
		/**
		 * ActionListener for the renameBtn
		 * renames selected section
		 * @param section_id id of the selected Section
		 */
		renameBtn.addActionListener(l -> {
			if (subsectionRadioBtn.isSelected()) {
				try {
					sqlConnection.sqlRenameSubsection(section_id, renameField.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (sectionRadioBtn.isSelected()) {
				try {
					sqlConnection.sqlRenameSection(section_type, renameField.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dispose();
			cancelBtn.setText("Exit");
		});

		cancelBtn.addActionListener(l -> {
			dispose();
		});

	}
}
