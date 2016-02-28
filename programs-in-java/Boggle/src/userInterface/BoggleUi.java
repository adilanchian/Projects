package userInterface;



import core.Die;
import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;
import core.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Random;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
/**
 *
 * @author alecdilanchian
 */

public class BoggleUi extends JFrame {

  //Menu Components
  private JMenuBar menuBar;
  private JMenu boggle;
  private JMenuItem newGame;
  private JMenuItem exit;
  
  // Declaring Gobal Variables
  private String newLetter;
  private String currentLetters = "";
  private String allWords = "";
  int score =0;
  int rows;
  int cols;
  int index = 0;
  int cpuScore = 0;
  private boolean[][]dieChecker;
  
  // ArrayLists for the dice buttons and also the dictionary data
  private final ArrayList diceButtons;
  private final ArrayList dictionaryData;
  // ArrayList checks to see if the word has been entered already
  ArrayList <String> wordCheck = new ArrayList<>();
  ArrayList <String> cpuWords = new ArrayList<>();
  ArrayList <String> shuffledWords = new ArrayList<>();
  
  // Panel for where the dice will go
  private JPanel bogglePanel;
  
  // Buttons on board
  private JButton submitWord;
  private JPanel shakeDicePanel;
  private JButton shakeDice;
  
  // 2D array to put dice on board
  private JButton[][] dieButton;
 
  // Other UI and variable declarations
  private static JPanel wordPanel;
  private static JPanel mainWordPanel;
  private static JPanel currentWord;
  private static JPanel scorePanel;
  private JTextPane userText;
  private JScrollPane scroll;
  private JLabel timeLabel;
  private Timer timeCounter;
  private JLabel wordCurrent;
  private JLabel playerScore;
  private final Board boggleBoard;
  private static int min=2;
  private static int sec=59;
  private final int i =0;
  int indexRand;
  // Set style for strikethrough
  Style primaryStyle;
  Style secondaryStyle;
  StyledDocument styleDocument;
  Font font;
  Font font1;
  
  // Constructor with class Board and dictionary data as parameters
  public BoggleUi(Board inBoard, ArrayList dictionaryData)
    {
        // Create instance diceButtons
        diceButtons = new ArrayList();
        // Set dictionary file to pass through
        this.dictionaryData = dictionaryData;
        // Set Board to pass through
        this.boggleBoard = inBoard;
        // Create interface
        initComponents();
    }
   
