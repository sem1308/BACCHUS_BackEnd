package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderFoodCountDTO {
    private int foodNum;

    private String foodName;

    private int count;

    private int price;
}

