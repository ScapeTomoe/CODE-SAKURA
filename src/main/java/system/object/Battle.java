package system.object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Battle {
	private List<ActionData> queue;

	public Battle(Pon rie, Pon kazuha, Pon enemy1, Pon enemy2) {
	    this.queue = new ArrayList<>();
	    queue.add(new ActionData(rie));
	    queue.add(new ActionData(kazuha));
	    queue.add(new ActionData(enemy1));
	    queue.add(new ActionData(enemy2));
	    //初期整理
	    queue.sort(Comparator.comparingInt(ActionData::getActionValue));    
		int minActionValue=queue.get(0).getActionValue();
		//全てを減算
		for(ActionData data:queue) {
			data.moveSlot(minActionValue);
		}
	}
	
	private class ActionData{
		private int actionValue;
		private Pon pon;
		public ActionData(Pon pon) {
			this.actionValue=200-pon.getBO().speed();
			this.pon=pon;
		}
		
		public int getActionValue() {
			return this.actionValue;
		}
		
		public String getName() {
			return pon.getBO().name();
		}
		
		public void act() {
			actionValue=200-pon.getBO().speed();
		}
		
		public void moveSlot(int value) {
			this.actionValue-=value;
		}
		
		public Pon getPon() {
			return this.pon;
		}
	}
	
	public void moveSlot() {
		//今行動したやつを処理
		queue.get(0).act();
		queue.removeIf(data -> data.getPon().isDown());
		if (isVictory() || isDefeat()) {
	        return; // 決着したので行動順の再計算は不要
	    }
		queue.sort(Comparator.comparingInt(ActionData::getActionValue));    
		int minActionValue=queue.get(0).getActionValue();
		//全てを減算
		for(ActionData data:queue) {
			data.moveSlot(minActionValue);
		}
	}
	
	public boolean isVictory() {
	    return queue.stream().allMatch(a -> !a.getPon().isEnemy());
	    // 生存者が全員 isEnemy=false（味方）→ 勝利
	}

	public boolean isDefeat() {
	    return queue.stream().allMatch(a -> a.getPon().isEnemy());
	    // 生存者が全員 isEnemy=true（敵）→ 敗北
	}
	
	
}
