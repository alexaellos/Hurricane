package net.anorrah;

public class DuelingBonus extends bonus{
	
	public void onBeenHit(enemyEntities enemy, damageObject damage)
	{
		int vectorX = enemy.tX-Core.player.tX;
		int vectorY = enemy.tY-Core.player.tY ;
			System.out.println("vector X:" + vectorX + "vector Y:" + vectorY);
		if(vectorX<=1 &&vectorX>=-1&&vectorY<=1&&vectorY>=-1)
		{
			
		}
		else
		{
			damage.damage = 0;
		}
	}

}