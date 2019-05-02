import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFParser {
	
	String theResumePath;
	
	public PDFParser(String theResumePath){
		this.theResumePath = theResumePath;
	}
	
	public PDFParser() {
		theResumePath = null;
	}
	
	//Not used, but for best practice
	public void setResumePath(String theResumePath) {
		this.theResumePath = theResumePath;
	}
	
	//Not used, but for best practice
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
		DataPersistence database = new DataPersistence(userEmail,userSkills);
		
		//Insert database info into POSTGRESQL local server
		if (userSkills != null) {
			try {
				database.insertData(database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Close the loaded resume
		document.close();
		
		//Return search results
		try {
			results = database.retreiveData(database);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
}
