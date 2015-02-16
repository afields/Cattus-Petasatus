package com.team.catswithhats;

import java.util.Random;

import com.team.catswithhats.Objects.Cat;
import com.team.catswithhats.Objects.Hat;


public class BattleSystem {
	
	private static int attackNumber;
	static Cat computerCat;
	
	//attackingCat is attacking defendingCat is defending
	public static void sendAttack(Cat attackingCat, Cat defendingCat, int attackNumber)
	{
		BattleSystem.attackNumber = attackNumber;
		
		//refresh values to represent a new fighting 'instance'
		calcTurnStats(attackingCat);
		calcTurnStats(defendingCat);
		
		// calculating turn stats slightly alters from base stats of each cat
		calcAttackingCatTurnStats(attackingCat); 
		calcDefendingCatTurnStats(defendingCat);
		
		//calculates the damage done (currently assumes only one attack per cat)
		calcDamage(attackingCat, defendingCat);	
	}
	
	private static void calcTurnStats(Cat cat)
	{
		cat.setTurnStrength(cat.getStrength() + cat.getHat().getAttacks().get(attackNumber).attackValue);
		cat.setTurnDefense(cat.getDefense());
		cat.setTurnAgility(cat.getAgility());
	}
	
	private static void calcDamage(Cat attackingCat, Cat defendingCat)
	{
		int bonusElementalAmt = 0;
		int calcAttack = 0;
		
		//1.5 Turn bonus if Defending cat is elementally weak
		if (isAttackElemental(attackingCat, defendingCat) == 1)
		{					
			bonusElementalAmt = (int) (attackingCat.getTurnStrength() * 1.5);
			attackingCat.setTurnStrength(bonusElementalAmt);
			bonusElementalAmt = (int) (attackingCat.getTurnDefense() * 1.5);
			attackingCat.setTurnDefense(bonusElementalAmt);
			bonusElementalAmt = (int) (attackingCat.getTurnAgility() * 1.5);
			attackingCat.setTurnAgility(bonusElementalAmt);
		}
		
		if (attackingCat.getTurnAgility() >= defendingCat.getTurnAgility())
		{
			calcAttack = attackingCat.getTurnStrength() - defendingCat.getTurnDefense();
			
			//'breaking even' and negative values are possible because of nearbyVal
			if(calcAttack > 0)
			{                                      
				defendingCat.setCurrentHP(defendingCat.getCurrentHP() - calcAttack);
			}
		}
	}
	
	// This method is meant to return true if the defending cat is elementally weak. The method is currently simple and needs to be updated accordingly.
	private static int isAttackElemental(Cat attackingCat, Cat defendingCat)
	{  
		return attackingCat.getHat().compareTo(defendingCat.getHat());
	}
	
	public static String getSuccessfulAttackMsg(Cat attackingCat, Cat defendingCat)
	{
		int effectiveAMT = 0;
		StringBuilder response = new StringBuilder();
		response.append(String.format("%s attacks with %s!\n", attackingCat.getName(), attackingCat.getHat().getAttacks().get(attackNumber).attackName));
		
		if (attackingCat.getTurnAgility() >= defendingCat.getTurnAgility())
		{
			effectiveAMT = attackingCat.getTurnStrength() - defendingCat.getTurnDefense();
		}
		
		//Attacking cat has successfully damaged defending cat
		if (effectiveAMT > 0)
		{					
			if (isAttackElemental(attackingCat, defendingCat) == 1)
			{
				response.append("Elemental! ");
			}
			if (isCritical(attackingCat, defendingCat))
			{
				response.append("Critical! ");
			}
			response.append("Damaged with " + effectiveAMT + " points!");
			
			if (defendingCat.getCurrentHP() == 0)
			{
				response.append(String.format("\n%s has been defeated!!", defendingCat.getName()));
			}
			
			return response.toString();
		}
		
		//returned 'ineffective' if no damage was done (ex. defending cat had more agility or defense points)
		return response.append("The move was ineffective...").toString();
	}
	
	//'critical' is simply defined as a lucky ratio between attacking cat and defending cat. It has no effect on modifying turn values.
	private static boolean isCritical(Cat attackingCat, Cat defendingCat)
	{  
		float attackingCatAttackRatio;
		float defendingCatDefenseRatio;
		float criticalRatio;
		
		attackingCatAttackRatio = (attackingCat.getTurnStrength()/attackingCat.getStrength());
		defendingCatDefenseRatio = (defendingCat.getTurnDefense()/defendingCat.getDefense());
		
		criticalRatio = attackingCatAttackRatio - defendingCatDefenseRatio;
		
		if (criticalRatio >= 0.3)
		{
			return true;
		}
		
		return false;
	}
	
	public void capture(){
		
		
	}
	
