package uos.seclass.bacchus.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;

import javax.persistence.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintFoodDinnerCountDTO {
    private int foodDinnerCountNum;

    private int dinnerNum;

    private Food food;

    private int count;
}

