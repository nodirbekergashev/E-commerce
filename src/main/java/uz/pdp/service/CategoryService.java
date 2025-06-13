package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private static final ArrayList<Category> categories = new ArrayList<>();
    @Override
    public boolean add(Category category) {
        categories.add(category);
        return true;
    }

    @Override
    public void update(UUID id, Category category) {
        Category old = getById(id);
        if (old != null) {
            old.setName(category.getName());
            old.updateTimestamp();
        }
    }

    @Override
    public void delete(UUID id) {
        Category category = getById(id);
        if (category != null) {
            category.setActive(false);
        }
    }

    @Override
    public List<Category> showAll() {
        return List.of();
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
}
