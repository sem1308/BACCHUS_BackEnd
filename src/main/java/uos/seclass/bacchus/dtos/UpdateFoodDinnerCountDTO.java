package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateFoodDinnerCountDTO {
    private int foodDinnerCountNum;

    private int foodNum;

    private int count;
}
