package system.json;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

    public <T> T load(String file, Class<T> clazz) throws IOException {
        FileHandle fileHandle = Gdx.files.internal(file);
        return mapper.readValue(fileHandle.read(), clazz);
    }
}