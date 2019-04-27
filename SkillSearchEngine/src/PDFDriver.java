import java.awt.EventQueue;
import java.io.IOException;

//Main class to start the program.
public class PDFDriver implements Runnable{

	public static void main(String[] args) throws IOException {
		
		(new Thread(new PDFDriver())).start();
		
		//Launch the Swing GUI Application.
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					SwingApp window = new SwingApp();
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				
				}
			}
		});	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Starting new program.");
	}
}
