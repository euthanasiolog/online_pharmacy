package entity.user;

import entity.Entity;
import lombok.*;


@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class User extends Entity {
    @Getter @Setter
    private String userName;
    @Getter @Setter
    private String eMail;
}
