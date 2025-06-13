package uz.pdp.service;


import uz.pdp.model.Cart;
import uz.pdp.model.Product;

import java.util.ArrayList;

import java.util.UUID;

public class CartService {
    private final ArrayList<Cart> carts = new ArrayList<>();

    public void add(UUID userId, Product product, int quantity) {
        Cart.CartItem item = new Cart.CartItem(product, quantity);
        carts.add(new Cart(userId, item));
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
}
