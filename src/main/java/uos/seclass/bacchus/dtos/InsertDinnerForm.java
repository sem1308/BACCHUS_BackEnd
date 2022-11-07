package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertDinnerForm {
    private Set<InsertFoodDinnerCountDTO> foodCountDTOs;

    private InsertDinnerDTO insertDinnerDTO;
}
