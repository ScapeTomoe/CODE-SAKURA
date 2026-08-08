package system.json;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class JsonWriter {

    private static final ObjectMapper mapper = createMapper();

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(
            PropertyNamingStrategies.SNAKE_CASE
        );
        return mapper;
    }


    public static void write(
            String path,
            Object object
    ) throws IOException {

        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(
                  new File(path),
                  object
              );
    }
}