import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class myPDF {

	public static void main(String[] args) throws IOException {
		//Get all text from specified PDF into one string
		File file = new File("/Users/stevenwilliams/eclipse-workspace/resume2.pdf");
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfstripper = new PDFTextStripper();	
		String resume = pdfstripper.getText(document);
		
		//Split string by spaces and create keywords to look for
		String delimeters = "[ ]+";
		String[] resumeTokens = resume.split(delimeters);
		String[] skills = {"Python", "SQL", "Java"};		
		
		//Iterate through space delimited string and search for keywords. If found, print them out. 
		for(int i = 0; i < resumeTokens.length; i++) {
			for(int j = 0; j < skills.length; j++) {
				if(resumeTokens[i].contains(skills[j])) {
					System.out.println("Found the skill " + skills[j] + ".");
				}
			}
		}
		//Close the loaded resume
		document.close();
	}

}
