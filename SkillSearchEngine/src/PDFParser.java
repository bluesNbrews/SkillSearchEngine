import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFParser {
	
	String theResumePath;
	
	public PDFParser(String rp){
		this.theResumePath = rp;
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
		
		//Iterate through space delimited string and search for keywords. If found, print them out. 
		for(int i = 0; i < resumeTokens.length; i++) {
			for(int j = 0; j < skills.length; j++) {
				if(resumeTokens[i].contains(skills[j])) {
					//System.out.println("Found the skill " + skills[j] + ".");
					results += skills[j] + " ";
				}
			}
		}
		
		//Close the loaded resume
		document.close();
		
		//Return search results
		return results;
	
	}
}