    private void initComponents(){
        // Set Interface
        this.setTitle("Boggle");
        this.setMinimumSize(new Dimension(650, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        // Call methods to add panels
        createMenu();
        setupBogglePanel();
        setupWordPanel();
        setupCurrentWordPanel();
        
        // Show components of board
        timeCounter = new Timer(1000, timeAction);
        this.add(bogglePanel, BorderLayout.WEST);
        this.add(wordPanel, BorderLayout.CENTER);
        this.add(mainWordPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
     
    private void createMenu() {
        // Create instances of menu items
        boggle = new JMenu("Boggle");
        menuBar = new JMenuBar();
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(new newGame());
        exit = new JMenuItem("Exit");
        
        // Add a new action listener for menu item exit
        exit.addActionListener(new exit());
        
        // Add components to create menu
        menuBar.add(boggle);
        boggle.add(newGame);
        boggle.add(exit);
        this.setJMenuBar(menuBar);
    }
    
    private void setupBogglePanel() {
        // Create JPanel to hold components
        bogglePanel = new JPanel(new GridLayout(4, 4));
        bogglePanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        bogglePanel.setMinimumSize(new Dimension(400, 400));
        bogglePanel.setPreferredSize(new Dimension(400, 400));
        
        // Implement array list to get random dice
        ArrayList<Die> dice = boggleBoard.shakeDice();
        
        // Create new instance of dieButton
        dieButton = new JButton [4][4];
        dieChecker = new boolean[4][4];
        
            // Start loop to create dice in a 4x4 grid
            for(int k=0; k<4;k++)
            {
                for(int j=0; j<4; j++)
                {
                    String dieLetter = dice.get(4*k+j).getLetter();
                    dieButton[k][j] = new JButton(dieLetter);
                    bogglePanel.add(dieButton[k][j]);
                    dieButton[k][j].addActionListener(new dieButton());
                    dieChecker[k][j]= false;
                }
            }    
        }
     
    private void setupWordPanel(){
        // Setup word panel to add to JFrame 
        wordPanel = new JPanel();
        wordPanel.setMinimumSize(new Dimension(400, 500));
        wordPanel.setPreferredSize(new Dimension(400, 500));
        wordPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.Y_AXIS));
        
        // Set style for strikethrough and font
        StyleContext sc = new StyleContext();
        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        userText = new JTextPane(doc);
        font = new Font("Times New Roman", Font.PLAIN, 15);
        userText.setFont(font);
        userText.setEditable(false);
        userText.setPreferredSize(new Dimension(400, 800));
        
        // Set style to document
        styleDocument = userText.getStyledDocument();
        primaryStyle = styleDocument.addStyle("Primary", null);
        secondaryStyle = styleDocument.addStyle("Secondary", primaryStyle);
        
        // Set font to stlye document
        StyleConstants.setFontFamily(primaryStyle, "Times New Roman");
        StyleConstants.setFontSize(primaryStyle, 15);
        StyleConstants.setStrikeThrough(secondaryStyle, true);
        scroll = new JScrollPane(userText);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        wordPanel.add(scroll);
        
        //Create new instances of timer panel and components
        JPanel timerPanel = new JPanel();
        timerPanel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        timeLabel = new JLabel ("3:00");
        Font font = new Font ("Times New Roman" , Font.PLAIN, 48);
        timeLabel.setFont(font);
        timerPanel.add(timeLabel);
        timerPanel.setAlignmentX(CENTER_ALIGNMENT);
        scroll.setAlignmentX(CENTER_ALIGNMENT);
        
        // Create new instance of shakeDice method
        shakeDicePanel = new JPanel();
        shakeDicePanel.setMinimumSize(new Dimension(250, 100));
        shakeDicePanel.setPreferredSize(new Dimension(250, 100));
        shakeDice = new JButton("Shake Dice");
        shakeDice.setPreferredSize(new Dimension(250,100));
        shakeDicePanel.add(shakeDice);
        shakeDicePanel.add(shakeDice, BorderLayout.SOUTH);
        
        // Add an action listener to shakeDice button
        shakeDice.addActionListener(new shakeDice());
        
        // Display window
        wordPanel.add(scroll);
        wordPanel.add(timerPanel);
        wordPanel.add(shakeDicePanel);
    }  
     
        
     private void setupCurrentWordPanel(){
         // Setup current word panel to add to JFrame
         mainWordPanel = new JPanel();
         mainWordPanel.setBorder(BorderFactory.createTitledBorder("Current Words"));
         mainWordPanel.setMinimumSize(new Dimension(150,100 ));
         mainWordPanel.setPreferredSize(new Dimension(150, 100));
         mainWordPanel.setLayout(new BoxLayout(mainWordPanel, BoxLayout.Y_AXIS));
         mainWordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         
         // Setup current word Panel to add inside mainWordPanel
         currentWord = new JPanel();
         currentWord.setMinimumSize(new Dimension(250, 50));
         currentWord.setPreferredSize(new Dimension(250, 50));
         currentWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
         currentWord.setLayout(new FlowLayout(FlowLayout.LEFT));
         
         // Setup score panel to add inside mainWordPanel
         scorePanel = new JPanel();
         scorePanel.setMinimumSize(new Dimension(100, 50));
         scorePanel.setPreferredSize(new Dimension(100, 50));
         scorePanel.setBorder(BorderFactory.createTitledBorder("Player Score"));
         
         // Setup JLabel for current word display text
         wordCurrent = new JLabel();
         submitWord = new JButton("Submit Word");
         submitWord.setPreferredSize(new Dimension(250, 50));
         
         // Add action listener to submit word button
         submitWord.addActionListener(new submitWord());
         playerScore = new JLabel("0");
         currentWord.add(wordCurrent);
         scorePanel.add(playerScore);
         mainWordPanel.add(currentWord);
         mainWordPanel.add(submitWord);
         mainWordPanel.add(scorePanel);
    } 
     
    private void randomWord(){
        // Start loop to add words to new array list
        if(wordCheck.size()>0){
            for(int j = 0; j<wordCheck.size(); j++){
                shuffledWords.add(wordCheck.get(j));
                 }
                 
        // Reset so cpu words and user words are shown for strike throughs
        userText.setText("");
        Random computerWord = new Random();
        
        // Get random number for the amount of words CPU found
        int randNum = computerWord.nextInt(wordCheck.size());
        Collections.shuffle(shuffledWords);
                 
        // Add found words to CPU array list
        for(int j = 0; j<randNum; j++){
            cpuWords.add(shuffledWords.get(j));
                     }
        
        // Print Out words found by cpu
        userText.setText(userText.getText()+"Your Opponent's Words:\n");
        for(int k=0; k<cpuWords.size(); k++){
            userText.setText(userText.getText()+(""+cpuWords.get(k)+"\n"));
                 }
        
        // Print out user words with or without strikethrough 
        userText.setText(userText.getText()+"Your Words:\n");
        for(int i = 0; i<wordCheck.size();i++){
            if(cpuWords.contains(wordCheck.get(i)))
            {
                try
                    {
                      styleDocument.insertString(styleDocument.getLength(),wordCheck.get(i)+ "\n", secondaryStyle);
                    }
                catch(BadLocationException ex)
                    {
                             
                    }
        // Subtract player points for matching words
        int wordLength = wordCheck.get(i).length();
        if (wordLength <= 4){
            score--;}
        else if(wordLength == 5){
            score-=2;}
        else if(wordLength == 6){
            score-=3;}
        else if(wordLength == 7){
            score-=5;}
        else if(wordLength >= 8){
            score-=11;}
        playerScore.setText(String.valueOf(score));
            }
        else{
                try{
                        styleDocument.insertString(styleDocument.getLength(), wordCheck.get(i)+"\n", primaryStyle);
                    }
                catch(BadLocationException ex)
                         {
                             
                         }
                    }
                }
            }
        }
    
    // Implement action listener for exit tab in the menubar   
    class exit implements ActionListener
    {
       @Override
       public void actionPerformed (ActionEvent e)
       {   // Show dialog asking if you are sure you want to quit
           int response = JOptionPane.showConfirmDialog(null, "Confirm to exit Boggle?", 
                    "Exit?", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION)
                System.exit(0);	
       }
    }
    // Implement action listener for new game tab in the menubar 
    class newGame implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {   // Stop timer if timer is started
            timeCounter.stop();
            
            // Reset score back to a value of 0
            shakeDice.setEnabled(true);
            score=0;
            playerScore.setText(String.valueOf(score));
            
            // Reset words found and current words
            allWords="";
            userText.setText("");
            wordCurrent.setText("");
            
            // Reset timer back to three minutes
            min= 3;
            sec=0;
            String reset = String.format(" "+min+":"+"%02d", sec);
            font1 = new Font("Times New Roman", Font.PLAIN, 48);
            timeLabel.setFont(font1);
            timeLabel.setText(reset);
            
            // Enablethe shakeDice button to be pressed again
            shakeDice.setEnabled(true);
            
            // Clear the array that held all the words to check to see if they are repeated
            wordCheck.clear();
        }
    }
    // Create action listener for submit word button 
    class submitWord implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {   // Set flag to catch if word is in dictionary file or not
            int flag = 0;
            
            // Start loop through ditionary file
            for(int i=0; i<dictionaryData.size();i++ ){
                
                // Ignore if the letters are uppercase or lowercase
                if(currentLetters.equalsIgnoreCase(dictionaryData.get(i).toString()))
                {
                    flag = 1;
                    // Checks to see if the word has been used already
                    int wordLength = currentLetters.length();
                    if(wordCheck.contains(currentLetters)){
                       userText.setText(userText.getText()+"Word has already been used!\n");
                       continue;
                   }
                    
                    // Display if two or less letters are enetered
                    if(wordLength <= 2){
                    userText.setText(userText.getText()+"Word is too short!\n");
                    }
                    
                    // Add to player score and add to wordCheck array
                    else if(wordLength <=4 )
                    {
                        score++;
                        playerScore.setText(String.valueOf(score));
                        userText.setText(userText.getText()+currentLetters+"\n");
                        wordCheck.add(currentLetters);
                        
                    }
                    
                    // Add to player score and add to wordCheck array
                    else if(wordLength ==5){
                        score+=2;
                        playerScore.setText(String.valueOf(score));
                        userText.setText(userText.getText()+currentLetters+"\n");
                        wordCheck.add(currentLetters);
                    }
                    
                    // Add to player score and add to wordCheck array
                    else if(wordLength == 6){
                        score+= 3;
                        playerScore.setText(String.valueOf(score));
                        userText.setText(userText.getText()+currentLetters+"\n");
                        wordCheck.add(currentLetters);
                    }
                    
                    // Add to player score and add to wordCheck array
                    else if(wordLength == 7){ 
                        score+=5;
                        playerScore.setText(String.valueOf(score));
                        userText.setText(userText.getText()+currentLetters+"\n");
                        wordCheck.add(currentLetters);
                    }
                    
                    // Add to player score and add to wordCheck array
                    else if(wordLength >= 8){
                        score+=11;
                        playerScore.setText(String.valueOf(score));
                        userText.setText(userText.getText()+currentLetters+"\n");
                        wordCheck.add(currentLetters);
                    
                    }
                    break;
                }
            }
            if (flag == 0){
                userText.setText(userText.getText()+"Word is not valid!\n");
            }
            // Reset current word text area
            wordCurrent.setText("");
            
            // Reset string for letters 
            currentLetters = "";
            
            // Allow all buttons to be pressed
            for (int i =0; i<4; i++)
            {
               for(int j=0; j<4; j++){
                   dieButton[i][j].setEnabled(true);
                   dieChecker[i][j]= false;
               }
           }
            
        }
    }
    // Create action listener for shakeDice Button  
    class shakeDice implements ActionListener
    {
       
