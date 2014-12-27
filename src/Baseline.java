import java.io.*;
import java.util.*;

//Priyanka Pathak
//Computation Methods - Spring 2011
//Class name: Baseline.java
//Purpose: Question 4 on homework. Applies baseline standards to test set to determine baseline percentage (compare to classifier)

public class Baseline {
	
	static String story_filename;
	static String predicted_label;
	static String gold_standard;
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> allFiles = new ArrayList<String>();
	
		
		//Create output prediction file
		FileOutputStream fileOut = new FileOutputStream("prediction_file_baseline.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
		
		//For each line in "Testing.txt" store in the array (i.e. each file name gets stored in the arraylist)
		File testing = new File("Testing.txt");
		
        Scanner sc3 = new Scanner(testing);
		while(sc3.hasNextLine()){
			files.add(sc3.nextLine());
		}
		
		for(String s: files){
				//Store filename as "story_filename"
				story_filename = s.substring(0, s.indexOf("."));
	
				
				//Open file
				File file = new File("docs/" + s);
				
			    //Look at title to see if the keywords are present
				//Take first line, tokenize, loop through to see if matches any keywords
				Scanner sc = new Scanner(file);
				String line = sc.nextLine();
				
				Scanner sc2 = new Scanner(line);
				
				while(sc2.hasNext()){
					String str = sc2.next();
					
						if(str.equals("prevent") || str.equals("prevents") || str.equals("help") || str.equals("reduce") || str.equals("fight") || str.equals("against") || str.equals("decrease") || str.equals("decreases") || str.equals("risk")){
							//Store variable "predicted_label" accordingly
							predicted_label = "prevent";
						}else{
							predicted_label = "cause";
						}
					
				}
				sc2.close();
				
				//Read the file's class file, store the token inside as "gold_standard"
				File file2 = new File("docs");
				File[] files2 = file2.listFiles();
				
				for(File f: files2){
					String t = f.getName();
					
					if(t.startsWith(story_filename) && t.endsWith(".label")){
						Scanner sc4 = new Scanner(f);
						
						gold_standard = sc4.next();
						sc4.close();
					}
				}

				//Write output to prediction_file
				out.writeObject(story_filename + "\t" + predicted_label + "\t" + gold_standard + "\n");
			}
		
		//Close out prediction file
		out.close();
        fileOut.close();
        
        System.out.println("Baseline finished! Output file saved as 'prediction_file_baseline'.");
		}
	

	}
