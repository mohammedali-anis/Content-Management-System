/**
 * @author Mohammed Ali Anis
 * This Class crates a Interface for Delete Section
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
import javax.swing.ImageIcon;

public class DeleteSectionFrame extends JFrame {

	private JPanel contentPane;
	private static String section_id;
	private static String section_type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteSectionFrame frame = new DeleteSectionFrame(section_id, section_type);
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
	public DeleteSectionFrame(String ASection_id, String ASection_type) {
		setResizable(false);
		this.section_id = ASection_id;
		this.section_type = ASection_type;
		System.err.println("section_id : " + section_id);
		System.err.println("section_type : " + section_type);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 158);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 124);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setIcon(new ImageIcon(DeleteSectionFrame.class.getResource("/code/icons/cancel.png")));
		cancelBtn.setBounds(133, 89, 86, 29);
		panel.add(cancelBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setIcon(new ImageIcon(DeleteSectionFrame.class.getResource("/code/icons/delete copy.png")));
		deleteBtn.setBounds(231, 89, 79, 29);
		panel.add(deleteBtn);
		
		JRadioButton subsectionRadioBtn = new JRadioButton("Delete the selected subsection only");
		subsectionRadioBtn.setBounds(6, 20, 256, 23);
		subsectionRadioBtn.setSelected(true);
		panel.add(subsectionRadioBtn);
		
		JRadioButton sectionRadioBtn = new JRadioButton("Delete the section with his belogns subsections");
		sectionRadioBtn.setBounds(6, 54, 334, 23);
		panel.add(sectionRadioBtn);
		
		ButtonGroup group = new ButtonGroup();
		group.add(subsectionRadioBtn);
		group.add(sectionRadioBtn);
		
/** ActionListener for deleteSectionButton
		 * deletes a Section or a Subsection
		 *
		 */
		deleteBtn.addActionListener(l -> {
			if(subsectionRadioBtn.isSelected()) {
				try {
					sqlConnection.sqlDeleteSubsection(section_id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(sectionRadioBtn.isSelected()) {
				try {
					sqlConnection.sqlDeleteSection(section_type);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dispose();
			cancelBtn.setText("Exit");
		});
		
/** ActionListener for cancelButton
		 * closes the frame
		 */
		cancelBtn.addActionListener(l -> {
			dispose();
		});
		
	}
}