        @Override
        public void actionPerformed(ActionEvent e)
        {  // Start the timer 
           timeCounter.start();
           
           // Call in shakedice method
           ArrayList<Die> dice = boggleBoard.shakeDice();
           
           // Remove all dice from board
           bogglePanel.removeAll();
           bogglePanel.revalidate();
           
           // Set player score back to 0
           playerScore.setText("0");
           
           // Clear current word label
           wordCurrent.setText("");
           currentLetters= "";
           userText.setText("");
           
           // Randomize location of the dice
           Collections.shuffle(dice);
           
           // Disable shakeDice button
           shakeDice.setEnabled(false);
           
           // Put dice into Boggle Panel
           dieButton = new JButton [4][4];
           dieChecker = new boolean[4][4];
           
           // Begin loop
           for(int i=0; i<4;i++)
                {
                for(int j=0; j<4; j++)
                    
                    {   // Allows us to know location of the dice
                        String dieLetter = dice.get(4*i+j).getLetter();
                        dieButton[i][j] = new JButton(dieLetter);
                        bogglePanel.add(dieButton[i][j]);
                        dieButton[i][j].addActionListener(new dieButton());
                        dieChecker[i][j]= false;
                    }
                }   
            }
        }
    
    // Create action listener for dieButton
    class dieButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
         // Create a new Jbutton
         JButton b;
         
