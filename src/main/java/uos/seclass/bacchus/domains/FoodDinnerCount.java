package uos.seclass.bacchus.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "FoodDinnerCounts")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class FoodDinnerCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_dinner_count_num")
    private int foodDinnerCountNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false)
    private Dinner dinner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_num", nullable = false)
    private Food food;
    /*  */

    @Column(name="count", nullable = false)
    private int count;
}

