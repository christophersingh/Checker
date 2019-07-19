import java.io.*;     
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.security.*;
 
public class DirectoryScanner 
{
     static void RecursivePrint(File[] arr,int index,int level) throws FileNotFoundException 
     {
         if(index == arr.length || level == arr.length) {
             return;
         }
         
         for (int i = 0; i < level; i++) {
             System.out.print("\n");
         }
         
         if(arr[index].isFile() && arr[index].getName().endsWith("md5.txt")) {
     
        	 for (File file : arr) {
         	    if (file.isFile()) {
         	       if (file.getName().endsWith("md5.txt")) {
         	           File goodFile = (file);
         	           Scanner in = new Scanner(goodFile);
         	           
         	           while(in.hasNextLine()) {
         	        	   String MD5_Info = in.nextLine();
         	        	   String MD5 = MD5_Info.substring(0, 32);  //extract the md5 from the full line 
         	        	   String Name = MD5_Info.substring(33); //second part of extraction process 
         	        	   System.out.println(MD5_Info);
         	        	   
         	        	   String Source_Path = file.getAbsolutePath();
         	        	   System.out.println(Source_Path);
         	        	   
         	        	   double size = file.length();
         	        	   System.out.println(size);
         	        	   String timeStamp = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss").format(new Date());  //create a date time stamp for each file entered into db
           	               System.out.println(timeStamp);
           	               
           	               enterInfoInDB(MD5, Name, Source_Path , size, timeStamp); //call database method 
           	              
           	               //Main variables to db are MD5_Info, Source_Path and timeStamp
           	               //declare size variable outside of loop
           	        
         	           }
         	           in.close();    	        	  
         	           //System.out.println("Source path: "+file.getAbsolutePath());
         	           System.out.println(); //take out this print statement if null pointer exception occurs
         	           
         	       }
         	    }         	 
         	    //move source path to one line in the print statement
         	    //create 2 variables to send to the mysql database (MD5 and path) 
         	    //save the read input into a variable and send variable 
         	    
        	//System.out.println(arr[index]);
        	//System.out.println(arr[index].getName());
         }
        }
         
             
          
         else if(arr[index].isDirectory())
         {
             //System.out.println("[" + arr[index].getName() + "]");
              
            RecursivePrint(arr[index].listFiles(), 0, level );
         }  
     
         //
         RecursivePrint(arr,++index, level);
         //
    }
     
     static void deleteDuplicateRecords() 
     {
    	 System.out.println("There is hope");
     }
     
     static void enterInfoInDB (String MD5, String Name, String Source_Path, double size, String timeStamp) 
     {
    	 
    	 try {
    		 Connection myConnector = DriverManager.getConnection("jdbc:mysql://localhost:3306/FileDirectory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", "XXXXX", "XXXXX");
    		 Statement go = myConnector.createStatement();
    		 ResultSet myRs = go.executeQuery("select * from MD5_Info");
    		 
    		 PreparedStatement statement = myConnector.prepareStatement("Insert into MD5_Info values(?,?,?,?,?)");//insert statement to mysql database
    		 
    		 statement.setString(1, MD5);
    		 statement.setString(2, Name);
    		 statement.setString(3, Source_Path);
    		 statement.setDouble(4, size);
    		 statement.setString(5, timeStamp);
    		 
    		 statement.executeUpdate();
    		
    		 deleteDuplicateRecords();
    	 }
    	 
    	 catch (Exception e) {
    		boolean now = true;
    		if(now) {
    			//System.out.println(e.printStackTrace());
    		}
    	 }
    	 
     }
  
    public static void main(String[] args) throws IOException, InterruptedException
    {
        // Provide full path for directory(change accordingly)  
        String sourcePath = "xxxxxxxxxxxxxxx";
                 
        // File object
        File directory = new File(sourcePath);
        
          
        if(directory.exists() && directory.isDirectory())
        {
        	
        	File arr[] = directory.listFiles();
        	
             
           //System.out.println("Files from main gdirectory : " + directory);
           // System.out.println();
      
             
            RecursivePrint(arr,0,0); 
            SendFiles.main(null);
       } 
        
    }
    
    public void printInfo() {
    	return;
    }
    
}

