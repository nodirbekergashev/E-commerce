package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseModel {
    private Category category;
    private UUID sellerId;
    private String name;
    private double price;
}
