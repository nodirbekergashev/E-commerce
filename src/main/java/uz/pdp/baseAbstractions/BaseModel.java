package uz.pdp.baseAbstractions;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter

public abstract class BaseModel {
    private final UUID id;
    @Setter
    private boolean isActive;;

    public BaseModel() {
        this.id = UUID.randomUUID();
        this.isActive = true;
    }


}
