package uz.pdp.service;

import uz.pdp.wrapper.CategoryListWrapper;
import uz.pdp.baseAbstractions.BaseService;

import uz.pdp.model.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.fileUtils.XmlUtil.readFromXmlFile;
import static uz.pdp.fileUtils.XmlUtil.writeToXmlFile;
import static uz.pdp.model.Category.PARENT_ID;

public class CategoryService implements BaseService<Category> {
    private static List<Category> categories = new ArrayList<>();
    private static final String pathName = "categories.xml";

    static {
        categories = readFromXmlFile(pathName, Category.class);
    }

    @Override
    public boolean add(Category category) {
        categories.add(category);
        saveCategoriesToFile();
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
            saveCategoriesToFile();
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
            deleteChildCategories(c.getId());
        }
    }

    @Override
    public List<Category> showAll() {
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
        Category categoryByName = getById(parentId);
        parentId = categoryByName != null ? categoryByName.getId() : PARENT_ID;
        ArrayList<Category> childCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId().equals(parentId) && category.isActive()) {
                childCategories.add(category);
            }
        }
        return childCategories;
    }

    private void saveCategoriesToFile() {
        writeToXmlFile(new File(pathName), new CategoryListWrapper(categories));
    }

    public Category getCategoryByName(String s) {
        for (Category category : categories) {
            if (category != null && category.getName().equalsIgnoreCase(s)) {
                return category;
            }
        }
        return null;
    }

    public Category getParentCategory(UUID id) {
        Category category = getById(id);
        if (category != null) {
            return getById(category.getParentId());
        }
        return null;
    }
}