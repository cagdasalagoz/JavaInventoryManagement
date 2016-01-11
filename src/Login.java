import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnNewButton;
	
	//db connection bilgileri
	 String hostName = "db4free.net:3306";
	 String dbName = "javaproje6_db";
	 String db_username = "cagdasalagoz";
	 String db_password = "JavaProje6";
	 Connection conn=null;
	 Statement stmt = null;
	 
	/**
	 * Launch the application.
	 */
	 public void dispose(){this.dispose();}
	 public void hide(){this.hide();}
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public void sqlLogin(String log_username,String log_password){
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			    conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
				
			    String loginSqlStringAdmin = "SELECT * FROM users WHERE users_name='" + log_username + "' && users_password='" + log_password+ "'" + "&& users_rank=0";
			    String loginSqlStringUser = "SELECT * FROM users WHERE users_name='" + log_username + "' && users_password='" + log_password+ "'" + "&& users_rank=1";
			    
			    stmt = conn.createStatement();
		        ResultSet rs_a = stmt.executeQuery(loginSqlStringAdmin);
			    //eleman varsa admin penceresini ac
			     if(rs_a.next()){ Admin Frame_admin = new Admin();
					Frame_admin.setVisible(true);
					}
			     else if(!rs_a.next())
			     {
			    	 ResultSet rs_u = stmt.executeQuery(loginSqlStringUser);
			    	 if(rs_u.next())
			    	 {
			    		 User US = new User();
			    		 US.setVisible(true);
			    	 }
			    	 else if(!rs_u.next())
			    	 {
			    		 JOptionPane.showMessageDialog(null,
								    "Wrong username or password.",
								    "You're doing it wrong!",
								    JOptionPane.ERROR_MESSAGE);
			    	 }
			     }
			    	  /*dongu de kullanilabilirdi ama sadece 2 cesit kullanici oldugu icin
			    	   * gerek duymadim.
			    	   */
			     
			      try
			      {
			          if(conn != null)
			              conn.close();
			      } catch (SQLException e) {
			          e.printStackTrace();
			      }
			      
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
	/**
	 * Create the frame.
	 */
	public Login() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 231, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 150, 0};
		gbl_contentPane.rowHeights = new int[]{28, 28, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		contentPane.add(lblUsername, gbc_lblUsername);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		contentPane.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		contentPane.add(passwordField, gbc_passwordField);
		
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String passText = new String(passwordField.getPassword());
				sqlLogin(textField.getText(), passText);
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

}
