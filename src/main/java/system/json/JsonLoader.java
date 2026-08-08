package system.json;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class JsonLoader {
	private static final ObjectMapper mapper = createMapper();

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(
            PropertyNamingStrategies.SNAKE_CASE
        );
        return mapper;
    }
    
    public static <T> T load(String file,Class<T> clazz) throws IOException{
    	return mapper.readValue(new File(file), clazz);
    }
}
