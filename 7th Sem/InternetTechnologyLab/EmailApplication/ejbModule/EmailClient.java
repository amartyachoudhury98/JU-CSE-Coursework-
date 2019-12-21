import java.awt.BorderLayout;

import java.awt.EventQueue;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DropMode;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.util.ArrayList;

public class EmailClient extends JFrame {

	private JPanel contentPane;
	private JTextField recieverId;
	private JTextField senderId;
	private JTextField subject;
	private JTable inbox;
	private JTable sent;
	private JPasswordField passwordField;
	private JTextField username;
	private JPasswordField inboxpassword;

	private int inboxViewStart;
	private int inboxViewEnd;
	
	private int sentViewStart;
	private int sentViewEnd;
	
	Message[] msgsInbox;
	Message[] msgsSent;
	private JTextField usernameSender;
	private JPasswordField passwordSender;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailClient frame = new EmailClient();
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
	public EmailClient() {
		
		inboxViewStart = 1;
		inboxViewEnd = 100;
		
		sentViewStart = 1;
		sentViewEnd = 100;
		
		setTitle("Mail Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel composePanel = new JPanel();
		composePanel.setForeground(SystemColor.activeCaption);
		tabbedPane.addTab("Compose", null, composePanel, null);
		composePanel.setLayout(null);
		
		JLabel lblTo = new JLabel("To :");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTo.setForeground(SystemColor.desktop);
		lblTo.setBounds(25, 25, 56, 16);
		composePanel.add(lblTo);
		
		recieverId = new JTextField();
		recieverId.setBounds(100, 23, 253, 22);
		composePanel.add(recieverId);
		recieverId.setColumns(10);
		
		JLabel lblFrom = new JLabel("From :");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFrom.setForeground(SystemColor.desktop);
		lblFrom.setBounds(12, 66, 56, 16);
		composePanel.add(lblFrom);
		
		senderId = new JTextField();
		senderId.setBounds(100, 64, 253, 22);
		composePanel.add(senderId);
		senderId.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSubject.setForeground(SystemColor.desktop);
		lblSubject.setBounds(12, 155, 69, 22);
		composePanel.add(lblSubject);
		
		subject = new JTextField();
		subject.setBounds(100, 153, 441, 22);
		composePanel.add(subject);
		subject.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMessage.setBackground(new Color(240, 240, 240));
		lblMessage.setForeground(SystemColor.desktop);
		lblMessage.setBounds(12, 197, 69, 22);
		composePanel.add(lblMessage);
		
		JTextArea messageArea = new JTextArea(10,10);
		messageArea.setLocation(0, 0);
		
		composePanel.add(messageArea,BorderLayout.CENTER);
		JScrollPane scrolltextArea = new JScrollPane(messageArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		composePanel.add(scrolltextArea);
		scrolltextArea.setBounds(100, 190, 400, 300);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String to = recieverId.getText();
				String from = senderId.getText();
				String password = passwordField.getText();
				String sub = subject.getText();
				String msg = messageArea.getText();
				EmailUtility.getInstance().send(from, password, to, sub, msg);
				
			}
		});
		
