package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alec
 */
public class Board {
    
    // Declare variable names and values
    private final int NUMBER_OF_DICE = 16;
    private final int NUMBER_OF_SIDES = 6;
    private final int GRID = 4; 
    
    // Create fileData and die ArrayList
    private ArrayList fileData = new ArrayList();
    private final ArrayList<Die> die  = new ArrayList();
    // Store the fileData into the boggleData ArrayList
    public Board(ArrayList boggleData) {
            fileData = boggleData;
    }
   
    // Create method populateDice and new instance of the Die class
     public void populateDice(){
        Die createDie = new Die();
        // Loop through the fileData ArrayList to create sides of dice
        for(int i=0; i<fileData.size(); i++){
            if(i%NUMBER_OF_SIDES == 0 && i != 0){
        	die.add(createDie);
          	createDie = new Die();
            }
            // Add letters to the dice
            createDie.addLetter(fileData.get(i).toString());
            if(i+1 == fileData.size())
                die.add(createDie);
        }
    }
    // Create method randomLetter
     public void randomLetter(){
    // Create new instance of class Random     
        Random gen = new Random ();
    // Print out the letter generated
        System.out.print(gen);}
    // Create method displayAllDice 
   public void displayAllDice() {
    //Loop through each die to print out the die number and letters on the dice   
    for(int i=0; i<die.size(); i++) {
      System.out.print("Die " + i + ": ");
      die.get(i).displayAllLetters();
      System.out.println();
    }
    
  }
    
    // Create method shakeDice
    public ArrayList shakeDice(){
    // Print out the header for the Boggle Board    
        System.out.println("Boogle Board");
        // Intiate  counter and call populateDice method
        int counter=0;
        populateDice();
        // Create a variable to store letters from Die class 
        String randLetter;
        // Loop through the number of dice 
        for(int j=0; j<NUMBER_OF_DICE; j++){
            // Set variable equal to the random letter generated
            randLetter=die.get(j).getLetter();
            // Print out the letter 
            System.out.print(randLetter+" ");
            counter++;
            // This allowes to print out the letters in a 4x4 grid
        if(counter==GRID){
                counter=0;
                System.out.println();
            }
            
            
                }
       
            
        return die;
    }
}