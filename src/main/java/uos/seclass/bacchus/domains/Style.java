package uos.seclass.bacchus.domains;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Styles")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Style {
    @Id
    @Column(name="style_code", length = 10)
    private String styleCode;

    /* Foreign Key */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_code", nullable = false, insertable = false, updatable = false)
    private List<Order> orders;
    /*  */

    @Column(nullable = false, length = 64)
    private String content;
}