	//Attacking cat gets a bonus 'offset' to make the fight go faster
	private static void calcAttackingCatTurnStats(Cat attackingCat) 
	{		
		int nearbyVal = 0;
		int offset = 0;
		int calculatedTurnVal = 0;
		
		offset = (int) attackingCat.getTurnStrength() / 5;
		nearbyVal = getNearbyInt(attackingCat.getTurnStrength());
		calculatedTurnVal = nearbyVal + offset;
		attackingCat.setTurnStrength(calculatedTurnVal);
		
		offset = (int) attackingCat.getTurnDefense() / 5;
		nearbyVal = getNearbyInt(attackingCat.getTurnDefense());
		calculatedTurnVal = nearbyVal + offset;
		attackingCat.setTurnDefense(calculatedTurnVal);
		
		offset = (int) attackingCat.getTurnAgility() / 5;
		nearbyVal = getNearbyInt(attackingCat.getTurnAgility());
		calculatedTurnVal = nearbyVal + offset;
		attackingCat.setTurnAgility(calculatedTurnVal);
	}
	
	private static Cat calcDefendingCatTurnStats(Cat cat)
	{
		int nearbyVal = 0;
		
		nearbyVal = getNearbyInt(cat.getTurnStrength());
		cat.setTurnStrength(nearbyVal);
		
		nearbyVal = getNearbyInt(cat.getTurnDefense());
		cat.setTurnDefense(nearbyVal);
		
		nearbyVal = getNearbyInt(cat.getTurnAgility());
		cat.setTurnAgility(nearbyVal);
		
		return cat;
	}
	
	//Since HP is always positive and gaussianNearZero will be negative half of the time, chances of capturing are at most 1/2
	public static boolean attemptCapture(Cat capturingCat){
		int gaussianNearZero = 0;
		Random rand = new Random();
		
		//gaussianNearZero will most likely equal an int around zero, even though getCurrentHP is a factor
		gaussianNearZero = (int) (rand.nextGaussian() * capturingCat.getCurrentHP());
		
		if (capturingCat.getCurrentHP() < gaussianNearZero)
		{ 
			return true;
		}
		
		return false;
	}
	
	//creates a respectable Cat-versary
	public static Cat generateAI(Cat referenceCat, Hat wildCatHat)
	{
		int closeLvl;
		
		//the average between to gaussian variables will significantly reduce strong random cats, but still make it possible
		closeLvl = (getNearbyInt(referenceCat.getLevel()) * getNearbyInt(referenceCat.getLevel())) / 2; 
		// total number of cats available including "default" case
		
		StringBuilder name = new StringBuilder();
		switch(wildCatHat.getType())
		{
		case FIRE:
			name.append("FireCat");
			break;
		case WATER:
			name.append("WaterCat");
			break;
		case EARTH:
			name.append("EarthCat");
			break;
		case GRASS:
			name.append("GrassCat");
			break;
		case ELECTRIC:
			name.append("ElectricCat");
			break;
		default:
			name.append("DefaultCat");
		}
		
//		int n = 18;	 
//		switch(enemySlotMachine(n)) {
//		case 1:
//			computerCat = new Cat("Feral Cat", wildCatHat);
//			break;
//		case 2:
//			computerCat = new Cat("Rabid Cat", wildCatHat);
//			break;
//		case 3:
//			computerCat = new Cat("Ferocious Kitten", wildCatHat);
//			break;
//		case 4:
//			computerCat = new Cat("Lazy Kitten", wildCatHat);
//			break;
//		case 5:
//			computerCat = new Cat("Obese Cat", wildCatHat);
//			break;
//		case 6:
//			computerCat = new Cat("Hairless Cat", wildCatHat);
//			break;
//		case 7:
//			computerCat = new Cat("Stray Cat", wildCatHat);
//			break;
//		case 8:
//			computerCat = new Cat("Zombie Cat", wildCatHat);
//			break;
//		case 9:
//			computerCat = new Cat("Chicken-Cat", wildCatHat);
//			break;
//		case 10:
//			computerCat = new Cat("Spider Cat", wildCatHat);
//			break;
//		case 11:
//			computerCat = new Cat("SophistiKitten", wildCatHat);
//			break;
//		case 12:
//			computerCat = new Cat("SophistiCat", wildCatHat);
//			break;
//		case 13:
//			computerCat = new Cat("Tony, the Fat Cat", wildCatHat);
//			break;
//		case 14:
//			computerCat = new Cat("DJ Kitty Kat", wildCatHat);
//			break;
//		case 15:
//			computerCat = new Cat("Cobra Kitty", wildCatHat);
//			break;
//		case 16:
//			computerCat = new Cat("Cat Dog", wildCatHat);
//			break;
//		case 17:
//			computerCat = new Cat("Cat Dog Centipede", wildCatHat);
//			break;
//		default:
//			computerCat = new Cat("Feral Cat", wildCatHat);
//			break;
//		}
		
		computerCat = new Cat(name.toString(), wildCatHat);
		
		//since getNearbyInt can (unlikely) produce a strong Feral Cat, the following lvlUp loop will generate accordingly strong stats
		while (closeLvl > 0){
			computerCat.levelUp();
			closeLvl--;
		}
		
		return computerCat;
	}

	public static void simpleAI(Cat computerCat, Cat playerCat){
		
		Random rand = new Random();
		BattleSystem.sendAttack(computerCat, playerCat, rand.nextInt(4));
	}
	
	private static int getNearbyInt(int val)
	{
		int calculatedVal = 0;
		Random rand = new Random();
		
		calculatedVal = (int)Math.abs((rand.nextGaussian() * 2) * val);
		
		return calculatedVal;
	}
}
