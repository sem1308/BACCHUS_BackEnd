package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateDinnerFoodCountDTO {
    private int dinnerFoodCountNum;

    private int foodNum;

    private int count;
}
