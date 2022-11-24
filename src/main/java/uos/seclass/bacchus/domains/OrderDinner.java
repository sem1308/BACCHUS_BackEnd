package uos.seclass.bacchus.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "OrderDinners")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_dinner_num")
    private int orderDinnerNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", nullable = false)
    private Order order;
    /*  */

    @JoinColumn(name = "dinner_num", nullable = false)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Dinner dinner;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_dinner_num", nullable = false, updatable = false, insertable = false)
    private Set<OrderFoodCount> foodCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_code", nullable = false)
    private Style style;
}

