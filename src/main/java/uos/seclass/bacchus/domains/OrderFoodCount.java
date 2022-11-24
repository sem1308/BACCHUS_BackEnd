package uos.seclass.bacchus.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity(name = "OrderFoodCounts")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderFoodCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_food_count_num")
    private int orderFoodCountNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_dinner_num", nullable = false)
    private OrderDinner orderDinner;

    @ManyToOne(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
    @JoinColumn(name = "food_num", nullable = false)
    private Food food;
    /*  */

    @Column(name="food_name", nullable = false)
    private String foodName;

    @Column(name="price", nullable = false)
    private int price;

    @Column(name="count", nullable = false)
    private int count;
}

