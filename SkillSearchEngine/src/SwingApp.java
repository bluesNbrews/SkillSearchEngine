import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;

public class SwingApp {

	//Global Variables for the GUI Application
	JFrame frame;
	private JTextField txtPdfFile;
	private JTextField txtEnterSkillsHere;
	private JTextField txtResultsDisplayHere;
	
	//File location of resume as specified by the user
	private String resumePath = null;
	
	//User input for strings to search for
	private String searchSkills = null;
	
	//Results of search
	private String searchResults = null;
	
	//Global object to perform search logic
	PDFParser myPDF = new PDFParser(resumePath);

	/*
	 * Create the application.
	 */
	
	public SwingApp() {
		
		initialize();
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//See various text in blue for objects of GUI application
		//Ex: Top "greeting banner" label is lblNewLabel
		JLabel lblNewLabel = new JLabel("Welcome to the Simple Search Engine");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 438, 53);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblGetPdf = new JLabel("1) Select PDF File Location  ------------->");
		lblGetPdf.setBounds(16, 52, 287, 30);
		frame.getContentPane().add(lblGetPdf);
		
		JLabel lblEnterSkills = new JLabel("2) Provide Skills To Find ------>");
		lblEnterSkills.setBounds(16, 127, 213, 30);
		frame.getContentPane().add(lblEnterSkills);
		
		txtPdfFile = new JTextField();
		txtPdfFile.setText("File Path Displays Here");
		txtPdfFile.setBounds(26, 85, 391, 30);
		frame.getContentPane().add(txtPdfFile);
		txtPdfFile.setColumns(10);
		
		//Implement JChooser for file location selection. Gives options of "All Files" and "PDF Files".
		//Need to remove the "All Files" choice. In progress...
		JButton btnSelect = new JButton("Select");
		
		btnSelect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
			    chooser.setFileFilter(filter);
			    Component parent = null;
				int returnVal = chooser.showOpenDialog(parent);
			    
				if(returnVal == JFileChooser.APPROVE_OPTION) {
			       
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			    
				}
			    
				//Change the default text of "File Path Displays Here" to the actual file location
				//Set resume path variable and create object with resume path variable
				//Set global object to also be used later
			    txtPdfFile.setText(chooser.getSelectedFile().getPath());
			    resumePath = chooser.getSelectedFile().getPath();
			    PDFParser myTempPDF = new PDFParser(resumePath);
			    myPDF = myTempPDF;
			
			}
		});
		
		btnSelect.setBounds(300, 54, 117, 29);
		frame.getContentPane().add(btnSelect);
		
		JLabel lblClickSearch = new JLabel("3) Click Search ---------------------->");
		lblClickSearch.setBounds(16, 185, 287, 16);
		frame.getContentPane().add(lblClickSearch);
		
		txtEnterSkillsHere = new JTextField();
		txtEnterSkillsHere.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				txtEnterSkillsHere.setText("");
			
			}
		});
		
		txtEnterSkillsHere.setText("Ex: Python,SQL,Java");
		txtEnterSkillsHere.setBounds(232, 129, 185, 26);
		frame.getContentPane().add(txtEnterSkillsHere);
		txtEnterSkillsHere.setColumns(10);
		
		txtResultsDisplayHere = new JTextField();
		txtResultsDisplayHere.setText("Results Display Here");
		txtResultsDisplayHere.setBounds(26, 213, 397, 47);
		frame.getContentPane().add(txtResultsDisplayHere);
		txtResultsDisplayHere.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				searchSkills = txtEnterSkillsHere.getText();
				
				//Go ahead and call the search logic and pass through the user input for search Strings.
				try {
					
					searchResults = myPDF.parsePDF(searchSkills);
					txtResultsDisplayHere.setText(searchResults);
				
				} catch (IOException e1) {
					
					// TODO Auto-generated catch block
					e1.printStackTrace();		
				} 
			}
		});
		
		btnNewButton.setBounds(300, 180, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
}