package system;

import com.badlogic.gdx.Game;

import system.screen.CreditScreen;
import system.screen.TitleScreen;

public class MyGame extends Game{
	CreditScreen credit;
	TitleScreen title;
	@Override
	public void create() {
		credit=new CreditScreen(this);
		title=new TitleScreen(this);
		setScreen(title);
	}

	
	public TitleScreen getTitleScreen() {return title;}
	
}
