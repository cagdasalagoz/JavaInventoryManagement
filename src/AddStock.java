import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box.Filler;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;


public class AddStock extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	String hostName = "db4free.net:3306";
	 String dbName = "javaproje6_db";
	 String db_username = "cagdasalagoz";
	 String db_password = "JavaProje6";
	 Connection conn=null;
	 Statement stmt = null;
	 private JLabel lblHowMuchStock;
	 int leftstockint;
	 private MyDocumentFilter documentFilter;
	 private JTextField txt_incnumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStock frame = new AddStock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void close(){this.dispose();}
	
	public int StockCheck(String productName) //urunden stokta kactane bulundugunu gosteren fonk.
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			String StockCheckSql = "Select products_quantity FROM products where products_name='"+productName+"';";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(StockCheckSql);
			rs.next();
			leftstockint = rs.getInt(1);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leftstockint;
	}
	
	public void AddStock_f(String productName,int increase) //stok arttiran fonksiyon
	{
		int stocknew=StockCheck(productName)+increase;
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
				String AddStockSql = "UPDATE `javaproje6_db`.`products` SET `products_quantity`='"+stocknew+"' WHERE `products_name`='"+productName+"';";
				stmt = conn.createStatement();
				stmt.executeUpdate(AddStockSql);
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	public void fillCombobox() //combobox'a db'den veri ceken fonksiyon
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			String comboSql = "SELECT * FROM javaproje6_db.products";
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(comboSql);
			
			while (rs.next()) {  
				comboBox.addItem(rs.getString("products_name"));  
			 }
				conn.close();
			    
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
	public AddStock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 145);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAddStock = new JButton("Add Stock");
		btnAddStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddStock_f(comboBox.getSelectedItem().toString(),Integer.parseInt(txt_incnumber.getText()));
				JOptionPane.showMessageDialog(null, "Stock successfuly added to the products.");
			}
		});
		
		JLabel lblWichProductYou = new JLabel("Wich product you want to add stock");
		
		comboBox = new JComboBox();
		fillCombobox();
		comboBox.setToolTipText("");
		
		lblHowMuchStock = new JLabel("How much stock you're getting?");
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		
		decimalFormat.setGroupingUsed(false);
		
		txt_incnumber = new JTextField();
		((AbstractDocument)txt_incnumber.getDocument()).setDocumentFilter(
                new MyDocumentFilter());        
        contentPane.add(txt_incnumber); 
        
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		close();
	}
});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWichProductYou)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblHowMuchStock)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txt_incnumber)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnClose)
							.addPreferredGap(ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
							.addComponent(btnAddStock)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWichProductYou)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHowMuchStock)
						.addComponent(txt_incnumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddStock)
						.addComponent(btnClose))
					
					.addGap(18)
					.addComponent(btnAddStock)
					.addGap(422))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
