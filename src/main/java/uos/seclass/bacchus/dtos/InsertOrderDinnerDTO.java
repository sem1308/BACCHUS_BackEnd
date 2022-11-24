package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderDinnerDTO {
    private int dinnerNum;

    private String styleCode;

    private Set<InsertOrderFoodCountDTO> insertOrderFoodCountDTOs;
}

