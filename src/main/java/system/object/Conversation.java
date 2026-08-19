package system.object;

import java.io.IOException;

import system.data.ConversationData;
import system.json.JsonLoader;

public class Conversation {
	private ConversationData data;
	private int index=0;
	private boolean finish;
	public Conversation(String id,String dir) {
		finish=false;
		JsonLoader loader=new JsonLoader();
		try {
			data=loader.load("data/conversation/"+dir+"/"+id+".json",ConversationData.class);
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
		if(index==data.topic().size()-1)finish=true;
	}
	
	public ConversationData getData() {
		return data;
	}
	
	public boolean HaveFinished() {
		return finish;
	}
}
