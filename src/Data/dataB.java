package Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataB {
	public static void main(String[] args)
	{
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";
		double average=0;
		int counter=0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			
			
			Statement statement = connection.createStatement();
			
			//select data
			String allCustomersQuery = "SELECT * FROM logs WHERE FirstID = 208761452";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			System.out.println("FirstID\t\tSecondID\tThirdID\t\tLogTime\t\t\t\tPoint\t\tSomeDouble");
			while(resultSet.next())
			{
				counter++;
				average=average+resultSet.getDouble("Point");
			}
			average=average/counter;
			resultSet.close();		
			statement.close();		
			connection.close();		
		}
		
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("AVERAGE"+average);
	}



}

