package system.data;

import java.util.List;

public record Request(
		String id,
		Info info,
		List<Item> needItem,
		List<Item> getItem,
		Time time,
		String canReceive
		) {
	
	
	 public record Info(
			String name,
			int reward,
			String description,
			String tag,
			int moti
			) {}
	
	 public record Item(
			String name,
			int num
			) {}
	
	 public record Time(
			int start,
			int end
			) {}
}
