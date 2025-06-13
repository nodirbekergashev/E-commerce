package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Category extends BaseModel {
    private String name;
    private final List<Category> subCategories = new ArrayList<>();


    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
    }

}
