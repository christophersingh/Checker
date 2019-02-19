import java.util.*; 

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea ;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.io.*;

//RUNS ON GUI 

public class SendFiles extends JFrame{
	
	public SendFiles() {
		
		Runtime runtime;
		Process process;
		
		BufferedReader stdout;
		String output;
		
		JTextArea displayOutput;
		StringBuffer allOutput;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JFrame frame;
		frame = new JFrame("Snowball");
		
		getContentPane().setLayout(new BorderLayout());
		displayOutput = new JTextArea(90,90);
		getContentPane().add(new JScrollPane(displayOutput), BorderLayout.SOUTH);
		pack();
		
		setVisible(true);
		allOutput = new StringBuffer();
		runtime = Runtime.getRuntime();
		
		try {
			process = runtime.exec("rsync --progress --verbose --recursive --stats /Users/christophersingh/mnt/JSONVideo/275009 /Users/christophersingh/Snowball_Test_Code");
			stdout = new BufferedReader(new InputStreamReader(process. getInputStream())); 
			while ((output = stdout.readLine()) != null) { 
				// Append the collected input into the message buffer 
				allOutput.append(output); 
				// If we gont one or more characters then we need a newline 
				// since we are reading a line at a time 
				if (output.length() > 0) { 
				allOutput.append('\n'); 
				} 

				// Place the collected text into the TextArea 
				displayOutput.setText(allOutput.toString()); 

				// Set the caret in the TextArea to the end of the current text 
				// so that the most recent output is visible 
				displayOutput.setCaretPosition(allOutput.length()); 
				}
		}
		catch(Throwable throwable) {
			
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		new SendFiles();
		
	    String command = "aws s3 cp /Users/christophersingh/trans2.log s3://snowball-transfer";
		String command2 = "rsync --progress --verbose --recursive --stats /Users/christophersingh/mnt/JSONVideo/275009 /Users/christophersingh/Snowball_Test_Code";
		String command3 = "rsync --help";
		
		Scanner in = new Scanner(System.in);
		//System.out.println("Enter destination");
		//String destination = in.nextLine();
		
        Process proc = Runtime.getRuntime().exec(command2);
        
        //try entire directory 
        
        // Read the output
      
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
               
                  
        while((line = reader.readLine()) != null)
        {
        	
        	//printTextField(line);
        	
            System.out.println(line);
        }

        proc.waitFor(); 
        int x = 10;
        if(x > 20) System.out.println(command);
       

	}
	

}
