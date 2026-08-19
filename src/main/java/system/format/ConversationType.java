package system.format;

public enum ConversationType {
	STORY("story"),CASUAL("casual"),QUEST("quest");
	private final String id; 
	ConversationType(String id){
		this.id=id;
	}
	
	public String toDirectoryName() {
		return this.id;
	}
}
