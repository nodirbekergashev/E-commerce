package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Order extends BaseModel {
    private UUID userId;
    private UUID productId;
}
