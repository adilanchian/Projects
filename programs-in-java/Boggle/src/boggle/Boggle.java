
package boggle;

import java.util.ArrayList;
import inputOutput.ReadDataFile;
import core.Board;
import userInterface.BoggleUi;

/**
 *
 * @author alec dilanchian
 */
public class Boggle {
     // Declare all variables and ArrayList
    private static ArrayList fileData = new ArrayList();
    private static ArrayList dictionaryData = new ArrayList();
    private static String fileName= "BoggleData.txt";
    private static String dictionary="TemporaryDictionary.txt";
    
           
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // Todo List
        // Create new instance of ReadDataFile class
        ReadDataFile readData= new ReadDataFile(fileName);
        ReadDataFile readDictionary = new ReadDataFile(dictionary);
        // Call method populateData from class ReadDataFile
        readData.populateData();
        readDictionary.populateData();
        // Set fileData equal to the readData information
        fileData= readData.getData();
        dictionaryData = readDictionary.getData();
        // Create new instance of boggleBoard with the fileData
        Board boggleBoard= new Board(fileData);
        // Call method populateDice on Boggle Board
        boggleBoard.populateDice();
        // Call methoad displayAllDice to show all dice
        boggleBoard.displayAllDice();
        // Call method shakeDice to randomize dice in 4x4 Grid
        boggleBoard.shakeDice();
        // Call in UI to pass in the boggle board and dictionary 
        BoggleUi boggleUI = new BoggleUi(boggleBoard, dictionaryData);
    }
}
       

