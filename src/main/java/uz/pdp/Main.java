package uz.pdp;

import uz.pdp.enums.UserRole;
import uz.pdp.model.*;
import uz.pdp.service.*;

import java.util.*;

import static uz.pdp.model.Category.PARENT_ID;

public class Main {

    static Scanner strScan = new Scanner(System.in);
    static Scanner intScan = new Scanner(System.in);
    static UserService userService = new UserService();
    static CartService cartService = new CartService();
    static CategoryService categoryService = new CategoryService();
    static OrderService orderService = new OrderService();
    static ProductService productService = new ProductService();
    static User currentUser;

    public static void main(String[] args) {
        int stepCode = 10;
        while (stepCode != 0) {
            stepCode = printMenu();
            switch (stepCode) {
                case 1 -> register();
                case 2 -> {
                    currentUser = login();
                    if (currentUser != null) {
                        if (currentUser.getRole() == UserRole.ADMIN) {

                            System.out.println("Welcome Admin " + currentUser.getName() + "!");
                            int adminStepCode = 13;
                            while (adminStepCode != 0) {
                                adminStepCode = printAdminMenu();
                                switch (adminStepCode) {
                                    case 1 -> manageUsersPage();
                                    case 2 -> manageCategoriesPage();
                                    case 3 -> manageProductsPage();
                                    case 4 -> manageCartsPage();
                                    case 5 -> changeUserRolePage();
                                    case 6 -> searchGlobalPage();
                                    case 7 -> changePasswordPage(currentUser);
                                    case 8 -> addCategory();
                                    case 9 -> adminStepCode = logout();
                                    case 0 -> System.exit(13);
                                    default -> {
                                        System.out.println("Invalid input, try again!");
                                        printAdminMenu();
                                    }
                                }
                            }
                        } else if (currentUser.getRole() == UserRole.SELLER) {
                            int stepcode2 = -1;
                            while (stepcode2 != 0) {
                                System.out.println("""
                                        === Seller Menu ===
                                        1. Add Product
                                        2. Update Product
                                        3. List Product
                                        4. Delete Product
                                        0. Exit """);
                                stepcode2 = intScan.nextInt();

                                switch (stepcode2) {
                                    case 1 -> {
                                        System.out.println("Enter product name:");
                                        String productName = strScan.nextLine();

                                        System.out.println("Enter product price:");
                                        double price = intScan.nextDouble();

                                        System.out.println("Choose product category:");



                                    }
                                    case 2 -> {
                                        System.out.println("Enter product name to update:");
                                        String oldName = strScan.nextLine();

                                        List<Product> sellerProducts = productService.showAllProductsBySellerId(currentUser.getId());
                                        Product toUpdate = null;
                                        for (Product p : sellerProducts) {
                                            if (p.getName().equalsIgnoreCase(oldName)) {
                                                toUpdate = p;
                                                break;
                                            }
                                        }

                                        if (toUpdate == null) {
                                            System.out.println("Product not found.");
                                            break;
                                        }

                                        System.out.println("Enter new name (blank to keep):");
                                        String newName = strScan.nextLine();
                                        if (!newName.isBlank()) toUpdate.setName(newName);

                                        System.out.println("Enter new price (or -1 to keep current):");
                                        double newPrice = intScan.nextDouble();

                                        if (newPrice >= 0) toUpdate.setPrice(newPrice);

                                        productService.update(toUpdate.getId(), toUpdate);
                                        System.out.println("Product updated.");
                                    }
                                    case 3 -> {
                                        List<Product> sellerProducts = productService.showAllProductsBySellerId(currentUser.getId());
                                        if (sellerProducts.isEmpty()) {
                                            System.out.println("No products found.");
                                        } else {
                                            for (Product p : sellerProducts) {
                                                System.out.println("Name: " + p.getName() +
                                                        " | Price: $" + p.getPrice() +
                                                        " | Category: " + p.getCategory().getName() +
                                                        " | Active: " + p.isActive());
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        System.out.println("Enter product name to delete:");
                                        String delName = strScan.nextLine();

                                        List<Product> sellerProducts = productService.showAllProductsBySellerId(currentUser.getId());
                                        Product toDelete = null;
                                        for (Product p : sellerProducts) {
                                            if (p.getName().equalsIgnoreCase(delName)) {
                                                toDelete = p;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Welcome User " + currentUser.getName() + "!");
                            int stepCodeUser;
                            stepCodeUser = printUserMenu();
                            while (stepCodeUser != 0) {
                                switch (stepCodeUser) {
                                    case 1 -> {
                                        ArrayList<Cart> userCarts = cartService.getCartsByUserId(currentUser.getId());
                                        if (userCarts.isEmpty()) {
                                            System.out.println("Your cart is empty.");
                                        } else {
                                            System.out.println("Your Cart:");
                                            for (Cart cart : userCarts) {
                                                System.out.println(cart.getItem().getProduct().getName() + " - " + cart.getItem().getQuantity() + " pcs");
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        ArrayList<Category> childCategories = getChildCategories("Root");
                                        if (childCategories.isEmpty()) {
                                            System.out.println("No categories available.");
                                        } else {
                                            System.out.println("Available Categories:");
                                            int i = 1;
                                            for (Category category : childCategories)
                                                System.out.println(i++ + ":" + category.getName());

                                            System.out.print("Select a category by number to view products:");
                                            int categoryIndex = intScan.nextInt() - 1;
                                            Category category = childCategories.get(categoryIndex);
                                            System.out.println("You selected category: " + category.getName());
                                            while (true) {
                                                if (!getChildCategories(category.getName()).isEmpty()) {
                                                    ArrayList<Product> byCategory = productService.getByCategory(category.getName());
                                                    for (Product product : byCategory) {
                                                        System.out.println("Product Name: " + product.getName() +
                                                                ", Price: " + product.getPrice() +
                                                                ", Seller: " + userService.getById(product.getSellerId()).getName());
                                                    }
                                                }
                                                System.out.println("enter category name");


                                            }
                                        }
                                    }
                                    case 3 -> {
                                        orderService.getOrdersByUserId(currentUser.getId())
                                                .forEach(order -> System.out.println("Order ID: " + order.getId() + ", Total: " + order.getTotalPrice()));
                                        ;
                                    }
                                    case 4 -> {
                                        ArrayList<Order> historyByUser = orderService.getHistoryByUserId(currentUser.getId());
                                        if (historyByUser.isEmpty()) {
                                            System.out.println("No order history found.");
                                        } else {
                                            System.out.println("Order History:");
                                            for (Order order : historyByUser) {
                                                System.out.println("Order ID: " + order.getId() + ", Total: " + order.getTotalPrice());
                                            }
                                        }
                                    }
                                    case 5 -> System.out.println("Update Account functionality not implemented yet.");
                                    case 0 -> System.out.println("Exiting User Menu...");
                                    default -> System.out.println("Invalid option, please try again.");
                                }
                                stepCodeUser = printUserMenu();
                            }
                        }
                    }else{
                        System.out.println("User not found!");
                    }
                }
                case 3 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid step code, please try again.");
            }
        }
    }


    public static void manageUsersPage() {
        showAllUsers();
        System.out.println("0. >>> Exit");
        System.out.println("Remove user by username:");
        System.out.print("Choose your own: ");
        String username = strScan.nextLine();
        if (username.equals("0")) {
            return;
        }
        User user = userService.getUserByUsername(username);
        if (user != null) {
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("You cannot delete yourself!");
                return;
            }
            userService.delete(user.getId());
            System.out.println("User removed successfully!");
        } else {
            System.out.println("User not found!");
        }
    }

    private static void manageProductsPage() {
        List<Product> products = productService.showAll();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        boolean willContinue = true;
        while (willContinue) {
            for (Product product : products) {
                User byId = userService.getById(product.getSellerId());
                if (byId != null) {
                    System.out.println("ID: " + product.getId() + ", Name: " + product.getName() +
                            ", Price: " + product.getPrice() + ", Seller: " + byId.getName() +
                            ", Category: " + product.getCategory().getName());
                }
            }
            System.out.print("Enter product ID to remove: ");
            UUID productId = UUID.fromString(strScan.nextLine());
            productService.delete(productId);
            System.out.println("Product removed successfully!");
            System.out.println("Do you want to continue managing products? (yes/no)");
            String response = strScan.nextLine().trim().toLowerCase();
            if (!response.equals("yes"))
                willContinue = false;
        }
    }

    public static void manageCategoriesPage() {
        System.out.println("--- Manage Categories ---");
        if (categoryService.showAll().isEmpty()) {
            System.out.println("No categories found.");
        } else {
            browseCategories();
        }
    }

    private static void manageCartsPage() {
        ArrayList<Cart> carts = cartService.showAllCarts();
        if (carts.isEmpty()) {
            System.out.println("No carts found.");
            return;
        }
        while (true) {
            System.out.println("Carts:");
            for (Cart cart : carts) {
                System.out.println("Cart ID: " + cart.getId() + ", User ID: " + userService.getById(cart.getUserId()).getUsername() +
                        ", Product: " + cart.getItem().getProduct().getName() +
                        ", Quantity: " + cart.getItem().getQuantity());
            }
            System.out.print("Enter cart ID to remove or exit to 0: ");
            String cartIdInput = strScan.nextLine();
            if (cartIdInput.equals("0")) {
                return;
            } else {
                try {
                    UUID cartId = UUID.fromString(cartIdInput);
                    cartService.delete(cartId);
                    System.out.println("Cart removed successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid cart ID format. Please try again.");
                }
            }
        }
    }

    private static void showAllUsers() {
        List<User> users = userService.showAll();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Users:");
            for (User user : users) {
                if (user.isActive()) {
                    System.out.println("ID: " + user.getId() + ", Name: " + user.getName() +
                            ", Username: " + user.getUsername() + ", Role: " + user.getRole());
                }
            }
        }
    }

    private static void register() {
        System.out.print("Enter first name:");
        String name = strScan.nextLine();
        System.out.print("Enter username:");
        String username = strScan.nextLine();
        if (!userService.isUsernameValid(username)) {
            System.out.println("Invalid username format. It should be 4-13 characters long and can only contain lowercase letters and underscores.");
            return;
        }
        System.out.print("Enter password:");
        String password = strScan.nextLine();
        userService.add(new User(name, username, password, UserRole.CUSTOMER));
    }

    private static User login() {
        System.out.print("Enter username:");
        String phoneNumber = strScan.nextLine();
        System.out.print("Enter password:");
        String password = strScan.nextLine();
        return userService.login(phoneNumber, password);
    }


    static int printMenu() {
        System.out.println("""
                1. >>> Register
                2. >>> Login
                0. >>> Exit
                """);
        System.out.print("Enter to: ");
        return intScan.nextInt();
    }

    static int printAdminMenu() {
        System.out.println("""
                 1. Manage Users
                 2. Manage Categories
                 3. Manage Products
                 4. Manage Carts
                 5. Change User Role
                 6. Search Global
                 7. Change Password
                 8. Add Category
                 9. Logout
                 0. >>> Exit
                """);
        System.out.print("Enter to: ");
        return intScan.nextInt();
    }

    static int printSellerMenu() {
        System.out.println("""
                1. >>> Add Product
                2. >>> List Products
                3. >>> Update Product
                4. >>> Delete Product
                0. >>> Logout
                """);
        System.out.print("Enter to: ");
        return intScan.nextInt();
    }

    static int printUserMenu() {
        System.out.println("""
                1. >>> Cart
                2. >>> Products
                3. >>> Orders
                4. >>> History
                5. >>> Update Account
                0. >>> Logout
                """);
        System.out.print("Enter to: ");
        return intScan.nextInt();
    }

    public static ArrayList<Category> getChildCategories(String parentName) {
        Category categoryByName = categoryService.getCategoryByName(parentName);
        return categoryService.getChildCategories(categoryByName.getParentId());
    }


    public static void browseCategories() {
        String prevInput = "Root";
        Category categoryByName = categoryService.getCategoryByName(prevInput);
        List<Category> level;
        UUID grandparentId = null;
        if (categoryByName == null) {
            level = categoryService.getChildCategories(null);
        } else {
            level = categoryService.getChildCategories(categoryByName.getId());
            grandparentId = categoryByName.getParentId();
        }
        while (true) {
            Category parentCategory;
            for (Category category : level) {
                parentCategory = categoryService.getParentCategory(category.getParentId());

                String name;

                if (parentCategory == null) {
                    name = " ";
                } else {
                    name = category.getName();
                }

                System.out.println("Name: " + category.getName() +
                        ", Parent" + name);
            }
            Category tempCategory;
            System.out.print("Go to: ");

            String input = strScan.nextLine();
            if (input.trim().equals(".")) {
                input = prevInput;
                UUID parentId = categoryService.getCategoryByName(input).getParentId();
                if (parentId == null) {
                    return;
                }
                grandparentId = categoryService.getById(parentId).getParentId();
                level = categoryService.getChildCategories(grandparentId);
                continue;
            }
            tempCategory = categoryService.getCategoryByName(input);
            level = categoryService.getChildCategories(tempCategory.getId());
            if (level.isEmpty()) {
                List<Product> products = productService
                        .getByCategory(input);
                if (products.isEmpty()) {
                    System.out.println("No products found in this category.");
                } else {
                    System.out.println("Products in " + input + ":");
                    for (Product product : products) {
                        System.out.println("Product Name: " + product.getName() +
                                ", Price: " + product.getPrice() +
                                ", Seller: " + userService.getById(product.getSellerId()).getName());
                    }
                }
            } else {
                tempCategory = categoryService.getCategoryByName(input);
                level = categoryService.getChildCategories(tempCategory.getId());
            }
            prevInput = input;
        }
    }

    public static int logout() {
        System.out.println("Logged out successfully!");
        return 0;
    }

    public static void searchGlobalPage() {
        System.out.println("Enter a search term: ");
        String keyword = strScan.nextLine();

        List<User> matchingUsers = new ArrayList<>();
        for (User user : userService.showAll()) {
            if (user != null) {
                if (user.getName().toLowerCase().contains(keyword)
                        || user.getUsername().toLowerCase().contains(keyword)) {
                    matchingUsers.add(user);
                }
            }
        }
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : productService.showAll()) {
            if (product.getName().toLowerCase().contains(keyword)
                    || product.getCategory().getName().toLowerCase().contains(keyword)) {
                matchingProducts.add(product);

            }
        }
        List<Order> matchingOrders = new ArrayList<>();
        for (Order order : orderService.showAll()) {
            User user = userService.getById(order.getUserId());
            if ((user != null && user.getName().toLowerCase().contains(keyword)) ||
                    String.valueOf(order.getTotalPrice()).contains(keyword)) {
                matchingOrders.add(order);
            }
        }
        List<Category> matchingCategories = new ArrayList<>();
        for (Category category : categoryService.showAll()) {
            if (category.getName().toLowerCase().contains(keyword)) {
                matchingCategories.add(category);
            }
        }

        List<Cart> matchingCarts = new ArrayList<>();
        for (Cart cart : cartService.showAllCarts()) {
            User user = userService.getById(cart.getUserId());
            Product product = productService.getById(cart.getId());
            if ((user != null && user.getName().toLowerCase().contains(keyword)) ||
                    (product != null && product.getName().toLowerCase().contains(keyword))) {
                matchingCarts.add(cart);
            }
        }

        System.out.println("\n=== Search results ===");
        if (!matchingUsers.isEmpty()) {
            System.out.println("\n Users: ");
            for (User user : matchingUsers) {
                System.out.println(user);
            }
        }

        if (!matchingProducts.isEmpty()) {
            System.out.println("\n Products: ");
            for (Product product : matchingProducts) {
                System.out.println(product);
            }
        }

        if (!matchingOrders.isEmpty()) {
            System.out.println("\n Orders: ");
            for (Order order : matchingOrders) {
                System.out.println(order);
            }
        }

        if (!matchingCategories.isEmpty()) {
            System.out.println("\n Categories:");
            for (Category category : matchingCategories) {
                System.out.println(category);
            }
        }

        if (!matchingCarts.isEmpty()) {
            System.out.println("\nCarts: ");
            for (Cart cart : matchingCarts) {
                System.out.println(cart);
            }
        }

        if (matchingUsers.isEmpty() && matchingProducts.isEmpty() &&
                matchingOrders.isEmpty() && matchingCategories.isEmpty() &&
                matchingCarts.isEmpty()) {
            System.out.println("No matching information was found.");
        }
    }

    private static void addCategory() {
        System.out.print("Enter category name: ");
        String name = strScan.nextLine();
        if (name.isBlank()) {
            System.out.println("Category name cannot be empty.");
            return;
        }
        System.out.println("choose parent category or is that a head category?(yes/no)");
        if (strScan.nextLine().equals("yes")) {
            if (categoryService.getCategoryByName(name) != null) {
                System.out.println("Category with this name already exists.");
            }
            Category newCategory = new Category(name, PARENT_ID);
            if (categoryService.add(newCategory)) {
                System.out.println("Category added successfully.");
            } else {
                System.out.println("Failed to add category.");
            }
            return;
        }
        UUID category = chooseParentCategory();
        Category newCategory = new Category(name, category);
        if (categoryService.add(newCategory)) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add category.");
        }
    }

    private static void changePasswordPage(User currentUser) {
        if (currentUser == null) {
            System.out.println("Log in first.");
            return;
        }

        System.out.print("Enter your old password: ");
        String oldPassword = strScan.nextLine();

        if (!currentUser.getPassword().equals(oldPassword)) {
            System.out.println("Incorrect old password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = strScan.nextLine();

        System.out.print("Re-enter new password: ");
        String confirmPassword = strScan.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("The passwords do not match.");
            return;
        }

        if (newPassword.length() < 4) {
            System.out.println("The password must be at least 4 characters long.");
            return;
        }

        currentUser.setPassword(newPassword);
        userService.update(currentUser.getId(), currentUser);

        System.out.println("Password updated successfully.");
    }

    static void changeUserRolePage() {
        showAllUsers();
        System.out.println("Enter username to change role:");
        String username = strScan.nextLine();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        UUID id = user.getId();
        System.out.println("Select new role: 1. ADMIN 2. SELLER 3. CUSTOMER");
        System.out.print("Enter to: ");
        int option = intScan.nextInt();
        switch (option) {
            case 1 -> {
                user = userService.getById(id);
                if (user.getRole() != UserRole.ADMIN) {
                    user.setRole(UserRole.ADMIN);
                    System.out.println(user.getName() + " Changed Successfully!!!");
                    break;
                }
                System.out.println("This user is already Admin...");
            }
            case 2 -> {
                user = userService.getById(id);
                if (user.getRole() != UserRole.SELLER) {
                    user.setRole(UserRole.SELLER);
                    System.out.println(user.getName() + " Changed Successfully!!!");
                    break;
                }
                System.out.println("This user is already Seller...");
            }
            case 3 -> {
                user = userService.getById(id);
                if (user.getRole() != UserRole.CUSTOMER) {
                    user.setRole(UserRole.CUSTOMER);
                    System.out.println(user.getName() + " Changed Successfully!!!");
                    break;
                }
                System.out.println("This user is already User...");
            }
        }
    }



    private static UUID chooseParentCategory() {
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