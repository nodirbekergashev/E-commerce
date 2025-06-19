package uz.pdp.fileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uz.pdp.model.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {
    private static final XmlMapper xmlMapper;
    private static final String DATA = "data/";
    static {
        xmlMapper = XmlMapper.builder().build();
        xmlMapper.registerModule(new JavaTimeModule());
    }

    public static <T> void writeToXmlFile(File pathname, T data) {
        try {
            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DATA + pathname), data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> List<T> readFromXmlFile(String pathName, Class<T> clazz) {
        try {
            File file = new File(DATA + pathName);
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            return xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
