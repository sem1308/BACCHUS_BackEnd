package uos.seclass.bacchus.dtos;

import lombok.*;
import uos.seclass.bacchus.domains.Food;
import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertDinnerDTO {
    private String name;

    private int numPeople;

    private Set<Integer> foodNum;

    private String extraContent;
}

