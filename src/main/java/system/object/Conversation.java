package system.object;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import system.data.ConversationData;
import system.json.JsonLoader;

public class Conversation {
	private ConversationData data;
	private int index=0,nowIndex=0;
	private boolean finish;
	private float nowDistance=0.04f,stock=0.0f;
	private String nowText="";
	private Sound sound;
	public Conversation(String id,String dir) {
		finish=false;
		JsonLoader loader=new JsonLoader();
		try {
			sound=Gdx.audio.newSound(Gdx.files.internal("audio/se/rie_voice1.wav"));
			data=loader.load("data/conversation/"+dir+"/"+id+".json",ConversationData.class);
			nowText+=data.topic().get(index).content().charAt(0);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public String getConversationID() {
		return data.id();
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String getFileName() {
		return data.back_ground()+".png";
	}
	
	public void slide() {
		index=Math.min(index+1,data.topic().size()-1);
		nowIndex=0;
		System.out.println("今これ："+index);
		sound.play();
		if(data.topic().get(index).content()!=null)nowText=""+data.topic().get(index).content().charAt(nowIndex);
		if(index==data.topic().size()-1)finish=true;
	}
	
	public ConversationData getData() {
		return data;
	}
	
	public boolean HaveFinished() {
		return finish;
	}
	
	public void nextIndex(float dt) {
		String text=data.topic().get(index).content();
		if(text==null) return;
		stock+=dt;
		if(stock>=nowDistance && nowIndex <text.length()-1) {
			stock=0f;
			nowIndex++;
			if(text.charAt(nowIndex)=='k') {
				nowDistance=0.16f;
			}else {
				sound.play();
				nowDistance=0.04f;
				nowText+=text.charAt(nowIndex);
			}
		}
	}
	
	public String getNowText() {
		return nowText;
	}
}
