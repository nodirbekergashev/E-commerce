package uz.pdp.baseAbstractions;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter

public abstract class BaseModel {
    private final UUID id;
    @Setter
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseModel() {
        this.id = UUID.randomUUID();
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public void updateTimestamp() { // qachondir nimadir o'zgarganda chaqiriladi va updated at o'zgartirib boriladi
        this.updatedAt = LocalDateTime.now();
    }

}
