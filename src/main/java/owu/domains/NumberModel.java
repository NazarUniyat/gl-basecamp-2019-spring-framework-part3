package owu.domains;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NumberModel {

    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private int guess;

    @Override
    public String toString() {
        return "NumberModel{" +
                "id=" + id +
                ", guess=" + guess +
                '}';
    }
}
