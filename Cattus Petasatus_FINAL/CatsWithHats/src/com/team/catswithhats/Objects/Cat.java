package com.team.catswithhats.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.team.catswithhats.CatsWithHats;

public class Cat {

	CatsWithHats game;
	private String name;
	private Hat hat;
	private int baseStrength;
	private int baseDefense;
	private int baseAgility;
	private int baseHP;
	private int turnStrength;
	private int turnDefense;
	private int turnAgility;
	private int currentHP;
	private int XPCurrent;
	private int XPToNextLevel;
	private int level;
	private String picturePath;
	private Texture picture;

	public Cat(String name, Hat hat) {
		this(name, 5, 4, 4, 10, hat, 1);
	}

	public Cat(String name, int strength, int agility, int defense, int baseHP,
			Hat hat, int level) {
		this(name, strength, agility, defense, baseHP, baseHP, hat, level, 1,
				500);
	}

	public Cat(String name, int strength, int agility, int defense, int currentHP, int baseHP, Hat hat, int level, int currentXP, int nextXP) {
    	setName(name);
    	setHat(hat);
    	setBaseStrength(strength);
    	setBaseAgility(agility);
    	setBaseDefense(defense);
    	setCurrentHP(currentHP);
    	setBaseHP(baseHP);
    	setLevel(level);
    	setXPCurrent(currentXP);
    	setXPToNextLevel(nextXP);
    	
//    	if(name == "Feral Cat") {
//    		this.picturePath = "catdb/Normal.jpg";
//    	}
//    	else if(name == "Rabid Cat") {
//    		this.picturePath = "catdb/RabidCat.png";
//    	}
//    	else if(name == "Ferocious Kitten") {
//    		this.picturePath = "catdb/FerociousKitten.png";
//    	}
//    	else if(name == "Lazy Kitten") {
//    		this.picturePath = "catdb/LazyKitten.png";
//    	}
//    	else if(name == "Obese Cat") {
//    		this.picturePath = "catdb/ObeseCat.png";
//    	}
//    	else if(name == "Hairless Cat") {
//    		this.picturePath = "catdb/HairlessCat.png";
//    	}
//    	else if(name == "Stray Cat") {
//    		this.picturePath = "catdb/StrayCat.png";
//    	}
//    	else if(name == "Zombie Cat") {
//    		this.picturePath = "catdb/ZombieCat.png";
//    	}
//    	else if(name == "Chicken-Cat") {
//    		this.picturePath = "catdb/ChickenCat.png";
//    	}
//    	else if(name == "Spider Cat") {
//    		this.picturePath = "catdb/SpiderCat.png";
//    	}
//    	else if(name == "SophistiKitten") {
//    		this.picturePath = "catdb/SophistiKitten.png";
//    	}
//    	else if(name == "SophistiCat") {
//    		this.picturePath = "catdb/SophistiCat.png";
//    	}
//    	else if(name == "Tony, the Fat Cat") {
//    		this.picturePath = "catdb/Tony.png";
//    	}
//    	else if(name == "DJ Kitty Kat") {
//    		this.picturePath = "catdb/DJ.png";
//    	}
//    	else if(name == "Cobra Kitty") {
//    		this.picturePath = "catdb/CobraKitty.png";
//    	}
//    	else if(name == "Cat Dog") {
//    		this.picturePath = "catdb/CatDog.png";
//    	}
//    	else if(name == "Cat Dog Centipede") {
//    		this.picturePath = "catdb/CatDogCentipede.png";
//    	}
//    	else {
//    		this.picturePath = "catdb/Normal.jpg";
//    	}
    	
    	this.picture = hat.getCatTexture();
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setHat(Hat hat) {
		this.hat = hat;
		this.picture = hat.getCatTexture();
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setXPCurrent(int currentXP) {
		XPCurrent = currentXP;
	}

	public void setXPToNextLevel(int nextXP) {
		XPToNextLevel = nextXP;
	}

	public int getStrength() {
		return this.baseStrength + this.hat.getStrength();
	}

	public int getDefense() {
		return this.baseDefense + this.hat.getDefense();
	}

	public int getAgility() {
		return this.baseAgility + this.hat.getAgility();
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public int getBaseHP() {
		return baseHP;
	}

	public int getTurnStrength() {
		return this.turnStrength;
	}

	public int getTurnDefense() {
		return this.turnDefense;
	}

	public int getTurnAgility() {
		return this.turnAgility;
	}

	public Texture getPicture() {
		return picture;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public String getName() {
		return name;
	}

	public Hat getHat() {
		return hat;
	}

	public int getXPCurrent() {
		return XPCurrent;
	}

	public int getXPToNextLevel() {
		return XPToNextLevel;
	}

	public int getLevel() {
		return level;
	}

	public void setBaseStrength(int baseStrength) {// setters for cat base stats
													// are for level up ability
		this.baseStrength = baseStrength;
	}

	public void setBaseDefense(int baseDefense) {// setters for cat base stats
													// are for level up ability
		this.baseDefense = baseDefense;
	}

	public void setBaseAgility(int baseAgility) {// setters for cat base stats
													// are for level up ability
		this.baseAgility = baseAgility;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = (currentHP < 0) ? 0 : currentHP;
	}

	public void setBaseHP(int baseHP) {
		this.baseHP = baseHP;
	}

	public void setTurnStrength(int turnStrength) {
		this.turnStrength = turnStrength;
	}

	public void setTurnDefense(int turnDefence) {
		this.turnDefense = turnDefence;
	}

	public void setTurnAgility(int turnAgility) {
		this.turnAgility = turnAgility;
	}

	/**
	 * Used for the ResultScreen
	 * 
	 * @param XPGained
	 *            Amount of XP that was gained from the battle
	 * @return Returns true if the player's cat levels up.
	 */
	public boolean increaseXP(int XPGained) {
		XPCurrent += XPGained;

		if (XPCurrent >= XPToNextLevel) {
			levelUp();
			return true;
		} else {
			return false;
		}
	}

	public void levelUp() {
		level++;
		XPToNextLevel += 4987 * level;
		baseAgility += (baseAgility * logarithmicFraction(baseAgility));
		baseStrength += (baseStrength * logarithmicFraction(baseStrength));
		baseDefense += (baseDefense * logarithmicFraction(baseDefense));
		baseHP += (baseHP * logarithmicFraction(baseHP));
		currentHP = baseHP;
		XPCurrent = 0;
	}

	private static float logarithmicFraction(int seed) {
		float returnFraction = 0;

		returnFraction = (float) Math.log(seed) / (float) Math.log(2);
		returnFraction = 1 / returnFraction;

		return returnFraction;
	}

	// TODO: Create a legitimate XP calculator
	public int getXPGivenWhenBeat(Cat winningCat) {
		int XPGained = 0;
		XPGained = (int)(logarithmicFraction(getXPToNextLevel())*(getXPToNextLevel()));
		
		if (level > 5){
			XPGained *= ((logarithmicFraction(getXPToNextLevel())/winningCat.getLevel()));
		}
		
		return XPGained;
	}
}
