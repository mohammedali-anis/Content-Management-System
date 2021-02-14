/**
 * @author Mohammed Ali Anis
 * This Class crates a Interface for Add Blog
 * 
 */
package code;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * @use creating geters and seters for panels,panes,fields and combobox
 */
public class AddBlogFrame extends JFrame {

	private JPanel contentPane;
	private JTextPane contentEditor;
	private JTextField titleField;
	private JTextField fromField;
	private JTextField toField;
	private JComboBox sectionComboBox;

	public JTextPane getContentEditor() {
		return contentEditor;
	}

	public JTextField getTitleField() {
		return titleField;
	}

	public JTextField getFromField() {
		return fromField;
	}

	public JTextField getToField() {
		return toField;
	}

	public JComboBox getSectionComboBox() {
		return sectionComboBox;
	}

	public JCheckBox getWebsiteCheckbox() {
		return websiteCheckbox;
	}

	public JCheckBox getTerminalCheckBox() {
		return terminalCheckBox;
	}

	public JTextField getImageField() {
		return imageField;
	}

	public JTextField getVideoField() {
		return videoField;
	}

	public void setContentEditor(String s) {
		this.contentEditor.setText(s);
	}

	public void setTitleField(String s) {
		this.titleField.setText(s);
	}

	public void setFromField(String s) {
		this.fromField.setText(s);
	}

	public void setToField(String s) {
		this.toField.setText(s);
	}

	public void setSectionComboBox(int s) {
		this.sectionComboBox.setSelectedIndex(s);
	}

	public void setWebsiteCheckbox(boolean s) {
		this.websiteCheckbox.setSelected(s);
	}

	public void setTerminalCheckBox(boolean s) {
		this.terminalCheckBox.setSelected(s);
	}

	public void setImageField(String s) {
		this.imageField.setText(s);
	}

	public void setVideoField(String s) {
		this.videoField.setText(s);
	}

	private JCheckBox websiteCheckbox;
	private JCheckBox terminalCheckBox;
	private JTextField imageField;
	private JTextField videoField;
	private JButton boldBtn;
	private JButton italicBtn;
	private JButton underlineBtn;
	private JComboBox textSize;
	private JComboBox textArt;
	private JButton textColor;
	public String choosertitle;
	File[] selectedFile;
	static String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBlogFrame frame = new AddBlogFrame(username);
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
	 * @use adding buttons, windows, panels ,labels, text-fields to the frame
	 *      getting sqlconnection for subsection creating paths dor image and video
	 *      getting email from user
	 * @throws SQLException
	 */
	public AddBlogFrame(String AUsername) throws SQLException {
		this.username = AUsername;
		setResizable(false);
		setTitle("Add Blog");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 650, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(false);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 638, 587);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel titleLabel = new JLabel("Title :");
		titleLabel.setBounds(6, 33, 47, 25);
		panel.add(titleLabel);

		titleField = new JTextField();
		titleField.setBounds(55, 32, 446, 26);
		panel.add(titleField);
		titleField.setColumns(10);

		JLabel contentBlog = new JLabel("Content");
		contentBlog.setBounds(6, 81, 61, 16);
		panel.add(contentBlog);

		contentEditor = new JTextPane();
		contentEditor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentEditor.setBounds(6, 100, 495, 363);
		contentEditor.setContentType("text/html");
		panel.add(contentEditor);

		JScrollPane scrollPane = new JScrollPane(contentEditor);
		scrollPane.setBounds(6, 100, 495, 363);
		panel.add(scrollPane);

		websiteCheckbox = new JCheckBox("Website");
		websiteCheckbox.setBounds(525, 121, 81, 23);
		panel.add(websiteCheckbox);

		terminalCheckBox = new JCheckBox("Terminal");
		terminalCheckBox.setBounds(525, 146, 92, 23);
		panel.add(terminalCheckBox);

//		////////////////////////////////////////
//		To set the User's Section into JComboBox

		ResultSet r1 = null;
		ArrayList<String> sectionList = new ArrayList<String>();
		/*
		 * Select subsection from Section natural join User Natural jOin User_Section
		 * where username='username'
		 */
		String Query1 = "Select subsection From Section Natural Join User Natural Join User_Section Where username = '"
				+ username + "';";
		Statement s1 = sqlConnection.connect().createStatement();
		r1 = s1.executeQuery(Query1);
		while (r1.next()) {

			String subsection = r1.getString("subsection");

			sectionList.add(subsection);

		}

