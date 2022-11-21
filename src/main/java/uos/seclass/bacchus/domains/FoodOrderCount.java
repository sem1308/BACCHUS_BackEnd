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
    /*  */

    @Column(name="name", nullable = false)
    private int name;

    @Column(name="count", nullable = false)
    private int count;

    @Column(name="price", nullable = false)
    private int price;
}

