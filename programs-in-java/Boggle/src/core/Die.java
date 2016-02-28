package core;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Alec
 */
public class Die {
    // Declare variable for sides of dice
    int NUMBER_OF_SIDES= 6;
    // Create ArrayList and string variable
    private final ArrayList <String> die= new ArrayList();
    private String letter;
    // Create method randomLetter
    public void randomLetter(){
      // Create an instance of class Random
      Random generateRandom= new Random();
        // Set letter variable to the number of the random letter
        int num = generateRandom.nextInt(this.NUMBER_OF_SIDES);
        letter=die.get(num);
        
    }
   // Create method getLetter 
   public String getLetter(){
       // Calls method randomLetter
        this.randomLetter();
        return letter;
    }
    // Create method addLetter 
    public void addLetter(String dieSide ){
        die.add(dieSide);
    
}
    // Create method displayAllLetters
    public void displayAllLetters(){
        //loop through all the sides of the die and display the data
        int lineBreak=0;
        for (String die1 : die) {
            System.out.print(die1 + " ");
            lineBreak++;
        }
    }
    }


    

    
