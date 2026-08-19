package system.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import system.MyGame;
import system.data.Option;
import system.data.Topic;
import system.format.ConversationType;
import system.object.Conversation;

public class DTalkScreen implements Screen{
	
	private MyGame game;
	private int select;
	private Conversation conversation;
	private ShapeRenderer render;
	private SpriteBatch batch;
	private ConversationType type;
	private Texture background,rie;
	private float clickDelta;
	private boolean flag;
	private String condition="",text;
	private List<String> texts=new ArrayList<>();
	private FreeTypeFontGenerator generator;
	private BitmapFont font;
	private Map<String,Texture> rie_Texture=new HashMap<>();
	public DTalkScreen(MyGame game,ConversationType type,String id) {
		this.game=game;
		this.type=type;
		this.conversation=new Conversation(id,type.toDirectoryName());
		rie_Texture.put("normal",new Texture(Gdx.files.internal("image/character/rie/normal.png")));
		rie_Texture.put("anger",new Texture(Gdx.files.internal("image/character/rie/anger.png")));
		rie_Texture.put("donbiki",new Texture(Gdx.files.internal("image/character/rie/donbiki.png")));
		rie_Texture.put("confuse",new Texture(Gdx.files.internal("image/character/rie/normal.png")));
		rie_Texture.put("smile",new Texture(Gdx.files.internal("image/character/rie/smile.png")));
		rie_Texture.put("surprised",new Texture(Gdx.files.internal("image/character/rie/surprised.png")));
		rie_Texture.put("tere",new Texture(Gdx.files.internal("image/character/rie/tere.png")));
		rie_Texture.put("tired",new Texture(Gdx.files.internal("image/character/rie/tired.png")));
	}
	
	@Override
	public void show() {
		generator=new FreeTypeFontGenerator(Gdx.files.internal("font/NotoSansJP.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter =
			    new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size=32;
		for(Topic topic:conversation.getData().topic()) {
			if(topic.content()!=null) {
				parameter.characters+=topic.content();
			}
			if(topic.options()!=null) {
				for(Option op:topic.options()) {
					parameter.characters+=op.text();
				}
			}
		}
		parameter.characters+="空テキスト";
		font =generator.generateFont(parameter);
		generator.dispose();
		render=new ShapeRenderer();
		batch=new SpriteBatch();
		background=new Texture(Gdx.files.internal("image/background/"+conversation.getFileName()));
	}

	@Override
	public void render(float delta) {
		clickDelta+=delta;
		Topic nowTopic=conversation.getData().topic().get(conversation.getIndex());
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && clickDelta>0.1f) {
			clickDelta=0.0f;
			if("choice".equals(nowTopic.type())) {
				texts.clear();
				condition=nowTopic.options().get(select).id();
				select=0;
			}
			if(conversation.HaveFinished()
			        || (nowTopic.condition()!=null && nowTopic.condition().equals(condition))) {
			    flag=true;
			}else {
			    conversation.slide();
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && clickDelta>0.1f) {
		    clickDelta=0f;
		    select=Math.max(select-1,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && clickDelta>0.1f) {
		    clickDelta=0f;
		    if("choice".equals(nowTopic.type())) {
		        select=Math.min(select+1,nowTopic.options().size()-1);
		    }
		}
		if(nowTopic.condition()==null) {
			text=(nowTopic.content()==null ? "空テキスト":nowTopic.content());
		}else {
			while(nowTopic.condition()!=null &&
			        !nowTopic.condition().equals(condition) &&
			        !conversation.HaveFinished()) {
				conversation.slide();
				nowTopic=conversation.getData().topic().get(conversation.getIndex());
			}
			text=(nowTopic.content()==null ? "空テキスト":nowTopic.content());
		}
		if("choice".equals(nowTopic.type())) {
			for(Option op:nowTopic.options()) {
				texts.add(op.text());
			}
		}
		if(nowTopic.emotion()!=null) {
			rie=rie_Texture.get(nowTopic.emotion());
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//背景およびキャラクター
		batch.begin();
		batch.draw(background,0,0,1280,720);
		batch.draw(rie,100,0,480,720);
		batch.end();
		//テキストボックス
		render.begin(ShapeType.Filled);
		render.setColor(Color.WHITE);
		render.rect(0f,0f,1280f,200f);
		render.setColor(Color.BLACK);
		render.rect(10f,10f,1260f,180f);
		render.end();
		//テキスト
		batch.begin();
		if("choice".equals(nowTopic.type())) {
			float i=3.0f;
			for(String s:texts) {
				font.draw(batch,s,55f,60f*i);
				i-=1.0f;
			}
		}else {
			font.draw(batch,text,15f,180f);
		}
		batch.end();
		//ここの処理は選択モードの時実行
		if("choice".equals(nowTopic.type())) {
			Gdx.gl.glLineWidth(3f);
			render.begin(ShapeType.Line);
			render.setColor(Color.WHITE);
			render.rect(50f,60f*(3-select)-32f,400f,37f);
			render.end();
		}
		if(flag)game.setScreen(game.getDemoScreen());
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
		dispose();
	}

	@Override
	public void dispose() {
		render.dispose();
		batch.dispose();
		font.dispose();
		background.dispose();
	}
	
}
