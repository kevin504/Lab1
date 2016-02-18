package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Homework1 {
	private static ResultSet resultSet = null;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		creatTable();
		Scanner input = new Scanner(System.in);
		boolean EXIT = false;
		while (EXIT == false) {
			System.out.println("Enter the desired command.");
			String command = input.nextLine();	
			if (command.equals("getID")) {
				System.out.println("Enter the ID you are searching for.");
				command = input.nextLine();
				getID(command);
				if (command.equals("postID")) {
					System.out.println("Enter ID");
					String ID = input.nextLine();
					System.out.println("Enter TODO message");
					String TODO = input.nextLine();
					postID(ID, TODO);
				}	
			} if (command.equals("deleteID")) {
				System.out.println("Enter the ID you wish to delete.");
				command = input.nextLine();
				deleteID(command);
			} if (command.equals("get")) {
				get();
			} if (command.equals("EXIT")) {
				EXIT = true;
			}
			System.out.println();
		}
		System.out.println("Exiting program now..");
		input.close();
	}
	public static void postID(String id, String todo) throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement postID = connection.prepareStatement("INSERT INTO lab1 VALUES ( " + id + ", " + todo + ", CURDATE() );");
			postID.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Posted completed.");
		}
	}
	
	public static void deleteID(String id) throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement deleteID = connection.prepareStatement("DELETE FROM lab1 WHERE id = '" + id + "';");
			deleteID.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void get() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		Connection connection = getConnection();
		PreparedStatement get = connection.prepareStatement("SELECT * FROM lab1;");
		resultSet = get.executeQuery();
		while (resultSet.next()) {
			String resultID = resultSet.getString(1);
			String resultTODO = resultSet.getString(2);
			map.put(resultID, resultTODO);
		}
		
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		
		while (iterator.hasNext()) {
            Map.Entry m =(Map.Entry)iterator.next();
            String key=(String)m.getKey();
            String value=(String)m.getValue();
            System.out.println("Key : " + key + " | Value :" + value);
		}
	}
	public static void getID(String id) throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement getID = connection.prepareStatement("SELECT * FROM lab1 WHERE id = '" + id + "';");
			resultSet = getID.executeQuery();
			resultSet.next();

			String resultTODO = resultSet.getString(2);
			String resultDATE = resultSet.getString(3);
			System.out.println("TODO: " + resultTODO);
			System.out.println("TIME: " + resultDATE);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public static void creatTable() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS lab1(id VARCHAR(20) NOT NULL PRIMARY KEY, todo LONGTEXT NOT NULL, timestamp DATE NOT NULL)");
			create.executeUpdate();
		} catch (Exception e) {System.out.println(e);}
		  finally{
			  System.out.println("Function complete.");
		  };
	}
public static Connection getConnection() throws Exception{
	try{
		String driver ="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/homework";
		String username="root";
		String password="Success504$";
		Class.forName(driver);
		
		Connection conn= DriverManager.getConnection(url, username, password);
		System.out.println("Connected");
		return conn;
	}catch (Exception e) {System.out.println(e);}
	

	return null;
}
	
}

