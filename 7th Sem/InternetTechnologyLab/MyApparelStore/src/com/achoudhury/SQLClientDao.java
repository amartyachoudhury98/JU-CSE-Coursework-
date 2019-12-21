package com.achoudhury;
import java.sql.*;
import java.util.ArrayList;
public class SQLClientDao extends DAOClient {
	
	private static SQLClientDao instance;
	private Connection connection;
	public static final String JDBC_CONNECTION_URL ="jdbc:sqlserver://localhost\\MSSQLSERVER:60768;databaseName=myApparelStore;user=achoudhury98;password=1234";
	public static final String VALIDATE_CUSTOMER = "SELECT * FROM customers WHERE customer_id=? AND password=?";
	public static final String CREATE_CUSTOMER ="INSERT INTO customers(customer_id,firstname,lastname,mobile_no,password,sex) VALUES (?,?,?,?,?,?)";
	public static final String CHECK_CUSTOMER_ID = "SELECT * FROM customers WHERE customer_id = ?";
	public static final String GET_CUSTOMER_SEX = "SELECT sex FROM customers WHERE customer_id= ?";
	public static final String UPDATE_CUSTOMER_PREFERENCE = "UPDATE customers SET preference = ? WHERE customer_id=?";
	public static final String GET_DISCOUNT_ITEMS = "SELECT description,imageUrl,price,discount,discount_percentage FROM items WHERE sex = ? AND discount = 'True' AND description LIKE ?";
	public static final String GET_NEW_ITEMS = "SELECT description,imageUrl,price,discount,discount_percentage FROM items WHERE sex = ? AND new_arrival = 'True' AND description LIKE ?";
	public static final String GET_CUSTOMER_PREFERENCE = "SELECT preference FROM customers WHERE customer_id=?";
	
	/* setting up sqlserver driver */
	
	private SQLClientDao(){
		 try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
		 } 
		 catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	public static SQLClientDao getInstance() {
		try {
		if( instance == null || instance.connection.isClosed()) {
			instance = new SQLClientDao();
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	public Connection getConnection() {
		return this.connection;
	}
	/*check for existing customer*/
	
	private Boolean userExists(String userid) {
		Connection conn =this.getConnection();
		Boolean exists = false;
		try {
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(CHECK_CUSTOMER_ID);
			p.setString(1,userid);
			ResultSet res = p.executeQuery();
			if(res.next()) exists= true;
			p.close();
			//conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	private String getSex(String userId) {
		String sex = "";
		Connection conn = this.getConnection();
		try {
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(GET_CUSTOMER_SEX);
			p.setString(1,userId);
			System.out.println(userId);
			ResultSet res = p.executeQuery();
			if(res.next())sex = res.getString(1);
			p.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return sex;
	}
	
	private void updatePreference(String userId,String preference) {
		Connection conn = this.getConnection();
		try {
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(UPDATE_CUSTOMER_PREFERENCE);
			p.setString(1,preference);
			p.setString(2,userId);
			p.executeUpdate();
			p.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return;
	}
	public Boolean createUser(String firstname,String lastname,String mobileNo,
			String userid,String password,String sex) {
		Connection conn = this.getConnection();
		if(userExists(userid)) return false;
		try {
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(CREATE_CUSTOMER);
			p.setString(1,userid);
			p.setString(2,firstname);
			p.setString(3, lastname);
			p.setString(4, mobileNo);
			p.setString(5,password);
			p.setString(6,sex);
			int row = p.executeUpdate();
			
			p.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public Boolean validateUser(String userid,String password) {
		Connection conn =this.getConnection();
		Boolean exists = false;
		try {
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(VALIDATE_CUSTOMER);
			p.setString(1,userid);
			p.setString(2,password);
			ResultSet res = p.executeQuery();
			if(res.next()) exists= true;
			p.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	public String getPreference(String username) {
		String preference = "";
		Connection conn = this.getConnection();
		try {
			conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p = conn.prepareStatement(GET_CUSTOMER_PREFERENCE);
			p.setString(1,username);
			ResultSet res = p.executeQuery();
			if(res.next())preference = res.getString(1);
			p.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return preference;
	}
	public ArrayList<Item> getItemsForUser(String username,String searchStr,String preference){
		Connection conn = this.getConnection();
		ArrayList<Item> items = null;
		String pattern = "%";
		for(int i=0;i<searchStr.length();i++) {
			pattern += searchStr.charAt(i)+"%";
		}
		System.out.println(pattern);
		try {
			updatePreference(username,preference);
			String sex = getSex(username);
			//conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			PreparedStatement p;
			if(preference.contentEquals("discount"))p = conn.prepareStatement(GET_DISCOUNT_ITEMS);
			else p = conn.prepareStatement(GET_NEW_ITEMS);
			p.setString(1, sex);
			p.setString(2,pattern);
			ResultSet res = p.executeQuery();
			while(res.next()) {
				if(items == null) items = new ArrayList<Item>();
				Item item = new Item(res.getString(1),res.getString(2),res.getInt(3),res.getBoolean(4),res.getInt(5));
				items.add(item);
			}
			p.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
}


