package uos.seclass.bacchus.domains;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Orders")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_num")
    private int orderNum;

    /* Foreign Key */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_num", nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false)
    private Set<Dinner> dinners;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", nullable = false, updatable = false, insertable = false)
    private Set<FoodOrderCount> foodCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_code", nullable = false)
    private Style style;
    /* */

    @Column(name="state", nullable = false)
    private String state;

    @Column(name="order_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;

    @Column(name="delivered_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredTime;

    @Column(name="wanted_delivered_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wantedDeliveredTime;

    @Column(name="total_price", nullable = false)
    private int totalPrice;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="card_num", nullable = false)
    private String cardNum;
}

