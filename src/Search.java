import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

public class Search extends JFrame {

	 private JPanel contentPane;
	 JScrollPane scrollPane;
	 String hostName = "db4free.net:3306";
	 String dbName = "javaproje6_db";
	 String db_username = "cagdasalagoz";
	 String db_password = "JavaProje6";
	 Connection conn=null;
	 Statement stmt = null;
	 ResultSet rs;
	 JTable table;
	 private JTextField searchTextField;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JScrollPane filltable()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			String tableSql ="SELECT products_name,products_price,products_quantity FROM javaproje6_db.products;";
			// create the java statement
		    Statement stmt = conn.createStatement();
		       
		    // execute the query, and get a java resultset
		    rs = stmt.executeQuery(tableSql);
		    
		    table = new JTable(buildTableModel(rs));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JScrollPane(table);   
	}
	
	public JScrollPane filltableWithSearch(String searchtext)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostName + "/" + dbName, db_username, db_password);
			//String tableSql ="SELECT products_name,products_price,products_quantity FROM javaproje6_db.products WHERE products_name LIKE '"+searchtext+"'OR products_price LIKE '"+searchtext+"' OR products_quantity LIKE '"+searchtext+"';";
			String tableSql ="SELECT products_name,products_price,products_quantity FROM javaproje6_db.products WHERE products_name LIKE 'Kalem';";
			// create the java statement
		    Statement stmt = conn.createStatement();
		       
		    // execute the query, and get a java resultset
		    rs = stmt.executeQuery(tableSql);
		    
		    table = new JTable(buildTableModel(rs));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JScrollPane(table);   
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	   /* for (int column = 1; column <= columnCount; column++) {
	      columnNames.add(metaData.getColumnName(column));
	    }
	    */
	    columnNames.add("Name");
	    columnNames.add("Price");
	    columnNames.add("Quantity");
	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);
	}
	/**
	 * Create the frame.
	 */
	public Search() {
		setTitle("Stok Takip Programı - Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//pencereyi ortala
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				scrollPane = filltableWithSearch("boy");
				
				scrollPane.setBounds(5, 41, 464, 130);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(314, 6, 155, 29);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane = filltableWithSearch("boy");
				try {
					buildTableModel(rs).fireTableChanged(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.setLayout(null);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(5, 5, 188, 28);
		searchTextField.setColumns(10);
		contentPane.add(searchTextField);
		contentPane.add(btnSearch);
		contentPane.add(scrollPane);
	}
}
