Description:

This classifier takes a set of articles about cancer from the mass media - divided into training and test sets. Some talk about ways to prevent cancer, and others talk about causes of cancer. This classifier uses the Naive Bayes Bernoulli algorithm to learn to identify which article is about which topic, "prevent" or "cause". 

Instructions:

I included the same files I generated and used to test the program (Testing.txt, Training.txt, and the two prediction files) so these can be used if you want. However, if you want to do it all yourself, you can begin by running the TrainingAndTesting program. Running SplitDocs.java takes all the docs and randomly removes 1/3 of them to put into a testing set, leaving the rest as a training set. Then, you can open the Baseline.java class and run that to create a prediction_file_baseline.txt file, which contains the baseline predictions. You can then evaluate that in Evaluator.java, which will prompt you for a file name (leave off the file type .txt, just type in "prediction_file_baseline"). Then you can run Classifier.java, which will then create a prediction_file.txt file, which you can run through Evaluator.java.

Each program can be run in an IDE (I used Eclipse) or console.

Class Descriptions:

These are included in the code, but just in case:

SplitDocs.java (inside TrainingAndTesting) - This program goes through an arraylist of all the docs and uses a random number generator to select random indices to remove from the array. This continues until 1/3 of the documents are moved to the testing set, and the remaining 2/3 are considered the training set.

Baseline.java - This program looks at the title of each article in the testing set. If the title contains one of the pre-selected "prevent" features (see the Word doc), then it is labeled as "prevent." If it does not, it is considered to be a "cause" document. The results are outputted into a prediction_file_baseline.txt file. 

Evaluator.java - This program compares the gold standard and predicted values inside a predicted_file and computes the percentage of matches. 

Classifier.java - This program uses the Naive Bayes Bernoulli approach to train the model on the training set of documents, and then apply it to the testing set. This program outputs a prediction_file, which can then be run in the Evaluator.
