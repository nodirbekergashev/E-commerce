package uz.pdp.fileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonUtil {
    protected static final ObjectMapper mapper;


    static {
        mapper = JsonMapper.builder().enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER).build();
    }
    public static <T> void writeToJsonFile(String pathname, T data)  {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("data" + pathname), data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> ArrayList<T> readFromJsonFile(String pathName) throws IOException{
        return mapper.readValue(new File(pathName), new TypeReference<ArrayList<T>>() {});
    }


}
