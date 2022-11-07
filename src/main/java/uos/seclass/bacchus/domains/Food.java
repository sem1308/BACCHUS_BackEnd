package uos.seclass.bacchus.domains;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity(name = "Foods")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_num")
    private Integer foodNum;

    @Column(name="name", nullable = false, length = 16)
    private String name;

    @Column(name="stock", nullable = false)
    private Integer stock;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="type", nullable = false, length = 10)
    private String type;
}

