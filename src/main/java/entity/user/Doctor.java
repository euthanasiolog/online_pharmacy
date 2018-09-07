package entity.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User{
    @Getter @Setter
    private String specialization;
    @Getter @Setter
    private String workplace;
}
