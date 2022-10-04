package uos.seclass.bacchus.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "FoodOrderCounts")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class FoodOrderCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_order_count_num")
    private int foodOrderCountNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_num", nullable = false)
    private Food food;
    /*  */

    @Column(name="count", nullable = false)
    private int count;
}

