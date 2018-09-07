package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Entity {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private boolean archive;
}
