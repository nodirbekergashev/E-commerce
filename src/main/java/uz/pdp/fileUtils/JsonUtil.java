package uz.pdp.fileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    protected static final ObjectMapper mapper;
    private static final String DATA = "data/";

    static {
        mapper = JsonMapper.builder().enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER).build();
        mapper.registerModule(new JavaTimeModule());
    }

    public static <T> void writeToJsonFile(String pathname, T data) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DATA + pathname), data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> ArrayList<T> readFromJsonFile(String pathName, Class<T> valueType) {
        try {
            if (new File(DATA + pathName).length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(new File(DATA + pathName),
                    mapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (IOException e) {
            System.err.println("Error to read from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
