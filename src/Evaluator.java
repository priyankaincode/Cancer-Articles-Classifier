import java.io.*;
import java.util.*;

//Priyanka Pathak
//Computation Methods - Spring 2011
//Class name: Evaluator.java
//Purpose: Question 3 on the homework. Take in a prediction file that has "<story_filename>\t<predicted_label>\t<goldstandard_label>\n"
//         in it, and compute the classification accuracy.

public class Evaluator {
	
	static String fileName;
	static BufferedReader reader;
	private static String articleName;
	private static String predicted_label;
	private static String gold_standard;
	private static boolean result;
	private static int correctCount;
	private static int incorrectCount;
	private static double accuracy;

	
	public static void main(String[] args){
		//Open program by asking user to input prediction file's name
		reader = new BufferedReader(new InputStreamReader(System.in)); 

		System.out.print("Please enter the prediction file's name: ");
		
		//Read each file name, store as a File
		try {
			fileName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File file = new File(fileName + ".txt");
		
		//Open file, store each token (separated by whitespace)
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				//Read line and store tokens
				readFile(scanner.nextLine());
				
				//Evaluate line in file
				result = evaluate(predicted_label, gold_standard);
				
				//Count number of accurate vs. inaccurate
				if(result == true){
					correctCount++;
				}else if(result == false){
					incorrectCount++;
				}
			}
			scanner.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		//Compute accuracy
		double total = correctCount + incorrectCount;
		
		accuracy = ((double) correctCount / total)*100;
		
		System.out.println("The accuracy of this file is " + accuracy + "%.");
		
	}
	
	private static void readFile(String line) {
		// Takes line from prediction file, separates tokens, and stores each token as a variable
		Scanner sc2 = new Scanner(line);
		
		articleName = sc2.next();
		predicted_label = sc2.next();
		gold_standard = sc2.next();
		
		sc2.close();

	}
	
	private static boolean evaluate(String pred, String gold){
		//Compare the predicted label to the gold standard label
		if(pred.equals(gold)){
			return true;
		}else{
			return false;
		}
	}
}
