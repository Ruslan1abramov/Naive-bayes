package file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FootballStats {
	//number of positions in our case
	public final static int NUMBER_OF_POSITIONS = 6;
	//number of attributes
	public final static int NUMBER_OF_ATTRIBUTES = 12;
	
	public final static int MAX_ATTRIBUTE = 3;
	
	public static int NUMBER_OF_PLAYERS;
	
	final static String[] POSITIONS = 
		{"attacking-player" , "wing-attacker" , "cm" , "dm" , "cb" ,"defender"};
	
	private FileHandler fh;
	
	private Map<String, ArrayList<int[]>> positionToAttributes = new HashMap<String, ArrayList<int[]>>();
	private Map<String, double[][]> positionToProbability = new HashMap<String, double[][]>();
	public FootballStats (String dataBasePath){
		initMap();
		fh = new FileHandler(dataBasePath);
		String data = fh.readFile();
		readData(data);
		calculateProbabilityMap();
	}
	
	private void initMap(){
		for(int counter = 0 ; counter < NUMBER_OF_POSITIONS ; counter++)
			this.positionToAttributes.put(POSITIONS[counter], new ArrayList<int[]>() );
	}

	
	private void readData(String data){
		Scanner dataScan = new Scanner(data);
		for(;dataScan.hasNextLine();){
			String scannedData = dataScan.nextLine();
			if(!scannedData.isEmpty())
				addToMap(scannedData);
			//each line represents a player
			NUMBER_OF_PLAYERS++;
		}
		dataScan.close();
	}
	/***Adding the new attributes to the map where the key is the attributes position***/
	private void addToMap(String data){
		
		Scanner dataScan = new Scanner(data);
		//the data is saved this way : "string + 12 doubles" in each row
		//the first string is the position name
		String position = dataScan.next();
		//the next 12 are the attributes
		int[] attributes = new int[NUMBER_OF_ATTRIBUTES];
		//reading the attributes
		for(int counter = 0;dataScan.hasNext();counter++)
			attributes[counter] = Integer.parseInt(dataScan.next());
		ArrayList<int[]> positionList = positionToAttributes.get(position);
		positionList = positionList == null ? new ArrayList<int[]>(): positionList ;
		positionList.add(attributes);
		//adding the attributes to the map
		positionToAttributes.put(position, positionList);
		dataScan.close();
	}

	private void  calculateProbabilityMap(){
		//for each position
		for(String position : positionToAttributes.keySet()){
			int counter = 0;
			double[][] positionProbability = new double[3][12];
			//for each player in this position
			for(int[] attributesInt : positionToAttributes.get(position)){
				for(int arrayCounter = 0 ; arrayCounter < attributesInt.length ; arrayCounter++ , counter++)
					positionProbability[attributesInt[arrayCounter] - 1][arrayCounter] += attributesInt[arrayCounter];
				}
			//normalizing the results
			for(int rowCounter = 0 ; rowCounter < positionProbability.length ; rowCounter++ )
				for(int colCounter = 0 ; colCounter< positionProbability[0].length ; colCounter++)
					positionProbability[rowCounter][colCounter] /= counter;

			positionToProbability.put(position, positionProbability);
		}
	}
	
	public Map<String, double[][]> getPositionToProbability(){
		return this.positionToProbability;
	}
	
	public void saveData(String data){
		fh.addData(data);
	}
}
