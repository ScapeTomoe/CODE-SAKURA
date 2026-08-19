package system.data;

import java.util.List;

public record Topic(
		String teller,
		String emotion,
		String type,
		String content,
		List<Option> options,
		String condition
		) {

}
