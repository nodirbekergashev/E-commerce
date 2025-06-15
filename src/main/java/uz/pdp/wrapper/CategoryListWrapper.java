package uz.pdp.wrapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import uz.pdp.model.Category;

import java.util.List;

@JacksonXmlRootElement(localName = "Categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListWrapper {
    @JacksonXmlProperty(localName = "category")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Category> categoryList;
}
