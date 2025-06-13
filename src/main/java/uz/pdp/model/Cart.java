package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;


import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseModel {
    private UUID userId;
    public CartItem item;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CartItem {
        private Product product;
        private int quantity;
    }
}
