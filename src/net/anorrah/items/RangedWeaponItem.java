package net.anorrah.items;

import java.util.ArrayList;

import net.anorrah.items.bonus.bonus;

public class RangedWeaponItem extends ItemObject{
	
	double damage;

	public RangedWeaponItem(int levelBonus)
	{
		super(levelBonus);
		possibleBonuses = new ArrayList<bonus>();
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

//	public double attack()
//	{
//		return damage;
//	}
	
	
}
