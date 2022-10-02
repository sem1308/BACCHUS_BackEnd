package uos.seclass.bacchus.domains;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_num", nullable = false)
    private Employee employee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false)
    private Set<Dinner> dinners;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_code", nullable = false)
    private Style style;
    /* */

    @Column(name="total_price", nullable = false)
    private int totalPrice;

    @Column(name="order_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
}

