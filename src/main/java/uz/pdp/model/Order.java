package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Order extends BaseModel {
    private User userId;
    private Product productId;
}
