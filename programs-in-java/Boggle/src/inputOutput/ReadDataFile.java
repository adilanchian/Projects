package inputOutput;

import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;





/**
 *
 * @author Alec
 */
public class ReadDataFile{
    // Create all variables 
    private Scanner readFile; 
    private String storeFile;
    // Create new intance of ArrayList
    private ArrayList storeFileData = new ArrayList();
    // Enter parameters for ReadDataFile class
    public ReadDataFile(String iFileName ){
        // Stores file name to read in
        this.storeFile= iFileName;
        }
    
    public void populateData(){
        // Create try statement to scan in file
        try
        {
            // Get file information
            URL url= getClass().getResource(storeFile);
                // Create new instance File URL Class
                File file= new File(url.toURI());
                // Create instance of Scanner Class
                Scanner scanFile= new Scanner(file);
                while(scanFile.hasNext()){
                    storeFileData.add(scanFile.next());}
        }
        // Catch statement will catch any exceptions
        catch(Exception ex){
            System.out.println(ex.toString());
            ex.printStackTrace();}
        }
            
    // Create method getData        
    public ArrayList getData(){
        return storeFileData;
    }
       
        
    
    
    
    
}
