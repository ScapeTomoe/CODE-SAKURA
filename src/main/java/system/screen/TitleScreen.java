package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import system.MyGame;

public class TitleScreen implements Screen{
	private int select;
	private MyGame game;
	FreeTypeFontGenerator generator;
	Texture Sakuramati;
	BitmapFont font;
	SpriteBatch batch;
	Music bgm;
	Sound pi;
	Sound pin;
	
    public TitleScreen(MyGame game) {
        this.game = game;
    }

	@Override
	public void show() {
		select=0;
		generator=new FreeTypeFontGenerator(Gdx.files.internal("font/NotoSansJP.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter =
			    new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		parameter.borderWidth = 2;
		parameter.borderColor = Color.WHITE;
		parameter.characters="はじめからつづきクレジットゲームをおえる";
		font =
		    generator.generateFont(parameter);
		generator.dispose();
		batch=new SpriteBatch();
		Sakuramati=new Texture(Gdx.files.internal("image/background/city.png"));
		pi= Gdx.audio.newSound(Gdx.files.internal("audio/se/pi.wav"));
		pin= Gdx.audio.newSound(Gdx.files.internal("audio/se/pin.mp3"));
		bgm = Gdx.audio.newMusic(
			    Gdx.files.internal("audio/bgm/title.mp3")
				);
		bgm.setLooping(true);
		bgm.setVolume(0.5f);
		bgm.play();
	}

	@Override
	public void render(float delta) {
		//キー入力処理
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if(select>0) {
				select--;
				pi.play();
			}
        }
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if(select<3) {
				select++;
				pi.play();
			}
        }
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			pin.play();
			switch (select) {
			case 0:
				game.setScreen(game.getDemoScreen());
				break;
			case 1:
				game.setScreen(game.getDemoScreen());
				break;
			case 2:
				break;
			case 3:
				Gdx.app.exit();
				break;
			}
        }
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(Sakuramati,0,-300);
		final String[] phrase= {"はじめから","つづきから","クレジット","ゲームをおえる"};
		for(int i=0;i<4;i++) {
			if(i==select)font.setColor(Color.RED);
			else font.setColor(Color.WHITE);
			font.draw(batch,phrase[i],900,400-i*100);
		}
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
		bgm.stop();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		Sakuramati.dispose();
		bgm.dispose();
		pi.dispose();
	}

}
