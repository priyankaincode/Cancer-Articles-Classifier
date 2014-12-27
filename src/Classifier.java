import java.io.*;
import java.util.*;

//Priyanka Pathak
//Computation Methods - Spring 2011
//Class name: Classifier.java
//Purpose: Trains a basic Naive Bayes Bernoulli model on training set. Tests on testing set, creates a prediction file, and outputs percentage accuracy (after running through the evaluator)

public class Classifier {

	static int docCount = 0;
	static int docCountPrevent = 0;
	static int docCountCause = 0;
	static double prior_c;
	static double prior_p;
	static String[] classes = {"cause", "prevent"};
	static String[] vocabulary = {"prevent", "prevents", "help", "reduce", "fight", "against", "decrease", "decreased", "risk", "increases", "increased", "likely", "cause", "caused"};
	static double[][] condprob = new double[vocabulary.length][classes.length];
	//static ArrayList<String> labels = new ArrayList<String>();
	static ArrayList<String> txts = new ArrayList<String>();
	static ArrayList<String> pred = new ArrayList<String>();
	static ArrayList<String> gs = new ArrayList<String>();
	static double score_c;
	static double score_p;
	
	
	public static void main(String[] args) throws IOException {
		//Train model on the training set
		train();
		System.gc();
		//Apply model to testing set
		test();
		System.gc();
		//Create prediction file
		evaluate();
	}
	
	public static void train() throws FileNotFoundException{
		//tokenize docs
		File file = new File("Training.txt");
		Scanner sc = new Scanner(file);

		//count number of training docs (= N) and add each training doc to arraylist txts
		while(sc.hasNextLine()){
			docCount++;
			txts.add(sc.nextLine());
		}
		sc.close();

		for(int i=0; i < classes.length; i++){
			//Create array of all files so that you can open gold standard labels
			File file2 = new File("docs");				
			File[] files2 = file2.listFiles();
			
			
			//Retrieve file names with gold standard labels
			//Check gold standard to see what type of class each doc is in; count number of each type
			for(String s: txts){
				String sub = s.substring(0, s.indexOf(".")) + ".label";
				
				for(File f: files2){
					if(f.getName().equals(s)){
						Scanner sc4 = new Scanner(new File("docs/" + sub));
						String t = sc4.next();
						sc4.close();
							
						if(t.equals("cause")){
							docCountCause++;
						}else{
							docCountPrevent++;
							}
						
					}
				}
			}

			//for each term in vocabulary, Nct = count number of docs in the class with that term and store condprob in matrix
				
			for(int k=0; k < vocabulary.length; k++){
				int countDoc = 0;
				
				//prior[c] = Nc/N
				if(i ==0){
					prior_c = docCountCause / docCount;
				}else{
					prior_p = docCountPrevent / docCount;
				}
				
				for(String r: txts){
					File temp = new File("docs/" + r);
					Scanner sc5 = new Scanner(temp);
					
					outer: while(sc5.hasNext()){
						if(sc5.next().equals(vocabulary[k])){						
							countDoc++;
							break outer;
						}
					}
					sc5.close();
				}
					
				//condprob[t][c] = (Nct + 1)/ (N+2)
				double num = (countDoc + 1);	
				double denomC = (docCountCause + 1);
				double denomP = (docCountPrevent + 2);
				
				if(i == 0){
					condprob[k][i] = num/denomC;
				}else{
					condprob[k][i] = num/denomP;
				}
					
			}
		}	
	}
		
	
	public static void test() throws FileNotFoundException{
		//Clear old arraylists from training set
		txts.clear();
		
		//Tokenize docs
		File file = new File("Testing.txt");
		Scanner sc = new Scanner(file);

		//Add each testing doc to arraylist txts
		while(sc.hasNextLine()){
			txts.add(sc.nextLine());
		}
		sc.close();
		
		for(String r: txts){
			File temp = new File("docs/" + r);
			Scanner sc5 = new Scanner(temp);
		
			//For each class (cause/prevent)
			for(int i=0; i < classes.length; i++){
			
			//score[c] = log prior [c]
			if(i == 0){
				score_c = (Math.log(prior_c)/Math.log(2));
			}else{
				score_p = (Math.log(prior_p)/Math.log(2));
			}
		
			//for each term in vocabulary
			for(int k=0; k < vocabulary.length; k++){
				outer: while(sc5.hasNext()){
					//if term is in document
						//score[c] += log condprob[t][c]
					//else
						//score[c] += log (1-condprob[t][c])
					if(sc5.next().equals(vocabulary[k])){
						if(i == 0){
							score_c += (Math.log(condprob[k][i])/Math.log(2));
							break outer;
						}else{
							score_p += (Math.log(condprob[k][i])/Math.log(2));
							break outer;
						}
					}
					else{
						if(i == 0){
							score_c += (Math.log(1-condprob[k][i])/Math.log(2));
							break outer;
						}else{
							score_p += (Math.log(1-condprob[k][i])/Math.log(2));
							break outer;
							}
						}
					}
				}
			
			}
		//each doc returns (prevent/cause) depending on higher score; results are stored in an arraylist
		if(score_c > score_p){
			pred.add("cause");
		}else if (score_c < score_p){
			pred.add("prevent");
		}else{
			pred.add("cause");
		}
		
		
		//Create array of all files so that you can open gold standard labels
		File file2 = new File("docs");				
		File[] files2 = file2.listFiles();
		
		//Fill labels array with gold standard labels for testing set
		for(String s: txts){
			String sub = s.substring(0, s.indexOf(".")) + ".label";
			
			for(File f: files2){
				if(f.getName().equals(s)){
					Scanner sc4 = new Scanner(new File("docs/" + sub));
					String t = sc4.next();
					sc4.close();
						
					if(t.equals("cause")){
						gs.add("cause");
					}else{
						gs.add("prevent");
					}
				}
			}
		}

		}
	}
	
	
	public static void evaluate() throws IOException{
		
		//Create output prediction file
		FileOutputStream fileOut = new FileOutputStream("prediction_file.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        
        //Print output in format needed for prediction_file. Txts is now filled with testing docs, pred with matching predictions, and labels with actual labels of testing set.
        for(int p=0; p < txts.size(); p++){
        	out.writeObject(txts.get(p) + "\t" + pred.get(p) + "\t" + gs.get(p) + "\n");
        }
        
        fileOut.close();
        out.close();
 
        //Print confirmation and instructions
        System.out.println("Prediction file created! Run 'prediction_file' through the Evaluator now.");
        
	}
}
