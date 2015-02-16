package com.team.catswithhats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.Objects.Cat;
import com.team.catswithhats.Objects.Hat;

public class GameFile {

	//Last time quick heal was clicked
	private static long lastCall;
	// handle for the game file
	private static FileHandle handler;
	
	// sound
	private static boolean soundEnabled;
	
	// notifications
	private static boolean receiveNotifications;
	
	// created profile
	private static boolean createdProfile;
	
	// haberdasher name
	private static String haberdasherName;
	
	// player cat
	private static Cat haberdasherCat;
	/*
	// player hat list
	private static ArrayList<Hat> hatInventory;
	
	public static ArrayList<Hat> getInventory() {
		return hatInventory;
	}
	*/
	
	public static void quickHeal() {
		haberdasherCat.setCurrentHP(haberdasherCat.getBaseHP());
	}
	
	private static void deleteProfile() {
		
		createdProfile = false;
		
		// this allows the file to be rewritten according to the current settings
		handler.writeString("", false);
				
		// sound enabled
		if (soundEnabled)
			handler.writeString("true\n", true);
		else
			handler.writeString("false\n", true);
				
		// notifications enabled
		if (receiveNotifications)
			handler.writeString("true\n", true);
		else
			handler.writeString("false\n", true);
	}
	
	private static void writeGameFile() {
		
		// this allows the file to be rewritten according to the current settings
		handler.writeString("", false);
		
		// sound enabled
		if (soundEnabled)
			handler.writeString("true\n", true);
		else
			handler.writeString("false\n", true);
		
		// notifications enabled
		if (receiveNotifications)
			handler.writeString("true\n", true);
		else
			handler.writeString("false\n", true);
		
		if (createdProfile) {
			// player name
			handler.writeString(haberdasherName + "\n", true);
			
			// cat settings
			handler.writeString(haberdasherCat.getName() + "\n", true);
			handler.writeString(Integer.toString(haberdasherCat.getStrength()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getAgility()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getDefense()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getCurrentHP()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getBaseHP()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getLevel()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getXPCurrent()) + " ", true);
			handler.writeString(Integer.toString(haberdasherCat.getXPToNextLevel()) + "\n", true);
			
			// hat type##hat name
			handler.writeString(haberdasherCat.getHat().getType() + "##" + haberdasherCat.getHat().getName() + "\n", true);
			
			// list the hats
			int i,j;
			HatType[] types = HatType.values();
			for (i = 0; i < types.length; i++) {
				for (j = 0; j < HatDatabase.getNumHats(types[i]); j++) {
					Hat checkHat = HatDatabase.getHat(types[i], j);
					if (checkHat.getSeen())
						if (checkHat.getCaught())
							handler.writeString(checkHat.getType() + "##" + checkHat.getName() + "##true\n", true);
						else
							handler.writeString(checkHat.getType() + "##" + checkHat.getName() + "##false\n", true);
				}
			}
		}
	}
	
	private static void readFromGameFile() {
		// read the entire file into a string
		String gfContents = handler.readString();
		
		// break the file into new lines
		String[] gfArray = gfContents.split("\n");
		
		// sound enabled
		soundEnabled = Boolean.parseBoolean(gfArray[0]);
			
		// notifications
		receiveNotifications = Boolean.parseBoolean(gfArray[1]);
		
		if (gfArray.length > 2) {
			// created profile
			createdProfile = true;
			
			// player name
			haberdasherName = gfArray[2];
			
			// cat settings
			String catName = gfArray[3];
			
			// attributes
			String[] attr = gfArray[4].split(" ");
			int strength = Integer.parseInt(attr[0]);
			int agility = Integer.parseInt(attr[1]);
			int defense = Integer.parseInt(attr[2]);
			int currentHP = Integer.parseInt(attr[3]);
			int baseHP = Integer.parseInt(attr[4]);
			int level = Integer.parseInt(attr[5]);
			int currentXP = Integer.parseInt(attr[6]);
			int nextXP = Integer.parseInt(attr[7]);
			
			// hat details
			String[] hatDetails = gfArray[5].split("##");
			System.out.println("Loaded hat from " + hatDetails[0] + " named " + hatDetails[1]);
			Hat hat = HatDatabase.getHat(HatType.valueOf(hatDetails[0]), hatDetails[1]);
			
			// create the players cat
			haberdasherCat = new Cat(catName, strength, agility, defense, currentHP, baseHP, hat, level, currentXP, nextXP);
			
			// read the hats
			int i = 6;
			while (i < gfArray.length) {
				hatDetails = gfArray[i++].split("##");
				hat = HatDatabase.getHat(HatType.valueOf(hatDetails[0]), hatDetails[1]);
				boolean caught = Boolean.parseBoolean(hatDetails[2]);
				HatDatabase.markAsSeen(hat);
				if (caught) HatDatabase.markAsCaught(hat);
//				hat.setSeen(seen);
//				hat.update();
			}
		}
	}
	
	public static void init() {
		// open the game file
		handler = Gdx.files.local("gamefile.txt");
		lastCall = 0;
		
		// created profile
		createdProfile = false;
		
		// read the game file
		if (handler.exists()) {
			readFromGameFile();
		}
	}
	
	public static boolean createdProfile() {
		return createdProfile;
	}
	
	public static void createProfile(String playerName, Cat cat) {
		haberdasherName = playerName;
		haberdasherCat = cat;
		createdProfile = true;
		HatDatabase.markAsCaught(cat.getHat());
		HatDatabase.markAsSeen(cat.getHat());
		writeGameFile();
		System.out.println("Game Created");
	}
	
	public static boolean exists() {
		// check if the handler has been set
		if (handler == null)
			return false;
		
		// return the handler status
		return handler.exists();
	}
	
	public static void toggleSound() {
		soundEnabled = !soundEnabled;
		writeGameFile();
	}
	
	public static boolean soundEnabled() {
		return soundEnabled;
	}
	
	public static void toggleNotifications() {
		receiveNotifications = !receiveNotifications;
		writeGameFile();
	}
	
	public static boolean notificationsEnabled() {
		return receiveNotifications;
	}
	
	public static void saveCat(Cat haberdasherCat) {
		GameFile.haberdasherCat = haberdasherCat;
		writeGameFile();
	}
	
	public static void reset() {
		deleteProfile();
	}
	
	public static Cat getCat() {
		return haberdasherCat;
	}
	
	public static int checkHeal(long timeclicked){
		if(lastCall==0){
			lastCall = System.currentTimeMillis();
			return 0;
		}
		if(timeclicked-lastCall>600000){
		lastCall = System.currentTimeMillis();
		return 0;
		}
		
		return (int) ((600000-(timeclicked-lastCall))/1000);
	}
}
