package entity.user;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Client extends User{
    @Getter @Setter
    private byte discount;
}
