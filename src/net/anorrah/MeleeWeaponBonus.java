package net.anorrah;

public class MeleeWeaponBonus extends bonus {

	private int weaponDamage;
	
	public MeleeWeaponBonus(int damage)
	{
		this.weaponDamage = damage;
	}
	
	public void onAttack(enemyEntities enemy,damageObject damage, boolean onHit)
	{
		damage.damage +=weaponDamage;
	}
	
}
