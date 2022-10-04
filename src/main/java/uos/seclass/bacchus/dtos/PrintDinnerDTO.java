package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintDinnerDTO {
    private int dinnerNum;

    private String name;

    private String extraContent;

    private Set<PrintFoodDinnerCountDTO> foodCounts;

    private int numPeople;
}

