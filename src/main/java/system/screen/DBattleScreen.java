package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import system.MyGame;
import system.data.BattleObject;

public class DBattleScreen implements Screen{
	ShapeRenderer render;
	SpriteBatch batch;
	BitmapFont text;
	FreeTypeFontGenerator generator;
	private MyGame game;
	BattleObject rie=new BattleObject("理恵",500,2,50,40);
	BattleObject kazuha=new BattleObject("和葉",500,2,50,40);
	BattleObject enemy1=new BattleObject("クマ",500,2,50,40);
	BattleObject enemy2=new BattleObject("イノシシ",500,2,50,40);
	Music bgm;
	Sound text_log;
	public DBattleScreen(MyGame game) {
		this.game=game;
		render=new ShapeRenderer();
		batch=new SpriteBatch();
		bgm=Gdx.audio.newMusic(Gdx.files.internal("files/audio/bgm/ch4_battle.ogg"));
		bgm.setLooping(true);
		bgm.play();
		//行動値を決める
	}
	
	@Override
	public void show() {
		
	}
	@Override
	public void render(float delta) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void hide() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void dispose() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
}
