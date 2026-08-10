package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import system.MyGame;

public class DemoScreen implements Screen{
	private int select=0;
	private MyGame game;
	BitmapFont font;
	SpriteBatch batch;
	FreeTypeFontGenerator generator;
	Sound pi;
	public DemoScreen(MyGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		generator=new FreeTypeFontGenerator(Gdx.files.internal("files/フォント/NotoSansJP.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter =
			    new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size=32;
		parameter.characters="この画面はデモ版です。矢印キーで選択しデモ機能をプレイしてください"+"ショップ戦闘会話依頼サイト"+"Xキーでタイトルに戻る";
		font =generator.generateFont(parameter);
		generator.dispose();
		batch=new SpriteBatch();
		pi=Gdx.audio.newSound(Gdx.files.internal("files/素材/pi.wav"));
		
	}

	@Override
	public void render(float delta) {
		//キー処理
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if(0<select) {
				select--;
				pi.play();
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if(select<3) {
				select++;
				pi.play();
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			switch(select) {
			case 0:
				break;
			case 1:
				game.setScreen(new DBattleScreen(game));
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			game.setScreen(game.getTitleScreen());
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch,"この画面はデモ版です",200,600);
		font.draw(batch,"矢印キーで選択しデモ機能をプレイしてください",200,560);
		String[] texts= {"ショップ","戦闘","会話","依頼サイト"};
		for(int i=0;i<4;i++) {
			if(i==select) {
				font.setColor(Color.RED);
			}
			else {
				font.setColor(Color.WHITE);
			}
			font.draw(batch,texts[i],200,450-100*i);
		}
		font.setColor(Color.WHITE);
		font.draw(batch,"Xキーでタイトルに戻る",800,450);
		batch.end();
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
		pi.dispose();
		batch.dispose();
		font.dispose();
	}

}
