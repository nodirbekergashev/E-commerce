package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService implements BaseService<Product> {
    private ArrayList<Product> products = new ArrayList<>();
    @Override
    public boolean add(Product product) {
        products.add(product);
        return true;
    }

    @Override
    public void update(UUID id, Product product) {
        Product old = getById(id);
        if (old != null) {
            old.setCategory(product.getCategory());
            old.setName(product.getName());
            old.setPrice(product.getPrice());
            old.setActive(product.isActive());
            old.updateTimestamp();
        }
    }

    @Override
    public void delete(UUID id) {
        Product deletingProduct = getById(id);
        if (deletingProduct != null) {
            deletingProduct.setActive(false);
        }
    }

    @Override
    public List<Product> showAll() {
        return products;
    }

    @Override
    public Product getById(UUID id) {
        for (Product p : products) {
            if (p != null && p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
