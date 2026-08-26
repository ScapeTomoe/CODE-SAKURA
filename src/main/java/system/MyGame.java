package system;

import com.badlogic.gdx.Game;

import system.screen.CreditScreen;
import system.screen.DemoScreen;
import system.screen.TitleScreen;
import system.screen.DMapScreen;

public class MyGame extends Game{
	CreditScreen credit;
	TitleScreen title;
	DemoScreen demo;
	DMapScreen dmap;
	@Override
	public void create() {
		credit=new CreditScreen(this);
		title=new TitleScreen(this);
		demo=new DemoScreen(this);
		dmap=new DMapScreen(this,"001");
		setScreen(title);
	}

	
	public TitleScreen getTitleScreen() {return title;}
	public DemoScreen getDemoScreen() {return demo;}
	public DMapScreen getDMapScreen() {return dmap;}
}
