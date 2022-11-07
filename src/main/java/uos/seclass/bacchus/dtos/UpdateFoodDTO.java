package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateFoodDTO {
    private String name;

    private int stock;

    private int price;

    private String type;
}
