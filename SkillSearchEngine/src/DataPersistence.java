import java.sql.*;
import org.postgresql.Driver;

public class DataPersistence {
	
	//Refers to email, firstName, lastName columns in person(table below) and email, skill defined in applicant(table below)
	String userEmail;
	String[] userSkills;
	String firstName;
	String lastName;
	
	//Constructor, get, and set methods for best practice
	public DataPersistence(String userEmail, String[] userSkills, String firstName, String lastName) {
		this.userEmail = userEmail;
		this.userSkills = userSkills;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void setSkill(String[] userSkills) {
		this.userSkills = userSkills;
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
	
	public String[] getSkills() {
		return userSkills;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	//Check if applicant table (skills) is empty 
	public boolean checkDBEmpty() throws SQLException {
		
		//Return value
		boolean DBEmpty = false;
		
		//Connect
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
				
		//Looking for a zero value here
		PreparedStatement stCheckDB = conn.prepareStatement("SELECT COUNT(*) FROM applicant;");
		
		//Results of count
		ResultSet rs = stCheckDB.executeQuery();
		
		//If count is zero, table empty
		while (rs.next())
		{
		    if(rs.getString(1).equals("0"))
		    	DBEmpty = true;
		}
		
		//Close connections
		rs.close();
		stCheckDB.close();
		
		//Return determination if table is empty, false by default
		return DBEmpty;
	}
	
	//Create person (name) and applicant (skills) tables
	public void createDB(DataPersistence db) throws SQLException {
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
				
		//Create person table
		PreparedStatement stCreatePerson = conn.prepareStatement("CREATE TABLE IF NOT EXISTS person (email varchar(256) PRIMARY KEY, firstName varchar(32), lastName varchar(32))");
		
		//Insert values into person table
		PreparedStatement stInsertPerson = conn.prepareStatement("INSERT INTO person (email, firstName, lastName) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT * FROM person)");
				
		//Create applicant table
		PreparedStatement stCreateApplicant = conn.prepareStatement("CREATE TABLE IF NOT EXISTS applicant (email varchar(256) REFERENCES person, skill varchar(32))");
				
		//Set values
		stInsertPerson.setString(1, db.getEmail());
		stInsertPerson.setString(2, db.getFirstName());
		stInsertPerson.setString(3, db.getLastName());
				
		//Execute updates
		stCreatePerson.executeUpdate();
		stInsertPerson.executeUpdate();
		stCreateApplicant.executeUpdate();
		
		//Close connections
		stCreatePerson.close();
		stInsertPerson.close();
		stCreateApplicant.close();
	}
	
	//Insert all PDF data into applicant table (skills)
	public void insertAllData(DataPersistence db, String[] data) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		
		//Insert into applicant table
		PreparedStatement stInsertApplicant = conn.prepareStatement("INSERT INTO APPLICANT (EMAIL, SKILL) VALUES (?, ?)");
		int count =  0;
		
		for (String d : data) {
			
			//Add pair (email and skill) to batch
			stInsertApplicant.setString(1, db.getEmail());
			stInsertApplicant.setString(2, d);
		    
			//Execute batch
			stInsertApplicant.addBatch();
            
			count++;
			
            // execute every 500 rows or less
            if (count % 500 == 0 || count == data.length) {
            	stInsertApplicant.executeBatch();
            }
        
		}
		
		//Close connection
		stInsertApplicant.close();
		
	}
	
	public String retreiveData(DataPersistence db) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		String results = "The following skills have been found: ";
		
		//Get skills from applicant table
		PreparedStatement stSelectApplicant = conn.prepareStatement("SELECT * FROM APPLICANT WHERE email = ? AND skill = ?");
		
		for (String s : db.userSkills) {
			
			//Set values for email and skill (recursive for skill)
			stSelectApplicant.setString(1, db.getEmail());
			stSelectApplicant.setString(2, s);
			
			//Execute
			ResultSet rs = stSelectApplicant.executeQuery();
			
			//Add to result string
			while (rs.next())
			{
				results += rs.getString(2) + " ";
			}
			
			//Close connection
			rs.close();
			
		}
	
		//Close connection
		stSelectApplicant.close();
		
		//Return skills string
		return results;
		
	}
	
	//Not used in program but helpful to have for future use
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
		
		//Set email values
		stDeleteApplicant.setString(1, db.getEmail());
		stDeletePerson.setString(1, db.getEmail());
		
		//Execute 
		stDeleteApplicant.executeUpdate();
		stDropApplicant.executeUpdate();
		stDeletePerson.executeUpdate();
		stDropPerson.executeUpdate();
		
		//Close connections
		stDeleteApplicant.close();
		stDropApplicant.close();
		stDeletePerson.close();
		stDropPerson.close();
	}

}
