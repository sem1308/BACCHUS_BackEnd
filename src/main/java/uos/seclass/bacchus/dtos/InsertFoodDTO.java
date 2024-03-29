package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertFoodDTO {
    private String name;

    private int stock;

    private int price;

    private String type;
}

