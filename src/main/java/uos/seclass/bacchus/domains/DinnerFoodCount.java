package uos.seclass.bacchus.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "DinnerFoodCounts")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class DinnerFoodCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dinner_food_count_num")
    private int dinnerFoodCountNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false)
    private Dinner dinner;

    @ManyToOne(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
    @JoinColumn(name = "food_num", nullable = false)
    private Food food;
    /*  */

    @Column(name="count", nullable = false)
    private int count;
}

