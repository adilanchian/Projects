/*Alec Dilanchian
 *CIS 3360
 *Assignment #1 - Hillcipher
 *02/28/16 
 */
import java.io.*;
import java.util.*;

public class hillcipher 
{
	// Create variables to read in text and key
	private static BufferedReader text;

	public static void main(String[] args)
	{
		// Formatting for output
		System.out.println();
		System.out.println();
		
		// List variables
		ArrayList<Character> charWordArray = new ArrayList<Character>(10000);
		String text = "";
		String key = "";
		int wordArrayLen;
		int argLen;
		int i;
		int keySizeVal = 0;
		
		//Get number of args and create array to hold strings
		argLen = args.length;
		String[] params = new String[argLen];
			
		//Store args in array
		for(i = 0; i < argLen; i++)
			params[i] = args[i];
		
		// Set each file name to the correct names
		key = params[0];
		text = params[1];
		
		// Get first int from key to find out size of matrix
		try
		{
			Scanner keySize = new Scanner(new File(key));
			keySizeVal = keySize.nextInt();
			keySize.close();
		}
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("There was an error opening your file.");
			e.printStackTrace();
		}
		
		// Formatting for output
		System.out.println("Key Matrix:");
		System.out.println();
		
		// Initialize 2D array and call readKey
		int[][] keyArray = new int[keySizeVal][keySizeVal];
		keyArray = readKey(key, keySizeVal);
		
		// Formatting for output
		System.out.println("Plaintext:");
		System.out.println();
		
		// Call readFile to read the text file and return array of all characters
		charWordArray = readFile(text);
		wordArrayLen = charWordArray.size();
				
		// Add padding if necessary
		charWordArray = addPadding(charWordArray, wordArrayLen, keySizeVal);
		int finalTextSize = charWordArray.size();
		
		//Cipher text
		cipher(charWordArray, finalTextSize, keyArray, keySizeVal);
	}
	
	// Start of helper functions
	public static ArrayList<Character> readFile(String fileName)
	{
		// List all variables
		String noChar = "";
		String words = "";
		String line;
		int i;
		int noCharsLen;
	
		// Create try and catch for file read I/O
		try 
		{	
			text = new BufferedReader(new FileReader(fileName));
		
			// Go through each line and set strings to text variable
			while((line = text.readLine()) != null)
				words += line;
		
			// Strip all chars other than the letters
			noChar = words.replaceAll("[^a-zA-Z]", "").toLowerCase();
		} 
	
		// If file does not exist or cannot be read		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("There was an error opening your file.");
			e.printStackTrace();
		}
		// Get length of string and create a char array 
		noCharsLen = noChar.length();
		ArrayList<Character> noCharArray = new ArrayList<Character>();
	
		for(i = 0; i < noCharsLen ; i++)
		{	
			noCharArray.add(noChar.charAt(i));
		}	
		// Return array to main
		return noCharArray;
	}
	
	public static int[][] readKey(String key, int arraySize)
	{
		// List variables
		int[][] keyArray2D = new int[arraySize][arraySize];
		int numSize = 0;
		int i = 0;
		int j = 0; 
		
		// Create try and catch for file read I/O
		try 
		{	
			// Scan in 
			Scanner nums = new Scanner(new File(key));
			
			// Skip first number because it is size of 2D array
			nums.nextInt();
			
			// Add numbers to 2D array until there are none left
			while(nums.hasNextInt())
			{
				numSize = nums.nextInt();
				keyArray2D[i][j] = numSize;
				
				// Formatting for output
				if(keyArray2D[i][j] <= 9)
					System.out.print(keyArray2D[i][j]+"  ");
				else
					System.out.print(keyArray2D[i][j]+" ");
				if(j == arraySize - 1)
				{
					i++;
					j = 0;
					System.out.println();
				}
				else
					j++;
			}
			nums.close();
		}
		
		// If file does not exist or cannot be read		
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("There was an error opening your file.");
			e.printStackTrace();
		}
		
		// Formatting for output
		System.out.println();
		System.out.println();
		// Return 2D array back to main
		return keyArray2D; 
	}
	// Function used to add padding if neccessary
	public static ArrayList<Character> addPadding(ArrayList<Character> text, int textLen, int keySize)
	{
		// List variables
		int mod = textLen % keySize;
		int padding = 0;
		int is80 = 0;
		
		while(mod != 0)
		{
			padding++;
			textLen ++;
			mod = textLen % keySize;
		}
		
		// Add padding
		for(int l = 0; l < padding; l++)
			text.add('x');
		
		for (int i = 0; i < text.size(); i++)
		{
			if(is80 == 80)
			{
				System.out.println();	
				is80 = 0;
			}
			else
			{
				System.out.print(text.get(i));
				is80++;
			}
		}
		
		// Formatting for output
		System.out.println();
		System.out.println();
		System.out.println();
		// Return arraylist with padding
		return text;
	}
	// Function used to encrypt text
	public static void cipher(ArrayList <Character> text, int textLen, int[][] key, int keySize)
	{		
		// List variables
		int textPosition = 0;
		int encryptVal = 0;
		int finalVal = 0;
		int is80 = 0;

		// Create arrays for calculations
		int[] charsToEncrypt = new int[keySize];
		
		// Create header for output
		System.out.println("Ciphertext:");
		System.out.println();
		
		// Loop through character size / key Size
		for(int x = 0; x < (textLen / keySize); x++)
		{
			// Set int value of chars into an array of size keySize
			for(int i = 0; i < keySize; i++)
			{
				charsToEncrypt[i] = text.get(textPosition) - 'a';
				textPosition++;
			}
			// Loop through the key to start algorithm
			for(int j = 0; j < keySize; j++)
			{
				for(int k = 0; k < keySize; k++)
				{
					// Begin matrix multiplication
					encryptVal = (key[j][k]*charsToEncrypt[k]);
					finalVal += encryptVal;
				}
			
				finalVal = finalVal % 26;
			
				// 80 character limit output formatter
				if(is80 == 80)
				{
					System.out.println();
					is80 = 0;
				}
				// Formatting for output
				System.out.print((char)(finalVal+'a'));
				is80++;
				
				// Reset variables for hillcipher algorithm
				encryptVal = 0;
				finalVal = 0;
			}
		}
		// Formatting for output
		System.out.println();		
	}
}
