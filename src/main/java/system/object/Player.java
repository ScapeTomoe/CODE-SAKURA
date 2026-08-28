package system.object;

public class Player {
	private float playerX,playerY;
	public Player(float playerX,float playerY) {
		this.playerX=playerX;
		this.playerY=playerY;
	}

	public void move(float deltaX,float deltaY) {
		this.playerX+=deltaX;
		this.playerY+=deltaY;
	}
	
	public float getPlayerX() {
		return playerX;
	}
	
	public float getPlayerY() {
		return playerY;
	}
	
	public void setPlayerX(float value) {
		this.playerX=value;
	}
	
	public void setPlayerY(float value) {
		this.playerY=value;
	}
}
