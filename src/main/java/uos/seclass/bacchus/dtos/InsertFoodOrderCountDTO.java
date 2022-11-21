package uos.seclass.bacchus.dtos;

import lombok.*;
import uos.seclass.bacchus.domains.Order;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertFoodOrderCountDTO {
    private int foodNum;

    private int name;

    private int count;

    private int price;
}