		String[] sectionArr = sectionList.toArray(new String[sectionList.size()]);
//		////////////////////////////////////////

		sectionComboBox = new JComboBox(sectionArr);
		sectionComboBox.setBounds(513, 245, 119, 27);
		panel.add(sectionComboBox);

		JLabel sectionLabel = new JLabel("Select Section");
		sectionLabel.setBounds(513, 227, 93, 16);
		panel.add(sectionLabel);

		JButton exitBtn = new JButton("Exit");
		exitBtn.setIcon(new ImageIcon(AddBlogFrame.class.getResource("/code/icons/cancel.png")));
		exitBtn.setBounds(130, 552, 117, 29);
		panel.add(exitBtn);

		JButton sendBtn = new JButton("Send Request");
		sendBtn.setIcon(new ImageIcon(AddBlogFrame.class.getResource("/code/icons/question.png")));
		sendBtn.setBounds(310, 552, 117, 29);
		panel.add(sendBtn);

		JLabel fromLabel = new JLabel("From : 01.01.2020");
		fromLabel.setForeground(Color.LIGHT_GRAY);
		fromLabel.setBounds(513, 295, 119, 16);
		panel.add(fromLabel);

		fromField = new JTextField();
		fromField.setBounds(513, 312, 119, 26);
		panel.add(fromField);
		fromField.setColumns(10);

		JLabel toLabel = new JLabel("To : 30.12.2020");
		toLabel.setForeground(Color.LIGHT_GRAY);
		toLabel.setBounds(513, 350, 119, 16);
		panel.add(toLabel);

		toField = new JTextField();
		toField.setColumns(10);
		toField.setBounds(513, 367, 119, 26);
		panel.add(toField);

		JLabel imageLabel = new JLabel("Image Path :");
		imageLabel.setBounds(6, 486, 80, 25);
		panel.add(imageLabel);

		imageField = new JTextField();
		imageField.setColumns(10);
		imageField.setBounds(98, 485, 446, 26);
		panel.add(imageField);

		JLabel videoLabel = new JLabel("Video Path  :");
		videoLabel.setBounds(6, 515, 80, 25);
		panel.add(videoLabel);

		videoField = new JTextField();
		videoField.setColumns(10);
		videoField.setBounds(98, 514, 446, 26);
		panel.add(videoField);

		JButton imageBrowseBtn = new JButton("Browse");
		imageBrowseBtn.setBounds(550, 485, 82, 29);
		panel.add(imageBrowseBtn);

		JButton videoBrowseBtn = new JButton("Browse");
		videoBrowseBtn.setBounds(550, 514, 82, 29);
		panel.add(videoBrowseBtn);

		boldBtn = new JButton("B");
		boldBtn.setFocusPainted(false);
		boldBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		boldBtn.setBounds(304, 77, 45, 23);
		panel.add(boldBtn);

		boldBtn.addActionListener(l -> boldStyle());

		italicBtn = new JButton("I");
		italicBtn.setFont(new Font("Tahoma", Font.ITALIC, 12));
		italicBtn.setFocusPainted(false);
		italicBtn.setBounds(349, 77, 41, 23);
		panel.add(italicBtn);

		italicBtn.addActionListener(l -> italicStyle());

		underlineBtn = new JButton("U");
		underlineBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		underlineBtn.setFocusPainted(false);
		underlineBtn.setBounds(390, 77, 45, 23);
		panel.add(underlineBtn);

		underlineBtn.addActionListener(l -> underlineStyle());

		textSize = new JComboBox();
		textSize.setModel(new DefaultComboBoxModel(new String[] { "8", "9", "10", "11", "12", "14", "16", "18", "20",
				"22", "24", "26", "28", "30", "32", "34", "36", "38", "40" }));
		textSize.setBounds(248, 77, 56, 23);
		panel.add(textSize);

		textSize.addActionListener(l -> text_size());

		textArt = new JComboBox();
		textArt.setModel(
				new DefaultComboBoxModel(new String[] { "Tahoma", "Arial", "Arial Black", "Calibri", "Sitka Small" }));
		textArt.setBounds(167, 77, 81, 23);
		panel.add(textArt);

