import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import java.awt.Component;


public class AddUser extends JFrame {

	private JPanel contentPane;
	private JTextField txt_name;
	private JComboBox rank;
	
	String hostName = "db4free.net:3306";
	String dbName = "javaproje6_db";
	String db_username = "cagdasalagoz";
	String db_password = "JavaProje6";
	Connection conn=null;
	Statement stmt = null;
	private JPasswordField txt_pass1;
	private JPasswordField txt_pass2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUser frame = new AddUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void close(){this.dispose();} //pencereyi kapat fonksiyonu
	
	public boolean usernameExistCheck(String username) //username db de var mi kontrolu
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			String UserCheckStringSql = "SELECT * FROM users WHERE users_name='" + username + "'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(UserCheckStringSql);
			
			if(rs.next()){conn.close();return true;} //hesap daha once kayit edilmise true dondur
			else{conn.close(); return false; }		//edilmemisse false dondur.
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;//eger fonksiyon duzgun calismazsa true degeri verecek.
	}
	
	//kullanici ekleme fonksiyonu
	public void AddUser_f(String username,String password,int rank) 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			
			String AddUserSql = "INSERT INTO `javaproje6_db`.`users` (`users_name`, `users_password`, `users_rank`) VALUES ('"+username+"', '"+password+"', '"+rank+"')";
			
			stmt = conn.createStatement();
			stmt.executeUpdate(AddUserSql);
			conn.close();
			
			JOptionPane.showMessageDialog(null, username + "'s account successfuly created.");
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int RankChecker_f(JComboBox rank_sec) //combobox'dan rank'in sirasini ceken fonksiyon.
	{
	return rank_sec.getSelectedIndex();
	}
	
	
	/**
	 * Create the frame.
	 */
	public AddUser() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//passwordleri degiskene ata
				String first = new String(txt_pass1.getPassword());
			    String second = new String(txt_pass2.getPassword());
			    
			    //birinci ve ikinci password ayni mi sorgusu
			    if (first.equals("") || second.equals("") || txt_name.getText().equals("")) {
			    	JOptionPane.showMessageDialog(null,
						    "You can't left fields empty.",
						    "You're doing it wrong!",
						    JOptionPane.ERROR_MESSAGE);
			    
			    } else if (!first.equals(second)) {
			    	JOptionPane.showMessageDialog(null,
						    "The password and confirmation password do not match.",
						    "You're doing it wrong!",
						    JOptionPane.ERROR_MESSAGE);
			    }
			    else {
			    	
			    	//username de bosluk var mi sorgusu
			    	if(txt_name.getText().contains(" "))
			    	{
			    		JOptionPane.showMessageDialog(null,
						    "You can't use blank in a username!",
						    "You're doing it wrong!",
						    JOptionPane.ERROR_MESSAGE);}
			    	else{
			    		if(usernameExistCheck(txt_name.getText()))
			    		{
			    			JOptionPane.showMessageDialog(null,
								    "This username is already exist!",
								    "You're doing it wrong!",
								    JOptionPane.ERROR_MESSAGE);
			    		}
			    		else{
			    		
					    	//Olusturulmak istenen hesabin yetkileri neler olacak?
					    	if(RankChecker_f(rank)==1)
					    	{
					    		String passText = new String(txt_pass1.getPassword());
					    		AddUser_f(txt_name.getText(), passText, 1);
					    	}
					    	else if(RankChecker_f(rank)==0)
					    	{
					    		String passText = new String(txt_pass1.getPassword());
					    		AddUser_f(txt_name.getText(), passText, 0);
					    	}
			    		}
			    	}
			    	
			    }
				
			}
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin Ad = new Admin();
				Ad.setVisible(true);
				close();
			}
		});
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblUserPassword = new JLabel("User Password");
		
		JLabel lblUserPasswordagain = new JLabel("User Password (Again)");
		
		txt_name = new JTextField();
		txt_name.setColumns(10);
		
		JLabel lblUserRank = new JLabel("User Rank");
		
		rank = new JComboBox();
		
		rank.setModel(new DefaultComboBoxModel(new String[] {"Admin", "User"}));
		rank.setSelectedIndex(1);
		
		txt_pass1 = new JPasswordField();
		
		txt_pass2 = new JPasswordField();
		
		//pencereyi ortala
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUserPassword)
						.addComponent(lblUsername)
						.addComponent(lblUserRank)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(btnClose)
							.addComponent(lblUserPasswordagain)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(43)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(rank, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddUser))
							.addContainerGap(14, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_name)
							.addGap(11))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_pass1)
							.addGap(11))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_pass2, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
							.addGap(11))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUsername)
						.addComponent(txt_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUserPassword))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(txt_pass1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUserPasswordagain))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(txt_pass2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUserRank)
						.addComponent(rank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnAddUser))
					.addContainerGap(9, Short.MAX_VALUE))
		);
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {txt_name, txt_pass1, txt_pass2});
		contentPane.setLayout(gl_contentPane);
	}
}
