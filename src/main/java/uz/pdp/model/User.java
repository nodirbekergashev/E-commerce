package uz.pdp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;
import uz.pdp.enums.UserRole;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "User")
public class User extends BaseModel {
    private String name;
    private String username;
    private String password;
    private UserRole role;

    public User(UserRole role) {
        this.name = name;
        this.username = "admin";
        this.password = "123";
        this.role = role;
    }
}
