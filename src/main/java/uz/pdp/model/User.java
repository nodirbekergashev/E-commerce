package uz.pdp.model;

import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;
import uz.pdp.enums.UserRole;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    private String name;
    private String username;
    private String password;
    private UserRole role;
}
