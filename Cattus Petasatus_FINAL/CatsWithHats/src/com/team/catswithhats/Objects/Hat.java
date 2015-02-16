package com.team.catswithhats.Objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.team.catswithhats.HatDatabase.HatType;

public class Hat implements Comparable<Hat>{
	
	private String name;
	private HatType type;
	private String found;
	private String description;
	private Texture hat;
	private Texture catWearingHat;
	private int strength;
	private int defense;
	private int agility;
	private boolean seen;
	private boolean caught;
	private ArrayList<Attack> attacks;

	public Hat(String name, int strength, int agility, int defense, Texture hat, Texture catTexture, ArrayList<Attack> attacks) 
	{
		this(name, strength, agility, defense, hat, catTexture, attacks, false, false);
	}
	
	public Hat(String name, int strength, int agility, int defense, Texture hat, Texture catTexture, ArrayList<Attack> attacks, boolean seen, boolean caught) 
	{
		setName(name);
		setStrength(strength);
		setAgility(agility);
		setDefense(defense);
		setHat(hat);		
		setAttacks(attacks);
		setSeen(seen);
		setCaught(caught);
		setCatTexture(catTexture);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HatType getType() {
		return type;
	}

	public void setType(HatType type) {
		this.type = type;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Texture getHat() {
		return hat;
	}

	public Texture getCatTexture() {
		return catWearingHat;
	}

	public void setHat(Texture hat) {
		this.hat = hat;
	}

	public void setCatTexture(Texture cat) {
		this.catWearingHat = cat;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}
	
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	public boolean getSeen() {
		return seen;
	}
	
	public void setCaught(boolean caught) {
		this.caught = caught;
	}
	
	public boolean getCaught() {
		return caught;
	}
	
	/*public void update() {
		HatDatabase.update(this);
	}*/
	
	@Override
	public boolean equals (Object h) {
		if (!(h instanceof Hat))
			return false;
		
		Hat hh = (Hat) h;
		if (getName().compareTo(hh.getName()) == 0)
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Hat other) {
		switch (other.getType()){
			case WATER:
				if (this.type == HatType.FIRE)
					return 1;
				else if (this.type == HatType.ELECTRIC)
					return -1;
				break;
			case FIRE:
				if (this.type == HatType.GRASS)
					return 1;
				else if (this.type == HatType.WATER)
					return -1;
				break;
			case GRASS:
				if (this.type == HatType.EARTH)
					return 1;
				else if (this.type == HatType.FIRE)
					return -1;
				break;
			case EARTH:
				if (this.type == HatType.ELECTRIC)
					return 1;
				else if (this.type == HatType.GRASS)
					return -1;
				break;
			case ELECTRIC:
				if (this.type == HatType.WATER)
					return 1;
				else if (this.type == HatType.EARTH)
					return -1;
				break;
			default:
				break;
		}
		return 0;
	}
}
