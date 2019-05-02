import java.sql.*;
//import org.postgresql.Driver;

public class DataPersistence {
	
	//Refers to email, firstName, lastName columns in person(table below) and email, skill defined in applicant(table below)
	String userEmail;
	String userSkill;
	String firstName;
	String lastName;
	
	//Constructor, get, and set methods for best practice
	public DataPersistence(String userEmail, String userSkill, String firstName, String lastName) {
		this.userEmail = userEmail;
		this.userSkill = userSkill;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void setSkill(String userSkill) {
		this.userSkill = userSkill;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return userEmail;
	}
	
	public String getSkill() {
		return userSkill;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void createDB(DataPersistence db) throws SQLException {
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
				
		//Create person table
		PreparedStatement stCreatePerson = conn.prepareStatement("CREATE TABLE person (email varchar(256) PRIMARY KEY, firstName varchar(32), lastName varchar(32))");
		
		//Insert values into person table
		PreparedStatement stInsertPerson = conn.prepareStatement("INSERT INTO person (email, firstName, lastName) VALUES (?, ?, ?)");
				
		//Create applicant table
		PreparedStatement stCreateApplicant = conn.prepareStatement("CREATE TABLE applicant (email varchar(256) REFERENCES person, skill varchar(32))");
				
		stInsertPerson.setString(1, db.getEmail());
		stInsertPerson.setString(2, db.getFirstName());
		stInsertPerson.setString(3, db.getLastName());
				
		stCreatePerson.executeUpdate();
		stInsertPerson.executeUpdate();
		stCreateApplicant.executeUpdate();
		
		stCreatePerson.close();
		stInsertPerson.close();
		stCreateApplicant.close();
	}
	
	public void insertData(DataPersistence db) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		
		//Insert into applicant table
		PreparedStatement stInsertApplicant = conn.prepareStatement("INSERT INTO APPLICANT (EMAIL, SKILL) VALUES (?, ?)");
		
		stInsertApplicant.setString(1, db.getEmail());
		stInsertApplicant.setString(2, db.getSkill());
		
		stInsertApplicant.executeUpdate();
		stInsertApplicant.close();
	}
	
	public String retreiveData(DataPersistence db) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		String results = "The following skills have been found: ";
		
		//Get skills from applicant table
		PreparedStatement stSelectApplicant = conn.prepareStatement("SELECT * FROM APPLICANT WHERE email = ?");
		stSelectApplicant.setString(1, db.getEmail());
		
		ResultSet rs = stSelectApplicant.executeQuery();
		
		while (rs.next())
		{
		    results += rs.getString(2) + " ";
		}
		
		rs.close();
		stSelectApplicant.close();
		
		return results;
		
	}
	
	public void cleanUpDB(DataPersistence db) throws SQLException{
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
				
		//Clear applicant table and delete
		PreparedStatement stDeleteApplicant = conn.prepareStatement("DELETE FROM applicant WHERE email = ?");
		PreparedStatement stDropApplicant = conn.prepareStatement("DROP TABLE applicant");
				
		//Clear person table and delete
		PreparedStatement stDeletePerson = conn.prepareStatement("DELETE FROM person WHERE email = ?");
		PreparedStatement stDropPerson = conn.prepareStatement("DROP TABLE person");
		
		stDeleteApplicant.setString(1, db.getEmail());
		stDeletePerson.setString(1, db.getEmail());
		
		stDeleteApplicant.executeUpdate();
		stDropApplicant.executeUpdate();
		stDeletePerson.executeUpdate();
		stDropPerson.executeUpdate();
		
		stDeleteApplicant.close();
		stDropApplicant.close();
		stDeletePerson.close();
		stDropPerson.close();
	
	}

}
