package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.fileUtils.XmlUtil;
import uz.pdp.model.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private static final ArrayList<Category> categories = new ArrayList<>();

    @Override
    public boolean add(Category category) {
        categories.add(category);
        XmlUtil.writeToJsonFile(new File("data/categories.xml"), categories);
        return true;
    }

    @Override
    public void update(UUID id, Category category) {
//        Category old = getById(id);
//        if (old != null) {
//            old.setName(category.getName());
//            old.updateTimestamp();
//        }
    }

    @Override
    public void delete(UUID id) {
        Category category = getById(id);
        if (category != null) {
            category.setActive(false);
            deleteChildCategories(category.getId());
            category.updateTimestamp();
        }
    }

    private void deleteChildCategories(UUID id) {
        ArrayList<Category> childCategories = new ArrayList<>();
        for (Category temp : categories) {
            if (temp.getParentId().equals(id)) {
                childCategories.add(temp);
            }
        }
        if (childCategories.isEmpty()) return;
        for (Category c : childCategories) {
            c.setActive(false);
            c.updateTimestamp();
            deleteChildCategories(c.getId());
        }
    }

    @Override
    public ArrayList<Category> showAll() {
        return categories;
    }

    @Override
    public Category getById(UUID id) {
        for (Category category : categories) {
            if (category != null && category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }
    public ArrayList<Category> getChildCategories(UUID parentId) {
        ArrayList<Category> childCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId().equals(parentId)) {
                childCategories.add(category);
            }
        }
        return childCategories;
    }
}
