package uos.seclass.bacchus.domains;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Foods")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_num")
    private int foodNum;

    /* Foreign Key */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false)
    private Dinner dinner;
    /* */

    @Column(name="name", nullable = false, length = 16)
    private String name;

    @Column(name="price", nullable = false)
    private int price;

    @Column(name="type", nullable = false, length = 10)
    private String type;

    @Column(name="num_food", nullable = false)
    private int count;
}

