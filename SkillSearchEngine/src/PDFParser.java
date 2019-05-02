import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFParser {
	
	//Resume file path as defined by the user
	String theResumePath;
	
	//Constructor, get, and set methods for best practice
	public PDFParser(String theResumePath){
		this.theResumePath = theResumePath;
	}
	
	public PDFParser() {
		theResumePath = null;
	}
	
	public void setResumePath(String theResumePath) {
		this.theResumePath = theResumePath;
	}
	
	public String getResumePath() {
		return theResumePath;
	}
	
	public String parsePDF(String searchSkillsParam) throws IOException{
		
		//Get all text from specified PDF into one string
		File file = new File(theResumePath);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfstripper = new PDFTextStripper();	
		String resume = pdfstripper.getText(document);
		
		//Split resume string by delimiters below
		//Create regex pattern to find email address
		String[] resumeTokens = resume.split(" |,|;|:|-|&|•|\r|\n|\t|\f|\b");
		Pattern patternEmail = Pattern.compile("^(.+)@(.+)$");
		
		String results = null;	
		String userEmail = null;
		String userSkills = null;
		
		//Manually set first and last name. Assuming they are the first and third words in the resume, respectively.
		String firstName = resumeTokens[0];
		String lastName = resumeTokens[2];
		
		//Find email and skill
		for(String s : resumeTokens) {
			Matcher matcherEmail = patternEmail.matcher(s);
			if(matcherEmail.matches()) {
				userEmail = s;
			}
			if(s.equals(searchSkillsParam)) {
				userSkills = s;
				System.out.println("Found skill: " + userSkills);
			}
		}
		
		//Create database object initialized with email and skill
		DataPersistence database = new DataPersistence(userEmail,userSkills,firstName,lastName);
		
		//Create POSTGRESQL tables
		try {
			database.createDB(database);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Insert into said tables
		if (userSkills != null) {
			try {
				database.insertData(database);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		//Close the loaded resume
		document.close();
		
		//Return search results
		try {
			results = database.retreiveData(database);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		//Delete data and tables
		try {
			database.cleanUpDB(database);
		} catch (SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		
		//Return skills to GUI
		return results;
		
	}
}
