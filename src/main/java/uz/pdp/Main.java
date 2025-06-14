package uz.pdp;

public class Main {

    static Scanner strScan = new Scanner(System.in);
    static Scanner intScan = new Scanner(System.in);
    static UserService userService = new UserService();
    static CartService cartService = new CartService();
    static CategoryService categoryService = new CategoryService();
    static OrderService orderService = new OrderService();
    static ProductService productService = new ProductService();


    public static void main(String[] args) {
        int stepCode = 13;
        while (stepCode != 0) {
            stepCode = printMenu();
            switch (stepCode) {
                case 1 -> register();
                case 2 -> {
                    User currentUser = login();
                    if (currentUser != null) {
                        if (currentUser.getRole() == UserRole.ADMIN) {

                            System.out.println("Welcome Admin " + currentUser.getName() + "!");
                            int adminStepCode = 13;
                            adminStepCode = printAdminMenu();
                            while (adminStepCode != 0) {
                                adminStepCode = printAdminMenu();
                                switch (adminStepCode) {
                                    case 1 -> userService.showAll();
                                    case 2 -> {
                                        System.out.print("Enter id to delete: ");
                                        UUID id = UUID.fromString(strScan.nextLine());

                                        userService.delete(id);
                                    }
                                    case 3 -> {
                                        System.out.print("Enter username to change role: ");
                                        String username = strScan.nextLine();
                                        System.out.print("Enter role to change role: ");
                                        String role = strScan.nextLine();
                                        userService.changeUserRole(username, UserRole.valueOf(role.toUpperCase()));
                                    }
                                    case 0 -> System.out.println("Exiting Admin Menu...");
                                    default -> System.out.println("Invalid option, please try again.");
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


                                        // Simple category creation (optional: select existing)
                                        System.out.println("Enter category name:");
                                        String categoryName = strScan.nextLine();
                                        Category category = new Category();
                                        category.setName(categoryName);

                                        Product product = new Product(category, currentUser.getId(), productName, price);
                                        if (productService.add(product)) {
                                            System.out.println("Product added successfully!");
                                        } else {
                                            System.out.println("Product with this name already exists!");
                                        }
                                    }

                                    case 2 -> {
                                        System.out.println("Enter product name to update:");
                                        String oldName = strScan.nextLine();

                                        List<Product> sellerProducts = productService.showAllProducts(currentUser.getId());
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
                                        List<Product> sellerProducts = productService.showAllProducts(currentUser.getId());
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

                                        List<Product> sellerProducts = productService.showAllProducts(currentUser.getId());
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
                                        ArrayList<Category> categories = categoryService.showAll();
                                        if (categories.isEmpty()) {
                                            System.out.println("No categories available.");
                                        } else {
                                            System.out.println("Available Categories:");
                                            int i = 1;
                                            for (Category category : categories)
                                                System.out.println(i++ + ":" + category.getName());

                                            System.out.print("Select a category by number to view products:");
                                            int categoryIndex = intScan.nextInt() - 1;
                                            Category category = categories.get(categoryIndex);
                                            System.out.println("You selected category: " + category.getName());
                                            if (getChildCategories(category.getId()).isEmpty()) {
                                                ArrayList<Product> byCategory = productService.getByCategory(category);

                                            }
                                        }
                                    }
                                    case 3 -> System.out.println("Orders functionality not implemented yet.");
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
                    }
                }
                case 3 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid step code, please try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter first name:");
        String name = strScan.nextLine();
        System.out.print("Enter username:");
        String username = strScan.nextLine();
        System.out.print("Enter password:");
        String password = strScan.nextLine();
        userService.add(new User(name, username, password, UserRole.USER));
    }

    private static User login() {
        System.out.print("Enter phone number:");
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
                1. >>> Show All Users
                2. >>> Delete
                3. >>> Change Role
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
                0. >>> Exit
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
                0. >>> Exit
                """);
        System.out.print("Enter to: ");
        return intScan.nextInt();
    }

    public static ArrayList<Category> getChildCategories(UUID parentId) {
        return categoryService.getChildCategories(parentId);
    }
}