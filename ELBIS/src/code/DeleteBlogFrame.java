/**
 * @author Mohammed Ali Anis
 * This Class crates a Interface for Delete Blog
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

public class DeleteBlogFrame extends JFrame {

	private JPanel contentPane;
	private static String blog_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteBlogFrame frame = new DeleteBlogFrame(blog_id);
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
	public DeleteBlogFrame(String blog_id) {
		setResizable(false);
		setTitle("Delete Blog");
		this.blog_id = blog_id;

		System.err.println("user_id : " + blog_id);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 145);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 111);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setIcon(new ImageIcon(DeleteBlogFrame.class.getResource("/code/icons/cancel.png")));
		cancelBtn.setBounds(142, 65, 86, 29);
		panel.add(cancelBtn);

		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setIcon(new ImageIcon(DeleteBlogFrame.class.getResource("/code/icons/website.png")));
		deleteBtn.setBounds(233, 65, 79, 29);
		panel.add(deleteBtn);

		JLabel msgLabel1 = new JLabel("Are you sure you want to delete the Blog : " + blog_id + " ");
		msgLabel1.setBounds(6, 23, 426, 16);
		panel.add(msgLabel1);

		deleteBtn.addActionListener(l -> {

			try {
				sqlConnection.sqlDeleteBlog(blog_id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispose();
			cancelBtn.setText("Exit");
		});

		cancelBtn.addActionListener(l -> {
			dispose();
		});

	}
}