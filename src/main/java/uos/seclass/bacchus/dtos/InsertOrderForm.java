package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderForm {
    private Set<InsertFoodOrderCountDTO> foodCountDTOs;

    private InsertOrderDTO insertOrderDTO;
}

