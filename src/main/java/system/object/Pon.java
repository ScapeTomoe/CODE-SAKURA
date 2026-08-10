package system.object;

import java.util.Set;

import system.data.BattleObject;

public class Pon {
	private static final Set<String> ALLY_NAMES = Set.of("理恵", "和葉", "スカーレット");
	private BattleObject bo;
	private boolean isDown;
	private boolean isEnemy;
	private int currentHP;
	public Pon(BattleObject bo) {
		isDown=false;
		this.isEnemy = !ALLY_NAMES.contains(bo.name());
		this.bo=bo;
		this.currentHP=bo.hp();
	}
	
	public void heal(int value) {
		this.currentHP=Math.min(currentHP+value,bo.hp());
	}
	
	public void damage(int value) {
		this.currentHP=Math.max(0, currentHP-value);
		if(currentHP==0) isDown=true;
	}
	
	public boolean isDown() {
		return isDown;
	}
	
	public boolean isEnemy() {
		return this.isEnemy;
	}
	
	public BattleObject getBO() {
		return this.bo;
	}
}
