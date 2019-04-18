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
	
	public String parsePDF(String searchSkills) throws InvalidPasswordException, IOException {
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
		String theSearchSkills = searchSkills; 
		String[] skills = theSearchSkills.split(delimeterComma);	
		String results = "The following skills have been found: ";	
		String userEmail = "mstevenwilliams@gmail.com";
		String userSkill = null;
		
		DataPersistence database = new DataPersistence();
		
		//Iterate through space delimited string and search for keywords. If found, print them out. 
		for(int i = 0; i < resumeTokens.length; i++) {	
			
			for(int j = 0; j < skills.length; j++) {
				
				if(resumeTokens[i].contains(skills[j])) {
					userSkill = skills[j];
					System.out.println("Found the skill " + skills[j] + ".");
					results += skills[j] + " ";
					i = (resumeTokens.length + 1);
					j = (skills.length + 1);
				}
				
			}
			
		}
		
		try {
			database.insertData(userEmail, userSkill);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Close the loaded resume
		document.close();
		
		//Return search results
		return results;
	}
	
}
