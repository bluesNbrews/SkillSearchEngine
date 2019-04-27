import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
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
	
	public String parsePDF(String searchSkillsParam) throws InvalidPasswordException, IOException{
		
		//Get all text from specified PDF into one string
		File file = new File(theResumePath);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfstripper = new PDFTextStripper();	
		String resume = pdfstripper.getText(document);
		
		//Split resume string by spaces and create keywords to look for
		//Split user input by commas and create keywords to look for
		//Create string of results to return
		String delimeterSpace = "[ ]+";
		String delimeterComma = "[,]+";
		String[] resumeTokens = resume.split(delimeterSpace);
		String searchSkills = searchSkillsParam; 
		String[] skills = searchSkills.split(delimeterComma);	
		String results = null;	
		String userEmail = "mstevenwilliams@gmail.com";
		String userSkills = null;
		
		//Iterate through space delimited string and search for keywords. If found, print them out. 
		for(int i = 0; i < resumeTokens.length; i++) {	
			
			for(int j = 0; j < skills.length; j++) {
				
				if(resumeTokens[i].contains(skills[j])) {
					//userSkills += skills[j] + " ";
					System.out.println("Found the skill " + skills[j] + ".");
					//results += skills[j] + " ";
					userSkills = skills[j];
					i = resumeTokens.length;
					j = skills.length;
					
				}
				
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
