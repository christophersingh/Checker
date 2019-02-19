import javax.swing.JFrame; 
import javax.swing.JTextArea; 
import javax.swing.JScrollPane; 
import java.awt.BorderLayout; 
import java.io.BufferedReader; 
import java.io.InputStreamReader; 

public class ExeOutputFrame extends JFrame { 
public ExeOutputFrame() { 
Runtime runtime; 
Process process; 
BufferedReader stdout; 
String output; 
JTextArea displayOutput; 
StringBuffer allOutput; 

setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

// Setup the JFrame 

// Use BorderLayout 
getContentPane().setLayout(new BorderLayout()); 

// Create the TextArea which will display the EXE's output 
displayOutput = new JTextArea(90, 60); 

// Place the TextArea on the JFrame 
getContentPane().add(new JScrollPane(displayOutput), BorderLayout.SOUTH); 

// Size the JFrame 
pack(); 

// Show the JFrame 
setVisible(true); 

// Now we will run the EXE and display the output 

// Need a place to house the collected output 
allOutput = new StringBuffer(); 

// Access the runtime environment so we can run the EXE 
runtime = Runtime.getRuntime(); 

// We can encounter checked exceptions, so we need a try block 
try { 
// Run the EXE, retain the reference to the process that is started 
process = runtime.exec("ifconfig"); 

// Get the standard output from the EXE 
stdout = new BufferedReader(new InputStreamReader(process. 
getInputStream())); 

// As long as we don't get a null value, keep reading from the stream 
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
} catch (Throwable throwable) { 
// Report the error 

} 
} 

/** 
* Run the program 
* 
* @param args String[] 
*/ 
public static void main(String[] args) { 
new ExeOutputFrame(); 
} 
} 