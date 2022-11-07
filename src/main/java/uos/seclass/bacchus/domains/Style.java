package uos.seclass.bacchus.domains;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Style {
    @Id
    @Column(name="style_code", length = 10)
    private String styleCode;

    @Column(nullable = false, length = 64)
    private String content;
}

