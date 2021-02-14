/**
 * @author Mohammed Ali Anis
 * This class is an Interface for the Editor to Accept or Reject the selected Blog 
 */
package code;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.itextpdf.text.DocumentException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class CheckFrame extends JFrame {

	private JPanel contentPane;
	private JTextField titleField;
	private JTextField fromField;
	private JTextField toField;
	private JTextField imageField;
	private JTextField videoField;
	public String choosertitle;
	File[] selectedFile;

	static String username;
	static String blog_id;
	static String blog_title;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckFrame frame = new CheckFrame(username, blog_id, blog_title);
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
	public CheckFrame(String AUsername, String ABlog_id, String ABlog_title) throws SQLException {
		this.username = AUsername;
		this.blog_id = ABlog_id;
		this.blog_title = ABlog_title;
		System.err.println(blog_id);
		System.err.println(username);
		System.err.println(blog_title);
		setResizable(false);
		setTitle("Edit Blog");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 959, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(false);

		JPanel panel = new JPanel();
		panel.setBounds(315, 6, 638, 587);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel titleLabel = new JLabel("Title :");
		titleLabel.setBounds(6, 33, 47, 25);
		panel.add(titleLabel);

		titleField = new JTextField();
		titleField.setEnabled(false);
		titleField.setEditable(false);
		titleField.setBounds(55, 32, 446, 26);
		panel.add(titleField);
		titleField.setColumns(10);

		JLabel contentBlog = new JLabel("Content");
		contentBlog.setBounds(6, 81, 61, 16);
		panel.add(contentBlog);

		JEditorPane contentEditor = new JEditorPane();
		// contentEditor.setEnabled(false);
		contentEditor.setEditable(false);
		contentEditor.setBounds(6, 100, 495, 363);
		contentEditor.setContentType("text/html");
		panel.add(contentEditor);

		JScrollPane scrollPane = new JScrollPane(contentEditor);
		scrollPane.setBounds(6, 100, 495, 363);
		panel.add(scrollPane);

		JCheckBox websiteCheckbox = new JCheckBox("Website");
		websiteCheckbox.setEnabled(false);
		websiteCheckbox.setBounds(525, 121, 81, 23);
		panel.add(websiteCheckbox);

		JCheckBox terminalCheckBox = new JCheckBox("Terminal");
		terminalCheckBox.setEnabled(false);
		terminalCheckBox.setBounds(525, 146, 92, 23);
		panel.add(terminalCheckBox);

//		////////////////////////////////////////
//		To set the User's Section into JComboBox

		ResultSet r = null;
		ArrayList<String> sectionList = new ArrayList<String>();
		/*
		 * Select subsection from Section natural join User Natural jOin User_Section
		 * where username='username'
		 */
		String Query = "Select subsection From Section Natural Join User Natural Join User_Section Where username = '"
				+ username + "';";
		Statement s = sqlConnection.connect().createStatement();
		r = s.executeQuery(Query);
		while (r.next()) {

			String subsection = r.getString("subsection");

			sectionList.add(subsection);

		}

		String[] sectionArr = sectionList.toArray(new String[sectionList.size()]);
//		////////////////////////////////////////

		JComboBox sectionComboBox = new JComboBox(sectionArr);
		sectionComboBox.setEnabled(false);
		sectionComboBox.setBounds(513, 245, 119, 27);
		panel.add(sectionComboBox);

		JLabel sectionLabel = new JLabel("Subsection");
		sectionLabel.setBounds(513, 227, 93, 16);
		panel.add(sectionLabel);

		JLabel fromLabel = new JLabel("From : 01.01.2020");
		fromLabel.setForeground(Color.LIGHT_GRAY);
		fromLabel.setBounds(513, 295, 119, 16);
		panel.add(fromLabel);

		fromField = new JTextField();
		fromField.setEditable(false);
		fromField.setEnabled(false);
		fromField.setBounds(513, 312, 119, 26);
		panel.add(fromField);
		fromField.setColumns(10);

		JLabel toLabel = new JLabel("To : 30.12.2020");
		toLabel.setForeground(Color.LIGHT_GRAY);
		toLabel.setBounds(513, 350, 119, 16);
		panel.add(toLabel);

		toField = new JTextField();
		toField.setEnabled(false);
		toField.setEditable(false);
		toField.setColumns(10);
		toField.setBounds(513, 367, 119, 26);
		panel.add(toField);

		JLabel imageLabel = new JLabel("Image Path :");
		imageLabel.setBounds(6, 486, 80, 25);
		panel.add(imageLabel);

		imageField = new JTextField();
		imageField.setEnabled(false);
		imageField.setEditable(false);
		imageField.setColumns(10);
		imageField.setBounds(98, 485, 446, 26);
		panel.add(imageField);

		JLabel videoLabel = new JLabel("Video Path  :");
		videoLabel.setBounds(6, 515, 80, 25);
		panel.add(videoLabel);

		videoField = new JTextField();
		videoField.setEnabled(false);
		videoField.setEditable(false);
		videoField.setColumns(10);
		videoField.setBounds(98, 514, 446, 26);
		panel.add(videoField);

		JButton imageBrowseBtn = new JButton("Browse");
		imageBrowseBtn.setEnabled(false);
		imageBrowseBtn.setBounds(550, 485, 82, 29);
		panel.add(imageBrowseBtn);

		JButton videoBrowseBtn = new JButton("Browse");
		videoBrowseBtn.setEnabled(false);
		videoBrowseBtn.setBounds(550, 514, 82, 29);
		panel.add(videoBrowseBtn);

		ArrayList<String> dataList = sqlConnection.sqlGetBlogData(blog_id);
		titleField.setText(dataList.get(0));
		contentEditor.setText(dataList.get(1));
		fromField.setText(dataList.get(2));
		toField.setText(dataList.get(3));
		sectionComboBox.setSelectedItem(dataList.get(4));
		if (dataList.get(5).equals("Yes")) {
			websiteCheckbox.setSelected(true);
		} else {
			websiteCheckbox.setSelected(false);
		}
		if (dataList.get(6).equals("Yes")) {
			terminalCheckBox.setSelected(true);
		} else {
			terminalCheckBox.setSelected(false);
		}
		imageField.setText(dataList.get(7));
		videoField.setText(dataList.get(8));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 6, 297, 587);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton exitBtn = new JButton("Exit");
		exitBtn.setIcon(new ImageIcon(CheckFrame.class.getResource("/code/icons/cancel.png")));
		exitBtn.setBounds(26, 427, 117, 29);
		panel_1.add(exitBtn);

		JRadioButton acceptRadioBtn = new JRadioButton("Accepted");
		acceptRadioBtn.setBounds(16, 86, 141, 23);
		panel_1.add(acceptRadioBtn);

		JRadioButton rejectRadioBtn = new JRadioButton("Rejected");
		rejectRadioBtn.setBounds(16, 111, 85, 23);
		panel_1.add(rejectRadioBtn);

		ButtonGroup group = new ButtonGroup();
		group.add(acceptRadioBtn);
		group.add(rejectRadioBtn);

		JLabel infoLabel = new JLabel("The Blog has been :");
		infoLabel.setBounds(16, 45, 141, 16);
		panel_1.add(infoLabel);

		JEditorPane msgText = new JEditorPane();
		msgText.setBounds(6, 171, 285, 210);
		msgText.setVisible(false);
		panel_1.add(msgText);

		JButton okBtn = new JButton("OK");
		okBtn.setIcon(new ImageIcon(CheckFrame.class.getResource("/code/icons/enter2.png")));
		okBtn.setBounds(159, 427, 117, 29);
		panel_1.add(okBtn);

		JLabel reasonLabel = new JLabel("Reason :");
		reasonLabel.setBounds(6, 153, 61, 16);
		reasonLabel.setVisible(false);
		panel_1.add(reasonLabel);

		imageBrowseBtn.addActionListener(l -> {

			JFileChooser imageFileChooser = new JFileChooser();
			imageFileChooser.setMultiSelectionEnabled(true);

			int option = imageFileChooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File[] imagesFileArr = imageFileChooser.getSelectedFiles();
				String imageFilePathes = "";
				for (File file : imagesFileArr) {
					imageFilePathes += file.getAbsolutePath() + ", ";
				}
				imageField.setText(imageFilePathes);
			}

		});

		videoBrowseBtn.addActionListener(l -> {

			JFileChooser videoFileChooser = new JFileChooser();
			videoFileChooser.setMultiSelectionEnabled(true);

			int option = videoFileChooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File[] videoFileArr = videoFileChooser.getSelectedFiles();
				String videoFilePathes = "";
				for (File file : videoFileArr) {
					videoFilePathes += file.getAbsolutePath() + ", ";
				}
				videoField.setText(videoFilePathes);
			}

		});

		acceptRadioBtn.addActionListener(l -> {
			msgText.setVisible(false);
			reasonLabel.setVisible(false);
		});
		rejectRadioBtn.addActionListener(l -> {
			msgText.setVisible(true);
			reasonLabel.setVisible(true);
		});

		okBtn.addActionListener(l -> {

			if (acceptRadioBtn.isSelected()) {
				String toEmail = null;
				try {
					sqlConnection.sqlAcceptBlog(blog_id, username);

					ResultSet r2 = null;
					/*
					 * Select email from User Natural Join Blog where blog_id = blog_id;
					 */
					String Query2 = " Select email from User Natural Join Blog where blog_id = '" + blog_id + "';";
					Statement s2 = null;
					s2 = sqlConnection.connect().createStatement();
					r2 = s2.executeQuery(Query2);
					while (r2.next()) {
						toEmail = r2.getString("email");
					}
					s2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (terminalCheckBox.isSelected()) {
					ConverteHTMLtoPDF HTMLtoPDF = new ConverteHTMLtoPDF();
					try {
						HTMLtoPDF.converte(titleField.getText(), contentEditor.getText(),
								sectionComboBox.getSelectedItem().toString(), fromField.getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dispose();
				SendEmail.emailAcceptBlog(toEmail, AUsername, blog_title);

			} else if (rejectRadioBtn.isSelected()) {
				String toEmail = null;
				try {
					sqlConnection.sqlRejectBlog(blog_id, username);

					ResultSet r2 = null;
					/*
					 * Select email from User Natural Join Blog where blog_id = blog_id;
					 */
					String Query2 = " Select email from User Natural Join Blog where blog_id = '" + blog_id + "';";
					Statement s2 = null;
					s2 = sqlConnection.connect().createStatement();
					r2 = s2.executeQuery(Query2);
					while (r2.next()) {
						toEmail = r2.getString("email");
					}
					s2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				dispose();
				SendEmail.emailRejectBlog(toEmail, AUsername, blog_title, msgText.getText());
			}

		});

		exitBtn.addActionListener(l -> dispose());

	}
}
