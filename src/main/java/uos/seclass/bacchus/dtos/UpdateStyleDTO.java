package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateStyleDTO {
    private String styleCode;

    private String name;

    private String content;

    private int price;
}

