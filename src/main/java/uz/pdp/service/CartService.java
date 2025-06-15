package uz.pdp.service;


import uz.pdp.model.Cart;
import uz.pdp.model.Product;

import java.util.ArrayList;

import java.util.UUID;

public class CartService {
    public static final ArrayList<Cart> carts = new ArrayList<>();

    public void add(UUID userId, Product product, int quantity) {
        Cart.CartItem item = new Cart.CartItem(product, quantity);
        carts.add(new Cart(userId, item));
    }

    public Cart getById(UUID id) {
        for (Cart cart : carts) {
            if (cart != null && cart.getId().equals(id)) {
                return cart;
            }
        }
        return null;
    }

    public ArrayList<Cart> showAllCarts() {
        return carts;
    }

    public ArrayList<Cart> getCartsByUserId(UUID id) {
        ArrayList<Cart> userCarts = new ArrayList<>();
        for (Cart cart : carts) {
            if (cart != null && cart.getUserId().equals(id)) {
                userCarts.add(cart);
            }
        }
        return userCarts;
    }

    public double getTotalPrice(UUID userId) {
        double total = 0.0;
        for (Cart cart : carts) {
            if (cart.getUserId().equals(userId)) {
                total += cart.getItem().getProduct().getPrice() * cart.getItem().getQuantity();
            }
        }
        return total;
    }

    public void delete(UUID id) {
        Cart cart = getById(id);
        if (cart != null) {
            carts.remove(cart);
        } else {
            System.out.println("Cart not found");
        }
    }
}
