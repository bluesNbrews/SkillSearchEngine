import java.sql.*;
//import org.postgresql.Driver;

public class DataPersistence {
	
	//Refers to email and skills columns defined in the applicant table below
	String userEmail;
	String userSkill;
	
	//Constructors, get, and set methods for best practice
	public DataPersistence(String userEmail, String userSkill) {
		this.userEmail = userEmail;
		this.userSkill = userSkill;
	}
	
	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void setSkill(String userSkill) {
		this.userSkill = userSkill;
	}
	
	public String getEmail() {
		return userEmail;
	}
	
	public String getSkill() {
		return userSkill;
	}
	
	//Create the table manually in PostGreSQL
	/*
	 * CREATE TABLE person (
	 *      email varchar(256) PRIMARY KEY, fname varchar(32), lname varchar(32));
	 *
	 * INSERT INTO person (email, fname, lname) 
	 *      values('mstevenwilliams@gmail.com', 'Steven', 'Williams');
	 *
	 * CREATE TABLE applicant (
	 *      email varchar(256) REFERENCES person, skill varchar(32)); 
	 */
	
	public void insertData(DataPersistence db) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		
		//Insert email and skill parameters into database. Cleanup (close connections) afterwards.
		PreparedStatement st = conn.prepareStatement("INSERT INTO APPLICANT (EMAIL, SKILL) VALUES (?, ?)");
		
		st.setString(1, db.getEmail());
		st.setString(2, db.getSkill());
		
		st.executeUpdate();
		st.close();
	}
	
	public String retreiveData(DataPersistence db) throws SQLException {
		
		//Connect to local POSTGRESQL server
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		String results = "The following skills have been found: ";
		
		PreparedStatement st = conn.prepareStatement("SELECT * FROM APPLICANT WHERE email = ?");
		st.setString(1, db.getEmail());
		ResultSet rs = st.executeQuery();
		while (rs.next())
		{
		    results += rs.getString(2) + " ";
		}
		rs.close();
		st.close();
		
		return results;
	}
		
}