		btnSend.setBounds(479, 20, 97, 25);
		composePanel.add(btnSend);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(12, 110, 82, 16);
		composePanel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 108, 129, 22);
		composePanel.add(passwordField);
		JPanel inboxPanel = new JPanel();
		tabbedPane.addTab("Inbox", null, inboxPanel, null);
		inboxPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(12, 122, 563, 342);
		inboxPanel.add(scrollPane);
		
		inbox = new JTable();
		inbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("clicked");
				JTable source = (JTable)e.getSource();
				int row = source.rowAtPoint(e.getPoint());
				System.out.println(row);
				Message msg = msgsInbox[100 - row -1];
				String email  = EmailUtility.getInstance().getCompleteMessage(msg,username.getText(),inboxpassword.getText());
				EmailViewer emailViewer = new EmailViewer();
				emailViewer.textArea.setText(email);
				emailViewer.setVisible(true);
			}
		});
		scrollPane.setViewportView(inbox);
		
		JButton btnRefreshInbox = new JButton("Refresh");
		btnRefreshInbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = username.getText();
				String password = inboxpassword.getText();
				msgsInbox = EmailUtility.getInstance().recieve(user, password,inboxViewStart,inboxViewEnd);
				if(msgsInbox != null) {
					DefaultTableModel inboxmodel = (DefaultTableModel)inbox.getModel();
					int rowCount = inboxmodel.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
					    inboxmodel.removeRow(i);
					}
					for(int i= msgsInbox.length -1;i>=0;i--) {
							Message msg = msgsInbox[i];
							try {
								if(msg.getFlags().contains(Flags.Flag.RECENT)) inboxmodel.addRow(new Object[] {msg.getSentDate(),msg.getFrom()[0],msg.getSubject(),"RECENT"});
								else if(msg.getFlags().contains(Flags.Flag.SEEN)) inboxmodel.addRow(new Object[] {msg.getSentDate(),msg.getFrom()[0],msg.getSubject(),"SEEN"});
								else inboxmodel.addRow(new Object[] {msg.getSentDate(),msg.getFrom()[0],msg.getSubject(),"UNREAD"});
							} catch (MessagingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
			
				}
			}
		});
		btnRefreshInbox.setBounds(493, 477, 82, 25);
		inboxPanel.add(btnRefreshInbox);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(12, 30, 76, 16);
		inboxPanel.add(lblUsername);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(100, 28, 253, 22);
		inboxPanel.add(username);
		
		JLabel label_1 = new JLabel("Password:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(12, 74, 82, 16);
		inboxPanel.add(label_1);
		
		inboxpassword = new JPasswordField();
		inboxpassword.setBounds(100, 72, 129, 22);
		inboxPanel.add(inboxpassword);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inboxViewStart += 100;
				inboxViewEnd += 100;
				btnRefreshInbox.doClick();
			}
		});
		btnNext.setBounds(358, 477, 97, 25);
		inboxPanel.add(btnNext);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inboxViewStart -= 100;
				inboxViewEnd -= 100;
				btnRefreshInbox.doClick();
			}
		});
		btnPrevious.setBounds(230, 477, 97, 25);
		inboxPanel.add(btnPrevious);
		DefaultTableModel inboxModel = (DefaultTableModel)inbox.getModel();
		
		JPanel sentPanel = new JPanel();
		tabbedPane.addTab("Sent", null, sentPanel, null);
		sentPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 104, 555, 357);
		sentPanel.add(scrollPane_1);
		
		sent = new JTable();
		sent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable)e.getSource();
				int row = source.rowAtPoint(e.getPoint());
				System.out.println(row);
				Message msg = msgsSent[100 - row -1];
				String email  = EmailUtility.getInstance().getCompleteSentMessage(msg);
				EmailViewer emailViewer = new EmailViewer();
				emailViewer.textArea.setText(email);
				emailViewer.setVisible(true);
			}
		});
		scrollPane_1.setViewportView(sent);
		
		JButton btnRefreshSent = new JButton("Refresh");
		btnRefreshSent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameSender.getText();
				String password = passwordSender.getText();
				msgsSent = EmailUtility.getInstance().prefetchSent(username, password,sentViewStart,sentViewEnd);
				if(msgsSent != null) {
					DefaultTableModel sentmodel = (DefaultTableModel)sent.getModel();
					int rowCount = sentmodel.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
					    sentmodel.removeRow(i);
					}
					for(int i= msgsSent.length -1;i>=0;i--) {
							Message msg = msgsSent[i];
							try {
								if(msg.getFlags().contains(Flags.Flag.RECENT)) sentmodel.addRow(new Object[] {msg.getSentDate(),msg.getRecipients(RecipientType.TO)[0],msg.getSubject()});
								else if(msg.getFlags().contains(Flags.Flag.SEEN)) sentmodel.addRow(new Object[] {msg.getSentDate(),msg.getRecipients(RecipientType.TO)[0],msg.getSubject()});
								else sentmodel.addRow(new Object[] {msg.getSentDate(),msg.getFrom()[0],msg.getSubject()});
							} catch (MessagingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
			
				}
				
			}
		});
		btnRefreshSent.setBounds(481, 474, 97, 25);
		sentPanel.add(btnRefreshSent);
		
		usernameSender = new JTextField();
		usernameSender.setColumns(10);
		usernameSender.setBounds(111, 25, 253, 22);
		sentPanel.add(usernameSender);
		
		JLabel label = new JLabel("Username:");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(23, 27, 76, 16);
		sentPanel.add(label);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(23, 71, 82, 16);
		sentPanel.add(label_2);
		
		passwordSender = new JPasswordField();
		passwordSender.setBounds(111, 69, 129, 22);
		sentPanel.add(passwordSender);
		
		JButton btnNext_1 = new JButton("Next");
		btnNext_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sentViewStart +=100;
				sentViewEnd += 100;
				btnRefreshSent.doClick();
			}
		});
		btnNext_1.setBounds(372, 474, 97, 25);
		sentPanel.add(btnNext_1);
		
		JButton btnPrevious_1 = new JButton("Previous");
		btnPrevious_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sentViewStart -=100;
				sentViewEnd -= 100;
				btnRefreshSent.doClick();
			}
		});
		btnPrevious_1.setBounds(267, 474, 97, 25);
		sentPanel.add(btnPrevious_1);
		DefaultTableModel sentModel = (DefaultTableModel)sent.getModel();
		sentModel.addColumn("Time");
		sentModel.addColumn("Recipient");
		sentModel.addColumn("Subject");
		
		inboxModel.addColumn("Time");
		inboxModel.addColumn("Sender");
		inboxModel.addColumn("Subject");
		inboxModel.addColumn("Status");
		
	}
}
