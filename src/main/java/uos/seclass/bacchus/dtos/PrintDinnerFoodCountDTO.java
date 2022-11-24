package uos.seclass.bacchus.dtos;

import lombok.*;
import uos.seclass.bacchus.domains.Food;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintDinnerFoodCountDTO {
    private int foodDinnerCountNum;

    private int dinnerNum;

    private Food food;

    private int count;
}

