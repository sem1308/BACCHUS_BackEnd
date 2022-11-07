package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateDinnerDTO {
    private String name;

    private String extraContent;

    private int numPeople;
}
