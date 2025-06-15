package uz.pdp;


import uz.pdp.model.Category;
import uz.pdp.model.Product;
import uz.pdp.model.User;
import uz.pdp.service.CategoryService;

import java.util.*;

import static uz.pdp.Main.*;
import static uz.pdp.model.Category.PARENT_ID;
import static uz.pdp.service.UserService.users;

public class TestMain {
    public static void main(String[] args) {

    }

    public static UUID addCategory() {
        UUID currId = PARENT_ID;

        while (true) {
            UUID finalId = currId;
            List<Category> categories = categoryService.showAll();
            List<Category> children = new ArrayList<>();

            for (Category category : categories) {
                if (Objects.equals(category.getParentId(), finalId)) {
                    children.add(category);
                }
            }

            Category currCat = categoryService.getById(finalId);

            if (currCat == null) {
                System.out.println("0. Head (current)");
            } else {
                System.out.printf("0. %s (current)\n", currCat.getName());
            }

            System.out.println("Sub categories");
            int c = 1;
            for (Category child : children) {
                System.out.println(c++ + ". " + child.getName());
            }
            System.out.print("Select category: ");
            int res = intScan.nextInt();

            if (res == 0) return currId;

            int idx = res - 1;
            if (idx >= 0 && idx < children.size()) {
                currId = children.get(idx).getId();
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
}

