package net.anorrah;

public class SwordItem extends MeleeWeaponItem {

	
	String str;
	public SwordItem(String r, int currentLevel)
	{
		super(currentLevel);
		damage = 10+enchantment;
		str = r;
	}


	
	@Override
	public String description() {
		
		return "stabby stabby stab! " + enchantment;
	}
	
	
	
	
}
