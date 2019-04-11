import java.awt.EventQueue;
import java.io.IOException;

//Main class to start the program.
public class PDFDriver {

	public static void main(String[] args) throws IOException {
		
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

}
