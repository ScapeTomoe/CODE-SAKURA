package system.data;

import java.util.List;

public record ConversationData(
		String id,
		String back_ground,
		List<Topic> topic
		) {}
