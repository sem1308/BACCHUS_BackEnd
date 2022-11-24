package uos.seclass.bacchus.domains;

import java.util.Set;

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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_num", nullable = false, updatable = false, insertable = false)
    private Set<DinnerFoodCount> foodCounts;
    /*  */

    @Column(name="name", nullable = false, length = 16)
    private String name;

    @Column(name="extra_content", nullable = true, length = 64)
    private String extraContent;

    @Column(name="num_people", nullable = false)
    private int numPeople;

    @Column(name="state", nullable = false)
    private String state;
}