		textArt.addActionListener(l -> text_art());

		textColor = new JButton("Color");
		textColor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textColor.setBounds(435, 77, 65, 23);
		panel.add(textColor);

		textColor.addActionListener(l -> text_color());

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

		sendBtn.addActionListener(l -> {

			String website = "";
			String terminal = "";

			String title = titleField.getText();
			String content = contentEditor.getText();
			if (websiteCheckbox.isSelected()) {
				website = "Yes";
			} else {
				website = "No";
			}
			if (terminalCheckBox.isSelected()) {
				terminal = "Yes";
			} else {
				terminal = "No";
			}
			String subsection = sectionComboBox.getSelectedItem().toString();
			String from = fromField.getText();
			String to = toField.getText();
			String[] imageArrSplit = imageField.getText().split(", ");
			String[] videoArrSplit = videoField.getText().split(", ");

			try {
				sqlConnection.sqlAddBlog(username, title, content, website, terminal, subsection, from, to,
						imageArrSplit, videoArrSplit);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,
					"An E-Mail will be sent to the editor to check if your blog can be approved, You will recive an E-Mail about the decision soon",
					"Attention", JOptionPane.WARNING_MESSAGE);

			ResultSet r2 = null;
			/*
			 * SELECT email FROM User Natural Join User_Section Natural Join Section Natural
			 * Join Role Natural Join User_Role Where subsection = subsection AND role_type
			 * = 'Editor' ORDER BY random() LIMIT 1;
			 */
			String Query2 = "SELECT email FROM User Natural Join User_Section Natural Join Section Natural Join Role Natural Join User_Role Where subsection = '"
					+ subsection + "' AND role_type = 'Editor' ORDER BY random() LIMIT 1;";
			Statement s2 = null;
			try {
				s2 = sqlConnection.connect().createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				r2 = s2.executeQuery(Query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String toEmail = null;
			;
			try {
				while (r2.next()) {
					toEmail = r2.getString("email");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				s2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dispose();
			SendEmail.emailAddBlog(toEmail, username, title, subsection);

		});

		exitBtn.addActionListener(l -> {
			dispose();

		});

	}

	/**
	 * @use adding italicstyle to texteditor
	 */
	private void italicStyle() {
		// TODO Auto-generated method stub
		StyledDocument doc = (StyledDocument) contentEditor.getDocument();
		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setItalic(asNew, !StyleConstants.isItalic(as));
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);
	}

	/**
	 * @use adding text_size to texteditor
	 */
	private void text_size() {
		// TODO Auto-generated method stub
		String value = textSize.getSelectedItem().toString();
		int val = Integer.parseInt(value);

		StyledDocument doc = (StyledDocument) contentEditor.getDocument();
		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setFontSize(asNew, val);
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);
	}

	/**
	 * adding text_art to text editor
	 */
	private void text_art() {
		// TODO Auto-generated method stub
		String value = textArt.getSelectedItem().toString();

		StyledDocument doc = (StyledDocument) contentEditor.getDocument();
		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setFontFamily(asNew, value);
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);
	}

	/**
	 * @use adding text_color to textfield
	 */
	private void text_color() {
		// TODO Auto-generated method stub
		Color val = JColorChooser.showDialog(null, "Choose a color", null);
		StyledDocument doc = (StyledDocument) contentEditor.getDocument();
		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setForeground(asNew, val);
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);
	}

	/**
	 * @use adding boldstyle to textfield
	 */
	private void boldStyle() {
		// TODO Auto-generated method stub
		StyledDocument doc = (StyledDocument) contentEditor.getDocument();
		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setBold(asNew, !StyleConstants.isBold(as));
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);

	}

	/**
	 * @use adding underlineStyle to textfield
	 */
	private void underlineStyle() {

		StyledDocument doc = (StyledDocument) contentEditor.getDocument();

		int selectionEnd = contentEditor.getSelectionEnd();
		int selectionStart = contentEditor.getSelectionStart();
		if (selectionStart == selectionEnd) {
			return;
		}
		Element element = doc.getCharacterElement(selectionStart);
		AttributeSet as = element.getAttributes();

		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
		StyleConstants.setUnderline(asNew, !StyleConstants.isUnderline(as));
		doc.setCharacterAttributes(selectionStart, contentEditor.getSelectedText().length(), asNew, true);
	}
}
