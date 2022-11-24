package uos.seclass.bacchus.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uos.seclass.bacchus.domains.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintOrderDinnerDTO {
    private PrintDinnerDTO dinner;

    private Style style;

    private Set<OrderFoodCount> foodCounts;
}