         // Get the command from the button pressed and store in string
         newLetter = e.getActionCommand();
         
         // Get source of JButton
         b = (JButton)e.getSource();
         
         // Add button value just pressed to current word string
         currentLetters += newLetter;
         wordCurrent.setText(currentLetters);
         
         // Begin loop to enable only valid buttons
         for(int i =0; i<4; i++)
         {
             for(int j =0; j<4;j++)
             {
                 dieButton[i][j].setEnabled(false);
                 if(b == dieButton[i][j]){
                     rows=i;
                     cols=j;
                     dieChecker[i][j]=true;
                    }
                }
            }
         
         // Says which buttons can be enabled or not
         for(int i =rows -1; i<= rows+1; i++){
             for(int j = cols-1; j<= cols+1; j++){
                 if(i >=0 && i<4 && j>=0 && j<4)
                 {
                     dieButton[i][j].setEnabled(true);
                     if(dieChecker[i][j])
                         dieButton[i][j].setEnabled(false);
                    }
                }
            }
        }
    }
   
    // Create action listener for timer        
    ActionListener timeAction = new ActionListener()
     {
       @Override
       public void actionPerformed(ActionEvent e)
       {
        // Format string to look like a timer
        String number = String.format(" "+min+":"+"%02d", sec);
        
        // Set the timer in the timeLabel
        timeLabel.setText(number);
        
        // Countdown timer
        sec -= 1;
        
        // Stop timer when the second and minute both equal 0
        if(sec < 0)
        {
            sec=59;
                if(min == 0){
                timeCounter.stop();
                for(int i =0; i<4; i++)
                    {
                        for(int j =0; j<4;j++)
                            {
                              dieButton[i][j].setEnabled(false);

                        }
                    }
                randomWord();
                timeLabel.setFont(font);
                timeLabel.setText("Comparing Words...");
                submitWord.setEnabled(false);
                
                }
            min--; 
        }
        
    }
        
  };
     
}



   

   
   
   


  
        
    
               
         
            
            
   

   
    


     
     
     
     

    
     
     
     
     
     
     
     
     
     
 
 
    
    
    
    
    
    
    
            
    

