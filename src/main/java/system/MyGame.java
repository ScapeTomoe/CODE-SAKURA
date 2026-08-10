package system;

import com.badlogic.gdx.Game;

import system.screen.CreditScreen;
import system.screen.DemoScreen;
import system.screen.TitleScreen;

public class MyGame extends Game{
	CreditScreen credit;
	TitleScreen title;
	DemoScreen demo;
	@Override
	public void create() {
		credit=new CreditScreen(this);
		title=new TitleScreen(this);
		demo=new DemoScreen(this);
		setScreen(title);
	}

	
	public TitleScreen getTitleScreen() {return title;}
	public DemoScreen getDemoScreen() {return demo;}
}
