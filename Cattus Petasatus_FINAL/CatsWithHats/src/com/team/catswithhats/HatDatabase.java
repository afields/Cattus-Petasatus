package com.team.catswithhats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.team.catswithhats.Objects.Attack;
import com.team.catswithhats.Objects.Hat;

public class HatDatabase {

	private static HashMap<HatType, HashMap<String, Hat>> db;

	public static void init() {
		db = new HashMap<HatType, HashMap<String, Hat>>();
		buildDatabase();
	}

	public enum HatType {
	    WATER("WATER"),
	    FIRE("FIRE"),
	    EARTH("EARTH"),
	    GRASS("GRASS"),
	    ELECTRIC("ELECTRIC"),
	    DEFAULT("DEFAULT");

		private final String text;

		private HatType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	private static void buildDatabase() {
		FileHandle handler = Gdx.files.internal("hatdb/hats.txt");

		// initialize a hash map for each type
		HashMap<String, Hat> waterHats = new HashMap<String, Hat>();
		HashMap<String, Hat> fireHats = new HashMap<String, Hat>();
		HashMap<String, Hat> earthHats = new HashMap<String, Hat>();
		HashMap<String, Hat> grassHats = new HashMap<String, Hat>();
		HashMap<String, Hat> electricHats = new HashMap<String, Hat>();
		HashMap<String, Hat> defHats = new HashMap<String, Hat>();

		if (handler.exists()) {
			String contents = handler.readString();
			String[] lines = contents.split("\n");
			int i = 0;
			while (i < lines.length)
			{
				// hat name
				String name = lines[i++];

				// hat type
				HatType type = HatType.valueOf(lines[i++]);

				// hat stats
				String[] stats = lines[i++].split(" ");

				// hat texture
				String texture = lines[i++];

				// cat texture
				String catTexture = lines[i++];

				// hat description
				String desc = lines[i++];

				// hat location
				String location = lines[i++];

				// hat attacks
				String[] attr = lines[i++].split(" ");
				ArrayList<Attack> attacks = new ArrayList<Attack>();
				int j;
				for (j = 0; j < 4; j++) {
					attacks.add(new Attack(Integer.parseInt(attr[j]), lines[i++]));
				}

				// skip the new line
				i++;

				// create the hat
				Hat hat = new Hat(
					name, 
					Integer.parseInt(stats[0]), 
					Integer.parseInt(stats[1]), 
					Integer.parseInt(stats[2]), 
					new Texture(texture), 
					new Texture(catTexture),
					attacks
				);
				// set details
				hat.setDescription(desc);
				hat.setFound(location);
				hat.setType(type);

				switch (type) {

				case WATER:
					waterHats.put(name, hat);
					break;

				case FIRE:
					fireHats.put(name, hat);
					break;

				case EARTH:
					earthHats.put(name, hat);
					break;

				case GRASS:
					grassHats.put(name, hat);
					break;

				case ELECTRIC:
					electricHats.put(name, hat);
					break;

				case DEFAULT:
					defHats.put(name, hat);
					break;
				}
			}
		}

		// populate the database
		db.put(HatType.WATER, waterHats);
		db.put(HatType.FIRE, fireHats);
		db.put(HatType.EARTH, earthHats);
		db.put(HatType.GRASS, grassHats);
		db.put(HatType.ELECTRIC, electricHats);
		db.put(HatType.DEFAULT, defHats);
	}

	/*
	 * public static Set<HatType> getKeySet() { return db.keySet(); }
	 */

	public static int getNumHats(HatType location) {
		return db.get(location).size();
	}

	public static Hat getRandomHat(HatType location) {
		Hat[] hatList = Arrays.copyOf(db.get(location).values().toArray(), db.get(location).values().size(), Hat[].class);
		int ndx = (int) (Math.random() * 100) % hatList.length;
		return hatList[ndx];
	}

	public static Hat getHat(HatType location, int ndx) {
		// Hat[] hatArray = (Hat[] ) db.get(location).values().toArray();
		// dbKeys = Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		Hat[] hatArray = Arrays.copyOf(db.get(location).values().toArray(), db.get(location).values().size(), Hat[].class);
		return hatArray[ndx];
	}

	public static Hat getHat(HatType location, String name) {
		return (Hat) db.get(location).get(name);
	}

	/*public static void update(Hat hat) {
		// remove the old hat
		db.get(hat.getType()).remove(hat.getName());

		// add this new hat
		db.get(hat.getType()).put(hat.getName(), hat);
	}*/

	public static void markAsSeen(Hat hat)
	{
		db.get(hat.getType()).get(hat.getName()).setSeen(true);
	}
	
	public static void markAsCaught(Hat hat)
	{
		db.get(hat.getType()).get(hat.getName()).setCaught(true);
	}

	private static ArrayList<Hat> getHatList() {
		int count = 0;
		ArrayList<Hat> hatsList = new ArrayList<Hat>();
		Iterator<HatType> keys = db.keySet().iterator();
		while (keys.hasNext()) {
			HashMap<String, Hat> hats = db.get(keys.next());
			Iterator<Hat> hatsK = hats.values().iterator();
			while (hatsK.hasNext()) {
				hatsList.add(hatsK.next());
				System.out.println("hat: " + hatsList.get(count).getName());
				count++;
			}
		}
		return hatsList;
	}

	public static ArrayList<Hat> getCaughtList() {
		int count = 0;
		ArrayList<Hat> hatsList = getHatList();
		ArrayList<Hat> caughtList = new ArrayList<Hat>();
		for (Hat hat : hatsList) {
			if (hat.getCaught()) {
				caughtList.add(hat);
				System.out.println("hat22: " + caughtList.get(count).getName());
				count++;
			}
		}
		return caughtList;
	}

	public static ArrayList<Hat> getSeenList() {
		ArrayList<Hat> hatsList = getHatList();
		ArrayList<Hat> seenList = new ArrayList<Hat>();
		for (Hat hat : hatsList) {
			if (hat.getSeen())
				seenList.add(hat);
		}
		return seenList;
	}
	
	public static HatType getHatArea(float lati, float longi) {
		// UPPER LEFT
		if ((lati < 28.611839 && lati > 28.602157)
				&& (longi > -81.207633 && longi < -81.197376))
			return HatType.WATER;
		// UPPER RIGHT
		if ((lati < 28.611839 && lati > 28.602157)
				&& (longi > -81.197376 && longi < -81.187334))
			return HatType.FIRE;
		// BOTTOM LEFT
		if ((lati < 28.602157 && lati > 28.590476)
				&& (longi > -81.207633 && longi < -81.197376))
			return HatType.GRASS;
		// BOTTOM RIGHT
		if ((lati < 28.602157 && lati > 28.590476)
				&& (longi > -81.197376 && longi < -81.187334)) {
			if (lati % 2 == 0) {
				return HatType.EARTH;
			}
			return HatType.ELECTRIC;
		}

		// DEFAULT RETURN
		return HatType.DEFAULT;
	}

}
