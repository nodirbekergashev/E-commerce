package uz.pdp.fileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static uz.pdp.fileUtils.JsonFileUtils.mapper;

public class XmlUtils {
    private static final XmlMapper xmlMapper;
    static {
        xmlMapper = XmlMapper.builder().build();
    }

    public static <T> void writeToJsonFile(String pathname, T data)  {
        try {
            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("data" + pathname), data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> ArrayList<T> readFromJsonFile(String pathName) throws IOException{
        return mapper.readValue(new File(pathName), new TypeReference<ArrayList<T>>() {});
    }
}
