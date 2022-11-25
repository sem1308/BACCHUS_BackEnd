package uos.seclass.bacchus.dtos;

import lombok.*;
import uos.seclass.bacchus.domains.*;

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

