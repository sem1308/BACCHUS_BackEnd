package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateDinnerForm {
    private Set<UpdateDinnerFoodCountDTO> foodCountDTOs;

    private UpdateDinnerDTO UpdateDinnerDTO;
}

