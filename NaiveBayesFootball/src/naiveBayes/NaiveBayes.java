package naiveBayes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import file.FootballStats;

public class NaiveBayes {
	FootballStats fs;
	Map<String, double[][]> positionToProbability;
	int[] inputData = new int[FootballStats.NUMBER_OF_ATTRIBUTES];
	Map<String, Double> bayes = new HashMap<String, Double>();

	public NaiveBayes(String filePath, String dataToCheck) {
		fs = new FootballStats(filePath);
		positionToProbability = fs.getPositionToProbability();
		getInputData(dataToCheck);
		calculateBayes();
	}

	private void getInputData(String dataToCheck) {
		Scanner inData = new Scanner(dataToCheck);
		for (int counter = 0; counter < inputData.length; counter++)
			inputData[counter] = Integer.parseInt(inData.next());
		inData.close();
	}

	private void calculateBayes() {
		for (String position : positionToProbability.keySet()) {
			double bayesPosition = 1;
			double[][] positionProbability = positionToProbability.get(position);
			for (int counter = 0; counter < inputData.length; counter++)
				bayesPosition *= positionProbability[inputData[counter]-1][counter];
			//System.out.println(position + ": " + bayesPosition);
			bayes.put(position, bayesPosition);
		}
	}

	public String getPosition() {
		Double maxValue = Collections.max(bayes.values());
		String returnString = "";
		for (String position : bayes.keySet())
			if (bayes.get(position) == maxValue)
				returnString = position;
		return returnString;
	}
	
	public void saveData(String data){
		fs.saveData(data);
	}

}
