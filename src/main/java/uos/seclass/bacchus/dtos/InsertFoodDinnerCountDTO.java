package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertFoodDinnerCountDTO {
    private int foodNum;

    private int count;
}

