package demoRun;

import java.util.Scanner;

import naiveBayes.NaiveBayes;

public class DemoRun {
	
	public static Scanner userIn = new Scanner(System.in);
	public static final String  DATA_PATH = "FootballerData.txt";
	public final static String[] ATTRIBUTES = 
		{"finishing",	"shot to goal",	"creativity",	"dribbling",	"passing",	"marking",
				"heading",	"tackling",	"crossing",	"aggression",	"positioning",	"flair"};
	public final static String[] OUT_MSG = 
		{ "Please enter a value between 1 to 3 for the players attribute - ",
		"Where 1 is Bad , 2 Is O.K and 3 is Great", "\nThe player should play in the " , " Position"};
	
	public static void main(String[] args) {
		String userIn = getUserData();
		NaiveBayes nb = new NaiveBayes(DATA_PATH , userIn);
		System.out.println(OUT_MSG[2] + nb.getPosition()+ OUT_MSG[3]);
		addDataToDataBase(nb ,  nb.getPosition() +" " + userIn);
		pressToExit();
	}
	
	public static String getUserData(){
		String userData = "";
		for(int counter = 0 ; counter < ATTRIBUTES.length ; counter++){
			System.out.println(OUT_MSG[0] + ATTRIBUTES[counter]+ "\n" + OUT_MSG[1] + ": ");
			userData+= Integer.parseInt(userIn.nextLine()) +" ";
		}
		return userData;
	}
	
	public static void addDataToDataBase(NaiveBayes nb ,String dataToSave){
		nb.saveData(dataToSave);
	}
	
	public static void pressToExit(){
		
		final String MSG = "\nPress Enter to Exit";
		System.out.println(MSG);
		userIn.nextLine();
	}

}
