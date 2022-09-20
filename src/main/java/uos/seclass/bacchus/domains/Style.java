package uos.seclass.bacchus.domains;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false, length = 64)
    private String content;
}
