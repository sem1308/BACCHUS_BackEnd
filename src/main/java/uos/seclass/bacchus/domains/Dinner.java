package uos.seclass.bacchus.domains;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Dinners")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Dinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dinner_num")
    private int dinnerNum;

    /* Foreign Key */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_code", nullable = false)
    private Style style;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false, insertable = false, updatable = false)
    private List<Food> foods;
    /* */

    @Column(name="name", nullable = false, length = 16)
    private String name;

    @Column(name="extra_content", nullable = true, length = 64)
    private String extraContent;

    @Column(name="num_people", nullable = false)
    private int numPeople;

    @Column(name="order_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
}

