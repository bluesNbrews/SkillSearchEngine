import java.sql.*;
import org.postgresql.Driver;

public class DataPersistence {
	
	String userEmail;
	String userSkill;
	
	public DataPersistence(String userEmail, String userSkill) {
		this.userEmail = userEmail;
		this.userSkill = userSkill;
	}
	
	public DataPersistence() {
		userEmail = null;
		userSkill = null;
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
	
	public void insertData(String uE, String uS) throws SQLException {
		
		//
		String url = "jdbc:postgresql://localhost/?user=stevenwilliams&password=password123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		
		//
		PreparedStatement st = conn.prepareStatement("INSERT INTO APPLICANT (EMAIL, SKILL) VALUES (?, ?)");
		st.setString(1, uE);
		st.setString(2, uS);
		st.executeUpdate();
		st.close();
	}
		
}
