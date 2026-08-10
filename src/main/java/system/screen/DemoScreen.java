package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
		
	}

	@Override
	public void render(float delta) {
		//キー処理
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			game.setScreen(game.getTitleScreen());
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch,"この画面はデモ版です",200,600);
		font.draw(batch,"矢印キーで選択しデモ機能をプレイしてください",200,560);
		font.draw(batch,"ショップ",200,450);
		font.draw(batch,"戦闘",200,350);
		font.draw(batch,"会話",200,250);
		font.draw(batch,"依頼サイト",200,150);
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
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
